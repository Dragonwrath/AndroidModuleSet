package com.joker.main;
import android.app.Application;

public class BaseApplication extends Application implements Thread.UncaughtExceptionHandler{
 @Override
 public void onCreate(){
  super.onCreate();
  new SocialManager(this).weChat().weiBo();
 }

 @Override
 public void uncaughtException(Thread t,Throwable e){
  e.printStackTrace();
 }
}
