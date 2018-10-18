package com.joker.scan;
import android.arch.lifecycle.LifecycleObserver;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.joker.scan.core.CameraManager;
import com.joker.scan.core.DecodeManager;
import com.joker.scan.core.OnScanResultListener;
import com.joker.scan.impl.CameraManagerImpl;
import com.joker.scan.impl.DecodeManagerImpl;
import com.joker.scan.listener.SoundPlayer;
import com.joker.scan.listener.VibratePlayer;
import com.joker.scan.widget.FrameSurfaceView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class QrScanManagerImpl implements QrScanManager, LifecycleObserver{
 private WeakReference<AppCompatActivity> mWeakReference;
 private CameraManager cameraManager;
 private DecodeManager decodeManager;
 private ScanListenerWrapper listener;

 private QrScanManagerImpl(AppCompatActivity activity){
  mWeakReference=new WeakReference<>(activity);
  decodeManager=new DecodeManagerImpl(activity);
 }

 @Override public void onStart(){
  try{
   cameraManager.openCamera();
  }catch(Exception ex){
   listener.onScanFailure(ex);
  }
 }

 @Override public void onStop(){
  cameraManager.closeCamera();
 }

 @Override public void onDestroy(){
  decodeManager.onDestroy();
 }

 @Override public void switchLight(){
  cameraManager.switchFlashLight();
 }

 private static class ScanListenerWrapper implements OnScanResultListener{

  private final static int TYPE_SOUND=0;
  private final static int TYPE_VIBRATE=1;

  private final ArrayList<OnScanResultListener> list;

  private ScanListenerWrapper(){
   list=new ArrayList<>();
  }

  @Override public void onScanSuccess(String message){
   for(OnScanResultListener listener : list) {
    listener.onScanSuccess(message);
   }
  }

  @Override public void onScanFailure(Exception ex){
   for(OnScanResultListener listener : list) {
    listener.onScanFailure(ex);
   }
  }
 }

 public static class Builder implements QrScanManager.Builder{
  private WeakReference<AppCompatActivity> mWeakReference;
  private QrScanManagerImpl manager;

  public Builder(@NonNull AppCompatActivity activity){
   mWeakReference=new WeakReference<>(activity);
   manager=new QrScanManagerImpl(activity);
  }

  @Override public QrScanManager.Builder supportSensor(boolean shouldSupport){
   return this;
  }

  @Override public QrScanManager.Builder surfaceView(FrameSurfaceView view){
   if(manager.cameraManager==null){
    manager.cameraManager=new CameraManagerImpl();
   }
   manager.cameraManager.bindViewDelegate(view);
   return this;
  }

  @Override public QrScanManager.Builder addListener(OnScanResultListener listener){
   if(manager.listener==null){
    manager.listener=new ScanListenerWrapper();
   }
   manager.listener.list.add(listener);
   return this;
  }

  @Override public QrScanManager.Builder requireSound(){
   manager.listener.list.add(ScanListenerWrapper.TYPE_SOUND,new SoundPlayer(mWeakReference.get()));
   return this;
  }

  @Override public QrScanManager.Builder requireVibrate(){
   manager.listener.list.add(ScanListenerWrapper.TYPE_VIBRATE,new VibratePlayer(mWeakReference.get()));
   return this;
  }

  @Override public QrScanManager create(){
   if(manager.cameraManager==null){
    throw new IllegalStateException("must call surfaceHolder(SurfaceHolder)first");
   }
   if(manager.listener==null){
    throw new IllegalStateException("Listener should be added by addListener(OnScanResultListener) at least");
   }
   manager.cameraManager.bindDecodeManager(manager.decodeManager);
   return manager;
  }
 }
}
