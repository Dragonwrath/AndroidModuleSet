package com.joker.main;

import android.Manifest;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.joker.common.utils.dialog.TipsDialog;
import com.joker.permissions.BasePermissionActivity;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends BasePermissionActivity{

  public final static String TAG=MainActivity.class.getName();
  private ListView list;
  private ArrayList<String> strings;
  private ArrayAdapter<String> adapter;
  private ImageView image;
  public String[] PERMISSIONS=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
  private TextureView mTextureView;
  private EditText mEditText;
  private RelativeLayout mRelativeLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
//    needRequestPermission(1234,new String[]{Manifest.permission.CAMERA});
//    initView();
  }

  private void initView(){
    list=(ListView)findViewById(R.id.list);
    strings=new ArrayList<>();
    adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,strings);
    list.setAdapter(adapter);
    image=(ImageView)findViewById(R.id.image);
  }

  private void initCameraPreview(){
    mRelativeLayout=(RelativeLayout)findViewById(R.id.main);
    list=(ListView)findViewById(R.id.list);
    strings=new ArrayList<>();
    adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,strings);
    list.setAdapter(adapter);
  }

  private void initCameraV2(){
    mTextureView=(TextureView)findViewById(R.id.preview);
  }

  @Override
  protected void onActivityResult(int requestCode,int resultCode,Intent data){
    super.onActivityResult(requestCode,resultCode,data);
    if(resultCode==RESULT_OK){
    }
  }

  private void initCameraV1(){
    mTextureView=(TextureView)findViewById(R.id.preview);
    if(!checkHardwareAccelerated(mTextureView)){
      Log.e(TAG,"HardwareAccelerated is disable");
    }
    mEditText=(EditText)findViewById(R.id.et_input);
    mEditText.setOnTouchListener(new View.OnTouchListener(){
      @Override
      public boolean onTouch(View v,MotionEvent event){
        int action=event.getAction();
        switch(action){
          case MotionEvent.ACTION_DOWN:
            float x=event.getX();
            float parentX=v.getMeasuredWidth();
            if(x>0.7*parentX&&x<parentX){
              Editable text=mEditText.getText();
              strings.add(text.toString());
              adapter.notifyDataSetChanged();
              mEditText.setText("");
            }
            return true;
        }
        return false;
      }
    });
    mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener(){
      private Camera mCamera;

      @Override
      public void onSurfaceTextureAvailable(SurfaceTexture surface,int width,int height){
        mCamera=Camera.open();
        try{
          mCamera.setPreviewTexture(surface);
          mCamera.startPreview();
        }catch(IOException e){
          e.printStackTrace();
        }
      }

      @Override
      public void onSurfaceTextureSizeChanged(SurfaceTexture surface,int width,int height){
      }

      @Override
      public boolean onSurfaceTextureDestroyed(SurfaceTexture surface){
        if(mCamera!=null){
          surface.release();
          mCamera.stopPreview();
          mCamera.release();
          return true;
        }
        return false;
      }

      @Override
      public void onSurfaceTextureUpdated(SurfaceTexture surface){
        surface.release();
      }
    });
  }


  public boolean checkHardwareAccelerated(View source){
    View view=source;
    while(view.isHardwareAccelerated()){
      view=(View)view.getParent();
      if(view==null){
        return true;
      }
    }
    return false;
  }

  public void pick(View view){
//    ArrayList<String> imageList=new ArrayList<>();
//    Cursor imageCursor=getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,new String[]{MediaStore.Images.Media.DATA,MediaStore.Images.Media._ID},null,null,MediaStore.Images.Media._ID);
//    if(imageCursor!=null){
//      LogUtils.w(TAG," cursor is null");
//      while(imageCursor.moveToNext()){
//        imageList.add(imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
//      }
//      imageCursor.close();
//      for(String s : imageList) {
//        LogUtils.w(TAG,s);
//      }
//    }
//    image.setImageURI(Uri.parse(imageList.get(imageList.size()-1)));

    new TipsDialog.Builder(this)
        .setMessage(R.string.app_name)
        .setTitle(R.string.title)
        .setTitleDrawable(R.drawable.number_bg)
        .setPositiveBtnTxt(R.string.confirm).show();

  }

  public void request(View view){
    new TipsDialog.Builder(this)
        .setMessage(R.string.app_name)
        .setTitle(R.string.title)
        .setNegativeBtnTxt(R.string.cancel)
        .setPositiveBtnTxt(R.string.confirm).show();

  }

  @Override
  public void allPermissionGranted(){
    Toast.makeText(this,"All permissions has granted",Toast.LENGTH_SHORT).show();
  }

  @Override
  public void somePermissionsDenied(){
    Toast.makeText(this,"All permissions has denied",Toast.LENGTH_SHORT).show();
  }
}
