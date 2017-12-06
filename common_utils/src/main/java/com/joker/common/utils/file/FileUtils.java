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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtils{
  private FileUtils() {
    throw new AssertionError("This class can not be instantiated");
  }

  @Nullable
  public static File produceFile(File root,String fileName,@NonNull String suffix){
    String realName=new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(new Date());
    if(!TextUtils.isEmpty(fileName)){
      realName += fileName;
    }
    File file= null;
    try{
      file = File.createTempFile(
          realName,  /* prefix */
          suffix,         /* suffix */
          root      /* directory */
      );
    }catch(IOException e){
      e.printStackTrace();
    }
    return file;
  }


  @Nullable
  public static Uri getFileUri(@NonNull Context context,@NonNull File file){
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
