package application.model.model;

import java.util.LinkedList;
import java.util.List;

public class FillingStationHeader {

	private List<FillingStation> fillingStation;
	
	public List<FillingStation> getFillingStationList(){
		return this.fillingStation;
	}
	
	public void setFillingStationList(List<FillingStation> fillingStation){
		this.fillingStation = fillingStation;
	}
}
