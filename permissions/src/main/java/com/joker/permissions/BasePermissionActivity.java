package com.joker.permissions;

import android.content.Intent;

import com.joker.common.utils.BaseActivity;


public abstract class BasePermissionActivity extends BaseActivity implements PermissionResultCallback{

  private int permissionCode;
  private String[] permissions;

  protected void needRequestPermission(int requestCode, String[] permissions){
    permissionCode=requestCode;
    this.permissions = permissions;
    RuntimePermissionsChecker.hasPermissions(this,permissions,requestCode, this);
  }

  @Override
  protected void onActivityResult(int requestCode,int resultCode,Intent data){
    if(permissionCode == requestCode){
      RuntimePermissionsChecker.onActivityResult(this,data,permissions,this);
    }
    super.onActivityResult(requestCode,resultCode,data);
  }

}
