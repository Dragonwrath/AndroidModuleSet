package com.joker.http.core.ssl;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/**
 * 可信任的CA机构颁发的证书可以使用这个类
 */
public class DefaultTrustedSSLFactory{
 private DefaultTrustedSSLFactory(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public static SSLSocketFactory getDefaultSSLServerSocketFactory() {
  try{
   SSLContext context=SSLContext.getInstance("TLS");
   context.init(null,null,null);
   return context.getSocketFactory();
  }catch(NoSuchAlgorithmException | KeyManagementException e){
   //NOTHING
  }
  return null;
 }
}
