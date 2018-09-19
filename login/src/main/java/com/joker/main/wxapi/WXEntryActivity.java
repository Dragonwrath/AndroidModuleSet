
package com.joker.main.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.joker.main.Constant;
import com.joker.main.network.DefaultHttpManager;
import com.sina.weibo.sdk.WbSdk;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 登陆接入流程
 * 1、创建对应的<applicationId>.wxapi.WXEntryActivity，并实现IWXAPIEventHandler
 * 2、Manifest文件中注册的信息为：
 * <activity
 * android:name=".wxapi.WXEntryActivity"
 * android:configChanges="keyboardHidden|orientation|screenSize"
 * android:exported="true"
 * android:theme="@android:style/Theme.Translucent.NoTitleBar"
 * android:launchMode="singleTop"/>
 * 3、根据项目集成的Bus以及对应的逻辑，集成之后的结果
 * 4、集成完成之后，直接调用下面的代码，
 * IWXAPI wxapi=WXAPIFactory.createWXAPI(context,Constant.WeChat.APP_ID,true);
 * if(wxapi.isWXAppInstalled()){  //可以给用户提示引导安装
 * final SendAuth.Req req=new SendAuth.Req();
 * req.scope="snsapi_userinfo";
 * req.state=UUID.randomUUID().toString();
 * wxapi.sendReq(req);
 * Toast.makeText((Context)mView,"微信安装",Toast.LENGTH_SHORT).show();
 * } else {
 * Toast.makeText((Context)mView,"微信未安装",Toast.LENGTH_SHORT).show();
 * }
 * <p>
 * 详细的接口说明参考
 *
 * @see <a href="微信接口说明">https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list</a>
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler{
 private  final IWXAPI mWxApi=WXAPIFactory.createWXAPI(this,Constant.WeChat.APP_ID,true);

 private static class Hints{
  private final static String errcode_success="发送成功";
  private final static String errcode_cancel="发送取消";
  private final static String errcode_deny="发送被拒绝";
  private final static String errcode_unsupported="不支持错误";
  private final static String errcode_unknown="发送返回";
 }

 @Override
 protected void onCreate(@Nullable Bundle savedInstanceState){
  super.onCreate(savedInstanceState);
  mWxApi.registerApp(Constant.WeChat.APP_ID);
  mWxApi.handleIntent(getIntent(),this);
 }

 @Override
 protected void onDestroy(){
  super.onDestroy();
  mWxApi.unregisterApp();
  DefaultHttpManager.onDestroy();
 }

 @Override
 protected void onNewIntent(Intent intent){
  super.onNewIntent(intent);
  mWxApi.handleIntent(getIntent(),this);
 }

 @Override
 public void onReq(BaseReq baseReq){
  //nothing
 }

 @Override
 public void onResp(BaseResp baseResp){
  if(baseResp==null){
   this.finish();
   return;
  }
  String result;
  switch(baseResp.errCode){
   case BaseResp.ErrCode.ERR_OK:
    result=Hints.errcode_success;
    break;
   case BaseResp.ErrCode.ERR_USER_CANCEL:
    result=Hints.errcode_cancel;
    break;
   case BaseResp.ErrCode.ERR_AUTH_DENIED:
    result=Hints.errcode_deny;
    break;
   case BaseResp.ErrCode.ERR_UNSUPPORT:
    result=Hints.errcode_unsupported;
    break;
   default:
    result=Hints.errcode_unknown;
    break;
  }
  //type 为2表示的是分享，需要忽略结果
  if(baseResp.getType()==2||baseResp.errCode!=BaseResp.ErrCode.ERR_OK){
   Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
   this.finish();
   return;
  }

  if(baseResp instanceof SendAuth.Resp){
   DefaultHttpManager.queryWeChatUserInfo(this,(SendAuth.Resp)baseResp);
  }
 }
}
