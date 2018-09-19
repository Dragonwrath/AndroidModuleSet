package com.joker.main.core;

import com.joker.main.UserInfo;
public interface LoginMsgBus{

 void onSuccess(UserInfo info);

 void cancel();

 void onFailure(Throwable throwable);
}
