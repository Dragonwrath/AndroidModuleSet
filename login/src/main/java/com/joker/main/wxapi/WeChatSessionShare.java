package com.joker.main.wxapi;

import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

public class WeChatSessionShare extends BaseWeChatShare{

 public WeChatSessionShare(Context context){
  super(context);
 }

 @Override
 int getScene(){
  return SendMessageToWX.Req.WXSceneSession;
 }

}