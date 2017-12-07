package com.joker.permissions;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.joker.common.utils.ResourcesUtils;

import java.util.LinkedList;
import java.util.Locale;


public class PermissionsRequestActivity extends AppCompatActivity implements PermissionResultCallback{
  private final static int PERMISSION_REQUEST_CODE=101;
  private final static int SETTING_REQUEST_CODE=102;
  private LinkedList<String> stack;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_premissions_request);
    checkPermission();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState){
    super.onSaveInstanceState(outState);

  }

  boolean isMarshmallow(){
    return Build.VERSION.SDK_INT<23;
  }

  public void checkPermission(){
    if(isMarshmallow()) return;
    Intent intent=getIntent();
    String[] permissions=intent.getStringArrayExtra("permissions");
    stack=new LinkedList<>();
    for(String permission : permissions) {
      //todo 外部判断 还是内部判断
      if(checkSelfPermission(permission)!=PackageManager.PERMISSION_GRANTED){
        stack.push(permission);
      }
    }
    if(stack.size()>0){
      requestPermissions(stack.toArray(new String[0]),PERMISSION_REQUEST_CODE);
    }else{
      allPermissionGranted();
    }
  }

  private void openSettingForPermission(){
    Intent intent=new Intent();
    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    intent.addCategory(Intent.CATEGORY_DEFAULT);
    intent.setData(Uri.fromParts("package",getPackageName(),null));
    ComponentName name=intent.resolveActivity(getPackageManager());
    if(name!=null){
      startActivityForResult(intent,SETTING_REQUEST_CODE);
    }
  }

  @Override
  public void overridePendingTransition(int enterAnim,int exitAnim){
    super.overridePendingTransition(0,0);
  }

  @Override
  protected void onActivityResult(int requestCode,int resultCode,Intent data){
    super.onActivityResult(requestCode,resultCode,data);
    if(requestCode==SETTING_REQUEST_CODE){
      //todo activity结果要单独提出来 可能授权之后按返回键取消
      checkPermission();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String[] permissions,@NonNull int[] grantResults){
    super.onRequestPermissionsResult(requestCode,permissions,grantResults);

    if(isMarshmallow()||permissions==null||permissions.length==0){
      return;
    }
    for(String permission : stack) {
      if(checkSelfPermission(permission)!=PackageManager.PERMISSION_GRANTED){
        showDialog(permission);
        return;
      }
    }
    allPermissionGranted();
  }



  @Override
  public void allPermissionGranted(){
    setResult(RESULT_OK,getIntent());
    this.finish();
  }

  @Override
  public void somePermissionsDenied(){
    setResult(RESULT_CANCELED,getIntent());
    this.finish();
  }

  private void showDialog(String permission){
    final Dialog dialog=new Dialog(this,R.style.BaseDialog);
    ViewGroup parent=(ViewGroup)getWindow().getDecorView();
    if(parent != null){
      View view=getLayoutInflater().inflate(R.layout.dialog_alert,parent,false);
      TextView title=(TextView)view.findViewById(R.id.tv_dialog_title);
      title.setText(R.string.permission_dialog_title);
      TextView message=(TextView)view.findViewById(R.id.tv_dialog_content);
      message.setText(getMessage(permission));
      Button pos=(Button)view.findViewById(R.id.btn_dialog_pos);
      pos.setText(R.string.settings);
      pos.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
          dialog.dismiss();
          openSettingForPermission();
        }
      });

      Button neg=(Button)view.findViewById(R.id.btn_dialog_neg);
      neg.setVisibility(View.VISIBLE);
      neg.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
          dialog.dismiss();
          somePermissionsDenied();
        }
      });
      dialog.setContentView(view);
      dialog.show();
    }
  }

  @NonNull
  private String getMessage(String permission){
    String builder=ResourcesUtils.getString(this, R.string.permission_dialog_build);
    String head=DangerousPermissionPrinciple.translateHead(this,permission);
    String appName=ResourcesUtils.getString(this,R.string.app_name);
    String tail=DangerousPermissionPrinciple.translateTail(this,permission);
    return String.format(Locale.getDefault(),builder, head, appName,tail);
  }

}
