package com.joker.permissions;

public interface PermissionResultCallback{
  void allPermissionGranted();
  void somePermissionsDenied();
}
