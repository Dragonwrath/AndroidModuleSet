package com.joker.main.share;
import com.joker.main.share.bean.ImageShareBean;
import com.joker.main.share.bean.TextShareBean;
import com.joker.main.share.bean.VideoShareBean;
import com.joker.main.share.bean.WebShareBean;
public interface ShareAction{

 void sendText(TextShareBean bean) throws IllegalAccessException;

 void sendWebPage(WebShareBean bean) throws IllegalAccessException;

 void sendImage(ImageShareBean bean) throws IllegalAccessException;

 void sendVideo(VideoShareBean bean) throws IllegalAccessException;
}
