package com.joker.test_java;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TestClass{

 private final NewTest test=new NewTest();

 private final static String message ="{\n"+
   "    \"resultCode\": \"0000\",\n"+
   "    \"msg\": \"操作成功\",\n"+
   "    \"data\": {\n"+
   "        \"快速化妆\": [\n"+
   "            {\n"+
   "                \"id\": 7,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1535116068698_2.3011592145200908\",\n"+
   "                \"title\": \"超级美口红棒/裸色NUDE\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS07\",\n"+
   "                \"detail\": \"超级美口红棒/裸色NUDE\",\n"+
   "                \"videoUrl\": \"\",\n"+
   "                \"createTime\": \"2018-08-08 20:59:14\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 1,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 7,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 8,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1535116110239_0.8036334536118594\",\n"+
   "                \"title\": \"超级美口红棒/橘色ORANGE\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS08\",\n"+
   "                \"detail\": \"超级美口红棒/橘色ORANGE\",\n"+
   "                \"videoUrl\": \"\",\n"+
   "                \"createTime\": \"2018-08-08 21:22:11\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 1,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 7,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 9,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1535116209025_2.8680407199477695\",\n"+
   "                \"title\": \"超级美口红棒/粉色PINK\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS09\",\n"+
   "                \"detail\": \"超级美口红棒/粉色PINK\",\n"+
   "                \"videoUrl\": \"\",\n"+
   "                \"createTime\": \"2018-08-08 21:40:50\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 7,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 10,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1535116231030_2.6546485463994496\",\n"+
   "                \"title\": \"超级美口红棒/红色RED\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS010\",\n"+
   "                \"detail\": \"超级美口红棒/红色RED\",\n"+
   "                \"videoUrl\": \"\",\n"+
   "                \"createTime\": \"2018-08-08 21:53:11\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 7,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 15,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1533811573038_0.42924725715299483\",\n"+
   "                \"title\": \"迪奥Dior雪晶灵亮肤绽放修颜乳\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 9.9,\n"+
   "                \"goodsNo\": \"GDS015\",\n"+
   "                \"detail\": \"\",\n"+
   "                \"videoUrl\": \"\",\n"+
   "                \"createTime\": \"2018-08-08 22:28:59\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 0,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 16,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1533873473870_0.5698811615330068\",\n"+
   "                \"title\": \"韩国clio菲丽菲拉 WhollyDeep锁妆眼线笔\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 59,\n"+
   "                \"goodsNo\": \"GDS016\",\n"+
   "                \"detail\": \"\",\n"+
   "                \"videoUrl\": \"\",\n"+
   "                \"createTime\": \"2018-08-08 22:29:51\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 7,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 17,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1533811772605_0.07025966685115792\",\n"+
   "                \"title\": \"韩国clio菲丽菲拉墨水彩色睫毛膏\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 59,\n"+
   "                \"goodsNo\": \"GDS017\",\n"+
   "                \"detail\": \"\",\n"+
   "                \"videoUrl\": \"\",\n"+
   "                \"createTime\": \"2018-08-08 22:32:13\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 7,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 19,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1535116421141_0.2828433443017153\",\n"+
   "                \"title\": \"发帖+头绳\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS019\",\n"+
   "                \"detail\": \"发粘3个，头绳一个\",\n"+
   "                \"videoUrl\": \"\",\n"+
   "                \"createTime\": \"2018-08-08 22:38:01\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 6,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 20,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1535118145598_2.6830916647800107\",\n"+
   "                \"title\": \"超级美素颜水\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS020\",\n"+
   "                \"detail\": \"搭配保湿清洁水，使用于化妆喷枪\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536841849812_1.6957230690433178\",\n"+
   "                \"createTime\": \"2018-08-08 22:43:27\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 5,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 22,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1534935511818_3.9796800058516766\",\n"+
   "                \"title\": \"「自然色」\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS022\",\n"+
   "                \"detail\": \"搭配化妆喷枪使用，用于肤色粉底\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536842078155_3.0549651904365693\",\n"+
   "                \"createTime\": \"2018-08-08 22:57:26\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 6,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 23,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1534935644403_3.136308231694466\",\n"+
   "                \"title\": \"「浅粉色」\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS023\",\n"+
   "                \"detail\": \"搭配化妆喷枪使用，用于腮红和眼影过渡\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536842078155_3.0549651904365693\",\n"+
   "                \"createTime\": \"2018-08-08 23:06:09\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 1,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 6,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 24,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1534935759168_0.7863427097028963\",\n"+
   "                \"title\": \"「棕色」\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS024\",\n"+
   "                \"detail\": \"搭配化妆喷枪使用，用于眼影、眉毛和补发际线\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536842078155_3.0549651904365693\",\n"+
   "                \"createTime\": \"2018-08-08 23:08:20\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 6,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 25,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1534935787030_2.151163245238127\",\n"+
   "                \"title\": \"「轮廓黑」\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS025\",\n"+
   "                \"detail\": \"搭配化妆喷枪使用，用于眉毛、眼线和补发际线\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536842078155_3.0549651904365693\",\n"+
   "                \"createTime\": \"2018-08-08 23:15:05\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 6,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 27,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1536805723477_4.329574058505113\",\n"+
   "                \"title\": \"眉眼套装\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS027\",\n"+
   "                \"detail\": \"搭配眉粉、眼影盘和睫毛夹使用\",\n"+
   "                \"videoUrl\": \"\",\n"+
   "                \"createTime\": \"2018-08-09 17:47:20\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 4,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 29,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1534936313719_2.130502877104247\",\n"+
   "                \"title\": \"「象牙白」\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS029\",\n"+
   "                \"detail\": \"搭配化妆喷枪使用，用于白皮粉底\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536842078155_3.0549651904365693\",\n"+
   "                \"createTime\": \"2018-08-22 19:12:21\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": null,\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": null,\n"+
   "                \"goodsNum\": 5,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": null\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 31,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1535116827108_4.653382593890443\",\n"+
   "                \"title\": \"卷发筒\",\n"+
   "                \"species\": 2,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS031\",\n"+
   "                \"detail\": \"配合造型机快速打造卷发造型\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536818446513_4.568147416544262\",\n"+
   "                \"createTime\": \"2018-08-24 21:22:26\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": null,\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": null,\n"+
   "                \"goodsNum\": 0,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": null\n"+
   "            }\n"+
   "        ],\n"+
   "        \"香体系统\": [\n"+
   "            {\n"+
   "                \"id\": 1,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1534146222848_2.9581407246848856\",\n"+
   "                \"title\": \"ZERO 零\",\n"+
   "                \"species\": 3,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS01\",\n"+
   "                \"detail\": \"快速祛除异味 还原纯净体香\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536841930603_1.8251871859575641\",\n"+
   "                \"createTime\": \"2018-08-08 18:31:10\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 1,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 2147483647,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 2,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1534146252342_0.08077423719067056\",\n"+
   "                \"title\": \"SEDUCTION 诱惑\",\n"+
   "                \"species\": 3,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS02\",\n"+
   "                \"detail\": \"玫瑰桃花基调 散发迷人魅力\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536841930603_1.8251871859575641\",\n"+
   "                \"createTime\": \"2018-08-08 18:33:34\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 1,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 2147483647,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 3,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1534174775087_0.07674340498333598\",\n"+
   "                \"title\": \"LADY LUCK 幸运女神\",\n"+
   "                \"species\": 3,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS03\",\n"+
   "                \"detail\": \"4种幸运花制成 带来好运\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536841930603_1.8251871859575641\",\n"+
   "                \"createTime\": \"2018-08-08 18:37:46\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 1,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 2147483647,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 4,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1534146304235_1.6303041380322092\",\n"+
   "                \"title\": \"HEALING 治愈\",\n"+
   "                \"species\": 3,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS04\",\n"+
   "                \"detail\": \"薰衣草等治愈系味道 舒缓情绪\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536841930603_1.8251871859575641\",\n"+
   "                \"createTime\": \"2018-08-08 18:41:22\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 1,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 2147483647,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            }\n"+
   "        ],\n"+
   "        \"紧急护肤\": [\n"+
   "            {\n"+
   "                \"id\": 5,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1533724993242_2.595043239409801\",\n"+
   "                \"title\": \"韩国WHOO/后 还幼眼霜\",\n"+
   "                \"species\": 1,\n"+
   "                \"price\": 25,\n"+
   "                \"goodsNo\": \"GDS05\",\n"+
   "                \"detail\": \"\",\n"+
   "                \"videoUrl\": \"\",\n"+
   "                \"createTime\": \"2018-08-08 18:43:31\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 1,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 0,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 6,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1533726644039_2.10567788083139\",\n"+
   "                \"title\": \"无创祛黑头\",\n"+
   "                \"species\": 1,\n"+
   "                \"price\": 10,\n"+
   "                \"goodsNo\": \"GDS06\",\n"+
   "                \"detail\": \"内含消毒棉片、毛孔清理水（擦拭片*1（方形，可湿敷））、毛孔闭合水（擦拭片*1（方形，可湿敷））、一次性黑头吸管\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536839025065_4.343480086749244\",\n"+
   "                \"createTime\": \"2018-08-08 19:12:55\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 1,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 6,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 11,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1535117890857_1.2143711913253041\",\n"+
   "                \"title\": \"超级美贵妇水\",\n"+
   "                \"species\": 1,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS011\",\n"+
   "                \"detail\": \"搭配保湿清洁水，使用于无针水光注氧枪\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536841739491_3.9578235277224696\",\n"+
   "                \"createTime\": \"2018-08-08 22:02:11\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 4,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 12,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1535117979913_4.259724723305604\",\n"+
   "                \"title\": \"超级美养肤水\",\n"+
   "                \"species\": 1,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS012\",\n"+
   "                \"detail\": \"搭配保湿清洁水，使用于无针水光注氧枪\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536841804288_1.0075471050094953\",\n"+
   "                \"createTime\": \"2018-08-08 22:05:26\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 3,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 13,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1535116457548_1.10348681868777\",\n"+
   "                \"title\": \"清洁&卸妆包\",\n"+
   "                \"species\": 1,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS013\",\n"+
   "                \"detail\": \"全效卸妆湿巾、清洁湿巾、消毒棉片、洁面巾\",\n"+
   "                \"videoUrl\": \"\",\n"+
   "                \"createTime\": \"2018-08-08 22:11:17\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 14,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 14,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1533906151069_4.153484757423602\",\n"+
   "                \"title\": \"STASIA润肌修护膜\",\n"+
   "                \"species\": 1,\n"+
   "                \"price\": 20,\n"+
   "                \"goodsNo\": \"GDS014\",\n"+
   "                \"detail\": \"眼药水级别面膜\",\n"+
   "                \"videoUrl\": \"\",\n"+
   "                \"createTime\": \"2018-08-08 22:20:42\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 7,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 21,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1533869568798_2.0035428621168627\",\n"+
   "                \"title\": \"RF童颜机\",\n"+
   "                \"species\": 1,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS021\",\n"+
   "                \"detail\": \"内含超级美紧致淡纹露+消毒棉片+洁面巾\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536841895024_1.181197225665629\",\n"+
   "                \"createTime\": \"2018-08-08 22:50:08\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": \"\",\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": \"\",\n"+
   "                \"goodsNum\": 6,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": \"\"\n"+
   "            },\n"+
   "            {\n"+
   "                \"id\": 30,\n"+
   "                \"isSell\": 1,\n"+
   "                \"floor\": null,\n"+
   "                \"aFew\": null,\n"+
   "                \"bigSort\": null,\n"+
   "                \"smallSort\": null,\n"+
   "                \"isDelete\": 0,\n"+
   "                \"goodsBannerImgs\": null,\n"+
   "                \"goodsDetailImg\": null,\n"+
   "                \"picUrl\": \"http://qiniuofficial.chaojimei.cn/1535118449983_4.546277134591561\",\n"+
   "                \"title\": \"深层清洁\",\n"+
   "                \"species\": 1,\n"+
   "                \"price\": 1,\n"+
   "                \"goodsNo\": \"GDS030\",\n"+
   "                \"detail\": \"配有洁面巾，使用于无针水光注氧枪\",\n"+
   "                \"videoUrl\": \"http://qiniuofficial.chaojimei.cn/1536842285993_2.7573294113117006\",\n"+
   "                \"createTime\": \"2018-08-24 20:52:47\",\n"+
   "                \"updateTime\": null,\n"+
   "                \"createUser\": 4,\n"+
   "                \"status\": 0,\n"+
   "                \"remarks\": null,\n"+
   "                \"collenction\": null,\n"+
   "                \"vectis\": null,\n"+
   "                \"videoName\": null,\n"+
   "                \"goodsNum\": 5,\n"+
   "                \"type\": null,\n"+
   "                \"createUserName\": null,\n"+
   "                \"viedoName\": null\n"+
   "            }\n"+
   "        ]\n"+
   "    }\n"+
   "}";
 public static void main(String[] args) throws IOException, InterruptedException{

  Observable.range(1,10)
    .subscribeOn(Schedulers.newThread())
    .map(new Function<Integer,Integer>(){
     @Override public Integer apply(Integer integer) throws Exception{
      try{
       Thread.sleep(10);
      }catch(InterruptedException e){
       e.printStackTrace();
      }
      System.out.println("TestClass.Function----"+Thread.currentThread().getName());

      return 1+10;
     }
    })
    .observeOn(Schedulers.computation())
    .subscribe(new Observer<Integer>(){
     @Override public void onSubscribe(Disposable d){
      System.out.println("TestClass.onSubscribe----"+Thread.currentThread().getName());
     }

     @Override public void onNext(Integer integer){

      System.out.println("TestClass.onNext----"+Thread.currentThread().getName());

     }

     @Override public void onError(Throwable e){
      System.out.println("TestClass.onComplete----"+Thread.currentThread().getName());
     }

     @Override public void onComplete(){
      System.out.println("TestClass.onComplete----"+Thread.currentThread().getName());

     }
    });
  Thread.sleep(10000);

// String url="https://api.weibo.com/2/users/show.json?access_token=2.00U_J9vCCb8yYB16947545ddoMg_vD&uid=2681225804";
//  Request request=new Request.Builder().url(url).get().build();
//  Response response=new OkHttpClient().newCall(request).execute();
//
//  Single.just(response)
//    .map(new GsonConvertFunction<HashMap<String,Object>>())
//    .subscribe(new SingleObserver<HashMap<String,Object>>(){
//     @Override public void onSubscribe(Disposable d){
//
//     }
//
//     @Override public void onSuccess(HashMap<String,Object> stringObjectHashMap){
//      for(String key : stringObjectHashMap.keySet()) {
//       System.out.println("key = "+key+"---------value = "+stringObjectHashMap.get(key));
//      }
//     }
//
//     @Override public void onError(Throwable e){
//
//     }
//    });
 }

}
