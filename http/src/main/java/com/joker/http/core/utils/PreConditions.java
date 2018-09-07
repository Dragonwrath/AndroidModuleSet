package com.joker.http.core.utils;
import java.io.File;
public class PreConditions{

 private PreConditions(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public static void requireStringNotNull(String args,String message){
  if(args==null||args.length()==0||args.trim().length()==0) throw new IllegalArgumentException(message);
 }

 public static void requestObjectNotNull(Object object,String message){
  if(object==null) throw new IllegalArgumentException(message);
 }

 public static void requireFileNotNull(File file){
  if(file==null||!file.canRead()||file.length()==0) throw new IllegalArgumentException("file 's path:"+file+" ,maybe is null, or its length is 0,or it can't be read");
 }
}
