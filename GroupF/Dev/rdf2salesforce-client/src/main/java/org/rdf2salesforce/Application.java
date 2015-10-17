package org.rdf2salesforce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	private static Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String args[]) {
		ConfigurableApplicationContext ctx = SpringApplication.run(
				Application.class, args);
		Rdf2SalesforceService service = ctx
				.getBean(Rdf2SalesforceService.class);
		AccessToken login = service.login();
		String instance = login.getInstanceUrl().replace("https://", "")
				.split("\\.")[0];
		String allContacts = service.getAll(login.getAccessToken(), instance);
		LOGGER.info(allContacts);
		String contact = service.getContact("0032400000BhDdzAAF", login.getAccessToken(), instance);
		LOGGER.info(contact);
		String rdfContact = service.getContactAsRdf("0032400000BhDdzAAF", login.getAccessToken(), instance);
		LOGGER.info(rdfContact);
		String newContact = new String("{\"Name\":\"John Nash\",\"FirstName\":\"John\",\"LastName\":\"Nash\",\"Email\":\"john.nash@example.com\"}");
		String createResponse = service.createContact(newContact, login.getAccessToken(), instance);
		LOGGER.info(createResponse);
		
	}

}