package org.rdf2salesforce.services;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rdf2salesforce.AccessToken;
import org.rdf2salesforce.Application;
import org.rdf2salesforce.model.Contact;
import org.rdf2salesforce.model.CreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ContactServiceTest {

	@Autowired
	private ContactService contactService;
	@Autowired
	private LoginService loginService;
	private String token;
	private String instance;
	private Contact contact;

	@Before
	public void init() {
		AccessToken accessToken = loginService.getToken();
		token = accessToken.getAccessToken();
		// i.e. https://eu5.salesforce.com -> we remove https:// and pick the
		// first part of the url from the array ["eu5", "salesforce", "com"]
		instance = accessToken.getInstanceUrl().replace("https://", "").split("\\.")[0];
		contact = new Contact();
		contact.setFamilyName("Nash");
		contact.setGivenName("John");
	}

	@Test
	public void testGetAll() {
		List<Contact> allContacts = contactService.getAll(token, instance);
		assertTrue(allContacts.size() > 0);
	}

	@Test
	public void testGetContact() {
		List<Contact> allContacts = contactService.getAll(token, instance);
		assertTrue(allContacts.size() > 0);
		Contact firstContact = allContacts.get(0);
		assertTrue(firstContact.getId() != null);
		Contact responseContact = contactService.getContact(
				firstContact.getId(), token, instance);
		assertTrue(responseContact.getId().equals(firstContact.getId()));
	}

	@Test
	public void testCreateContact() {
		CreateResponse createResponse = contactService.createContact(contact,
				token, instance);
		assertTrue(createResponse.getId() != null);
	}

	@Test
	public void testUpdateContact() {
		List<Contact> allContacts = contactService.getAll(token, instance);
		assertTrue(allContacts.size() > 0);
		Contact firstContact = allContacts.get(0);
		assertTrue(firstContact.getId() != null);
		firstContact.setGivenName(firstContact.getName() + "new name part");
		contactService.updateContact(firstContact, token, instance);
		Contact updatedContact = contactService.getContact(
				firstContact.getId(), token, instance);
		assertTrue(firstContact.getGivenName().equals(
				updatedContact.getGivenName()));
	}

	@Test
	public void testDeleteContact() {
		CreateResponse createResponse = contactService.createContact(contact,
				token, instance);
		assertTrue(createResponse.getId() != null);
		contact.setId(createResponse.getId());
		contactService.deleteContact(contact.getId(), token, instance);
		Contact deletedContact = contactService.getContact(
				createResponse.getId(), token, instance);
		assertTrue(deletedContact.getName() == null);
	}

	@Test
	public void testCreateFromRdf() {
		ArrayList<Contact> contactsFromRdf = contactService.createFromRdf();
		assertTrue(contactsFromRdf.size() > 0);
	}
	
	@Test
	public void testWriteContactAsRdf(){
		System.out.println(contact.toRdf());
	}

}
