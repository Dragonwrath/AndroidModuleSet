package com.joker.common.utils;

import android.content.Intent;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 解决PendingIntent,在6.0以上系统中传输Parcelable 对象出现丢失的问题
 */
public class PendingIntentResolver{

 private PendingIntentResolver(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public static Intent serial(Intent intent,String key,Parcelable parcelable) {
  if(Build.VERSION.SDK_INT>=23){
   Parcel parcel=Parcel.obtain();
   parcelable.writeToParcel(parcel, 0);
   byte[] result=parcel.marshall();
   parcel.recycle();
   intent.putExtra(key,result);
  } else {
   intent.putExtra(key,parcelable);
  }
  return intent;
 }

 public static <T extends Parcelable> T deserial(Intent intent,String key,Parcelable.Creator<T> creator) {
  if(Build.VERSION.SDK_INT>=23) {
   byte[] bytes=intent.getByteArrayExtra(key);
   Parcel parcel=Parcel.obtain();
   parcel.unmarshall(bytes, 0, bytes.length);
   parcel.setDataPosition(0);
   T result=creator.createFromParcel(parcel);
   parcel.recycle();
   return (result);
  } else {
   return intent.getParcelableExtra(key);
  }
 }
}
