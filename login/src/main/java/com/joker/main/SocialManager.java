package com.joker.main;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;


public class SocialManager{
 private final Context context;

 SocialManager(Application context){
  this.context=context;
 }

 public SocialManager weChat(){
  if(TextUtils.isEmpty(Constant.WeChat.APP_ID)
    ||TextUtils.isEmpty(Constant.WeChat.APP_SECRET)){
   throw new IllegalStateException("WeChat.APP_ID or WeChat.APP_SECRET hasn't config yet");
  }
  return this;
 }

 public SocialManager weiBo() {
  if(TextUtils.isEmpty(Constant.WeiBo.APP_KEY)
    ||TextUtils.isEmpty(Constant.WeiBo.REDIRECT_URL)
    ||TextUtils.isEmpty(Constant.WeiBo.SCOPE)){
   throw new IllegalStateException("WeiBo.APP_KEY,WeiBo.REDIRECT_URL or WeiBo.SCOPE hasn't config yet");
  }
  WbSdk.install(context,new AuthInfo(context,Constant.WeiBo.APP_KEY,Constant.WeiBo.REDIRECT_URL,Constant.WeiBo.SCOPE));
  return this;
 }


}
