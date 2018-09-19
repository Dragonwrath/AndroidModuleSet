package com.joker.http.connection.runnable;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.joker.http.connection.request.BaseRequest;
import com.joker.http.core.JsonHelper;
import com.joker.http.core.exceptions.HttpException;
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

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500Principal;

/**
 *
 * @param <Request> 请求的泛型
 * @param <Response> 返回的接收类型
 */
abstract class RequestRunnable<Request,Response> implements Runnable{

 BaseRequest<Request> request;
 HttpURLConnection connection;
 ResponseCallback<ResponseData<Response>> callback;

 RequestRunnable(BaseRequest<Request> request,ResponseCallback<ResponseData<Response>> callback){
  this.request=request;
  this.callback=callback;
 }

 void beforeExecute() throws IOException{
  String url=request.url();
  if(url.startsWith("https")){
   connection=(HttpsURLConnection)new URL(url).openConnection();
   HttpsURLConnection httpsURLConnection=(HttpsURLConnection)connection;
   //FIXME update ssl
   httpsURLConnection.setSSLSocketFactory((SSLSocketFactory)SSLSocketFactory.getDefault());
   httpsURLConnection.setHostnameVerifier((hostname,sslSession)->{
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
   });
  } else {
   connection=(HttpURLConnection)new URL(url).openConnection();
  }
  connection.setRequestMethod(request.method());
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
   writeToConnection();
   int code=connection.getResponseCode();
   if(code>=200&&code<300) {
    parseResponse(connection);
   } else {
    throw new HttpException(code,connection.getResponseMessage());
   }
  } catch(Exception e) {
   callback.onFailure(e);
  } finally{
   connection.disconnect();
  }
 }

 void writeToConnection() throws Exception{

 }

 void afterExecute() throws Exception{
  connection.disconnect();
 }


 @Override public void run(){
  try{
   beforeExecute();
   execute();
  }catch(Exception e){
   callback.onFailure(e);
  } finally{
   try{
    afterExecute();
   }catch(Exception e){
    //release resource exception
   }
  }
 }

 abstract void parseResponse(HttpURLConnection connection) throws Exception;

 void handleJsonInputStream(InputStream inputStream) throws JsonParseException{
  callback.onSuccess(JsonHelper.deSerial(new JsonReader(new InputStreamReader(inputStream)),new TypeToken<ResponseData<Response>>(){}));
 }

 void handleApkDownload(InputStream inputStream) throws IOException{
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
   ResponseData<File> responseData=new ResponseData<>(String.valueOf(200),connection.getResponseMessage(),apkFile);
   callback.onSuccess((ResponseData<Response>)responseData);
  } finally{
   okhttp3.internal.Util.closeQuietly(writer);
   okhttp3.internal.Util.closeQuietly(inputStream);
  }
 }
}
