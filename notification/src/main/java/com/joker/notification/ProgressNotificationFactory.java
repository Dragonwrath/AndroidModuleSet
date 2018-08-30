package com.joker.notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;
public class ProgressNotificationFactory{

 private ProgressNotificationFactory(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public static void produceProgress(Context context,int progress,int total){
  produceProgress(context,progress,total,context.getPackageName());
 }

 /**
  * @param context  上下文对象用来获取一些必要的属性
  * @param progress 该参数需要根据相应进度来进行设置
  * @param total    总共的进度的长度
  */
 public static void produceProgress(Context context,int progress,int total,String channel){
  if(context==null||progress<0||total<=0)
   throw new IllegalArgumentException("context maybe  is null,or progress must be positive,or total must be lager than 0");
  if(progress>total){
   return;
  }

  NotificationCompat.Builder builder=new NotificationCompat.Builder(context,channel);
  RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.notification_process);
  views.setTextColor(R.id.title,ColorResolverProxy.getTitleColor(context));
  views.setProgressBar(R.id.progress,total,progress,false);
  builder.setSmallIcon(R.drawable.ic_launcher).setContent(views);
  NotificationManagerCompat.from(context).notify(9527,builder.build());
 }
}
