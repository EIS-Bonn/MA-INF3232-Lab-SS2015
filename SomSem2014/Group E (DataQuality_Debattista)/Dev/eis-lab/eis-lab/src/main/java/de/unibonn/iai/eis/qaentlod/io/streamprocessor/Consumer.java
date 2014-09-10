package de.unibonn.iai.eis.qaentlod.io.streamprocessor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hp.hpl.jena.sparql.core.Quad;

import de.unibonn.iai.eis.qaentlod.datatypes.Object2Quad;
import de.unibonn.iai.eis.qaentlod.io.utilities.DataSetResults;
import de.unibonn.iai.eis.qaentlod.util.Dimension;
import de.unibonn.iai.eis.qaentlod.util.Metrics;
import de.unibonn.iai.eis.qaentlod.util.ResultDataSet;
import de.unibonn.iai.eis.qaentlod.util.Results;
import de.unibonn.iai.eis.qaentlod.util.ResultsHelper;

/**
 * This class read all the values produce by the producer an execute the metrics over the quad obtained
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
	private static String fileName = "C:\\Lab\\results.xml";
	private static List<DataSetResults> results;
	
	/**
	 * Creator of the class
	 * @param streamManager, this class is the stream Manager to put all the values obtain
	 * @param producer, producer of the events to be read
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
		int contAux=0;
		this.setRunning(true);
		//Run the consumer while the producer is publishing data
		while(producer.isRunning()){
			value = new Object2Quad(streamManager.get()).getStatement();
			//Here we compute all the metrics
			this.streamManager.digMetric.compute(value);
			this.streamManager.autMetric.compute(value);
			//this.streamManager.freeMetric.compute(value);
			cont++;
			contAux++;
			if(contAux == 10000){
				contAux = 0;
				System.out.println("Read 10000 triples");
			}
		}
		this.writeFile();
		//System.out.println("The number of quads read is: " + cont);
		//this.stop();
		this.setRunning(false);
		//this.writeFile();
	}


	
	public void writeFile(){
		this.results = new ArrayList<DataSetResults>();
		DataSetResults result = new DataSetResults(this.producer.getServiceUrl(), streamManager.digMetric,
				streamManager.autMetric, streamManager.freeMetric);
		results.add(result);

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

		Dimension dimension2 = new Dimension();
		dimension2.setName("Free of Error");
		dimension2.getMetrics().add(metric3);

		Results results = new Results();
		results.setUrl(this.producer.getServiceUrl());
		results.getDimensions().add(dimension1);
		results.getDimensions().add(dimension2);
		
		try {
			ResultDataSet resultToWrite = ResultsHelper.read(fileName);
					
			resultToWrite.setLastDate(new Date());
			boolean modified = false;
			for (Results resultAux : resultToWrite.getResults()) {
				if(resultAux.getUrl().equals(this.producer.getServiceUrl())){
					resultAux = results;
					modified = true;
				}
			}
			
			if(!modified)
				resultToWrite.getResults().add(results);
			
			ResultsHelper.write(resultToWrite, fileName);

			
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
	 * @param running the running to set
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
}

