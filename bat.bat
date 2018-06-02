:: 显示或设置活动代码页编号 
:: 比如输入：mode con cp select=936，则表示显示简体中文。如果输入mode con cp select=437，则表示显示MS-DOS 美国英语，而中文显示将会是?。
:: 当cmd命令行不能显示中文时键入如下命令：chcp 936  即可显示中文了
chcp 437


:: 查看当前使用的端口
netstat -an

:: 查看并过滤端口
netstat -ano | findstr "8080"

:: 根据进程号查看进程详细信息
tasklist | findstr "11234"

:: 清除缓存域名
ipconfig /flushdns

:: 关闭pid为4的进程
tskill 4

:: Apache无法启动的原因，80端口被PID为4的system进程占用，该进程会启动http系统服务，进而占据80端口
net stop http 