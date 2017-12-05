package com.joker.photoselector.helper;

import android.database.Cursor;
import android.support.annotation.Nullable;

public interface OnLoadFinishedListener{
  /**
   * We can use this method, in two situation below:
   * 1、reset all data
   * 2、init data
   * @param cursor the result cursor
   */
  void onLoad(@Nullable Cursor cursor);

  /**
   * We load more data
   * @param cursor more data
   */
  void onLoadMore(@Nullable Cursor cursor);

  /**
   * we suggest that you can duel which method should be used
   * by call this method
   * @return should use load or load more method
   */
  boolean getState();


  void setState(boolean shouldLoadMore);
}
