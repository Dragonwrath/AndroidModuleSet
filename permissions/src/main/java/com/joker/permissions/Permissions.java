package com.joker.permissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissions{
  public static String[] hasPermissions(Context context,String[] permissions){
    if(Build.VERSION.SDK_INT>=23){
      List<String> result=new ArrayList<>();
      for(String permission : permissions) {
        if(ActivityCompat.checkSelfPermission(context,permission)!=PackageManager.PERMISSION_GRANTED){
          result.add(permission);
        }
      }
      return result.toArray(new String[0]);
    }else{
      return new String[0];
    }
  }
}
