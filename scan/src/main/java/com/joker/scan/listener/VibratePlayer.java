package com.joker.scan.listener;
import android.app.Activity;

import com.joker.scan.core.OnScanResultListener;

import java.lang.ref.WeakReference;

public class VibratePlayer implements OnScanResultListener{
 private WeakReference<Activity> mWeakRef;

 public VibratePlayer(Activity activity){
  mWeakRef=new WeakReference<>(activity);
 }

 @Override public void onScanSuccess(String message){

 }

 @Override public void onScanFailure(Exception ex){

 }
}
