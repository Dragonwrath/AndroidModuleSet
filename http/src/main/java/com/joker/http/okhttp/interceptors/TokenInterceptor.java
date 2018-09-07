package com.joker.http.okhttp.interceptors;
import com.joker.http.core.user.UserTokenListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor{

 private UserTokenListener tokenListener;

 public TokenInterceptor(UserTokenListener tokenListener){
  this.tokenListener=tokenListener;
 }

 @Override
 public Response intercept(Chain chain) throws IOException{
  final Request request=chain.request();
  Response response=chain.proceed(request);
  String originalToken=request.header("token");
  String newToken=response.header("token");
  if(newToken!=null&&newToken.length()>0){ //有token
   if(!newToken.equals(originalToken))
    tokenListener.onTokenChanged(newToken);
  }else{
   if(originalToken!=null&&originalToken.length()>0){
    tokenListener.onTokenError();
   }
  }
  return response;
 }
}
