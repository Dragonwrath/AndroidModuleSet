package com.joker.main;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.joker.common.utils.LogUtils;
import com.joker.permissions.BasePermissionActivity;

import java.util.ArrayList;

public class MainActivity extends BasePermissionActivity {

  public final static String TAG=MainActivity.class.getName();
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
    image=(ImageView)findViewById(R.id.image);
  }

  public void pick(View view){
    ArrayList<String> imageList=new ArrayList<>();

    Cursor imageCursor=getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,new String[]{MediaStore.Images.Media.DATA,MediaStore.Images.Media._ID},null,null,MediaStore.Images.Media._ID);
    if(imageCursor!=null){
      LogUtils.w(TAG," cursor is null");
      while(imageCursor.moveToNext()){
        imageList.add(imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
      }
      imageCursor.close();
      for(String s : imageList) {
        LogUtils.w(TAG,s);
      }
    }
    image.setImageURI(Uri.parse(imageList.get(imageList.size()-1)));
  }

  public void request(View view){
    needRequestPermission(12345,PERMISSIONS);
  }

  @Override
  protected void onActivityResult(int requestCode,int resultCode,Intent data){
    super.onActivityResult(requestCode,resultCode,data);
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
