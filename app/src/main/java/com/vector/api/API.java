package com.vector.api;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import static org.asynchttpclient.Dsl.asyncHttpClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
public class API {
    public static Response getResponse(String url) throws InterruptedException, ExecutionException {
        AsyncHttpClient client = asyncHttpClient();
        Future<Response> whenResponse = client.prepare("GET", url).execute();
        return whenResponse.get();
    }
}
