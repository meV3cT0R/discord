package com.vector.services;

public interface APIService {
    <T> T getAPIResponse(Class<T> clazz,String url);
}
