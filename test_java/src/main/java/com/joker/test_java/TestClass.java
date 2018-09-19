package com.joker.test_java;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestClass{
 public static void main(String[] args) throws IOException{
 String url="https://api.weibo.com/2/users/show.json?access_token=2.00U_J9vCCb8yYB16947545ddoMg_vD&uid=2681225804";
  Request request=new Request.Builder().url(url).get().build();
  Response response=new OkHttpClient().newCall(request).execute();

  Single.just(response)
    .map(new GsonConvertFunction<HashMap<String,Object>>())
    .subscribe(new SingleObserver<HashMap<String,Object>>(){
     @Override public void onSubscribe(Disposable d){

     }

     @Override public void onSuccess(HashMap<String,Object> stringObjectHashMap){
      for(String key : stringObjectHashMap.keySet()) {
       System.out.println("key = "+key+"---------value = "+stringObjectHashMap.get(key));
      }
     }

     @Override public void onError(Throwable e){

     }
    });
 }

}
