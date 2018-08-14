package com.joker.main.share.bean;
import com.joker.main.share.ShareBean;
import com.joker.main.share.ShareType;
public class TextShareBean implements ShareBean{

 private final String title;
 private final String message;

 public TextShareBean(String title,String message){
  this.title=title;
  this.message=message;
 }

 public String getTitle(){
  return title;
 }

 public String getMessage(){
  return message;
 }

 @Override
 public int getType(){
  return ShareType.TEXT;
 }
}
