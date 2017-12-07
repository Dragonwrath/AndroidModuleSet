package com.joker.camera.system;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

public class AppCompactCameraHelper extends BaseCameraHelper<AppCompatActivity>{


  public AppCompactCameraHelper(AppCompatActivity activity){
    super(activity);
  }

  @Override
  public void openCamera(int requestCode,String filename, boolean useExternal){
    Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    Uri uri=generateUri(host,filename,useExternal);
  }

  @Override
  public File handleCapturedPicture(int requestCode){

  }


}
