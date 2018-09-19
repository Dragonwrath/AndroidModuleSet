package com.joker.main.weibo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import com.joker.main.Constant;
import com.joker.main.core.LoginMangerBridge;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.net.HttpManager;
import com.sina.weibo.sdk.net.WeiboParameters;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 必须在Application中调用WbSdk.install的方法
 * {@link WbSdk#install(Context,AuthInfo) WbSdk#install(Context, AuthInfo)}
 */
public class WeiBoAuthActivity extends AppCompatActivity implements WbAuthListener{
 private SsoHandler mSsoHandler;

 @Override
 protected void onCreate(Bundle savedInstanceState){
  super.onCreate(savedInstanceState);
  //必须在application初始化的时候调用该方法
  //WbSdk.install(this,new AuthInfo(this, Constant.WeiBo.APP_KEY, Constant.WeiBo.REDIRECT_URL, Constant.WeiBo.SCOPE));
  mSsoHandler=new SsoHandler(this);
  mSsoHandler.authorize(this);
 }

 @Override protected void onActivityResult(int requestCode,int resultCode,Intent data){
  super.onActivityResult(requestCode,resultCode,data);
  if(mSsoHandler!=null){
   mSsoHandler.authorizeCallBack(requestCode,resultCode,data);
  }
 }

 /**
  * @param oauth2AccessToken 用户授权信息
  * @see <a href="获取用户基本信息">http://open.weibo.com/wiki/2/users/show</a>
  * https://api.weibo.com/2/users/show.json?access_token=token&uid=uid
  */
 @Override public void onSuccess(Oauth2AccessToken oauth2AccessToken){
  //handle token
  if(oauth2AccessToken.isSessionValid()){
   //todo auth by self
   String url="https://api.weiBo.com/2/users/show.json?access_token="+oauth2AccessToken.getToken()+"&uid="+oauth2AccessToken.getUid();
   //todo do on worker thread
   if(Looper.myLooper()==getMainLooper()){
    throw new IllegalThreadStateException("can't do this on main thread");
   }
   String response=HttpManager.openUrl(this,url,"GET",new WeiboParameters(Constant.WeiBo.APP_KEY));
   JSONObject jsonObject;
   try{
    WeiBoUserInfo info=new WeiBoUserInfo();
    jsonObject=new JSONObject(response);
    if(jsonObject.has("id")){ //
     info.setWeiboId(jsonObject.getString("id"));
    }
    if(jsonObject.has("screen_name")){
     info.setWeiboId(jsonObject.getString("screen_name"));
    }
    if(jsonObject.has("profile_image_url")){
     info.setAvatarUrl(jsonObject.getString("profile_image_url"));
    }else if(jsonObject.has("avatar_large")){
     info.setAvatarUrl(jsonObject.getString("avatar_large"));
    }else if(jsonObject.has("avatar_hd")){
     info.setAvatarUrl(jsonObject.getString("avatar_hd"));
    }

    if(jsonObject.has("gender")){
     String gender=jsonObject.getString("gender");
     if(gender.equals("m")){
      info.setSex(1);
     }else if(gender.equals("f")){
      info.setSex(0);
     }
    }
    info.setAccessToken(oauth2AccessToken.getToken());
    LoginMangerBridge.getMsgBus().onSuccess(info);
   }catch(JSONException e){
    LoginMangerBridge.getMsgBus().onFailure(e);
   }
  }
 }

 @Override public void cancel(){
  //cancel
  LoginMangerBridge.getMsgBus().cancel();
 }

 @Override public void onFailure(WbConnectErrorMessage wbConnectErrorMessage){
  LoginMangerBridge.getMsgBus().onFailure(new Throwable(wbConnectErrorMessage.getErrorMessage()));

 }
}
