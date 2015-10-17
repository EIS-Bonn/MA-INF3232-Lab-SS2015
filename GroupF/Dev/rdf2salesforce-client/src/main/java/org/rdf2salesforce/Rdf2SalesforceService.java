package org.rdf2salesforce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class Rdf2SalesforceService {

	@Autowired
	AppConfig appConfig;

	private static Logger LOGGER = LoggerFactory
			.getLogger(Rdf2SalesforceService.class);

	public AccessToken login() {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(appConfig.LOGIN_URL)
				.queryParam("client_id", appConfig.CLIENT_ID)
				.queryParam("client_secret", appConfig.CLIENT_SECRET)
				.queryParam("username", appConfig.USERNAME)
				.queryParam("password", appConfig.PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<AccessToken> exchange = restTemplate.exchange(builder
				.build().encode().toUri(), HttpMethod.POST, entity,
				AccessToken.class);
		LOGGER.info(exchange.toString());
		return exchange.getBody();
	}

	public void revokeToken(String token) {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				appConfig.LOGOUT_URL).queryParam("token", token);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<String> exchange = restTemplate.exchange(builder.build()
				.encode().toUri(), HttpMethod.GET, entity, String.class);
		if (exchange.getStatusCode().equals(HttpStatus.OK)) {
			// everything is fine, TODO: handle errors
		}
	}

	public String getAll(String token, String instance) {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://localhost:8080/contact/")
				.queryParam("token", token).queryParam("instance", instance);
		HttpHeaders headers = createHeaders(token);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<String> exchange = restTemplate.exchange(builder.build()
				.encode().toUri(), HttpMethod.GET, entity, String.class);
		return exchange.getBody();
	}

	public String getContact(String contactId, String token, String instance) {
		ResponseEntity<String> exchange = null;
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://localhost:8080/contact/" + contactId)
				.queryParam("token", token).queryParam("instance", instance);
		HttpHeaders headers = createHeaders(token);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		exchange = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.GET, entity, String.class);
		return exchange.getBody();
	}

	public String getContactAsRdf(String contactId, String token,
			String instance) {
		ResponseEntity<String> exchange = null;
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://localhost:8080/contact/rdf/" + contactId)
				.queryParam("token", token).queryParam("instance", instance);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_XML_VALUE);
		headers.add("Content-Type", "application/xml");
		headers.set("Authorization", "Bearer" + " " + token);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		exchange = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.GET, entity, String.class);
		return exchange.getBody();
	}

	private HttpHeaders createHeaders(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Bearer" + " " + token);
		return headers;
	}

	public String createContact(String contact, String token, String instance) {
		RestTemplate restTemplate = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl("http://localhost:8080/contact/")
				.queryParam("token", token).queryParam("instance", instance);
		HttpHeaders headers = createHeaders(token);
		HttpEntity<String> entity = new HttpEntity<>(contact, headers);
		ResponseEntity<String> exchange = restTemplate.exchange(builder.build()
				.encode().toUri(), HttpMethod.POST, entity, String.class);

		return exchange.getBody();
	}

}
