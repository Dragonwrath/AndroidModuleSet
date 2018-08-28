package com.joker.notification;
import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;
public class MessageNotificationFactory{

 private MessageNotificationFactory(){
  throw new AssertionError("This class can not be instantiated!");
 }

 public static void produceMessage(@NonNull Context context,@NonNull String content){
  produceMessage(context,"",content,null,context.getPackageName());
 }

 public static void produceMessage(@NonNull Context context,@Nullable String title,
                                   @NonNull String content,@Nullable PendingIntent intent){
  produceMessage(context,title,content,intent,context.getPackageName());
 }

 public static void produceMessage(
   @NonNull Context context,@Nullable String title,@NonNull String content,
   @Nullable PendingIntent intent,@NonNull String channel){
  if(TextUtils.isEmpty(content)) throw new IllegalArgumentException("content maybe  is null");

  NotificationCompat.Builder builder=generateBuilder(context,title,content,intent,channel);
  NotificationManagerCompat.from(context).notify(1024,builder.build());
 }

 public static void produceBigMessage(@NonNull Context context,@NonNull String content,
                                      @NonNull String bigContent){
  produceBigMessage(context,"",content,bigContent,null,context.getPackageName());
 }

 public static void produceBigMessage(@NonNull Context context,@Nullable String title,
                                      @NonNull String content,@NonNull String bigContent,
                                      @Nullable PendingIntent intent){
  produceBigMessage(context,title,content,bigContent,intent,context.getPackageName());
 }

 public static void produceBigMessage(
   @NonNull Context context,@Nullable String title,@NonNull String content,
   @NonNull String bigContent,@Nullable PendingIntent intent,@NonNull String channel){
  if(TextUtils.isEmpty(content)) throw new IllegalArgumentException("content maybe  is null");

  NotificationCompat.Builder builder=generateBuilder(context,title,content,intent,channel);
  builder.setStyle(new NotificationCompat.BigTextStyle().bigText(bigContent));
  NotificationManagerCompat.from(context).notify(1024,builder.build());
 }

 @NonNull
 private static NotificationCompat.Builder generateBuilder(
   @NonNull Context context,
   @Nullable String title,
   @NonNull String content,@Nullable PendingIntent intent,@NonNull String channel){
  NotificationCompat.Builder builder;
  if(TextUtils.isEmpty(channel)){
   builder=new NotificationCompat.Builder(context,context.getPackageName());
  }else{
   builder=new NotificationCompat.Builder(context,channel);
  }
  final RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.notification_message);

  if(TextUtils.isEmpty(title)){
   views.setCharSequence(R.id.title,"setText",context.getString(R.string.app_name));
  }else{
   views.setCharSequence(R.id.title,"setText",title);
  }
  views.setCharSequence(R.id.content,"setText",content);
  views.setTextColor(R.id.title,ColorResolverProxy.getTitleColor(context));
  views.setTextColor(R.id.content,ColorResolverProxy.getContentColor(context));
  if(intent!=null)
   builder.setContentIntent(intent);
  builder.setAutoCancel(true).setSmallIcon(R.drawable.ic_launcher).setContent(views);
  return builder;
 }

}
