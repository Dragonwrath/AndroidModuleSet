package com.joker.common.utils.easypermissions;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.joker.common.utils.R;
import com.joker.common.utils.ResourcesUtils;
import com.joker.common.utils.dialog.AlertDialogFragment;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(23)
abstract class BasePermissionHelper<T>{
  private final static String PERMISSION_SP="permission_config";

  private final T host;


  BasePermissionHelper(T host){
    this.host=host;
  }

  T getHost(){
    return host;
  }


  abstract void failureMessage();

  abstract boolean hasPermission(@NonNull String[] permissions);

  abstract void openSettingForPermission(int requestCode);

  void onActivityResult(int requestCode,int resultCode,Intent data){
    //todo should be implement
  }

  void requestPermissions(String[] permissions,int requestCode,boolean showRational){
    this.requestPermissions(permissions,requestCode,showRational,null,null);
  }

  abstract void requestPermissions(String[] permissions,int requestCode,boolean showRational,
                                   DialogInterface.OnClickListener posListener,
                                   DialogInterface.OnClickListener negListener);

  boolean hasPermission(@NonNull Context context,@NonNull String[] permissions){
    if(context==null){
      throw new IllegalArgumentException("Can't check permissions for null context");
    }
    for(String perm : permissions) {
      if(ContextCompat.checkSelfPermission(context,perm)!=PackageManager.PERMISSION_GRANTED){
        return false;
      }
    }
    return true;
  }

  boolean canAccessRequestPermission(Activity activity,String[] permissions){
    SharedPreferences preferences=activity.getSharedPreferences(PERMISSION_SP,Context.MODE_PRIVATE);
    boolean result=true;
    for(String permission : permissions) {
      result&=(activity.shouldShowRequestPermissionRationale(permission)
          ||preferences.getBoolean(permission,true));
    }
    return result;
  }

  AlertDialogFragment showRationalDialog(Context context,final String[] permissions,
                                         DialogInterface.OnClickListener posListener,
                                         DialogInterface.OnClickListener negListener){
    DangerousPermissionRational principles=new DangerousPermissionRational(context,permissions);
    String[] strings=principles.translate();
    StringBuilder builder=new StringBuilder();
    for(String principle : strings) {
      builder.append("  ");
      builder.append(principle);
      builder.append("\r\n");
    }
    String content=builder.toString();
    Bundle bundle=new Bundle();
    bundle.putString("title",ResourcesUtils.getString(context,R.string.request_permission_title));
    bundle.putString("content",content);
    AlertDialogFragment fragment=AlertDialogFragment.newInstance(bundle);
    fragment.addPositiveClickListener(posListener);
    fragment.addOnNegativeClickListener(negListener);
    return fragment;
  }


  void onRequestPermissionsResult(int requestCode,String[] permissions,
                                  int[] grantResults,Object[] receivers){
    Activity context=null;
    if(host instanceof Fragment){
      context=((Fragment)getHost()).getActivity();
    }else if(host instanceof AppCompatActivity){
      context=(AppCompatActivity)getHost();
    }
    if(context!=null){
      SharedPreferences.Editor editor=context.getSharedPreferences(PERMISSION_SP,Context.MODE_PRIVATE).edit();
      try{
        List<String> granted=new ArrayList<>();
        List<String> denied=new ArrayList<>();
        for(int i=0;i<permissions.length;i++) {
          String perm=permissions[i];
          if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
            granted.add(perm);
          }else{
            denied.add(perm);
            //record permission which are set "Never ask again"
            if(!context.shouldShowRequestPermissionRationale(perm)){
              editor.putBoolean(perm,false);
            }
          }
        }
        if(receivers!=null){
          for(Object object : receivers) {
            // Report granted permissions, if any.
            if(!granted.isEmpty()){
              if(object instanceof Permissions.Callbacks){
                ((Permissions.Callbacks)object).onPermissionsGranted(requestCode,granted);
              }
            }
            // Report denied permissions, if any.
            if(!denied.isEmpty()){
              if(object instanceof Permissions.Callbacks){
                ((Permissions.Callbacks)object).onPermissionsDenied(requestCode,denied);
              }
            }
          }
        }
      }finally{
        editor.apply();
      }
    }
  }
}
