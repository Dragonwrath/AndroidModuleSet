package com.joker.common.utils.easypermissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.joker.common.utils.R;
import com.joker.common.utils.ResourcesUtils;
import com.joker.common.utils.dialog.AlertDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

@RequiresApi(23)
abstract class BasePermissionHelper<T>{
  private final static String PERMISSION_SP="permission_config";

  private final T host;

  private final AtomicInteger requestNum;

  BasePermissionHelper(T host){
    this.host=host;
    this.requestNum = new AtomicInteger();
  }

  T getHost(){
    return host;
  }


  abstract void failureMessage();

  abstract boolean hasPermission(@NonNull String[] permissions);

  @CallSuper
  void openSettingForPermission(int requestCode){
    requestNum.set(requestCode);
  }

  @CallSuper
  void requestPermissions(String[] permissions,int requestCode,boolean showRational){
    requestNum.set(requestCode);
  }

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
          || preferences.getBoolean(permission,true));
    }
    return result;
  }

  AlertDialogFragment showRationalDialog(Context context,final String[] permissions,final int requestCode){
    List<String> list=Arrays.asList(Permissions.SYSTEM);
    Locale locale=Locale.getDefault();
    StringBuilder builder=new StringBuilder();
    String[] strings;
    String head;
    if(locale.equals(Locale.CHINA)){
      strings = Permissions.Rational.RATIONAL_ZH;
      head = Permissions.Rational.HEAD_ZH;
    } else {
      strings = Permissions.Rational.RATIONAL_ZH;
      head = Permissions.Rational.HEAD_ZH;
    }
    builder.append(head);
    builder.append("\r\n");
    for(String permission : permissions) {
      if(context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
        int i=list.indexOf(permission);
        builder.append("  ");
        builder.append(strings[i]);
        builder.append("\r\n");
      }
    }
    String content=builder.toString();
    Bundle bundle=new Bundle();
    bundle.putString("title",ResourcesUtils.getString(context,R.string.request_permission_title));
    bundle.putString("content",content);
    final AlertDialogFragment fragment=AlertDialogFragment.newInstance(bundle);
//    fragment.addPositiveClickListener(new DialogInterface.OnClickListener(){
//      @Override
//      public void onClick(DialogInterface dialog,int which){
//        Fragment parent=fragment.getParentFragment();
//        if(parent!=null){
//          parent.requestPermissions(permissions,requestCode);
//        }else{
//          fragment.getActivity().requestPermissions(permissions,requestCode);
//        }
//      }
//    });

    return fragment;
  }


  void onRequestPermissionsResult(int requestCode,String[] permissions,
                                  int[] grantResults,Object[] receivers){
    if(requestCode != requestNum.get()) return;
    Activity context=null;
    //todo should optimize
    if(host instanceof Fragment) {
      context = ((Fragment)getHost()).getActivity();
    } else if(host instanceof AppCompatActivity){
      context = (AppCompatActivity)getHost();
    }
    if(context != null){
      SharedPreferences.Editor editor=context.getSharedPreferences(PERMISSION_SP,Context.MODE_PRIVATE).edit();
      try {
        List<String> granted=new ArrayList<>();
        List<String> denied=new ArrayList<>();
        for(int i=0;i<permissions.length;i++) {
          String perm=permissions[i];
          if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
            granted.add(perm);
          }else{
            denied.add(perm);
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
      } finally{
        editor.apply();
      }
    }
  }

  void onActivityResult(int requestCode,int resultCode,Intent data){

  }
}
