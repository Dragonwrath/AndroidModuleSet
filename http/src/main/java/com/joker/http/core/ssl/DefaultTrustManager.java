package com.joker.http.core.ssl;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 使用自签名的
 */
public class DefaultTrustManager implements X509TrustManager{

 public DefaultTrustManager(){
 }

 @Override
 public void checkClientTrusted(X509Certificate[] x509Certificates,String authType) throws CertificateException{
  //noinspection all
 }

 @Override
 public void checkServerTrusted(X509Certificate[] x509Certificates,String authType) throws CertificateException{
  if(x509Certificates==null||x509Certificates.length==0){
   throw new IllegalArgumentException("Server X509Certificate is null or empty");
  }
  int failureCount=0;
  for(X509Certificate cert : x509Certificates) {
   //检查服务器端证书签名是否有问题
   try{
    cert.checkValidity();
    cert.verify(getServerCert().getPublicKey(),authType);
   } catch(GeneralSecurityException e){
    failureCount++;
   }
  }
  if(failureCount==x509Certificates.length) {
   throw new CertificateException("all verifies are failed");
  }
 }

 @Override public X509Certificate[] getAcceptedIssuers(){
  return new X509Certificate[0];
 }

 private X509Certificate getServerCert() throws CertificateException,NullPointerException{
  InputStream in=getServerCertInputStream();
  if(in==null) throw new NullPointerException("can't find server cert");
  BufferedInputStream inputStream=new BufferedInputStream(in);
  CertificateFactory factory=CertificateFactory.getInstance("X.509");
  return (X509Certificate)factory.generateCertificate(inputStream);
 }

 private InputStream getServerCertInputStream(){
  //FIXME  需要自己实现相应的细节，如果server的cert，放在assets目录下，可以getAssets().open(name)来获取
  return null;
 }
}
