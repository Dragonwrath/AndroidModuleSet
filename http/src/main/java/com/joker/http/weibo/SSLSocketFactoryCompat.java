package com.joker.http.weibo;

import android.os.Build.VERSION;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

@SuppressWarnings({"DuplicateThrows","unused"})
public class SSLSocketFactoryCompat extends SSLSocketFactory {
 private SSLSocketFactory defaultFactory;
 private static String[] protocols = null;
 private static String[] cipherSuites = null;

 public SSLSocketFactoryCompat(X509TrustManager tm) {
  try {
   SSLContext sslContext = SSLContext.getInstance("TLS");
   sslContext.init(null, tm != null ? new X509TrustManager[]{tm} : null,null);
   this.defaultFactory = sslContext.getSocketFactory();
  } catch (GeneralSecurityException var3) {
   throw new AssertionError();
  }
 }

 private void upgradeTLS(SSLSocket ssl) {
  if (protocols != null) {
   ssl.setEnabledProtocols(protocols);
  }

  if (VERSION.SDK_INT < 21 && cipherSuites != null) {
   ssl.setEnabledCipherSuites(cipherSuites);
  }

 }

 public String[] getDefaultCipherSuites() {
  return cipherSuites;
 }

 public String[] getSupportedCipherSuites() {
  return cipherSuites;
 }

 public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
  Socket ssl = this.defaultFactory.createSocket(s, host, port, autoClose);
  if (ssl instanceof SSLSocket) {
   this.upgradeTLS((SSLSocket)ssl);
  }

  return ssl;
 }

 public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
  Socket ssl = this.defaultFactory.createSocket(host, port);
  if (ssl instanceof SSLSocket) {
   this.upgradeTLS((SSLSocket)ssl);
  }

  return ssl;
 }

 public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
  Socket ssl = this.defaultFactory.createSocket(host, port, localHost, localPort);
  if (ssl instanceof SSLSocket) {
   this.upgradeTLS((SSLSocket)ssl);
  }

  return ssl;
 }

 public Socket createSocket(InetAddress host, int port) throws IOException {
  Socket ssl = this.defaultFactory.createSocket(host, port);
  if (ssl instanceof SSLSocket) {
   this.upgradeTLS((SSLSocket)ssl);
  }

  return ssl;
 }

 public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
  Socket ssl = this.defaultFactory.createSocket(address, port, localAddress, localPort);
  if (ssl instanceof SSLSocket) {
   this.upgradeTLS((SSLSocket)ssl);
  }

  return ssl;
 }

 static {
  try {
   SSLSocket socket = (SSLSocket)SSLSocketFactory.getDefault().createSocket();
   if (socket != null) {
    List<String> protocols = new LinkedList<>();
    String[] var2 = socket.getSupportedProtocols();
    for(String protocol : var2) {
     if(!protocol.toUpperCase().contains("SSL")){
      protocols.add(protocol);
     }
    }
    SSLSocketFactoryCompat.protocols =(String[])protocols.toArray(new String[protocols.size()]);
    if (VERSION.SDK_INT < 21) {
     List<String> allowedCiphers = Arrays.asList("TLS_RSA_WITH_AES_256_GCM_SHA384", "TLS_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", "TLS_ECHDE_RSA_WITH_AES_128_GCM_SHA256", "TLS_RSA_WITH_3DES_EDE_CBC_SHA", "TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA", "TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA");
     List<String> availableCiphers = Arrays.asList(socket.getSupportedCipherSuites());
     HashSet<String> preferredCiphers = new HashSet<>(allowedCiphers);
     preferredCiphers.retainAll(availableCiphers);
     preferredCiphers.addAll(new HashSet<>(Arrays.asList(socket.getEnabledCipherSuites())));
     cipherSuites =preferredCiphers.toArray(new String[preferredCiphers.size()]);
    }
   }

  } catch (IOException var6) {
   throw new RuntimeException(var6);
  }
 }
}
