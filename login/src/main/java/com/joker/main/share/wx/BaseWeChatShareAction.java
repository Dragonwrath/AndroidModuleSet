package com.joker.main.share.wx;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaDataSource;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.joker.main.Constant;
import com.joker.main.share.ShareAction;
import com.joker.main.share.bean.ImageShareBean;
import com.joker.main.share.bean.TextShareBean;
import com.joker.main.share.bean.VideoShareBean;
import com.joker.main.share.bean.WebShareBean;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.ref.WeakReference;

abstract class BaseWeChatShareAction implements ShareAction{

 private final static int THUMB_SIZE=150;

 private final IWXAPI mWxApi;

 private final WeakReference<Context> mContextReference;

 BaseWeChatShareAction(Context context){
  mContextReference=new WeakReference<>(context);
  this.mWxApi=WXAPIFactory.createWXAPI(context,Constant.WeChat.APP_ID,true);
  mWxApi.registerApp(Constant.WeChat.APP_ID);
 }

 abstract int getScene();

 @Override
 public void sendText(TextShareBean bean) throws IllegalArgumentException{
  if(TextUtils.isEmpty(bean.getMessage())){
   throw new IllegalArgumentException("can't support send empty message to WeChat");
  }
  String text=bean.getMessage();
  WXTextObject textObj=new WXTextObject();
  textObj.text=text;
  WXMediaMessage msg=new WXMediaMessage();
  msg.mediaObject=textObj;
  msg.description=text;
  SendMessageToWX.Req req=new SendMessageToWX.Req();
  req.transaction=buildTransaction("text");
  req.message=msg;
  req.scene=getScene();
  mWxApi.sendReq(req);
 }

 @Override
 public void sendImage(ImageShareBean bean) throws IllegalArgumentException{
  File file=bean.getFile();
  if(file==null||!file.exists()||file.length()>10*1024*1024||!file.canRead()){
   throw new IllegalArgumentException("file not exist or file is too large or couldn't to read");
  }
  Bitmap bmp=null;
  Bitmap thumbBmp=null;
  try{
   bmp=BitmapFactory.decodeFile(file.getAbsolutePath());
   WXImageObject imgObj=new WXImageObject(bmp);

   WXMediaMessage msg=new WXMediaMessage();
   msg.mediaObject=imgObj;
   if(TextUtils.isEmpty(bean.getTitle())) msg.title=bean.getTitle();

   //generate thumb
   thumbBmp=Bitmap.createScaledBitmap(bmp,THUMB_SIZE,THUMB_SIZE,true);
   msg.thumbData=bmpToByteArray(thumbBmp);
   SendMessageToWX.Req req=new SendMessageToWX.Req();
   req.transaction=buildTransaction("img");
   req.message=msg;
   req.scene=getScene();
   mWxApi.sendReq(req);
  }finally{
   if(bmp!=null)
    bmp.recycle();
   if(thumbBmp!=null)
    thumbBmp.recycle();
   if(Build.VERSION.SDK_INT<21){
    System.gc();
   }
  }
 }

 @Override
 public void sendVideo(VideoShareBean bean) throws IllegalArgumentException{
  Bitmap bmp=null;
  Bitmap thumbBmp=null;
  try{
   WXVideoObject video=new WXVideoObject();
   video.videoUrl=bean.getUrl();
   if(!video.checkArgs()){
    throw new IllegalArgumentException("one of videoUrl and lowBandUrl url ,maybe is null, or too long");
   }
   WXMediaMessage msg=new WXMediaMessage(video);
   msg.title=bean.getTitle();
   msg.description=bean.getMessage();
   bmp=BitmapFactory.decodeFile(bean.getThumb());
   thumbBmp=Bitmap.createScaledBitmap(bmp,THUMB_SIZE,THUMB_SIZE,true);
   msg.thumbData=bmpToByteArray(thumbBmp);
   SendMessageToWX.Req req=new SendMessageToWX.Req();
   req.transaction=buildTransaction("video");
   req.message=msg;
   req.scene=getScene();
   mWxApi.sendReq(req);
  } finally{
   if(bmp!=null)
    bmp.recycle();
   if(thumbBmp!=null)
    thumbBmp.recycle();
   if(Build.VERSION.SDK_INT<21){
    System.gc();
   }
  }
 }

 @Override
 public void sendWebPage(WebShareBean bean) throws IllegalArgumentException{
  Bitmap bmp=null;
  Bitmap thumbBmp=null;
  try{
   WXWebpageObject webpage=new WXWebpageObject();
   webpage.webpageUrl=bean.getUrl();
   if(!webpage.checkArgs()) {
    throw new IllegalArgumentException("url maybe is null,or too long");
   }
   WXMediaMessage msg=new WXMediaMessage(webpage);
   msg.title=bean.getTitle();
   msg.description=bean.getMessage();
   bmp=BitmapFactory.decodeFile(bean.getThumb());
   thumbBmp=Bitmap.createScaledBitmap(bmp,THUMB_SIZE,THUMB_SIZE,true);
   msg.thumbData=bmpToByteArray(thumbBmp);

   SendMessageToWX.Req req=new SendMessageToWX.Req();
   req.transaction=buildTransaction("webpage");
   req.message=msg;
   req.scene=getScene();
   mWxApi.sendReq(req);
  } finally{
   if(bmp!=null)
    bmp.recycle();
   if(thumbBmp!=null)
    thumbBmp.recycle();
   if(Build.VERSION.SDK_INT<21){
    System.gc();
   }
  }
 }

 private String buildTransaction(final String type){
  return (type==null)?String.valueOf(System.currentTimeMillis()): type+System.currentTimeMillis();
 }

 private static byte[] bmpToByteArray(final Bitmap bmp){
  ByteArrayOutputStream output=new ByteArrayOutputStream();
  try{
   bmp.compress(Bitmap.CompressFormat.PNG,100,output);
  }finally{
   bmp.recycle();
   try{
    output.close();
   }catch(Exception e){
    e.printStackTrace();
   }
  }
  return output.toByteArray();
 }
}
