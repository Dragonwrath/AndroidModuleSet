package com.joker.http.connection.runnable;

import com.joker.http.connection.request.GetRequest;
import com.joker.http.core.header.HeadersConstant;
import com.joker.http.core.manager.ResponseCallback;
import com.joker.http.core.manager.ResponseData;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class GetRequestRunnable<T,R> extends RequestRunnable<T,R>{

 public GetRequestRunnable(GetRequest<T> request,ResponseCallback<ResponseData<R>> callback){
  super(request,callback);
 }

 @Override void beforeExecute() throws IOException{
  super.beforeExecute();
  //TODO android版本，如果使用doOutPut则默认为PUT
  connection.setDoOutput(false);
 }

 @Override void parseResponse(HttpURLConnection connection) throws Exception{
  Map<String,List<String>> map=connection.getHeaderFields();
  List<String> strings=map.get(HeadersConstant.HEAD_KEY_CONTENT_TYPE);
  for(String string : strings) {
   System.out.println("string = "+string);
   if(string.contains("json")) {
    handleJsonInputStream(connection.getInputStream());
    break;
   } else if(string.contains("application/vnd.android.package-archive")){
    handleApkDownload(connection.getInputStream());
    break;
   }
  }
 }

}
