package application.test;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import application.model.dao.tdb.MobivocDAO;
import application.model.model.FillingStation;

import com.google.gson.Gson;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class MobivocTest {

	final static Logger logger = Logger.getLogger(MobivocDAO.class);
	private static Model model;
	private List<FillingStation> fillingStation = new LinkedList<FillingStation>();
	 
	public MobivocTest(){
		FileManager.get().addLocatorClassLoader(MobivocDAO.class.getClassLoader());
        this.model = FileManager.get().loadModel("FillingStationInstances.ttl", null, "TURTLE");
	
	}
	
	public static String testSparql(){
		
		String queryString = 
                "PREFIX mv: <http://eccenca.com/mobivoc/> " +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
                "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
                
                "SELECT ?fuelstation " + 
                "WHERE { { ?fuelstation a mv:FuelStation. } }" ;
		
		return null;
	}
	
	
	public static String getAllFs(){
		
		JSONObject oJsonInner = new JSONObject();
		JSONObject feature = new JSONObject();
		JSONObject fillingStationJSON = new JSONObject();
		JSONObject resultJSON = new JSONObject();
		
		ArrayList<JSONObject> fillingStationFeauture = new ArrayList<JSONObject>();
		ArrayList<JSONObject> fillingStationList = new ArrayList<JSONObject>();
		ArrayList<JSONObject> resultList = new ArrayList<JSONObject>();

		String queryString = 
                "PREFIX mv: <http://eccenca.com/mobivoc/> " +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
                "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
                
                "SELECT ?fuelstation " + 
                "WHERE { { ?fuelstation a mv:FuelStation. } }" ;
                					
                
		Query queryFillingStation = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(queryFillingStation, model);
        
        try {
            ResultSet results = qexec.execSelect();
            while ( results.hasNext() ) {
            	fillingStationFeauture = new ArrayList<JSONObject>();
            	fillingStationJSON = new JSONObject();
                QuerySolution soln = results.next();
                Resource fuelStationObject = soln.getResource("fuelstation");
                
                String queryStation =
    					"SELECT ?property ?hasValue " +
    					  "WHERE { " + 
    					  			"{ <" + fuelStationObject + "> ?property ?hasValue } " +		
    					  "}" ;
    
                Query query2 = QueryFactory.create(queryStation);
                QueryExecution qexec2 = QueryExecutionFactory.create(query2, model);
               
                try{                	
                	ResultSet results2 = qexec2.execSelect();
                	while ( results2.hasNext() ) {
                		oJsonInner = new JSONObject();
                		feature = new JSONObject();
                		
                		QuerySolution soln2 = results2.next();
                		Resource propertyResource = soln2.getResource("property");
                		                		
                		oJsonInner.put("Property", propertyResource);
            			                		
                		if(soln2.get("hasValue").isLiteral()){
                			Literal literal = soln2.getLiteral("hasValue");
                			oJsonInner.put("Literal", literal);
                		}else if(soln2.get("hasValue").isURIResource()){
                			Resource resource = soln2.getResource("hasValue");
                			oJsonInner.put("Resource", resource);
                		}               		
                		feature.put("Feature", oJsonInner);
                		fillingStationFeauture.add(feature);
                	}	               	
                	fillingStationJSON.put("FillingStation", fillingStationFeauture);
                	fillingStationList.add(fillingStationJSON);               	
	            }
                catch(Exception e){
                	
                }     
            }
            
        } finally {
            qexec.close();
        }
        resultJSON.put("FillingStationList", fillingStationList);
        resultList.add(resultJSON);
        
		return resultJSON.toString();
	}


	public String getFsLocation(String lat, String longi){
		return null;
	}
	public String getFsParkWc(String parkingChoice, String wcChoice){
		Model local = ModelFactory.createDefaultModel();		
		Model model = FileManager.get().readModel(local, "/Users/umut/Documents/workspace/eclipse_git_enterprise/Mobivoc/src/main/resources/mymodel3_1.ttl");
		
		JSONObject oJsonInner_label = new JSONObject();
		JSONObject oJsonInner_lat = new JSONObject();
		JSONObject oJsonInner_long = new JSONObject();
		
		JSONObject feature_label = new JSONObject();
		JSONObject feature_lat = new JSONObject();
		JSONObject feature_long = new JSONObject();

		
		JSONObject fillingStationJSON = new JSONObject();
		JSONObject resultJSON = new JSONObject();
		
		ArrayList<JSONObject> fillingStationFeauture = new ArrayList<JSONObject>();
		ArrayList<JSONObject> fillingStationList = new ArrayList<JSONObject>();
		ArrayList<JSONObject> resultList = new ArrayList<JSONObject>();
	
		String queryString = 
	            "PREFIX mv: <http://eccenca.com/mobivoc/> " +
	            "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
	            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
	            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
	            "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
	            "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
	            "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
	            
	            "SELECT ?fuelstation " + 
	            "WHERE { { ?fuelstation a mv:FuelStation. } }" ;
	            					
	            
		Query queryFillingStation = QueryFactory.create(queryString);
	    QueryExecution qexec = QueryExecutionFactory.create(queryFillingStation, model);
	    
	    ParameterizedSparqlString pss = new ParameterizedSparqlString();
	    
	    try {
	        ResultSet results = qexec.execSelect();
	        while ( results.hasNext() ) {
	        	fillingStationFeauture = new ArrayList<JSONObject>();
	        	fillingStationJSON = new JSONObject();
	            QuerySolution soln = results.next();
	            Resource fuelStationObject = soln.getResource("fuelstation");
	            
	            ParameterizedSparqlString queryStation = new ParameterizedSparqlString();
	            queryStation.setCommandText("SELECT * " +
						  "WHERE { " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2000/01/rdf-schema#label> ?hasValue } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/fillingStationNumber> ?hasID } " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?hasLat } " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?hasLong } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/hasParkingFacility> ?parkingChoice } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/hasWCFacility> ?wcChoice } " +	
						  "}" );
	            
	           
	            System.out.println("parkingChoice: " + parkingChoice);
	            System.out.println("wcChoice: " + wcChoice);
	           
	            
	            int type = 0;
	            
	            if(!parkingChoice.equals("E")){
	            	type = 1;
	            	System.out.println("Enter parkingChoice" + parkingChoice);
	            	queryStation.setLiteral("parkingChoice", parkingChoice);
	            }
	           
	            if(!wcChoice.equals("E")){
	            	type = 1;
	            	System.out.println("Enter wcChoice" + wcChoice);
	            	queryStation.setLiteral("wcChoice", wcChoice);
	            }
	            
	            
	            Query query = queryStation.asQuery();
	            
	            Query query2 = QueryFactory.create(query);
	            QueryExecution qexec2 = QueryExecutionFactory.create(query2, model);
	            
	            int counter = 0;
	           
	            try{                	
	            	ResultSet results2 = qexec2.execSelect();
	            	while ( results2.hasNext() ) {
	            		
	            		oJsonInner_label = new JSONObject();
	            		oJsonInner_lat = new JSONObject();
	            		oJsonInner_long = new JSONObject();
	            		feature_label = new JSONObject();
	            		feature_lat = new JSONObject();
	            		feature_long = new JSONObject();
	            		
	            		QuerySolution soln2 = results2.next();
	            		//Resource propertyResource = soln2.getResource("property");
	            		if(type == 2){
	            			if(soln2.get("hasValue").isLiteral()){
		            			Literal literal = soln2.getLiteral("hasValue");
		            			oJsonInner_label.put("Property", "http://www.w3.org/2000/01/rdf-schema#label");
			            		oJsonInner_label.put("Literal", literal);
			            		feature_label.put("Feature", oJsonInner_label);	
			            		fillingStationFeauture.add(feature_label);
		            		}
		            		if(soln2.get("hasID").isLiteral()){
		            			oJsonInner_lat.put("Property", "http://eccenca.com/mobivoc/fillingStationNumber");
			            		Literal literal_lat = soln2.getLiteral("hasID");
			            		oJsonInner_lat.put("Literal", literal_lat);
			        			feature_lat.put("Feature", oJsonInner_lat);
			            		fillingStationFeauture.add(feature_lat);
		            		}
		            		System.out.println("Counter: " + counter++);
	            		}
	            		if(type == 1 && counter == 0){
	            			if(soln2.get("hasValue").isLiteral()){
		            			Literal literal = soln2.getLiteral("hasValue");
		            			oJsonInner_label.put("Property", "http://www.w3.org/2000/01/rdf-schema#label");
			            		oJsonInner_label.put("Literal", literal);
			            		feature_label.put("Feature", oJsonInner_label);	
			            		fillingStationFeauture.add(feature_label);
		            		}
		            		if(soln2.get("hasID").isLiteral()){
		            			oJsonInner_lat.put("Property", "http://eccenca.com/mobivoc/fillingStationNumber");
			            		Literal literal_lat = soln2.getLiteral("hasID");
			            		oJsonInner_lat.put("Literal", literal_lat);
			        			feature_lat.put("Feature", oJsonInner_lat);
			            		fillingStationFeauture.add(feature_lat);
		            		}
		            		System.out.println("Counter: " + counter++);
	            		}
	            		
	            	}	               	
	            	fillingStationJSON.put("FillingStation", fillingStationFeauture);
	            	fillingStationList.add(fillingStationJSON);               	
	            }
	            catch(Exception e){
	            	
	            }     
	        }
	    } finally {
	        qexec.close();
	    }
	    resultJSON.put("FillingStationList", fillingStationList);
	    resultList.add(resultJSON);
	    
	    System.out.println(resultJSON.toString());
	   
	    
		return resultJSON.toString();
	
	}
	public String getFsWashingWc(String washingChoice, String wcChoice){
		Model local = ModelFactory.createDefaultModel();		
		Model model = FileManager.get().readModel(local, "/Users/umut/Documents/workspace/eclipse_git_enterprise/Mobivoc/src/main/resources/mymodel3_1.ttl");
		
		
		JSONObject oJsonInner_label = new JSONObject();
		JSONObject oJsonInner_lat = new JSONObject();
		JSONObject oJsonInner_long = new JSONObject();
		
		JSONObject feature_label = new JSONObject();
		JSONObject feature_lat = new JSONObject();
		JSONObject feature_long = new JSONObject();

		
		JSONObject fillingStationJSON = new JSONObject();
		JSONObject resultJSON = new JSONObject();
		
		ArrayList<JSONObject> fillingStationFeauture = new ArrayList<JSONObject>();
		ArrayList<JSONObject> fillingStationList = new ArrayList<JSONObject>();
		ArrayList<JSONObject> resultList = new ArrayList<JSONObject>();
	
		String queryString = 
	            "PREFIX mv: <http://eccenca.com/mobivoc/> " +
	            "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
	            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
	            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
	            "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
	            "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
	            "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
	            
	            "SELECT ?fuelstation " + 
	            "WHERE { { ?fuelstation a mv:FuelStation. } }" ;
	            					
	            
		Query queryFillingStation = QueryFactory.create(queryString);
	    QueryExecution qexec = QueryExecutionFactory.create(queryFillingStation, model);
	    
	    ParameterizedSparqlString pss = new ParameterizedSparqlString();
	    
	    try {
	        ResultSet results = qexec.execSelect();
	        while ( results.hasNext() ) {
	        	fillingStationFeauture = new ArrayList<JSONObject>();
	        	fillingStationJSON = new JSONObject();
	            QuerySolution soln = results.next();
	            Resource fuelStationObject = soln.getResource("fuelstation");
	            
	            ParameterizedSparqlString queryStation = new ParameterizedSparqlString();
	            queryStation.setCommandText("SELECT * " +
						  "WHERE { " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2000/01/rdf-schema#label> ?hasValue } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/fillingStationNumber> ?hasID } " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?hasLat } " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?hasLong } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/hasWashingFacility> ?washingChoice } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/hasWCFacility> ?wcChoice } " +	
						  "}" );
	            
	           
	            System.out.println("washingChoice: " + washingChoice);
	            System.out.println("wcChoice: " + wcChoice);
	           
	            
	            int type = 0;
	            
	            
	            if(!washingChoice.equals("E")){
	            	type = 1;
	            	System.out.println("Enter parkingChoice" + washingChoice);
	            	queryStation.setLiteral("parkingChoice", washingChoice);
	            }
	           
	            if(!wcChoice.equals("E")){
	            	type = 1;
	            	System.out.println("Enter wcChoice" + wcChoice);
	            	queryStation.setLiteral("wcChoice", wcChoice);
	            }
	            
	            
	            Query query = queryStation.asQuery();
	            
	            Query query2 = QueryFactory.create(query);
	            QueryExecution qexec2 = QueryExecutionFactory.create(query2, model);
	            
	            int counter = 0;
	           
	            try{                	
	            	ResultSet results2 = qexec2.execSelect();
	            	while ( results2.hasNext() ) {
	            		
	            		oJsonInner_label = new JSONObject();
	            		oJsonInner_lat = new JSONObject();
	            		oJsonInner_long = new JSONObject();
	            		feature_label = new JSONObject();
	            		feature_lat = new JSONObject();
	            		feature_long = new JSONObject();
	            		
	            		QuerySolution soln2 = results2.next();
	            		//Resource propertyResource = soln2.getResource("property");
	            		if(type == 2){
	            			if(soln2.get("hasValue").isLiteral()){
		            			Literal literal = soln2.getLiteral("hasValue");
		            			oJsonInner_label.put("Property", "http://www.w3.org/2000/01/rdf-schema#label");
			            		oJsonInner_label.put("Literal", literal);
			            		feature_label.put("Feature", oJsonInner_label);	
			            		fillingStationFeauture.add(feature_label);
		            		}
		            		if(soln2.get("hasID").isLiteral()){
		            			oJsonInner_lat.put("Property", "http://eccenca.com/mobivoc/fillingStationNumber");
			            		Literal literal_lat = soln2.getLiteral("hasID");
			            		oJsonInner_lat.put("Literal", literal_lat);
			        			feature_lat.put("Feature", oJsonInner_lat);
			            		fillingStationFeauture.add(feature_lat);
		            		}
		            		System.out.println("Counter: " + counter++);
	            		}
	            		if(type == 1 && counter == 0){
	            			if(soln2.get("hasValue").isLiteral()){
		            			Literal literal = soln2.getLiteral("hasValue");
		            			oJsonInner_label.put("Property", "http://www.w3.org/2000/01/rdf-schema#label");
			            		oJsonInner_label.put("Literal", literal);
			            		feature_label.put("Feature", oJsonInner_label);	
			            		fillingStationFeauture.add(feature_label);
		            		}
		            		if(soln2.get("hasID").isLiteral()){
		            			oJsonInner_lat.put("Property", "http://eccenca.com/mobivoc/fillingStationNumber");
			            		Literal literal_lat = soln2.getLiteral("hasID");
			            		oJsonInner_lat.put("Literal", literal_lat);
			        			feature_lat.put("Feature", oJsonInner_lat);
			            		fillingStationFeauture.add(feature_lat);
		            		}
		            		System.out.println("Counter: " + counter++);
	            		}
	            		
	            	}	               	
	            	fillingStationJSON.put("FillingStation", fillingStationFeauture);
	            	fillingStationList.add(fillingStationJSON);               	
	            }
	            catch(Exception e){
	            	
	            }     
	        }
	    } finally {
	        qexec.close();
	    }
	    resultJSON.put("FillingStationList", fillingStationList);
	    resultList.add(resultJSON);
	    
	    System.out.println(resultJSON.toString());
	   
	    
		return resultJSON.toString();
	
	}
	
	public String getFsWc(String wcChoice){
		Model local = ModelFactory.createDefaultModel();		
		Model model = FileManager.get().readModel(local, "/Users/umut/Documents/workspace/eclipse_git_enterprise/Mobivoc/src/main/resources/mymodel3_1.ttl");
		
		
		JSONObject oJsonInner_label = new JSONObject();
		JSONObject oJsonInner_lat = new JSONObject();
		JSONObject oJsonInner_long = new JSONObject();
		
		JSONObject feature_label = new JSONObject();
		JSONObject feature_lat = new JSONObject();
		JSONObject feature_long = new JSONObject();

		
		JSONObject fillingStationJSON = new JSONObject();
		JSONObject resultJSON = new JSONObject();
		
		ArrayList<JSONObject> fillingStationFeauture = new ArrayList<JSONObject>();
		ArrayList<JSONObject> fillingStationList = new ArrayList<JSONObject>();
		ArrayList<JSONObject> resultList = new ArrayList<JSONObject>();
	
		String queryString = 
	            "PREFIX mv: <http://eccenca.com/mobivoc/> " +
	            "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
	            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
	            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
	            "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
	            "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
	            "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
	            
	            "SELECT ?fuelstation " + 
	            "WHERE { { ?fuelstation a mv:FuelStation. } }" ;
	            					
	            
		Query queryFillingStation = QueryFactory.create(queryString);
	    QueryExecution qexec = QueryExecutionFactory.create(queryFillingStation, model);
	    
	    ParameterizedSparqlString pss = new ParameterizedSparqlString();
	    
	    try {
	        ResultSet results = qexec.execSelect();
	        while ( results.hasNext() ) {
	        	fillingStationFeauture = new ArrayList<JSONObject>();
	        	fillingStationJSON = new JSONObject();
	            QuerySolution soln = results.next();
	            Resource fuelStationObject = soln.getResource("fuelstation");
	  
	            
	            ParameterizedSparqlString queryStation = new ParameterizedSparqlString();
	            queryStation.setCommandText("SELECT * " +
						  "WHERE { " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2000/01/rdf-schema#label> ?hasValue } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/fillingStationNumber> ?hasID } " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?hasLat } " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?hasLong } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/hasWCFacility> ?wcChoice } " +	
						  "}" );
	     
	            System.out.println("wcChoice: " + wcChoice);
	           
	            
	            int type = 0;
	            //queryStation.setIri("fuelChoice", "http://eccenca.com/mobivoc/Biodiesel");
	           
	            if(!wcChoice.equals("E")){
	            	type = 1;
	            	System.out.println("Enter wcChoice" + wcChoice);
	            	queryStation.setLiteral("wcChoice", wcChoice);
	            }
	            
	            
	            Query query = queryStation.asQuery();
	            
	            Query query2 = QueryFactory.create(query);
	            QueryExecution qexec2 = QueryExecutionFactory.create(query2, model);
	            
	            int counter = 0;
	           
	            try{                	
	            	ResultSet results2 = qexec2.execSelect();
	            	while ( results2.hasNext() ) {
	            		
	            		oJsonInner_label = new JSONObject();
	            		oJsonInner_lat = new JSONObject();
	            		oJsonInner_long = new JSONObject();
	            		feature_label = new JSONObject();
	            		feature_lat = new JSONObject();
	            		feature_long = new JSONObject();
	            		
	            		QuerySolution soln2 = results2.next();
	            		//Resource propertyResource = soln2.getResource("property");
	            		if(counter == 0){
	            			if(soln2.get("hasValue").isLiteral()){
		            			Literal literal = soln2.getLiteral("hasValue");
		            			oJsonInner_label.put("Property", "http://www.w3.org/2000/01/rdf-schema#label");
			            		oJsonInner_label.put("Literal", literal);
			            		feature_label.put("Feature", oJsonInner_label);	
			            		fillingStationFeauture.add(feature_label);
		            		}
		            		if(soln2.get("hasID").isLiteral()){
		            			oJsonInner_lat.put("Property", "http://eccenca.com/mobivoc/fillingStationNumber");
			            		Literal literal_lat = soln2.getLiteral("hasID");
			            		oJsonInner_lat.put("Literal", literal_lat);
			        			feature_lat.put("Feature", oJsonInner_lat);
			            		fillingStationFeauture.add(feature_lat);
		            		}
		            		System.out.println("Counter: " + counter++);
	            		}
	            		
	            	}	               	
	            	fillingStationJSON.put("FillingStation", fillingStationFeauture);
	            	fillingStationList.add(fillingStationJSON);               	
	            }
	            catch(Exception e){
	            	
	            }     
	        }
	    } finally {
	        qexec.close();
	    }
	    resultJSON.put("FillingStationList", fillingStationList);
	    resultList.add(resultJSON);
	    
	    System.out.println(resultJSON.toString());
	   
	    
		return resultJSON.toString();
	
	}
	
	public String getFsWashingShop(String washingChoice, String shoppingChoice){
		Model local = ModelFactory.createDefaultModel();		
		Model model = FileManager.get().readModel(local, "/Users/umut/Documents/workspace/eclipse_git_enterprise/Mobivoc/src/main/resources/mymodel3_1.ttl");
		
		
		JSONObject oJsonInner_label = new JSONObject();
		JSONObject oJsonInner_lat = new JSONObject();
		JSONObject oJsonInner_long = new JSONObject();
		
		JSONObject feature_label = new JSONObject();
		JSONObject feature_lat = new JSONObject();
		JSONObject feature_long = new JSONObject();

		
		JSONObject fillingStationJSON = new JSONObject();
		JSONObject resultJSON = new JSONObject();
		
		ArrayList<JSONObject> fillingStationFeauture = new ArrayList<JSONObject>();
		ArrayList<JSONObject> fillingStationList = new ArrayList<JSONObject>();
		ArrayList<JSONObject> resultList = new ArrayList<JSONObject>();
	
		String queryString = 
	            "PREFIX mv: <http://eccenca.com/mobivoc/> " +
	            "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
	            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
	            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
	            "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
	            "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
	            "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
	            
	            "SELECT ?fuelstation " + 
	            "WHERE { { ?fuelstation a mv:FuelStation. } }" ;
	            					
	            
		Query queryFillingStation = QueryFactory.create(queryString);
	    QueryExecution qexec = QueryExecutionFactory.create(queryFillingStation, model);
	    
	    ParameterizedSparqlString pss = new ParameterizedSparqlString();
	    
	    try {
	        ResultSet results = qexec.execSelect();
	        while ( results.hasNext() ) {
	        	fillingStationFeauture = new ArrayList<JSONObject>();
	        	fillingStationJSON = new JSONObject();
	            QuerySolution soln = results.next();
	            Resource fuelStationObject = soln.getResource("fuelstation");
	   
	            
	            ParameterizedSparqlString queryStation = new ParameterizedSparqlString();
	            queryStation.setCommandText("SELECT * " +
						  "WHERE { " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2000/01/rdf-schema#label> ?hasValue } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/fillingStationNumber> ?hasID } " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?hasLat } " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?hasLong } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/hasWashingFacility> ?washingChoice } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/hasShoppingFacility> ?shoppingChoice } " +	
						  "}" );
	            
	           
	            System.out.println("washingChoice: " + washingChoice);
	            System.out.println("shoppingChoice: " + shoppingChoice);
	           
	            
	            int type = 0;
	            //queryStation.setIri("fuelChoice", "http://eccenca.com/mobivoc/Biodiesel");
	            
	            
	            if(!washingChoice.equals("E")){
	            	type = 1;
	            	System.out.println("Enter washingChoice" + washingChoice);
	            	queryStation.setLiteral("washingChoice", washingChoice);
	            }
	           
	            if(!shoppingChoice.equals("E")){
	            	type = 1;
	            	System.out.println("Enter wcChoice" + shoppingChoice);
	            	queryStation.setLiteral("wcChoice", shoppingChoice);
	            }
	            
	            
	            Query query = queryStation.asQuery();
	            
	            Query query2 = QueryFactory.create(query);
	            QueryExecution qexec2 = QueryExecutionFactory.create(query2, model);
	            
	            int counter = 0;
	           
	            try{                	
	            	ResultSet results2 = qexec2.execSelect();
	            	while ( results2.hasNext() ) {
	            		
	            		oJsonInner_label = new JSONObject();
	            		oJsonInner_lat = new JSONObject();
	            		oJsonInner_long = new JSONObject();
	            		feature_label = new JSONObject();
	            		feature_lat = new JSONObject();
	            		feature_long = new JSONObject();
	            		
	            		QuerySolution soln2 = results2.next();
	            		//Resource propertyResource = soln2.getResource("property");
	            		if(counter == 0){
	            			if(soln2.get("hasValue").isLiteral()){
		            			Literal literal = soln2.getLiteral("hasValue");
		            			oJsonInner_label.put("Property", "http://www.w3.org/2000/01/rdf-schema#label");
			            		oJsonInner_label.put("Literal", literal);
			            		feature_label.put("Feature", oJsonInner_label);	
			            		fillingStationFeauture.add(feature_label);
		            		}
		            		if(soln2.get("hasID").isLiteral()){
		            			oJsonInner_lat.put("Property", "http://eccenca.com/mobivoc/fillingStationNumber");
			            		Literal literal_lat = soln2.getLiteral("hasID");
			            		oJsonInner_lat.put("Literal", literal_lat);
			        			feature_lat.put("Feature", oJsonInner_lat);
			            		fillingStationFeauture.add(feature_lat);
		            		}
		            		System.out.println("Counter: " + counter++);
	            		}
	            		
	            		
	            	}	               	
	            	fillingStationJSON.put("FillingStation", fillingStationFeauture);
	            	fillingStationList.add(fillingStationJSON);               	
	            }
	            catch(Exception e){
	            	
	            }     
	        }
	    } finally {
	        qexec.close();
	    }
	    resultJSON.put("FillingStationList", fillingStationList);
	    resultList.add(resultJSON);
	    
	    System.out.println(resultJSON.toString());
	   
	    
		return resultJSON.toString();
	
	}

	public String getFsWheelChair(String wheelChairChoice){
		Model local = ModelFactory.createDefaultModel();		
		Model model = FileManager.get().readModel(local, "/Users/umut/Documents/workspace/eclipse_git_enterprise/Mobivoc/src/main/resources/mymodel3_1.ttl");
		
		
		JSONObject oJsonInner_label = new JSONObject();
		JSONObject oJsonInner_lat = new JSONObject();
		JSONObject oJsonInner_long = new JSONObject();
		
		JSONObject feature_label = new JSONObject();
		JSONObject feature_lat = new JSONObject();
		JSONObject feature_long = new JSONObject();

		
		JSONObject fillingStationJSON = new JSONObject();
		JSONObject resultJSON = new JSONObject();
		
		ArrayList<JSONObject> fillingStationFeauture = new ArrayList<JSONObject>();
		ArrayList<JSONObject> fillingStationList = new ArrayList<JSONObject>();
		ArrayList<JSONObject> resultList = new ArrayList<JSONObject>();
	
		String queryString = 
	            "PREFIX mv: <http://eccenca.com/mobivoc/> " +
	            "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
	            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
	            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
	            "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
	            "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
	            "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
	            
	            "SELECT ?fuelstation " + 
	            "WHERE { { ?fuelstation a mv:FuelStation. } }" ;
	            					
	            
		Query queryFillingStation = QueryFactory.create(queryString);
	    QueryExecution qexec = QueryExecutionFactory.create(queryFillingStation, model);
	    
	    ParameterizedSparqlString pss = new ParameterizedSparqlString();
	    
	    try {
	        ResultSet results = qexec.execSelect();
	        while ( results.hasNext() ) {
	        	fillingStationFeauture = new ArrayList<JSONObject>();
	        	fillingStationJSON = new JSONObject();
	            QuerySolution soln = results.next();
	            Resource fuelStationObject = soln.getResource("fuelstation");
	            
	            ParameterizedSparqlString queryStation = new ParameterizedSparqlString();
	            queryStation.setCommandText("SELECT * " +
						  "WHERE { " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2000/01/rdf-schema#label> ?hasValue } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/fillingStationNumber> ?hasID } " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?hasLat } " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?hasLong } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/hasWheelChairEnabled> ?wheelChairChoice } " +	
						  "}" );
	            
	           
	            System.out.println("parkingChoice: " + wheelChairChoice);
	            
	            int type = 0;
	           
	            
	            	System.out.println("Enter parkingChoice" + wheelChairChoice);
	            	queryStation.setLiteral("parkingChoice", wheelChairChoice);
	            
	          
	            Query query = queryStation.asQuery();
	            
	            Query query2 = QueryFactory.create(query);
	            QueryExecution qexec2 = QueryExecutionFactory.create(query2, model);
	            
	            int counter = 0;
	           
	            try{                	
	            	ResultSet results2 = qexec2.execSelect();
	            	while ( results2.hasNext() ) {
	            		
	            		oJsonInner_label = new JSONObject();
	            		oJsonInner_lat = new JSONObject();
	            		oJsonInner_long = new JSONObject();
	            		feature_label = new JSONObject();
	            		feature_lat = new JSONObject();
	            		feature_long = new JSONObject();
	            		
	            		QuerySolution soln2 = results2.next();
	            		//Resource propertyResource = soln2.getResource("property");
	            		if(counter == 0){
	            			if(soln2.get("hasValue").isLiteral()){
		            			Literal literal = soln2.getLiteral("hasValue");
		            			oJsonInner_label.put("Property", "http://www.w3.org/2000/01/rdf-schema#label");
			            		oJsonInner_label.put("Literal", literal);
			            		feature_label.put("Feature", oJsonInner_label);	
			            		fillingStationFeauture.add(feature_label);
		            		}
		            		if(soln2.get("hasID").isLiteral()){
		            			oJsonInner_lat.put("Property", "http://eccenca.com/mobivoc/fillingStationNumber");
			            		Literal literal_lat = soln2.getLiteral("hasID");
			            		oJsonInner_lat.put("Literal", literal_lat);
			        			feature_lat.put("Feature", oJsonInner_lat);
			            		fillingStationFeauture.add(feature_lat);
		            		}
		            		System.out.println("Counter: " + counter++);
	            		}
	            	}	               	
	            	fillingStationJSON.put("FillingStation", fillingStationFeauture);
	            	fillingStationList.add(fillingStationJSON);               	
	            }
	            catch(Exception e){
	            	
	            }     
	        }
	    } finally {
	        qexec.close();
	    }
	    resultJSON.put("FillingStationList", fillingStationList);
	    resultList.add(resultJSON);
	    
	    System.out.println(resultJSON.toString());
	   
	    
		return resultJSON.toString();
	}
	
	public String getFsShoppingChoice(String shoppingChoice){
		Model local = ModelFactory.createDefaultModel();		
		Model model = FileManager.get().readModel(local, "/Users/umut/Documents/workspace/eclipse_git_enterprise/Mobivoc/src/main/resources/mymodel3_1.ttl");
		
		JSONObject oJsonInner_label = new JSONObject();
		JSONObject oJsonInner_lat = new JSONObject();
		JSONObject oJsonInner_long = new JSONObject();
		
		JSONObject feature_label = new JSONObject();
		JSONObject feature_lat = new JSONObject();
		JSONObject feature_long = new JSONObject();

		
		JSONObject fillingStationJSON = new JSONObject();
		JSONObject resultJSON = new JSONObject();
		
		ArrayList<JSONObject> fillingStationFeauture = new ArrayList<JSONObject>();
		ArrayList<JSONObject> fillingStationList = new ArrayList<JSONObject>();
		ArrayList<JSONObject> resultList = new ArrayList<JSONObject>();
	
		String queryString = 
	            "PREFIX mv: <http://eccenca.com/mobivoc/> " +
	            "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
	            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
	            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
	            "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
	            "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
	            "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
	            
	            "SELECT ?fuelstation " + 
	            "WHERE { { ?fuelstation a mv:FuelStation. } }" ;
	            					
	            
		Query queryFillingStation = QueryFactory.create(queryString);
	    QueryExecution qexec = QueryExecutionFactory.create(queryFillingStation, model);
	    
	    ParameterizedSparqlString pss = new ParameterizedSparqlString();
	    
	    try {
	        ResultSet results = qexec.execSelect();
	        while ( results.hasNext() ) {
	        	fillingStationFeauture = new ArrayList<JSONObject>();
	        	fillingStationJSON = new JSONObject();
	            QuerySolution soln = results.next();
	            Resource fuelStationObject = soln.getResource("fuelstation");
	  
	            
	            ParameterizedSparqlString queryStation = new ParameterizedSparqlString();
	            queryStation.setCommandText("SELECT * " +
						  "WHERE { " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2000/01/rdf-schema#label> ?hasValue } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/fillingStationNumber> ?hasID } " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?hasLat } " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?hasLong } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/hasShoppingFacility> ?shoppingChoice } " +	
						  "}" );
	            
	           
	            System.out.println("shoppingChoice: " + shoppingChoice);
	            
	            
	            int type = 0;
	            
	            if(!shoppingChoice.equals("E")){
	            	type = 1;
	            	System.out.println("Enter parkingChoice" + shoppingChoice);
	            	queryStation.setLiteral("parkingChoice", shoppingChoice);
	            }
	          
	            Query query = queryStation.asQuery();
	            
	            Query query2 = QueryFactory.create(query);
	            QueryExecution qexec2 = QueryExecutionFactory.create(query2, model);
	            
	            int counter = 0;
	           
	            try{                	
	            	ResultSet results2 = qexec2.execSelect();
	            	while ( results2.hasNext() ) {
	            		
	            		oJsonInner_label = new JSONObject();
	            		oJsonInner_lat = new JSONObject();
	            		oJsonInner_long = new JSONObject();
	            		feature_label = new JSONObject();
	            		feature_lat = new JSONObject();
	            		feature_long = new JSONObject();
	            		
	            		QuerySolution soln2 = results2.next();
	            		//Resource propertyResource = soln2.getResource("property");
	            		if(counter == 0){
	            			if(soln2.get("hasValue").isLiteral()){
		            			Literal literal = soln2.getLiteral("hasValue");
		            			oJsonInner_label.put("Property", "http://www.w3.org/2000/01/rdf-schema#label");
			            		oJsonInner_label.put("Literal", literal);
			            		feature_label.put("Feature", oJsonInner_label);	
			            		fillingStationFeauture.add(feature_label);
		            		}
		            		if(soln2.get("hasID").isLiteral()){
		            			oJsonInner_lat.put("Property", "http://eccenca.com/mobivoc/fillingStationNumber");
			            		Literal literal_lat = soln2.getLiteral("hasID");
			            		oJsonInner_lat.put("Literal", literal_lat);
			        			feature_lat.put("Feature", oJsonInner_lat);
			            		fillingStationFeauture.add(feature_lat);
		            		}
		            		System.out.println("Counter: " + counter++);
	            		}
	            		
	            	}	               	
	            	fillingStationJSON.put("FillingStation", fillingStationFeauture);
	            	fillingStationList.add(fillingStationJSON);               	
	            }
	            catch(Exception e){
	            	
	            }     
	        }
	    } finally {
	        qexec.close();
	    }
	    resultJSON.put("FillingStationList", fillingStationList);
	    resultList.add(resultJSON);
	    
	    System.out.println(resultJSON.toString());
	   
	    
		return resultJSON.toString();
	}
	
	public String getFsParking(String parkingChoice){
		Model local = ModelFactory.createDefaultModel();		
		Model model = FileManager.get().readModel(local, "/Users/umut/Documents/workspace/eclipse_git_enterprise/Mobivoc/src/main/resources/mymodel3_1.ttl");
		
		
		JSONObject oJsonInner_label = new JSONObject();
		JSONObject oJsonInner_lat = new JSONObject();
		JSONObject oJsonInner_long = new JSONObject();
		
		JSONObject feature_label = new JSONObject();
		JSONObject feature_lat = new JSONObject();
		JSONObject feature_long = new JSONObject();

		
		JSONObject fillingStationJSON = new JSONObject();
		JSONObject resultJSON = new JSONObject();
		
		ArrayList<JSONObject> fillingStationFeauture = new ArrayList<JSONObject>();
		ArrayList<JSONObject> fillingStationList = new ArrayList<JSONObject>();
		ArrayList<JSONObject> resultList = new ArrayList<JSONObject>();
	
		String queryString = 
	            "PREFIX mv: <http://eccenca.com/mobivoc/> " +
	            "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
	            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
	            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
	            "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
	            "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
	            "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
	            
	            "SELECT ?fuelstation " + 
	            "WHERE { { ?fuelstation a mv:FuelStation. } }" ;
	            					
	            
		Query queryFillingStation = QueryFactory.create(queryString);
	    QueryExecution qexec = QueryExecutionFactory.create(queryFillingStation, model);
	    
	    ParameterizedSparqlString pss = new ParameterizedSparqlString();
	    
	    try {
	        ResultSet results = qexec.execSelect();
	        while ( results.hasNext() ) {
	        	fillingStationFeauture = new ArrayList<JSONObject>();
	        	fillingStationJSON = new JSONObject();
	            QuerySolution soln = results.next();
	            Resource fuelStationObject = soln.getResource("fuelstation");
	   
	            
	            ParameterizedSparqlString queryStation = new ParameterizedSparqlString();
	            queryStation.setCommandText("SELECT * " +
						  "WHERE { " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2000/01/rdf-schema#label> ?hasValue } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/fillingStationNumber> ?hasID } " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?hasLat } " +
						  			"{ <" + fuelStationObject + "> <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?hasLong } " +
						  			"{ <" + fuelStationObject + "> <http://eccenca.com/mobivoc/hasParkingFacility> ?parkingChoice } " +	
						  "}" );
	            
	           
	            System.out.println("parkingChoice: " + parkingChoice);
	            
	            
	            int type = 0;
	            //queryStation.setIri("fuelChoice", "http://eccenca.com/mobivoc/Biodiesel");
	            
	            
	            if(!parkingChoice.equals("E")){
	            	type = 1;
	            	System.out.println("Enter parkingChoice" + parkingChoice);
	            	queryStation.setLiteral("parkingChoice", parkingChoice);
	            }
	          
	            Query query = queryStation.asQuery();
	            
	            Query query2 = QueryFactory.create(query);
	            QueryExecution qexec2 = QueryExecutionFactory.create(query2, model);
	            
	            int counter = 0;
	           
	            try{                	
	            	ResultSet results2 = qexec2.execSelect();
	            	while ( results2.hasNext() ) {
	            		
	            		oJsonInner_label = new JSONObject();
	            		oJsonInner_lat = new JSONObject();
	            		oJsonInner_long = new JSONObject();
	            		feature_label = new JSONObject();
	            		feature_lat = new JSONObject();
	            		feature_long = new JSONObject();
	            		
	            		QuerySolution soln2 = results2.next();
	            		//Resource propertyResource = soln2.getResource("property");
	            		if(counter == 0){
	            			if(soln2.get("hasValue").isLiteral()){
		            			Literal literal = soln2.getLiteral("hasValue");
		            			oJsonInner_label.put("Property", "http://www.w3.org/2000/01/rdf-schema#label");
			            		oJsonInner_label.put("Literal", literal);
			            		feature_label.put("Feature", oJsonInner_label);	
			            		fillingStationFeauture.add(feature_label);
		            		}
		            		if(soln2.get("hasID").isLiteral()){
		            			oJsonInner_lat.put("Property", "http://eccenca.com/mobivoc/fillingStationNumber");
			            		Literal literal_lat = soln2.getLiteral("hasID");
			            		oJsonInner_lat.put("Literal", literal_lat);
			        			feature_lat.put("Feature", oJsonInner_lat);
			            		fillingStationFeauture.add(feature_lat);
		            		}
		            		System.out.println("Counter: " + counter++);
	            		}
	            	}	               	
	            	fillingStationJSON.put("FillingStation", fillingStationFeauture);
	            	fillingStationList.add(fillingStationJSON);               	
	            }
	            catch(Exception e){
	            	
	            }     
	        }
	    } finally {
	        qexec.close();
	    }
	    resultJSON.put("FillingStationList", fillingStationList);
	    resultList.add(resultJSON);
	    
	    System.out.println(resultJSON.toString());
	   
	    
		return resultJSON.toString();
	}
	
	public synchronized void  createFsInstances() {
		// TODO Auto-generated method stub
		Model semanticModel = ModelFactory.createDefaultModel();
		int counter = 0;
	
		String linkedGeoDataQuery = 
				"PREFIX lgdo: <http://linkedgeodata.org/ontology/> " +
						"PREFIX ogc: <http://www.opengis.net/ont/geosparql#> " +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
						"PREFIX wgs: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
						"PREFIX geom: <http://geovocab.org/geometry#> " +
						"PREFIX bif: <http://www.openlinksw.com/schemas/bif#> " +
						"SELECT DISTINCT ?petrolStationObject " + 
						"WHERE { " + 
								"?petrolStationObject a lgdo:FuelStation ;" + 
						"} LIMIT 50";
		
		
		
		Query query = QueryFactory.create(linkedGeoDataQuery);
	    QueryExecution qexec = QueryExecutionFactory.sparqlService("http://linkedgeodata.org/vsparql", query);
	
	    
	    try {
	    	ResultSet results = qexec.execSelect();
	        while ( results.hasNext() ) {
	            QuerySolution soln = results.nextSolution();
	            Resource petrolStationObject = soln.getResource("petrolStationObject");
	            
	            String petrolStationObjectRes = petrolStationObject.toString();
	            
	            String queryStation =
						"SELECT ?property ?hasValue ?checkLabel ?checkLocation" +
						  "WHERE { " + 
						  			"{ <" + petrolStationObjectRes + "> ?property ?hasValue } " +
						  			
						  "}" ;
	
	            Query query2 = QueryFactory.create(queryStation);
	            QueryExecution qexec2 = QueryExecutionFactory.sparqlService("http://linkedgeodata.org/sparql", query2);
	            
	            counter++;
	            try{
	            	ResultSet results2 = qexec2.execSelect();
	            	while ( results2.hasNext() ) {
	            		QuerySolution soln2 = results2.nextSolution();
	            		Resource propertyResource = soln2.getResource("property");
	            		Literal literalPart = null;
	            		Resource resourcePart;
	            		Property property = semanticModel.createProperty(propertyResource.toString());
	            	                		
	            		//System.out.println("Property: " + property.toString());
	            		
	            		if(soln2.get("hasValue").isLiteral()){
	            			literalPart = soln2.getLiteral("hasValue");
	            			//System.out.println("Literal: " + literalPart.toString() );
	            			
	            		}
	            		else{
	            			resourcePart= soln2.getResource("hasValue");
	            			//System.out.println("Resource: " + resourcePart.toString() );
	            		}
	            		
	            		if(propertyResource.toString().equals("http://www.w3.org/2000/01/rdf-schema#label") ){
	            		
	            			semanticModel.add(petrolStationObject, property, literalPart);
	            		}
	            		if(propertyResource.toString().equals("http://www.w3.org/2003/01/geo/wgs84_pos#lat") ){
	            			
	            			semanticModel.add(petrolStationObject, property, literalPart);
	            		}
	            		if(propertyResource.toString().equals("http://www.w3.org/2003/01/geo/wgs84_pos#long") ){
	            			
	            			semanticModel.add(petrolStationObject, property, literalPart);
	            		}
	            		
	            		
	            		try{
	            			
	            			Property property2 = semanticModel.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	            			Property property3 = semanticModel.createProperty("http://eccenca.com/mobivoc/hasOffer");
	            			
	            			
	            			Property property4 = semanticModel.createProperty("http://eccenca.com/mobivoc/fillingStationNumber");
	            			Property property5 = semanticModel.createProperty("http://eccenca.com/mobivoc/fillingStationHeight");
	            			Property property6 = semanticModel.createProperty("http://eccenca.com/mobivoc/hasParkingFacility");
	            			Property property7 = semanticModel.createProperty("http://eccenca.com/mobivoc/hasWashingFacility");
	            		
	            			Property property8 = semanticModel.createProperty("http://eccenca.com/mobivoc/hasShoppingFacility");
	            			Property property9 = semanticModel.createProperty("http://eccenca.com/mobivoc/hasWCFacility");
	            			Property property10 = semanticModel.createProperty("http://eccenca.com/mobivoc/hasWheelChairEnabled");
	            			
	            			
	            			
	            			Literal literal = semanticModel.createLiteral(new String("Yes"));
	            			
	            			
	            	
	            			Literal literal2 = semanticModel.createTypedLiteral(counter);
	            			
	            			Literal literal3 = semanticModel.createLiteral(new String("10"));
	            			
	            			
	            			
	            			Resource resource_parking = semanticModel.createResource("http://eccenca.com/mobivoc/ParkingFacility");
	            			Property property_parking = semanticModel.createProperty("http://eccenca.com/mobivoc/hasParkingFees");
	            			 
	            			
	           
	            			Resource resource = semanticModel.createResource("http://eccenca.com/mobivoc/FuelStation");
	            			Resource resource2 = semanticModel.createResource("http://eccenca.com/mobivoc/Adblue");
	            			Resource resource3 = semanticModel.createResource("http://eccenca.com/mobivoc/Autogas");
	            			Resource resource4 = semanticModel.createResource("http://eccenca.com/mobivoc/Biodiesel");
	            			Resource resource5 = semanticModel.createResource("http://eccenca.com/mobivoc/CompressedNaturalGas");
	            			Resource resource6 = semanticModel.createResource("http://eccenca.com/mobivoc/DieselFuel");
	            			Resource resource7 = semanticModel.createResource("http://eccenca.com/mobivoc/E85");
	            			Resource resource8 = semanticModel.createResource("http://eccenca.com/mobivoc/Ethanol");
	            			Resource resource9 = semanticModel.createResource("http://eccenca.com/mobivoc/ExcelliumDiesel");
	            			Resource resource10 = semanticModel.createResource("http://eccenca.com/mobivoc/ExcelliumSuperPlus");
	            			Resource resource11 = semanticModel.createResource("http://eccenca.com/mobivoc/Hydrogen");
	            			Resource resource12 = semanticModel.createResource("http://eccenca.com/mobivoc/LiquidGas");
	            			Resource resource13 = semanticModel.createResource("http://eccenca.com/mobivoc/LKWDiesel");
	            			Resource resource14 = semanticModel.createResource("http://eccenca.com/mobivoc/MaxxMotionDiesel");
	            			Resource resource15 = semanticModel.createResource("http://eccenca.com/mobivoc/MaxxMotionSuper100");
	            			Resource resource16 = semanticModel.createResource("http://eccenca.com/mobivoc/Methane");
	            			Resource resource17 = semanticModel.createResource("http://eccenca.com/mobivoc/Petrol");
	            			Resource resource18 = semanticModel.createResource("http://eccenca.com/mobivoc/Super(E5)");
	            			Resource resource19 = semanticModel.createResource("http://eccenca.com/mobivoc/Super(E10)");
	            			Resource resource20 = semanticModel.createResource("http://eccenca.com/mobivoc/SuperDiesel");
	            			Resource resource21 = semanticModel.createResource("http://eccenca.com/mobivoc/SuperDiesel");
	            			Resource resource22 = semanticModel.createResource("http://eccenca.com/mobivoc/UltimateDiesel");
	            			Resource resource23 = semanticModel.createResource("http://eccenca.com/mobivoc/UltimateSuper");
	            			Resource resource24 = semanticModel.createResource("http://eccenca.com/mobivoc/VPowerDiesel");
	            			Resource resource25 = semanticModel.createResource("http://eccenca.com/mobivoc/VPowerRacing");
	            		
	            			
	                		
	            			semanticModel.add(petrolStationObject, property2, resource);
	                		
	            			semanticModel.add(petrolStationObject, property3, resource2);
	            			semanticModel.add(petrolStationObject, property3, resource3);
	            			semanticModel.add(petrolStationObject, property3, resource4);
	            			semanticModel.add(petrolStationObject, property3, resource5);
	            			semanticModel.add(petrolStationObject, property3, resource6);
	            			semanticModel.add(petrolStationObject, property3, resource7);
	            			semanticModel.add(petrolStationObject, property3, resource8);
	            			semanticModel.add(petrolStationObject, property3, resource9);
	            			semanticModel.add(petrolStationObject, property3, resource10);
	            			semanticModel.add(petrolStationObject, property3, resource11);
	            			semanticModel.add(petrolStationObject, property3, resource12);
	            			semanticModel.add(petrolStationObject, property3, resource13);
	            			semanticModel.add(petrolStationObject, property3, resource14);
	            			semanticModel.add(petrolStationObject, property3, resource15);
	            			semanticModel.add(petrolStationObject, property3, resource16);
	            			semanticModel.add(petrolStationObject, property3, resource17);
	            			semanticModel.add(petrolStationObject, property3, resource18);
	            			semanticModel.add(petrolStationObject, property3, resource19);
	            			semanticModel.add(petrolStationObject, property3, resource20);
	            			semanticModel.add(petrolStationObject, property3, resource21);
	            			semanticModel.add(petrolStationObject, property3, resource22);
	            			semanticModel.add(petrolStationObject, property3, resource23);
	            			semanticModel.add(petrolStationObject, property3, resource24);
	            			semanticModel.add(petrolStationObject, property3, resource25);

	                		//System.out.println("Im here7");
	                		
	            			semanticModel.add(petrolStationObject, property4, literal2);
	                		
	            			semanticModel.add(petrolStationObject, property5, literal3);
	                		
	            			semanticModel.add(petrolStationObject, property6, literal);
	                		
	            			semanticModel.add(petrolStationObject, property7, literal);
	                		
	            			semanticModel.add(petrolStationObject, property8, literal);
	                		
	            			semanticModel.add(petrolStationObject, property9, literal);
	                		
	            			semanticModel.add(petrolStationObject, property10, literal);
	                		
	                		
	            		}
	            		catch(Exception e){
	            			
	            			Resource resource = soln2.getResource("hasValue");
	            			semanticModel.add(petrolStationObject, property, resource);
	                		
	            			System.out.println("Not Literal " + e);
	            		}	
	            	}	
	            }finally{
	            	qexec2.close();
	            }                
	        }
	    }catch(Exception e){ 
	    	System.out.printf("The error: " + e);
	    }finally {
	        qexec.close();
	    }
	    
	    try {
	    	FileWriter out = new FileWriter( "/Users/umut/Documents/workspace/eclipse_git_enterprise/Mobivoc/src/main/resources/mymodel3_1.ttl" );
	    	semanticModel.write( out, "Turtle" );
	        out.flush();
	        out.close();
	        semanticModel.resetRDFWriterF();
	        semanticModel.resetRDFReaderF();
	       
	      }
	      catch(Exception e) {
	        System.out.println("Error");
	      }
	}
	
	
	public synchronized void  createFSInstancesByLocation(String location) {
		// TODO Auto-generated method stub
		Model semanticModel = ModelFactory.createDefaultModel();
		int counter = 0;
		
		String longitude = "12.37307", latitude = "51.33970";
		
		if (location == "Bonn"){
			longitude = "7.09821";
			latitude = "50.73743";
		}
		if (location == "Leipzig"){
			longitude = "12.37307";
			latitude = "51.33970";
		}
	
		String linkedGeoDataQuery = 
				"PREFIX lgdo: <http://linkedgeodata.org/ontology/> " +
						"PREFIX ogc: <http://www.opengis.net/ont/geosparql#> " +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
						"PREFIX wgs: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
						"PREFIX geom: <http://geovocab.org/geometry#> " +
						"PREFIX bif: <http://www.openlinksw.com/schemas/bif#> " +
						"SELECT DISTINCT ?petrolStationObject " + 
						"WHERE { " + 
								"?petrolStationObject a lgdo:FuelStation ;" +
									//	 "rdfs:label ?checkLabel ;"  + 
									//	 "wgs:lat ?checkLatitute ;" +
										 "geom:geometry [ ogc:asWKT ?g ] ;" +
								"Filter(bif:st_intersects (?g, bif:st_point (" + longitude + ", " +  latitude + "), 0.1))" + 
						"} LIMIT 50";
		
		
		
		Query query = QueryFactory.create(linkedGeoDataQuery);
	    QueryExecution qexec = QueryExecutionFactory.sparqlService("http://linkedgeodata.org/vsparql", query);
	
	    
	    try {
	    	ResultSet results = qexec.execSelect();
	        while ( results.hasNext() ) {
	            QuerySolution soln = results.nextSolution();
	            Resource petrolStationObject = soln.getResource("petrolStationObject");
	            
	            String petrolStationObjectRes = petrolStationObject.toString();
	            
	            String queryStation =
						"SELECT ?property ?hasValue ?checkLabel ?checkLocation" +
						  "WHERE { " + 
						  			"{ <" + petrolStationObjectRes + "> ?property ?hasValue } " +
						  			
						  "}" ;
	
	            Query query2 = QueryFactory.create(queryStation);
	            QueryExecution qexec2 = QueryExecutionFactory.sparqlService("http://linkedgeodata.org/sparql", query2);
	            
	            counter++;
	            try{
	            	ResultSet results2 = qexec2.execSelect();
	            	while ( results2.hasNext() ) {
	            		QuerySolution soln2 = results2.nextSolution();
	            		Resource propertyResource = soln2.getResource("property");
	            		Literal literalPart = null;
	            		Resource resourcePart;
	            		Property property = semanticModel.createProperty(propertyResource.toString());
	            	                		
	            		
	            		
	            		if(soln2.get("hasValue").isLiteral()){
	            			literalPart = soln2.getLiteral("hasValue");
	            			//System.out.println("Literal: " + literalPart.toString() );
	            			
	            		}
	            		else{
	            			resourcePart= soln2.getResource("hasValue");
	            			//System.out.println("Resource: " + resourcePart.toString() );
	            		}
	            		
	            		if(propertyResource.toString().equals("http://www.w3.org/2000/01/rdf-schema#label") ){
	            			
	            			semanticModel.add(petrolStationObject, property, literalPart);
	            		}
	            		if(propertyResource.toString().equals("http://www.w3.org/2003/01/geo/wgs84_pos#lat") ){
	            			
	            			semanticModel.add(petrolStationObject, property, literalPart);
	            		}
	            		if(propertyResource.toString().equals("http://www.w3.org/2003/01/geo/wgs84_pos#long") ){
	            			
	            			semanticModel.add(petrolStationObject, property, literalPart);
	            		}
	            		
	            		
	            		
	            		try{
	            			
	            			
	            			Property property2 = semanticModel.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
	            			Property property3 = semanticModel.createProperty("http://eccenca.com/mobivoc/hasOffer");
	            			
	            			
	            			
	            			Property property4 = semanticModel.createProperty("http://eccenca.com/mobivoc/fillingStationNumber");
	            			Property property5 = semanticModel.createProperty("http://eccenca.com/mobivoc/fillingStationHeight");
	            			Property property6 = semanticModel.createProperty("http://eccenca.com/mobivoc/hasParkingFacility");
	            			Property property7 = semanticModel.createProperty("http://eccenca.com/mobivoc/hasWashingFacility");
	            		
	            			Property property8 = semanticModel.createProperty("http://eccenca.com/mobivoc/hasShoppingFacility");
	            			Property property9 = semanticModel.createProperty("http://eccenca.com/mobivoc/hasWCFacility");
	            			Property property10 = semanticModel.createProperty("http://eccenca.com/mobivoc/hasWheelChairEnabled");
	            			
	            			
	            			Literal literal = semanticModel.createLiteral(new String("Yes"));
	            			
	            			
	            			Literal literal2 = semanticModel.createTypedLiteral(counter);
	            			
	            			Literal literal3 = semanticModel.createLiteral(new String("10"));
	            			
	            			
	            			
	            			Literal literal_long = semanticModel.createLiteral(longitude);
	            			Literal literal_lat = semanticModel.createLiteral(latitude);
	            			
	            			
	            			
	            			Resource resource_parking = semanticModel.createResource("http://eccenca.com/mobivoc/ParkingFacility");
	            			Property property_parking = semanticModel.createProperty("http://eccenca.com/mobivoc/hasParkingFees");
	            			
	            			Resource resource = semanticModel.createResource("http://eccenca.com/mobivoc/FuelStation");
	            			Resource resource2 = semanticModel.createResource("http://eccenca.com/mobivoc/Adblue");
	            			Resource resource3 = semanticModel.createResource("http://eccenca.com/mobivoc/Autogas");
	            			Resource resource4 = semanticModel.createResource("http://eccenca.com/mobivoc/Biodiesel");
	            			Resource resource5 = semanticModel.createResource("http://eccenca.com/mobivoc/CompressedNaturalGas");
	            			Resource resource6 = semanticModel.createResource("http://eccenca.com/mobivoc/DieselFuel");
	            			Resource resource7 = semanticModel.createResource("http://eccenca.com/mobivoc/E85");
	            			Resource resource8 = semanticModel.createResource("http://eccenca.com/mobivoc/Ethanol");
	            			Resource resource9 = semanticModel.createResource("http://eccenca.com/mobivoc/ExcelliumDiesel");
	            			Resource resource10 = semanticModel.createResource("http://eccenca.com/mobivoc/ExcelliumSuperPlus");
	            			Resource resource11 = semanticModel.createResource("http://eccenca.com/mobivoc/Hydrogen");
	            			Resource resource12 = semanticModel.createResource("http://eccenca.com/mobivoc/LiquidGas");
	            			Resource resource13 = semanticModel.createResource("http://eccenca.com/mobivoc/LKWDiesel");
	            			Resource resource14 = semanticModel.createResource("http://eccenca.com/mobivoc/MaxxMotionDiesel");
	            			Resource resource15 = semanticModel.createResource("http://eccenca.com/mobivoc/MaxxMotionSuper100");
	            			Resource resource16 = semanticModel.createResource("http://eccenca.com/mobivoc/Methane");
	            			Resource resource17 = semanticModel.createResource("http://eccenca.com/mobivoc/Petrol");
	            			Resource resource18 = semanticModel.createResource("http://eccenca.com/mobivoc/Super(E5)");
	            			Resource resource19 = semanticModel.createResource("http://eccenca.com/mobivoc/Super(E10)");
	            			Resource resource20 = semanticModel.createResource("http://eccenca.com/mobivoc/SuperDiesel");
	            			Resource resource21 = semanticModel.createResource("http://eccenca.com/mobivoc/SuperDiesel");
	            			Resource resource22 = semanticModel.createResource("http://eccenca.com/mobivoc/UltimateDiesel");
	            			Resource resource23 = semanticModel.createResource("http://eccenca.com/mobivoc/UltimateSuper");
	            			Resource resource24 = semanticModel.createResource("http://eccenca.com/mobivoc/VPowerDiesel");
	            			Resource resource25 = semanticModel.createResource("http://eccenca.com/mobivoc/VPowerRacing");
	            		
	            			
	                		
	            			semanticModel.add(petrolStationObject, property2, resource);
	                		
	            			semanticModel.add(petrolStationObject, property3, resource2);
	            			semanticModel.add(petrolStationObject, property3, resource3);
	            			semanticModel.add(petrolStationObject, property3, resource4);
	            			semanticModel.add(petrolStationObject, property3, resource5);
	            			semanticModel.add(petrolStationObject, property3, resource6);
	            			semanticModel.add(petrolStationObject, property3, resource7);
	            			semanticModel.add(petrolStationObject, property3, resource8);
	            			semanticModel.add(petrolStationObject, property3, resource9);
	            			semanticModel.add(petrolStationObject, property3, resource10);
	            			semanticModel.add(petrolStationObject, property3, resource11);
	            			semanticModel.add(petrolStationObject, property3, resource12);
	            			semanticModel.add(petrolStationObject, property3, resource13);
	            			semanticModel.add(petrolStationObject, property3, resource14);
	            			semanticModel.add(petrolStationObject, property3, resource15);
	            			semanticModel.add(petrolStationObject, property3, resource16);
	            			semanticModel.add(petrolStationObject, property3, resource17);
	            			semanticModel.add(petrolStationObject, property3, resource18);
	            			semanticModel.add(petrolStationObject, property3, resource19);
	            			semanticModel.add(petrolStationObject, property3, resource20);
	            			semanticModel.add(petrolStationObject, property3, resource21);
	            			semanticModel.add(petrolStationObject, property3, resource22);
	            			semanticModel.add(petrolStationObject, property3, resource23);
	            			semanticModel.add(petrolStationObject, property3, resource24);
	            			semanticModel.add(petrolStationObject, property3, resource25);

	                		
	                		
	            			semanticModel.add(petrolStationObject, property4, literal2);
	                		
	            			semanticModel.add(petrolStationObject, property5, literal3);
	                		
	            			semanticModel.add(petrolStationObject, property6, literal);
	                		
	            			semanticModel.add(petrolStationObject, property7, literal);
	                		
	            			semanticModel.add(petrolStationObject, property8, literal);
	                		
	            			semanticModel.add(petrolStationObject, property9, literal);
	                		
	            			semanticModel.add(petrolStationObject, property10, literal);
	                		
	                		
	            		}
	            		catch(Exception e){
	            			
	            			Resource resource = soln2.getResource("hasValue");
	            			semanticModel.add(petrolStationObject, property, resource);
	                		
	            			System.out.println("Not Literal " + e);
	            		}	
	            	}	
	            }finally{
	            	qexec2.close();
	            }                
	        }
	    }catch(Exception e){ 
	    	System.out.printf("The error: " + e);
	    }finally {
	        qexec.close();
	    }
	    
	    try {
	    	
	    	FileWriter out = new FileWriter( "/Users/umut/Documents/workspace/eclipse_git_enterprise/Mobivoc/src/main/resources/mymodel3_1.ttl" );
	    	semanticModel.write( out, "Turtle" );
	        out.flush();
	        out.close();
	        semanticModel.resetRDFWriterF();
	        semanticModel.resetRDFReaderF();
	          
	      }
	      catch(Exception e) {
	        System.out.println("Error");
	      }
	}

}
