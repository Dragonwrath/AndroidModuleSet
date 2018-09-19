package com.joker.main.wxapi;
import com.joker.main.UserInfo;

public class WxUserInfo extends UserInfo{

 private String unionId;
 private String openId;

 public String getOpenId(){
  return openId;
 }

 public String getUnionId(){
  return unionId;
 }

 public void setUnionId(String unionId){
  this.unionId=unionId;
 }

 public void setOpenId(String openId){
  this.openId=openId;
 }

 @Override
 public int getChannel(){
  return Channel.WECHAT;
 }
}
