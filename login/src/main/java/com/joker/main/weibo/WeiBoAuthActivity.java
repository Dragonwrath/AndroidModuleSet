package com.joker.main.weibo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.joker.main.core.LoginManger;
import com.joker.main.network.DefaultHttpManager;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * 必须在Application中调用WbSdk.install的方法
 * {@link WbSdk#install(Context,AuthInfo) WbSdk#install(Context, AuthInfo)}
 */
public class WeiBoAuthActivity extends AppCompatActivity implements WbAuthListener{
 private SsoHandler mSsoHandler;

 @Override
 protected void onCreate(Bundle savedInstanceState){
  super.onCreate(savedInstanceState);
  System.out.println("WXEntryActivity------>"+WbSdk.isWbInstall(this));

  mSsoHandler=new SsoHandler(this);
  mSsoHandler.authorize(this);
 }

 @Override protected void onActivityResult(int requestCode,int resultCode,Intent data){
  super.onActivityResult(requestCode,resultCode,data);
  if(mSsoHandler!=null){
   mSsoHandler.authorizeCallBack(requestCode,resultCode,data);
  }
 }

 @Override protected void onDestroy(){
  super.onDestroy();
  DefaultHttpManager.onDestroy();
 }

 /**
  * @param oauth2AccessToken 用户授权信息
  * @see <a href="获取用户基本信息">http://open.weibo.com/wiki/2/users/show</a>
  * https://api.weibo.com/2/users/show.json?access_token=token&uid=uid
  */
 @Override public void onSuccess(Oauth2AccessToken oauth2AccessToken){
  //handle token
  if(oauth2AccessToken.isSessionValid()){
   DefaultHttpManager.queryWeiBoUserInfo(this,oauth2AccessToken);
  } else {
   LoginManger.getMsgBus().onFailure(new Throwable("invalid WeiBo token"));
   this.finish();
  }
 }

 @Override public void cancel(){
  //cancel
  LoginManger.getMsgBus().cancel();
  this.finish();
 }

 @Override public void onFailure(WbConnectErrorMessage wbConnectErrorMessage){
  LoginManger.getMsgBus().onFailure(new Throwable(wbConnectErrorMessage.getErrorMessage()));
  this.finish();
 }
}
