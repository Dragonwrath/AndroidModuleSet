import os
import sys 

from config import version_name

from sign import sign_output_apk

# java -jar walle-cli-all.jar batch -f channel  source.apk output_dir

#adb push --sync D:/Project/tinker/TinkerSample/bak/tinker/1.0.1/1.0.1_to_app_1.0.1_jiagu_signed/patch_signed.apk/storage/emulated/0/Pictures/

walle_jar_folder="D:/Project/tinker/TinkerSample/bak/walle/walle-cli-all.jar"
walle_base= "D:/Project/tinker/TinkerSample/bak/walle"
walle_channel= walle_base  +"/channel.txt"
walle_output_folder=	walle_base+"/"+version_name

def walle():
	if sign_output_apk.strip()!="" and sign_output_apk.rfind(".apk")>0 :
		print(walle_output_folder)
		if not os.path.exists(walle_output_folder):
			os.makedirs(walle_output_folder)
		print(os.system("java -jar " + walle_jar_folder 
		+" batch -f " +walle_channel
		+" "+sign_output_apk
		+" " +walle_output_folder
		))
	else :
		raise RuntimeError("some thing happened")

if __name__=='__main__':
	walle()