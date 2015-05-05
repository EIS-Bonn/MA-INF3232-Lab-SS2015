package de.unibonn.iai.eis.qaentlod.io;

import de.unibonn.iai.eis.qaentlod.exceptions.ProcessorNotInitialised;

public interface IOProcessor {

	public void setUpProcess();
	
	public void startProcessing() throws ProcessorNotInitialised;
	
	public void cleanUp() throws ProcessorNotInitialised;
}
