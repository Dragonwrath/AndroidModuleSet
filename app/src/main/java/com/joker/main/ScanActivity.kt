package com.joker.main

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import com.joker.common.utils.LogUtils
import com.joker.permissions.BasePermissionActivity
import com.joker.scan.QrScanManager
import com.joker.scan.QrScanManagerImpl
import com.joker.scan.core.OnScanResultListener
import kotlinx.android.synthetic.main.activity_scan.*

class ScanActivity : BasePermissionActivity(),OnScanResultListener {
  override fun onScanFailure(ex : Exception?) {
    LogUtils.e(ScanActivity::javaClass.name,ex.toString())
  }

  override fun onScanSuccess(message : String?) {
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
  }

  override fun somePermissionsDenied() {

  }

  private var scanManager : QrScanManager? = null

  override fun allPermissionGranted() {
    scanManager=QrScanManagerImpl.Builder(this).addListener(this)
        .surfaceView(frame).create()
  }

  override fun onCreate(savedInstanceState : Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_scan)
    needRequestPermission(2048,arrayOf(Manifest.permission.CAMERA))
  }

}
