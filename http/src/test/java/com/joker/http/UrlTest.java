package com.joker.http;
import org.junit.Assert;
import org.junit.Test;

public class UrlTest{
 @Test
 public void checkUrl(){
  Assert.assertTrue("http:\\\\www.youtube.cn".matches("^http.*[a-zA-Z]$"));
  Assert.assertTrue("https:\\\\www.youtube.cn".matches("^http.*[a-zA-Z]$"));
  Assert.assertFalse("https:\\\\www.youtube.cn3".matches("^http.*[a-zA-Z]$"));
  Assert.assertFalse("".matches("^http.*[a-zA-Z]$"));
  Assert.assertFalse("https:\\\\www.youtube.".matches("^http.*[a-zA-Z]$"));
  Assert.assertTrue("https:\\\\www.youtube.c3n".matches("^http.*[a-zA-Z]$"));
  Assert.assertTrue("https:\\\\www.youtube.2cn".matches("^http.*[a-zA-Z]$"));
 }

 @Test
 public void usefulJsonString() {
  Assert.assertTrue("{}".matches("^\\{.*}$"));
  Assert.assertTrue("{1}".matches("^\\{.*}$"));
  Assert.assertTrue("{a}".matches("^\\{.*}$"));
  Assert.assertTrue("{b}".matches("^\\{.*}$"));
 }
}
