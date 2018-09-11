package com.joker.http.core.ssl;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
public class SSLCustom{
 public void custom() {
  try{
   //以X.509形式获取证书
   CertificateFactory certificateFactory=CertificateFactory.getInstance("X.509");
   Certificate cert=certificateFactory.generateCertificate(getServerCertInputStream());
   cert.getType();
   //生成一个包含服务器端证书的KeyStore
   String keyStoreType=KeyStore.getDefaultType();
   KeyStore keyStore=KeyStore.getInstance(keyStoreType);
   keyStore.load(null,null);

   //用包含服务端证书的KeyStore生成一个TrustManager
   String algorithm=TrustManagerFactory.getDefaultAlgorithm();
   TrustManagerFactory trustManagerFactory=TrustManagerFactory.getInstance(algorithm);
   trustManagerFactory.init(keyStore);

   //生成一个我们的TrustManager的SSLContext
   SSLContext sslContext=SSLContext.getInstance("TLS");
   sslContext.init(null,trustManagerFactory.getTrustManagers(),null);

   URL url=new URL("");
   HttpsURLConnection httpsURLConnection=(HttpsURLConnection)url.openConnection();
   httpsURLConnection.setSSLSocketFactory(sslContext.getSocketFactory());
   InputStream in=httpsURLConnection.getInputStream();
   //todo update

  }catch(CertificateException e){
   e.printStackTrace();
  }catch(KeyStoreException e){
   e.printStackTrace();
  }catch(NoSuchAlgorithmException e){
   e.printStackTrace();
  }catch(IOException e){
   e.printStackTrace();
  }catch(KeyManagementException e){
   e.printStackTrace();
  }
 }

 private InputStream getServerCertInputStream() {
  return null;
 }
}
