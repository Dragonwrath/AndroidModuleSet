package com.joker.common.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class AESUtils{

  private AESUtils(){
    throw new AssertionError("This class can not be instantiated!");
  }

  @Nullable
  public static String encrypt(String sSrc,String key){
    if(TextUtils.isEmpty(sSrc)||TextUtils.isEmpty(key)) return null;
    try{
      Charset charset=Charset.forName("utf-8");
      byte[] raw=Arrays.copyOfRange(key.getBytes(charset),0,16);
      SecretKeySpec skeySpec=new SecretKeySpec(raw,"AES");
      Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE,skeySpec,new IvParameterSpec(raw));
      byte[] encrypted=cipher.doFinal(sSrc.getBytes(charset));
      return Base64.encodeToString(encrypted,Base64.DEFAULT);
    }catch(Exception ex){
      return null;
    }
  }

  public static String decrypt(String sSrc,String key){
    if(TextUtils.isEmpty(sSrc)||TextUtils.isEmpty(key)) return null;
    try{
      Charset charset=Charset.forName("utf-8");
      byte[] raw=Arrays.copyOfRange(key.getBytes(charset),0,16);
      byte[] decrypt=Base64.decode(sSrc,Base64.DEFAULT);
      SecretKeySpec skeySpec=new SecretKeySpec(raw,"AES");
      Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE,skeySpec,new IvParameterSpec(raw));
      byte[] encrypted=cipher.doFinal(decrypt);
      return new String(encrypted,charset);
    }catch(Exception e){
      return null;
    }
  }

  public static void encrypt(InputStream in,OutputStream out,byte[] raw){
    if(raw==null)
      throw new IllegalArgumentException("");
    CipherInputStream cis=null;
    try{
      SecretKeySpec skeySpec=new SecretKeySpec(raw,"AES");
      Cipher cipher;
      cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE,skeySpec,new IvParameterSpec(raw));
      cis=new CipherInputStream(in,cipher);
      int len;
      byte[] bytes=new byte[128];
      while((len=cis.read(bytes))!=-1){
        out.write(bytes,0,len);
      }
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      try{
        in.close();
        out.close();
        out.flush();
        if(cis!=null){
          cis.close();
        }
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }

  public static void decrypt(InputStream in,OutputStream out,byte[] raw){
    CipherOutputStream cos=null;
    try{
      SecretKeySpec skeySpec=new SecretKeySpec(raw,"AES");
      Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
      cipher.init(Cipher.DECRYPT_MODE,skeySpec,new IvParameterSpec(raw));
      cos=new CipherOutputStream(out,cipher);
      int len;
      byte[] bytes=new byte[128];
      while((len=in.read(bytes))!=-1){
        cos.write(bytes,0,len);
      }

    }catch(Exception e){
      e.printStackTrace();
    }finally{
      try{
        in.close();
        out.flush();
        if(cos!=null){
          cos.flush();
          cos.close();
          out.close();
        }
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }
}
