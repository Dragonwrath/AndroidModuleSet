package com.joker.scan.core;

import android.graphics.Rect;
public interface DecodeManager{

 void registerScanListener(OnScanResultListener listener);

 void decode(byte[] data,int width,int height,Rect frameRect);

 void onDestroy();

}
