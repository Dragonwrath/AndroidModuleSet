package com.joker.permissions;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RuntimePermissionsChecker{
  public static void hasPermissions(AppCompatActivity activity,String[] permissions,int requestCode,PermissionResultCallback callback){
    if(Build.VERSION.SDK_INT>=23){
      List<String> result=new ArrayList<>();
      for(String permission : permissions) {
        if(ActivityCompat.checkSelfPermission(activity,permission)!=PackageManager.PERMISSION_GRANTED){
          result.add(permission);
        }
      }
      String[] strings=result.toArray(new String[0]);
      if(strings.length>0){
        Intent intent=new Intent(activity,PermissionsRequestActivity.class);
        intent.putExtra("permissions",strings);
        activity.startActivityForResult(intent,requestCode);
        return;
      }
    }
    callback.allPermissionGranted();
  }

  public static void hasPermissions(Fragment fragment,String[] permissions,int requestCode,PermissionResultCallback callback){
    if(Build.VERSION.SDK_INT>=23){
      List<String> result=new ArrayList<>();
      for(String permission : permissions) {
        if(ActivityCompat.checkSelfPermission(fragment.getContext(),permission)!=PackageManager.PERMISSION_GRANTED){
          result.add(permission);
        }
      }
      String[] strings=result.toArray(new String[0]);
      if(strings.length>0){
        Intent intent=new Intent(fragment.getContext(),PermissionsRequestActivity.class);
        intent.putExtra("permissions",strings);
        fragment.startActivityForResult(intent,requestCode);
        return;
      }
    }
    callback.allPermissionGranted();
  }

  public static void onActivityResult(Context context,Intent data,String[] permissions,PermissionResultCallback callback){
    if(Build.VERSION.SDK_INT>=23){
      if(data!=null){
        if(data.getBooleanExtra("permissions",false)){
          callback.allPermissionGranted();
        }else{
          callback.somePermissionsDenied();
        }
      }else{
        for(String permission : permissions) {
          if(context.checkSelfPermission(permission)!=PackageManager.PERMISSION_GRANTED){
            callback.somePermissionsDenied();
            return;
          }
        }
        callback.allPermissionGranted();
      }
    }
  }
}
