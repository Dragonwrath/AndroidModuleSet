package com.joker.http.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
public class JsonHelper{
 private final static class Holder {
  private final static Gson INSTANCE=new GsonBuilder()
    .setDateFormat("yyyy-MM-dd hh:mm:ss")
    .create();
 }
 private JsonHelper(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public static  <T> String serial(Object o,TypeToken<T> token){
  return Holder.INSTANCE.toJson(o,token.getType());
 }

 public static <T> T deSerial(String json,TypeToken<T> token) throws RuntimeException{
  return Holder.INSTANCE.fromJson(json,token.getType());
 }

 public static <T> T deSerial(JsonReader reader,TypeToken<T> token) throws RuntimeException{
  return Holder.INSTANCE.fromJson(reader,token.getType());
 }
}
