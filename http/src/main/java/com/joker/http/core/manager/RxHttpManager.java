package com.joker.http.core.manager;

import io.reactivex.Observable;

public interface RxHttpManager<Request,Response>{

 Observable<Response> enqueue(Request request);
}
