package com.joker.photoselector;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;

import com.bumptech.glide.Glide;
import com.joker.photoselector.adapter.CustomCursorAdapter;
import com.joker.photoselector.adapter.PhotoSimpleCursorAdapter;
import com.joker.photoselector.helper.LoaderManagerHelper;


/**
 * before this activity start, we should check STORAGE permission
 */
public class PhotoSelectorActivity extends AppCompatActivity {

  private Cursor cursor;
  private PhotoSimpleCursorAdapter photoAdapter;
  private CustomCursorAdapter cursorAdapter;
  private final String[] THUMBNAILS_PROJECTIONS=new String[]{MediaStore.Images.Thumbnails.DATA,MediaStore.Images.Thumbnails.IMAGE_ID};
  private final String[] IMAGE_PROJECTIONS=new String[]{MediaStore.Images.Media.DATA,MediaStore.Images.Media.DATE_MODIFIED,MediaStore.Images.Media.SIZE};
  private String[] projections=IMAGE_PROJECTIONS;
  public LoaderManagerHelper helper;
  public RecyclerView recycler;

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photo_selector);
    helper=new LoaderManagerHelper(this,getSupportLoaderManager());
    recyclerViewAdapter();
    helper.initLoader(LoaderManagerHelper.SELECTOR_LOADER_ID,generateImageBundle(),cursorAdapter);
  }

  public void init(){
    Intent intent=getIntent();
    Parcelable params=intent.getParcelableExtra("params");
    if(params!=null&&params instanceof PhotoSelectorParams){
      PhotoSelectorParams selectorParams=(PhotoSelectorParams)params;
      int maxLimit=selectorParams.getMaxLimit();
    }
  }

  private int size=20;
  private int offset=0;

  public void appendOffset(int offset){
    this.offset+=offset;
  }

  private Bundle generateImageBundle(){
    Bundle bundle=new Bundle();
    bundle.putString(LoaderManagerHelper.URI,MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
    bundle.putStringArray(LoaderManagerHelper.PROJECTIONS,IMAGE_PROJECTIONS);
    bundle.putString(LoaderManagerHelper.SORT_ORDER,IMAGE_PROJECTIONS[1]+" DESC "+"Limit "+size+" Offset "+offset);
    return bundle;
  }

  private Bundle generateThumbnailsBundle() {
    Bundle bundle=new Bundle();
    bundle.putString(LoaderManagerHelper.URI,MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI.toString());
    bundle.putStringArray(LoaderManagerHelper.PROJECTIONS,THUMBNAILS_PROJECTIONS);
    bundle.putString(LoaderManagerHelper.SORT_ORDER,THUMBNAILS_PROJECTIONS[1]+" DESC "+"Limit "+size+" Offset "+offset);
    return bundle;
  }

  private void recyclerViewAdapter(){
    recycler=(RecyclerView)findViewById(R.id.container);
    GridLayoutManager manager=new GridLayoutManager(this,3);
    recycler.setLayoutManager(manager);
    cursorAdapter=new CustomCursorAdapter(this, 8);
    cursorAdapter.setProjections(projections);
    recycler.setAdapter(cursorAdapter);
    recycler.setOnFlingListener(new RecyclerView.OnFlingListener(){
      @Override
      public boolean onFling(int velocityX,int velocityY){
        if(Math.abs(velocityY)>500){
          if(velocityY>0){
            appendOffset(size);
            size=100;
            helper.loaderMore(LoaderManagerHelper.SELECTOR_LOADER_ID,generateImageBundle());
          }
          recycler.smoothScrollBy(0,velocityY);
          return true;
        } else {
          if(velocityY>0){
            appendOffset(size);
            size=20;
            helper.loaderMore(LoaderManagerHelper.SELECTOR_LOADER_ID,generateImageBundle());
          }
          return false;
        }
      }
    });
    recycler.addOnScrollListener(new RecyclerView.OnScrollListener(){
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView,int newState){
        super.onScrollStateChanged(recyclerView,newState);
      }

      @Override
      public void onScrolled(RecyclerView recyclerView,int dx,int dy){
        super.onScrolled(recyclerView,dx,dy);
        if(dy >0){
          int tail=(recyclerView.computeVerticalScrollRange());
          int scrollExtent=recyclerView.computeVerticalScrollExtent();
          if(scrollExtent>=tail){ //the region is all on screen
            return;
          }
          if(!recyclerView.canScrollVertically(1)){
            appendOffset(size);
            helper.loaderMore(LoaderManagerHelper.SELECTOR_LOADER_ID,generateImageBundle());
          }
        }
      }
    });
  }

  private void simpleCursorAdapter(){
    GridView container=(GridView)findViewById(R.id.container);
    photoAdapter=new PhotoSimpleCursorAdapter(this,R.layout.item_grid,
        cursor,new String[]{projections[0]},new int[]{R.id.iv_photo},
        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    container.setAdapter(photoAdapter);
  }

  @Override
  protected void onDestroy(){
    super.onDestroy();
    Glide.clear(recycler);

  }

  @RequiresApi(23)
  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String[] permissions,@NonNull int[] grantResults){
    super.onRequestPermissionsResult(requestCode,permissions,grantResults);
  }

}
