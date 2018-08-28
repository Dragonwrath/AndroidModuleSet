package com.joker.lock

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.*

class MusicService : Service(),Handler.Callback,MediaPlayer.OnPreparedListener {

  override fun onPrepared(mp : MediaPlayer?) {
    mp?.start()
  }

  companion object {
    const val ACTION_START = 0
    const val ACTION_STOP = 1
    const val ACTION_PAUSE = 2
  }

  override fun handleMessage(msg : Message?) : Boolean {
    when (msg?.what) {
      ACTION_START -> {
        player = MediaPlayer()
        player?.apply {
          reset()
          setDataSource(assets.openFd("lover.mp3").fileDescriptor)
          setAudioStreamType(AudioManager.STREAM_MUSIC)
          setOnPreparedListener(this@MusicService)
          isLooping=true
          prepareAsync()
        }
      }

      ACTION_PAUSE -> {
        player?.pause()
      }

      ACTION_STOP  -> {
        player?.stop()
        player?.release()
        player = null
      }
    }
    return false
  }

  private val messenger by lazy {
    val thread = HandlerThread(MusicService::class.java.simpleName)
    thread.start()
    val handler = Handler(thread.looper,this)
    Messenger(handler)
  }

  private var receiver : LockScreenReceiver? = null
  private var player : MediaPlayer? = null

  override fun onCreate() {
    super.onCreate()
    receiver = LockScreenReceiver()
    val filter = IntentFilter()
    filter.addAction(Intent.ACTION_SCREEN_OFF)
    filter.addAction(Intent.ACTION_SCREEN_ON)
    registerReceiver(receiver,filter)
    println("MusicService.onCreate")
  }

  override fun onBind(intent : Intent) : IBinder {
    println("MusicService.onBind")
    return messenger.binder
  }

  override fun onDestroy() {
    super.onDestroy()
    println("MusicService.onDestroy")
    receiver?.also { unregisterReceiver(receiver) }
  }


}
