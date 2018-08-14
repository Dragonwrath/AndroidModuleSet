package com.joker.main.share.bean;
import com.joker.main.share.ShareBean;
import com.joker.main.share.ShareType;

import java.io.File;

/**
 * 由于音频与视频都是相似的，因此，我们直接分享相应的文件即可
 * 所以，需要首先保存文件到本地，之后再进行相应的分享
 */
public class VideoShareBean implements ShareBean{
 private final String title;
 private final String message;
 private final String thumb;
 private final String url;

 public VideoShareBean(String title,String message,String thumb,String url){
  this.title=title;
  this.message=message;
  this.thumb=thumb;
  this.url=url;
 }

 public String getTitle(){
  return title;
 }

 public String getMessage(){
  return message;
 }

 public String getThumb(){
  return thumb;
 }

 public String getUrl(){
  return url;
 }

 @Override
 public int getType(){
  return ShareType.VIDEO;
 }
}
