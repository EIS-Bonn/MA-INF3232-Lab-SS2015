package de.unibonn.iai.eis.qaentlod.qualitymetrics.freeoferror;

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
 * @author Rauf Agayev, Vugar Agayev Verifies whether given data set has got
 *         syntax error respect to subject, predicate and object. Metrics checks
 *         if subject, predicate and object is syntactically correct by
 *         definition of these terms.
 */

public class FreeOfError extends AbstractQualityMetric {

	private final Resource METRIC_URI = DQM.Accessibility;

	// counter will increase when free of error quad is checked
	protected int counter = 0;

	/**
	 * Data set consists of number of quad triples. In each step quad comes to
	 * compute method and divided subject, predicate and object part then free
	 * of error metric applied by checking syntax error of each part.
	 */

	@Override
	public void compute(Quad quad) {
		boolean flag = true;

		Node subject = quad.getSubject();
		Node predicate = quad.getPredicate();
		Node object = quad.getObject();

		if (subject.isURI()) {
			flag = isValidURI(subject);
		} else if (subject.isBlank()) {
		} else {
			flag = false;
		}

		if (predicate.isURI()) {
			flag = isValidURI(predicate);
		} else {
			flag = false;
		}

		if (object.isURI()) {
			flag = isValidURI(object);
		} else if (object.isBlank()) {
		} else if (object.isLiteral()) {
			RDFDatatype dataType = object.getLiteralDatatype();
			flag = (dataType != null) ? dataType.isValidLiteral(object
					.getLiteral()) : false;
		} else {
			flag = false;
		}
		if (flag) {
			counter++;
		}

	}

	@Override
	public double metricValue() {
		// return number of free of error quads
		return counter;
	}

	@Override
	public Resource getMetricURI() {
		return METRIC_URI;
	}

	@Override
	public ProblemList<?> getQualityProblems() {
		return null;
	}

	public boolean isValidURI(Node node) {
		try {
			URL url = new URL(node.getURI());
			URLConnection conn = url.openConnection();
			conn.connect();
		} catch (MalformedURLException e) {
			// the URL is not in a valid form
			return false;
		} catch (IOException e) {
			// the connection couldn't be established
			return false;
		}
		return true;
	}

}
