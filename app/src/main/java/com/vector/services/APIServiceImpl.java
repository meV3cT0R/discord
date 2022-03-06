package com.vector.services;

import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;



import com.jsoniter.JsonIterator;
import com.vector.api.API;



import org.asynchttpclient.Response;

public class APIServiceImpl implements APIService{

    @Override
    public <T> T getAPIResponse(Class<T> clazz, String url,Map<? extends CharSequence, ?> headers) {
        Response response =null;
        try {
            response = API.getResponse(url,headers);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert response !=null;
        System.out.println(response.getResponseBody());
        T responseData = JsonIterator.deserialize(response.getResponseBody(), clazz);
        return responseData;
    }

    @Override
    public <T> List<T> getAPIResponseInList(Class<T> clazz,String url,Map<? extends CharSequence,?> headers){
        Response response =null;
        try {
            response = API.getResponse(url,headers);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert response !=null;
        
        String responseBody = response.getResponseBody();
        responseBody = responseBody.substring(1,responseBody.length()-1);
        
        List<T> responseData = Arrays.asList(JsonIterator.deserialize(responseBody, clazz));
        return responseData;
    }

    @Override
    public <T> T getAPIResponse(Class<T> clazz, String url) {
        
        return getAPIResponse(clazz, url, new HashMap<String,String>());
    }
    
}
