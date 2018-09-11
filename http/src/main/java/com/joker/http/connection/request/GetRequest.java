package com.joker.http.connection.request;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.joker.http.core.utils.PreConditions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

public class GetRequest<T> extends BaseRequest<T>{

 @Override
 public String method(){
  return "GET";
 }

 public static class Builder<T> extends BaseRequest.Builder<T,Builder>{

  public Builder(){
   request=new GetRequest<>();
  }

  @Override
  public Builder addParams(
    @Nullable String key,@NonNull Object value) throws IllegalArgumentException{
   if(!(value instanceof String)){
    throw new IllegalArgumentException("just support String");
   }
   String trueValue=(String)value;
   PreConditions.requireStringNotNull(trueValue,"Get Request's value can't be null");
   try{
    trueValue=URLEncoder.encode(trueValue,"utf-8");
   }catch(UnsupportedEncodingException e){
    throw new IllegalArgumentException("not support encoding");
   }
   return super.addParams(key,trueValue);
  }

  @Override
  public BaseRequest<T> build(){
   String url=request.url();
   HashMap<String,Object> bodies=request.bodies();
   StringBuilder builder=new StringBuilder(url);
   if(bodies!=null){
    int size=bodies.size();
    if(bodies.size()>0){
     builder.append("?");
     for(String key : bodies.keySet()) {
      builder.append(key).append("=").append(bodies.get(key));
      if(size-->1){
       builder.append("&");
      }else{
       break;
      }
     }
    }
   }
   url(builder.toString());
   return super.build();
  }
 }
}
