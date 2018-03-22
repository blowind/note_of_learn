:: 显示或设置活动代码页编号 
:: 比如输入：mode con cp select=936，则表示显示简体中文。如果输入mode con cp select=437，则表示显示MS-DOS 美国英语，而中文显示将会是?。
:: 当cmd命令行不能显示中文时键入如下命令：chcp 936  即可显示中文了
chcp 437


查看当前使用的端口
netstat -an

查看并过滤端口
netstat -an | findstr "8080"

清除缓存域名
ipconfig /flushdns
