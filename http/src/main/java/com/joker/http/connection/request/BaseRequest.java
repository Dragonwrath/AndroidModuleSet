package com.joker.http.connection.request;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.joker.http.core.header.HttpConfig;
import com.joker.http.core.request.RequestBuilder;
import com.joker.http.core.utils.HttpUtils;
import com.joker.http.core.utils.PreConditions;

import java.util.HashMap;

import kotlin.NotImplementedError;

public class BaseRequest<T> implements Request<T>{
 public final static String EMPTY_KEY="EMPTY_KEY";

 private String url;
 private HashMap<String,String> headers=new HashMap<>();
 private HashMap<String,Object> bodies=new HashMap<>();

 BaseRequest(){
 }

 @Override
 public String method(){
  throw new NotImplementedError();
 }

 @NonNull
 @Override
 public String url(){
  return url;
 }

 @Override
 public @Nullable HashMap<String,String> headers(){
  HashMap<String,String> headers=HttpConfig.getHeaders();
  HashMap<String,String> map=new HashMap<>(headers);
  map.putAll(this.headers);
  return map;
 }

 @Override
 public HashMap<String,Object> bodies(){
  return bodies;
 }

 @SuppressWarnings("unchecked")
 static class Builder<T,B extends Builder> implements RequestBuilder<BaseRequest<T>,B>{
  BaseRequest<T> request;

  @Override
  public B url(String url) throws IllegalArgumentException{
   HttpUtils.verifyUrl(url);
   request.url=url;
   return (B)this;
  }

  @Override
  public B addHead(@NonNull String key,@NonNull String value) throws IllegalArgumentException{
   PreConditions.requireStringNotNull(key,"head's key is null or empty");
   PreConditions.requireStringNotNull(value,"head's value is null or empty");
   request.headers.put(key,value);
   return (B)this;
  }

  @Override
  public B addParams(@Nullable String key,@NonNull Object value) throws IllegalArgumentException{
   PreConditions.requestObjectNotNull(value,"value could not be null");
   if(key==null||key.length()==0){
    key=EMPTY_KEY;
   }
   request.bodies.put(key,value);
   return (B)this;
  }

  @Override
  public BaseRequest<T> build(){
   HashMap<String,String> headerWrapper=new HashMap<>();
   HashMap<String,String> headers=request.headers;
   if(request.headers!=null){
    headerWrapper.putAll(request.headers);
   }
   headers.putAll(HttpConfig.getHeaders());
   request.headers=headerWrapper;
   return request;
  }
 }
}
