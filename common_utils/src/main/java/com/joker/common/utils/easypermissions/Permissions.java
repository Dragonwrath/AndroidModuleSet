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

import android.app.Activity;
import android.content.Intent;
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

  public final static int PERMISSION_REQUEST_CODE=101;

  /**
   * Callback interface to receive the results of {@code Permissions.requestPermissions()}
   * calls.
   */
  public interface Callbacks extends ActivityCompat.OnRequestPermissionsResultCallback{
    void onPermissionsGranted(int requestCode,@NonNull List<String> perms);
    void onPermissionsDenied(int requestCode,@NonNull List<String> perms);
  }

  private final BasePermissionHelper helper;
  private final int code;

  public Permissions(AppCompatActivity host,int requestCode){
    helper=new ActivityPermissionHelperImplV23(host);
    code=requestCode;
  }

  public Permissions(Fragment host,int requestCode){
    helper=new FragmentPermissionHelperImplV23(host);
    code=requestCode;
  }

  public boolean hasPermission(String[] permissions){
    return helper.hasPermission(permissions);
  }

  public void requestPermissions(
      @NonNull String[] permissions,int requestCode,boolean showRational){
    if(code!=requestCode&&(permissions==null||permissions.length==0)) return;
    helper.requestPermissions(permissions,requestCode,showRational);
  }

  public void onOpenSettingActivityResulst(int requestCode,int resultCode,String[] permissions){
    if(code!=requestCode) return;
    if(resultCode==AppCompatActivity.RESULT_OK){
      //TODO hold open setting
      if(requestCode==PERMISSION_REQUEST_CODE){

      }
    }
  }

  public void onRequestPermissionsResult(int requestCode,String[] permissions,
                                         int[] grantResults,Object[] receivers){
    if(code!=requestCode) return;
    if(permissions==null||grantResults==null||permissions.length!=grantResults.length){
      helper.failureMessage();
    }
    helper.onRequestPermissionsResult(requestCode,permissions,grantResults,receivers);
  }

  public void openSettingForPermission(int requestCode){
    if(requestCode != code) return;
    helper.openSettingForPermission(requestCode);
  }

  public void onActivityResult(int requestCode,int resultCode,Intent data){
    if(resultCode !=Activity.RESULT_OK || requestCode != code){
      helper.failureMessage();
    }
    helper.onActivityResult(requestCode, resultCode, data);
  }
}
