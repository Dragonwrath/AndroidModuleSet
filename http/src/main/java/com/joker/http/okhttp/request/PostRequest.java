package com.joker.http.okhttp.request;

import okhttp3.Request;
import okhttp3.RequestBody;
public abstract class PostRequest<T> extends BaseRequest<PostRequest>{
 T value;

 public abstract PostRequest body(T value) throws IllegalArgumentException;

 abstract RequestBody getBody();

 @Override
 public Request build(){
  return builder.url(url).headers(getHeaders()).post(getBody()).build();
 }
}
