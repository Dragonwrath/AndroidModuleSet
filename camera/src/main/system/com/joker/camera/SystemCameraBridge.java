package com.joker.camera;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class SystemCameraBridge{

  private final CameraBase camera;

  public SystemCameraBridge(AppCompatActivity activity){
    camera=new AppCompatActivityCamera(activity);
  }

  public SystemCameraBridge(Fragment fragment){
    camera=new FragmentCamera(fragment);
  }

  public void openCamera(int requestCode) throws CameraOpenFailedException{
    camera.openCamera(requestCode,true,false);
  }

  public void openCamera(int requestCode,boolean useThumbnail,boolean useExternal) throws CameraOpenFailedException{
    camera.openCamera(requestCode,useThumbnail,useExternal);
  }

  public void handleResult(Intent data,ImageView view){
    camera.handleActivityResult(data,view);
  }

}
