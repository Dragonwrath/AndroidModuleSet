package com.joker.main.share.wx;
import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
public class WeChatTimelineShare extends BaseWeChatShareAction{
 protected WeChatTimelineShare(Context context){
  super(context);
 }

 @Override
 int getScene(){
  return SendMessageToWX.Req.WXSceneTimeline;
 }
}
