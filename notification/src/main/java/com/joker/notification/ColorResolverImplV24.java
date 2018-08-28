package com.joker.notification;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

@RequiresApi(Build.VERSION_CODES.N)
public class ColorResolverImplV24 {

 public static int getTitleColor(@NonNull Context context){
  TextView textView=new TextView(context);
  try{
   textView.setTextAppearance(android.R.style.TextAppearance_Material_Notification_Title);
   return textView.getCurrentTextColor();
  } catch(Exception e){
   try{
    textView.setTextAppearance(android.R.style.TextAppearance_StatusBar_EventContent_Title);
    return textView.getCurrentTextColor();
   } catch(Exception ex){
    return Color.BLACK;
   }
  }
 }

 public static int getContentColor(@NonNull Context context){
  TextView textView=new TextView(context);
  try{
   textView.setTextAppearance(android.R.style.TextAppearance_Material_Notification_Info);
   return textView.getCurrentTextColor();
  } catch(Exception e){
   try{
    textView.setTextAppearance(android.R.style.TextAppearance_StatusBar_EventContent);
    return textView.getCurrentTextColor();
   } catch(Exception ex){
    return Color.LTGRAY;
   }
  }
 }
}
