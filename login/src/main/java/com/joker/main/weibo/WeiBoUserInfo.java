package com.joker.main.weibo;
import com.joker.main.UserInfo;

@SuppressWarnings("SpellCheckingInspection")
public class WeiBoUserInfo extends UserInfo{

 private String weiboId;
 private String accessToken;

 public String getWeiboId(){
  return weiboId;
 }

 public void setWeiboId(String weiboId){
  this.weiboId=weiboId;
 }

 public String getAccessToken(){
  return accessToken;
 }

 public void setAccessToken(String accessToken){
  this.accessToken=accessToken;
 }

 @Override public int getChannel(){
  return Channel.WEIBO;
 }
}
