package com.joker.main;

import java.io.Serializable;

public class UserInfo implements Serializable{

 @SuppressWarnings("SpellCheckingInspection")
 public final static class Channel {
  public final static int OFFICIAL=0;
  public final static int WECHAT=1;
  public final static int QQ=2;
  public final static int WEIBO=3;
 }

 private String id;
 private String name;
 //女为0，男为1，默认为女
 private int sex;
 private String avatarUrl;

 public int getChannel(){
  return Channel.OFFICIAL;
 }

 public String getId(){
  return id;
 }

 public void setId(String id){
  this.id=id;
 }

 public String getName(){
  return name;
 }

 public void setName(String name){
  this.name=name;
 }

 public int getSex(){
  return sex;
 }

 public void setSex(int sex){
  this.sex=sex;
 }

 public String getAvatarUrl(){
  return avatarUrl;
 }

 public void setAvatarUrl(String avatarUrl){
  this.avatarUrl=avatarUrl;
 }

 @Override
 public boolean equals(Object o){
  if(this==o) return true;
  if(o==null||getClass()!=o.getClass()) return false;
  UserInfo userInfo=(UserInfo)o;
  return sex==userInfo.sex&&id.equals(userInfo.id)&&name.equals(userInfo.name)&&avatarUrl.equals(userInfo.avatarUrl);
 }

 @Override
 public int hashCode(){
  int result=id.hashCode();
  result=31*result+name.hashCode();
  result=31*result+sex;
  result=31*result+avatarUrl.hashCode();
  return result;
 }

 @Override
 public String toString(){
  return "UserInfo{"+
    "id='"+id+'\''+
    ", name='"+name+'\''+
    ", sex="+sex+
    ", avatarUrl='"+avatarUrl+'\''+
    '}';
 }
}
