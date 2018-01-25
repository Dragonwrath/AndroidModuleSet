package com.joker.common.utils.widgets;

import android.view.View;

import java.util.WeakHashMap;

public class ErrorClickChecker{

  private final static class Holder{
    private final static WeakHashMap<View,Long> reference=new WeakHashMap<>();
  }

  private ErrorClickChecker(){
    throw new AssertionError("This class can not be instantiated!");
  }

  public static boolean check(View view){
    if(Holder.reference.containsKey(view)){
      if(System.currentTimeMillis()-Holder.reference.get(view)<1000){
        return true;
      }else{
        Holder.reference.put(view,System.currentTimeMillis());
      }
    }else{
      Holder.reference.clear();
      Holder.reference.put(view,System.currentTimeMillis());
    }
    return false;
  }
}