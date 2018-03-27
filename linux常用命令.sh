
1、查看内存使用相关命令：
free  监控内存使用情况         free -m   将内存使用情况以M为单位显示
输出各字段含义：
total:总计物理内存的大小。
used:已使用多大。
free:可用有多少。
Shared:多个进程共享的内存总额。
Buffers/cached:磁盘缓存的大小。

watch -n 2 -d free   每两秒执行free一次，执行前清除屏幕，在同样位置显示数据。 -n 控制刷新 -d 每次显示不同的地方

/proc/meminfo            比较复杂的看内存使用的方法

top  动态显示系统各个进程的资源占用情况，包括task情况，CPU使用率，Mem使用率，swap交换区使用情况
第一个行是  
12:38:33 up 50 days, 23:15,  7 users,  load average: 60.58, 61.14, 61.22(分别为1分钟，5分钟，15分钟的平均负载)
类似uptime命令：
13:22:30 up 8 min,  4 users,  load average: 0.14, 0.38, 0.25   (uptime显示结果)


vmstat 5 5           在5秒时间内进行5次采样
http://blog.csdn.net/jamesjiangqian/article/details/6782548

2、查看CPU使用情况
/proc/cpuinfo            


3、统计文件夹下的文件个数
ls -l | wc -l                ### 先按行输出每个文件，再统计行数   ls -al显示隐藏行

find . -type f -print | wc -l       ##  搜索当前文件夹下的file并打印


4、给网卡添加地址
ifconfig eth1 add 2001:250:1800:1::88/64         ###  设置ipv6地址，掩码是64 ？？？？？


5、添加路由
route -A inet6 add default gw 2001:250:1800:1::1
同时要修改文件：
vi /etc/sysconfig/network
NETWORKING_IPV6=yes


6、搜索子目录中包含某字符串的特定文件
find ./src -name '*.ec' -exec grep -i niuc {} /; -print     ###  搜索./src目录下包含"niuc"的所有.ec文件

grep -R --include="*.cpp" key dir       ### 在dir目录下递归查找所有.cpp文件中的关键字key
grep -r : 明确要求搜索子目录
grep -d skip :忽略子目录
grep -i pattern files : 不区分大小写的搜索。默认情况下区分大小写
grep -l pattern files : 只列出匹配的文件名
grep -L pattern files : 只列出不匹配的文件名
grep -w pattern files : 只匹配整个单词，而不是字符串的一部分(如匹配“magic”而不是“magical”)
grep pattern1 | pattern2 files : 显示匹配pattern1或者pattern2的行
grep pattern1 file | grep pattern2 : 显示既匹配pattern1又匹配pattern2的行，其实是用管道把两个grep合并起来
grep '/<man'  : 匹配以man为单词前三个字母的行， /<标注单词的开始
grep 'man/>'  : 匹配以man为单词结尾的行，    />标注单词的结尾
^ 匹配的字符串在行首
$ 匹配的字符串在行尾


7、查看文件某些行的内容
sed -n '190,196p' song.txt        ### 查看文件song.txt的第190到196行

8、系统
uname -a  ## 查看内核/操作系统/CPU信息

head -n 1 /etc/issue       ## 查看issue文件的第一行，即操作系统版本

cat /proc/cpuinfo         ##  查看CPU信息

hostname            ### 查看计算机名

lspci -tv           ### 列出所有PCI设备

lsusb -tv           ### 列出所有USB设备

lsmod               ###  列出加载的内核模块

env                 ###  查看环境变量

9、资源
free -m            ##  查看内存使用量和交换区使用量

df -h              ##  查看各分区使用情况

du -sh <目录名>    ##  查看指定目录的大小

grep MemTotal /proc/meminfo    ## 查看内存总想

grep MemFree /proc/meminfo     ##  查看空闲内存量

uptime       ## 查看系统运行时间、用户数、负载

cat /proc/loadavg          ##  查看系统负载 

10、磁盘和分区
mount | column -t          ##  查看挂接的分区状态

fdisk -l                   ##  查看所有分区

swapon -s                  ##  查看所有交换分区

hdparm -i /dev/hda          ## 查看磁盘参数(仅适用于IDE设备)

dmesg | grep IDE              ##  查看启动时IDE设备检测状况

11、网络
ifconfig            ## 查看所有网络接口的属性

ifconfig eth0       ## 查看网卡eth0的信息，包括本机地址，掩码，广播地址，MAC地址，MTU等

ping -b 206.168.112.127   ## 针对上面找出的广播地址进行ping操作，可以得到局域网内所有其他机器的地址

iptables -L         ##  查看防火墙设置

route -n            ## 查看路由表

netstat -lntp       ##  查看所有监听端口

netstat -antp       ##  查看所有已建立的连接

netstat -s          ##  查看网络统计信息

netstat -ni     ## -i提供网络接口的信息：一般为eth0, lo(loopback) -n标志输出数值地址，而不是把输出反向解析成名字 

netstat -nr     ## -r展示路由表

netstat -a      ##  查看套接字情况


用linux设置iptables做网关，进行port forward
基本信息：
web site ip port: 192.168.12.50 80 (windows IIS server  or linux apache)
gateway public ip(eth0): 210.211.22.20, private ip(eth1): 192.168.12.10 (centos 5.2)
现在我们希望访问http://210.211.22.20 即可访问http://192.168.12.50:80.   ### 前者是外网IP，后者是内网服务器IP
过程如下：
vi /etc/sysctl.conf
修改设置：
net.ipv4.ip_forward = 1          ### 改为允许本机进行转发
退出保存，执行：
sysctl -p
修改转发tables：
#nat表，PREROUTING链，设置对eth0的目标端口是80的tcp协议，放到DNAT，forward到192.168.12.50:80   
iptables -A PREROUTING -t nat -i eth0 -p tcp --dport 80 -j DNAT --to 192.168.12.50:80
#filter表，对接到的eth0的，从eth1转到192.168.12.50:80 
iptables -A FORWARD -p tcp -i eth0 -o eth1 -d 192.168.12.50 --dport 80 -j ACCEPT
#但是，也要做nat变换，维护一份映射表，从eth1送出时采用内网地址，回来时变为公网地址。否则外网会收不到回复。
iptables -A POSTROUTING -t nat -j MASQUERADE -o eth1
设置完毕，执行service iptables save

设置mssql数据库的代理。
#1.在nat表设置PREROUTING链，这里没有 -o选项。
iptables -t nat -A PREROUTING -p tcp -i eth0  --dport 1433 -j DNAT --to 192.168.12.123:1433
#2.设置filter表
iptables -A FORWARD -p tcp -i eth0 -o eth1 --dport 1433 -d 192.168.12.123 -j ACCEPT
#3.nat变换，如果有了就可以不设置
iptables -t nat -A POSTROUTING -j MASQUERADE -o eth1


iptables 


12、进程
ps -ef               ##  查看所有进程

top                  ##  实时显示进程状态

13、用户
w                    ##  查看活动用户

id <用户名>          ##  查看指定用户信息

last                 ##  查看用户登录日志

cut -d: -f1 /etc/passwd        ## 查看系统所有用户

cut -d: -f1 /etc/group         ## 查看系统所有组

crontab -l             ## 查看当前用户的计划任务

13、服务
chkconfig --list          ## 列出所有系统服务

chkconfig --list | grep on   ## 列出所有启动的系统服务

14、程序
rpm -qa    ## 查看所有安装的软件包


15、cut命令
cut是以每一行为一个处理对象的，怎么告诉cut我想定位到的剪切内容呢？
cut命令主要是接受三个定位方法：
第一，字节（bytes），用选项-b  例如：who | cut -b 3### 显示who的每行输出的第三个字符  cut -b 3-5,8 获得第3,4,5,8字符
第二，字符（characters），用选项-c  对ASCII码来说跟上面没区别，对中文字符来说有区别
第三，域（fields），用选项-f    cat /etc/passwd | head -n 5 | cut -d: -f1  ## -d设置间隔符号为: -f设置我要取的域

16、文件操作
rm  删除相关的命令
当一个目录下有大量文件时，直接用rm -f *.log会收到出错信息，提示参数列表太长
通过 rm -f a*.log可以分批删除，但是一类一类的定点删除太麻烦
解决办法：  ls *.log | xargs rm -f      ### 先把内容做成管道，再通过管道一个个传送给rm进行删除
或者使用：  rm -fr (路径/要删文件)

cp 文件复制相关
cp [-adfilprsu] src1,src2,...dest    ###  将多个源文件拷贝到目的文件夹

17、使用 tar 压缩/解压文件


-c: 建立压缩档案
-x：解压
-t：查看内容
-r：向压缩归档文件末尾追加文件
-u：更新原压缩包中的文件
这五个是独立的命令，压缩解压都要用到其中一个，可以和别的命令连用但只能用其中一个。下面的参数是根据需要在压缩或解压档案时可选的。

-z：有gzip属性的
-j：有bz2属性的
-Z：有compress属性的
-v：显示所有过程
-O：将文件解开到标准输出

下面的参数-f是必须的
-f: 使用档案名字，切记，这个参数是最后一个参数，后面只能接档案名。

压缩
tar -zcvf myfile.tar.gz * ## 将当前目录下所有文件打包成myfile.tar后，再用gzip压缩，生成一个gzip压缩包，命名为myfile.tar.gz

解压
tar -zxvf myfile.tar.gz    ##  解压gzip压缩过的包


                   ##################    例子shell小程序           #############
循环杀死进程的小程序：	
合并成一个句的版本：
ps -ef | grep DataServer | grep -v grep | awk '{print $2}' | xargs kill -9
###  直接用pkill杀进程：  pkill -9 DataServer 或者 kill all DataServer

ps -ef | grep kpdf | grep -v grep | awk '{print $2}' | while read pid           ### kpdf是要杀死的进程
do
	kill -9 $pid
done
上述内容存为一个shell脚本，然后在crontab里面设置
*/15 * * * *  /root/kill.sh
				   
				   
. /etc/profile  #如果要把这个shell脚本放到crontab中，这句不能少，用来初始化程序环境变量，这是root用户的环境变量文件
PNAME="程序名字"    #引号不能少，因为程序名字后面可能会有空格和参数
PATHNAME=程序所在文件夹绝对路径
###  语句解释: grep -v grep 过滤掉包含有grep字符的行
###  
LENGTH=`ps -ef | grep "$PNAME" | grep -v grep |cut -b 49-200|wc -c `  #引号不能少，同上
if test $LENGTH -eq 0
then
cd $PATHNAME
nohup $PNAME >/dev/null  &
fi


chgrp  -R  groupname  filename
chown  -R  ownername[:groupname]  filename
chmod  -R  777 filename
cp src dest

/etc/services   ## 存储了本服务器完整的端口分配表
/etc/shadow     ## 账号管理的文件
/etc/group      ## 用户组组名，使用chgrp修改文件所述用户组时，必须是本文件里存在的组名
/etc/passwd     ## 用户信息，使用chown修改文件所有者时，必须是本文件里存在的用户名