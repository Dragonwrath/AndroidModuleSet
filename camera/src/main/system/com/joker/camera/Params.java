package com.joker.camera;

class Params{
  int code;
  boolean useThumbnail;
  boolean useExternal;
  String suffix;
  String fileName;
  String root;

  Params(){
  }

  void setCode(int code){
    this.code=code;
  }

  void setUseThumbnail(boolean useThumbnail){
    this.useThumbnail=useThumbnail;
  }

  void setUseExternal(boolean useExternal){
    this.useExternal=useExternal;
  }

  void setSuffix(String suffix){
    this.suffix=suffix;
  }

  void setFileName(String fileName){
    this.fileName=fileName;
  }

  void setRoot(String root){
    this.root=root;
  }
}
