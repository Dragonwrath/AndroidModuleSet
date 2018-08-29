package com.joker.lock

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.annotation.SuppressLint
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.drawable.ColorDrawable
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class LockScreenActivity : AppCompatActivity(){

  private val mConnection : ServiceConnection = object : ServiceConnection {
    override fun onServiceConnected(name : ComponentName?,service : IBinder?) {
      service?.also {
        binder = it
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

  private val musicService by lazy { Intent(this,MusicService::class.java) }

  private lateinit var binder : IBinder

  private var messenger : Messenger? = null

  private val container by lazy { findViewById<LinearLayout>(R.id.container)!! }

  override fun onCreate(savedInstanceState : Bundle?) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
      this.setShowWhenLocked(true)
    } else {
      window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
        addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
      }
    }
    window.apply {
      addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
      addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON)
      addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
    if (Build.VERSION.SDK_INT > 18) {
      window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    } else {
      window.setBackgroundDrawableResource(android.R.color.transparent)
    }

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_lock_screen)
    findViewById<TextView>(R.id.text).setOnClickListener {
      this@LockScreenActivity.finish()
    }
    findViewById<Button>(R.id.start).setOnClickListener {
      Message().apply {
        what = MusicService.ACTION_START
        try {
          messenger?.send(this)
        } catch (e : Exception) {
        }
      }
    }
    findViewById<Button>(R.id.stop).setOnClickListener {
      Message().apply {
        what = MusicService.ACTION_STOP
        try {
          messenger?.send(this)
        } catch (e : Exception) {
        }
      }
    }
    findViewById<Button>(R.id.pause).setOnClickListener {
      Message().apply {
        what = MusicService.ACTION_PAUSE
        try {
          messenger?.send(this)
        } catch (e : Exception) {
        }
      }
    }
    bindService(musicService,mConnection,Service.BIND_AUTO_CREATE)
    var parent=container.parent
    while (parent!=null){
      (parent as ViewGroup).background=ColorDrawable(resources.getColor(android.R.color.transparent))
      parent=parent.parent
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    unbindService(mConnection)
  }

  override fun onBackPressed() {
    super.onBackPressed()
  }

  private lateinit var displayMetrics : DisplayMetrics
  private var startX = 0F
  private var startY = 0F

  override fun onTouchEvent(event : MotionEvent?) : Boolean {
    println("LockScreenActivity.onTouchEvent---${event?.x}---${event?.y}")
    val params = window.attributes
    when (event?.action) {
      MotionEvent.ACTION_DOWN,MotionEvent.ACTION_POINTER_DOWN -> {
        displayMetrics = resources.displayMetrics
        startX = event.x
        startY = event.y
        return true
      }
      MotionEvent.ACTION_MOVE                                 -> {
        val vectorX = (startX - event.x).toInt()
        val vectorY = ((startY - event.y)).toInt()
        var vectorAlpha = Math.max(Math.abs(vectorX),Math.abs(vectorY)).toFloat() / 200
        vectorAlpha = if (vectorAlpha > 0.8F) {
          0.2F
        } else {
          1 - vectorAlpha
        }
        params.alpha = vectorAlpha
        window.attributes = params
        val dX = event.x - startX
        container.translationX= dX
        val dY = event.y - startY
        container.translationY= dY
        val scale = Math.min(1F - Math.abs(dX) / displayMetrics.widthPixels,
            1F - Math.abs(dY) / displayMetrics.heightPixels)
        container.scaleX=scale
        container.scaleY=scale
        container.postInvalidate()
        return true
      }
      MotionEvent.ACTION_UP,MotionEvent.ACTION_POINTER_UP     -> {
        val endX = if ((event.x - startX) > 100) displayMetrics.widthPixels * 1F else 0F
        val endY = if ((event.y - startY) > 150) displayMetrics.heightPixels * 1F else 0F
        var shouldFinish = true
        if (endX == 0F && endY == 0F) {
          shouldFinish = false
        }
        val endAlpha = if (shouldFinish) 0f else 1f
        val set = AnimatorSet()
        val list = mutableListOf<Animator>()
        if (endX != 0F)
          list.add(ObjectAnimator.ofFloat(container,"translationX",event.x-startX,endX-startX))
        if (endY != 0F)
          list.add(ObjectAnimator.ofFloat(container,"translationY",event.y-startY,endY-startY))
        list.add(ObjectAnimator.ofFloat(container,"alpha",params.alpha,endAlpha))
        set.playTogether(list)
        set.duration = 500
        set.interpolator = LinearInterpolator()
        set.startDelay = 0
        set.start()
        set.addListener(object : Animator.AnimatorListener {
          override fun onAnimationCancel(animation : Animator?) {

          }

          override fun onAnimationStart(animation : Animator?) {
          }

          override fun onAnimationEnd(animation : Animator?) {
            this@LockScreenActivity.finish()
            overridePendingTransition(0,0)
          }

          override fun onAnimationRepeat(animation : Animator?) {
          }

        })
        return true
      }
    }
    return super.onTouchEvent(event)
  }

}
