package org.rdf2salesforce.controller;

import org.rdf2salesforce.AccessToken;
import org.rdf2salesforce.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	@Autowired
	LoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public AccessToken login(@RequestParam(value = "client_id") String clientId,
			@RequestParam(value = "client_secret") String clientSecret,
			@RequestParam(value = "username") String clientUsername,
			@RequestParam(value = "password") String clientPassword) {
		AccessToken token = loginService.login(clientId, clientSecret,
				clientUsername, clientPassword);
		return token;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void revokeToken(@RequestParam(value = "token") String token) {
		loginService.revokeToken(token);
	}
	
}
