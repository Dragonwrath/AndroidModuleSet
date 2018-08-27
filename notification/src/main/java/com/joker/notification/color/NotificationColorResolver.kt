package com.joker.notification.color

import android.app.Notification
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.IdRes
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.widget.TextView

object NotificationColorResolver {

  //todo should define default color

  @ColorInt
  fun getNotificationTitleColor(context : Context,theme : Resources.Theme?) : Int {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) { //24以上的版本支持
      getNotificationDefaultColor(context,android.R.id.title)
    } else {
      getNotificationDefaultTextColorV24(context)
    }
  }

  fun getNotificationContentColor(context : Context,theme : Resources.Theme?):Int {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) { //24以上的版本支持
      getNotificationDefaultColor(context,android.R.id.text1)
    } else {
      getNotificationDefaultContentColorV24(context)
    }
  }


  /**
   * 默认情况下，我们可以创建一个Notification，这样，我们可以通过contentView获取对应的内容的颜色
   *
   */
  private fun getNotificationDefaultColor(context : Context,@IdRes targetId : Int) : Int {
    //just create to capture color
    val notification = Notification.Builder(context)
        .setSmallIcon(android.R.drawable.ic_lock_idle_alarm).setContentTitle("hahah").setContentText("this is message").build()
    val textColor : Int = Color.WHITE
    try {
      val id = notification.contentView!!.layoutId
      val view = LayoutInflater.from(context).inflate(id,null,false)
      val color = view.findViewById<TextView>(targetId)?.currentTextColor
      if (color != null) {
        return if (isSimilarColor(Color.WHITE,color)) {
          //todo
          color
        } else {
          //todo return another color
          color
        }
      }
    } catch (e : Exception) {
      e.printStackTrace()
    } finally {
      //to release resource
    }
    return textColor
  }

  private fun isSimilarColor(baseColor : Int,color : Int) : Boolean {
    val simpleBaseColor = baseColor or -0x1000000
    val simpleColor = color or -0x1000000
    val baseRed = Color.red(simpleBaseColor) - Color.red(simpleColor)
    val baseGreen = Color.green(simpleBaseColor) - Color.green(simpleColor)
    val baseBlue = Color.blue(simpleBaseColor) - Color.blue(simpleColor)
    val value = Math.sqrt((baseRed * baseRed + baseGreen * baseGreen + baseBlue * baseBlue).toDouble())
    return value < 180
  }

  @RequiresApi(Build.VERSION_CODES.N)
  fun getNotificationDefaultTextColorV24(context : Context) : Int {
    val textView = TextView(context)
    textView.setTextColor(Color.RED)
    textView.setTextAppearance(android.R.style.TextAppearance_Material_Notification_Title)
    if (textView.currentTextColor ==Color.RED) {
      textView.setTextAppearance(android.R.style.TextAppearance_StatusBar_EventContent_Title)
    }
    return if (textView.currentTextColor==Color.RED){
      Color.BLACK
    } else{
      textView.currentTextColor
    }
  }

  @RequiresApi(Build.VERSION_CODES.N)
  fun getNotificationDefaultContentColorV24(context : Context) : Int {
    val textView = TextView(context)
    textView.setTextColor(Color.RED)
    try {
      textView.setTextAppearance(android.R.style.TextAppearance_Material_Notification_Info)
    } catch (e:Exception) {
      try {
        if (textView.currentTextColor ==Color.RED) {
          textView.setTextAppearance(android.R.style.TextAppearance_StatusBar_EventContent)
        }
      } catch (e:Exception){
        //ignore
      }
    }
    return if (textView.currentTextColor==Color.RED){
      Color.GRAY
    } else{
      textView.currentTextColor
    }
  }
}
