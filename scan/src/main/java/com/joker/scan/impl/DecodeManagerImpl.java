package com.joker.scan.impl;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Handler;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.joker.scan.core.DecodeManager;
import com.joker.scan.core.OnScanResultListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class DecodeManagerImpl implements DecodeManager, OnScanResultListener{

 private final Handler handler;
 private OnScanResultListener listener;
 private final static Reader mReader=new QRCodeReader();
 private ExecutorService mDecodeService=Executors.newFixedThreadPool(3);

 public DecodeManagerImpl(Activity activity){
  handler=new Handler(activity.getMainLooper());
 }

 @Override public void registerScanListener(OnScanResultListener listener){
  this.listener=listener;
 }

 @Override public void decode(byte[] data,int width,int height,Rect frameRect){
  mDecodeService.submit(new QrDecodeRunnable(data,width,height,frameRect,listener));
 }

 @Override public void onDestroy(){
  mDecodeService.shutdown();
 }

 @Override public void onScanSuccess(final String message){
  try{
   mDecodeService.shutdownNow();
  }catch(Exception ex){
   //noting
  }finally{
   handler.post(new Runnable(){
    @Override public void run(){
     listener.onScanSuccess(message);
    }
   });
  }
 }

 @Override public void onScanFailure(Exception ex){
  //nothing
  if(!(ex instanceof ReaderException)){
   listener.onScanFailure(ex);
  }
 }

 private static class QrDecodeRunnable implements Runnable{
  private byte[] data;
  private int width, height;
  private final Rect rect;
  private final OnScanResultListener listener;

  private QrDecodeRunnable(byte[] data,int width,int height,Rect rect,OnScanResultListener listener){
   this.data=data;
   this.width=width;
   this.height=height;
   this.rect=rect;
   this.listener=listener;
  }

  @Override public void run(){
   //modify here
   byte[] rotatedData=new byte[data.length];
   for(int y=0;y<height;y++) {
    for(int x=0;x<width;x++)
     rotatedData[x*height+height-y-1]=data[x+y*width];
   }
   int tmp=width; // Here we are swapping, that's the difference to #11
   //noinspection all
   width=height;
   height=tmp;
   PlanarYUVLuminanceSource source=new PlanarYUVLuminanceSource(rotatedData,width,height,rect.left,rect.top,
     rect.width(),rect.height(),false);
   BinaryBitmap bitmap=new BinaryBitmap(new HybridBinarizer(source));
   try{
    Result result=mReader.decode(bitmap);
    if(result!=null){
     listener.onScanSuccess(result.getText());
    }
   }catch(Exception ex){
    // continue
    listener.onScanFailure(ex);
   }finally{
    mReader.reset();
   }
  }
 }
}
