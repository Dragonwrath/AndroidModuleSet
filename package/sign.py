# coding:utf-8

import os
import sys 
 
from config import version_name
from config import project_folder

singer_location="D:/Development/SDK/build-tools/28.0.0/apksigner"
jiagu_output_apk= project_folder+"/bak/jiagu/"+version_name
sign_out_dir=project_folder+"/bak/sign/"+version_name
sign_output_apk=sign_out_dir+"/app_"+version_name+"_jiagu_signed.apk"
sign_jsk_location="D:/key/mlh.jks"
sign_jks_key="meilianhui98"
sign_jks_alias="美联汇"
sign_jks_store_password="meilianhui98"

def sign():
	for file in os.listdir(jiagu_output_apk):
		if file:
			if not os.path.exists(sign_out_dir):
				os.makedirs(sign_out_dir)
			f = open(sign_output_apk, 'w')
			f.close()
			print("------------singer begin--------------------")
			command =singer_location+" sign --ks " + sign_jsk_location  #jks 路径
			command +=" --ks-key-alias "+sign_jks_alias #jks文件别名	
			command +=" --key-pass pass:"+sign_jks_key 	#jks文件的密码		
			command +=" --ks-pass pass:"+sign_jks_store_password #jsk 文件的存储密码
			command +=" --out "+ sign_output_apk #签名之后的文件输出目录
			command +=" "+jiagu_output_apk+"/"+file #签名前的apk目录
			print(command)
			print(os.system(command))
			print("------------singer end--------------------")

if __name__== '__main__':
	sign();
