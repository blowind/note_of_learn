#!/bin/sh  -

###  ����ĺ��߱�ʾû��shellѡ����ڰ�ȫ����

###  ѡ���ǿ��п��޵ģ�����Ҫ������ѡ����Ժϲ�

myvar = this_is_a_var         # ��������
echo $myvar    ##  ��ȡ����

who | grep $1  ## ��ȡ�����ű��Ĳ���������10Ҫд�� $(10)

sh -x script.sh   ### �������д�ִ�и��ٹ���
set -x     ### �ڽű����ִ�и��ٹ���
set +x      ###  �ر�ִ�и��ٹ���

tr -d '\r' < dos-file.txt > UNIX-file.txt   ####  ��������ض���
tr -d '\r' < dos-file.txt | sort > UNIX-file.txt  ###  �ܵ���ʹ��
/dev/null           ###  λͰ


BRE����������ʽƥ�����
.  ƥ���κε����ַ�
*  ƥ��ǰ��һ���ַ������ⳤ��    .* ��ʾƥ����һ�ַ������ⳤ��
^  ��ͷƥ��
$  ��βƥ��
[...]   ƥ�䷽�����ڵ���һ�ַ�
[^...]   ƥ�䲻�ڷ������ڵ���һ�ַ�
[:alnum:]  ƥ�����ֺ��ַ�
[:alpha:]  ƥ����ĸ�ַ�
[:digit:]  ƥ�������ַ�
[:lower:]  ƥ��Сд��ĸ�ַ�
[:upper:]  ƥ���д��ĸ�ַ�
[:xdigit:] ƥ��ʮ����������
[]*\.-]    ƥ�� ] * \ . - �������
\{n,m\}    ������ʽ��ƥ������ǰ���������ʽ���ظ��������䣬��Сn�����m��
\{n\}    ������ʽ��ƥ������ǰ���������ʽn��
\{n,\}    ������ʽ��ƥ������ǰ���������ʽ����n��
\( \)      ��������֮���ģʽ�洢�ڱ����ռ䣬���9��������ģʽ��ͨ��ת������ \1 �� \9 ȡ��

�������ã�\(["']\).*\1   ###   ƥ������ 'foo'  �������������������ַ���  "

ERE��չ������ʽƥ�����
+  ƥ��ǰ���һ������ʵ��
?  ƥ��ǰ����������ʵ��
|  ����ƥ�䣬ƥ��ǰ�����һģʽ
[\[\-\]\\]      ƥ�� [ - ] \ �ĸ�����
{n, m}         ������ʽ
\< \>   ����ƥ�䣬 ����  \<chop  ƥ����chop��ͷ�ĵ��ʣ� chop\> ƥ����chop��β�ĵ���

^abcd|edgh$  ƥ�� abcd��ͷ ���� edgh��β ����
^(abcd|edgh)$  ƥ������� abcd ���� edgh ����



���echo
echo -n "Enter your name:" ### ʡ�Խ�β���з�

��� tr -c -d -s 
�û�ת���ַ�
-c ȡԴ�ַ��ķ���
-d �Ա�׼����ɾ��Դ�ַ�������ַ���������ת��
-s Ũ���ظ����ַ�

���stty
���������ն˵ĸ�������
stty -echo  ��ӡ���ַ�����ʾ
stty echo    ���ַ���ӡ�����ַ����ܣ����������������

���grep
-e  ָ�����ŵĲ���Ϊģʽ
-f  ָ���ӽ��ŵĲ����ļ��ж�ȡ���е�ƥ��ģʽ
-i  ƥ��ʱ���Դ�Сд
-l  �г�ƥ��ģʽ���ļ����ƣ������Ǵ�ӡƥ�����
-v  ��ʾ��ƥ��ģʽ����
-E  ������չ������ʽegrep�Ĺ���������
-F  ���ÿ���������ʽfgrep�Ĺ��������У������ڲ���Ԫ�ַ������ƥ�䣬���У���
grep -v '^$' infile | outfile   ȥ���ļ�������п���

���getconf
�õ�POSIX����ķ����ͳ���������  getconf RE_DUP_MAX

���sed
�����ڴ������������ı��滻��û��e��fѡ��ʱ��һ���������޸�������ʽ
sed -e 's/foo/bar/g' -e 's/chicken/cow/g' myfile.xml > myfile2.xml
-e  ָ���������Ĳ���Ϊ�޸�������ʽ
sed -f fixup.sed myfile.xml > myfile2.xml
-f  ָ���ӽ��ŵĲ����ļ��ж�ȡ�޸�������ʽ
sed 's/Tolstoy/Camus/g' < example.txt
#####    ȫ���滻�����Ľ綨�������  g
sed 's/Tolstoy/Camus/2' < example.txt
#####    �����Ľ綨���ϼ�������n��ʾ�滻��n�����ֵ�ƥ��
sed 's/:.*//' /etc/passwd       ɾ����һ��ð��֮������ж���
����/��Ҳ������;�����綨��
find /home/tolstoy -type d -print |          ###  Ѱ������Ŀ¼
	sed 's;/home/tolstoy;/home/lt;' |        ##  �޸����ƣ��˴��ָ�����;
		sed 's/^/mkdir /'    |               ###  ����mkdir����
		   sh -x                             ###  shell����ģʽִ��
		   
sed 's/Atlanta/&, the capital of the South/' < atlga   ###  &���÷�����ʾ������ŵ�����

sed -n '/<HTML>/p' *.html        ###   -n��ʾ�رմ�ӡ�����p��ʾ��ӡ������˴���ʾ����ӡ��<HTML>����

ƥ���ض��У�Ȼ������s///�����滻��
/oldfunc/ s/$/# XXX: migrate to newfunc/    ####  ֻ�޸ĺ���oldfunc���У��ڸ��н�β����ע��
/Tolstoy/  s//& and Camus/g        ####   �ҵ�ָ���У���ģʽָʹ��ǰ���������ʽ��Ȼ���������
sed -n '10,42p' foo.xml    ###  ����ӡ10~42��
sed '/foo/,/bar/ s/baz/quux/g'   ###  ȫ���滻  foo  �� bar  ֮��ƥ����У� ǰ����ƥ����Ѱ�����������
sed '/used/!s/new/used/g'   ###  ��û��used����������new�滻Ϊused��!֮��Ҫ�ſո�


2>&1   ���������������뵽��������


forѭ����أ�

for i in $(seq 10)
for i in `ls`
for i in $*; do
for i in *.txt
for i in $(ls *.txt)
for in�����` `��$( )���ã�����` `��$( )�Ľ����к�Ϊһ�е�ȱ�ݣ�ʵ���Ǻ�Ϊһ���ַ���
============ -_- ==============for num in $(seq 1 100)

LIST = " rootfs usr data data2"
for d in $LIST; do
��for in����Զ����ַ������ո���������ԣ��Զ��Ŀ¼����

for i in {1..10}

arr = ("a" "b" "c")
echo "arr is (${arr[@]})"
for i in ${arr[@]}
do echo "$i"
done

for i in $*;
do echo $i
done

for File in /proc/sys/net/ipv4/conf/*/accept_redirects;
do echo $File
done

for i in f1 f2 f3; 
do echo $i
done

for((i=1;i<=10;i++));  ###  ����ѭ������
do
	echo $(expr $i \* 4);
done;




################################         linux�³��ù���˵��        ################################ 
�޸��û�����ʹ�õľ�����ƣ�Ĭ��ֵ��1024

1. ulimit -a �鿴��ǰ�û����ļ�������� 
2. �û�����ľ���������޸ġ�  
�޸� /etc/security/limits.conf ��������Ĵ��룺  
�û���(������*��ʾ�����û�)  soft nofile 65535    
�û��� hard nofile 65535   
���������ƣ�һ����soft�����ƣ�����Ŀ���������Ƶ�ʱ��ϵͳ�����warning���棬���ǴﵽhardӲ���Ƶ�ʱ��ϵͳ���ܾ������쳣�ˡ�  
�޸�֮�������Ҫ����shell��Ч��
3. ϵͳ����ľ���������޸ġ�  
sysctl -w fs.file-max 65536  
����  
echo "65536" > /proc/sys/fs/file-max  
������������ͬ�ģ�ǰ�߸��ں˲���������ֱ���������ں˲����������ļ�ϵͳ��procfs, psuedo file system���϶�Ӧ���ļ����ѡ�  
���������������鿴�µ�����  
sysctl -a | grep fs.file-max  
����  
cat /proc/sys/fs/file-max  
�޸��ں˲���  
/etc/sysctl.conf  
echo "fs.file-max=65536" >> /etc/sysctl.conf  
sysctl -p  
�鿴ϵͳ������ ���cat /proc/sys/fs/file-max    
�鿴����ϵͳĿǰʹ�õ��ļ�����������cat /proc/sys/fs/file-nr   
�鿴ĳ�����̿�����Щ��� ��lsof -p pid    
ĳ�����̿��˼������ ��lsof -p pid |wc -l    
Ҳ���Կ���ĳ��Ŀ¼ /�ļ���ʲô����ռ����,��ʾ�Ѵ򿪸�Ŀ¼���ļ������н�����Ϣ ��lsof path/filename 



--�鿴ϵͳĬ�ϵ�����ļ��������ϵͳĬ����1024
ulimit -n

----�鿴��ǰ���̴��˶��پ����
lsof -n|awk '{print $2}'|sort|uniq -c|sort -nr|more


---�鿴��ǰϵͳһ������֧�ִ�����ļ������
cat /proc/sys/fs/file-max