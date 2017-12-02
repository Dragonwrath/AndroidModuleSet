package com.joker.common.utils.easypermissions;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.joker.common.utils.R;
import com.joker.common.utils.ResourcesUtils;
import com.joker.common.utils.dialog.AlertDialogFragment;

@RequiresApi(23)
class FragmentPermissionHelperImplV23 extends BasePermissionHelper<Fragment>{

  FragmentPermissionHelperImplV23(Fragment host){
    super(host);
  }

  @Override
  void failureMessage(){
    Context context=getHost().getContext();
    Toast.makeText(context,ResourcesUtils.getString(context,R.string.permission_failure),Toast.LENGTH_SHORT).show();
  }

  @Override
  void openSettingForPermission(int requestCode){
    Intent intent=new Intent(Settings.ACTION_APPLICATION_SETTINGS);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addCategory(Intent.CATEGORY_DEFAULT);
    intent.setData(Uri.fromParts("package",getHost().getContext().getPackageName(),null));
    ComponentName name=intent.resolveActivity(getHost().getContext().getPackageManager());
    if(name!=null){
      getHost().startActivityForResult(intent,requestCode);
    }
  }

  @Override
  boolean hasPermission(@NonNull String[] permissions){
    return super.hasPermission(getHost().getContext(),permissions);
  }

  @Override
  void requestPermissions(String[] permissions,int requestCode,boolean showRational,
                          DialogInterface.OnClickListener posListener,
                          DialogInterface.OnClickListener negListener){
    if(canAccessRequestPermission(getHost().getActivity(),permissions)){
      getHost().requestPermissions(permissions,requestCode);
    }else{
      if(showRational){
        AlertDialogFragment dialog=showRationalDialog(getHost().getContext(),permissions,posListener,negListener);
        dialog.show(getHost().getChildFragmentManager(),"permission");
      }else{
        failureMessage();
      }
    }
  }
}
