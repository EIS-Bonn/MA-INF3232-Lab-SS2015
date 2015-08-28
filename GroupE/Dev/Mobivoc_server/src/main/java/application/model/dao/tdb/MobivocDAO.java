package application.model.dao.tdb;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.Literal;

import application.model.dao.IMobivocDAO;
import application.model.model.FillingStation;

public class MobivocDAO implements IMobivocDAO {

	final static Logger logger = Logger.getLogger(MobivocDAO.class);
	private Model model;
	private List<FillingStation> fillingStation = new LinkedList<FillingStation>();
	 
	public MobivocDAO(){
		FileManager.get().addLocatorClassLoader(MobivocDAO.class.getClassLoader());
        this.model = FileManager.get().loadModel("mymodel3_1.ttl", null, "TURTLE");
        
	}
	
	public List<FillingStation> getFillingStation() {
		// TODO Auto-generated method stub
		
		return fillingStation;
	}

	public List<String> getFuelStation() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getChargingStation() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getFillingStation(String longtitute, String latitute) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createFillingStationInstances(String longitude, String latitude) {
		// TODO Auto-generated method stub
		
		Model model2 = ModelFactory.createDefaultModel();

		String linkedGeoDataQuery = 
				"PREFIX lgdo: <http://linkedgeodata.org/ontology/> " +
						"PREFIX ogc: <http://www.opengis.net/ont/geosparql#> " +
						"PREFIX geom: <http://geovocab.org/geometry#> " +
						"PREFIX bif: <http://www.openlinksw.com/schemas/bif#> " +
						"SELECT DISTINCT ?petrolStationObject " + 
						"WHERE { " + 
								"?petrolStationObject a lgdo:FuelStation ;" +
										 "geom:geometry [ ogc:asWKT ?g ] ;" +
								"Filter(bif:st_intersects (?g, bif:st_point (" + longitude + ", " +  latitude + "), 0.1))" + 
						"} LIMIT 10";
		
		
		
		Query query = QueryFactory.create(linkedGeoDataQuery);
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://linkedgeodata.org/vsparql", query);

        
        try {
        	ResultSet results = qexec.execSelect();
            while ( results.hasNext() ) {
                QuerySolution soln = results.nextSolution();
                Resource petrolStationObject = soln.getResource("petrolStationObject");
                
                String petrolStationObjectRes = petrolStationObject.toString();
                
                String queryStation =
    					"SELECT ?property ?hasValue " +
    					  "WHERE { " + 
    					  			"{ <" + petrolStationObjectRes + "> ?property ?hasValue } " +		
    					  "}" ;
    
                Query query2 = QueryFactory.create(queryStation);
                QueryExecution qexec2 = QueryExecutionFactory.sparqlService("http://linkedgeodata.org/vsparql", query2);
                
                try{
                	ResultSet results2 = qexec2.execSelect();
                	while ( results2.hasNext() ) {
                		QuerySolution soln2 = results2.nextSolution();
                		Resource propertyResource = soln2.getResource("property");
                		Literal literalPart = null;
                		Resource resourcePart;
                		Property property = model2.createProperty(propertyResource.toString());
                	                		
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
                			System.out.println("IM HERERERERERERER" + literalPart.toString());
                			model2.add(petrolStationObject, property, literalPart);
                		}
                		
                		
                		//System.out.println(propertyResource.toString());
                		
                		try{
                			
                			//System.out.println("Im here");
                			
                			//Property property = model2.createProperty(propertyResource.toString());
                			Property property2 = model2.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
                			Property property3 = model2.createProperty("http://eccenca.com/mobivoc/hasOffer");
                			
                			//System.out.println("Im here2");
                			
                			Property property4 = model2.createProperty("http://eccenca.com/mobivoc/fillingStationNumber");
                			Property property5 = model2.createProperty("http://eccenca.com/mobivoc/fillingStationHeight");
                			Property property6 = model2.createProperty("http://eccenca.com/mobivoc/hasParkingFacility");
                			Property property7 = model2.createProperty("http://eccenca.com/mobivoc/hasWashingFacility");
                		
                			Property property8 = model2.createProperty("http://eccenca.com/mobivoc/hasShoppingFacility");
                			Property property9 = model2.createProperty("http://eccenca.com/mobivoc/hasWCFacility");
                    		
                			//System.out.println("Im here3");
                			
                			//Literal a = model.createLiteral("Yes");
                			
                			Literal literal = model2.createLiteral("Yes");
                			Literal literal2 = model2.createLiteral("10");
                			
                			
                			//Literal literal = soln2.getLiteral("Yes");
                			//Literal literal2 = soln2.getLiteral("10");
                			
                			System.out.println("Im here4");
                			
                			Resource resource = model2.createResource("http://eccenca.com/mobivoc/FuelStation");
                			Resource resource2 = model2.createResource("http://eccenca.com/mobivoc/Adblue");
                			Resource resource3 = model2.createResource("http://eccenca.com/mobivoc/Autogas");
                			Resource resource4 = model2.createResource("http://eccenca.com/mobivoc/Biodiesel");
                			Resource resource5 = model2.createResource("http://eccenca.com/mobivoc/CompressedNaturalGas");
                			Resource resource6 = model2.createResource("http://eccenca.com/mobivoc/DieselFuel");
                			Resource resource7 = model2.createResource("http://eccenca.com/mobivoc/E85");
                			Resource resource8 = model2.createResource("http://eccenca.com/mobivoc/Ethanol");
                			Resource resource9 = model2.createResource("http://eccenca.com/mobivoc/ExcelliumDiesel");
                			Resource resource10 = model2.createResource("http://eccenca.com/mobivoc/ExcelliumSuperPlus");
                			Resource resource11 = model2.createResource("http://eccenca.com/mobivoc/Hydrogen");
                			Resource resource12 = model2.createResource("http://eccenca.com/mobivoc/LiquidGas");
                			Resource resource13 = model2.createResource("http://eccenca.com/mobivoc/LKWDiesel");
                			Resource resource14 = model2.createResource("http://eccenca.com/mobivoc/MaxxMotionDiesel");
                			Resource resource15 = model2.createResource("http://eccenca.com/mobivoc/MaxxMotionSuper100");
                			Resource resource16 = model2.createResource("http://eccenca.com/mobivoc/Methane");
                			Resource resource17 = model2.createResource("http://eccenca.com/mobivoc/Petrol");
                			Resource resource18 = model2.createResource("http://eccenca.com/mobivoc/Super(E5)");
                			Resource resource19 = model2.createResource("http://eccenca.com/mobivoc/Super(E10)");
                			Resource resource20 = model2.createResource("http://eccenca.com/mobivoc/SuperDiesel");
                			Resource resource21 = model2.createResource("http://eccenca.com/mobivoc/SuperDiesel");
                			Resource resource22 = model2.createResource("http://eccenca.com/mobivoc/UltimateDiesel");
                			Resource resource23 = model2.createResource("http://eccenca.com/mobivoc/UltimateSuper");
                			Resource resource24 = model2.createResource("http://eccenca.com/mobivoc/VPowerDiesel");
                			Resource resource25 = model2.createResource("http://eccenca.com/mobivoc/VPowerRacing");
                			

                			//System.out.println("Im here5");
                			
                    		
                    		//model2.add(petrolStationObject, property, literal);
                    		
                    		//System.out.println("Im here6");
                    		
                    		model2.add(petrolStationObject, property2, resource);
                    		
                    		model2.add(petrolStationObject, property3, resource2);
                    		model2.add(petrolStationObject, property3, resource3);
                    		model2.add(petrolStationObject, property3, resource4);
                    		model2.add(petrolStationObject, property3, resource5);
                    		model2.add(petrolStationObject, property3, resource6);
                    		model2.add(petrolStationObject, property3, resource7);
                    		model2.add(petrolStationObject, property3, resource8);
                    		model2.add(petrolStationObject, property3, resource9);
                    		model2.add(petrolStationObject, property3, resource10);
                    		model2.add(petrolStationObject, property3, resource11);
                    		model2.add(petrolStationObject, property3, resource12);
                    		model2.add(petrolStationObject, property3, resource13);
                    		model2.add(petrolStationObject, property3, resource14);
                    		model2.add(petrolStationObject, property3, resource15);
                    		model2.add(petrolStationObject, property3, resource16);
                    		model2.add(petrolStationObject, property3, resource17);
                    		model2.add(petrolStationObject, property3, resource18);
                    		model2.add(petrolStationObject, property3, resource19);
                    		model2.add(petrolStationObject, property3, resource20);
                    		model2.add(petrolStationObject, property3, resource21);
                    		model2.add(petrolStationObject, property3, resource22);
                    		model2.add(petrolStationObject, property3, resource23);
                    		model2.add(petrolStationObject, property3, resource24);
                    		model2.add(petrolStationObject, property3, resource25);
                    		
                    		
                    		//System.out.println("Im here7");
                    		
                    		model2.add(petrolStationObject, property4, literal2);
                    		
                    		model2.add(petrolStationObject, property5, literal2);
                    		
                    		model2.add(petrolStationObject, property6, literal);
                    		
                    		model2.add(petrolStationObject, property7, literal);
                    		
                    		model2.add(petrolStationObject, property8, literal);
                    		
                    		model2.add(petrolStationObject, property9, literal);
                    		
                    		//System.out.println("Im here8");
                			
                    		
                		}
                		catch(Exception e){
                			
                			Resource resource = soln2.getResource("hasValue");
                			model2.add(petrolStationObject, property, resource);
                    		
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
        
        FileWriter out;
		
        try {
            // XML format - long and verbose
            //out = new FileWriter( "mymodel.xml" );
            //m.write( out, "RDF/XML-ABBREV" );

            // OR Turtle format - compact and more readable
            // use this variant if you're not sure which to use!
            //out = new FileWriter( "/Users/umut/Desktop/mymodel3_1.ttl" );
        	out = new FileWriter( "/Users/umut/Documents/workspace/eclipse_git_enterprise/Mobivoc/src/main/resources/mymodel3_1.ttl" );
        	
            model2.write( out, "Turtle" );
          }
          catch(Exception e) {
            System.out.println("Error");
            }
	
	}
	
	public String getAllFs(String fillingStationChoice, String fuelChoice, String parkingChoice,
			String wcChoice, String shoppingChoice, String washingChoice, String heightChoice, String wheelChairChoice){
		
		String resultString = null;
		
		StringBuilder result = new StringBuilder();
		
		Gson gson = new Gson();
		JSONObject mainObj = new JSONObject();
		JSONObject feature = new JSONObject();
		
		ArrayList mainList = new ArrayList();
		
		//ArrayList<JSONObject<ArrayList>> mainList = new ArrayList<ArrayList<JSONObject>>();
		
		ArrayList mainObjArray = new ArrayList();
		
		ArrayList aProjects = new ArrayList();
		JSONObject oJsonInner = new JSONObject();
		
		//JSONArray aProjects = new JSONArray();

		
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
            	aProjects = new ArrayList();
                QuerySolution soln = results.next();
                Resource fuelStationObject = soln.getResource("fuelstation");
                
                String petrolStationObjectRes = fuelStationObject.toString();
                
                //System.out.println(petrolStationObjectRes);
                
                //result.append(petrolStationObjectRes);
                resultString += petrolStationObjectRes; 
                
                
                
                //oJsonInner.put("Resource: ", petrolStationObjectRes);
               
               
                String queryStation =
    					"SELECT ?property ?hasValue " +
    					  "WHERE { " + 
    					  			"{ <" + fuelStationObject + "> ?property ?hasValue } " +		
    					  "}" ;
    
                Query query2 = QueryFactory.create(queryStation);
                QueryExecution qexec2 = QueryExecutionFactory.create(query2, model);
                
                int counter = 0;
                
                try{
                	ResultSet results2 = qexec2.execSelect();
                	while ( results2.hasNext() ) {
                		oJsonInner = new JSONObject();
                		feature = new JSONObject();
                		//System.out.println("Counter: " + counter++);
                		QuerySolution soln2 = results2.next();
                		Resource propertyResource = soln2.getResource("property");
                		
                		
                		oJsonInner.put("Property", propertyResource);
                		
                		resultString += propertyResource + "\t ";
            			
                		
                		if(soln2.get("hasValue").isLiteral()){
                			Literal literal = soln2.getLiteral("hasValue");	
                			oJsonInner.put("Literal", literal);
                		}else if(soln2.get("hasValue").isURIResource()){
                			Resource resource = soln2.getResource("hasValue");
                			oJsonInner.put("Resource", resource);
                		}
                		
                		feature.put("Feature", oJsonInner);
                		aProjects.add(feature);
                		//aProjects.add(oJsonInner);
                	}	
                	//aProjects.add(oJsonInner);
	            }
                catch(Exception e){
                	
                }
           
                mainObj.put("FillingStation", aProjects);
                mainObjArray.add(mainObj);
                
                //aProjects.add(oJsonInner);
                
                
                
                
            }
            
        } finally {
            qexec.close();
        }
		
        JSONObject mainObj2 = new JSONObject();
        
        mainObj2.put("FillingStationList: ", mainObjArray);
		
		ArrayList mainList2 = new ArrayList();
		
		mainList2.add(mainObj2);
        
        System.out.println("Result");
		//return mainList2.toString();
		return mainObjArray.toString();
        //return resultString;
	}
	
	
	Resource createFuelResource(){
		
		int randomNumber;
		Random random = new Random();
		int numberFuelType = 23;
		randomNumber = createRandomNumber(0, numberFuelType, random);
		
		
		Resource fuelResource = randomFuelResource(randomNumber);
		
		return fuelResource;
	}
	
	private Resource randomFuelResource(int randomNumber) {
		// TODO Auto-generated method stub
		
		Resource resource;
		
		if(randomNumber == 0){
			resource = ResourceFactory.createResource();
			return resource;
		}else if(randomNumber == 1){
			resource = ResourceFactory.createResource();
			return resource;
		}
		
		
		else
			return null;
	}

	int createRandomNumber(int aStart, int aEnd, Random aRandom){
		if (aStart > aEnd) {
		      throw new IllegalArgumentException("Start cannot exceed End.");
		    }
		    //get the range, casting to long to avoid overflow problems
		    long range = (long)aEnd - (long)aStart + 1;
		    // compute a fraction of the range, 0 <= frac < range
		    long fraction = (long)(range * aRandom.nextDouble());
		    int randomNumber =  (int)(fraction + aStart);   
		    
		    return randomNumber;
		    
	}

}
