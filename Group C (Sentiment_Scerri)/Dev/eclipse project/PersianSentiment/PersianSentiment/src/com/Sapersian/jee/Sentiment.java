package com.Sapersian.jee;

import java.io.File;
import java.math.*;
import java.util.LinkedHashMap;
import java.lang.Object;
import org.json.simple.*;


import gate.Document;
import gate.Corpus;
import gate.CorpusController;
import gate.FeatureMap;
import gate.Gate;
import gate.Factory;
import gate.util.persistence.PersistenceManager;


public class Sentiment {
	
	public void oncreate (){
		
		if (Gate.getPluginsHome() != null)
		{
			System.out.println(Gate.getPluginsHome());
		}
		else{
			Gate.setPluginsHome(new File("C:/Program Files/GATE_Developer_8.0/plugins"));		
		}
	}
	
	
	public JSONObject persian_sentiment(String text) throws Exception {
		
		
		oncreate();
	     
		    
		    File PersianGapp = new File( "C:/Users/mohammad/Desktop/New folder/Gate/application.xgapp");
	    // initialise GATE - this must be done before calling any GATE APIs
	    Gate.init();

	    // load the saved application
	    
	    CorpusController application =
	      (CorpusController)PersistenceManager.loadObjectFromFile(PersianGapp);

	    // Create a Corpus to use.  We recycle the same Corpus object for each
	    // iteration.  The string parameter to newCorpus() is simply the
	    // GATE-internal name to use for the corpus.  It has no particular
	    // significance.
	    Corpus corpus = Factory.newCorpus("BatchProcessApp Corpus");
	    application.setCorpus(corpus);

	    // process the files one by one
	    
	      // load the document (using the specified encoding if one was given)
	      
	      Document doc = Factory.newDocument(text);

	      // put the document in the corpus
	      corpus.add(doc);
	      
	      // run the application
	      application.execute();
	      
	      String featureName = "Doc_sentiment";
	      FeatureMap features = doc.getFeatures();
	      // remove the document from the corpus again
	      corpus.clear();	
	      
	      
	      
	     // doc.getFeatures().
	      // Release the document, as it is no longer needed
	      Factory.deleteResource(doc);

	      LinkedHashMap  originalContent = (LinkedHashMap )  features.get(featureName);
	      
	      String obj =(String) originalContent.get("sentiment");  
	      //BigDecimal pos =(BigDecimal) originalContent.get("positive");    
	      //BigDecimal neg =(BigDecimal) originalContent.get("negative");   
	      //System.out.println(obj);
	      //create Json for response to user
	      JSONObject obj1=new JSONObject();
	      obj1.put("sentiment",obj);
	      /*obj1.put("positive",pos);
	      //obj1.put("negative",neg);
	      System.out.print("----------");
	      System.out.print(obj1);
	      System.out.print("----------");*/
	     //application.cleanup();
		return  obj1;
		
		
	   
	}	

	  /**
	   * The character encoding to use when loading the docments.  If null, the
	   * platform default encoding is used.
	   */
	 // private static String encoding = "UTF-8";

}
