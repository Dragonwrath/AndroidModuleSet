package com.joker.main.share.bean;
import com.joker.main.share.ShareBean;
import com.joker.main.share.ShareType;

import java.io.File;

/**
 * 因为图片的必须使用本地的进行分享，因此需要用到FileUri
 * 其中还需要区分，api25以上版本的FileProvider的问题
 * 此外，微信对于分享的图片的大小有限制，图片不能超过10M，
 * 文件名的长度不能超过1024，这些最好做校验
 *
 * {@link com.tencent.mm.opensdk.modelmsg.WXImageObject}
 */
public class ImageShareBean implements ShareBean{
 private final String title;
 private final String message;
 private final File file;

 public ImageShareBean(String title,String message,File file){
  this.title=title;
  this.message=message;
  this.file=file;
 }

 public String getTitle(){
  return title;
 }

 public String getMessage(){
  return message;
 }

 public File getFile(){
  return file;
 }

 @Override
 public int getType(){
  return ShareType.IMAGE;
 }
}
