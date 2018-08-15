package com.joker.main.wxapi;
import com.joker.main.UserInfo;

public class WxUserInfo extends UserInfo{

 private final String unionId;
 private final String openId;

 public WxUserInfo(String id,String name,int sex,String avatar,String unionId,String openId){
  super(id,name,sex,avatar);
  this.unionId=unionId;
  this.openId=openId;
 }

 public String getOpenId(){
  return openId;
 }

 public String getUnionId(){
  return unionId;
 }

 @Override
 public int getChannel(){
  return Channel.WECHAT;
 }
}
