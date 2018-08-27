package com.joker.notification.color;

import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.joker.notification.R;

/**
 * 低于API24的版本使用这个类来解决Notification的文字的颜色问题，
 */
public class ColorResolverImpl implements ColorResolver{

 //todo 设置默认的notification的颜色

 @Override
 public int getTitleColor(Context context){
  return calculateColorInternal(context,android.R.id.title);
 }


 @Override
 public int getContentColor(Context context){
  return calculateColorInternal(context,android.R.id.text1);
 }

 private int calculateColorInternal(Context context,@IdRes int id){
  Notification notification=produceNotification(context);
  RemoteViews remoteViews=notification.contentView;
  if(remoteViews!=null) {
   int layoutId=remoteViews.getLayoutId();
   View view=LayoutInflater.from(context).inflate(layoutId,null,false);
   View titleView=view.findViewById(id);
   if(titleView!=null&& titleView instanceof TextView) {
    //todo  define custom color
    return ((TextView)titleView).getCurrentTextColor();
   }
   return nonRecursiveFindColor(view,id);
  }
  return Color.BLACK;
 }

 private @ColorInt int nonRecursiveFindColor(View source,@IdRes int id){
  ViewParent parent = source.getParent();
  ViewGroup group;
  View child;
  int possibleColor=Color.BLACK;
  while(parent!=null&& parent instanceof ViewGroup) {
   group=(ViewGroup)parent;
   int count=group.getChildCount();
   for(int i=0;i<count;i++) {
    child=group.getChildAt(i);
    if(child instanceof TextView){
     possibleColor=((TextView)child).getCurrentTextColor();
     if(child.getId()==id){
      return possibleColor;
     }
    }
   }
   parent=group.getParent();
  }
  return possibleColor;
 }


 private Notification produceNotification(Context context) {
  return new Notification.Builder(context)
    .setSmallIcon(R.drawable.ic_launcher)
    .setContentTitle("Title")
    .setContentText("Message")
    .build();
 }
}
