package com.joker.permissions;


import android.content.Intent;
import android.support.v4.app.Fragment;

public abstract class BasePermissionFragment extends Fragment implements PermissionResultCallback{

  public BasePermissionFragment(){
    // Required empty public constructor
  }
  private int permissionCode;
  private String[] permissions;


  protected void needRequestPermission(int requestCode, String[] permissions){
    permissionCode=requestCode;
    this.permissions = permissions;
    RuntimePermissionsChecker.hasPermissions(this,permissions,requestCode, this);
  }

  @Override
  public void onActivityResult(int requestCode,int resultCode,Intent data){
    if(permissionCode == requestCode){
      RuntimePermissionsChecker.onActivityResult(getContext(),data,permissions,this);
    }
    super.onActivityResult(requestCode,resultCode,data);
  }
}
