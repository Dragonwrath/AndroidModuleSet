package com.joker.photoselector;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;

import com.joker.common.utils.easypermissions.Permissions;
import com.joker.photoselector.adapter.CustomCursorAdapter;
import com.joker.photoselector.adapter.PhotoSimpleCursorAdapter;

import java.util.List;


/**
 * before this activity start, we should check STORAGE permission
 */
public class PhotoSelectorActivity extends AppCompatActivity implements Permissions.Callbacks, LoaderManager.LoaderCallbacks<Cursor>{

  private final static int SELECTOR_LOADER_ID=0x1;
  private final String[] IMAGE_PROJECTIONS=new String[]{MediaStore.Images.Media.DATA,MediaStore.Images.Media.DATE_MODIFIED};
  private Permissions permission;
  private Cursor cursor;
  private PhotoSimpleCursorAdapter photoAdapter;
  private CustomCursorAdapter cursorAdapter;
  private final String[] THUMBNAILS_PROJECTIONS=new String[]{MediaStore.Images.Thumbnails.DATA,MediaStore.Images.Thumbnails._ID};
  String[] projections=THUMBNAILS_PROJECTIONS;

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photo_selector);
    projections =IMAGE_PROJECTIONS;
    LoaderManager loaderManager=getSupportLoaderManager();
    loaderManager.initLoader(SELECTOR_LOADER_ID,null,this);
    recyclerViewAdapter();
    Log.w(getApplicationInfo().getClass().getSimpleName(),"working on "+Thread.currentThread().getName());

  }

  private void recyclerViewAdapter(){
    RecyclerView recycler = (RecyclerView)findViewById(R.id.container);
    GridLayoutManager manager=new GridLayoutManager(this,3);
    recycler.setLayoutManager(manager);
    cursorAdapter=new CustomCursorAdapter(this);
    cursorAdapter.setProjections(projections);
    recycler.setAdapter(cursorAdapter);
  }
  private void simpleCursorAdapter(){
    GridView container=(GridView)findViewById(R.id.container);
    photoAdapter=new PhotoSimpleCursorAdapter(this,R.layout.item_grid,
    cursor,new String[]{projections[0]},new int[]{R.id.iv_photo},
    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    container.setAdapter(photoAdapter);
  }

  public void init(){
    Intent intent=getIntent();
    Parcelable params=intent.getParcelableExtra("params");
    if(params!=null&&params instanceof PhotoSelectorParams){
      PhotoSelectorParams selectorParams=(PhotoSelectorParams)params;
      int maxLimit=selectorParams.getMaxLimit();
    }
  }

  @RequiresApi(23)
  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String[] permissions,@NonNull int[] grantResults){
    permission.onRequestPermissionsResult(requestCode,permissions,grantResults,new Object[]{this});
    super.onRequestPermissionsResult(requestCode,permissions,grantResults);

  }

  @Override
  public void onPermissionsGranted(int requestCode,@NonNull List<String> perms){

  }

  @Override
  public void onPermissionsDenied(int requestCode,@NonNull List<String> perms){

  }

  private long time;
  @Override
  public Loader<Cursor> onCreateLoader(int id,Bundle bundle){
    time = System.currentTimeMillis();
    CursorLoader loader=new CursorLoader(this);
    loader.setUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    loader.setProjection(projections);
    loader.setSortOrder(projections[1]+" DESC" );
    return loader;
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader,Cursor cursor){
    int id=loader.getId();
    switch(id) {
      case SELECTOR_LOADER_ID:
//        photoAdapter.changeCursor(cursor);
        Log.w(getApplicationInfo().getClass().getSimpleName(),"working cost "+(System.currentTimeMillis() - time));
        cursorAdapter.notifyCursorChanged(cursor);
        break;
    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader){
  }

}
