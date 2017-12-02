package com.joker.photoselector;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class PhotoSelectorParams implements Parcelable{


  private final int maxLimit;
  private final ArrayList<String> filePaths;

  public PhotoSelectorParams(int maxLimit,ArrayList<String> filePaths){
    this.maxLimit=maxLimit;
    this.filePaths=filePaths;
  }

  public int getMaxLimit(){
    return maxLimit;
  }

  public ArrayList<String> getFilePaths(){
    return filePaths;
  }

  protected PhotoSelectorParams(Parcel in){
    maxLimit = in.readInt();
    filePaths=new ArrayList<>();
    in.readStringList(filePaths);
  }

  public static final Creator<PhotoSelectorParams> CREATOR=new Creator<PhotoSelectorParams>(){
    @Override
    public PhotoSelectorParams createFromParcel(Parcel in){
      return new PhotoSelectorParams(in);
    }

    @Override
    public PhotoSelectorParams[] newArray(int size){
      return new PhotoSelectorParams[size];
    }
  };

  @Override
  public int describeContents(){
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest,int flags){
    dest.writeInt(maxLimit);
    dest.writeStringList(filePaths);
  }
}
