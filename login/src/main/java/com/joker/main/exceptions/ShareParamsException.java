package com.joker.main.exceptions;

public class ShareParamsException extends IllegalArgumentException{

 public static final int ILLEGAL_TITLE=0x0001_0000;

 public static final int ILLEGAL_CONTENT=0x0002_0000;

 public static final int ILLEGAL_URL=0x0003_0000;

 public static final int ILLEGAL_THUMB=0x0004_0000;

 public static final int ILLEGAL_IMAGE=0x0005_0000;

 public static final int ILLEGAL_VIDEO=0x0006_0000;

 public static final int ILLEGAL_TYPE_EMPTY=0x0000_0001;

 public static final int ILLEGAL_TYPE_LONG=0x0000_0002;

 public static final int ILLEGAL_TYPE_ACCESS=0x0000_0003;

 private final int illegalType;

 public ShareParamsException(int type) {
  illegalType=type;
 }

}
