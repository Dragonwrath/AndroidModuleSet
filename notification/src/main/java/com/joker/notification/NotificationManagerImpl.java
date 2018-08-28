package com.joker.notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class NotificationManagerImpl{
 private final static String PACKAGE="com.joker.notification";

 public static class Record{
  public final static String GROUP=PACKAGE+".record";
  public final static String UPDATE=GROUP+".UPDATE";
 }

 private final static String CONFIG="config";
 private final static String NOTIFICATION="notification";
 private static volatile boolean hasCreated=false;
 private final static Semaphore semaphore=new Semaphore(1);

 /**
  * should init this in WorkThread
  *
  * @param context 上下文对象
  */
 @WorkerThread
 public static void init(Context context){
  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ //support 8.0
   Context applicationContext=context.getApplicationContext();
   SharedPreferences preferences=applicationContext.getSharedPreferences(CONFIG,Context.MODE_PRIVATE);
   int nVersion=preferences.getInt(NOTIFICATION,0);
   if(nVersion<BuildConfig.NOTIFICATION){
    try{
     //FIXME custom group
     semaphore.tryAcquire(20,TimeUnit.SECONDS);
     ArrayList<NotificationChannelGroup> groups=new ArrayList<>();
     ArrayList<NotificationChannel> channels=new ArrayList<>();
     //recorder groups
     NotificationChannelGroup recorderGroup=new NotificationChannelGroup(Record.GROUP,context.getString(R.string.app_name));
     groups.add(recorderGroup);

     //create channels
     NotificationChannel channel=new NotificationChannel(Record.UPDATE,context.getString(R.string.app_name),NotificationManager.IMPORTANCE_DEFAULT);
     channel.setGroup(Record.GROUP);
     channels.add(channel);

     NotificationChannel channel1=new NotificationChannel(context.getPackageName(),context.getString(R.string.app_name),NotificationManager.IMPORTANCE_DEFAULT);
     channel1.setGroup(Record.GROUP);
     channels.add(channel1);


     //add all groups and channels
     Object service=context.getSystemService(Context.NOTIFICATION_SERVICE);
     if(service!=null&&service instanceof NotificationManager){
      NotificationManager manager=(NotificationManager)service;
      manager.createNotificationChannelGroups(groups);
      manager.createNotificationChannels(channels);
     }

     preferences.edit().putInt(NOTIFICATION,BuildConfig.NOTIFICATION).apply();
    }catch(Exception e){
     e.printStackTrace();
    }finally{
     //maybe some situation to make this action of creating notification failure,
     //so just try to
     hasCreated=true;
     semaphore.release();
    }
   }
  }else{
   hasCreated=true;
  }
 }

 public static boolean isFinishInitial(){
  try{
   semaphore.tryAcquire(20,TimeUnit.SECONDS);
   return hasCreated;
  }catch(InterruptedException e){
   //nothing to catch
  }finally{
   semaphore.release();
  }
  return false;
 }
}
