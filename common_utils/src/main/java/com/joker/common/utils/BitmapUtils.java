package com.joker.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BitmapUtils{
  private final static int MAX_SIZE=1<<20;

  private BitmapUtils(){
    throw new AssertionError("This class can not be instantiated!");
  }

  public static boolean compressBitmap(InputStream in,OutputStream out) throws Exception{
    //define this required image size
    float maxHeight=1920f;
    float maxWidth=1080f;
    BitmapFactory.Options options=new BitmapFactory.Options();
    options.inJustDecodeBounds=true;
    Bitmap scaledBitmap=null;
    if(Build.VERSION.SDK_INT<19&&in.markSupported()){
      in.mark(1024);
    }
    //Must wrap InputStream, otherwise InputStream#reset() may throw Exception;
    ByteArrayInputStream copyIn=wrapInputStream(in);
    Bitmap bitmap=BitmapFactory.decodeStream(copyIn,new Rect(0,0,0,0),options);
    int actualHeight=options.outHeight;
    int actualWidth=options.outWidth;
    float imgRatio=(float)actualWidth/(float)actualHeight;
    float maxRatio=maxWidth/maxHeight;
    if(actualHeight>maxHeight||actualWidth>maxWidth){
      if(imgRatio<maxRatio){
        imgRatio=maxHeight/actualHeight;
        actualWidth=(int)(imgRatio*actualWidth);
        actualHeight=(int)maxHeight;
      }else if(imgRatio>maxRatio){
        imgRatio=maxWidth/actualWidth;
        actualHeight=(int)(imgRatio*actualHeight);
        actualWidth=(int)maxWidth;
      }else{
        actualHeight=(int)maxHeight;
        actualWidth=(int)maxWidth;
      }
    }
    options.inJustDecodeBounds=false;
    options.inSampleSize=calculateInSampleSize(options,actualWidth,actualHeight);
    try{
      if(copyIn!=null){
        //we BitmapFactory.decodeStream() with copyIn before, so that we should reset this instance
        //of InputStream
        copyIn.reset();
        bitmap=BitmapFactory.decodeStream(copyIn,null,options);
        scaledBitmap=Bitmap.createBitmap(actualWidth,actualHeight,Bitmap.Config.ARGB_8888);
        if(scaledBitmap!=null){
          float ratioX=actualWidth/(float)options.outWidth;
          float ratioY=actualHeight/(float)options.outHeight;
          float middleX=actualWidth/2.0f;
          float middleY=actualHeight/2.0f;
          Matrix scaleMatrix=new Matrix();
          scaleMatrix.setScale(ratioX,ratioY,middleX,middleY);
          Canvas canvas=new Canvas(scaledBitmap);
          canvas.setMatrix(scaleMatrix);
          canvas.drawBitmap(bitmap,middleX-bitmap.getWidth()/2,middleY-bitmap.getHeight()/2,new Paint(Paint.FILTER_BITMAP_FLAG));
          scaledBitmap=Bitmap.createBitmap(scaledBitmap,0,0,scaledBitmap.getWidth(),scaledBitmap.getHeight(),new Matrix(),true);
          int quality=100;
          //CompressFormat must use JPEG or other,But Must Not use PNG,
          // because PNG couldn't compress with quality
          scaledBitmap.compress(Bitmap.CompressFormat.JPEG,quality,out);
          Bitmap.Config config=bitmap.getConfig();
          int perByte=1;
          switch(config){
            case ARGB_4444:
              perByte=2;
              break;
            case ARGB_8888:
              perByte=4;
              break;
            case ALPHA_8:
              perByte=1;
              break;
            case RGB_565:
              perByte=2;
              break;
          }
          long size=scaledBitmap.getWidth()*scaledBitmap.getHeight()*perByte;
          int actualSize=(int)(100*(MAX_SIZE*1f/size));
          if(actualSize>100){
            actualSize=100;
          }else if(actualSize<10){
            actualSize=10;
          }
          return scaledBitmap.compress(Bitmap.CompressFormat.JPEG,actualSize,out);
        }
      }
    }finally{
      try{
        if(copyIn!=null)
          copyIn.close();
        out.flush();
        out.close();
      }catch(IOException e){
        e.printStackTrace();
      }
      if(bitmap!=null&&!bitmap.isRecycled()){
        bitmap.recycle();
      }
      if(scaledBitmap!=null&&!scaledBitmap.isRecycled()){
        scaledBitmap.recycle();
      }
    }
    return false;
  }

  public static void compressBitmap(Context context,String srcPath,File file) throws Exception{
    if(context==null||TextUtils.isEmpty(srcPath)) throw new IllegalArgumentException();
    float maxHeight=1920.0f;
    float maxWidth=1080.0f;
    Bitmap scaledBitmap=null;
    Bitmap bmp=null;
    OutputStream out=null;
    ExifInterface exif=null;
    try{
      BitmapFactory.Options options=new BitmapFactory.Options();
      options.inJustDecodeBounds=true;
      BitmapFactory.decodeFile(srcPath,options);

      int actualHeight=options.outHeight;
      int actualWidth=options.outWidth;
      float imgRatio=(float)actualWidth/(float)actualHeight;
      float maxRatio=maxWidth/maxHeight;

      if(actualHeight>maxHeight||actualWidth>maxWidth){
        if(imgRatio<maxRatio){
          imgRatio=maxHeight/actualHeight;
          actualWidth=(int)(imgRatio*actualWidth);
          actualHeight=(int)maxHeight;
        }else if(imgRatio>maxRatio){
          imgRatio=maxWidth/actualWidth;
          actualHeight=(int)(imgRatio*actualHeight);
          actualWidth=(int)maxWidth;
        }else{
          actualHeight=(int)maxHeight;
          actualWidth=(int)maxWidth;
        }
      }
      options.inSampleSize=calculateInSampleSize(options,actualWidth,actualHeight);
      options.inJustDecodeBounds=false;
      options.inDither=false;
      options.inPurgeable=true;
      options.inInputShareable=true;
      options.inTempStorage=new byte[16*1024];

      bmp=BitmapFactory.decodeFile(srcPath,options);
      scaledBitmap=Bitmap.createBitmap(actualWidth,actualHeight,Bitmap.Config.ARGB_8888);
      float ratioX=actualWidth/(float)options.outWidth;
      float ratioY=actualHeight/(float)options.outHeight;
      float middleX=actualWidth/2.0f;
      float middleY=actualHeight/2.0f;

      Matrix scaleMatrix=new Matrix();
      scaleMatrix.setScale(ratioX,ratioY,middleX,middleY);
      Canvas canvas=new Canvas(scaledBitmap);
      canvas.setMatrix(scaleMatrix);
      canvas.drawBitmap(bmp,middleX-bmp.getWidth()/2,middleY-bmp.getHeight()/2,new Paint(Paint.FILTER_BITMAP_FLAG));

      exif=new ExifInterface(srcPath);
      int orientation=exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,0);
      Matrix matrix=new Matrix();
      if(orientation==6){
        matrix.postRotate(90);
      }else if(orientation==3){
        matrix.postRotate(180);
      }else if(orientation==8){
        matrix.postRotate(270);
      }
      scaledBitmap=Bitmap.createBitmap(scaledBitmap,0,0,scaledBitmap.getWidth(),scaledBitmap.getHeight(),matrix,true);

      if(file!=null){
        out=new FileOutputStream(file);
//        Bitmap.Config config=scaledBitmap.getConfig();
//        int perByte=1;
//        switch(config){
//          case ARGB_4444:
//            perByte=2;
//            break;
//          case ARGB_8888:
//            perByte=4;
//            break;
//          case ALPHA_8:
//            perByte=1;
//            break;
//          case RGB_565:
//            perByte=2;
//            break;
//        }
//        long size=scaledBitmap.getWidth()*scaledBitmap.getHeight()*perByte;
//        int actualSize=(int)(100*(MAX_SIZE*1f/size));
//        if(actualSize>100){
//          actualSize=100;
//        }else if(actualSize<10){
//          actualSize=10;
//        }
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG,85,out);
      }
    }finally{
      if(bmp!=null&&!bmp.isRecycled()){
        bmp.recycle();
      }
      if(scaledBitmap!=null&&!scaledBitmap.isRecycled()){
        scaledBitmap.recycle();
      }
      if(out!=null){
        try{
          out.flush();
          out.close();
        }catch(Exception e){
          //ignore
        }
      }
    }
  }

  @Nullable
  private static ByteArrayInputStream wrapInputStream(InputStream in) throws Exception{
    if(in!=null){
      ByteArrayOutputStream out=null;
      try{
        out=new ByteArrayOutputStream();
        byte[] bytes=new byte[256];
        int len;
        while((len=in.read(bytes))>0){
          out.write(bytes,0,len);
        }
        return new ByteArrayInputStream(out.toByteArray());
      }finally{
        if(out!=null){
          try{
            in.close();
            out.close();
          }catch(IOException e){
            e.printStackTrace();
          }
        }
      }
    }
    return null;
  }

  private static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
    final int height=options.outHeight;
    final int width=options.outWidth;
    int inSampleSize=1;

    if(height>reqHeight||width>reqWidth){
      final int heightRatio=Math.round((float)height/(float)reqHeight);
      final int widthRatio=Math.round((float)width/(float)reqWidth);
      inSampleSize=heightRatio<widthRatio?heightRatio:widthRatio;
    }
    final float totalPixels=width*height;
    final float totalReqPixelsCap=reqWidth*reqHeight*2;

    while(totalPixels/(inSampleSize*inSampleSize)>totalReqPixelsCap){
      inSampleSize++;
    }
    return inSampleSize;
  }
}
