package com.vector.services;

import java.util.concurrent.ExecutionException;

import com.jsoniter.JsonIterator;
import com.vector.api.API;

import org.asynchttpclient.Response;

public class APIServiceImpl implements APIService{

    @Override
    public <T> T getAPIResponse(Class<T> clazz, String url) {
        Response response =null;
        try {
            response = API.getResponse(url);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert response !=null;
        T responseData = JsonIterator.deserialize(response.getResponseBody(), clazz);
        return responseData;
    }
    
}
