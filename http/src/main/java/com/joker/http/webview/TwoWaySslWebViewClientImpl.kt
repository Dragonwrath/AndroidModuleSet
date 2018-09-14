//package com.joker.ssl
//
//import android.content.Context
//import android.os.Build
//import android.support.annotation.RequiresApi
//import android.webkit.*
//import java.net.URL
//import java.security.KeyStore
//import java.security.cert.CertificateFactory
//import javax.net.ssl.*
//import android.webkit.WebResourceResponse
//
//
//
//object TwoWaySslWebViewClientImpl: WebViewClient() {
//
//  override fun shouldOverrideUrlLoading(view : WebView?,url : String?) : Boolean {
//    println("SslWebViewClientImpl.shouldOverrideUrlLoading---below 21")
//    return super.shouldOverrideUrlLoading(view,url)
//  }
//
//  override fun shouldOverrideUrlLoading(view : WebView?,request : WebResourceRequest?) : Boolean {
//    println("SslWebViewClientImpl.shouldOverrideUrlLoading---above 21")
//    return super.shouldOverrideUrlLoading(view,request)
//
//  }
//
//  override fun shouldInterceptRequest(view : WebView?,url : String?) : WebResourceResponse {
//    try {
//      val connection = URL(url).openConnection()
//      if (connection is HttpsURLConnection) {
//        view?.apply {
//          connection.sslSocketFactory = generateFactory(context)
//          connection.hostnameVerifier = HostnameVerifier { _,_ -> true }
//        }
//      }
//      val stream = connection.apply {
//        doInput = true
//        doOutput = true
//        connectTimeout = 20000
//        readTimeout = 20000
//      }.getInputStream()
//
//      val contentType = connection.contentType
//      val encoding = connection.contentEncoding
//      if (contentType != null) {
//
//        var mimeType = contentType
//
//        // Parse mime type from contenttype string
//        if (contentType.contains(";")) {
//          mimeType = contentType.split(";")[0].trim()
//        }
//        // Return the response
//        return WebResourceResponse(mimeType,encoding,stream)
//      }
//    } catch (e:Exception){
//      e.printStackTrace()
//    }
//    return WebResourceResponse(null,null,null)
//  }
//
//
//  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//  override fun shouldInterceptRequest(view : WebView?,request : WebResourceRequest?) : WebResourceResponse {
//    super.shouldInterceptRequest(view,request)
//    return shouldInterceptRequest(view,request?.url.toString())
//  }
//
//
//  private fun generateFactory(context: Context) : SSLSocketFactory {
//    //server trust manager
//    val keystore = KeyStore.getInstance(KeyStore.getDefaultType())
//    keystore.load(null)
//    val certificate = CertificateFactory.getInstance("X509").generateCertificate(context.assets.open("server.cer"))
//    keystore.setCertificateEntry("server",certificate)
//    val factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
//    factory.init(keystore)
//    val tls = SSLContext.getInstance("TLS")
//
//    //client keystore
//    val clientKeyStore = KeyStore.getInstance("BKS")
//    clientKeyStore.load(context.assets.open("client.bks"),"chaoji".toCharArray())
//    val keyManagerFactory = KeyManagerFactory.getInstance("X509")
//    keyManagerFactory.init(clientKeyStore,"chaoji".toCharArray())
//
//    tls.init(keyManagerFactory.keyManagers,factory.trustManagers,null)
//    return tls.socketFactory as SSLSocketFactory
//  }
//}