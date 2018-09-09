package com.joker.http.connection;
import com.joker.http.connection.request.BaseRequest;
import com.joker.http.connection.request.Request;
import com.joker.http.core.manager.HttpManager;
import com.joker.http.core.header.HttpConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.internal.Util;
import okio.ByteString;

public abstract class HttpConnectionManager implements HttpManager{

 public static  <T> void connection(Request<T> request) throws IOException{
  HttpURLConnection connection;
  InputStream in=null;
  OutputStream out=null;
  try{
   String url=request.url();
   if(url.startsWith("http")){
    connection=(HttpURLConnection)new URL(url).openConnection();
   }else{
    connection=(HttpsURLConnection)new URL(url).openConnection();
    HttpsURLConnection httpsURLConnection=(HttpsURLConnection)connection;
    //todo update ssl
    httpsURLConnection.setSSLSocketFactory((SSLSocketFactory)SSLSocketFactory.getDefault());
    httpsURLConnection.setHostnameVerifier(new HostnameVerifier(){
     @Override
     public boolean verify(String s,SSLSession sslSession){
      return true;
     }
    });
   }
   connection.setRequestMethod(request.method());
   connection.setConnectTimeout((int)HttpConfig.getConnectTimeOut());
   connection.setReadTimeout((int)HttpConfig.getReadTimeOut());
   connection.setDoInput(true);
   connection.setDoOutput(true);
   HashMap<String,String> map=request.headers();
   for(String key : map.keySet()) {
    connection.setRequestProperty(key,map.get(key));
   }
   out=connection.getOutputStream();
   final OutputStream mirrorOut= out;
   Observable.just(request.bodies())
     .subscribeOn(Schedulers.io())
     .map(new Function<HashMap<String,Object>,Object>(){
      @Override
      public Object apply(HashMap<String,Object> bodies) throws Exception{
       if(bodies!=null){
        byte[] cache=new byte[512];
        int len;
        Set<Map.Entry<String,Object>> set=bodies.entrySet();
        if(set.size()==1){
         Iterator<String> iterator=bodies.keySet().iterator();
         String key=iterator.next();
         Object object=bodies.get(key);
         if(object instanceof File){
          File file=(File)object;
          FileInputStream fileIn=new FileInputStream(file);

          while((len=fileIn.read(cache))>0){
           mirrorOut.write(cache,0,len);
           mirrorOut.flush();
          }
         } else {
          Object o=bodies.get(BaseRequest.EMPTY_KEY);
          mirrorOut.write(o.toString().getBytes());
          mirrorOut.flush();

         }
        } else {
         StringBuilder builder=new StringBuilder();
         Set<String> keySet=bodies.keySet();
         int count=0;
         for(String key : keySet) {
          builder.append(key).append("=").append(bodies.get(key));
          if(++count<keySet.size()){
           builder.append("&");
          }
         }
         ByteString byteString=ByteString.encodeUtf8(builder.toString());
         byteString.write(mirrorOut);
        } int responseCode=connection.getResponseCode();
        Map<String,List<String>> responseHeaders=connection.getHeaderFields();
        System.out.println("responseCode = "+responseCode);
        String responseMessage=connection.getResponseMessage();
        System.out.println("responseMessage = "+responseMessage);
        if(responseCode>=200&&responseCode<300) {
         convertInputStream(cache,connection.getInputStream());
        } else if(responseCode>=300&&responseCode<400) {
         convertInputStream(cache,connection.getErrorStream());
        } else if(responseCode>=400) {
         convertInputStream(cache,connection.getErrorStream());
        }
       }
       return new Object();
      }
     })
     .doFinally(new Action(){
    @Override
    public void run() throws Exception{
     System.out.println("HttpConnectionManager.Action.run");
     Util.closeQuietly(mirrorOut);
    }
   }).subscribe(new Observer<Object>(){
    @Override
    public void onSubscribe(Disposable d){

    }

    @Override
    public void onNext(Object o){
     System.out.println("HttpConnectionManager.Action.onNext");

    }

    @Override
    public void onError(Throwable e){
     e.printStackTrace();
    }

    @Override
    public void onComplete(){
     System.out.println("HttpConnectionManager.Action.onComplete");

    }
   });


  }finally{
   Util.closeQuietly(in);
   Util.closeQuietly(out);
  }
 }

 private static void convertInputStream(byte[] cache,InputStream mirrorIn) throws IOException{
  int len;
  StringBuilder builder=new StringBuilder();
  while((len=mirrorIn.read(cache))>0){
   builder.append(new String(cache,0,len,"utf-8"));
  }
  System.out.println(builder.toString());
 }

}
