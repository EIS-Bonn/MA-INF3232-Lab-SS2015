package de.unibonn.iai.eis.qaentlod.io.streamprocessor;

import com.hp.hpl.jena.query.QuerySolution;

import de.unibonn.iai.eis.qaentlod.qualitymetrics.freeoferror.FreeOfError;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.trust.verifiability.AuthenticityDataset;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.trust.verifiability.DigitalSignatures;

/**
 * This class is the one that manage all the Quad and compute all the streaming data
 * @author Carlos Montoya
 */
public class StreamManager {
	private boolean available = false; //This value is use as trafic Light
	public QuerySolution object; //Object to be pass between elements
	public DigitalSignatures digMetric = new DigitalSignatures(); //Metrics to be apply
	public AuthenticityDataset autMetric = new AuthenticityDataset(); //Metrics to be apply
	public FreeOfError freeMetric = new FreeOfError();
	
	/**
	 * This class obtain the values published by the producer
	 * @return the value published
	 */
	public synchronized QuerySolution get() {
		while (available == false) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		available = false;
		notifyAll();
		return object;
	}

	/**
	 * Method that is use to publish the information
	 * @param value
	 */
	public synchronized void put(QuerySolution value) {
		while (available == true) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		object = value;
		available = true;
		notifyAll();
	}
}
