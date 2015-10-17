package org.rdf2salesforce.controller;

import java.util.List;

import org.rdf2salesforce.model.Contact;
import org.rdf2salesforce.model.CreateResponse;
import org.rdf2salesforce.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class ContactController {

	@Autowired
	ContactService contactService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Contact getContact(@PathVariable(value = "id") String id,
			@RequestParam(value = "token") String token,
			@RequestParam(value = "instance") String instance) {
		return contactService.getContact(id, token, instance);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Contact> getAll(@RequestParam(value = "token") String token,
			@RequestParam(value = "instance") String instance) {
		return contactService.getAll(token, instance);
	}

	@RequestMapping(value = "/rdf/{id}", method = RequestMethod.GET, produces = { "application/xml" })
	public String getContactAsRdf(@PathVariable(value = "id") String id,
			@RequestParam(value = "token") String token,
			@RequestParam(value = "instance") String instance) {
		return contactService.getContact(id, token, instance).toRdf();
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public CreateResponse createContact(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "instance") String instance,
			@RequestBody Contact contact) {
		return contactService.createContact(contact, token, instance);
	}

	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public void deleteContact(@PathVariable(value = "id") String id,
			@RequestParam(value = "token") String token,
			@RequestParam(value = "instance") String instance) {
		contactService.deleteContact(id, token, instance);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public void updateContact(@PathVariable(value = "id") String id,
			@RequestParam(value = "token") String token,
			@RequestParam(value = "instance") String instance,
			@RequestBody Contact contact) {
		contactService.updateContact(contact, token, instance);
	}

}
