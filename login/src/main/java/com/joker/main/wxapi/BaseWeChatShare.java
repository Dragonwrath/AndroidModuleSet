package com.joker.main.wxapi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;

import com.joker.main.Constant;
import com.joker.main.share.Share;
import com.joker.main.share.bean.ImageShareBean;
import com.joker.main.share.bean.MultiImageShareBean;
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
import java.io.IOException;
import java.lang.ref.WeakReference;

/***
 * 微信关于缩略图的限制大小为32768，30KB
 * 微信单张图片的大小不能超过10MB
 * 微信关于文件的名称长度不能超过1024
 */
abstract class BaseWeChatShare implements Share{

 private final static int THUMB_SIZE=150;

 private final IWXAPI mWxApi;

 private final WeakReference<Context> mContextReference;

 BaseWeChatShare(Context context){
  mContextReference=new WeakReference<>(context);
  this.mWxApi=WXAPIFactory.createWXAPI(context,Constant.WeChat.APP_ID,true);
  mWxApi.registerApp(Constant.WeChat.APP_ID);
 }

 abstract int getScene();

 @Override
 public void sendText(TextShareBean bean) throws IllegalArgumentException{
  String text=bean.getMessage();
  if(TextUtils.isEmpty(bean.getMessage())||bean.getMessage().length()>10240){
   throw new IllegalArgumentException("Not support message!!Message is null or empty, or more than 10240");
  }
  WXTextObject textObj=new WXTextObject();
  textObj.text=text;
  WXMediaMessage msg=new WXMediaMessage();
  msg.title=bean.getTitle();
  msg.mediaObject=textObj;
  msg.description=text;
  SendMessageToWX.Req req=new SendMessageToWX.Req();
  req.transaction=buildTransaction("text");
  req.message=msg;
  req.scene=getScene();
  mWxApi.sendReq(req);
 }

 /**
  * 因为图片的必须使用本地的进行分享，因此需要用到FileUri
  * 其中还需要区分，api25以上版本的FileProvider的问题
  * 此外，微信对于分享的图片的大小有限制，图片不能超过10M，
  * 文件名的长度不能超过1024，这些最好做校验，
  *
  * <strong>特别强调一点，如果这个file为内部存储空间的文件，应该将其保存到外部存储</strong>
  * 再分享，因为，第三方无法访问相应的数据源
  *
  * {@link com.tencent.mm.opensdk.modelmsg.WXImageObject}
  */
 @Override
 public void sendImage(ImageShareBean bean) throws IllegalArgumentException{
  File file=new File(bean.getPath());
  //todo should to compress
  //todo check are args
  if(file==null||!file.exists()||file.length()>10*1024*1024||!file.canRead()){
   throw new IllegalArgumentException("file not exist or file is too large or couldn't to read");
  }
  if(verifyFileIsInternal(file.getAbsolutePath())) {
   throw new IllegalArgumentException("file couldn't be internal file");
  }
  WXImageObject imgObj=new WXImageObject();
  imgObj.imagePath=file.getAbsolutePath();
  WXMediaMessage msg=new WXMediaMessage();
  msg.mediaObject=imgObj;
  if(TextUtils.isEmpty(bean.getTitle())) msg.title=bean.getTitle();
  SendMessageToWX.Req req=new SendMessageToWX.Req();
  req.transaction=buildTransaction("img");
  req.message=msg;
  req.scene=getScene();
  mWxApi.sendReq(req);
 }

 /**
  * 由于音频与视频都是相似的，因此，我们直接分享相应的文件即可
  * 所以，需要首先保存文件到本地，之后再进行相应的分享
  * 缩略图的大小必须小于32KB
  */
 @Override
 public void sendVideo(VideoShareBean bean) throws IllegalArgumentException{
  Bitmap bmp=null;
  Bitmap thumbBmp=null;
  //todo check are args
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
 public void sendWebPage(WebShareBean bean) throws IllegalArgumentException{
  Bitmap bmp=null;
  Bitmap thumbBmp=null;
  try{
   WXWebpageObject webPage=new WXWebpageObject();
   webPage.webpageUrl=bean.getUrl();
   if(!webPage.checkArgs()){
    throw new IllegalArgumentException("url maybe is null,or too long");
   }
   WXMediaMessage msg=new WXMediaMessage(webPage);
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

 @Override public void sendMultiObject(MultiImageShareBean bean) throws IllegalArgumentException{
  //not support
 }

 private String buildTransaction(final String type){
  return (type==null)?String.valueOf(System.currentTimeMillis()): type+System.currentTimeMillis();
 }

 private static byte[] bmpToByteArray(final Bitmap bmp) throws IllegalArgumentException{
  //todo 需要处理图片角度的问题
  ByteArrayOutputStream output=new ByteArrayOutputStream();
  try{
   int quality=100;
   while(output.size()==0||output.size()>32768){
    output.reset();
    bmp.compress(Bitmap.CompressFormat.JPEG,quality,output);
    quality-=10;
    try{
     output.flush();
    }catch(IOException e){
     e.printStackTrace();
    }
    if(quality==0){
     throw new IllegalArgumentException("thumb can't be compressed");
    }
   }
  }finally{
   bmp.recycle();
   try{
    output.flush();
    output.close();
   }catch(Exception e){
    e.printStackTrace();
   }
  }
  return output.toByteArray();
 }

 private boolean verifyFileIsInternal(String path) {
  Context context=mContextReference.get();
  if(context==null||TextUtils.isEmpty(path)) {
   return true;
  }
  String internal=context.getFilesDir().getAbsolutePath();
  return path.startsWith(internal);
 }
}
