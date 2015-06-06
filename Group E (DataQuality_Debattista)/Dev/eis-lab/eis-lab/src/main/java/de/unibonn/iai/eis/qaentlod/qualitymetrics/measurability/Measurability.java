package de.unibonn.iai.eis.qaentlod.qualitymetrics.measurability;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.core.Quad;

import de.unibonn.iai.eis.qaentlod.datatypes.ProblemList;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.AbstractQualityMetric;
import de.unibonn.iai.eis.qaentlod.vocabularies.DQM;

/**
 * @author Rauf Agayev, Vugar Agayev verifies if given data is measurable or not. 
 * For this purpose it checks given data set size in terms of triples (it must higher than
 * some threshold) and checks if it has got access to sparql endpoint. 
 */

public class Measurability extends AbstractQualityMetric {

	private final Resource METRIC_URI = DQM.Accessibility;

	// counter will increase when free of error quad is checked
	protected int counter = 0,threshold=50;
	public boolean sparqlEndPointWorks=false;
	
	/**
	 * for being measurable data set must contain number of triples
	 * that bigger or equal to some threshold and program must have
	 * access to sparql endpoint	 
	 */

	@Override
	public void compute(Quad quad) {	
			counter++;
			sparqlEndPointWorks=true;
	}

	@Override
	public double metricValue() {
		// it must have access to sparql endpoint and counter must be greater than some thresold
		return ((counter>=threshold)&sparqlEndPointWorks)?1:0;
	}

	@Override
	public Resource getMetricURI() {
		return METRIC_URI;
	}

	@Override
	public ProblemList<?> getQualityProblems() {
		return null;
	}

}
