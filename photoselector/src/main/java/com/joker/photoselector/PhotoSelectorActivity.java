package com.joker.photoselector;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joker.permissions.PermissionResultCallback;
import com.joker.permissions.RuntimePermissionsChecker;
import com.joker.photoselector.adapter.SelectorCursorAdapter;
import com.joker.photoselector.helper.LoaderManagerHelper;
import com.joker.photoselector.listener.PhotoCaptureListener;

public class PhotoSelectorActivity extends AppCompatActivity implements PhotoCaptureListener,PermissionResultCallback{

  private SelectorCursorAdapter cursorAdapter;
  private final String[] THUMBNAILS_PROJECTIONS=new String[]{MediaStore.Images.Thumbnails.DATA,MediaStore.Images.Thumbnails.IMAGE_ID};
  private final String[] IMAGE_PROJECTIONS=new String[]{MediaStore.Images.Media.DATA,MediaStore.Images.Media.DATE_MODIFIED,MediaStore.Images.Media.SIZE};
  private String[] projections=IMAGE_PROJECTIONS;
  private LoaderManagerHelper helper;
  private RecyclerView recycler;
  private int size=20;
  private int offset=0;

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photo_selector);
    helper=new LoaderManagerHelper(this,getSupportLoaderManager());
    recyclerViewAdapter();
    helper.initLoader(LoaderManagerHelper.SELECTOR_LOADER_ID,generateImageBundle(),cursorAdapter);
  }


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

  private void recyclerViewAdapter(){
    recycler=(RecyclerView)findViewById(R.id.container);
    GridLayoutManager manager=new GridLayoutManager(this,3);
    recycler.setLayoutManager(manager);
    cursorAdapter=new SelectorCursorAdapter(this, 8);
    cursorAdapter.setProjections(projections);
    cursorAdapter.setCaptureListener(this);
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
          recycler.smoothScrollBy(0,velocityY>>1);
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

  @Override
  protected void onDestroy(){
    super.onDestroy();
    Glide.clear(recycler);
  }

  @Override
  public void checkPhotoCapturePermission(){
    RuntimePermissionsChecker.hasPermissions(this,
        new String[]{Manifest.permission.CAMERA},1232, this);
  }

  @Override
  protected void onActivityResult(int requestCode,int resultCode,Intent data){
    if(requestCode == 1232){
      RuntimePermissionsChecker.onActivityResult(this, data, new String[]{Manifest.permission.CAMERA},this);
    }
    super.onActivityResult(requestCode,resultCode,data);
  }

  @Override
  public void allPermissionGranted(){
    //todo take photo
    Toast.makeText(this,"Photo take", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void somePermissionsDenied(){
    //todo wait;
    Toast.makeText(this,"Photo Denied", Toast.LENGTH_SHORT).show();
  }
}
