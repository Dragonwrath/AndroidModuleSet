package com.joker.main;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobWorkItem;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;

import com.joker.permissions.BasePermissionActivity;

import static android.app.job.JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS;

@RequiresApi(24)
public class MainActivity extends BasePermissionActivity{

 @Override
 protected void onCreate(Bundle savedInstanceState){
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  allPermissionGranted();
 }

 @Override
 protected void onActivityResult(int requestCode,int resultCode,Intent data){
  super.onActivityResult(requestCode,resultCode,data);
 }

 @Override
 public void allPermissionGranted(){
  System.out.println("MainActivity.allPermissionGranted-----------"+Process.myUid()+"--------"+Process.myPid());
  System.out.println(Thread.currentThread());

  JobInfo.Builder builder=new JobInfo.Builder(1,new ComponentName(this,DiscoverService.class));
  JobInfo jobInfo=builder
    .addTriggerContentUri(new JobInfo.TriggerContentUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,FLAG_NOTIFY_FOR_DESCENDANTS))
    .build();
  Object object=getSystemService(Context.JOB_SCHEDULER_SERVICE);
  if(object!=null&&object instanceof JobScheduler){
   JobScheduler scheduler=(JobScheduler)object;
   scheduler.schedule(jobInfo);
   if(Build.VERSION.SDK_INT >= 26){
    scheduler.enqueue(jobInfo,new JobWorkItem(new Intent()));
   }
  }
 }

 @Override
 public void somePermissionsDenied(){

 }
}
