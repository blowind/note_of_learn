
1���鿴�ڴ�ʹ��������
free  ����ڴ�ʹ�����         free -m   ���ڴ�ʹ�������MΪ��λ��ʾ
������ֶκ��壺
total:�ܼ������ڴ�Ĵ�С��
used:��ʹ�ö��
free:�����ж��١�
Shared:������̹�����ڴ��ܶ
Buffers/cached:���̻���Ĵ�С��

watch -n 2 -d free   ÿ����ִ��freeһ�Σ�ִ��ǰ�����Ļ����ͬ��λ����ʾ���ݡ� -n ����ˢ�� -d ÿ����ʾ��ͬ�ĵط�

/proc/meminfo            �Ƚϸ��ӵĿ��ڴ�ʹ�õķ���

top  ��̬��ʾϵͳ�������̵���Դռ�����������task�����CPUʹ���ʣ�Memʹ���ʣ�swap������ʹ�����
��һ������  
12:38:33 up 50 days, 23:15,  7 users,  load average: 60.58, 61.14, 61.22(�ֱ�Ϊ1���ӣ�5���ӣ�15���ӵ�ƽ������)
����uptime���
13:22:30 up 8 min,  4 users,  load average: 0.14, 0.38, 0.25   (uptime��ʾ���)


vmstat 5 5           ��5��ʱ���ڽ���5�β���
http://blog.csdn.net/jamesjiangqian/article/details/6782548

2���鿴CPUʹ�����
/proc/cpuinfo            


3��ͳ���ļ����µ��ļ�����
ls -l | wc -l                ### �Ȱ������ÿ���ļ�����ͳ������   ls -al��ʾ������

find . -type f -print | wc -l       ##  ������ǰ�ļ����µ�file����ӡ


4����������ӵ�ַ
ifconfig eth1 add 2001:250:1800:1::88/64         ###  ����ipv6��ַ��������64 ����������


5�����·��
route -A inet6 add default gw 2001:250:1800:1::1
ͬʱҪ�޸��ļ���
vi /etc/sysconfig/network
NETWORKING_IPV6=yes


6��������Ŀ¼�а���ĳ�ַ������ض��ļ�
find ./src -name '*.ec' -exec grep -i niuc {} /; -print     ###  ����./srcĿ¼�°���"niuc"������.ec�ļ�

grep -R --include="*.cpp" key dir       ### ��dirĿ¼�µݹ��������.cpp�ļ��еĹؼ���key
grep -r : ��ȷҪ��������Ŀ¼
grep -d skip :������Ŀ¼
grep -i pattern files : �����ִ�Сд��������Ĭ����������ִ�Сд
grep -l pattern files : ֻ�г�ƥ����ļ���
grep -L pattern files : ֻ�г���ƥ����ļ���
grep -w pattern files : ֻƥ���������ʣ��������ַ�����һ����(��ƥ�䡰magic�������ǡ�magical��)
grep pattern1 | pattern2 files : ��ʾƥ��pattern1����pattern2����
grep pattern1 file | grep pattern2 : ��ʾ��ƥ��pattern1��ƥ��pattern2���У���ʵ���ùܵ�������grep�ϲ�����
grep '/<man'  : ƥ����manΪ����ǰ������ĸ���У� /<��ע���ʵĿ�ʼ
grep 'man/>'  : ƥ����manΪ���ʽ�β���У�    />��ע���ʵĽ�β
^ ƥ����ַ���������
$ ƥ����ַ�������β


7���鿴�ļ�ĳЩ�е�����
sed -n '190,196p' song.txt        ### �鿴�ļ�song.txt�ĵ�190��196��

8��ϵͳ
uname -a  ## �鿴�ں�/����ϵͳ/CPU��Ϣ

## �鿴�����ϵͳ�汾
lsb_release -a                  ## ����һ��centos�ײ���Ч
cat /etc/redhat-release         ## ��������centos�ײ���Ч
rpm -q centos-release ���� rpm -q redhat-release    ## �����������ݶ�Ӧϵͳѡ���Ӧ���centos�ײ���Ч
cat /proc/version               ## �鿴��ǰcentos �汾��redhat��Ӧ�İ汾������

## �鿴�м����߼�CPU���Լ�CPU�ͺ�
cat /proc/cpuinfo | grep name | cut -f2 -d: | uniq -c 
      8  Intel(R) Xeon(R) CPU E5620  @ 2.40GHz


head -n 1 /etc/issue       ## �鿴issue�ļ��ĵ�һ�У�������ϵͳ�汾

cat /proc/cpuinfo         ##  �鿴CPU��Ϣ

hostname            ### �鿴�������

lspci -tv           ### �г�����PCI�豸

lsusb -tv           ### �г�����USB�豸

lsmod               ###  �г����ص��ں�ģ��

env                 ###  �鿴��������

9����Դ
free -m            ##  �鿴�ڴ�ʹ�����ͽ�����ʹ����

df -h              ##  �鿴������ʹ�����

du -sh <Ŀ¼��>    ##  �鿴ָ��Ŀ¼�Ĵ�С

grep MemTotal /proc/meminfo    ## �鿴�ڴ�����

grep MemFree /proc/meminfo     ##  �鿴�����ڴ���

uptime       ## �鿴ϵͳ����ʱ�䡢�û���������

cat /proc/loadavg          ##  �鿴ϵͳ���� 

10�����̺ͷ���
mount | column -t          ##  �鿴�ҽӵķ���״̬

fdisk -l                   ##  �鿴���з���

swapon -s                  ##  �鿴���н�������

hdparm -i /dev/hda          ## �鿴���̲���(��������IDE�豸)

dmesg | grep IDE              ##  �鿴����ʱIDE�豸���״��

11������netstat
ifconfig            ## �鿴��������ӿڵ�����
ifconfig eth0       ## �鿴����eth0����Ϣ������������ַ�����룬�㲥��ַ��MAC��ַ��MTU��
ping -b 206.168.112.127   ## ��������ҳ��Ĺ㲥��ַ����ping���������Եõ����������������������ĵ�ַ

iptables -L         ##  �鿴����ǽ����

route -n        ## �鿴·�ɱ�
netstat -lntp   ##  �鿴���м����˿�
netstat -antp   ##  �鿴�����ѽ���������
netstat -s      ##  �鿴����ͳ����Ϣ
netstat -ni     ## -i�ṩ����ӿڵ���Ϣ��һ��Ϊeth0, lo(loopback) -n��־�����ֵ��ַ�������ǰ����������������� 
netstat -nr     ## -rչʾ·�ɱ�
netstat -a      ##  �鿴�׽������
netstat -a -tcp ##  �鿴���м���(-a)��tcp�������


12��iptables

iptables -t nat -L      ##  �鿴iptables�����Ƿ�ʹ��

### ������Ŀ��IP��192.168.1.100���˿���80��tcp���ض���8080�˿�
iptables -t nat -I PREROUTING -p tcp --dst 192.168.1.100 --dport 80 -j REDIRECT --to-ports 8080
iptables -t nat -I OUTPUT -p tcp --dst 192.168.1.100 --dport 80 -j REDIRECT --to-ports 8080


��linux����iptables�����أ�����port forward�˿�ת��
������Ϣ��
web site ip port: 192.168.12.50 80 (windows IIS server  or linux apache)
gateway public ip(eth0): 210.211.22.20, private ip(eth1): 192.168.12.10 (centos 5.2)
��������ϣ������http://210.211.22.20 ���ɷ���http://192.168.12.50:80.   ### ǰ��������IP������������������IP
�������£�
vi /etc/sysctl.conf
�޸����ã�
net.ipv4.ip_forward = 1          ### ��Ϊ����������ת��
�˳����棬ִ�У�
sysctl -p
�޸�ת��tables��
#nat��PREROUTING�������ö�eth0��Ŀ��˿���80��tcpЭ�飬�ŵ�DNAT��forward��192.168.12.50:80   
iptables -A PREROUTING -t nat -i eth0 -p tcp --dport 80 -j DNAT --to 192.168.12.50:80
#filter���Խӵ���eth0�ģ���eth1ת��192.168.12.50:80 
iptables -A FORWARD -p tcp -i eth0 -o eth1 -d 192.168.12.50 --dport 80 -j ACCEPT
#���ǣ�ҲҪ��nat�任��ά��һ��ӳ�����eth1�ͳ�ʱ����������ַ������ʱ��Ϊ������ַ�������������ղ����ظ���
iptables -A POSTROUTING -t nat -j MASQUERADE -o eth1
������ϣ�ִ��service iptables save

����mssql���ݿ�Ĵ���
#1.��nat������PREROUTING��������û�� -oѡ�
iptables -t nat -A PREROUTING -p tcp -i eth0  --dport 1433 -j DNAT --to 192.168.12.123:1433
#2.����filter��
iptables -A FORWARD -p tcp -i eth0 -o eth1 --dport 1433 -d 192.168.12.123 -j ACCEPT
#3.nat�任��������˾Ϳ��Բ�����
iptables -t nat -A POSTROUTING -j MASQUERADE -o eth1


iptables 


12������
ps -ef               ##  �鿴���н���

top                  ##  ʵʱ��ʾ����״̬

top -H -p 32381      ##  �鿴����32381�и����߳���Դռ�����

pmap -d  5647        ##  �鿴����5647���ڴ�ռ�����

13���û�
w                    ##  �鿴��û�

id <�û���>          ##  �鿴ָ���û���Ϣ

last                 ##  �鿴�û���¼��־

cut -d: -f1 /etc/passwd        ## �鿴ϵͳ�����û�

cut -d: -f1 /etc/group         ## �鿴ϵͳ������

crontab -l             ## �鿴��ǰ�û��ļƻ�����

13������
chkconfig --list          ## �г�����ϵͳ����
chkconfig --list | grep on   ## �г�����������ϵͳ����
chkconfig --level 2345 tomcat on  ## ��2,3,4,5�ĸ��㼶�Ͽ���������tomcat��Ҫ��rpm��װ��

14����дtomcat�����������ű�tomcat
1�����ű��������£�
==============================================
#!/bin/sh
JAVA_HOME=/usr/java/jdk1.8.04-28
CATALINA_HOME=/mnt/apache-tomcat-9.0.1
export JAVA_HOME CATALINA_HOME
exec $CATALINA_HOME/bin/catalina.sh $*
==============================================
2�����޸�tomcat�ű�Ȩ�޺�����
chown root.root tomcat
chmod 755 tomcat
3������tomcat�ű����Ƶ�/etc/rc.d/init.d/Ŀ¼��
4��������tomcat�����Զ�������ֹͣ
chkconfig --level 2345 tomcat on



15��cut����
cut����ÿһ��Ϊһ���������ģ���ô����cut���붨λ���ļ��������أ�
cut������Ҫ�ǽ���������λ������
��һ���ֽڣ�bytes������ѡ��-b  ���磺who | cut -b 3### ��ʾwho��ÿ������ĵ������ַ�  cut -b 3-5,8 ��õ�3,4,5,8�ַ�
�ڶ����ַ���characters������ѡ��-c  ��ASCII����˵������û���𣬶������ַ���˵������
��������fields������ѡ��-f    cat /etc/passwd | head -n 5 | cut -d: -f1  ## -d���ü������Ϊ: -f������Ҫȡ����

16���ļ�����
rm  ɾ����ص�����
��һ��Ŀ¼���д����ļ�ʱ��ֱ����rm -f *.log���յ�������Ϣ����ʾ�����б�̫��
ͨ�� rm -f a*.log���Է���ɾ��������һ��һ��Ķ���ɾ��̫�鷳
����취��  ls *.log | xargs rm -f      ### �Ȱ��������ɹܵ�����ͨ���ܵ�һ�������͸�rm����ɾ��
����ʹ�ã�  rm -fr (·��/Ҫɾ�ļ�)

cp �ļ��������
cp [-adfilprsu] src1,src2,...dest    ###  �����Դ�ļ�������Ŀ���ļ���

17��ʹ�� tar ѹ��/��ѹ�ļ�


18���鿴�����Ϣ

head -n 1 /dev/random         ##  �鿴�ں���ʱ��seed���������Ϣ������ϢҪ�ں�ʱ�䣬������������Ϣ֮����ʱ����
head -n 1 /dev/urandom        ##  �鿴α�����Ϣ��������ʱ�䣬���ʵʱ��


19���鿴��ǰϵͳ�������û�

cat /etc/passwd
cat /etc/shadow

-c: ����ѹ������
-x����ѹ
-t���鿴����
-r����ѹ���鵵�ļ�ĩβ׷���ļ�
-u������ԭѹ�����е��ļ�
������Ƕ��������ѹ����ѹ��Ҫ�õ�����һ�������Ժͱ���������õ�ֻ��������һ��������Ĳ����Ǹ�����Ҫ��ѹ�����ѹ����ʱ��ѡ�ġ�

-z����gzip���Ե�
-j����bz2���Ե�
-Z����compress���Ե�
-v����ʾ���й���
-O�����ļ��⿪����׼���

����Ĳ���-f�Ǳ����
-f: ʹ�õ������֣��мǣ�������������һ������������ֻ�ܽӵ�������

ѹ��
tar -zcvf myfile.tar.gz * ## ����ǰĿ¼�������ļ������myfile.tar������gzipѹ��������һ��gzipѹ����������Ϊmyfile.tar.gz

��ѹ
tar -zxvf myfile.tar.gz    ##  ��ѹgzipѹ�����İ�

18��ʹ��ǩ�����߼���У���(������������CentOS���ײ���Ч)

md5sum   abc.txt     ## MD5У���
sha1sum   abc.txt    ## sha1У���
sha224sum
sha256sum   abc.txt  ## sha256У���
sha384sum
sha512sum

% gpg --import KEYS           ##  KEYS �ļ���Ҫר������
% gpg --verify downloaded_file.asc downloaded_file   ##  asc�ļ���Ҫר������

19���ָ��ļ�
split -b 10m info.log.2018-04-28 -d -a 3 log0428_

-l 10      ָ�����������ָ��ļ����˴�ָ��ÿ10�оͷָ��һ��С�ļ�
-b 10m     ָ���ָ��ÿ���ļ��Ĵ�С��֧��m��k���ֵ�λ���˴�ָ���ָ���ļ���СΪ10MB
-d         ָ���ָ�������õ��ļ�����׺ʹ�����ֱ�ʾ����ָ��ʱĬ����abcdefg��ĸ
-a 3       ָ����������׺�ĳ��ȣ��˴���ʾ3λ������ʽ
log0428_   ָ���ָ���ļ�������ͬǰ׺


20�� ʱ��ת��
date -d "@1279592730"  ## ת����

date +%s       ## ��ʱ�������ʾ��ǰʱ��  1527165067

21���鿴��ǰ�û��ļƻ�����
crontab -l 

22��centos���ñ��ز��ֶ˿�ɨ�蹦��

yum install iptables-services   ##  centos 7 ��Ҫ��װiptables����


23���鿴linuxϵͳ��������
ifstat


24���鿴yum��װ���������Ϣ

yum info nginx  ## �鿴nginx��װ��Ϣ
yum -y update   ## �������а����ı�������ú�ϵͳ����,ϵͳ�汾�ں˶�����
yum -y upgrade  ## �������а������ı�������ú�ϵͳ���ã�ϵͳ�汾�������ں˲��ı� 

24��rpm�������װ�������
rpm -qa    ## �鿴���а�װ�������
rpm -qa | grep tomcat   ## �鿴����tomcat��rpm��װ��Ϣ
rpm -e tomcat --nodeps   ## ɾ��tomcat��װ����������������
rpm -ivh --nodeps --prefix /usr/local  tomcat-9.noarch.rpm    ## ����������İ�װtomcat-9.noarch.rpm������װ��/usr/localĿ¼��

25������û����û���
groupadd  nobody       ## ���nobody�û���
##  ����û�tomcat����tomcat���û�shell����Ϊ/sbin/nologin,  homeĿ¼Ϊ/opt/tomcat/temp, Ⱥ��Ϊnobody
useradd -s /sbin/nologin  -d /opt/tomcat/temp  -c 'Tomcat User' -g nobody tomcat


26��ɱ������
kill -TERM 5166  ## ɱ��pidΪ5166�Ľ���
kill -KILL 5166  ## ɱ��pidΪ5166�Ľ���


27������������
ln -s abc cde  ## ����abc��������





                   ##################    ����shellС����           #############
ѭ��ɱ�����̵�С����	
�ϲ���һ����İ汾��
ps -ef | grep DataServer | grep -v grep | awk '{print $2}' | xargs kill -9
###  ֱ����pkillɱ���̣�  pkill -9 DataServer ���� kill all DataServer

ps -ef | grep kpdf | grep -v grep | awk '{print $2}' | while read pid           ### kpdf��Ҫɱ���Ľ���
do
	kill -9 $pid
done
�������ݴ�Ϊһ��shell�ű���Ȼ����crontab��������
*/15 * * * *  /root/kill.sh
				   
				   
. /etc/profile  #���Ҫ�����shell�ű��ŵ�crontab�У���䲻���٣�������ʼ�����򻷾�����������root�û��Ļ��������ļ�
PNAME="��������"    #���Ų����٣���Ϊ�������ֺ�����ܻ��пո�Ͳ���
PATHNAME=���������ļ��о���·��
###  ������: grep -v grep ���˵�������grep�ַ�����
###  
LENGTH=`ps -ef | grep "$PNAME" | grep -v grep |cut -b 49-200|wc -c `  #���Ų����٣�ͬ��
if test $LENGTH -eq 0
then
cd $PATHNAME
nohup $PNAME >/dev/null  &
fi


chgrp  -R  groupname  filename
chown  -R  ownername[:groupname]  filename
chmod  -R  777 filename
cp src dest

/etc/services   ## �洢�˱������������Ķ˿ڷ����
/etc/shadow     ## �˺Ź�����ļ�
/etc/group      ## �û���������ʹ��chgrp�޸��ļ������û���ʱ�������Ǳ��ļ�����ڵ�����
/etc/passwd     ## �û���Ϣ��ʹ��chown�޸��ļ�������ʱ�������Ǳ��ļ�����ڵ��û���