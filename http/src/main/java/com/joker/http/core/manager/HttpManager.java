package com.joker.http.core.manager;

import java.io.IOException;
public interface HttpManager<Request,Response>{

 Response enqueue(Request request) throws IOException;

 void enqueue(Request request,ResponseCallback<Response> callback);

 void enqueue(Request request,ProgressCallback<Response> callback);
}
