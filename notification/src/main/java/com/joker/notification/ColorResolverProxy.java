package com.joker.notification;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;

public class ColorResolverProxy{

 private static volatile int titleColor=0x00FFFFFF;
 public static volatile int contentColor=0x00FFFFFF;

 private ColorResolverProxy(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public static @ColorInt
 int getTitleColor(Context context){
  if(titleColor==0x00FFFFFF)
   synchronized(ColorResolverProxy.class){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
     titleColor=ColorResolverImplV24.getTitleColor(context);
    }else{
     titleColor=ColorResolverImpl.getTitleColor(context);
    }
   }
  return titleColor;
 }

 public static @ColorInt
 int getContentColor(Context context){
  if(contentColor==0x00FFFFFF)
   synchronized(ColorResolverProxy.class){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
     contentColor=ColorResolverImplV24.getContentColor(context);
    }else{
     contentColor=ColorResolverImpl.getContentColor(context);
    }
   }
  return contentColor;
 }
}
