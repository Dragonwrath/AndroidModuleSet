package com.joker.http.connection.request;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.joker.http.core.header.HeadersConstant;
import com.joker.http.core.utils.PreConditions;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;

public class FilePostRequest extends BaseRequest<File>{

 private FilePostRequest(){
 }

 public static class Builder extends BaseRequest.Builder<File,Builder> {

  public Builder(){
   request=new FilePostRequest();
   addHead(HeadersConstant.HEAD_KEY_CONTENT_TYPE,"application/octet-stream");
  }

  @Override public Builder addBody(
    @Nullable String key,@NonNull Object value) throws IllegalArgumentException{
   PreConditions.requestObjectNotNull(value,"file can not be null");
   File file;
   if(value instanceof String) {
    file=new File((String)value);
   } else if(value instanceof File) {
    file=(File)value;
   } else {
    throw new IllegalArgumentException("just support file or file path");
   }
   if(!file.exists()) throw new IllegalArgumentException("file not exist");
   translateToType(file.getName());
   return super.addBody(EMPTY_KEY,value);
  }

  @Override public BaseRequest<File> build(){
   return super.build();
  }

  private void translateToType(String fileName) throws IllegalArgumentException{
   int index=fileName.lastIndexOf(File.separator);
   String realName=fileName;
   if(index>0) {
    realName=fileName.substring(index,fileName.length());
   }
   FileNameMap fileNameMap = URLConnection.getFileNameMap();
   String contentType = fileNameMap.getContentTypeFor(realName);
   if(contentType == null){
    throw new IllegalArgumentException("not support file type");
   } else {
    addHead(HeadersConstant.HEAD_KEY_CONTENT_DISPOSITION,"filename="+realName);
    addHead(HeadersConstant.HEAD_KEY_CONTENT_TYPE,contentType);
   }
  }
 }
}
