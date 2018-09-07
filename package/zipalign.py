#-*-coding:utf-8-*-

import os
import sys    

 
from inline_package import version_name
from inline_package import project_folder

from jiagu import jiagu_output_apk


zipalign_tool_folder="D:/Development/SDK/build-tools/28.0.0/"

zipalign_output_dir="D:\Project\tinker\TinkerSample\bak\zip_align"

def zipAlign():
	for(file in os.listdir(jiagu_output_apk)): #找到第一个加固的文件
		if file :
			
			print(os.system(zipalign_tool_folder+" " +)

if __name__== '__main__':
	zipAlign()
	
