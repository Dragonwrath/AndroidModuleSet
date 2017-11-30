package com.joker.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

public final class ResourcesUtils{
  private ResourcesUtils(){
    throw new AssertionError("This class can't be instantiated");
  }

  public static Resources getResources(Context context){
    Resources resources=context.getResources();
    if(resources==null){
      resources=Resources.getSystem();
    }
    return resources;
  }

  public static String getString(Context context,@StringRes int id){
    return getResources(context).getString(id);
  }

  public static int getColor(Context context,@ColorRes int color) {
    return getResources(context).getColor(color);
  }

}
