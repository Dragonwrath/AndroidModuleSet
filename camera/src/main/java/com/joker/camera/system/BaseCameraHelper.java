package com.joker.camera.system;

import android.os.Build;

import com.joker.common.utils.easypermissions.Permissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class BaseCameraHelper<Host> implements CameraHelper{

  final Host host;

  BaseCameraHelper(Host host){
    this.host=host;
  }

  abstract Permissions getPermissions();

  public void checkRequiredPermission(boolean useExternal) {
    if(Build.VERSION.SDK_INT >= 23){
      Permissions permissions=getPermissions();
      List<String> list = new ArrayList<>();
      int requestCode = 1;
      if(!permissions.hasPermission(Permissions.Camera.List)){
        list.addAll(Arrays.asList(Permissions.Camera.List));
        requestCode &= Permissions.Camera.PERMISSION_REQUEST_CODE;
      }
      if(useExternal && !permissions.hasPermission(Permissions.Storage.List)) {
        list.addAll(Arrays.asList(Permissions.Storage.List));
        requestCode &= Permissions.Storage.PERMISSION_REQUEST_CODE;
      }
      if(list.size()>0) {
        permissions.requestPermissions((String[])list.toArray(),requestCode,true);
      }
    }
  }
}
