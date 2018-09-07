# coding:utf-8

#tinker 打包模块，
#tinker 命令如下
#java -jar tinker-patch-cli.jar -old old.apk -new new.apk -config tinker_config.xml -out output_path

import os
import sys 

#
from config import base_apk_file
from config import should_path
from config import project_folder
from config import version_name

from sign import sign_output_apk

tinker_jar_location="D:/tinker-patch-cli-1.9.6.jar"
tinker_config="D:/tinker_config.xml"
tinker_output_folder=project_folder+"/bak/tinker/"+version_name

def tinker():
	if should_path :
		if base_apk_file and base_apk_file.strip() !="" and base_apk_file.rfind(".apk")>0:
			base_path_apk=base_apk_file[base_apk_file.rfind("/")+1:]	
			print(base_path_apk)
			base_path_apk_name=base_path_apk[:base_path_apk.rfind(".apk")]
			print(base_path_apk_name)
			tinker_output_apk_folder=tinker_output_folder+"/"+version_name+"_to_"+base_path_apk_name
			if not os.path.exists(tinker_output_folder):
					os.makedirs(tinker_output_folder)	
			print("------------tinker begin--------------------")

			print(os.system("java -jar "+tinker_jar_location #tinker-jar 的目录
			+ " -old " + base_apk_file # 基准包apk的地址
			+" -new " + sign_output_apk  # 新的签名包的apk的目录
			+" -config " + tinker_config # tinker_config的配置文件的内容
			+" -out "+ tinker_output_apk_folder)) # 输出目录  必须指向一个空目录，否则，会删除所有的文件
			print("------------tinker end--------------------")

		else:
			raise RuntimeError("base_apk_file is not exist!!")

if __name__=='__main__':
	tinker()