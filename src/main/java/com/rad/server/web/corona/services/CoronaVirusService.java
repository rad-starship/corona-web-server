package com.rad.server.web.corona.services;

public interface CoronaVirusService
{
	Object getCoronaVirusData(String tenant);

    Object getTotalsLatest();

    Object getTotalsDaily(String date);

    Object getCountriesLatest();

    Object getCountryDaily(long date, String countryName);

}