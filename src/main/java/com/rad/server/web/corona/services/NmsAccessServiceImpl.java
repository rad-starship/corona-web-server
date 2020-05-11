package com.rad.server.web.corona.services;

import java.util.*;

import com.rad.server.web.corona.responses.ErrorResponse;
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
	private final String permissionsServiceUri		= nmsAccessServiceUri + "permissions";
    private final String rolesidServiceUri		= nmsAccessServiceUri + "rolesid";
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

	public Object getPermissions()
	{
		return getForEntity(permissionsServiceUri);
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
	public Object deleteTenant(long id) {
		deleteForEntity(tenantsServiceUri+"/"+id);
		return id;
	}


	@Override
    public void deleteRole(long roleId) {
        deleteBy(rolesidServiceUri,"id",String.valueOf(roleId));

    }


    @Override
	public void deleteRole(String roleName) {
		 deleteBy(rolesServiceUri,"name",roleName);
	}

    @Override
    public Object updateRole(Long roleId, Object roleDetailes) {
        return putForEntity(rolesServiceUri+"/"+roleId,roleDetailes);
    }

	public Object updateUser(Long id,Object user){
		return putForEntity(usersServiceUri+"/"+id,user);
	}

	@Override
	public Object updateTenant(Long id, Object tenant) {
		return putForEntity(tenantsServiceUri+"/"+id,tenant);
	}

	private void deleteBy(String url,String type, String value) {
	    delete(url+"/{"+type+"}",type,value);
    }


    @SuppressWarnings("rawtypes")
	private Object getForEntity(String url)
	{
		if (isToUseKeycloakRestTemplate)
		{

			try {
				ResponseEntity<Object> response = keycloakRestTemplate.getForEntity(url, Object.class);

				if (response.getStatusCode().value() == 200) {
					return response.getBody();
				}
			}
			catch (HttpClientErrorException e) {
				if (e.getStatusCode().value() == 403) {
					return new ErrorResponse("No permission").getBody();
				}

			}

			return new ErrorResponse("Unknown Error").getBody();
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

			try {
				ResponseEntity<Object> response = keycloakRestTemplate.postForEntity(url, request, Object.class);
				if (response.getStatusCode().value() == 200) {
					return response.getBody();
				}
			}
			catch (HttpClientErrorException e) {
				if (e.getStatusCode().value() == 403) {
					return new ErrorResponse("No permission").getBody();
				}

			}

			return new ErrorResponse("Unknown Error").getBody();
		}
		else
		{
			ResponseEntity<Object> response = new RestTemplate().postForEntity(url, request, Object.class);
		    return response.getBody();
		}
	}

    private Object putForEntity(String url, Object request) {
        if (isToUseKeycloakRestTemplate)
        {
            keycloakRestTemplate.put(url, request);
            return request;
        }
        else
        {
            new RestTemplate().put(url, request);
            return request;
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

	private void delete(String url,String type, String name) {
		Map < String, String > params = new HashMap < String, String > ();
		params.put(type, name);

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