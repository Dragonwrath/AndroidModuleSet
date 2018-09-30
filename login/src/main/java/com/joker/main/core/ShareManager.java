package com.joker.main.core;
import android.app.Activity;
import android.os.Handler;

import com.joker.main.share.ShareBean;
import com.joker.main.share.ShareType;
import com.joker.main.share.bean.ImageShareBean;
import com.joker.main.share.bean.TextShareBean;
import com.joker.main.share.bean.VideoShareBean;
import com.joker.main.share.bean.WebShareBean;
import com.joker.main.weibo.WeiBoShare;
import com.joker.main.wxapi.WeChatSessionShare;
import com.joker.main.wxapi.WeChatTimelineShare;
import com.sina.weibo.sdk.WbSdk;

import java.lang.ref.WeakReference;
public class ShareManager{

 private static class Holder {
  private static final DefaultShareMsgBus INSTACE=new DefaultShareMsgBus();
 }
 private ShareManager(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public static void shareToWeChatSession(Activity activity,ShareBean bean,OnUserShareResultListener listener){
  WeChatSessionShare session=new WeChatSessionShare(activity);
  try{
   switch(bean.getType()){
    case ShareType.TEXT:
     session.sendText((TextShareBean)bean);
     break;
    case ShareType.IMAGE:
     session.sendImage((ImageShareBean)bean);
     break;
    case ShareType.VIDEO:
     session.sendVideo((VideoShareBean)bean);
     break;
    case ShareType.WEB:
     session.sendWebPage((WebShareBean)bean);
     break;
    default:
     throw new IllegalArgumentException("not support type");
   }
  }catch(IllegalArgumentException ex){
   listener.onFailure(ex);
  }
 }

 public static void shareToWeChatTimeline(Activity activity,ShareBean bean,OnUserShareResultListener listener){
  WeChatTimelineShare session=new WeChatTimelineShare(activity);
  Holder.INSTACE.register(activity,listener);
  try{
   switch(bean.getType()){
    case ShareType.TEXT:
     session.sendText((TextShareBean)bean);
     break;
    case ShareType.IMAGE:
     session.sendImage((ImageShareBean)bean);
     break;
    case ShareType.VIDEO:
     session.sendVideo((VideoShareBean)bean);
     break;
    case ShareType.WEB:
     session.sendWebPage((WebShareBean)bean);
     break;
    default:
     throw new IllegalArgumentException("not support type");
   }
  }catch(IllegalArgumentException ex){
   listener.onFailure(ex);
  }
 }

 public void shareToWeiBo(Activity activity,ShareBean bean,OnUserShareResultListener listener) {
  if(!WbSdk.isWbInstall(activity)){
   return;
  }
  WeiBoShare session=new WeiBoShare(activity);
  Holder.INSTACE.register(activity,listener);
  switch(bean.getType()) {
   case ShareType.TEXT:
    session.sendText((TextShareBean)bean);
    break;
   case ShareType.IMAGE:
    session.sendImage((ImageShareBean)bean);
    break;
   case ShareType.VIDEO:
    session.sendVideo((VideoShareBean)bean);
    break;
   case ShareType.WEB:
    session.sendWebPage((WebShareBean)bean);
    break;
   default:
    throw new IllegalArgumentException("not support type");

  }
 }

 private static class DefaultShareMsgBus implements ShareMsgBus{
  private WeakReference<Activity> mActRef;
  private WeakReference<OnUserShareResultListener> mListenerRef;

  private void register(Activity activity,OnUserShareResultListener listener){
   mActRef=new WeakReference<>(activity);
   mListenerRef=new WeakReference<>(listener);
  }

  @Override public void onShareSuccess(){
   Activity activity=mActRef.get();
   final OnUserShareResultListener listener=mListenerRef.get();
   if(activity!=null&&listener!=null) {
    new Handler(activity.getMainLooper()).postDelayed(new Runnable(){
     @Override public void run(){
       listener.onSuccess();
     }
    },500);
   }

  }

  @Override public void onShareFailure(final Exception e){
   Activity activity=mActRef.get();
   final OnUserShareResultListener listener=mListenerRef.get();
   if(activity!=null&&listener!=null) {
    new Handler(activity.getMainLooper()).postDelayed(new Runnable(){
     @Override public void run(){
      listener.onFailure(e);
     }
    },500);
   }
  }
 }
}
