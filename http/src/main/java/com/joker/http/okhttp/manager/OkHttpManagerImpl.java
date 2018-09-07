package com.joker.http.okhttp.manager;

import com.joker.http.core.manager.HttpManager;
import com.joker.http.core.manager.ResponseCallback;
import com.joker.http.core.user.HttpConfig;
import com.joker.http.okhttp.interceptors.RetryInterceptor;
import com.joker.http.okhttp.interceptors.TokenInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpManagerImpl implements HttpManager<Request,Response>{
 private final static AtomicReference<OkHttpClient> mClientRef=new AtomicReference<>();

 @Override
 public Response enqueue(Request request) throws IOException{
  return mClientRef.get().newCall(request).execute();
 }

 @Override
 public void enqueue(Request request,final ResponseCallback<Response> callback){
  OkHttpClient client=mClientRef.get();
  client.newCall(request).enqueue(new Callback(){
   @Override
   public void onFailure(Call call,IOException e){
    callback.onFailure(e);
   }

   @Override
   public void onResponse(Call call,Response response) throws IOException{
    callback.onSuccess(response);
   }
  });
 }

 private final static class Holder{
  private final static OkHttpClient.Builder BUILDER=new OkHttpClient.Builder()
    .connectTimeout(HttpConfig.getConnectTimeOut(),TimeUnit.SECONDS)
    .readTimeout(HttpConfig.getReadTimeOut(),TimeUnit.SECONDS)
    .writeTimeout(HttpConfig.getWriteTimeOut(),TimeUnit.SECONDS)
    .retryOnConnectionFailure(true)
    .addInterceptor(new RetryInterceptor())
    .addInterceptor(new TokenInterceptor(HttpConfig.getUserToken()))

//    .hostnameVerifier(new CjmNameVerifier())
    ;
 }

 static{
  mClientRef.set(Holder.BUILDER.build());
 }

 private OkHttpManagerImpl(){
  throw new AssertionError("This class can not be instantiated!");
 }




}
