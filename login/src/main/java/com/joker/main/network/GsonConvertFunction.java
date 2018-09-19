package com.joker.main.network;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.exception.WeiboHttpException;

import java.lang.reflect.Type;

import io.reactivex.functions.Function;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GsonConvertFunction<T> implements Function<Response,T>{

 public final static class Holder{
   private static final Gson INSTANCE=new GsonBuilder().create();
 }

 @Override public T apply(Response response) throws Exception{
  int code=response.code();
  if(code>=200&&code<300) {
   ResponseBody body=response.body();
   if(body!=null){
    String json=body.string();
    if(!TextUtils.isEmpty(json)) {
     Type type=new TypeToken<T>(){
     }.getType();
     return Holder.INSTANCE.fromJson(json,type);
    }
   }
  }
  throw new WeiboHttpException(response.message(),response.code());
 }
}
