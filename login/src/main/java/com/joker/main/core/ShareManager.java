package com.joker.main.core;
import android.app.Activity;

import com.joker.main.share.ShareBean;
import com.joker.main.share.ShareType;
import com.joker.main.share.bean.ImageShareBean;
import com.joker.main.share.bean.TextShareBean;
import com.joker.main.share.bean.VideoShareBean;
import com.joker.main.share.bean.WebShareBean;
import com.joker.main.wxapi.WeChatSessionShare;
import com.joker.main.wxapi.WeChatTimelineShare;
public class ShareManager{

 private ShareManager(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public void shareToWeChatSession(Activity activity,ShareBean bean,OnUserShareResultListener listener){
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

 public void shareToWeChatTimeline(Activity activity,ShareBean bean,OnUserShareResultListener listener){
  WeChatTimelineShare session=new WeChatTimelineShare(activity);
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

 }
}
