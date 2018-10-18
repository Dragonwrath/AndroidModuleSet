package com.joker.scan.utils;
public class Objects{

 private Objects(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public static <T> T requireNonNull(T obj, String message) {
  if (obj == null)
   throw new NullPointerException(message);
  return obj;
 }


}
