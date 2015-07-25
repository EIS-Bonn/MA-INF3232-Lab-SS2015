package application.model.dao.tdb;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

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
        this.model = FileManager.get().loadModel("FillingStation.ttl", null, "TURTLE");
        
	}
	//FileManager.get().addLocatorClassLoader(MobivocDAO.class);
	//Model model = FileManager.get().loadModel("FillingStation.ttl", null, "TURTLE");

	public List<FillingStation> getFillingStation() {
		// TODO Auto-generated method stub

	/*
		
	
		String queryString = 
                "PREFIX mv: <http://eccenca.com/mobivoc/> " +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
                "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
        		"SELECT ?fsNumber ?fsHeight ?oName ?cName ?locality ?pCode ?region ?sAddress ?tel ?url ?longtitute ?latitute " +
                "WHERE { " +
        		"    ?fuelStation a mv:FuelStation . " +
        		"    ?fuelStation mv:fillingStationNumber ?fsNumber . " +
        		"    ?fuelStation mv:fillingStationHeight ?fsHeight . " +
        		"    ?fuelStation vcard:organization-name  ?oName . " +
        		"    ?fuelStation vcard:country-name ?cName . " +
        		"    ?fuelStation vcard:locality ?locality . " +
        		"    ?fuelStation vcard:postal-code ?pCode . " +
        		"    ?fuelStation vcard:region ?region . " +
        		"    ?fuelStation vcard:street-address ?sAddress . " +
        		"    ?fuelStation vcard:tel ?tel . " +
        		"    ?fuelStation vcard:url ?url . " +
        		"    ?fuelStation geo:long ?longtitute . " +
        		"	 ?fuelStation geo:lat  ?latitute ." +
        		"}";
		
		String queryString2 = 
                "PREFIX mv: <http://eccenca.com/mobivoc/> " +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
                "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
        		"SELECT ?fsNumber ?fsHeight ?oName ?cName ?locality ?pCode ?region ?sAddress ?tel ?url ?longtitute ?latitute " +
                "WHERE { " +
        		"    ?fuelStation a mv:FuelStation . " +
        		"    ?fuelStation mv:fillingStationNumber ?fsNumber . " +
        		"    ?fuelStation mv:fillingStationHeight ?fsHeight . " +
        		"    ?fuelStation vcard:organization-name  ?oName . " +
        		"    ?fuelStation vcard:hasAddress  [ vcard:country-name ?cName ;  " +
        		"									  vcard:locality ?locality ;  " +	
        		"									  vcard:postal-code ?pCode ;  " +
        		"									  vcard:region ?region ;  " +	
        		"									  vcard:street-address ?sAddress; ]." +	
        		"    ?fuelStation vcard:tel ?tel . " +
        		"    ?fuelStation vcard:url ?url . " +
        		"    ?fuelStation geo:long ?longtitute . " +
        		"	 ?fuelStation geo:lat  ?latitute ." +
        		"}";
		
		
		System.out.println();
		System.out.println();
		
		String queryStringFillingStationAddress = 
                "PREFIX mv: <http://eccenca.com/mobivoc/> " +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
                "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
        		"SELECT ?fsNumber ?cName ?locality ?pCode ?region ?sAddress " +
                "WHERE { " +
        		"    ?fuelStation a mv:FuelStation . " +
        		"    ?fuelStation mv:fillingStationNumber ?fsNumber . " +
        		"    ?fuelStation vcard:hasAddress  [ vcard:country-name ?cName ;  " +
        		"									  vcard:locality ?locality ;  " +	
        		"									  vcard:postal-code ?pCode ;  " +
        		"									  vcard:region ?region ;  " +	
        		"									  vcard:street-address ?sAddress; ]." +	
        		"}";
		
		Query queryFillingStationAddress = QueryFactory.create(queryStringFillingStationAddress);
        QueryExecution qexec1 = QueryExecutionFactory.create(queryFillingStationAddress, model);
        
        try {
            ResultSet results = qexec1.execSelect();
            while ( results.hasNext() ) {
                QuerySolution soln = results.nextSolution();
                Literal fsNumber = soln.getLiteral("fsNumber");
                Literal locality = soln.getLiteral("locality");
                Literal pCode = soln.getLiteral("pCode");
                Literal region = soln.getLiteral("region");
                Literal sAddress = soln.getLiteral("sAddress");
                Literal cName = soln.getLiteral("cName");
                 
                System.out.println("ID: " +  fsNumber + ", Country: " + cName + ", City: " + 
                		locality + " , PostCode: " + pCode + " , Region: " + region + " , Street: " + sAddress 
                		 );
            }
        } finally {
            qexec1.close();
        }
		
		System.out.println();
		System.out.println();
		
		String queryStringGeoCoordinate = 
                "PREFIX mv: <http://eccenca.com/mobivoc/> " +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
                "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
        		"SELECT  ?fsNumber ?longtitute ?latitute " +
                "WHERE { " +
        		"    ?fuelStation a mv:FuelStation . " +
        		"    ?fuelStation mv:fillingStationNumber ?fsNumber . " +
        		"    ?fuelStation geo:long ?longtitute . " +
        		"	 ?fuelStation geo:lat  ?latitute ." +
        		"}";
		
		Query queryGeoCoordinate = QueryFactory.create(queryStringGeoCoordinate);
        QueryExecution qexec2 = QueryExecutionFactory.create(queryGeoCoordinate, model);
        
        try {
            ResultSet results = qexec2.execSelect();
            while ( results.hasNext() ) {
                QuerySolution soln = results.nextSolution();
                Literal fsNumber = soln.getLiteral("fsNumber");
                Literal longtitute = soln.getLiteral("longtitute");
                Literal latitute = soln.getLiteral("latitute");
                
                
                System.out.println("ID: " +  fsNumber + ", Longitude: " + longtitute + ", Latitude: " + 
                		latitute 
                		 );
            }
        } finally {
            qexec2.close();
        }
		
		
		
        System.out.println();
        System.out.println();
		
		
		
		
		String queryStringContactInfo = 
                "PREFIX mv: <http://eccenca.com/mobivoc/> " +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
                "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
        		"SELECT ?fsNumber ?tel ?url  " +
                "WHERE { " +
        		"    ?fuelStation a mv:FuelStation . " +
        		"    ?fuelStation mv:fillingStationNumber ?fsNumber . " +
        		"    ?fuelStation vcard:tel ?tel . " +
        		"    ?fuelStation vcard:url ?url . " +
        		"}";
		
		
		Query queryContactInfo = QueryFactory.create(queryStringContactInfo);
        QueryExecution qexec3 = QueryExecutionFactory.create(queryContactInfo, model);
        
        try {
            ResultSet results = qexec3.execSelect();
            while ( results.hasNext() ) {
                QuerySolution soln = results.nextSolution();
                Literal fsNumber = soln.getLiteral("fsNumber");
                Literal tel = soln.getLiteral("tel");
                Literal url = soln.getLiteral("url");
                
                System.out.println("ID: " +  fsNumber + ", Tel: " + tel + ", URL: " + 
                		url 
                		 );
            }
        } finally {
            qexec3.close();
        }
		
		
		
		
		
		String queryStringFillingStationHeight = 
                "PREFIX mv: <http://eccenca.com/mobivoc/> " +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#> " + 
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> " +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
                "PREFIX vcard: <http://www.w3.org/2006/vcard/ns#> " +
        		"SELECT ?fsNumber ?fsHeight ?oName ?cName ?locality ?pCode ?region ?sAddress ?tel ?url ?longtitute ?latitute " +
                "WHERE { " +
        		"    ?fuelStation a mv:FuelStation . " +
        		"    ?fuelStation mv:fillingStationHeight ?fsHeight . " +
        		"}";
		
		
		
		
		System.out.println();
		System.out.println();
		
		
		
		
		
		String queryString3 = 
				"PREFIX lgdo: <http://linkedgeodata.org/ontology/> " +
				"PREFIX ogc: <http://www.opengis.net/ont/geosparql#> " +
				"PREFIX geom: <http://geovocab.org/geometry#> " +
				"PREFIX bif: <http://www.openlinksw.com/schemas/bif#> " +
				"SELECT DISTINCT ?petrolStationObject ?operator " + 
				"WHERE { " + 
					"SERVICE <http://linkedgeodata.org/vsparql> " +
					"{ " +
						"?petrolStationObject a lgdo:FuelStation ;" +
								 "geom:geometry [ ogc:asWKT ?g ] ;" +
								 "lgdo:operator ?operator ." +
						"Filter(bif:st_intersects (?g, bif:st_point (7.09943, 50.74637), 0.01))" + 
					"}" +
				"} LIMIT 10";
					
		Query query4 = QueryFactory.create(queryString3);
        QueryExecution qexec4 = QueryExecutionFactory.create(query4, model);
        
        Resource petrolStationObj = null;
        String res = "null";
        
        try {
            ResultSet results2 = qexec4.execSelect();
            while ( results2.hasNext() ) {
                QuerySolution soln = results2.nextSolution();
                petrolStationObj = soln.getResource("petrolStationObject");
                Literal operator = soln.getLiteral("operator");
                
                 res = petrolStationObj.toString();
                
                
                System.out.println("PetrolStation: " + petrolStationObj + "Operator: " +  ", " + operator);
            }
        }finally {
        	qexec4.close();
        }
       
        String queryStation =
        					"PREFIX lgdo: <http://linkedgeodata.org/ontology/> " + 
        					"SELECT DISTINCT ?address ?operator " +
        					  "WHERE { " + 
        					  		//"SERVICE <http://linkedgeodata.org/vsparql> " + 
        					  		"{ " +
        					  			"{ <" + res + "> lgdo:addr%3Astreet ?address } " +
        					  			"{ <" + res + "> lgdo:operator ?operator } " +
        					  		"} " + 	
        					  "}" ;
        
        Model model2 = ModelFactory.createDefaultModel();
        
        Query query6 = QueryFactory.create(queryStation);
        QueryExecution qexec6 = QueryExecutionFactory.sparqlService("http://linkedgeodata.org/vsparql", query6);

        ResultSet results6 = qexec6.execSelect();
        //ResultSetFormatter.out(System.out, results, query);      
        
        
        
        Property hasTime = model.createProperty("http://linkedgeodata.org/ontology/addr%3Astreet");
        
        //Query query5 = QueryFactory.create(queryStation);
        //QueryExecution qexec5 = QueryExecutionFactory.create(query5, model2);
        
        try {
            //ResultSet results2 = qexec5.execSelect();
            while ( results6.hasNext() ) {
                QuerySolution soln = results6.nextSolution();
                Literal address = soln.getLiteral("address");
                Literal operator = soln.getLiteral("operator");
               
                //model2.createStatement(petrolStationObj, hasTime, "Bonner");
                model2.add(petrolStationObj, hasTime, "Bonner");
                
                System.out.println("address: " + address + "Operator: " +  ", " + operator);
            }
        }finally {
        	qexec6.close();
        }
        
        
        FileWriter out = null;
        try {
          // XML format - long and verbose
          //out = new FileWriter( "mymodel.xml" );
          //m.write( out, "RDF/XML-ABBREV" );

          // OR Turtle format - compact and more readable
          // use this variant if you're not sure which to use!
          out = new FileWriter( "/Users/umut/Desktop/mymodel3.ttl" );
          model2.write( out, "Turtle" );
        }
        catch(Exception e) {
          System.out.println("Error");
          }
        
        
        
        
        
        
        
        
        
        System.out.println("asdasdasdasdadsasdAddress: sdadasasdasd");

		
		Query query = QueryFactory.create(queryString2);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        
        try {
            ResultSet results = qexec.execSelect();
            while ( results.hasNext() ) {
                QuerySolution soln = results.nextSolution();
                Literal fsNumber = soln.getLiteral("fsNumber");
                Literal fsHeight = soln.getLiteral("fsHeight");
                Literal oName = soln.getLiteral("oName");
                Literal cName = soln.getLiteral("cName");
                Literal locality = soln.getLiteral("locality");
                Literal pCode = soln.getLiteral("pCode");
                Literal region = soln.getLiteral("region");
                Literal sAddress = soln.getLiteral("sAddress");
                Literal tel = soln.getLiteral("tel");
                Literal url = soln.getLiteral("url");
       
                Literal longtitute = soln.getLiteral("longtitute");
                Literal latitute = soln.getLiteral("latitute");
                
                System.out.println(longtitute + " , " + latitute + " ," +
                		fsNumber + " ," + fsHeight + " ," + oName + " ," + cName + " ," + 
                		locality + " ," + pCode + " ," + region + " ," + sAddress + " ," + 
                		tel + " ," + url );
                
                FillingStation fillStation = new FillingStation();
                fillStation.setCountryName(cName.toString());
                fillStation.setFillingStationHeight(fsHeight.toString());
                fillStation.setFillingStationNumber(fsNumber.toString());
                fillStation.setLat(latitute.toString());
                fillStation.setLocality(locality.toString());
                fillStation.setLong(longtitute.toString());
                fillStation.setOrganizationName(oName.toString());
                fillStation.setPostalCode(pCode.toString());
                fillStation.setRegion(region.toString());
                fillStation.setStreetAddress(sAddress.toString());
                fillStation.setTel(tel.toString());
                fillStation.setURL(url.toString());
                
                
                fillingStation.add(fillStation);
                
            }
        } finally {
            qexec.close();
        }
		
		
		*/
		
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

	public void createFillingStationInstances() {
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
								"Filter(bif:st_intersects (?g, bif:st_point (7.098207, 50.737430), 0.1))" + 
						"} LIMIT 5";
		
		
		
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
                		Property property = model2.createProperty(propertyResource.toString());
            			
                		
                		try{
                			
                			
                			//Property property = model2.createProperty(propertyResource.toString());
                			Property property2 = model2.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
                			Property property3 = model2.createProperty("http://eccenca.com/mobivoc/hasOffer");
                			Property property4 = model2.createProperty("http://eccenca.com/mobivoc/fillingStationNumber");
                			Property property5 = model2.createProperty("http://eccenca.com/mobivoc/fillingStationHeight");
                			Property property6 = model2.createProperty("http://eccenca.com/mobivoc/hasParkingFacility");
                			Property property7 = model2.createProperty("http://eccenca.com/mobivoc/hasWashingFacility");
                		
                			
                			Literal literal = soln2.getLiteral("hasValue");
                			Resource resource = model2.createResource("http://eccenca.com/mobivoc/FuelStation");
                			Resource resource2 = model2.createResource("http://eccenca.com/mobivoc/Adblue");
                			
                			
                    		
                    		model2.add(petrolStationObject, property, literal);
                    		model2.add(petrolStationObject, property2, resource);
                    		model2.add(petrolStationObject, property3, resource2);
                    		
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
            out = new FileWriter( "/Users/umut/Desktop/mymodel3_1.ttl" );
            model2.write( out, "Turtle" );
          }
          catch(Exception e) {
            System.out.println("Error");
            }
	
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
