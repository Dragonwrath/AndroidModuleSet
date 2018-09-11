package com.joker.http.okhttp.manager;

import com.joker.http.core.header.HttpConfig;
import com.joker.http.core.manager.HttpManager;
import com.joker.http.core.manager.ResponseCallback;
import com.joker.http.core.manager.ResponseData;
import com.joker.http.okhttp.interceptors.RetryInterceptor;
import com.joker.http.okhttp.interceptors.TokenInterceptor;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpManagerImpl implements HttpManager<Request>{

 private final static AtomicReference<OkHttpClient.Builder> mClientRef=new AtomicReference<>();

 static{
  mClientRef.set(Holder.BUILDER);
 }

 private final static WeakHashMap<Object,Call> map=new WeakHashMap<>();

 @Override
 public <Response> void enqueue(Request request,Object tag,ResponseCallback<ResponseData<Response>> callback){
  OkHttpClient.Builder builder=mClientRef.get();
  if(builder!=null) {
   Call newCall=builder.build().newCall(request);
   map.put(tag,newCall);
   try{

   } finally{

   }
  }
 }

 @Override public void cancel(Object tag){
  Call call=map.get(tag);
  if(call!=null) {
   call.cancel();
   map.remove(tag);
  }
 }

 // public <T extends Request,R> ResponseData<R> enqueue(T request) throws IOException{
//  OkHttpClient client=mClientRef.get();
//  Call newCall=client.newCall(request);
//  Call call=map.get(new Object());
//  Response response=newCall.execute();
//  ResponseBody responseBody=response.body();
//  if(responseBody!=null){
//   String string=responseBody.string();
//  }
//  return null;
// }
//
// @Override
// public void enqueue(Request request,final ResponseCallback<Response> callback){
//  OkHttpClient client=mClientRef.get();
//  client.newCall(request).enqueue(new Callback(){
//   @Override
//   public void onFailure(Call call,IOException e){
//    callback.onFailure(e);
//   }
//
//   @Override
//   public void onResponse(Call call,Response response) throws IOException{
//    callback.onSuccess(response);
//   }
//  });
// }
//
// public void enqueue(Request request,final ProgressCallback<Response> callback){
//  OkHttpClient client=mClientRef.get();
//  client.newCall(request).enqueue(new Callback(){
//   private long contentLength;
//   private long totalLength=-1L;
//   @Override
//   public void onFailure(Call call,IOException e){
//    callback.onFailure(e);
//   }
//
//   @Override
//   public void onResponse(Call call,Response response) throws IOException{
//    String totalLength=response.header("content-length");
//    if(this.totalLength==-1L&&totalLength!=null){
//     try{
//      this.totalLength=Long.parseLong(totalLength);
//     } catch(NumberFormatException e){
//      //noting
//     }
//    }
//    ResponseBody body=response.body();
//    if(body!=null){
//     contentLength+=body.bytes().length;
//     System.out.println("totalLength--"+this.totalLength+"-----body---"+contentLength);
//    }
//    if(contentLength<this.totalLength){
//     callback.onProgress(response,contentLength*1D/this.totalLength);
//    } else if(this.totalLength>-1){
//     callback.onSuccess(response);
//    }
//   }
//  });
// }
//
//
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

// private OkHttpManagerImpl(){
//  throw new AssertionError("This class can not be instantiated!");
// }
}
