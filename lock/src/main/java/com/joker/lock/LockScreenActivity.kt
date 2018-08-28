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
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class LockScreenActivity : AppCompatActivity(),View.OnTouchListener {
  /**
   * Called when a touch event is dispatched to a view. This allows listeners to
   * get a chance to respond before the target view.
   *
   * @param v The view the touch event has been dispatched to.
   * @param event The MotionEvent object containing full information about
   * the event.
   * @return True if the listener has consumed the event, false otherwise.
   */
  @SuppressLint("ClickableViewAccessibility")
  override fun onTouch(v : View?,event : MotionEvent?) : Boolean {
    //    println("LockScreenActivity.onTouchEvent---${event?.x}---${event?.y}")
    //    val params = window.attributes
    //    when(event?.action) {
    //      MotionEvent.ACTION_DOWN,MotionEvent.ACTION_POINTER_DOWN-> {
    //        displayMetrics=resources.displayMetrics
    //        startX=event.x
    //        startY=event.y
    //        return true
    //      }
    //      MotionEvent.ACTION_MOVE->{
    //        val vectorX = (event.x - startX).toInt()
    //        val vectorY = ((event.y - startY)).toInt()
    //        var vectorAlpha = Math.max(Math.abs(vectorX),Math.abs(vectorY)) * 1f / 200
    //        vectorAlpha = if (vectorAlpha > 0.5F){
    //          0.5f
    //        } else {
    //          1-vectorAlpha
    //        }
    //        params.alpha=vectorAlpha
    //        window.attributes=params
    //        container.scrollTo(vectorX,vectorY)
    //
    //        return true
    //      }
    //      MotionEvent.ACTION_UP,MotionEvent.ACTION_POINTER_UP->{
    //        val endX= if ((event.x-startX)>100) displayMetrics.widthPixels*1F else 0F
    //        val endY= if ((event.y-startY)>150) displayMetrics.heightPixels*1F else 0F
    //        var shouldFinish=true
    //        if (endX==0F&&endY==0F) {
    //          shouldFinish=false
    //        }
    //        val endAlpha = if (shouldFinish) 0f else 1f
    //        val set = AnimatorSet()
    //        val list= mutableListOf<Animator>()
    //        if (endX!=0F)
    //          list.add(ObjectAnimator.ofFloat(container,"translationX",event.x,endX))
    //        if (endY!=0F)
    //          list.add(ObjectAnimator.ofFloat(container,"translationY",event.y,endY))
    //        list.add(ObjectAnimator.ofFloat(container,"alpha", params.alpha,endAlpha))
    //        set.playTogether(list)
    //        set.duration=500
    //        set.interpolator=LinearInterpolator()
    //        set.startDelay=0
    //        set.start()
    //        return true
    //      }
    //    }
    return super.onTouchEvent(event)
  }

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

  private val musicService : Intent by lazy { Intent(this,MusicService::class.java) }

  private lateinit var binder : IBinder

  private var messenger : Messenger? = null

  private val container by lazy { findViewById<LinearLayout>(R.id.container) }
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
        var vectorAlpha = Math.max(Math.abs(vectorX),Math.abs(vectorY)) * 1f / 200
        vectorAlpha = if (vectorAlpha > 0.8F) {
          0.8F
        } else {
          1 - vectorAlpha
        }
        params.alpha = vectorAlpha
        window.attributes = params
        container.scrollTo(vectorX,vectorY)
        container.scaleX= (vectorX / 200).toFloat()
        container.scaleY= (vectorY / 200).toFloat()

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
