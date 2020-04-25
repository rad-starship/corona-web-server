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
	private final String usersServiceUri		= nmsAccessServiceUri + "users";
	private final String rolesServiceUri		= nmsAccessServiceUri + "roles";
	private final String tenantsServiceUri		= nmsAccessServiceUri + "tenants";

	@Autowired
	private KeycloakRestTemplate keycloakRestTemplate;
	
	private boolean isToUseKeycloakRestTemplate = true;

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

	public Object deleteUser(long id){
		deleteForEntity(usersServiceUri+"/"+id);
		return id;
	}


	@Override
	public void deleteRole(String roleName) {
		 delete(rolesServiceUri+"/{name}",roleName);
	}

	public Object updateUser(Long id,Object user){
		return deleteForEntity();
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
			ResponseEntity<Object> response = keycloakRestTemplate.postForEntity(url, request, Object.class);
		    return response.getBody();
		}
		else
		{
			ResponseEntity<Object> response = new RestTemplate().postForEntity(url, request, Object.class);
		    return response.getBody();
		}
	}


	private void deleteForEntity(String url)
	{
		if (isToUseKeycloakRestTemplate)
		{
			keycloakRestTemplate.delete(url);
		}
		else
		{
			new RestTemplate().delete(url);
		}
	}

	private void delete(String url, String name) {
		Map < String, String > params = new HashMap < String, String > ();
		params.put("name", name);

		if (isToUseKeycloakRestTemplate)
		{
			keycloakRestTemplate.delete(url,params);

		}
		else
		{
			new RestTemplate().delete(url,params);
		}
	}
}