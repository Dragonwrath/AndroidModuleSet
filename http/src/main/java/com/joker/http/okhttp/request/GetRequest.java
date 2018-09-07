package com.joker.http.okhttp.request;

import com.joker.http.core.utils.PreConditions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Set;

import okhttp3.Request;

public class GetRequest extends BaseRequest<GetRequest>{

 private LinkedHashMap<String,String> map;

 @Override
 public GetRequest addBody(String key,Object value) throws IllegalArgumentException{
  PreConditions.requireStringNotNull(key,"param's key is null or empty");
  PreConditions.requestObjectNotNull(value,"value could not be null");
  if(map==null){
   map=new LinkedHashMap<>();
  }
  try{
   map.put(URLEncoder.encode(key,"utf-8"),URLEncoder.encode(String.valueOf(value),"utf-8"));
  }catch(UnsupportedEncodingException e){
   throw new IllegalArgumentException("not support encoding");
  }
  return this;
 }

 @Override
 public Request build(){
  StringBuilder sBuilder=new StringBuilder();
  if(map.size()>0){
   Set<String> set=map.keySet();
   String[] kSet=new String[set.size()];
   set.toArray(kSet);
   final int size=kSet.length;
   sBuilder.append("?");
   for(int i=0;i<size;i++) {
    String key=kSet[i];
    sBuilder.append(key).append("=").append(map.get(key));
    if(i!=size-1){
     sBuilder.append("&");
    }
   }
  }
  return builder.url(url+sBuilder.toString())
    .method("GET",null)
    .headers(getHeaders()).build();
 }

}
