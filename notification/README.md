#### 写在最前
通常情况下来说，我们不需要自定义样式的话，可以使用默认的NotificationCompat.Builder
因为，他最终会回调到系统的实现，填充自己的样式，我们就不需要根据相应的主题色，进行对应的设置

##### 自定义Notification
我们需要进行相应的自定义，推荐使用，notification包里面的相应的工具类，
- 发送文本通知要点，必须setSmallIcon，推荐jpg，尝试过使用vector drawable做icon无法安装