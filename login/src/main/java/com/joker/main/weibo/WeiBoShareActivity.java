package com.joker.main.weibo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.joker.main.R;
import com.sina.weibo.sdk.api.BaseMediaObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;

public class WeiBoShareActivity extends AppCompatActivity implements WbShareCallback{

 /**
  * 默认启动该页面的方法
  *
  * @param context 上下文对象
  * @param type 用于区分微博默认的消息类型，{@link BaseMediaObject#getObjType()}的子类实现有问题
  * @param object 指定的分享的实体包装类
  */
 public static void startActivity(Context context,@WeiBoSupportType int type,BaseMediaObject object) {
  if(context==null) throw new NullPointerException("context reference maybe is null");
  Intent intent=new Intent(context,WeiBoShareActivity.class);
  intent.putExtra(EXTRAS_BEAN,object);
  intent.putExtra(EXTRAS_TYPE,type);
  context.startActivity(intent);
 }

 public final static String EXTRAS_TYPE=WeiBoShareActivity.class.getName()+"EXTRAS_TYPE";
 public final static String EXTRAS_BEAN=WeiBoShareActivity.class.getName()+"EXTRAS_BEAN";

 private WbShareHandler shareHandler;

 @Override
 protected void onCreate(Bundle savedInstanceState){
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_wei_bo_share);
  shareHandler = new WbShareHandler(this);
  shareHandler.registerApp();
  shareHandler.setProgressColor(0xff33b5e5);
  sendMessage();
 }

 private void sendMessage(){
  Intent intent=getIntent();
  int type=intent.getIntExtra(EXTRAS_TYPE,-1);
  WeiboMultiMessage message=new WeiboMultiMessage();
  switch(type) {
   case BaseMediaObject. MEDIA_TYPE_TEXT :
    message.textObject=intent.getParcelableExtra(EXTRAS_BEAN);
    break;
   case BaseMediaObject. MEDIA_TYPE_IMAGE :
    message.imageObject=intent.getParcelableExtra(EXTRAS_BEAN);
    break;
   case BaseMediaObject. MEDIA_TYPE_WEBPAGE :
    message.mediaObject=intent.getParcelableExtra(EXTRAS_BEAN);
    break;
   case BaseMediaObject. MEDIA_TYPE_MULITI_IMAGE :
    message.multiImageObject=intent.getParcelableExtra(EXTRAS_BEAN);
    break;
   case BaseMediaObject. MEDIA_TYPE_SOURCE_VIDEO :
    message.videoSourceObject=intent.getParcelableExtra(EXTRAS_BEAN);
    break;
   default:
    throw new IllegalArgumentException("not support type");
  }
  shareHandler.shareMessage(message,false);
 }

 @Override
 protected void onNewIntent(Intent intent) {
  super.onNewIntent(intent);
  shareHandler.doResultIntent(intent,this);
 }

 @Override public void onWbShareSuccess(){

 }

 @Override public void onWbShareCancel(){

 }

 @Override public void onWbShareFail(){

 }
}
