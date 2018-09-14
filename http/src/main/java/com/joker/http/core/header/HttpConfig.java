package com.joker.http.core.header;
import com.joker.http.core.user.UserTokenListener;
import com.joker.http.core.utils.PreConditions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.annotations.NonNull;

/**
 * This class should to set global's config of HTTP params.
 *
 * <strong>Warning:this is not thread safe.</strong>
 */
public final class HttpConfig{

 private int retryCount=3;

 private long readTimeOut=20_000L;

 private long writeTimeOut=20_000L;

 private long connectTimeOut=20_000L;

 private UserTokenListener tokenListener=new UserTokenListener(){
  private final AtomicReference<String> tokenHolder=new AtomicReference<>();
  @Override
  public String getUserToken(){
   return tokenHolder!=null?(tokenHolder.get()):null;
  }

  @Override
  public void onTokenChanged(String newToken){
   tokenHolder.compareAndSet(tokenHolder.get(),newToken);
  }

  @Override
  public void onTokenError(){
   //todo hold some situations when token's verify failed;
  }
 };

 private HashMap<String,String> headers=new HashMap<>();

 private HttpConfig(){
  headers.put("Accept-Language","zh-CN,zh;q=0.8");
 }

 private final static class Holder{
  private static HttpConfig Instance=new HttpConfig();
 }

 public static int getRetryCount(){
  return Holder.Instance.retryCount;
 }

 public static long getReadTimeOut(){
  return Holder.Instance.readTimeOut;
 }

 public static long getWriteTimeOut(){
  return Holder.Instance.writeTimeOut;
 }

 public static long getConnectTimeOut(){
  return Holder.Instance.connectTimeOut;
 }

 public static HashMap<String,String> getHeaders(){
  return Holder.Instance.headers;
 }

 public static UserTokenListener getUserToken(){
  return Holder.Instance.tokenListener;
 }

 private final static class Builder{
  final HttpConfig config;

  public Builder(){
   config=new HttpConfig();
   config.headers=new LinkedHashMap<>();
   config.headers.put(HeadersConstant.HEAD_KEY_ACCEPT_ENCODING,HeadersConstant.HEAD_VALUE_ACCEPT_ENCODING);
   config.headers.put(HeadersConstant.HEAD_KEY_CONNECTION,HeadersConstant.HEAD_VALUE_CONNECTION_KEEP_ALIVE);
  }

  public Builder retryCount(int count){
   config.retryCount=count;
   return this;
  }

  public Builder readTimeout(long timeout){
   config.readTimeOut=timeout;
   return this;
  }

  public Builder writeTimeOut(long timeout){
   config.writeTimeOut=timeout;
   return this;
  }

  public Builder connectTimeOut(long timeout){
   config.connectTimeOut=timeout;
   return this;
  }

  public Builder userTokenListener(UserTokenListener listener){
   config.tokenListener=listener;
   return this;
  }

  public Builder addHeader(@NonNull String key,@NonNull String value){
   PreConditions.requireStringNotNull(key,"head's key is null or empty");
   PreConditions.requireStringNotNull(value,"head's value is null or empty");
   config.headers.put(key,value);
   return this;
  }

  public HttpConfig build(){
   Holder.Instance=config;
   return Holder.Instance;
  }
 }

}
