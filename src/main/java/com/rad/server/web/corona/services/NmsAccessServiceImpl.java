package com.rad.server.web.corona.services;

import java.util.*;
import org.keycloak.adapters.springsecurity.client.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

/**
 * Check out Spring's RestTemplate Guide
 * https://docs.spring.io/spring-android/docs/current/reference/html/rest-template.html	 
 */
@Service
public class NmsAccessServiceImpl implements NmsAccessService
{
	private final String nmsAccessServiceUri	= "http://localhost:8081/";
	private final String coronaServiceUri		= "http://localhost:8002/";
	private final String usersServiceUri		= nmsAccessServiceUri + "users";
	private final String rolesServiceUri		= nmsAccessServiceUri + "roles";
	private final String tenantsServiceUri		= nmsAccessServiceUri + "tenants";

	@Autowired
	private KeycloakRestTemplate keycloakRestTemplate;
	
	private boolean isToUseKeycloakRestTemplate = false;

	public Object getUsers()
	{
		return getForEntity(usersServiceUri);	
	}

	public Object getRoles()
	{
		return getForEntity(rolesServiceUri);	
	}

	public Object getTenants()
	{
		return getForEntity(tenantsServiceUri);	
	}

	public Object addUser(Object user)
	{
		return postForEntity(usersServiceUri, user);
	}

	public Object addRole(Object role)
	{
		return postForEntity(rolesServiceUri, role);	
	}

	public Object addTenant(Object tenant)
	{
		return postForEntity(tenantsServiceUri, tenant);
	}
		
	@SuppressWarnings("rawtypes")
	private Object getForEntity(String url)
	{
		if (isToUseKeycloakRestTemplate)
		{
			ResponseEntity<ArrayList> response = keycloakRestTemplate.getForEntity(url, ArrayList.class);
			return response.getBody();
		}
		else
		{
			ResponseEntity<ArrayList> response = new RestTemplate().getForEntity(url, ArrayList.class);
		    return response.getBody();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private Object postForEntity(String url, Object request)  
	{
		if (isToUseKeycloakRestTemplate)
		{
			ResponseEntity<ArrayList> response = keycloakRestTemplate.postForEntity(url, request, ArrayList.class);
		    return response.getBody();
		}
		else
		{
			ResponseEntity<Object> response = new RestTemplate().postForEntity(url, request, Object.class);
		    return response.getBody();
		}
	}
}