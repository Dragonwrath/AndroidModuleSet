package com.joker.http.core.manager;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseData<T> implements Serializable{
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
