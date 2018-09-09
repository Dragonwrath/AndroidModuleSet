package com.joker.http.okhttp.request;
import android.support.annotation.NonNull;

import com.joker.http.core.utils.PreConditions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ParamsPostRequest extends PostRequest<String>{

 private final LinkedHashMap<String,List<Object>> params=new LinkedHashMap<>();

 @Override
 public PostRequest body(String value){
  //nothing
  return this;
 }

 @Override
 RequestBody getBody(){
  FormBody.Builder bodyBuilder = new FormBody.Builder();
  for (String key : params.keySet()) {
   List<Object> urlValues = params.get(key);
   for (Object value : urlValues) {
    bodyBuilder.add(key, value.toString());
   }
  }
  return bodyBuilder.build();
 }

 /**
  * 只支持主要参数类型的参数，包含字符串，数字等。如果是需要添加指定的file，推荐使用FilePostRequest
  * @param key params的key
  * @param value params的value
  * @return 返回创建者对象
  */
 @Override
 public PostRequest addBody(String key,@NonNull Object value) throws IllegalArgumentException{
  PreConditions.requireStringNotNull(key,"param's key is null or empty");
  PreConditions.requestObjectNotNull(value,"value could not be null");
  List<Object> list=params.computeIfAbsent(key,new Function<String,List<Object>>(){
   @Override
   public List<Object> apply(String k){
    return new ArrayList<>();
   }
  });
  list.add(value);
  return this;
 }

}
