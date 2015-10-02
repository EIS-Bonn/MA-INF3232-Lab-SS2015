package org.rdf2salesforce.services;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.rdf2salesforce.config.AppConfig;
import org.rdf2salesforce.model.Contact;
import org.rdf2salesforce.model.ContactResponse;
import org.rdf2salesforce.model.CreateResponse;
import org.rdf2salesforce.model.Odette;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;

@Service
public class ContactService {

	@Autowired
	private AppConfig appConfig;

	private static Logger LOGGER = LoggerFactory
			.getLogger(ContactService.class);

	public List<Contact> getAll(String token, String instance) {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(
						"https://" + instance + ".salesforce.com"
								+ appConfig.QUERY_BASE).queryParam("q",
						appConfig.QUERY_CONTACT_ALL);
		HttpHeaders headers = createHeaders(token);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<ContactResponse> exchange = restTemplate.exchange(
				builder.build().encode().toUri(), HttpMethod.GET, entity,
				ContactResponse.class);
		return exchange.getBody().getRecords();
	}

	public Contact getContact(String contactId, String token, String instance) {
		ResponseEntity<Contact> exchange = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl("https://" + instance + ".salesforce.com"
							+ appConfig.QUERY_CONTACT + contactId);
			HttpHeaders headers = createHeaders(token);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			exchange = restTemplate.exchange(builder.build().encode().toUri(),
					HttpMethod.GET, entity, Contact.class);
		} catch (HttpClientErrorException httpError) {
			LOGGER.error(httpError.getMessage());
			exchange = new ResponseEntity<Contact>(new Contact(),
					httpError.getStatusCode());
		}
		return exchange.getBody();
	}
	
	public CreateResponse createContact(Contact contact, String token, String instance) {
		ResponseEntity<CreateResponse> exchange = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl("https://"+instance+".salesforce.com"
							+ appConfig.QUERY_CONTACT);
			HttpHeaders headers = createHeaders(token);
			HttpEntity<Contact> entity = new HttpEntity<>(contact, headers);
			exchange = restTemplate.exchange(builder.build().encode().toUri(),
					HttpMethod.POST, entity, CreateResponse.class);

		} catch (HttpClientErrorException httpError) {
			LOGGER.error(httpError.getMessage());
			exchange = new ResponseEntity<CreateResponse>(new CreateResponse(),
					httpError.getStatusCode());
		}
		return exchange.getBody();
	}

	public String updateContact(Contact newContact, String token,
			String instance) {
		ResponseEntity<String> exchange = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
					"https://"+instance+".salesforce.com" + appConfig.QUERY_CONTACT
							+ newContact.getId()).queryParam("_HttpMethod",
					"PATCH");
			HttpHeaders headers = createHeaders(token);
			Contact oldContact = this.getContact(newContact.getId(), token,
					instance);
			Contact diffContact = this.getDiffContact(oldContact, newContact);

			HttpEntity<Contact> entity = new HttpEntity<>(diffContact, headers);
			URI uri = builder.build().encode().toUri();
			exchange = restTemplate.exchange(uri, HttpMethod.POST, entity,
					String.class);

		} catch (HttpClientErrorException httpError) {
			LOGGER.error(httpError.getMessage());
			exchange = new ResponseEntity<String>(new String(),
					httpError.getStatusCode());
		}
		return exchange.getBody();
	}

	/**
	 * In order to patch a Contact we have insert only the information that has
	 * changed. Therefore we need to find all the fields in the new contact that
	 * are different from the old version of this contact.
	 * 
	 * The method iterates over all {@code getDeclaredFields()} of
	 * {@link Contact} and store the differences in a separate result object.
	 */
	private Contact getDiffContact(Contact oldContact, Contact newContact) {
		Contact result = new Contact();
		Field[] fields = Contact.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				Object oldProperty = PropertyUtils.getProperty(oldContact,
						field.getName());
				Object newProperty = PropertyUtils.getProperty(newContact,
						field.getName());
				// introduced new field value that was null before
				if (newProperty != null && oldProperty == null) {
					PropertyUtils.setProperty(result, field.getName(),
							newProperty.toString());
					continue;
				}
				// changed old value to new value
				if (newProperty != null && oldProperty != null
						&& !newProperty.equals(oldProperty)) {
					PropertyUtils.setProperty(result, field.getName(),
							newProperty.toString());
				}
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				// TODO Auto-generated catch block
				LOGGER.error(e.getMessage());
			}
		}
		return result;
	}

	public void deleteContact(String contactId, String token, String instance) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl("https://"+instance+".salesforce.com"
							+ appConfig.QUERY_CONTACT + contactId);
			HttpHeaders headers = createHeaders(token);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			restTemplate.exchange(builder.build().encode().toUri(),
					HttpMethod.DELETE, entity, String.class);
		} catch (HttpClientErrorException httpError) {
			LOGGER.error(httpError.getMessage());
		}
	}

	private HttpHeaders createHeaders(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Bearer" + " " + token);
		return headers;
	}

	public ArrayList<Contact> createFromRdf() {
		FileManager.get().readModel(Odette.getModel(), "example-eccenca.ttl");
		ArrayList<Contact> resultList = new ArrayList<>();
		StmtIterator stmtIter = Odette.getModel().listStatements(null,
				RDF.type, FOAF.Person);
		while (stmtIter.hasNext()) {
			Contact person = new Contact();
			Statement stmt = stmtIter.next();
			Resource subject = stmt.getSubject();
			StmtIterator subProperties = subject.listProperties();
			while (subProperties.hasNext()) {
				Statement stmtWithSubject = subProperties.next();
				String property = stmtWithSubject.getPredicate().toString();
				property = property.substring(property.lastIndexOf('/') + 1,
						property.length());
				RDFNode object = stmtWithSubject.getObject();
				person.set(property, object.isLiteral() ? object.asLiteral()
						.toString() : object.asResource().toString());
			}
			resultList.add(person);
		}
		return resultList;
	}



}
