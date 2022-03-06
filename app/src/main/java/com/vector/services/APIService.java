package com.vector.services;


import java.util.List;
import java.util.Map;

public interface APIService {
    <T> T getAPIResponse(Class<T> clazz,String url);
    <T> T getAPIResponse(Class<T> clazz,String url,Map<?extends CharSequence,?> headers);
    <T> List<T> getAPIResponseInList(Class<T> clazz,String url,Map<?extends CharSequence,?> headers);

}
