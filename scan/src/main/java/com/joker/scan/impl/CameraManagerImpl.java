package com.joker.scan.impl;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import com.joker.scan.core.CameraManager;
import com.joker.scan.core.DecodeManager;
import com.joker.scan.core.ViewDelegate;
import com.joker.scan.exceptions.CameraOpenFailureException;
import com.joker.scan.utils.Objects;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class CameraManagerImpl implements CameraManager, Camera.PreviewCallback, SurfaceHolder.Callback{

 private Camera camera;
 private WeakReference<ViewDelegate> mViewReference;
 private DecodeManager manager;
 private Point screenResolution;
 private Rect mFrameRect;
 private Point cameraResolution;
// private Point screenResolution;


 @Override public void bindViewDelegate(@NonNull ViewDelegate view){
  mViewReference=new WeakReference<>(Objects.requireNonNull(view,"ViewDelegate can' be null"));
 }

 @Override public void switchFlashLight(){
  Camera.Parameters parameters=camera.getParameters();
  if(!parameters.getFlashMode().equals(Camera.Parameters.FLASH_MODE_OFF)){
   parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
  }else{
   parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
  }
  camera.setParameters(parameters);
 }

 @Override public void bindDecodeManager(DecodeManager manager){
  this.manager=manager;
 }

 @Override public void openCamera() throws CameraOpenFailureException{
  SurfaceHolder holder=null;
  if(mViewReference!=null&&mViewReference.get()!=null){
   holder=mViewReference.get().getSurfaceHolder();
   holder.addCallback(this);
  }
  if(camera==null&&holder!=null){
   try{
    camera=Camera.open();
    camera=Objects.requireNonNull(camera,"can't open camera");
    camera.setPreviewCallback(this);
    camera.setPreviewDisplay(holder);
    camera.startPreview();
   }catch(RuntimeException|IOException e){
    throw new CameraOpenFailureException(e.getMessage());
   }
  }
 }

 @Override public void closeCamera(){
  if(mViewReference!=null&&mViewReference.get()!=null){
   mViewReference.get().getSurfaceHolder().removeCallback(this);
  }
  if(camera!=null){
   camera.stopPreview();
   camera.release();
   camera=null;
  }
 }

 @Override public void onPreviewFrame(byte[] data,Camera camera){
  manager.decode(data,screenResolution.x,screenResolution.y,mFrameRect);
 }

 @Override public void surfaceCreated(SurfaceHolder holder){
  openCamera();
 }

 @Override public void surfaceChanged(SurfaceHolder holder,int format,int width,int height){
  adjustPreviewSize();
 }

 @Override public void surfaceDestroyed(SurfaceHolder holder){
  closeCamera();
 }

 @SuppressWarnings("SuspiciousNameCombination") private void adjustPreviewSize(){
  if(mViewReference!=null&&mViewReference.get()!=null){
   ViewDelegate viewDelegate=mViewReference.get();
   Camera.Parameters parameters=camera.getParameters();
   Context context=viewDelegate.getViewContext();
   WindowManager manager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
   if(manager!=null){
    Display display=manager.getDefaultDisplay();
    screenResolution=new Point();
    display.getSize(screenResolution);

    Point screenResolutionForCamera=new Point();
    screenResolutionForCamera.x=screenResolution.x;
    screenResolutionForCamera.y=screenResolution.y;
    // preview size is always something like 480*320, other 320*480
    if(screenResolution.x<screenResolution.y){
     screenResolutionForCamera.x=screenResolution.y;
     screenResolutionForCamera.y=screenResolution.x;
    }

    String previewSizeValueString=parameters.get("preview-size-values");
    // saw this on Xperia
    if(previewSizeValueString==null){
     previewSizeValueString=parameters.get("preview-size-value");
    }
    if(previewSizeValueString!=null){
     //Log.d(TAG, "preview-size-values parameter: " + previewSizeValueString);
     cameraResolution=findBestPreviewSizeValue(previewSizeValueString,screenResolutionForCamera);
    }
    if(cameraResolution==null){
     // Ensure that the camera resolution is a multiple of 8, as the screen may not be.
     cameraResolution=new Point(
       (screenResolution.x >> 3)<<3,
       (screenResolution.y >> 3)<<3);
    }
   }
  }
 }

  private static Point findBestPreviewSizeValue (CharSequence previewSizeValueString,Point screenResolution){
   int bestX=0;
   int bestY=0;
   int diff=Integer.MAX_VALUE;
   for(String previewSize : previewSizeValueString.toString().split(",")) {

    previewSize=previewSize.trim();
    int dimPosition=previewSize.indexOf('x');
    if(dimPosition<0){
     //Log.w(TAG, "Bad preview-size: " + previewSize);
     continue;
    }

    int newX;
    int newY;
    try{
     newX=Integer.parseInt(previewSize.substring(0,dimPosition));
     newY=Integer.parseInt(previewSize.substring(dimPosition+1));
    }catch(NumberFormatException nfe){
     continue;
    }

    int newDiff=Math.abs(newX-screenResolution.x)+Math.abs(newY-screenResolution.y);
    if(newDiff==0){
     bestX=newX;
     bestY=newY;
     break;
    }else if(newDiff<diff){
     bestX=newX;
     bestY=newY;
     diff=newDiff;
    }
   }
   if(bestX>0&&bestY>0){
    return new Point(bestX,bestY);
   }
   return null;
  }
//
// private Rect getFramingRect() {
//  Point screenResolution = configManager.getScreenResolution();
//  if (framingRect == null) {
//   if (camera == null) {
//    return null;
//   }
//
//   DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//   int width = (int) (metrics.widthPixels * 0.6);
//   int height = (int) (width * 0.9);
//
//   int leftOffset = (screenResolution.x - width) / 2;
//   int topOffset = (screenResolution.y - height) / 3;
//   framingRect = new Rect(leftOffset, topOffset, leftOffset + width,
//     topOffset + height);
//
//  }
//  return framingRect;
// }
//
// /**
//  * Like {@link #getFramingRect} but coordinates are in terms of the preview frame,
//  * not UI / screen.
//  */
// private Rect getFramingRectInPreview() {
//  if (framingRectInPreview == null) {
//   Rect rect = new Rect(getFramingRect());
//   Point cameraResolution = configManager.getCameraResolution();
//   Point screenResolution = configManager.getScreenResolution();
//   //The reason of rotation is that camera rotate to 90
//   rect.left = rect.left * cameraResolution.y / screenResolution.x;
//   rect.right = rect.right * cameraResolution.y / screenResolution.x;
//   rect.top = rect.top * cameraResolution.x / screenResolution.y;
//   rect.bottom = rect.bottom * cameraResolution.x / screenResolution.y;
//   framingRectInPreview = rect;
//  }
//  return framingRectInPreview;
// }
}
