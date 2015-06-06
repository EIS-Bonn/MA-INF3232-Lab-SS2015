package de.unibonn.iai.eis.qaentlod.io.streamprocessor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.core.Quad;

import de.unibonn.iai.eis.qaentlod.datatypes.Object2Quad;
import de.unibonn.iai.eis.qaentlod.io.utilities.ConfigurationLoader;
import de.unibonn.iai.eis.qaentlod.io.utilities.DataSetResults;
import de.unibonn.iai.eis.qaentlod.io.utilities.UtilMail;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.freeoferror.FreeOfError;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.measurability.Measurability;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.trust.verifiability.AuthenticityDataset;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.trust.verifiability.DigitalSignatures;
import de.unibonn.iai.eis.qaentlod.util.Dimension;
import de.unibonn.iai.eis.qaentlod.util.Metrics;
import de.unibonn.iai.eis.qaentlod.util.ResultDataSet;
import de.unibonn.iai.eis.qaentlod.util.Results;
import de.unibonn.iai.eis.qaentlod.util.ResultsHelper;

/**
 * This class read all the values produce by the producer an execute the metrics
 * over the quad obtained
 * 
 * @author Carlos
 */
public class Consumer extends Thread {
	/**
	 * 
	 */
	private StreamManager streamManager;
	private Producer producer;
	private int cont = 0;
	private boolean running;
	private static List<DataSetResults> results;
	private String mail;

	public DigitalSignatures digMetric = new DigitalSignatures(); // Metrics to
																	// be apply
	public AuthenticityDataset autMetric = new AuthenticityDataset(); // Metrics
																		// to be
																		// apply
	public FreeOfError freeMetric = new FreeOfError();
	public Measurability measurAbility = new Measurability();

	/**
	 * Creator of the class
	 * 
	 * @param streamManager
	 *            , this class is the stream Manager to put all the values
	 *            obtain
	 * @param producer
	 *            , producer of the events to be read
	 */
	public Consumer(StreamManager streamManager, Producer producer) {
		this.streamManager = streamManager;
		this.producer = producer;
	}

	/**
	 * This method start the thread to run the consumer
	 */
	public void run() {
		Quad value;
		int contAux = 0;
		this.setRunning(true);
		// Run the consumer while the producer is publishing data
		while (producer.isAlive() || streamManager.getCounter() > 0) {
			ResultSet aux = streamManager.get(); // Retrieves the resulset that
													// was published
			try {
				if (aux.hasNext()) {
					while (aux.hasNext()) {
						value = new Object2Quad(aux.next()).getStatement();
						// Here we compute all the metrics
						// Verifiability Metrics
						this.digMetric.compute(value);
						this.autMetric.compute(value);
						// Free of error metrics
						this.freeMetric.compute(value);
						// Measurability metrics
						this.measurAbility.compute(value);
						setCont(getCont() + 1);
						contAux++;
						if (contAux == 10000) { // Message to control the
												// information
							contAux = 0;
							System.out.println("Read 10000 triples");
						}
						if (this.cont % 100000 == 0) {
							this.writeFile(false);// When the dataset is to big
													// it
													// is better to store the
													// information every 100000
													// triples processed,
													// sometimes
													// the service is shotdown
													// and
													// then the info is lost
						}
					}
					streamManager.setCounter(streamManager.getCounter() - 1);// Announced
																				// that
																				// it
																				// consumer
																				// the
																				// resource
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		this.writeFile(true);
		System.out.println("The number of quads read is: " + cont);
	}

	/**
	 * 
	 */
	public void writeFile(boolean sendMessage) {
		Consumer.setResults(new ArrayList<DataSetResults>());
		DataSetResults result = new DataSetResults(
				this.producer.getServiceUrl(), digMetric, autMetric,
				freeMetric, measurAbility);
		getResults().add(result);

		Metrics metric1 = new Metrics();
		metric1.setName("Authenticity of the Dataset");
		metric1.setValue(Double.toString(result.getAutMetric().metricValue()));

		Metrics metric2 = new Metrics();
		metric2.setName("Digital Signatures");
		metric2.setValue(Double.toString(result.getDigMetric().metricValue()));

		Dimension dimension1 = new Dimension();
		dimension1.setName("Verifiability");
		dimension1.getMetrics().add(metric1);
		dimension1.getMetrics().add(metric2);

		Metrics metric3 = new Metrics();
		metric3.setName("Free of Error");
		metric3.setValue(Double.toString(result.getFreeMetric().metricValue()));

		Metrics metric4 = new Metrics();
		metric4.setName("Measurability");
		metric4.setValue(Double
				.toString(result.getMeasurMetric().metricValue()));

		Dimension dimension2 = new Dimension();
		dimension2.setName("Free of Error");
		dimension2.getMetrics().add(metric3);

		Dimension dimension3 = new Dimension();
		dimension3.setName("Measurability");
		dimension3.getMetrics().add(metric4);

		Results results = new Results();
		results.setUrl(this.producer.getServiceUrl());
		results.getDimensions().add(dimension1);
		results.getDimensions().add(dimension2);
		results.getDimensions().add(dimension3);

		try {
			ConfigurationLoader conf = new ConfigurationLoader();
			ResultDataSet resultToWrite = ResultsHelper.read(conf
					.loadDataBase());

			resultToWrite.setLastDate(new Date());
			boolean modified = false;

			List<Results> aux = new ArrayList<Results>();
			for (Results resultAux : resultToWrite.getResults()) {
				if (resultAux.getUrl().equals(this.producer.getServiceUrl())) {
					resultAux = results;
					modified = true;
				} else {
					aux.add(resultAux);
				}

			}

			if (!modified) {
				resultToWrite.getResults().add(results);
			} else {
				aux.add(results);
				resultToWrite.setResults(aux);
			}

			ResultsHelper.write(resultToWrite, conf.loadDataBase());
			if (sendMessage) {
				String text = "The process is already finish, you can check now in the web site of the QUENTLOD lab";
				if (this.getMail() != null)
					UtilMail.sendMail(this.getMail(), "Proccess Finish", text);
				else
					UtilMail.sendMail(conf.loadMailDefault(),
							"Proccess Finish", text);
			}

		} catch (Exception e) {
			System.out.println("****** Can't save the result because: "
					+ e.toString());
		}
	}

	/**
	 * @return the running
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * @param running
	 *            the running to set
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail
	 *            the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return the results
	 */
	public static List<DataSetResults> getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public static void setResults(List<DataSetResults> results) {
		Consumer.results = results;
	}

	/**
	 * @return the cont
	 */
	public int getCont() {
		return cont;
	}

	/**
	 * @param cont
	 *            the cont to set
	 */
	public void setCont(int cont) {
		this.cont = cont;
	}
}
