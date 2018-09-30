package com.joker.main.utils;

import android.net.Uri;
import android.os.Build;

import com.joker.main.exceptions.ShareParamsException;

import java.io.File;

/**
 * 分享对象的参数检查类，主要包含，标题，内容，url，缩略图，图片、视频大小，
 * 文件合法性检查
 */
public class ShareParamExaminer{

 private ShareParamExaminer(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public static void titleNotEmpty(String title){
  stringParamsNotEmpty(title,ShareParamsException.ILLEGAL_TITLE|ShareParamsException.ILLEGAL_TYPE_EMPTY);
 }

 public static void titleLengthIsLegal(String title,int limited){
  stringLengthIsLegal(title,limited,ShareParamsException.ILLEGAL_TITLE|ShareParamsException.ILLEGAL_TYPE_LONG);
 }

 public static void contentNotEmpty(String message) {
  stringParamsNotEmpty(message,ShareParamsException.ILLEGAL_CONTENT|ShareParamsException.ILLEGAL_TYPE_EMPTY);
 }

 public static void contentLengthIsLegal(String message,int limited) {
  stringLengthIsLegal(message,limited,ShareParamsException.ILLEGAL_CONTENT|ShareParamsException.ILLEGAL_TYPE_LONG);
 }

 public static void urlLengthIsLegal(String url,int limited) {
  stringLengthIsLegal(url,limited,ShareParamsException.ILLEGAL_CONTENT|ShareParamsException.ILLEGAL_TYPE_LONG);
 }

 public static void thumbIsLegal(String path,int limited) {
  fileIsLegal(path,limited,ShareParamsException.ILLEGAL_THUMB);
 }

 public static void imageIsLegal(String path,int limited) {
  fileIsLegal(path,limited,ShareParamsException.ILLEGAL_IMAGE);
 }

 public static void videoIsLegal(String path,int limited) {
  fileIsLegal(path,limited,ShareParamsException.ILLEGAL_VIDEO);
 }

 private static void stringParamsNotEmpty(String str,int exceptionType){
  if(str==null||str.isEmpty()){
   throw new ShareParamsException(exceptionType);
  }
 }

 private static void stringLengthIsLegal(String str,int limited,int exceptionType){
  stringParamsNotEmpty(str,exceptionType&0x11110000|ShareParamsException.ILLEGAL_TYPE_EMPTY);
  if(str.length()>limited){
   throw new ShareParamsException(exceptionType);
  }
 }

 private static void fileIsLegal(String path, int limited,int exceptionType) {
  stringParamsNotEmpty(path,exceptionType&0x11110000|ShareParamsException.ILLEGAL_TYPE_EMPTY);
  File file=new File(path);
  if(file.length()<=0) {
   throw new ShareParamsException(exceptionType&0x11110000|ShareParamsException.ILLEGAL_TYPE_EMPTY);
  }
  if(file.length()>limited) {
   throw new ShareParamsException(exceptionType&0x11110000|ShareParamsException.ILLEGAL_TYPE_LONG);
  }

  Uri uri=Uri.fromFile(file);
  if(Build.VERSION.SDK_INT<24) {

  }

 }
}
