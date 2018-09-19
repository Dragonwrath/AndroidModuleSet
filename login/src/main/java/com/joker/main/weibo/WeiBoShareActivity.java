package com.joker.main.weibo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.joker.main.R;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;

public class WeiBoShareActivity extends AppCompatActivity implements WbShareCallback{
 private WbShareHandler shareHandler;

 @Override
 protected void onCreate(Bundle savedInstanceState){
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_wei_bo_share);
  shareHandler = new WbShareHandler(this);
  shareHandler.registerApp();
  shareHandler.setProgressColor(0xff33b5e5);
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
