package application.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import application.model.dao.tdb.MobivocDAO;
import application.model.model.FillingStation;

import com.google.gson.Gson;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class MobivocTest {

	final static Logger logger = Logger.getLogger(MobivocDAO.class);
	private static Model model;
	private List<FillingStation> fillingStation = new LinkedList<FillingStation>();
	 
	public MobivocTest(){
		FileManager.get().addLocatorClassLoader(MobivocDAO.class.getClassLoader());
        this.model = FileManager.get().loadModel("mymodel3_1.ttl", null, "TURTLE");
	
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
        
        //System.out.println(JsonWriter.formatJson(mainObj.toString()));
		//return mainList2.toString();
		return mainObjArray.toString();
        //return resultString;
	}
	
	
}
