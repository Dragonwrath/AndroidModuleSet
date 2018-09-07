package com.joker.http.core.user;
public interface UserTokenListener{

 String getUserToken();

 void onTokenChanged(String newToken);

 void onTokenError();
}
