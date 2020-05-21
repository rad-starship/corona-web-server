package com.rad.server.web.corona.services;

import org.springframework.http.HttpHeaders;

public interface CoronaVirusService
{
	Object getCoronaVirusData(String tenant, HttpHeaders headers);

    Object getTotalsLatest(HttpHeaders headers);

    Object getTotalsDaily(String date, HttpHeaders headers);

    Object getCountriesLatest(HttpHeaders headers);

    Object getCountryDaily(long date, String countryName, HttpHeaders headers);

}