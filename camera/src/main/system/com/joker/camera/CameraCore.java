package com.joker.camera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ImageView;

import com.joker.common.utils.file.FileUtils;

import java.io.File;
import java.io.IOException;

abstract class CameraCore<Host>{
  private Uri mUri;
  private File mFile;
  final Host mHost;
  Params mParams;

  CameraCore(Host host,Params params){
    mHost=host;
    mParams=params;
  }

  void openCamera() throws CameraOpenFailedException{
    PackageManager manager=getContext().getPackageManager();
    if(!manager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
      throw new CameraOpenFailedException(getContext().getString(R.string.camera_unusable));
    }
    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if(intent.resolveActivity(manager)==null){
      throw new CameraOpenFailedException(getContext().getString(R.string.camera_open_failed));
    }
    if(!mParams.useThumbnail){
      storageExternalPath(mParams.useExternal);
      if(mUri!=null){
        intent.putExtra(MediaStore.EXTRA_OUTPUT,mUri);
      }else{
        throw new CameraOpenFailedException(getContext().getString(R.string.external_write_failed));
      }
    }
    startActivityProxy(intent);
  }

  private void storageExternalPath(boolean useExternal){
    File root;
    if(useExternal){
      root=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    }else{
      if(Build.VERSION.SDK_INT>=18){
        root=getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
      } else {
        root=getContext().getFilesDir();
      }
    }
    if(!TextUtils.isEmpty(mParams.root)){
      root = new File(root,mParams.root);
    }
    mFile=FileUtils.produceTmpFile(root,mParams.fileName,mParams.suffix);
    if(mFile!=null) mUri=FileUtils.getFileUri(getContext(),mFile);
  }

  void handleActivityResult(Intent intent,ImageView image){
    if(mParams.useThumbnail){
      Bundle extras=intent.getExtras();
      Bitmap bitmap=(Bitmap)extras.get("data");
      image.setImageBitmap(bitmap);
    }else{
      addPicToView(image);
    }
  }

  Uri getOutputUri(){
    return mUri;
  }

  private void addPicToView(ImageView view){
    try{
      ExifInterface info=new ExifInterface(mFile.getAbsolutePath());
      int orientation=info.getAttributeInt(ExifInterface.TAG_ORIENTATION,0);
      int degree=0;
      if(orientation!=0)
        switch(orientation){
          case 2:
          case 3:
            degree=180;
            break;
          case 5:
          case 6:
            degree=90;
            break;
          case 7:
          case 8:
            degree=270;
            break;
        }
      calculateBitmapV2(degree,view);
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  private void calculateBitmapV2(int degree, ImageView view) {
    String path = mFile.getAbsolutePath();
    Matrix matrix=new Matrix();
    matrix.postRotate(degree);
    int targetW=view.getMeasuredWidth();
    int targetH=view.getMeasuredHeight();
    BitmapFactory.Options options=new BitmapFactory.Options();
    options.inJustDecodeBounds=true;
    BitmapFactory.decodeFile(path,options);
    int photoW=options.outWidth;
    int photoH=options.outHeight;
    int scaleFactor=Math.min(photoW/targetW,photoH/targetH);
    options.inJustDecodeBounds=false;
    options.inSampleSize=scaleFactor;
    options.inPurgeable=true;
    Bitmap bitmap=BitmapFactory.decodeFile(path,options);
    Bitmap dst=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,false);
    matrix.reset();
    bitmap.recycle();
    matrix.postScale(targetW*1.0f/dst.getWidth(), targetH*1.0f/dst.getHeight());
    dst=Bitmap.createBitmap(dst,0,0,dst.getWidth(),dst.getHeight(),matrix,false);
    view.setImageBitmap(dst);
  }


  abstract Context getContext();

  abstract void startActivityProxy(Intent intent);

}
