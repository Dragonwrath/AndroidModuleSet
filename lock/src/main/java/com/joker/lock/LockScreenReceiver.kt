package com.joker.lock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class LockScreenReceiver : BroadcastReceiver() {

  override fun onReceive(context : Context,intent : Intent) {
    when(intent.action){
      Intent.ACTION_SCREEN_OFF->{
        val target = Intent(context,LockScreenActivity::class.java)
        target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(target)
      }
    }
  }
}
