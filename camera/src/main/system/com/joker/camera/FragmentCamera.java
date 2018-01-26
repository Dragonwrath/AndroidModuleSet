package com.joker.camera;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

class FragmentCamera extends CameraCore<Fragment>{

  FragmentCamera(Fragment frag,Params params){
    super(frag,params);
  }

  @Override
  Context getContext(){
    return mHost.getContext();
  }

  @Override
  void startActivityProxy(Intent intent){
    mHost.startActivityForResult(intent,mParams.code);
  }
}
