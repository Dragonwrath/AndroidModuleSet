/*
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.joker.common.utils.easypermissions;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * This class is based on google-EasyPermissions.
 * Some method which custom some feature has been modified .
 * This class only support ActivityCompat {@link  android.support.v4.app.ActivityCompat}
 * and Fragment {@link  android.support.v4.app.Fragment}
 * Note: you should use this class on M.
 */
@RequiresApi(23)
public class Permissions{

  /**
   * Callback interface to receive the results of {@code Permissions.requestPermissions()}
   * calls.
   */
  public interface Callbacks extends ActivityCompat.OnRequestPermissionsResultCallback{
    void onPermissionsGranted(int requestCode,@NonNull List<String> perms);
    void onPermissionsDenied(int requestCode,@NonNull List<String> perms);
  }

  public final static String[] SYSTEM= {
      Manifest.permission.CAMERA,//0
      Manifest.permission.READ_CONTACTS,//1
      Manifest.permission.RECORD_AUDIO, //2
  };


  final static class Rational{
    final static String HEAD_ZH = "应用需要以下功能，保证正常使用：";


    final static String[] RATIONAL_EN ={
    };

    final static String[] RATIONAL_ZH ={
        "相机权限-获取相机权限，来进行拍照", //0
        "存储权限-获取存储权限，来进行读取文件",//1
        "定位权限-获取定位权限，读取相应位置信息",//2
    };
  }

  private final BasePermissionHelper helper;


  public Permissions(AppCompatActivity host){
    helper=new ActivityPermissionHelperImplV23(host);
  }

  public Permissions(Fragment host){
    helper=new FragmentPermissionHelperImplV23(host);
  }

  public boolean hasPermission(String[] permissions){
    return helper.hasPermission(permissions);
  }

  public void requestPermissions(@NonNull String[] permissions,int requestCode,boolean showRational){
    if(permissions == null || permissions.length == 0)return;
    helper.requestPermissions(permissions,requestCode,showRational);
  }

  public void onOpenSettingActivityResulst(int requestCode,int resultCode,String[] permissions) {
    if(resultCode ==AppCompatActivity.RESULT_OK){
      //TODO hold open setting
    }
  }

  public void onRequestPermissionsResult(int requestCode,String[] permissions,
                                         int[] grantResults,Object[] receivers){
    if(permissions==null||grantResults==null||permissions.length!=grantResults.length){
      helper.failureMessage();
    }
    helper.onRequestPermissionsResult(requestCode,permissions,grantResults,receivers);
  }

}
