package com.rad.server.web.corona.responses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse implements Response {

    private Map<String,String> response;

    public ErrorResponse(String massege){
        response = new HashMap<>();
        response.put("Error",massege);
    }

    @Override
    public Map<String, String> getBody() {
        return response;
    }

    /**
     * The function recives client exception and buildes response at same type of the exception
     * @param e - the eaception causes by 404/401 Http request from nms/data
     * @return responseEntity that fits to the exception.
     */
    public static Object buildErrorResponse(HttpClientErrorException e) {
        Map<String,String> error= new HashMap<>();
        error.put("Error",e.getResponseBodyAsString());
        if(e.getStatusCode().value() == 401){
            error.put("Error","Unauthorized");
        }
        return new ResponseEntity<Object>(error,e.getStatusCode());
    }
}

