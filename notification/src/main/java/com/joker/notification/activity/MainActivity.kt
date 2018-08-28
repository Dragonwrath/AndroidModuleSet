package com.joker.notification.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import com.joker.notification.MessageNotificationFactory
import com.joker.notification.NotificationManagerImpl
import com.joker.notification.ProgressNotificationFactory
import com.joker.notification.R
import java.util.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
  /**
   * Called when a view has been clicked.
   *
   * @param v The view that was clicked.
   */
  override fun onClick(v : View?) {
    when (v?.id) {
      R.id.text     -> {
        sendText()
      }

      R.id.progress -> {
        autoProgress()
      }

      R.id.bigText  -> {
        bigContent()
      }
    }
  }

  override fun onCreate(savedInstanceState : Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    NotificationManagerImpl.init(this)
    findViewById<TextView>(R.id.text).setOnClickListener(this)
    findViewById<TextView>(R.id.progress).setOnClickListener(this)
    findViewById<TextView>(R.id.bigText).setOnClickListener(this)
  }

  private fun sendText() {
    MessageNotificationFactory.produceMessage(this,"哈哈哈哈哈哈哈哈哈哈哈哈")
  }

  private var currentProgress = 0

  private fun autoProgress() {
    ProgressNotificationFactory.produceProgress(this,currentProgress,100)
    currentProgress += Random().nextInt(10)
    if (currentProgress < 100)
      Handler().postDelayed({ autoProgress() },1000)
  }

  private fun bigContent() {
    MessageNotificationFactory.produceBigMessage(this,"内容","哈哈哈哈哈哈")
  }
}
