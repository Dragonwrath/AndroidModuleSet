package com.joker.notification;

import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 低于API24的版本使用这个类来解决Notification的文字的颜色问题，
 */
public class ColorResolverImpl{

 //todo 设置默认的notification的颜色

 public static int getTitleColor(Context context){

  int title=android.R.id.title;
  try{
   Class<?> clazz=Class.forName("com.android.internal.R$id");
   Field field=clazz.getField("title");
   field.setAccessible(true);
   title=field.getInt(null);
  } catch(ClassNotFoundException e){
   e.printStackTrace();
  }catch(IllegalAccessException e){
   e.printStackTrace();
  }catch(NoSuchFieldException e){
   e.printStackTrace();
  }
  return calculateColorInternal(context,title,Color.GREEN);
 }

 public static int getContentColor(Context context){
  int text=android.R.id.text1;
  try{
   Class<?> clazz=Class.forName("com.android.internal.R$id");
   Field field=clazz.getField("text");
   field.setAccessible(true);
   text=field.getInt(null);
  } catch(ClassNotFoundException e){
   e.printStackTrace();
  }catch(IllegalAccessException e){
   e.printStackTrace();
  }catch(NoSuchFieldException e){
   e.printStackTrace();
  }
  return calculateColorInternal(context,text,Color.BLUE);
 }

 private static int calculateColorInternal(Context context,@IdRes int id,@ColorInt int defaultColor){
  Notification notification=produceNotification(context);
  RemoteViews remoteViews=notification.contentView;
  if(remoteViews!=null){
   int layoutId=remoteViews.getLayoutId();
   View view=LayoutInflater.from(context).inflate(layoutId,null,false);
   View titleView=view.findViewById(id);
   if(titleView!=null&&titleView instanceof TextView){
    //todo  define custom color
    return ((TextView)titleView).getCurrentTextColor();
   }
  }
  return defaultColor;
 }

 private static Notification produceNotification(Context context){
  return new Notification.Builder(context)
    .setSmallIcon(R.drawable.ic_launcher)
    .setContentTitle("Title")
    .setContentText("Message")
    .build();
 }
}
