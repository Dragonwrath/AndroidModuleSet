package com.joker.main.share;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.lang.ref.WeakReference;

import com.joker.main.share.bean.ImageShareBean;
import com.joker.main.share.bean.MultiObjectShareBean;
import com.joker.main.share.bean.TextShareBean;
import com.joker.main.share.bean.VideoShareBean;
import com.joker.main.share.bean.WebShareBean;

/**
 * 原生的Intent分享只支持特定的几种类型，
 * 一种是{@link Intent#EXTRA_TEXT Intent.EXTRA_TEXT}
 * 一种是{@link Intent#EXTRA_STREAM Intent.EXTRA_STREAM}
 * 对应的Type类型，参考下面的方法，另外需要记住的是最好进行相应的参数校验
 * 比如，文件大小，文件名称的长度等等
 */
public class IntentShareAction implements Share{
 private WeakReference<Activity> mActRef;
 private WeakReference<Fragment> mFrgRef;

 public IntentShareAction(Activity activity){
  mActRef=new WeakReference<>(activity);
 }

 public IntentShareAction(Fragment fragment){
  mFrgRef=new WeakReference<>(fragment);

 }

 @Override
 public void sendText(TextShareBean bean) throws IllegalArgumentException{
  Intent intent=new Intent();
  intent.setAction(Intent.ACTION_SEND);
  intent.putExtra(Intent.EXTRA_TEXT,bean.getMessage());
  intent.setType("text/plain");
  startActivityProxy(intent);
 }

 @Override
 public void sendWebPage(WebShareBean bean) throws IllegalArgumentException{
  //todo not support
  throw new RuntimeException("this action is not supported yet.");
 }

 @Override
 public void sendImage(ImageShareBean bean) throws IllegalArgumentException{
  Intent shareIntent=new Intent();
  shareIntent.setAction(Intent.ACTION_SEND);
  Cursor cursor=null;
  long realId=0L;
  try{
   cursor=mActRef.get().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
     new String[]{MediaStore.Images.Media._ID},MediaStore.Images.Media.DATA+" like \"%"+bean.getFile().getName()+"\"" ,null,null);
   if(cursor!=null&&cursor.moveToFirst()){
    realId=cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
   }
  }finally{
   if(cursor!=null)
    cursor.close();
  }
  Uri uriToImage=Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString()+"/"+realId);
  shareIntent.putExtra(Intent.EXTRA_STREAM,uriToImage);
//  shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,uris);
  shareIntent.setType("image/*");
  startActivityProxy(shareIntent);
 }

 @Override
 public void sendVideo(VideoShareBean bean) throws IllegalArgumentException{
  Intent shareIntent=new Intent();
  shareIntent.setAction(Intent.ACTION_SEND);
  Cursor cursor=null;
  long id=0L;
  try{
   cursor=mActRef.get().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
     new String[]{"_id"},"_data="+bean.getUrl(),null,null);
   if(cursor!=null&&cursor.moveToFirst()){
    id=cursor.getLong(cursor.getColumnIndex("_id"));
   }
  }finally{
   if(cursor!=null)
    cursor.close();
  }
  Uri uriToImage=Uri.parse(MediaStore.Video.Media.EXTERNAL_CONTENT_URI.toString()+"/"+id);
  shareIntent.putExtra(Intent.EXTRA_STREAM,uriToImage);
  shareIntent.setType("video/*");
  startActivityProxy(shareIntent);
 }

 @Override public void sendMultiObject(MultiObjectShareBean bean) throws IllegalArgumentException{
  //not support yet
 }

 private void startActivityProxy(Intent intent){
  if(mActRef.get()!=null){
   mActRef.get().startActivity(intent);
  }else if(mFrgRef.get()!=null){
   mFrgRef.get().startActivity(intent);
  }
 }

}
