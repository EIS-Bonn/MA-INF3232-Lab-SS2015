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
			@RequestParam(value = "latitude", required = true) String latitude,
			@RequestParam(value = "fillingStationChoice", required = true) String fillingStationChoice,
			@RequestParam(value = "fuelChoice", required = true) String fuelChoice,
			@RequestParam(value = "parkingChoice", required = true) String parkingChoice,
			@RequestParam(value = "wcChoice", required = true) String wcChoice,
			@RequestParam(value = "shoppingChoice", required = true) String shoppingChoice,
			@RequestParam(value = "washingChoice", required = true) String washingChoice,
			@RequestParam(value = "heightChoice", required = true) String heightChoice,
			@RequestParam(value = "wheelChairChoice", required = true) String wheelChairChoice

			)
	{
		 
		MobivocTest mobivocTester = new MobivocTest();
		mobivocDAO.createFillingStationInstances(longitude, latitude);
		
		return mobivocDAO.getAllFs(fillingStationChoice, fuelChoice, parkingChoice,
				 	wcChoice, shoppingChoice, washingChoice, heightChoice, wheelChairChoice);
		
	}
	
	//Test Cases
	
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
	
	@RequestMapping("/testsparql")
	public @ResponseBody String  testSparql(){
		MobivocTest mobivocTester = new MobivocTest();
		return mobivocTester.testSparql();
	}
	
	@RequestMapping("/testallfs")
	public @ResponseBody String  getAllFs(){
		MobivocTest mobivocTester = new MobivocTest();
		//mobivocDAO.createFillingStationInstances();
		return mobivocTester.getAllFs();
	}
}
