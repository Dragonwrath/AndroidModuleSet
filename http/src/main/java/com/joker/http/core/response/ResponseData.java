package com.joker.http.core.response;

import com.google.gson.annotations.SerializedName;

public class ResponseData<T>{
 @SerializedName(value = "code",alternate={"resultCode"})
 private final String code;
 private final String msg;
 private final T data;

 public ResponseData(String code,String msg,T data){
  this.code=code;
  this.msg=msg;
  this.data=data;
 }

 public String getCode(){
  return code;
 }

 public String getMsg(){
  return msg;
 }

 public T getData(){
  return data;
 }
}
