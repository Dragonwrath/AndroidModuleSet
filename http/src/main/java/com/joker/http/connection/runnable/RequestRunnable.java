package com.joker.http.connection.runnable;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.joker.http.connection.request.GetRequest;
import com.joker.http.connection.request.Request;
import com.joker.http.core.JsonHelper;
import com.joker.http.core.header.HeadersConstant;
import com.joker.http.core.header.HttpConfig;
import com.joker.http.core.manager.ResponseCallback;
import com.joker.http.core.response.ResponseData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

abstract class RequestRunnable<T> implements Runnable{

 Request request;
 HttpURLConnection connection;
 ResponseCallback<ResponseData<T>> callback;

 RequestRunnable(GetRequest request,ResponseCallback<ResponseData<T>> callback){
  this.request=request;
  this.callback=callback;
 }

 void beforeExecute() throws IOException{
  String url=request.url();
  if(url.startsWith("http")){
   connection=(HttpURLConnection)new URL(url).openConnection();
  } else if(url.startsWith("https")){
   connection=(HttpsURLConnection)new URL(url).openConnection();
   HttpsURLConnection httpsURLConnection=(HttpsURLConnection)connection;
   //todo update ssl
   httpsURLConnection.setSSLSocketFactory((SSLSocketFactory)SSLSocketFactory.getDefault());
   httpsURLConnection.setHostnameVerifier(new HostnameVerifier(){
    @Override
    public boolean verify(String s,SSLSession sslSession){
     return true;
    }
   });
  }
  connection.setDoInput(true);
  connection.setDoOutput(true);
  connection.setReadTimeout((int)HttpConfig.getReadTimeOut());
  connection.setConnectTimeout((int)HttpConfig.getConnectTimeOut());
  HashMap<String,String> commonHeaders=HttpConfig.getHeaders();
  if(commonHeaders.size()>0) {
   for(String key : commonHeaders.keySet()) {
    connection.setRequestProperty(key,commonHeaders.get(key));
   }
  }
  HashMap<String,String> headers=request.headers();
  if(headers!=null&&headers.size()>0) {
   for(String key : headers.keySet()) {
    connection.setRequestProperty(key,headers.get(key));
   }
  }
 }

 void execute() throws Exception{
  try{
   int code=connection.getResponseCode();
   if(code>=200&&code<300) {
    Map<String,List<String>> map=connection.getHeaderFields();
    List<String> strings=map.get(HeadersConstant.HEAD_KEY_CONTENT_TYPE);
    for(String string : strings) {
     System.out.println("string = "+string);
     if(string.contains("json")) {
      handleJsonInputStream(connection.getInputStream());
     }
    }
   } else if(code>=300&&code<400) {

   } else if(code >=400) {

   }
  } catch(Exception e) {
   //todo rethrow
   callback.onFailure(e);
  }
 }

 private void handleJsonInputStream(InputStream inputStream) throws JsonParseException{
  callback.onSuccess(JsonHelper.deSerial(new JsonReader(new InputStreamReader(inputStream)),new TypeToken<ResponseData<T>>(){}));
 }

}
