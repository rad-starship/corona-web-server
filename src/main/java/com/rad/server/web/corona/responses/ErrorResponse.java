package com.rad.server.web.corona.responses;

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
}
