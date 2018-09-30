package com.joker.main.share.bean;
import com.joker.main.share.ShareBean;
import com.joker.main.share.ShareType;

public class ImageShareBean implements ShareBean{
 private final String title;
 private final String message;
 private final String path;

 public ImageShareBean(String title,String message,String path){
  this.title=title;
  this.message=message;
  this.path=path;
 }

 public String getTitle(){
  return title;
 }

 public String getMessage(){
  return message;
 }

 public String getPath(){
  return path;
 }

 @Override
 public int getType(){
  return ShareType.IMAGE;
 }
}
