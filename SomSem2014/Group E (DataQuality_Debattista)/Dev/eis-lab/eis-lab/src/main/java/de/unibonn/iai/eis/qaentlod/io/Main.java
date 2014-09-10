/**
 * 
 */
package de.unibonn.iai.eis.qaentlod.io;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.unibonn.iai.eis.qaentlod.io.streamprocessor.Consumer;
import de.unibonn.iai.eis.qaentlod.io.streamprocessor.Producer;
import de.unibonn.iai.eis.qaentlod.io.streamprocessor.StreamManager;
import de.unibonn.iai.eis.qaentlod.io.utilities.DataSetResults;
import de.unibonn.iai.eis.qaentlod.io.utilities.Menus;
import de.unibonn.iai.eis.qaentlod.util.Dimension;
import de.unibonn.iai.eis.qaentlod.util.Metrics;
import de.unibonn.iai.eis.qaentlod.util.ResultDataSet;
import de.unibonn.iai.eis.qaentlod.util.Results;
import de.unibonn.iai.eis.qaentlod.util.ResultsHelper;

/**
 * This class is the main class, it is in charge to load the data and create the
 * processes and clients to run faster.
 * 
 * @author Carlos Montoya
 */
public class Main {

	/**
	 * Variable to adjust the increment value of the call to the service
	 */
	private static int INCREMENT = 10000;

	private static String serviceUrl = "http://protein.bio2rdf.org/sparql";

	private static String fileName = "C:\\Lab\\results.xml";
	private static List<DataSetResults> results;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//int opt1 = Menus.menuMain();

		//String url = serviceUrl;
		
		String url = Menus.menuUrl();
		serviceUrl = url;

		StreamManager streamQuads = new StreamManager();
		Producer p1 = new Producer(streamQuads, INCREMENT, serviceUrl);
		Consumer c1 = new Consumer(streamQuads, p1);
		p1.start();
		c1.start();
		
		/*while( c1.isRunning() ){
			System.out.print("");
		}*/
		
		System.out.println("The value of the metrics ");

		//writeFile(streamQuads);
	}
	

	
	public static void writeFile(StreamManager streamQuads){
		System.out.println("entro");
		results = new ArrayList<DataSetResults>();
		DataSetResults result = new DataSetResults(serviceUrl, streamQuads.digMetric,
				streamQuads.autMetric, streamQuads.freeMetric);
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
		results.setUrl(serviceUrl);
		results.getDimensions().add(dimension1);
		results.getDimensions().add(dimension2);
		
		try {
			ResultDataSet resultToWrite = ResultsHelper.read(fileName);
					
			resultToWrite.setLastDate(new Date());
			resultToWrite.getResults().add(results);
			
			ResultsHelper.write(resultToWrite, fileName);

			
		} catch (Exception e) {
			System.out.println("****** Can't save the result because: "
					+ e.toString());
		}
	}
}
