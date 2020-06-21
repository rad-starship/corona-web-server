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
	private final String settingsServiceUri		= nmsAccessServiceUri + "settings";
    private final String rolesidServiceUri		= nmsAccessServiceUri + "rolesid";
    private final String loginUri				= nmsAccessServiceUri + "getToken";
	private final String logioutUri				= nmsAccessServiceUri + "logout";
	private final String tenantsServiceUri		= nmsAccessServiceUri + "tenants";

	@Autowired
	private KeycloakRestTemplate keycloakRestTemplate;
	
	private boolean isToUseKeycloakRestTemplate = true;

	public Object getUsers(HttpHeaders headers)
	{
		return getForEntity(usersServiceUri,headers);
	}

	public Object getUser(long id, HttpHeaders headers){
		return getForEntity(usersServiceUri+"/"+id,headers);
		//return id;
	}

	public Object getSettings(HttpHeaders headers)
	{
		return getForEntity(nmsAccessServiceUri+"getSettings",headers);
	}

	public Object getUserToken(HttpHeaders headers)
	{
		return getForEntity(usersServiceUri+"/getToken",headers);
	}

	public Object getTenantsForCorona(HttpHeaders headers){
		return getForEntity(tenantsServiceUri+"/tenantsForCorona",headers);
	}

	public Object getRoles(HttpHeaders headers)
	{
		return getForEntity(rolesServiceUri,headers);
	}

	public Object getTenants(HttpHeaders headers)
	{
		return getForEntity(tenantsServiceUri,headers);
	}

	public Object getPermissions(HttpHeaders headers)
	{
		return getForEntity(permissionsServiceUri,headers);
	}

	public Object addUser(Object user, HttpHeaders headers)
	{
		return postForEntity(usersServiceUri, user,headers);
	}



	public Object addRole(Object role, HttpHeaders headers)

	{
		return postForEntity(rolesServiceUri, role,headers);
	}

	public Object addTenant(Object tenant, HttpHeaders headers)
	{
		return postForEntity(tenantsServiceUri, tenant,headers);
	}

	public Object deleteUser(long id, HttpHeaders headers){
		return deleteForEntity(usersServiceUri+"/"+id,headers);
		//return id;
	}

	@Override
	public Object deleteTenant(long id, HttpHeaders headers) {
		return deleteForEntity(tenantsServiceUri+"/"+id,headers);
		//return id;
	}


	@Override
    public Object deleteRole(long roleId, HttpHeaders headers) {
        return deleteForEntity(rolesidServiceUri+"/"+roleId,headers);

    }


    @Override
	public Object deleteRole(String roleName, HttpHeaders headers)
	{
		  return deleteForEntity(rolesServiceUri+"/"+roleName,headers);
	}



    @Override
    public Object updateRole(Long roleId, Object roleDetailes, HttpHeaders headers) {
        return putForEntity(rolesServiceUri+"/"+roleId,roleDetailes,headers);
    }

	public Object updateUser(Long id, Object user, HttpHeaders headers){
		return putForEntity(usersServiceUri+"/"+id,user,headers);
	}

	@Override
	public Object updateTenant(Long id, Object tenant, HttpHeaders headers) {
		return putForEntity(tenantsServiceUri+"/"+id,tenant,headers);
	}



	@Override
    public Object postSettings(Object settings, HttpHeaders headers){
		return postForEntity(settingsServiceUri,settings,headers);
	}

	@Override
	public Object login(Object loginDetails) {

		return noKcPostForEntity(loginUri,loginDetails);
	}

	@Override
	public Object logout(Object refreshToken, HttpHeaders headers) {
		return postForEntity(logioutUri,refreshToken,headers);
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

				return new ResponseEntity<Object>(e.getResponseBodyAsString(),e.getStatusCode());
			}

			return new ErrorResponse("Unknown Error").getBody();
		}
		else
		{
			ResponseEntity<ArrayList> response = new RestTemplate().getForEntity(url, ArrayList.class);
		    return response.getBody();
		}
	}

	private Object getForEntity(String url, HttpHeaders headers) {
		try {
			HttpEntity<Object> requestUpdate = new HttpEntity<>(headers);
			ResponseEntity<Object> response = new RestTemplate().exchange(url, HttpMethod.GET, requestUpdate, Object.class);
			return response.getBody();
		}
		catch (HttpClientErrorException e) {

			return new ResponseEntity<Object>(e.getResponseBodyAsString(),e.getStatusCode());
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

				return ErrorResponse.buildErrorResponse(e);
			}

			return new ErrorResponse("Unknown Error").getBody();
		}
		else
		{
			return noKcPostForEntity(url, request);
		}
	}

	private Object postForEntity(String url,Object request, HttpHeaders headers) {
		try {
			HttpEntity<Object> requestUpdate = new HttpEntity<>(request, headers);
			ResponseEntity<Object> response = new RestTemplate().exchange(url, HttpMethod.POST, requestUpdate, Object.class);
			return response.getBody();
		}
		catch (HttpClientErrorException e) {

			return ErrorResponse.buildErrorResponse(e);
		}



	}


	private Object noKcPostForEntity(String url, Object request){
		try {
			ResponseEntity<Object> response = new RestTemplate().postForEntity(url, request, Object.class);
			return response.getBody();
		}
		catch (HttpClientErrorException e) {

			return ErrorResponse.buildErrorResponse(e);
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

	private Object putForEntity(String url,Object request, HttpHeaders headers) {
		try {
			HttpEntity<Object> requestUpdate = new HttpEntity<>(request, headers);
			ResponseEntity<Object> response = new RestTemplate().exchange(url, HttpMethod.PUT, requestUpdate, Object.class);
			return response.getBody();
		}
		catch (HttpClientErrorException e) {

			return ErrorResponse.buildErrorResponse(e);
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

			catch(HttpClientErrorException e){
				return ErrorResponse.buildErrorResponse(e);
			}
		}
		else
		{
			new RestTemplate().delete(url);
		}
		return null;
	}

	private Object deleteForEntity(String url,HttpHeaders headers)
	{
		try {
			HttpEntity<Object> requestUpdate = new HttpEntity<>(headers);
			ResponseEntity<Object> response = new RestTemplate().exchange(url, HttpMethod.DELETE, requestUpdate, Object.class);
			return response;
		}
		catch (HttpClientErrorException e) {
			return ErrorResponse.buildErrorResponse(e);
		}
	}


}