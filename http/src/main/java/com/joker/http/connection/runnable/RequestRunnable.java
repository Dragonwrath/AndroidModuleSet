package com.joker.http.connection.runnable;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.joker.http.connection.request.GetRequest;
import com.joker.http.connection.request.Request;
import com.joker.http.core.JsonHelper;
import com.joker.http.core.header.HeadersConstant;
import com.joker.http.core.header.HttpConfig;
import com.joker.http.core.manager.ResponseCallback;
import com.joker.http.core.manager.ResponseData;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500Principal;

abstract class RequestRunnable<T> implements Runnable{

 Request request;
 HttpURLConnection connection;
 ResponseCallback<ResponseData<T>> callback;

 RequestRunnable(GetRequest request,ResponseCallback<ResponseData<T>> callback){
  this.request=request;
  this.callback=callback;
 }

 void beforeExecute() throws IOException{
  String url=request.url();
  if(url.startsWith("http")){
   connection=(HttpURLConnection)new URL(url).openConnection();
  } else if(url.startsWith("https")){
   connection=(HttpsURLConnection)new URL(url).openConnection();
   HttpsURLConnection httpsURLConnection=(HttpsURLConnection)connection;
   //todo update ssl
   httpsURLConnection.setSSLSocketFactory((SSLSocketFactory)SSLSocketFactory.getDefault());
   httpsURLConnection.setHostnameVerifier(new HostnameVerifier(){
    @Override
    public boolean verify(String hostname,SSLSession sslSession){
     try {
      String peerHost = sslSession.getPeerHost(); //服务器返回的主机名
      X509Certificate[] peerCertificates = (X509Certificate[]) sslSession
        .getPeerCertificates();
      for (X509Certificate certificate : peerCertificates) {
       X500Principal subjectX500Principal = certificate
         .getSubjectX500Principal();
       String name = subjectX500Principal.getName();
       String[] split = name.split(",");
       for (String str : split) {
        if (str.startsWith("CN")) {//证书绑定的域名或者ip
         if (peerHost.equals(hostname)&&str.contains("客户端预埋的证书cn字段域名")) {
          return true;
         }
        }
       }
      }
     } catch (SSLPeerUnverifiedException e1) {
      e1.printStackTrace();
     }
     return false;
    }
   });
  }
  connection.setDoInput(true);
  connection.setDoOutput(true);
  connection.setReadTimeout((int)HttpConfig.getReadTimeOut());
  connection.setConnectTimeout((int)HttpConfig.getConnectTimeOut());
  HashMap<String,String> commonHeaders=HttpConfig.getHeaders();
  if(commonHeaders.size()>0) {
   for(String key : commonHeaders.keySet()) {
    connection.setRequestProperty(key,commonHeaders.get(key));
   }
  }
  HashMap<String,String> headers=request.headers();
  if(headers!=null&&headers.size()>0) {
   for(String key : headers.keySet()) {
    connection.setRequestProperty(key,headers.get(key));
   }
  }
 }

 void execute() throws Exception{
  try{
   int code=connection.getResponseCode();
   if(code>=200&&code<300) {
    Map<String,List<String>> map=connection.getHeaderFields();
    List<String> strings=map.get(HeadersConstant.HEAD_KEY_CONTENT_TYPE);
    for(String string : strings) {
     System.out.println("string = "+string);
     if(string.contains("json")) {
      handleJsonInputStream(connection.getInputStream());
     } else if(string.contains("application/vnd.android.package-archive")){
      handleApkDownload(connection.getInputStream());
     }
    }
   } else if(code>=300&&code<400) {

   } else if(code >=400) {

   }
  } catch(Exception e) {
   callback.onFailure(e);
  } finally{
   connection.disconnect();
  }
 }

 private void handleJsonInputStream(InputStream inputStream) throws JsonParseException{
  callback.onSuccess(JsonHelper.deSerial(new JsonReader(new InputStreamReader(inputStream)),new TypeToken<ResponseData<T>>(){}));
 }

 private void handleApkDownload(InputStream inputStream) throws IOException {
  BufferedOutputStream writer=null;
  try{
   System.out.println(new Date());
  File apkFile=new File("D:\\ModuleSet\\AndroidModuleSet\\cache.apk");
  if(!apkFile.exists()) {
   if(!apkFile.createNewFile()){
    throw new IOException("can't create file");
   }
  }
  writer=new BufferedOutputStream(new FileOutputStream(apkFile));
  int len;
  byte[] cache=new byte[1024];
  while((len=inputStream.read(cache))>0){
   writer.write(cache,0,len);
   writer.flush();
  }
   System.out.println(new Date());
   callback.onSuccess((ResponseData<T>)new ResponseData<>(String.valueOf(200),connection.getResponseMessage(),apkFile));
  } finally{
   okhttp3.internal.Util.closeQuietly(writer);
   okhttp3.internal.Util.closeQuietly(inputStream);
  }
 }

}
