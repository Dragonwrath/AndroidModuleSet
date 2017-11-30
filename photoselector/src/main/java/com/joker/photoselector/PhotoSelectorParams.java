package com.joker.photoselector;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PhotoSelectorParams implements Parcelable{

  private final int mode;
  private final int maxLimit;
  private final ArrayList<String> filePaths;

  public PhotoSelectorParams(int mode,int maxLimit,ArrayList<String> filePaths){
    this.mode=mode;
    this.maxLimit=maxLimit;
    this.filePaths=filePaths;
  }

  protected PhotoSelectorParams(Parcel in){
    mode = in.readInt();
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
    dest.writeInt(mode);
    dest.writeInt(maxLimit);
    dest.writeStringList(filePaths);
  }
}
