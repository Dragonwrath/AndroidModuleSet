package com.joker.http.okhttp.manager;

import com.joker.http.core.manager.HttpManager;
import com.joker.http.core.manager.ProgressCallback;
import com.joker.http.core.manager.ResponseCallback;
import com.joker.http.core.header.HttpConfig;
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
import okhttp3.ResponseBody;

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

 @Override
 public void enqueue(Request request,final ProgressCallback<Response> callback){
  OkHttpClient client=mClientRef.get();
  client.newCall(request).enqueue(new Callback(){
   private long contentLength;
   private long totalLength=-1L;
   @Override
   public void onFailure(Call call,IOException e){
    callback.onFailure(e);
   }

   @Override
   public void onResponse(Call call,Response response) throws IOException{
    String totalLength=response.header("content-length");
    if(this.totalLength==-1L&&totalLength!=null){
     try{
      this.totalLength=Long.parseLong(totalLength);
     } catch(NumberFormatException e){
      //noting
     }
    }
    ResponseBody body=response.body();
    if(body!=null){
     contentLength+=body.bytes().length;
     System.out.println("totalLength--"+this.totalLength+"-----body---"+contentLength);
    }
    if(contentLength<this.totalLength){
     callback.onProgress(response,contentLength*1D/this.totalLength);
    } else if(this.totalLength>-1){
     callback.onSuccess(response);
    }
   }
  });
 }

 private final static class Holder{
  private final static OkHttpClient.Builder BUILDER=new OkHttpClient.Builder()
    .connectTimeout(HttpConfig.getConnectTimeOut(),TimeUnit.SECONDS)
    .readTimeout(HttpConfig.getReadTimeOut(),TimeUnit.SECONDS)
    .writeTimeout(HttpConfig.getWriteTimeOut(),TimeUnit.SECONDS)
    .retryOnConnectionFailure(true)
    .addInterceptor(new TokenInterceptor(HttpConfig.getUserToken()))
    .addInterceptor(new RetryInterceptor())
//    .hostnameVerifier(new CjmNameVerifier())
    ;
 }

 static{
  mClientRef.set(Holder.BUILDER.build());
 }

// private OkHttpManagerImpl(){
//  throw new AssertionError("This class can not be instantiated!");
// }
}
