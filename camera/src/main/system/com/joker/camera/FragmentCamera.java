package com.joker.camera;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

class FragmentCamera extends CameraBase<Fragment>{

  FragmentCamera(Fragment fragment){
    super(fragment);
  }

  @Override
  Context getContext(){
    return mHost.getContext();
  }

  @Override
  void startActivityProxy(int requestCode,Intent intent){
    mHost.startActivityForResult(intent,requestCode);
  }
}
