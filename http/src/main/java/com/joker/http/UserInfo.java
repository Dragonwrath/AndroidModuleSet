package com.joker.http;
public class UserInfo{
 private long id;
 private String imageUrl;
 private boolean isMale;

 public long getId(){
  return id;
 }

 public void setId(long id){
  this.id=id;
 }

 public String getImageUrl(){
  return imageUrl;
 }

 public void setImageUrl(String imageUrl){
  this.imageUrl=imageUrl;
 }

 public boolean isMale(){
  return isMale;
 }

 public void setMale(boolean male){
  isMale=male;
 }

 @Override public String toString(){
  return "{"+
    "\"id\":"+id+
    ", \"imageUrl\":"+imageUrl+'\''+
    ", \"isMale\":"+isMale+
    '}';
 }
}
