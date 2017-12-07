package com.joker.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.PermissionChecker;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.joker.common.utils.BaseActivity;
import com.joker.common.utils.LogUtils;
import com.joker.permissions.PermissionsRequestActivity;
import com.joker.photoselector.PhotoSelectorActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity{

  public final static String TAG = MainActivity.class.getName();

  private ListView list;
  private ArrayList<String> strings;
  private ArrayAdapter<String> adapter;
  private ImageView image;
  public String[] PERMISSIONS=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    list=(ListView)findViewById(R.id.list);
    strings=new ArrayList<>();
    adapter=new ArrayAdapter<>(this,android.R.layout.activity_list_item,strings);
    list.setAdapter(adapter);
    image = (ImageView)findViewById(R.id.image);
  }

  public void pick(View view){
    ArrayList<String> imageList = new ArrayList<>();

    Cursor imageCursor =  getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID}, null, null, MediaStore.Images.Media._ID);
    if(imageCursor != null) {
      LogUtils.w(TAG, " cursor is null");
      while (imageCursor.moveToNext()) {
        imageList.add(imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
      }
      imageCursor.close();
      for(String s : imageList) {
        LogUtils.w(TAG, s);
      }
    }
    image.setImageURI(Uri.parse(imageList.get(imageList.size()-1)));
  }

  public void request(View view){
//    checkPermission(PERMISSIONS);
    Intent intent=new Intent(this,PermissionsRequestActivity.class);
    intent.putExtra("permissions",PERMISSIONS);
    startActivityForResult(intent, 110);
//    if(Build.VERSION.SDK_INT >= 23) {
//      permissions=new Permissions(this);
//      if(!permissions.hasPermission(Permissions.Storage.ListV16)){
//        permissions.requestPermissions(Permissions.Storage.ListV16,
//            Permissions.Storage.PERMISSION_REQUEST_CODE,false);
//        return;
//      }
//    }
//    startIntent();
//    getGrantedPermissions();
//    getPermission();
//    if(Build.VERSION.SDK_INT >=23) {
//      permissions=new Permissions(this, 101);
//      String[] permissions={Manifest.permission.CAMERA,
//          Manifest.permission.WRITE_EXTERNAL_STORAGE,
//          Manifest.permission.ACCESS_FINE_LOCATION
//      };
//      boolean b=this.permissions.hasPermission(permissions);
//      if(b) {
//        LogUtils.w(TAG,"All permissions have granted");
//      } else {
//        this.permissions.requestPermissions(permissions,101, true);
//      }
//    }
//
//    checkPermission(Manifest.permission.CAMERA);
//    checkPermission(Manifest.permission.ACCESS_FINE_LOCATION);
//    checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//    checkPermission(Manifest.permission.RECORD_AUDIO);
//
//    customCheckUriPermission(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//    customCheckUriPermission(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
//    customCheckUriPermission(MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
  }

  private void startIntent(){
    Intent intent=new Intent(this,PhotoSelectorActivity.class);
    startActivity(intent);
  }

  private void checkPermission(String permission){
    int camera_result=PermissionChecker.checkSelfPermission(this,permission);
    if(camera_result==PackageManager.PERMISSION_GRANTED){
        LogUtils.w(TAG,permission+" have granted");
    } else if(camera_result == PermissionChecker.PERMISSION_DENIED) {
      LogUtils.w(TAG,permission+" have denied");
    } else {
      LogUtils.w(TAG,permission+" don't be allowed");
    }
  }

  private void customCheckUriPermission(Uri uri) {
    int result=checkCallingOrSelfUriPermission(uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
    if(result == PackageManager.PERMISSION_GRANTED) {
      LogUtils.w(TAG,uri.toString()+" have granted");
    } else {
      LogUtils.w(TAG,uri.toString()+" have denied");
      android.support.v4.app.LoaderManager manager=getSupportLoaderManager();

    }
  }

  @RequiresApi(23)
  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String[] permissions,@NonNull int[] grantResults){
    super.onRequestPermissionsResult(requestCode,permissions,grantResults);
//    this.permissions.onRequestPermissionsResult(requestCode,permissions,grantResults,new Object[]{this});
  }

  @Override
  protected void onActivityResult(int requestCode,int resultCode,Intent data){
    super.onActivityResult(requestCode,resultCode,data);
    boolean result = true;
    for(String s : PERMISSIONS) {
      if(checkSelfPermission(s) != PackageManager.PERMISSION_GRANTED){
        result= false;
        break;
      }
    }
  }

}
