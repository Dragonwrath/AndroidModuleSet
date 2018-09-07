import jiagu
import sign
import tinker
import walle

#adb push src /storage/emulated/0/Pictures

#版本说明
#要点：版本说明添加在以下的区域内，采取最新的版本在最前面的方式说明
#区分：基准包与补丁包的区别,如果该版本属于某个版本的修复版本，需要特别说明，


#------------v-1.0.0-------------------------------------

#加固
jiagu.jiagu()

#sign
sign.sign()

#tinker
tinker.tinker()

#walle
walle.walle()

