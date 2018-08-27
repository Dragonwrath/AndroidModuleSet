package com.joker.notification.color;
import android.content.Context;
import android.support.annotation.ColorInt;
interface ColorResolver{
 @ColorInt int getTitleColor(Context context);
 @ColorInt int getContentColor(Context context);

}
