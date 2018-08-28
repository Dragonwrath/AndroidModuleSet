package com.joker.common.utils.file;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

public class FileFactory{
  private FileFactory() {
    throw new AssertionError("This class can not be instantiated");
  }

  @Nullable
  public static File produceFile(File root,String fileName,String suffix){
    if(root==null|| root.isFile()||TextUtils.isEmpty(fileName)||TextUtils.isEmpty(suffix))
      return null;
    if(!root.exists()) {
      //noinspection all
      root.mkdirs();
    }
    File file=new File(root,fileName+suffix);
    try{
      file.deleteOnExit();
      //noinspection all
      file.createNewFile();
      return file;
    }catch(Exception e){
      return null;
    }
  }

  @Nullable
  public static File produceTmpFile(String root,String fileName,String suffix){
    File file=new File(root);
    return produceTmpFile(file,fileName,suffix);
  }

  @Nullable
  public static File produceTmpFile(File root,String fileName,@NonNull String suffix){
    if(root==null|| !root.isDirectory()) return null;
    if(!root.exists()) {
      if(!root.mkdirs()){
        return null;
      }
    }
    File file= null;
    try{
      file = File.createTempFile(
          fileName,  /* prefix */
          suffix,         /* suffix */
          root      /* directory */
      );
    }catch(IOException e){
      e.printStackTrace();
    }
    return file;
  }


  @Nullable
  public static Uri getFileUri(Context context,File file){
    if(file == null || context == null) return  null;
    Uri fileUri;
    if(!file.exists()){
      //noinspection all
      file.mkdir();
    }

    if(Build.VERSION.SDK_INT>=24){   //判断相应的版本使用相应的Uri
      fileUri=FileProvider.getUriForFile(context,
          context.getPackageName() +".fileprovider",
          file);
    }else{
      fileUri=Uri.fromFile(file);
    }
    return fileUri;

  }
}
