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
	private final String nmsAccessServiceUri	= "http://localhost:8084/";
	private final String usersServiceUri		= nmsAccessServiceUri + "users";
	private final String rolesServiceUri		= nmsAccessServiceUri + "roles";
	private final String permissionsServiceUri		= nmsAccessServiceUri + "permissions";
	private final String settingsServiceUri		= nmsAccessServiceUri + "settings";
    private final String rolesidServiceUri		= nmsAccessServiceUri + "rolesid";
    private final String loginUri				= nmsAccessServiceUri + "getToken";
	private final String tenantsServiceUri		= nmsAccessServiceUri + "tenants";

	@Autowired
	private KeycloakRestTemplate keycloakRestTemplate;
	
	private boolean isToUseKeycloakRestTemplate = true;

	public Object getUsers()
	{
		return getForEntity(usersServiceUri);	
	}
	public Object getUserToken()
	{
		return getForEntity(usersServiceUri+"/getToken");
	}

	public Object getTenantsForCorona(){
		return getForEntity(tenantsServiceUri+"/tenantsForCorona");
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
		return deleteForEntity(usersServiceUri+"/"+id);
		//return id;
	}

	@Override
	public Object deleteTenant(long id) {
		return deleteForEntity(tenantsServiceUri+"/"+id);
		//return id;
	}


	@Override
    public Object deleteRole(long roleId) {
        return deleteBy(rolesidServiceUri,"id",String.valueOf(roleId));

    }


    @Override
	public Object deleteRole(String roleName) {
		  return deleteBy(rolesServiceUri,"name",roleName);
	}


	private Object deleteBy(String url, String type, String value) {
		return  delete(url+"/{"+type+"}",type,value);
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



	@Override
    public Object postSettings(Object settings){
		return postForEntity(settingsServiceUri,settings);
	}

	@Override
	public Object login(Object loginDetails) {
		return noKcPostForEntity(loginUri,loginDetails);
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
				System.out.println("Post to url: "+url);
				System.out.println(request);
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
			return noKcPostForEntity(url, request);
		}
	}

	private Object noKcPostForEntity(String url, Object request){
		ResponseEntity<Object> response = new RestTemplate().postForEntity(url, request, Object.class);
		return response.getBody();
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


    private Object deleteForEntity(String url)
	{
		if (isToUseKeycloakRestTemplate)
		{
			//keycloakRestTemplate.delete(url);
			try {
				ResponseEntity<Object> result = keycloakRestTemplate.exchange(url, HttpMethod.DELETE, null, Object.class);
				return result;
			}

			catch(HttpClientErrorException exception){
				return new ResponseEntity<String>(exception.getResponseBodyAsString(),exception.getStatusCode());
			}
		}
		else
		{
			new RestTemplate().delete(url);
		}
		return null;
	}

	private Object delete(String url, String type, String name) {
		Map < String, String > params = new HashMap < String, String > ();
		params.put(type, name);

		if (isToUseKeycloakRestTemplate)
		{
			try {
				ResponseEntity<Object> result = keycloakRestTemplate.exchange(url, HttpMethod.DELETE, null, Object.class, params);
				return result;
			}
			catch (HttpClientErrorException exception){
				return new ResponseEntity<String>(exception.getResponseBodyAsString(),exception.getStatusCode());
			}
		}
		else
		{
			new RestTemplate().delete(url,params);
		}
		return null;
	}


}