package com.joker.main.wxapi;
import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

public class WeChatTimelineShare extends BaseWeChatShare{

 public WeChatTimelineShare(Context context){
  super(context);
 }

 @Override
 int getScene(){
  return SendMessageToWX.Req.WXSceneTimeline;
 }

}
