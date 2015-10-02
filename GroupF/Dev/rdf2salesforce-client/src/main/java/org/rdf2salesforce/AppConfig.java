package org.rdf2salesforce;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
	
	@Value("${salesforce.revoke_token}")
	public String SALESFORCE_REVOKE_TOKEN;
	@Value("${client.id}")
	public String CLIENT_ID;
	@Value("${client.secret}")
	public String CLIENT_SECRET;
	@Value("${client.username}")
	public String USERNAME;
	@Value("${client.password}")
	public String PASSWORD;
	@Value("${login.url}")
	public String LOGIN_URL;
	@Value("${logout.url}")
	public String LOGOUT_URL;

	
	@Value("${query.base}")
	public String QUERY_BASE;
	@Value("${query.contact}")
	public String QUERY_CONTACT;
	@Value("${query.contact.all}")
	public String QUERY_CONTACT_ALL;
	
	
	

}
