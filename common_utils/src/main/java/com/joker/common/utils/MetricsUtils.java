package com.joker.common.utils;

import android.content.Context;
import android.content.res.Resources;

public final class MetricsUtils{
  private MetricsUtils(){
    throw new AssertionError("This class can't be instantiated");
  }

  public static int dp2px(Context context,int dp){
    Resources resources=ResourcesUtils.getResources(context);
    return (int)(dp*resources.getDisplayMetrics().density+0.5f);
  }

  public static int px2dp(Context context,int px){
    Resources resources=ResourcesUtils.getResources(context);
    return (int)(px*1.0f/resources.getDisplayMetrics().density+0.5f);
  }

}
