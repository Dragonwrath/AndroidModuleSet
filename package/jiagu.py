# coding:utf-8

import os
import sys 

from config import version_name

jiagu_jar="C:/Users/Joker/Desktop/360/jiagu/jiagu.jar"
jiagu_username="zhuning-916@163.com"
jiagu_password="zn04091402"
jiagu_release_apk="D:/Project/tinker/TinkerSample/app/release/app-release.apk"
jiagu_output_apk="D:/Project/tinker/TinkerSample/bak/jiagu/"+version_name+"/"


def jiagu():
	print(os.system("java -jar "+jiagu_jar+" -login "+jiagu_username+" "+jiagu_password))
	if not os.path.exists(jiagu_output_apk):
		os.makedirs(jiagu_output_apk) 
	print(os.system("java -jar "+ jiagu_jar+" -jiagu "+ jiagu_release_apk+ " "+jiagu_output_apk))

if __name__== '__main__':
	jiagu()
