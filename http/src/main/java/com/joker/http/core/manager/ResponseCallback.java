package com.joker.http.core.manager;
public interface ResponseCallback<Response>{
 void onSuccess(Response response);

 void onFailure(Throwable throwable);
}
