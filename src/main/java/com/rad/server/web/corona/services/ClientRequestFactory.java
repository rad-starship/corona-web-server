package com.rad.server.web.corona.services;

import org.keycloak.adapters.springsecurity.client.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

@Service
public class ClientRequestFactory
{
	@Autowired
	KeycloakClientRequestFactory keycloakClientRequestFactory;

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public KeycloakClientRequestFactory KeycloakClientRequestFactory()
	{
		return new KeycloakClientRequestFactory();
	}	
	
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public KeycloakRestTemplate keycloakRestTemplate()
	{
		return new KeycloakRestTemplate(keycloakClientRequestFactory);
	}	
}
