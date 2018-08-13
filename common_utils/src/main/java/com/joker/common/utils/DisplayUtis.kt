package com.joker.common.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration

/**
 * 这个应该用在BaseActivity之中，用来辅助修改Activity的density
 * 具体参考{@link https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA 头条文章}
 *
 */
class DisplayUtis {

 companion object{
  private var sNonCompatDensity:Float=0.toFloat()
  private var sNonCompatScaledDensity:Float=0.toFloat()

  private fun setCustomDesity(activity:Activity,application:Application) {
   val appDisplayMetrics=application.resources.displayMetrics
   if(sNonCompatDensity==0f) {
    sNonCompatDensity=appDisplayMetrics.density
    sNonCompatScaledDensity=appDisplayMetrics.scaledDensity
    application.registerComponentCallbacks(object:ComponentCallbacks {
     override fun onConfigurationChanged(newConfig:Configuration?) {
      if(newConfig!=null&&newConfig.fontScale>0) {
       sNonCompatScaledDensity=application.resources.displayMetrics.scaledDensity
      }
     }

     override fun onLowMemory() {}
    })
   }

   val targetDensity=appDisplayMetrics.widthPixels/360f
   val targetScaledDensity=targetDensity*(sNonCompatScaledDensity/sNonCompatDensity)
   val targetDensityDpi=(160*targetDensity).toInt()
   appDisplayMetrics.density=targetDensity
   appDisplayMetrics.scaledDensity=targetScaledDensity
   appDisplayMetrics.densityDpi=targetDensityDpi

   val activityDisplayMetrics=activity.resources.displayMetrics
   activityDisplayMetrics.density=targetDensity
   activityDisplayMetrics.scaledDensity=targetScaledDensity
   activityDisplayMetrics.densityDpi=targetDensityDpi
  }
 }
}