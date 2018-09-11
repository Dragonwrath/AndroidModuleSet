package com.joker.http.okhttp.request;

import android.support.annotation.NonNull;

import okhttp3.MediaType;
import okhttp3.RequestBody;
public class JsonPostRequest extends PostRequest<String>{
 private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");

 @Override
 public PostRequest addParams(String key,@NonNull Object value){
  //nothing
  return this;
 }

 @Override
 public PostRequest body(String value){
  this.value=value;
  return this;
 }

 @Override
 RequestBody getBody(){
  return RequestBody.create(MEDIA_TYPE_JSON,value);
 }
}
