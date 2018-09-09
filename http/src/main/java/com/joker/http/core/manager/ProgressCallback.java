package com.joker.http.core.manager;

public interface ProgressCallback<Response> extends ResponseCallback<Response>{
 void onProgress(Response response,double progress);
}
