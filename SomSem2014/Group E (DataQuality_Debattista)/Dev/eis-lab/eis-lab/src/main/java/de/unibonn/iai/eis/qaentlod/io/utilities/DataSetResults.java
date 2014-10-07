/**
 * 
 */
package de.unibonn.iai.eis.qaentlod.io.utilities;

import de.unibonn.iai.eis.qaentlod.qualitymetrics.freeoferror.FreeOfError;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.measurability.Measurability;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.trust.verifiability.AuthenticityDataset;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.trust.verifiability.DigitalSignatures;

/**
 * @author Carlos
 *
 */
public class DataSetResults {

	private String url;
	//Verifiability
	private DigitalSignatures digMetric;
	private AuthenticityDataset autMetric;
	//Free of Error
	private FreeOfError freeMetric;
	//Measurability
	private Measurability measurability;
	/**
	 * Creation method
	 * @param url
	 * @param digMetric
	 * @param autMetric
	 * @param freeMetric
	 */
	public DataSetResults(String url, DigitalSignatures digMetric, AuthenticityDataset autMetric, FreeOfError freeMetric, Measurability measurability){
		this.url = url;
		this.digMetric = digMetric;
		this.autMetric = autMetric;
		this.freeMetric = freeMetric;
		this.measurability = measurability;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the digMetric
	 */
	public DigitalSignatures getDigMetric() {
		return digMetric;
	}

	/**
	 * @param digMetric the digMetric to set
	 */
	public void setDigMetric(DigitalSignatures digMetric) {
		this.digMetric = digMetric;
	}

	/**
	 * @return the autMetric
	 */
	public AuthenticityDataset getAutMetric() {
		return autMetric;
	}

	/**
	 * @param autMetric the autMetric to set
	 */
	public void setAutMetric(AuthenticityDataset autMetric) {
		this.autMetric = autMetric;
	}

	/**
	 * @return the freeMetric
	 */
	public FreeOfError getFreeMetric() {
		return freeMetric;
	}

	/**
	 * @param freeMetric the freeMetric to set
	 */
	public void setFreeMetric(FreeOfError freeMetric) {
		this.freeMetric = freeMetric;
	}
	/**
	 * @return the Measurable metric
	 */
	public Measurability getMeasurMetric(){
		return measurability;
	}
	
	/**
	 * @param measurability the measurability to set
	 */
	public void setMeasurability(Measurability measurability) {
		this.measurability = measurability;
	}
}
