package com.joker.http.okhttp.interceptors;
import com.joker.http.core.user.HttpConfig;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 自定义Interceptor权重最高
 */
public class RetryInterceptor implements Interceptor{

 @Override
 public Response intercept(Chain chain) throws IOException{
  int count=HttpConfig.getRetryCount();
  Request request=chain.request();
  Response response=chain.proceed(request);
  int failureCount=0;
  while(failureCount<count&&!isUsefulCode(response.code())){
   response=chain.proceed(request);
   failureCount++;
  }
  return response;
 }

 private boolean isUsefulCode(int code) {
  return code>=200&&code<=300;
 }
}
