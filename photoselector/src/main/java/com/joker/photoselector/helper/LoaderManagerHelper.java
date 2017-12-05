package com.joker.photoselector.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.SparseArray;

public class LoaderManagerHelper implements LoaderManager.LoaderCallbacks<Cursor>{
  public final static int SELECTOR_LOADER_ID=0x1;

  private final LoaderManager mManager;
  private final Context mContext;
  public final static String URI="uri";
  public final static String PROJECTIONS="projections";
  public final static String SELECTION="selection";
  public final static String SELECTION_ARGS="selectionArgs";
  public final static String SORT_ORDER="sortOrder";
  private SparseArray<OnLoadFinishedListener> map;

  public LoaderManagerHelper(Context context,LoaderManager manager){
    mContext=context;
    mManager=manager;
  }


  public void initLoader(int code,Bundle bundle,OnLoadFinishedListener listener){
    mManager.initLoader(code,bundle,this);
    if(map==null) map=new SparseArray<>();
    map.put(code,listener);
  }

  public void loaderMore(int code ,Bundle bundle) {
    mManager.restartLoader(code,bundle,this);
    OnLoadFinishedListener listener=map.get(code);
    if(listener != null) listener.setState(true);
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id,Bundle bundle){
    switch(id){
      case SELECTOR_LOADER_ID:
        return generatePhotoSelectorLoader(bundle);
    }
    return null;
  }

  private Loader<Cursor> generatePhotoSelectorLoader(Bundle bundle){
    CursorLoader loader=new CursorLoader(mContext);
    Uri uri=Uri.parse(bundle.getString(URI));
    loader.setUri(uri);
    String[] projections=bundle.getStringArray(PROJECTIONS);
    loader.setProjection(projections);
    String selection=bundle.getString(SELECTION);
    loader.setSelection(selection);
    String[] selectionArgs=bundle.getStringArray(SELECTION_ARGS);
    loader.setSelectionArgs(selectionArgs);
    String sortOrder=bundle.getString(SORT_ORDER);
    loader.setSortOrder(sortOrder);
    return loader;
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader,Cursor cursor){
    int id=loader.getId();
    OnLoadFinishedListener listener=map.get(id);
    if(listener.getState()){
      listener.onLoadMore(cursor);
    }else{
      listener.onLoad(cursor);
    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader){
    loader.forceLoad();
  }

}
