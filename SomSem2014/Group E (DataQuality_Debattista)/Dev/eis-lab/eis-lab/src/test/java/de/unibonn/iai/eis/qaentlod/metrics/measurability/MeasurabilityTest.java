package de.unibonn.iai.eis.qaentlod.metrics.measurability;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.sparql.core.Quad;
import de.unibonn.iai.eis.qaentlod.configuration.DataSetMappingForTestCase;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.measurability.Measurability;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.trust.verifiability.AuthenticityDatasetTest;
import de.unibonn.iai.eis.qaentlod.qualitymetrics.utilities.TestLoader;

import org.junit.Assert;

public class MeasurabilityTest extends Assert {
	private static Logger logger = LoggerFactory
			.getLogger(AuthenticityDatasetTest.class);

	protected TestLoader loader = new TestLoader();
	protected Measurability metric = new Measurability();
	

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

		for (Quad quad : streamingQuads) {
			// Here we start streaming triples to the quality metric
			metric.compute(quad);	
		}
		
		double metricValue = metric.metricValue() * 1.0;		
		String s= ((int)metricValue==1)?"Data is measurable, because metric value is 1.0":"Data is not measurable because metric vlaue is 0";
		logger.trace(s);		
	}

}
