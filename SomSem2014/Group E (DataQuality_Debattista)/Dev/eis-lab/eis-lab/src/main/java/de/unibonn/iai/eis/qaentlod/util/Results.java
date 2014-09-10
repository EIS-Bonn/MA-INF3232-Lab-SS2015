/**
 * 
 */
package de.unibonn.iai.eis.qaentlod.util;

import java.util.ArrayList;
import java.util.List;

import de.unibonn.iai.eis.qaentlod.io.utilities.DataSetResults;

public class Results {
	private String url;
	private List<Dimension> dimensions;

	public Results(){
		this.dimensions = new ArrayList<Dimension>();
	}
	
	public void setUrl(String s) {
		url = s;
	}

	public String getUrl() {
		return url;
	}

	/**
	 * @return the dimensions
	 */
	public List<Dimension> getDimensions() {
		return dimensions;
	}

	/**
	 * @param dimensions the dimensions to set
	 */
	public void setDimensions(List<Dimension> dimensions) {
		this.dimensions = dimensions;
	}
}