package com.joker.main;

public class KeyHouse{
 static {
  System.loadLibrary("share_key");
 }

 private KeyHouse(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public static native String getWeChatAppId();

 public static native String getWeChatAppSecret();

 public static native String getWeiboKey();


}
