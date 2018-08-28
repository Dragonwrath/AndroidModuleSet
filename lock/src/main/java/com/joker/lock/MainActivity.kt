package com.joker.lock

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

  private val mConnection : ServiceConnection = object : ServiceConnection {
    override fun onServiceConnected(name : ComponentName?,service : IBinder?) {
      service?.also {
        binder=it
        messenger = Messenger(binder)
        try {
          binder.linkToDeath(mDeathRecipient,0)
        } catch (e : Exception) {
          //nothing
        }
      }
    }

    override fun onServiceDisconnected(name : ComponentName?) {
      messenger?.binder?.unlinkToDeath(mDeathRecipient,0)
    }
  }

  private val mDeathRecipient : IBinder.DeathRecipient = IBinder.DeathRecipient {
    bindService(musicService,mConnection,Service.BIND_AUTO_CREATE)
  }

  private val musicService : Intent by lazy { Intent(this,MusicService::class.java) }

  private lateinit var binder : IBinder

  private var messenger : Messenger? = null

  override fun onCreate(savedInstanceState : Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    findViewById<Button>(R.id.start).setOnClickListener {
      Message().apply {
        what = MusicService.ACTION_START
        messenger?.send(this)
      }
    }
    findViewById<Button>(R.id.stop).setOnClickListener {
      Message().apply {
        what = MusicService.ACTION_STOP
        messenger?.send(this)
      }
    }
    findViewById<Button>(R.id.pause).setOnClickListener {
      Message().apply {
        what = MusicService.ACTION_PAUSE
        messenger?.send(this)
      }
    }
    bindService(musicService,mConnection,Service.BIND_AUTO_CREATE)
  }

  override fun onDestroy() {
    super.onDestroy()
    unbindService(mConnection)
  }
}
