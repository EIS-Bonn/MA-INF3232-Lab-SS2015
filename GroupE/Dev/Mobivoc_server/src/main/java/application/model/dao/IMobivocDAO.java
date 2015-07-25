package application.model.dao;

import java.util.List;

import application.model.model.FillingStation;

public interface IMobivocDAO {
	List<FillingStation> getFillingStation();
	void createFillingStationInstances();
	List<String> getFuelStation();
	List<String> getChargingStation();
	List<String> getFillingStation(String longtitute, String latitute);
	
}