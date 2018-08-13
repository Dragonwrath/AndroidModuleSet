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
  }

  @Override
  protected void onActivityResult(int requestCode,int resultCode,Intent data){
    super.onActivityResult(requestCode,resultCode,data);
    if(resultCode==RESULT_OK){
    }
  }


  public void pick(View view){
  }

  public void request(View view){
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
