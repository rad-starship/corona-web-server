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
	private final String coronaVirusServiceUri = "http://localhost:8082/corona";
	private final String totalLatestUri = "http://localhost:8082/totalsLatest";
	private final String totalDailyUri = "http://localhost:8082/totalsDaily";
	private final String countryLatestUri = "http://localhost:8082/countriesLatest";
	private final String countryDailyUri = "http://localhost:8082/countryDaily";

    
	@Autowired
	private KeycloakRestTemplate keycloakRestTemplate;
	private boolean isToUseKeycloakRestTemplate = true;





	public Object getCoronaVirusData(String tenant)
	{
		return getForEntity(coronaVirusServiceUri+"/"+tenant);
	}

	@Override
	public Object getTotalsLatest() {
		return getForEntity(totalLatestUri);
	}

	@Override
	public Object getTotalsDaily(String date) {
		return getForEntity(totalDailyUri+"/?date="+date);
	}

	@Override
	public Object getCountriesLatest() {
		return getForEntity(countryLatestUri);
	}

	@Override
	public Object getCountryDaily(long date, String countryName) {
		return getForEntity(countryDailyUri+"/?date="+String.valueOf(date)+"&countryName="+countryName);
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
                    return new ErrorResponse("No permission to corona_read").getBody();
                }

            }

		    catch (Exception e){
		    	e.printStackTrace();
				return new ErrorResponse("Unknown Error").getBody();
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

