package com.joker.http.core.manager;

public interface ProgressCallback<Response>{
 void onProgress(Response response,double progress);

 void onSuccess(Response response);

 void onFailure(Throwable throwable);
}
