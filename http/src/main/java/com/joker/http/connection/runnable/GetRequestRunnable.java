package com.joker.http.connection.runnable;

import com.joker.http.connection.request.GetRequest;
import com.joker.http.core.manager.ResponseCallback;
import com.joker.http.core.response.ResponseData;

public class GetRequestRunnable<T> extends RequestRunnable<T>{

 public GetRequestRunnable(GetRequest request,ResponseCallback<ResponseData<T>> callback){
  super(request,callback);
 }

 @Override public void run(){
  try{
   beforeExecute();
   execute();
  }catch(Exception e){
   callback.onFailure(e);
  }
 }

}
