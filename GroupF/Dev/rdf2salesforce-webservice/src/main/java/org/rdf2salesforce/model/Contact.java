package org.rdf2salesforce.model;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {

	@JsonProperty("AccountId")
	private String accountId;
	@JsonProperty("Department")
	private String department;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("Name")
	private String name;
	@JsonProperty("FirstName")
	private String givenName;
	@JsonProperty("LastName")
	private String familyName;
	@JsonProperty("MailingCity")
	private String mailingCity;
	@JsonProperty("MailingStreet")
	private String mailingStreet;
	@JsonProperty("MailingState")
	private String mailingState;
	@JsonProperty("MailingCountry")
	private String mailingCountry;
	@JsonProperty("MailingPostalCode")
	private String mailingPostalCode;
	@JsonProperty("MailingCountryCode")
	private String mailingCountryCode;
	@JsonProperty("AddressZip")
	private String addressZip;
	@JsonProperty("Phone")
	private String telephoneNumber;
	@JsonProperty("Fax")
	private String fax;
	@JsonProperty("MobilePhone")
	private String mobilePhone;
	@JsonProperty("Email")
	private String emailAdress;
	@JsonProperty("Id")
	private String id;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String firstName) {
		this.givenName = firstName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String lastName) {
		this.familyName = lastName;
	}

	public String getMailingCity() {
		return mailingCity;
	}

	public void setMailingCity(String mailingCity) {
		this.mailingCity = mailingCity;
	}

	public String getMailingStreet() {
		return mailingStreet;
	}

	public void setMailingStreet(String mailingStreet) {
		this.mailingStreet = mailingStreet;
	}

	public String getMailingState() {
		return mailingState;
	}

	public void setMailingState(String mailingState) {
		this.mailingState = mailingState;
	}

	public String getMailingCountry() {
		return mailingCountry;
	}

	public void setMailingCountry(String mailingCountry) {
		this.mailingCountry = mailingCountry;
	}

	public String getMailingPostalCode() {
		return mailingPostalCode;
	}

	public void setMailingPostalCode(String mailingPostalCode) {
		this.mailingPostalCode = mailingPostalCode;
	}

	public String getMailingCountryCode() {
		return mailingCountryCode;
	}

	public void setMailingCountryCode(String mailingCountryCode) {
		this.mailingCountryCode = mailingCountryCode;
	}

	public String getAddressZip() {
		return addressZip;
	}

	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String phone) {
		this.telephoneNumber = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void set(String fieldName, String value) {
		try {
			Class<?> c = this.getClass();

			Field field = c.getDeclaredField(fieldName);
			field.set(this, value);
		} catch (Exception ex) {
			// System.out.println(ex);
		}
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		try {
			Class<?> c = this.getClass();
			Field[] fields = c.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].get(this) != null) {
					stringBuilder.append(fields[i].getName() + ":"
							+ fields[i].get(this) + "\n");
				}
			}
		} catch (Exception ex) {
			// System.out.println(ex);
		}
		return stringBuilder.toString();
	}

	public String toRdf() {
		Model model = ModelFactory.createDefaultModel();
		Resource personResource = ResourceFactory.createResource("https://vocab.eccenca.com/mdm/"
				+ this.getFamilyName());
		model.add(personResource, RDF.type, FOAF.Person)
				.add(personResource, FOAF.givenname, this.getGivenName())
				.add(personResource, FOAF.family_name, this.getFamilyName());
		OutputStream outputStream = new ByteArrayOutputStream();
		model.write(outputStream);
		return outputStream.toString();
	}

}
