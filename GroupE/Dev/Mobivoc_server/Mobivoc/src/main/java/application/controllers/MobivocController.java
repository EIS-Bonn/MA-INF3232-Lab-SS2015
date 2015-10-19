package application.controllers;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import application.model.dao.tdb.MobivocDAO;
import application.model.model.FillingStation;
import application.model.model.FillingStationHeader;
import application.test.MobivocTest;

@RestController
public class MobivocController {

	MobivocDAO mobivocDAO = new MobivocDAO();
	MobivocTest mobivocTester = new MobivocTest();

	@RequestMapping("/fuelstation")
	public @ResponseBody String getAllFuelStation(
			
			@RequestParam(value = "longitude", required = true) String longitude,
			@RequestParam(value = "latitude", required = true) String latitude,
			@RequestParam(value = "fuelChoice", required = true) String fuelChoice,
			@RequestParam(value = "parkingChoice", required = true) String parkingChoice,
			@RequestParam(value = "wcChoice", required = true) String wcChoice,
			@RequestParam(value = "shoppingChoice", required = true) String shoppingChoice,
			@RequestParam(value = "washingChoice", required = true) String washingChoice,
			@RequestParam(value = "heightChoice", required = true) String heightChoice,
			@RequestParam(value = "wheelChairChoice", required = true) String wheelChairChoice

			)
	{
	    System.out.println("Rest Request called" + fuelChoice);
	    System.out.println("Send Rest Request to GEO-Sparql");
	    System.out.println("Waiting for answer...");
	    mobivocDAO.createFillingStationInstances(longitude, latitude);
	    System.out.println("GEO-Sparql service return answer");
	    System.out.println("return data to application");
	    return mobivocDAO.getAllFs(fuelChoice, parkingChoice,
			 	wcChoice, shoppingChoice, washingChoice, heightChoice, wheelChairChoice);
	}
	
	
	    
	    @RequestMapping("/batterystation")
		public @ResponseBody String getAllBatteryStation(
				
				@RequestParam(value = "plugChoice", required = true) String plugChoice,
				@RequestParam(value = "accessChoice", required = true) String accessChoice,
				@RequestParam(value = "identifyChoice", required = true) String identifyChoice,
				@RequestParam(value = "chargeChoice", required = true) String chargeChoice,
				@RequestParam(value = "bookingChoice", required = true) String bookingChoice
				)
		{
		    
		    System.out.println("return data to application" + plugChoice +  accessChoice + identifyChoice + chargeChoice + bookingChoice);
		    return mobivocDAO.getAllBs(plugChoice, accessChoice, identifyChoice,
		    		chargeChoice, bookingChoice);
		}
	
	@RequestMapping("/fsdetail")
	public @ResponseBody String getFuelStationDetail(
			
			@RequestParam(value = "label", required = true) String ID
			
			)
	{
		return mobivocDAO.getFsDetail(ID);
	}
	
	@RequestMapping("/bsdetail")
	public @ResponseBody String getBatteryStationDetail(
			
			@RequestParam(value = "label", required = true) String label
			
			)
	{
		return mobivocDAO.getBsDetail(label);
	}
	
	@RequestMapping("/")
	public @ResponseBody void init()
	{
		System.out.println("init Connection");
	}

	
	
	@RequestMapping("/fslocation")
	public @ResponseBody String getFsByLabel(
			@RequestParam(value = "label", required = true) String id)
	{
		System.out.println("LABEL: " + id);
		
		
		return mobivocDAO.getFsByLabel(id);
	}
	
	//Test Cases
	
	
	@RequestMapping("/testlocation")
	public @ResponseBody String getFsLocation(
			@RequestParam(value = "lat", required = true) String lat,
			@RequestParam(value = "long", required = true) String longi)
	{
		
		return mobivocTester.getFsLocation(lat, longi);
	}
	
	//Parking and WC test with giving location
	
	@RequestMapping("/testparkwc")
	public @ResponseBody String getFsParkWc(
			@RequestParam(value = "lat", required = true) String latitude,
			@RequestParam(value = "long", required = true) String longitude,
			@RequestParam(value = "parkingChoice", required = true) String parkingChoice,
			@RequestParam(value = "wcChoice", required = true) String wcChoice)
	{
		mobivocDAO.createFillingStationInstances(longitude, latitude);
		return mobivocTester.getFsParkWc(parkingChoice, wcChoice);
	}
	
	//wheelChair test all germany
	
	//https://localhost:8080/Mobivoc/testwheelChair?wheelChair=Yes)
	
		@RequestMapping("/testwheelChair")
		public @ResponseBody String getFsParkWc(
				
				@RequestParam(value = "wheelChair", required = true) String wheelChair)
		{
			System.out.println("Im entering");
			mobivocTester.createFsInstances();
			return mobivocTester.getFsWheelChair(wheelChair);
		}
		//http://localhost:8080/Mobivoc/testshopping?shoppingChoice=Yes)
		//shoppingChoice test all germany
		
		@RequestMapping("/testshopping")
		public @ResponseBody String getFsShoppingChoice(
				
				@RequestParam(value = "shoppingChoice", required = true) String shoppingChoice)
		{
			mobivocTester.createFsInstances();
			return mobivocTester.getFsShoppingChoice(shoppingChoice);
		}
		
		//https://localhost:8080/Mobivoc/testwashingshopping?location=Leipzig&washingChoice=Yes&shoppingChoice=Yes)
		//Washing and shopping choice with giving location
		
		@RequestMapping("/testwashingshopping")
		public @ResponseBody String getFsWashingShop(
				@RequestParam(value = "location", required = true) String location,
				@RequestParam(value = "washingChoice", required = true) String washingChoice,
				@RequestParam(value = "shoppingChoice", required = true) String shoppingChoice)
		{
			System.out.println("Entering");
			mobivocTester.createFSInstancesByLocation(location);
			return mobivocTester.getFsWashingShop(washingChoice, shoppingChoice);
		}
	
		//http://localhost:8080/Mobivoc/testwashingwc?location=Bonn&parkingChoice=Yes&wcChoice=Yes
		//Washing and WC test with giving location
		
		@RequestMapping("/testwashingwc")
		public @ResponseBody String getFsWashingWc(
				@RequestParam(value = "location", required = true) String location,
				@RequestParam(value = "parkingChoice", required = true) String washingChoice,
				@RequestParam(value = "wcChoice", required = true) String wcChoice)
		{
			mobivocTester.createFSInstancesByLocation(location);
			return mobivocTester.getFsWashingWc(washingChoice, wcChoice);
		}
	
		//localhost:8080/Mobivoc/testwc?location=Bonn&wcChoice=Yes
		//WC test with giving location
		
		@RequestMapping("/testwc")
		public @ResponseBody String getFsShopping(
				@RequestParam(value = "location", required = true) String location,
				@RequestParam(value = "wcChoice", required = true) String wcChoice)
		{
			mobivocTester.createFSInstancesByLocation(location);
			return mobivocTester.getFsWc(wcChoice);
		}
				
		//Parking test with all location
		//htpp://localhost:8080/Mobivoc/testparking?parkingChoice=Yes
		
		
		@RequestMapping("/testparking")
		public @ResponseBody String getFsShopping(
				@RequestParam(value = "parkingChoice", required = true) String parkingChoice)
		{
			mobivocTester.createFsInstances();
			return mobivocTester.getFsParking(parkingChoice);
		}		
				
		
	@RequestMapping("/fsdetailer")
	public @ResponseBody String getFsLocation(
			@RequestParam(value = "label", required = true) String label)
	{
		System.out.println("LABEL location: " + label);
		
		return mobivocDAO.getFsLocation(label);
	}
	
	@RequestMapping("/testsparql")
	public @ResponseBody String  testSparql(){
		MobivocTest mobivocTester = new MobivocTest();
		return mobivocTester.testSparql();
	}
	
	@RequestMapping("/testallfs")
	public @ResponseBody String  getAllFs(){
		
		//mobivocDAO.createFillingStationInstances();
		return mobivocTester.getAllFs();
	}
}
