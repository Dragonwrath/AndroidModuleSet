package com.joker.photoselector.loader;


import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

public class PhotoLoader extends CursorLoader{

  public PhotoLoader(Context context,Uri uri,String[] projection,String selection,String[] selectionArgs,String sortOrder){
    super(context,uri,projection,selection,selectionArgs,sortOrder);
  }

  @Override
  public Cursor loadInBackground(){
    return super.loadInBackground();

  }

  @Override
  public void deliverResult(Cursor cursor){
      super.deliverResult(cursor);
  }
}
