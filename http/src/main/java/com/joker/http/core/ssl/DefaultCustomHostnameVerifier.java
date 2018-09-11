package com.joker.http.core.ssl;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class DefaultCustomHostnameVerifier implements HostnameVerifier{
 @Override public boolean verify(String s,SSLSession sslSession){
  System.out.println(s);
  return HttpsURLConnection.getDefaultHostnameVerifier().verify(s,sslSession);
 }
}
