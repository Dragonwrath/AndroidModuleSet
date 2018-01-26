package com.joker.camera;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

class AppCompatActivityCamera extends CameraCore<AppCompatActivity>{

  AppCompatActivityCamera(AppCompatActivity act,Params params){
    super(act,params);
  }

  @Override
  Context getContext(){
    return mHost;
  }

  @Override
  void startActivityProxy(Intent intent){
    mHost.startActivityForResult(intent,mParams.code);
  }
}
