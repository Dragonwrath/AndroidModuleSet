package com.joker.permissions;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.joker.common.utils.ResourcesUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.LinkedList;
import java.util.Locale;

/**
 * There are four step to grant permissions
 * 0-query permissions whether are granted.
 * if true , call PermissionResultCallback.allPermissionGranted
 * false means that we should go to next step request permissions and wait callback
 * 1-callback of Activity.requestPermissions.
 * if all permissions are granted, PermissionResultCallback.allPermissionGranted will be called
 * On anther way, we will go to next step
 * 2-we should show a dialog to hint this principle of permission to user
 * if cancel button is click ,  PermissionResultCallback.somePermissionsDenied will be called
 * if setting button is click ,start app setting page,and wait result
 * 3-check all permission，some permission deined to step 0
 */
@RequiresApi(23)
public class PermissionsRequestActivity extends AppCompatActivity
    implements PermissionResultCallback,View.OnClickListener{
  private final static int PERMISSION_REQUEST_CODE=101;
  private final static int SETTING_REQUEST_CODE=102;
  private int phase;
  private Dialog dialog;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    if(savedInstanceState == null){
      setContentView(R.layout.activity_premissions_request);
      checkPermission();
    }
  }

  private void handleSavedData(@Nullable Bundle savedInstanceState){
    if(savedInstanceState == null) return;
    String[] permissions=savedInstanceState.getStringArray("permissions");
    int phase=savedInstanceState.getInt("phase",0);
    if(permissions != null){
      switch(phase){
        case 0:
        case 1:
          checkPermission(permissions);
          break;
        case 2:
        case 3:
          handleRequestResult(permissions);
          break;
      }
    }
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState){
    super.onRestoreInstanceState(savedInstanceState);
    handleSavedData(savedInstanceState);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState){
    super.onSaveInstanceState(outState);
    outState.putStringArray("permissions",getIntent().getStringArrayExtra("permissions"));
    outState.putInt("phase",phase);
  }

  private void checkPermission(){
    Intent intent=getIntent();
    String[] permissions=intent.getStringArrayExtra("permissions");
    checkPermission(permissions);
  }

  @Phase(0)
  private void checkPermission(String[] permissions){
    phase=0;
    LinkedList<String> stack=new LinkedList<>();
    for(String permission : permissions) {
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

  @Phase(1)
  @Override
  public void onRequestPermissionsResult(int requestCode,
                                         @NonNull String[] permissions,@NonNull int[] grantResults){
    super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    phase=1;
    if(permissions==null||permissions.length==0){ //permission cancelled
      somePermissionsDenied();
      return;
    }
    handleRequestResult(permissions);
  }

  private void handleRequestResult(@NonNull String[] permissions){
    for(String permission : permissions) {
      if(checkSelfPermission(permission)!=PackageManager.PERMISSION_GRANTED){
        showDialog(permission);
        return;
      }
    }
    allPermissionGranted();
  }

  @Phase(2)
  private void showDialog(String permission){
    phase=2;
    final Dialog dialog=new Dialog(this,R.style.BaseDialog);
    ViewGroup parent=(ViewGroup)getWindow().getDecorView();
    if(parent!=null){
      View view=getLayoutInflater().inflate(R.layout.dialog_alert,parent,false);
      TextView title=(TextView)view.findViewById(R.id.tv_dialog_title);
      title.setText(R.string.permission_dialog_title);
      TextView message=(TextView)view.findViewById(R.id.tv_dialog_content);
      message.setText(getMessage(permission));
      Button pos=(Button)view.findViewById(R.id.btn_dialog_pos);
      pos.setText(R.string.settings);
      pos.setOnClickListener(this);
      Button neg=(Button)view.findViewById(R.id.btn_dialog_neg);
      neg.setVisibility(View.VISIBLE);
      neg.setOnClickListener(this);
      dialog.setContentView(view);
      dialog.setCanceledOnTouchOutside(false);
      dialog.setCancelable(false);
      dialog.onBackPressed();
      setDialog(dialog);
      dialog.show();
    }
  }

  @Phase(3)
  @Override
  protected void onActivityResult(int requestCode,int resultCode,Intent data){
    super.onActivityResult(requestCode,resultCode,data);
    if(requestCode==SETTING_REQUEST_CODE){
      phase= 3;
      checkPermission();
    }
  }

  @Override
  public void allPermissionGranted(){
    Intent intent=new Intent();
    intent.putExtra("permissions",true);
    setResult(RESULT_OK,intent);
    this.finish();
  }

  @Override
  public void somePermissionsDenied(){
    Intent intent=new Intent();
    intent.putExtra("permissions",false);
    setResult(RESULT_OK,intent);
    this.finish();
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

  @NonNull
  private String getMessage(String permission){
    String builder=ResourcesUtils.getString(this,R.string.permission_dialog_build);
    String head=DangerousPermissionPrinciple.translateHead(this,permission);
    String appName=ResourcesUtils.getString(this,R.string.app_name);
    String tail=DangerousPermissionPrinciple.translateTail(this,permission);
    return String.format(Locale.getDefault(),builder,head,appName,tail);
  }

  public void setDialog(Dialog dialog){
    this.dialog = dialog;
  }

  @Override
  public void onClick(View v){
    if(dialog != null&& dialog.isShowing()){
      dialog.dismiss();
    }
    int vId=v.getId();
    if(vId == R.id.btn_dialog_neg) {
      somePermissionsDenied();
    } else if(vId ==R.id.btn_dialog_pos) {
      openSettingForPermission();
    }
  }

  @Retention(RetentionPolicy.SOURCE)
  @Target(ElementType.METHOD)
  private @interface Phase{
    int value();
  }
}
