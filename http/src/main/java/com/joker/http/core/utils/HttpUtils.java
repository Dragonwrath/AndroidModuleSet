package com.joker.http.core.utils;

public class HttpUtils{
 private HttpUtils(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public static String verifyUrl(String url){
  PreConditions.requireStringNotNull(url,"url is null or empty");
  String urlTrimmed=url.trim();
  if(!urlTrimmed.matches("^http.*[a-zA-Z]$")) throw new IllegalArgumentException("url is not validate url");
  return urlTrimmed;
 }

}
