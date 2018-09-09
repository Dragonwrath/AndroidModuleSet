package com.joker.http.connection.request;

import android.support.annotation.Nullable;

import android.support.annotation.NonNull;

import java.util.HashMap;

public interface Request<T>{

 String method();

 @NonNull String url();

 @Nullable HashMap<String,String> headers();

 @Nullable HashMap<String,Object> bodies();
}
