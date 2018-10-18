package com.joker.scan.core;

public interface OnScanResultListener{

 void onScanSuccess(String message);

 void onScanFailure(Exception ex);
}
