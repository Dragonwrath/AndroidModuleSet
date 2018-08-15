package com.joker.main.share;
import com.joker.main.share.bean.ImageShareBean;
import com.joker.main.share.bean.TextShareBean;
import com.joker.main.share.bean.VideoShareBean;
import com.joker.main.share.bean.WebShareBean;
public interface ShareAction{

 void sendText(TextShareBean bean) throws IllegalArgumentException;

 void sendWebPage(WebShareBean bean) throws IllegalArgumentException;

 void sendImage(ImageShareBean bean) throws IllegalArgumentException;

 void sendVideo(VideoShareBean bean) throws IllegalArgumentException;
}
