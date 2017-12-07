package com.joker.permissions;

interface PermissionResultCallback{
  void allPermissionGranted();
  void somePermissionsDenied();
}
