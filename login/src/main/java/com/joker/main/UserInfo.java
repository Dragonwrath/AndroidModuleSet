package com.joker.main;

import java.io.Serializable;

public class UserInfo implements Serializable{
 public final static class Channel {
  public final static int OFFICIAL=0;
  public final static int WECHAT=1;
  public final static int QQ=2;
  public final static int WEIBO=3;

 }

 private final String id;
 private final String name;
 private final int sex;
 private final String avatar;

 public UserInfo(String id,String name,int sex,String avatar){
  this.id=id;
  this.name=name;
  this.sex=sex;
  this.avatar=avatar;
 }

 public int getType(){
  return Channel.OFFICIAL;
 }

 public String getId(){
  return id;
 }

 public String getName(){
  return name;
 }

 public int getSex(){
  return sex;
 }

 public String getAvatar(){
  return avatar;
 }

 @Override
 public boolean equals(Object o){
  if(this==o) return true;
  if(o==null||getClass()!=o.getClass()) return false;

  UserInfo userInfo=(UserInfo)o;

  return sex==userInfo.sex&&id.equals(userInfo.id)&&name.equals(userInfo.name)&&avatar.equals(userInfo.avatar);
 }

 @Override
 public int hashCode(){
  int result=id.hashCode();
  result=31*result+name.hashCode();
  result=31*result+sex;
  result=31*result+avatar.hashCode();
  return result;
 }

 @Override
 public String toString(){
  return "UserInfo{"+
    "id='"+id+'\''+
    ", name='"+name+'\''+
    ", sex="+sex+
    ", avatar='"+avatar+'\''+
    '}';
 }
}
