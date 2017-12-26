package com.joker.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.view.Surface;

class CameraRotate{
  Activity activity;

  public CameraRotate(Activity activity){
    this.activity=activity;
    int displayRotation=activity.getWindowManager().getDefaultDisplay()
        .getRotation();
    int CAMERA_ID=Camera.getNumberOfCameras();
    Camera mCamera=Camera.open(CAMERA_ID);
    Camera.CameraInfo cameraInfo=null;

    if(mCamera!=null){
      // Get camera info only if the camera is available
      cameraInfo=new Camera.CameraInfo();
      Camera.getCameraInfo(CAMERA_ID,cameraInfo);
      mCamera.setDisplayOrientation(calculatePreviewOrientation(cameraInfo,displayRotation));
    }
  }

  public static int calculatePreviewOrientation(Camera.CameraInfo info,int rotation){
    int degrees=0;
    switch(rotation){
      case Surface.ROTATION_0:
        degrees=0;
        break;
      case Surface.ROTATION_90:
        degrees=90;
        break;
      case Surface.ROTATION_180:
        degrees=180;
        break;
      case Surface.ROTATION_270:
        degrees=270;
        break;
    }
    int result;
    if(info.facing==Camera.CameraInfo.CAMERA_FACING_FRONT){
      result=(info.orientation+degrees)%360;
      result=(360-result)%360;  // compensate the mirror
    }else{  // back-facing
      result=(info.orientation-degrees+360)%360;
    }
    return result;
  }
}
