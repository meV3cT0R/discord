package com.vector.api;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import static org.asynchttpclient.Dsl.asyncHttpClient;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
public class API {
    public static Response getResponse(String url) throws InterruptedException, ExecutionException {
    AsyncHttpClient client = asyncHttpClient();
        ListenableFuture<Response> whenResponse = client.prepare("GET", url).execute();
        AtomicReference<Response> response = new AtomicReference<>();
        whenResponse.addListener(()->{
            try {
                response.set(whenResponse.get());
            } catch (InterruptedException | ExecutionException e) {
                 
                e.printStackTrace();
            }
        },runnable->new Thread(runnable).start());
        return response.get();
    }

    public static Response getResponse(String url,Map<? extends CharSequence,?> headers) throws InterruptedException, ExecutionException {
        AsyncHttpClient client = asyncHttpClient();
            Future<Response> whenResponse = client.prepare("GET", url).setSingleHeaders(headers).execute();
            return whenResponse.get();
        }
}
