package com.joker.camera;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class SystemCamera{

  private final CameraCore camera;

  private SystemCamera(AppCompatActivity act,Params params){
    camera=new AppCompatActivityCamera(act,params);
  }

  private SystemCamera(Fragment frag,Params params){
    camera=new FragmentCamera(frag,params);
  }

  public void openCamera() throws CameraOpenFailedException{
    camera.openCamera();
  }

  public void handleResult(Intent data,ImageView view){
    camera.handleActivityResult(data,view);
  }

  public Uri getOutputUri(){
    return camera.getOutputUri();
  }

  public static class Builder{
    private AppCompatActivity act;
    private Fragment frg;
    private int code;
    private boolean useThumbnail;
    private boolean useExternal;
    private String suffix;
    private String fileName;
    private String root;

    public Builder(AppCompatActivity act){
      this.act =act;
    }

    public Builder(Fragment frag){
      frg=frag;
    }

    public Builder setRequestCode(int code){
      this.code=code;
      return this;
    }

    public Builder setUseThumbnail(boolean useThumbnail){
      this.useThumbnail=useThumbnail;
      return this;
    }

    public Builder setUseExternal(boolean useExternal){
      this.useExternal=useExternal;
      return this;
    }

    public Builder setSuffix(String suffix){
      this.suffix=suffix;
      return this;
    }

    public Builder setFileName(String fileName){
      this.fileName=fileName;
      return this;
    }

    public Builder setRoot(String root){
      this.root=root;
      return this;
    }

    public SystemCamera create(){
      SystemCamera camera;
      Params params=new Params();
      if(code>0) {
        params.setCode(code);
      } else {
        throw new IllegalArgumentException("Request code must be positive");
      }
      params.setFileName(fileName);
      params.setRoot(root);
      params.setSuffix(suffix);
      params.setUseThumbnail(useThumbnail);
      params.setUseExternal(useExternal);
      if(act!=null){
        camera=new SystemCamera(act,params);
      } else if(frg!=null){
        camera=new SystemCamera(frg,params);
      } else{
        throw new IllegalArgumentException("Must set AppCompatActivityCamera or Fragment!");
      }
      return camera;
    }
  }
}
