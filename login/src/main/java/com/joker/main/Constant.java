package com.joker.main;

public class Constant{
 public static final class WeChat{
  public final static String APP_ID=KeyHouse.getWeChatAppId();
  public final static String APP_SECRET=KeyHouse.getWeChatAppSecret();
 }
 public static final class WeiBo{
  public final static String APP_KEY=KeyHouse.getWeiboKey();
  public static final String REDIRECT_URL = "http://www.sina.com";
  public static final String SCOPE = // 应用申请的高级权限,已逗号分割
    "email,direct_messages_read,direct_messages_write,"
      + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
      + "follow_app_official_microblog," + "invitation_write";
 }

}
