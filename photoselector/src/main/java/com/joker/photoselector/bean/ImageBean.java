package com.joker.photoselector.bean;

import android.content.ContentResolver;
import android.net.Uri;

public class ImageBean{
  /**
   * Path to the thumbnail file on disk.
   * <p>
   * Note that apps may not have filesystem permissions to directly
   * access this path. Instead of trying to open this path directly,
   * apps should use
   * {@link ContentResolver#openFileDescriptor(Uri,String)} to gain
   * access.
   * <p>
   * Type: TEXT
   */
  private String uri;

  /**
   * The original image for the thumbnal
   * <P>Type: INTEGER (ID from Images table)</P>
   */
  private long imageId;

  private boolean selected;

  public String getUri(){
    return uri;
  }

  public void setUri(String uri){
    this.uri=uri;
  }

  public long getImageId(){
    return imageId;
  }

  public void setImageId(long imageId){
    this.imageId=imageId;
  }

  public boolean isSelected(){
    return selected;
  }

  public void setSelected(boolean selected){
    this.selected=selected;
  }

}
