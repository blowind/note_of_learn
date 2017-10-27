#!/bin/sh  -

###  后面的横线表示没有shell选项，基于安全考虑

###  选项是可有可无的，不需要参数的选项可以合并

myvar = this_is_a_var         # 声明变量
echo $myvar    ##  读取变量

who | grep $1  ## 读取传给脚本的参数，超过10要写成 $(10)

sh -x script.sh   ### 在命令行打开执行跟踪功能
set -x     ### 在脚本里打开执行跟踪功能
set +x      ###  关闭执行跟踪功能

tr -d '\r' < dos-file.txt > UNIX-file.txt   ####  输入输出重定向
tr -d '\r' < dos-file.txt | sort > UNIX-file.txt  ###  管道的使用
/dev/null           ###  位桶


BRE基础正则表达式匹配规则：
.  匹配任何单个字符
*  匹配前面一个字符的任意长度    .* 表示匹配任一字符的任意长度
^  开头匹配
$  结尾匹配
[...]   匹配方括号内的任一字符
[^...]   匹配不在方括号内的任一字符
[:alnum:]  匹配数字和字符
[:alpha:]  匹配字母字符
[:digit:]  匹配数字字符
[:lower:]  匹配小写字母字符
[:upper:]  匹配大写字母字符
[:xdigit:] 匹配十六进制数字
[]*\.-]    匹配 ] * \ . - 五个符号
\{n,m\}    区间表达式，匹配在他前面的正则表达式的重复次数区间，最小n，最大m，
\{n\}    区间表达式，匹配在他前面的正则表达式n次
\{n,\}    区间表达式，匹配在他前面的正则表达式至少n次
\( \)      将两括号之间的模式存储在保留空间，最多9个独立子模式，通过转义序列 \1 至 \9 取用

后向引用：\(["']\).*\1   ###   匹配类似 'foo'  这样被引号引起来的字符串  "

ERE扩展正则表达式匹配规则：
+  匹配前面的一个或多个实例
?  匹配前面的零个或多个实例
|  交替匹配，匹配前后的任一模式
[\[\-\]\\]      匹配 [ - ] \ 四个符号
{n, m}         区间表达式
\< \>   单词匹配， 例如  \<chop  匹配以chop开头的单词， chop\> 匹配以chop结尾的单词

^abcd|edgh$  匹配 abcd开头 或者 edgh结尾 的行
^(abcd|edgh)$  匹配仅仅是 abcd 或者 edgh 的行



命令：echo
echo -n "Enter your name:" ### 省略结尾换行符

命令： tr -c -d -s 
用户转换字符
-c 取源字符的反义
-d 自标准输入删除源字符串里的字符，而不是转换
-s 浓缩重复的字符

命令：stty
用来控制终端的各种设置
stty -echo  打印的字符不显示
stty echo    打开字符打印输入字符功能，即解锁上面的命令

命令：grep
-e  指定接着的参数为模式
-f  指定从接着的参数文件中读取所有的匹配模式
-i  匹配时忽略大小写
-l  列出匹配模式的文件名称，而不是打印匹配的行
-v  显示不匹配模式的行
-E  调用扩展正则表达式egrep的功能来运行
-F  调用快速正则表达式fgrep的功能来运行，仅用于不含元字符的最简单匹配，并行，快
grep -v '^$' infile | outfile   去掉文件里的所有空行

命令：getconf
得到POSIX定义的符号型常数，例如  getconf RE_DUP_MAX

命令：sed
常用于处理输入流的文本替换，没有e和f选项时第一个参数是修改正则表达式
sed -e 's/foo/bar/g' -e 's/chicken/cow/g' myfile.xml > myfile2.xml
-e  指定接下来的参数为修改正则表达式
sed -f fixup.sed myfile.xml > myfile2.xml
-f  指定从接着的参数文件中读取修改正则表达式
sed 's/Tolstoy/Camus/g' < example.txt
#####    全局替换在最后的界定符后加上  g
sed 's/Tolstoy/Camus/2' < example.txt
#####    在最后的界定符上加上数字n表示替换第n个出现的匹配
sed 's/:.*//' /etc/passwd       删除第一个冒号之后的所有东西
除了/，也可以用;等做界定符
find /home/tolstoy -type d -print |          ###  寻找所有目录
	sed 's;/home/tolstoy;/home/lt;' |        ##  修改名称，此处分隔符是;
		sed 's/^/mkdir /'    |               ###  插入mkdir命令
		   sh -x                             ###  shell跟踪模式执行
		   
sed 's/Atlanta/&, the capital of the South/' < atlga   ###  &的用法，表示插入接着的内容

sed -n '/<HTML>/p' *.html        ###   -n表示关闭打印输出，p表示打印输出，此处表示仅打印含<HTML>的行

匹配特定行，然后再用s///进行替换：
/oldfunc/ s/$/# XXX: migrate to newfunc/    ####  只修改含有oldfunc的行，在该行结尾加行注释
/Tolstoy/  s//& and Camus/g        ####   找到指定行，空模式指使用前面的正则表达式，然后加上内容
sed -n '10,42p' foo.xml    ###  仅打印10~42行
sed '/foo/,/bar/ s/baz/quux/g'   ###  全局替换  foo  和 bar  之间匹配的行， 前两个匹配是寻找区间的作用
sed '/used/!s/new/used/g'   ###  将没有used的行里所有new替换为used，!之后不要放空格


2>&1   将错误输出放入混入到正常输入


for循环相关：

for i in $(seq 10)
for i in `ls`
for i in $*; do
for i in *.txt
for i in $(ls *.txt)
for in语句与` `和$( )合用，利用` `或$( )的将多行合为一行的缺陷，实际是合为一个字符串
============ -_- ==============for num in $(seq 1 100)

LIST = " rootfs usr data data2"
for d in $LIST; do
用for in语句自动对字符串按空格遍历的特性，对多个目录遍历

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

for((i=1;i<=10;i++));  ###  基本循环条件
do
	echo $(expr $i \* 4);
done;