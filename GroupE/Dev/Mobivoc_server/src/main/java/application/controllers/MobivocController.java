package application.controllers;

import java.util.List;

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
	
	@RequestMapping("/fuelstation")
	public @ResponseBody String getAllFuelStation(
			@RequestParam(value = "longitude", required = true) String longitude,
			@RequestParam(value = "latitude", required = true) String latitude){
		
		
		System.out.println("longitude: " + longitude );
		
		System.out.println("latitude: " + latitude );
		
		
		
		FillingStationHeader fillingStationHeader = new FillingStationHeader();
		Gson gson = new Gson();
		
		fillingStationHeader.setFillingStationList(mobivocDAO.getFillingStation());
		
		return gson.toJson(fillingStationHeader);    	
	}
	@RequestMapping("/createInstance")
	public void createInstance(){
		mobivocDAO.createFillingStationInstances();
	}
	
	@RequestMapping("/fscity")
	public @ResponseBody String getFsByCity(){
		return null;
	}
	
	@RequestMapping("/fsfueltype")
	public @ResponseBody String getFsByFuelType(){
		return null;
	}
	
	@RequestMapping("/fsfueltypeproperty")
	public @ResponseBody String getFsByFuelTypeProperty(){
		return null;
	}
	
	@RequestMapping("/fswheelchair")
	public @ResponseBody String getFsByWheelChair(){
		return null;
	}
	@RequestMapping("/fsheight")
	public @ResponseBody String getFsByHeight(){
		return null;
	}
	@RequestMapping("/fswashingfacility")
	public @ResponseBody String getFsByWashingFacility(){
		return null;
	}
	
	@RequestMapping("/testallfs")
	public @ResponseBody String  getAllFs(){
		MobivocTest mobivocTester = new MobivocTest();
		return mobivocTester.getAllFs();
	}
	
	
}
