package com.joker.camera.system;

import android.support.annotation.Nullable;

import java.io.File;

interface CameraHelper{
  void openCamera(int requestCode,String filename, boolean useExternal);

  @Nullable File handleCapturedPicture(int requestCode);
}
