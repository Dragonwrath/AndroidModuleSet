package com.joker.main.core;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.joker.main.Constant;
import com.joker.main.UserInfo;
import com.joker.main.weibo.WeiBoAuthActivity;
import com.sina.weibo.sdk.WbSdk;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.lang.ref.WeakReference;
import java.util.UUID;

public class LoginManger{

 private final static class Holder{
  private final static ActivityLoginMsgBus INSTANCE=new ActivityLoginMsgBus();
 }

 private LoginManger(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public static void weChat(Activity activity,final OnUserAuthResultListener listener){
  IWXAPI wxapi=WXAPIFactory.createWXAPI(activity,Constant.WeChat.APP_ID,true);
  if(wxapi.isWXAppInstalled()){
   final SendAuth.Req req=new SendAuth.Req();
   req.scope="snsapi_userinfo";
   req.state=UUID.randomUUID().toString();
   Holder.INSTANCE.register(activity,listener);
   wxapi.sendReq(req);
  }else{
   Toast.makeText(activity,"微信未安装",Toast.LENGTH_SHORT).show();
  }
 }

 public static void weiBo(Activity activity,OnUserAuthResultListener listener){
  WbSdk.checkInit();
  Holder.INSTANCE.register(activity,listener);
  activity.startActivity(new Intent(activity,WeiBoAuthActivity.class));
 }

 public static LoginMsgBus getMsgBus(){
  return Holder.INSTANCE;
 }

 /**
  * 默认的消息总线设计，可能需要一些延迟,等待相应的代理Activity的退出之后，再发送消息
  */
 private final static class ActivityLoginMsgBus implements LoginMsgBus{
  private WeakReference<Activity> mActRef;
  private OnUserAuthResultListener mListener;

  void register(Activity activity,OnUserAuthResultListener listener){
   mActRef=new WeakReference<>(activity);
   mListener=listener;
  }

  @Override public void onSuccess(final UserInfo info){
   Activity activity=mActRef.get();
   if(activity!=null&&mListener!=null)
    new Handler(activity.getMainLooper()).postDelayed(new Runnable(){
     @Override public void run(){
      mListener.onAuthSuccess(info);
     }
    },500);

  }

  @Override public void cancel(){
   Activity activity=mActRef.get();
   if(activity!=null&&mListener!=null)
   new Handler(mActRef.get().getMainLooper()).postDelayed(new Runnable(){
    @Override public void run(){
     mListener.onAuthCancel();
    }
   },500);
  }

  @Override public void onFailure(final Throwable throwable){
   Activity activity=mActRef.get();
   if(activity!=null&&mListener!=null)
   new Handler(mActRef.get().getMainLooper()).postDelayed(new Runnable(){
    @Override public void run(){
     mListener.onAuthFailure(throwable);
    }
   },500);
  }
 }

}
