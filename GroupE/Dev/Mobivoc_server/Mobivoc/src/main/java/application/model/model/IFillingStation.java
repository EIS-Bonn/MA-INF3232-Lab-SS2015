package application.model.model;

public interface IFillingStation {

	
	String getFillingStationNumber();
	String getFillingStationHeight();
	String getOrganizationName();
	String getCountryName();
	String getLocality();
	String getPostalCode();
	String getRegion();
	String getStreetAddress();
	String getTel();
	String getURL();
	String getLong();
	String getLat();

	
	
	void setFillingStationNumber(String fillingStationNumber);
	void setFillingStationHeight(String fillingStationHeight);
	void setOrganizationName(String organizationName);
	void setCountryName(String countryName);
	void setLocality(String locality);
	void setPostalCode(String postalCode);
	void setRegion(String region);
	void setStreetAddress(String streetAddress);
	void setTel(String tel);
	void setURL(String URL);
	void setLong(String longtitute);
	void setLat(String latitute);
	
}
