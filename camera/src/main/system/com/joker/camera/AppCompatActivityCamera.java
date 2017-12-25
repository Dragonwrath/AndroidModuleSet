package com.joker.camera;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

class AppCompatActivityCamera extends CameraBase<AppCompatActivity>{

  AppCompatActivityCamera(AppCompatActivity activity){
    super(activity);
  }

  @Override
  Context getContext(){
    return mHost;
  }

  @Override
  void startActivityProxy(int requestCode,Intent intent){
    mHost.startActivityForResult(intent,requestCode);
  }
}
