package com.joker.camera;

import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

@RequiresApi(21)
public class Camera2Activity extends AppCompatActivity{

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_camera2);
  }
}
