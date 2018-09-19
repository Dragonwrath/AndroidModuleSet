package com.joker.test_java;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import io.reactivex.functions.Function;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GsonConvertFunction<T> implements Function<Response,T>{

 private final static class Holder{
   private static final Gson INSTANCE=new GsonBuilder().create();
 }

 @Override public T apply(Response response) throws Exception{
  int code=response.code();
  if(code>=200&&code<300) {
   ResponseBody body=response.body();
   if(body!=null){
    String json=body.string();
    if(json!=null&&!json.isEmpty()) {
     Type type=new TypeToken<T>(){
     }.getType();
     return Holder.INSTANCE.fromJson(json,type);
    }
   }
  }
  throw new IllegalStateException(response.message());
 }
}
