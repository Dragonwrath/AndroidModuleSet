package com.joker.main.weibo;

import android.support.annotation.IntDef;

import com.sina.weibo.sdk.api.BaseMediaObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
@IntDef({BaseMediaObject.MEDIA_TYPE_TEXT,BaseMediaObject.MEDIA_TYPE_IMAGE,
  BaseMediaObject.MEDIA_TYPE_WEBPAGE,BaseMediaObject.MEDIA_TYPE_MULITI_IMAGE,
  BaseMediaObject.MEDIA_TYPE_SOURCE_VIDEO})
@Retention(RetentionPolicy.SOURCE)
@interface WeiBoSupportType{
}
