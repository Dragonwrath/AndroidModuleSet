package com.joker.http.connection.runnable;
import com.joker.http.connection.request.JsonPostRequest;
import com.joker.http.core.header.HeadersConstant;
import com.joker.http.core.manager.ResponseCallback;
import com.joker.http.core.manager.ResponseData;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.internal.Util;
public class JsonPostRequestRunnable<T> extends RequestRunnable<String,T>{

 public JsonPostRequestRunnable(JsonPostRequest request,ResponseCallback<ResponseData<T>> callback){
  super(request,callback);
 }

 @Override void writeToConnection() throws Exception{
  super.writeToConnection();
  OutputStream out=null;
  try {
   out=connection.getOutputStream();
   HashMap<String,Object> map=request.bodies();
   if(map!=null){
    String json=(String)map.get(JsonPostRequest.EMPTY_KEY);
    out.write(json.getBytes("utf-8"));
   }
  } finally{
   Util.closeQuietly(out);
  }
 }

 @Override void parseResponse(HttpURLConnection connection) throws Exception{
  Map<String,List<String>> map=connection.getHeaderFields();
  List<String> strings=map.get(HeadersConstant.HEAD_KEY_CONTENT_TYPE);
  for(String string : strings) {
   if(string.contains("json")) {
    handleJsonInputStream(connection.getInputStream());
    break;
   }
  }
 }

}
