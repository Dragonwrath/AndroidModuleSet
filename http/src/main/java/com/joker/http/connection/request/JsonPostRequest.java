package com.joker.http.connection.request;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.joker.http.core.utils.PreConditions;

public class JsonPostRequest extends BaseRequest<String>{
 private JsonPostRequest(){
 }

 @Override public String method(){
  return "POST";
 }

 public static class Builder extends BaseRequest.Builder<String,Builder> {
  public Builder(){
   request=new JsonPostRequest();
   addHead("Content-Type","application/json; charset=UTF-8");
  }

  @Override public Builder addParams(
    @Nullable String key,@NonNull Object value) throws IllegalArgumentException{
   if(!(value instanceof String)){
    throw new IllegalArgumentException("json request just support String");
   }
   String trueValue=PreConditions.requireStringNotNull(((String)value),"json may be is  null");
   return super.addParams(key,trueValue);
  }

  @Override public BaseRequest<String> build(){
   return super.build();
  }
 }
}
