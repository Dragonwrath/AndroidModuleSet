package com.joker.main;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.joker.common.utils.LogUtils;
import com.joker.common.utils.easypermissions.Permissions;
import com.joker.photoselector.PhotoSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Permissions.Callbacks{

  public final static String TAG = MainActivity.class.getName();

  private ListView list;
  private ArrayList<String> strings;
  private ArrayAdapter<String> adapter;
  private Permissions permissions;

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    list=(ListView)findViewById(R.id.list);
    strings=new ArrayList<>();
    adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,strings);
    list.setAdapter(adapter);
  }

  public void pick(View view){
    ArrayList<String> imageList = new ArrayList<String>();

    Cursor imageCursor =  getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID}, null, null, MediaStore.Images.Media._ID);
    if(imageCursor != null) {
      LogUtils.w(TAG, " cursor is null");
      while (imageCursor.moveToNext()) {
        imageList.add(imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
      }
      imageCursor.close();
      for(String s : imageList) {
        LogUtils.w(TAG, s);

      }
    }


  }

  public void request(View view){
//    getGrantedPermissions();
//    getPermission();
    if(Build.VERSION.SDK_INT >=23) {
      permissions=new Permissions(this);
      boolean b=permissions.hasPermission(Permissions.SYSTEM);
      if(b) {
        LogUtils.w(TAG,"All permissions have granted");
      } else {
        permissions.requestPermissions(Permissions.SYSTEM,101, true);
      }
    }
  }

  @RequiresApi(23)
  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String[] permissions,@NonNull int[] grantResults){
    this.permissions.onRequestPermissionsResult(requestCode,permissions,grantResults,new Object[]{this});
    super.onRequestPermissionsResult(requestCode,permissions,grantResults);
  }

  @Override
  public void onPermissionsGranted(int requestCode,@NonNull List<String> perms){
    for(String perm : perms) {
      LogUtils.w(TAG," "+perm +" have granted");
    }
  }

  @Override
  public void onPermissionsDenied(int requestCode,@NonNull List<String> perms){
    for(String perm : perms) {
      LogUtils.w(TAG," "+perm +" have denied");
    }
  }

  @Override
  protected void onActivityResult(int requestCode,int resultCode,Intent data){
    super.onActivityResult(requestCode,resultCode,data);
  }
}
