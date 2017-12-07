package com.joker.permissions;

import android.Manifest;
import android.content.Context;

import com.joker.common.utils.ResourcesUtils;



final class DangerousPermissionPrinciple{

  static String translateHead(Context context,String permission){
    String[] principles=ResourcesUtils.getStringArray(context,R.array.dangerous_permission_head);
    return translateInternal(permission,principles);
  }

  static String translateTail(Context context,String permission){
    String[] principles=ResourcesUtils.getStringArray(context,R.array.dangerous_permission_principle);
    return translateInternal(permission,principles);
  }

  private static String translateInternal(String permission,String[] principles){
    switch(permission){
      case Manifest.permission.READ_CALENDAR:
      case Manifest.permission.WRITE_CALENDAR:
        return principles[0];
      case Manifest.permission.CAMERA:
        return principles[1];
      case Manifest.permission.READ_CONTACTS:
      case Manifest.permission.WRITE_CONTACTS:
      case Manifest.permission.GET_ACCOUNTS:
        return principles[2];
      case Manifest.permission.ACCESS_FINE_LOCATION:
      case Manifest.permission.ACCESS_COARSE_LOCATION:
        return principles[3];
      case Manifest.permission.RECORD_AUDIO:
        return principles[4];
      case Manifest.permission.READ_PHONE_STATE:
      case Manifest.permission.CALL_PHONE:
      case Manifest.permission.READ_CALL_LOG:
      case Manifest.permission.WRITE_CALL_LOG:
      case Manifest.permission.ADD_VOICEMAIL:
      case Manifest.permission.USE_SIP:
      case Manifest.permission.PROCESS_OUTGOING_CALLS:
        return principles[5];
      case Manifest.permission.BODY_SENSORS:
        return principles[6];
      case Manifest.permission.SEND_SMS:
      case Manifest.permission.RECEIVE_SMS:
      case Manifest.permission.READ_SMS:
      case Manifest.permission.RECEIVE_WAP_PUSH:
      case Manifest.permission.RECEIVE_MMS:
        return principles[7];
      case Manifest.permission.READ_EXTERNAL_STORAGE:
      case Manifest.permission.WRITE_EXTERNAL_STORAGE:
        return principles[8];
      default:
        return "";
    }
  }
}
