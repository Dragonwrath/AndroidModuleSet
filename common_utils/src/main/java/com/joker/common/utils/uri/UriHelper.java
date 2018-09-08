package com.joker.common.utils.uri;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;

/**
 * 来源于Glide 4.x源码，根据相应的uri判断来进行判断，从而获取对应的内容，可以参考的是实现是
 * AssetFileDescriptor或者是
 */
public class UriHelper{

 private static final String ASSET_PATH_SEGMENT="android_asset";

 private UriHelper(){
  throw new AssertionError("This class can not be instantiated!");
 }

 private InputStream translateUri(Uri uri,Context context) throws IOException, NullPointerException{
  final String scheme=uri.getScheme();
  if(isLocalUri(scheme)){
   AssetManager assetManager=context.getAssets();
   if(AssetUriParser.isAssetUri(uri)){
    String path=AssetUriParser.toAssetPath(uri);
    InputStream stream=assetManager.open(path);
    if(stream==null) throw new NullPointerException("assert file not found");
    return stream;
   }else{
    AssetFileDescriptor fileDescriptor=context.getContentResolver().openAssetFileDescriptor(uri,"r");
    if(fileDescriptor==null) throw new NullPointerException("file not found");
    return fileDescriptor.createInputStream();
   }
  }
  throw new NullPointerException();
 }

 private static boolean isLocalUri(String scheme){
  return ContentResolver.SCHEME_FILE.equals(scheme)
    ||ContentResolver.SCHEME_CONTENT.equals(scheme)
    ||ContentResolver.SCHEME_ANDROID_RESOURCE.equals(scheme);
 }

}
