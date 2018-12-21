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

:: 关闭pid为4的进程
tskill 4

:: 清除缓存域名
ipconfig /flushdns

:: 查看本地ip等相关信息
ipconfig /all

:: 查看域名解析结果，输入命令后在里面的交互里输入网址
nslookup

:: Apache无法启动的原因，80端口被PID为4的system进程占用，该进程会启动http系统服务，进而占据80端口
net stop http 

:: 获取SID，显示当前的用户名和SID值
whoami /user

:: 删除注册表信息，其中<SID>从前述命令获取
reg delete "HKEY_USERS\<SID>\Software\Scooter Software\Beyond Compare 4" /v CacheId /f


:: 定义运行打印脚本
REM 设置
REM 搜索打开“任务计划程序”，点击左侧任务计划程序库，在中间任务列表区右击选择“新建任务”
REM 常规标签卡输入名称，
REM 触发器选项卡中点击新建设置运行时间，
REM 操作选项卡中点击新建指定要启动的程序(此处为下列bat脚本)，
REM 条件选项卡中看情况要不要勾选“只有在计算机使用交流电电源时才启动此任务”
REM 脚本内容
:one
echo %date:~0,10% %time:~0,-3% 检查当前事项...
ping 127.0.0.1 -n 10>nul
goto one
