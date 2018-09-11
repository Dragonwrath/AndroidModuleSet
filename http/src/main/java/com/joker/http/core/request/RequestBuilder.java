package com.joker.http.core.request;

import android.support.annotation.Nullable;
import android.support.annotation.NonNull;

public interface RequestBuilder<Request,Builder extends RequestBuilder>{

 /**
  * 设置对应的url
  * @param url 对应的url字符串，必须以字母结尾
  * @return 返回创建者对象
  * @exception IllegalArgumentException 检测url中是否以http开头，字母结尾
  */
 Builder url(String url) throws IllegalArgumentException;

 /**
  * 添加相应的Head,需要注意的是，只支持UTF-8编码
  * @param key head的key
  * @param value head的value
  * @return 返回创建者对象
  * @exception IllegalArgumentException 检查key是否为空，value是否为空
  */
 Builder addHead(@NonNull String key,@NonNull String value) throws IllegalArgumentException;

 /**
  * 添加相应的参数，可能在某些请求中未使用，key只支持UTF-8编码，value推荐编码
  * @param key params的key
  * @param value params的value
  * @return 返回创建者对象
  * @exception IllegalArgumentException 检查key是否为空，value是否为空
  */
 Builder addParams(@Nullable String key,@NonNull Object value) throws IllegalArgumentException;

 /**
  * 构建对应的Request对象，依赖相应的网络框架进行封装
  * @return 构建的Request对象
  */
  Request build();
}
