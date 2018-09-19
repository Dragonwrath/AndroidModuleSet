package com.joker.ssl

import android.os.Build
import android.support.annotation.RequiresApi
import android.net.http.SslError
import android.webkit.*
import java.security.KeyStore
import java.security.PrivateKey
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import android.net.http.SslCertificate
import java.util.*


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
object WebViewClientImplV21 : WebViewClient() {
  private val lock = CountDownLatch(1)
  private var privateKey : PrivateKey? = null
  private var certificates : Array<X509Certificate>? = null

  override fun onReceivedSslError(view : WebView,handler : SslErrorHandler,error : SslError) {
    val serverCert = CertificateFactory.getInstance("X509").generateCertificate(view.context.assets.open("server.cer"))
    val bundle = SslCertificate.saveState(error.certificate)
    val bytes = bundle.getByteArray("x509-certificate")
    if (serverCert.encoded != null) {
      if (Arrays.equals(bytes,serverCert.encoded)) {
        handler.proceed()
      }
    }
  }

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onReceivedClientCertRequest(view : WebView?,request : ClientCertRequest?) {
    if (privateKey == null || certificates == null) {
      Thread {
        if (view != null) {
          val clientKeyStore : KeyStore = KeyStore.getInstance("BKS")
          val password = "chaoji".toCharArray()
          clientKeyStore.load(view.context.assets.open("client.bks"),password)
          val aliases = clientKeyStore.aliases()
          for (alias in aliases) {
            (clientKeyStore.getKey(alias,password) as? PrivateKey)?.apply {
              val arrayOfCertificates = clientKeyStore.getCertificateChain(alias)
              val list = mutableListOf<X509Certificate>()
              for (certificate in arrayOfCertificates) {
                if (certificate is X509Certificate) {
                  list.add(certificate)
                }
              }
              if (list.size > 0) {
                privateKey = this
                certificates = list.toTypedArray()
                lock.countDown()
              }
            }
          }
        }
      }.start()
    }
    try {
      lock.await(3,TimeUnit.SECONDS)
      if (privateKey != null && certificates != null) {
        request?.proceed(privateKey,certificates)
        return
      }
    } catch (e : Throwable) {
      //      e.printStackTrace()
    }
    request?.cancel()
  }

}