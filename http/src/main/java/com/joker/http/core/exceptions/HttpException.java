package com.joker.http.core.exceptions;

public class HttpException extends Exception{
 private final int code;
 private final String message;

 public HttpException(int code,String message){
  this.code=code;
  this.message=message;
 }

 public int getCode(){
  return code;
 }

 public String getMessage(){
  return message;
 }

 @Override public String toString(){
  return "HttpException{"+
    "code="+code+
    ", message='"+message+'\''+
    '}';
 }
}
