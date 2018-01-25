package com.joker.common.utils.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


public class MessageNotification{

  public static void notify(Context context,@StringRes int title,
                     @StringRes int message,PendingIntent intent,String tag){
    Resources resources=context.getResources();
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
    Notification notification=builder.setContentTitle(resources.getString(title))
        .setContentText(resources.getString(message))
        //todo must add icon
//        .setSmallIcon(R.drawable.ic_notice)
        .setContentIntent(intent)
        .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
//        .setSound(Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.normal)) //optional
        .build();
    NotificationManagerCompat manager=NotificationManagerCompat.from(context);
    manager.notify(tag,0,notification);
  }

  public static void cancel(Context context,String tag){
    NotificationManagerCompat manager=NotificationManagerCompat.from(context);
    manager.cancel(tag,0);
  }
}
