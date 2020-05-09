package com.rad.server.web.corona.services;

import java.util.*;

import com.rad.server.web.corona.responses.ErrorResponse;
import org.keycloak.adapters.springsecurity.client.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

/**
 * @author raz_o
 */
@Service
public class CoronaVirusServiceImpl implements CoronaVirusService
{
	private final String coronaVirusServiceUri = "http://localhost:8081/corona";
    
	@Autowired
	private KeycloakRestTemplate keycloakRestTemplate;
	private boolean isToUseKeycloakRestTemplate = true;





	public Object getCoronaVirusData(String tenant)
	{
		return getForEntity(coronaVirusServiceUri+"/"+tenant);
	}
	
	@SuppressWarnings("rawtypes")
	private Object getForEntity(String url)
	{
		if (isToUseKeycloakRestTemplate)
		{

		    try {
                ResponseEntity<ArrayList> response = keycloakRestTemplate.getForEntity(url, ArrayList.class);

                if (response.getStatusCode().value() == 200) {
                    return response.getBody();
                }
            }
		    catch (HttpClientErrorException e) {
                if (e.getStatusCode().value() == 403) {
                    return new ErrorResponse("No permission to corona_read").getBody();
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
}

