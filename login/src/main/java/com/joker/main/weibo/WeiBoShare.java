package com.joker.main.weibo;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.joker.main.exceptions.ShareParamsException;
import com.joker.main.share.Share;
import com.joker.main.share.bean.ImageShareBean;
import com.joker.main.share.bean.MultiImageShareBean;
import com.joker.main.share.bean.TextShareBean;
import com.joker.main.share.bean.VideoShareBean;
import com.joker.main.share.bean.WebShareBean;
import com.joker.main.utils.ShareParamExaminer;
import com.sina.weibo.sdk.api.BaseMediaObject;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoSourceObject;
import com.sina.weibo.sdk.api.WebpageObject;

import java.lang.ref.WeakReference;

public class WeiBoShare implements Share{

 private WeakReference<Activity> mActivityRef;

 public WeiBoShare(Activity activity){
  mActivityRef=new WeakReference<>(activity);
 }

 /**
  * 限制
  * title 小于512b
  * text  小于 1024b
  *
  */
 @Override public void sendText(TextShareBean bean) throws IllegalArgumentException{
  String title=bean.getTitle();
  String message=bean.getMessage();
  if(title!=null&&title.length()>512) {
   throw new IllegalArgumentException("title is too long");
  }
  if(message==null||message.isEmpty()||message.length()>1024) {
   throw new IllegalArgumentException("message is empty,or too long");
  }
  TextObject object=new TextObject();
  object.title=title;
  object.text=message;
  WeiBoShareActivity.startActivity(mActivityRef.get(),BaseMediaObject.MEDIA_TYPE_TEXT,object);
 }

 /**
  * actionUrl <  512 b
  * identity < 512 b
  * thumb < 32768 b
  * title < 512 b
  * description < 1024 b
  */
 @Override public void sendWebPage(WebShareBean bean) throws IllegalArgumentException{
  String thumb=bean.getThumb();
  String title=bean.getTitle();
  String url=bean.getUrl();
  String content=bean.getMessage();
  if(url==null||url.isEmpty()||url.length()>512) {
   throw new ShareParamsException(ShareParamsException.ILLEGAL_URL);
  }
  if(title!=null&&title.length()>512) {
   throw new ShareParamsException(ShareParamsException.ILLEGAL_TITLE);
  }
  if(content!=null&&content.length()>1024) {
   throw new ShareParamsException(ShareParamsException.ILLEGAL_CONTENT);
  }

  if(thumb==null||thumb.isEmpty()) {
   throw new ShareParamsException(ShareParamsException.ILLEGAL_THUMB|ShareParamsException.ILLEGAL_TYPE_EMPTY);
  }
  WebpageObject mediaObject = new WebpageObject();
  mediaObject.title=title;
  mediaObject.description=content;
  //todo load default logo
  Bitmap bitmap=BitmapFactory.decodeFile(thumb);
  mediaObject.setThumbImage(bitmap);
  mediaObject.actionUrl=url;
  WeiBoShareActivity.startActivity(mActivityRef.get(),BaseMediaObject.MEDIA_TYPE_WEBPAGE,mediaObject);
 }

 /**
  * actionUrl <  512 b
  * identity < 512 b
  * image data < 2097152 b
  * image file size
  * title < 512 b
  * description < 1024 b
  *
  * data或者file选一
  *
  */
 @Override public void sendImage(ImageShareBean bean) throws ShareParamsException{
  String title=bean.getTitle();
  String content=bean.getMessage();
  String path=bean.getPath();
  if(!TextUtils.isEmpty(title)) {
   ShareParamExaminer.titleLengthIsLegal(title,512);
  }
  if(!TextUtils.isEmpty(content)) {
   ShareParamExaminer.contentLengthIsLegal(content,1024);
  }
  ShareParamExaminer.imageIsLegal(path,10485760);
  ImageObject imageObject=new ImageObject();
  imageObject.title=title;
  imageObject.description=content;
  imageObject.imagePath=path;
  WeiBoShareActivity.startActivity(mActivityRef.get(),BaseMediaObject.MEDIA_TYPE_IMAGE,imageObject);
 }

 @Override public void sendVideo(VideoShareBean bean) throws ShareParamsException{
  String title=bean.getTitle();
  String content=bean.getMessage();
  String thumb=bean.getThumb();
  String url=bean.getUrl();
  if(!TextUtils.isEmpty(title)) {
   ShareParamExaminer.titleLengthIsLegal(title,512);
  }
  if(!TextUtils.isEmpty(content)) {
   ShareParamExaminer.contentLengthIsLegal(content,1024);
  }
  VideoSourceObject videoSourceObject=new VideoSourceObject();

 }

 @Override public void sendMultiObject(MultiImageShareBean bean) throws ShareParamsException{

 }


}
