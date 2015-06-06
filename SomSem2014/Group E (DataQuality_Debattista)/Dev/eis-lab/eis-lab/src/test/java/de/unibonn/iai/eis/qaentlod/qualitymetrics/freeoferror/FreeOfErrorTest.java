package de.unibonn.iai.eis.qaentlod.qualitymetrics.freeoferror;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.sparql.core.Quad;
import de.unibonn.iai.eis.qaentlod.configuration.DataSetMappingForTestCase;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.freeoferror.FreeOfError;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.trust.verifiability.AuthenticityDatasetTest;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.utilities.TestLoader;

import org.junit.Assert;

public class FreeOfErrorTest extends Assert {
	private static Logger logger = LoggerFactory
			.getLogger(AuthenticityDatasetTest.class);

	protected TestLoader loader = new TestLoader();

	protected FreeOfError metric = new FreeOfError();

	@Before
	public void setUp() throws Exception {
		loader.loadDataSet(DataSetMappingForTestCase.MalformedDatatypeLiterals);
	}

	@After
	public void tearDown() throws Exception {
		// No clean-up required
	}

	@Test
	public void testMachineReadableLicense() {
		// Load quads for test case
		List<Quad> streamingQuads = loader.getStreamingQuads();
		int countLoadedQuads = 0;

		for (Quad quad : streamingQuads) {
			// Here we start streaming triples to the quality metric
			metric.compute(quad);
			countLoadedQuads++;
		}

		double metricValue = metric.metricValue() * 1.0;

		logger.trace(
				"Number of quads loaded, {} quads; And number of quads that haven't got error, {} quads ",
				countLoadedQuads, metricValue);

		/*
		 * Quality indication for given data set is ratio of free of error of
		 * data set and number of quads in it.
		 */
		logger.trace(
				"Average of free of error of given data set is, {} percent",
				metricValue / countLoadedQuads);

	}

}
