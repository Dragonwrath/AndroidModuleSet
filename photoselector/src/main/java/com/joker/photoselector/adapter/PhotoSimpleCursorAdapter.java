package com.joker.photoselector.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;


public class PhotoSimpleCursorAdapter extends SimpleCursorAdapter{

  public PhotoSimpleCursorAdapter(Context context,int layout,Cursor c,String[] from,int[] to,int flags){
    super(context,layout,c,from,to,flags);
  }

  @Override
  public View newView(Context context,Cursor cursor,ViewGroup parent){
    return super.newView(context,cursor,parent);
  }

  @Override
  public void bindView(View view,Context context,Cursor cursor){
    super.bindView(view,context,cursor);
  }
}
