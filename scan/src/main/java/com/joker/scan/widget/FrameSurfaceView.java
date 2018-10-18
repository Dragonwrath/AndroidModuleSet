package com.joker.scan.widget;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.joker.scan.core.ViewDelegate;

public class FrameSurfaceView extends SurfaceView implements ViewDelegate, SurfaceHolder.Callback{

 private Rect mFrameRect;
 private @ColorInt int maskColor;
 private @ColorInt int frameColor;
 private @ColorInt int cornerColor;
 private Paint mPaint;

 private final Runnable refreshRunnable=new Runnable(){
  @Override public void run(){
   final SurfaceHolder holder=getHolder();
   Canvas canvas=null;
   try{
    canvas=holder.lockCanvas();
    draw(canvas);
   }finally{
    if(canvas!=null){
     holder.unlockCanvasAndPost(canvas);
    }
   }
  }
 };

 public final HandlerThread thread=new HandlerThread(FrameSurfaceView.class.getName());
 private final Handler refreshHandler=new Handler(thread.getLooper());

 public FrameSurfaceView(Context context,@Nullable AttributeSet attrs,int defStyleAttr){
  super(context,attrs,defStyleAttr);
 }

 @RequiresApi(api=Build.VERSION_CODES.LOLLIPOP)
 public FrameSurfaceView(Context context,
                         @Nullable AttributeSet attrs,int defStyleAttr,int defStyleRes){
  super(context,attrs,defStyleAttr,defStyleRes);
 }

 private void init(Context context,@Nullable AttributeSet attrs){
  Resources resources=context.getResources();
  getHolder().addCallback(this);
  frameColor=Color.parseColor("#60000000");
  maskColor=Color.parseColor("#60000000");
  cornerColor=Color.parseColor("#ff4891");
  mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
  mFrameRect=new Rect();
 }

 @Override protected void onDraw(Canvas canvas){
  super.onDraw(canvas);
  int width=getWidth();
  int height=getHeight();
  drawMask(canvas,width,height);
 }

 private void drawMask(Canvas canvas,int width,int height){
  mPaint.setColor(maskColor);
  mPaint.setStyle(Paint.Style.FILL);
  canvas.drawRect(0,0,width,mFrameRect.top,mPaint);
  canvas.drawRect(0,mFrameRect.top,mFrameRect.left,mFrameRect.bottom,mPaint);
  canvas.drawRect(mFrameRect.right+1,mFrameRect.top,width,mFrameRect.bottom,mPaint);
  canvas.drawRect(0,mFrameRect.bottom,width,height,mPaint);
 }

 private void drawCorner(){
  //todo
 }

 private void drawFrame(){

 }

 @Override public Context getViewContext(){
  return getContext();
 }

 @Override public @NonNull SurfaceHolder getSurfaceHolder(){
  return getHolder();
 }

 @Override public void onPreviewFrameChanged(Rect rect){
  if(checkRectValidate(rect)){
   mFrameRect=rect;
   refreshHandler.removeCallbacks(refreshRunnable);
   refreshHandler.post(refreshRunnable);
  }
 }

 @Override public void surfaceCreated(SurfaceHolder holder){
  thread.start();
  refreshHandler.removeCallbacks(refreshRunnable);
 }

 @Override public void surfaceChanged(SurfaceHolder holder,int format,int width,int height){
  refreshHandler.post(refreshRunnable);
 }

 @Override public void surfaceDestroyed(SurfaceHolder holder){
  refreshHandler.removeCallbacks(refreshRunnable);
  thread.quitSafely();
 }

 private boolean checkRectValidate(Rect rect) {
  return rect.left>0&&rect.top>0&&rect.right<getWidth()&&rect.bottom<getHeight();
 }

}
