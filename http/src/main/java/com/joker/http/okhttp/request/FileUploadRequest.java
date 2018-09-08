package com.joker.http.okhttp.request;
import com.joker.http.core.header.HeadersConstant;
import com.joker.http.core.utils.PreConditions;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FileUploadRequest extends PostRequest<File>{
 private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

 @Override
 public PostRequest body(File file) throws IllegalArgumentException {
  PreConditions.requireFileNotNull(file);
  verifyFile(file);
  value=file;
  return this;
 }

 @Override
 RequestBody getBody(){
  MediaType mediaType=translateToType(value.getName());
  System.out.println(mediaType);
  addHead(HeadersConstant.HEAD_KEY_ACCEPT,mediaType.toString());
  addHead("Content-length",String.valueOf(value.length()));
  return new ProgressRequestBody(mediaType,value);
 }

 @Override
 public PostRequest addBody(String key,Object value){
  //nothing
  return this;
 }

 private MediaType translateToType(String fileName) {
  FileNameMap fileNameMap = URLConnection.getFileNameMap();
  fileName = fileName.replace("#", "");
  String contentType = fileNameMap.getContentTypeFor(fileName);
  return contentType == null?MEDIA_TYPE_STREAM:MediaType.parse(contentType);
 }

 private void verifyFile(File file){
  String path=file.getAbsolutePath();
  if(path.contains("#")) throw new IllegalArgumentException("File contains illegal");
  if(path.length()>1024) throw new IllegalArgumentException("File's name is too long");
  if(file.length()>10*1024*1024) throw new IllegalArgumentException("File is too large");
 }

}
