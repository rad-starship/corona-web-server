package com.rad.server.web.corona.services;

import java.util.*;

import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springsecurity.client.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import com.google.gson.*;

/**
 * @author raz_o
 */
@Service
public class CoronaVirusServiceImpl implements CoronaVirusService
{
	private final String coronaVirusServiceUri = "http://localhost:8082/corona";
    
	@Autowired
	private KeycloakRestTemplate keycloakRestTemplate;
	
	private boolean isToUseKeycloakRestTemplate = true;




	public Object getCoronaVirusData()
	{
		return getForEntity(coronaVirusServiceUri);
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
}

