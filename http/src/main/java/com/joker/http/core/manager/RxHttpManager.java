package com.joker.http.core.manager;

import io.reactivex.Observable;

public interface RxHttpManager{
 <Request,Response> Observable<ResponseData<Response>> enqueue(Request request,Object tag);

 void cancel(Object tag);
}
