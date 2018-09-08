package com.joker.http.okhttp.interceptors;

/**
 *  //自定义的interceptors，添加的顺序就是默认的劫持的顺序
 *  interceptors.addAll(client.interceptors());
 *  interceptors.add(retryAndFollowUpInterceptor);
 *  interceptors.add(new BridgeInterceptor(client.cookieJar())); //可以辅助设置cookie,源于client.cookieJar
 *  interceptors.add(new CacheInterceptor(client.internalCache()));  //缓存中的
 *  interceptors.add(new ConnectInterceptor(client));
 *  if (!forWebSocket) {
 *   interceptors.addAll(client.networkInterceptors());
 *  }
 *
 *
 */
