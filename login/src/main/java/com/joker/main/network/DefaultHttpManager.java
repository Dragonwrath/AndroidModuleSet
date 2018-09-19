package com.joker.main.network;
import android.app.Activity;
import android.text.TextUtils;

import com.joker.main.Constant;
import com.joker.main.core.LoginManger;
import com.joker.main.weibo.WeiBoUserInfo;
import com.joker.main.wxapi.WxUserInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DefaultHttpManager{
 private volatile static Disposable mDisposable;
 private volatile static Call currentCall;

 private DefaultHttpManager(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public static void queryWeiBoUserInfo(final Activity activity,final Oauth2AccessToken oauth2AccessToken){
  Single.just("https://api.weibo.com/2/users/show.json?access_token="
    +oauth2AccessToken.getToken()+"&uid="+oauth2AccessToken.getUid())
    .subscribeOn(Schedulers.io())
    .map(new Function<String,Response>(){
     @Override public Response apply(String url) throws Exception{
      Request request=new Request.Builder().url(url).get().build();
      currentCall=new OkHttpClient().newCall(request);
      return currentCall.execute();
     }
    })
    .map(new GsonConvertFunction<Map<String,Object>>())
    .map(new Function<Map<String,Object>,WeiBoUserInfo>(){
     @Override
     public WeiBoUserInfo apply(Map<String,Object> map) throws Exception{
      WeiBoUserInfo info=new WeiBoUserInfo();
      info.setWeiboId(((String)map.get("idStr")));
      info.setName((String)map.get("screen_name"));
      if(map.get("profile_image_url")!=null){
       info.setAvatarUrl((String)map.get("profile_image_url"));
      }else if(map.get("avatar_large")!=null){
       info.setAvatarUrl((String)map.get("avatar_large"));
      }else if(map.get("avatar_hd")!=null){
       info.setAvatarUrl((String)map.get("avatar_hd"));
      }

      if(map.get("gender")!=null){
       String gender=(String)map.get("gender");
       if(gender.equals("m")){
        info.setSex(1);
       }else if(gender.equals("f")){
        info.setSex(0);
       }
      }
      info.setAccessToken(oauth2AccessToken.getToken());
      return info;
     }
    })
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(new SingleObserver<WeiBoUserInfo>(){
     @Override public void onSubscribe(Disposable d){
      if(mDisposable!=null&&!mDisposable.isDisposed()){
       mDisposable.dispose();
      }
      mDisposable=d;
     }

     @Override public void onSuccess(WeiBoUserInfo weiBoUserInfo){
      LoginManger.getMsgBus().onSuccess(weiBoUserInfo);
      activity.finish();
     }

     @Override public void onError(Throwable e){
      LoginManger.getMsgBus().onFailure(e);
      activity.finish();
     }
    });
 }

 public static void queryWeChatUserInfo(final Activity activity,final SendAuth.Resp resp){
  Single.just("https://api.weixin.qq.com/sns/oauth2/access_token?"+
    "appid="+Constant.WeChat.APP_ID+
    "&secret="+Constant.WeChat.APP_SECRET+
    "&code="+resp.code+"&grant_type=authorization_code")
    .subscribeOn(Schedulers.io())
    .map(new Function<String,Response>(){
     @Override public Response apply(String url) throws Exception{
      Request request=new Request.Builder().url(url).get().build();
      currentCall=new OkHttpClient().newCall(request);
      return currentCall.execute();
     }
    })
    .map(new GsonConvertFunction<Map<String,String>>())
    .map(new Function<Map<String,String>,Response>(){
     @Override public Response apply(Map<String,String> map) throws Exception{
      if(map==null||map.get("errcode")!=null||map.get("errmsg")!=null){
       throw new IllegalStateException("some error hppen");
      }
      String openid=map.get("openid");
      String accessToken=map.get("access_token");
      if(TextUtils.isEmpty(openid)||TextUtils.isEmpty(accessToken))
       throw new IllegalArgumentException("wechat id or token maybe is null");
      String url="https://api.weixin.qq.com/sns/userinfo?"+
        "access_token="+accessToken+
        "&openid="+openid;
      Request request=new Request.Builder().url(url).get().build();
      currentCall=new OkHttpClient().newCall(request);
      return currentCall.execute();
     }
    })
    .map(new GsonConvertFunction<Map<String,String>>())
    .map(new Function<Map<String,String>,WxUserInfo>(){
     @Override public WxUserInfo apply(Map<String,String> map) throws Exception{
      if(map==null||map.get("errcode")!=null||map.get("errmsg")!=null){
       throw new IllegalStateException("some error hppen");
      }
      WxUserInfo info=new WxUserInfo();
      info.setOpenId(map.get("openid"));
      info.setName(map.get("nickname"));
      info.setUnionId(map.get("unionid"));
      info.setAvatarUrl(map.get("headimgurl"));
      info.setSex(Integer.parseInt(map.get("sex")));
      return info;
     }
    })
    .subscribeOn(AndroidSchedulers.mainThread())
    .subscribe(new SingleObserver<WxUserInfo>(){
     @Override public void onSubscribe(Disposable d){
      if(mDisposable!=null&&!mDisposable.isDisposed()){
       mDisposable.dispose();
      }
      mDisposable=d;
     }

     @Override public void onSuccess(WxUserInfo wxUserInfo){
      LoginManger.getMsgBus().onSuccess(wxUserInfo);
      activity.finish();
     }

     @Override public void onError(Throwable e){
      LoginManger.getMsgBus().onFailure(e);
      activity.finish();
     }
    });
 }

 public static void onDestroy(){
  if(mDisposable!=null&&!mDisposable.isDisposed()){
   mDisposable.dispose();
  }
  if(currentCall!=null) {
   currentCall.cancel();
  }
 }

}