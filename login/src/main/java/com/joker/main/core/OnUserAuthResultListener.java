package com.joker.main.core;
import com.joker.main.UserInfo;
public interface OnUserAuthResultListener{
 void onAuthSuccess(UserInfo userInfo);

 void onAuthCancel();

 void onAuthFailure(Throwable throwable);
}
