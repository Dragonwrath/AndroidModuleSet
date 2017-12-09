package com.joker.common.utils.handler;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class HandlerFactory{
  private final static Map<String,Handler> MAP = new ConcurrentHashMap<>();

  private HandlerFactory() {
    throw new AssertionError("This class can not be instantiated");
  }

  public static Handler newInstance(Context context,Handler.Callback callback){
    return new WeakHandler(context,callback);
  }

  public static Handler newInstance(String looperName, Handler.Callback callback) {
    HandlerThread thread=new HandlerThread(looperName);
    thread.start();
    Looper looper=thread.getLooper();
    Handler handler=new Handler(looper,callback);
    MAP.put(looperName, handler);
    return handler;
  }

  public static void terminateHandler(String looperName){
    Handler handler=MAP.get(looperName);
    if(handler!= null){
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
        handler.getLooper().quitSafely();
      } else {
        handler.getLooper().quit();
      }
    }
    MAP.remove(looperName);
  }

  private static class WeakHandler extends Handler{
    private final WeakReference<Context> reference;

    private WeakHandler(Context context,Callback callback){
      super(callback);
      reference=new WeakReference<>(context);
    }

    @Override
    public void handleMessage(Message msg){
      if(reference.get()!= null) {
        dispatchMessage(msg);
      }
    }
  }
}
