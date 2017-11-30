package com.joker.photoselector;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.joker.common.utils.CommonParams;


public class PhotoSelectorActivity extends AppCompatActivity{

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photo_selector);
    checkPermission();

  }

  private void checkPermission() {
    if(Build.VERSION.SDK_INT >=23) {
      if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},CommonParams.PERMISSION_REQUEST);
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String[] permissions,@NonNull int[] grantResults){
    super.onRequestPermissionsResult(requestCode,permissions,grantResults);

    //todo should hold user's cancel action that cause permissions or grantResult is null
  }


  private void shouldeToastHint(String[] permissions) {
    boolean result = true;
  }

}
