package com.joker.http.okhttp.request;
import android.support.annotation.NonNull;

import com.joker.http.core.header.HttpConfig;
import com.joker.http.core.request.RequestBuilder;
import com.joker.http.core.utils.HttpUtils;
import com.joker.http.core.utils.PreConditions;

import java.util.HashMap;

import okhttp3.Headers;
import okhttp3.Request;

@SuppressWarnings("unchecked")
abstract class BaseRequest<Builder extends BaseRequest> implements RequestBuilder<Request,Builder>{
 final Request.Builder builder;
 private HashMap<String,String> headers;
 String url;

 BaseRequest(){
  builder=new Request.Builder();
 }


 @Override
 public Builder url(String url) throws IllegalArgumentException{
  this.url=HttpUtils.verifyUrl(url);
  return (Builder)this;
 }

 @Override
 public Builder addHead(@NonNull String key,@NonNull String value) throws IllegalArgumentException{
  PreConditions.requireStringNotNull(key,"head's key is null or empty");
  PreConditions.requireStringNotNull(value,"head's value is null or empty");
  if(headers==null){
   headers=new HashMap<>();
  }
  headers.put(key,value);
  return (Builder)this;
 }

 Headers getHeaders(){
  Headers.Builder builder=new Headers.Builder();
  if(headers!=null&&headers.size()>0){
   for(String key : headers.keySet()) {
    builder.add(key,headers.get(key));
   }
  }
  final HashMap<String,String> headers=HttpConfig.getHeaders();
  if(headers.size()>0){
   builder.addAll(Headers.of(headers));
  }
  return builder.build();
 }
}
