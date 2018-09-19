package com.joker.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.joker.main.core.LoginMangerBridge
import com.joker.main.core.OnUserAuthResultListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState : Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    weibo.setOnClickListener{LoginMangerBridge.weiBo(this,object : OnUserAuthResultListener {
      override fun onAuthCancel() {
        println("LoginActivity.weibo.onAuthCancel")
      }

      override fun onAuthSuccess(userInfo : UserInfo?) {
        println(userInfo)
      }

      override fun onAuthFailure(throwable : Throwable?) {
        println("LoginActivity.weibo.onAuthFailure")
      }
    })}
    wechat.setOnClickListener{LoginMangerBridge.weChat(this,object : OnUserAuthResultListener {
      override fun onAuthCancel() {
        println("LoginActivity.wechat.onAuthCancel")
      }

      override fun onAuthSuccess(userInfo : UserInfo?) {
        println(userInfo)
      }

      override fun onAuthFailure(throwable : Throwable?) {
        println("LoginActivity.wechat.onAuthFailure")
      }
    })}
  }
}
