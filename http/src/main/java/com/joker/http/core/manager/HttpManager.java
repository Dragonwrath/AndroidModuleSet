package com.joker.http.core.manager;

public interface HttpManager<Request>{

 <Response> void enqueue(Request request,Object tag,ResponseCallback<ResponseData<Response>> callback);

 void cancel(Object tag);

}
