package com.joker.photoselector;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;

import com.joker.common.utils.CommonParams;
import com.joker.common.utils.easypermissions.Permissions;

import java.util.List;


/**
 * before this activity start, we should check STORAGE permission
 *
 */
public class PhotoSelectorActivity extends AppCompatActivity implements Permissions.Callbacks{

  private Permissions permission;

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photo_selector);
  }

  public void init(){
    Intent intent=getIntent();
    Parcelable params=intent.getParcelableExtra("params");
    if(params != null && params instanceof PhotoSelectorParams){
      PhotoSelectorParams selectorParams = (PhotoSelectorParams)params;
      int maxLimit=selectorParams.getMaxLimit();

    }
  }

  @RequiresApi(23)
  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String[] permissions,@NonNull int[] grantResults){
    permission.onRequestPermissionsResult(requestCode, permissions,grantResults,new Object[]{this});
    super.onRequestPermissionsResult(requestCode,permissions,grantResults);

  }

  @Override
  public void onPermissionsGranted(int requestCode,@NonNull List<String> perms){

  }

  @Override
  public void onPermissionsDenied(int requestCode,@NonNull List<String> perms){

  }
}
