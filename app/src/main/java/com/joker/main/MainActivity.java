package com.joker.main;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.joker.photoselector.PhotoSelectorActivity;

public class MainActivity extends AppCompatActivity{

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  public void pick(View view){
    Intent intent=new Intent(this,PhotoSelectorActivity.class);
    ActivityCompat.startActivity(this, intent, null);

  }
}
