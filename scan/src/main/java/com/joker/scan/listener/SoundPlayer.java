package com.joker.scan.listener;
import android.app.Activity;
import android.support.annotation.NonNull;

import com.joker.scan.core.OnScanResultListener;

import java.lang.ref.WeakReference;

public class SoundPlayer implements OnScanResultListener{

 private WeakReference<Activity> mWeakRef;
 public SoundPlayer(@NonNull Activity activity){
  mWeakRef=new WeakReference<>(activity);
 }

 @Override public void onScanSuccess(String message){
   //todo play music
 }

 @Override public void onScanFailure(Exception ex){
  //nothing
 }
}
