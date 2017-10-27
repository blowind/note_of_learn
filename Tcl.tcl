Unix上健壮的开头代码

#!/bin/sh
# tcl ignores the next line but 'sh' doesn't 
exec wish "$0" "$@"

#!/bin/sh
# Tcl ignores the next line but 'sh' doesn't 
exec wish "$0" ${1+"$@"}

#!/usr/bin/env tclsh
puts "The command name is \"$argv0\""   ;##  存储tcl脚本名
puts "There were $argc arguments: $argv"  ;## 存储命令行参数个数  列表形式存储的命令行参数
puts "Your home directory is $env(HOME)"  ;##  存储环境变量


格式细节：
1.在写if for foreach while等命令时通常会跟一个{布尔表达式}，但是注意的是if 和{}之间要有一个空格，否则可能会提示出错。
2.tclsh中花括号不能独立成行，比如
if { 表达式1 }
{
代码体
}
这样的格式使不能通过编译的，必须写成
if { 表达式1 } {
代码体
}
这种格式才行。

                    #############       基本操作         #############
					
set a 44    ;###  赋值
expr $a*4     ;###  读取变量值
set b [expr $a*4]    ;###  命令替换，方括号内内容作为独立脚本处理，其结果替换到方括号位置'

set entities {            ###  定义字典 
	& &amp;
	' &spos;
	> &gt;
	< &lt;
	\" &quot;
}


{}  里面的内容作为纯tcl文本处理，所以里面的内容不执行替换
expr 会把后面的内容先替换，再作为脚本处理

三种替换：
set kgrams 20; expr $kgrams*1.3;     ## 变量替换
set kgrams 20; set lbs [expr $kgrams*2.2];    ## 命令替换  []括起来的都会当做脚本执行
set msg Eggs:\ \$2.18/dozen\n;          ##  反斜线替换，其中空格，$，回车换行要转义

.canvas configure -width ${size}m  ;# 替换后接字符的情况

参数展开：
file delete {*}[glob *.o]    ;###   删除所有.o结尾的文件
;###如果不加  {*}  整个 a.o b.o c.o 会作为完整的文件名返回，此时file delete会找不到整个文件

eval file delete [glob *.o]   ;### 同上面的功能，eval的作用是加一次解析
;###  eval把所有单词连接起来，单词之间用空格隔开，然后整个作为Tcl脚本处理

Tcl替换规则：
1、Tcl解析一条命令时，只从左向右解析一次，进行一轮替换，每个字符都只会被扫描一次
2、每个字符只会发生一层替换，不会对替换后的结果再进行一次扫描替换


关联数组：  数组名和元素名都是任意的字符串，Tcl中数组是无序的数据结构
set earning(January) 97966
#   此处数组名是earning  元素名是January 其他语言中元素名一般都是数字，即索引号
#   所有元素名都是字符串  earning(1)  和  earning(1.0) 是不同的元素 

set capital(New\ Jersey) Trenton    ;## 在元素名中使用空格
set "capital(South Dakota)" Pierre  ;## 同上
set state "New Mexico"; set capital($state) "Santa Fe" ; ## 同上

多维数组：
set matrix(1,1) 140   ;## 其实是两个字符串索引1    
#  注意索引之间最好不要有空格，否则指向不一样
set matrix(1,2) 218   ;##
set i 1; set j 2; set cell $matrix($i,$j)  ;##读取多维数组内容 

                    #############       基本命令         #############

命令：append
append varName value ?value ...?
#  把每个value参数依次添加到变量varName中，不会自动加空格，需自己显示加入
append x $piece    ;#连接字符串
set x "$x$piece"   ;#同上，但是没上面的方法效率高

命令：incr
incr varName ?increment?
#  将变量的值加上increment，两个值都必须是整数，缺省increment时默认为1，increment可正可负
incr x 12

命令：set
set varName ?value?
#  给变量varName赋值

命令：unset
unset ?-nocomplain? ?--? varName ?varName varName ...?
#  删除varName指定的变量，返回一个空字符串
#  如果变量不存在，没有设置-nocomplain时会报警
unset a earnings(January) b   ;#  删除变量a b 和数组元素 January

命令：format
format "The square root of 10 is %.3f" [expr sqrt(10)]
#  第一个是字符串输出格式，后面是输出参数
代码示例：
puts "Integer ASCII"
for {set i 95} {$i <= 101} {incr i} {
	puts [format "%4d      %c" $i $i]
}
另一种写法：
puts "Integer ASCII"
for {set i 95} {$i <= 101} {incr i} {
	puts [format "%1$4d     %1$c" $i]
}

命令：scan
scan string format varName ?varName varName ...?
#  解析string中由format指定的字段，把与%序列匹配的值存入名为varName的变量中
#  返回值是成功解析的字段个数
#  如果没有提供varName变量，返回值就是匹配的字段列表
scan "16 units, 24.2% margin" "%d units, %f" a b    ;# a=16  b=24.2
#  第一个参数是待解析的字符串，第二个参数是控制解析方式的格式字符串
#  其他参数是用来存储转换出的值的变量

命令：array exists
array exists arrayName
#  判断名为arrayName的数组是否存在，返回布尔值

命令：array get
array get arrayName ?pattern?
#  返回包含名为arrayName的数组的内容的字典
set a(head) hat
set a(hand) glove
set a(foot) shoe
set apparel [array get a]
#  得到 foot shoe head hat hand glove   可以看出get得到键值对

命令：array set
array set arrayName dictionary
#  将dictionary的内容并入名为arrayName的数组中。返回空字符串
array set b $apparel
lsort [array names b]
#  得到 foot hand head

命令：array names
array names arrayName ?mode? ?pattern?
#  返回名为arrayName的数组的元素名列表，有多少返回多少，带空格的元素名用大括号括起来
foreach i [array names a] {
	if {($a($i) == "") || ($a($i) == 0)} {
		unset a($i)
	}
}
#  删除内容为空的键值对

命令：array size
array size arrayName
#  返回名为arrayName的数组中的元素个数

命令：array statistics
array statistics arrayName
#  返回名为arrayName数组的内部设置的描述

命令：array unset 
array unset arrayName ?pattern?
#  移除名为arrayName数组中所有符合pattern的元素，返回空字符串
#  没设置pattern，则整个数组为空


                    #############       字符串命令         #############
命令：regexp
regexp ?options? exp string ?matchVar? ?SubVar SubVar ...?
#  检查正则表达式exp是否与string全部匹配或者部分匹配
#　如果匹配，匹配范围的信息存入matchVar 和 SubVar中
#  建议总是把正则表达式括在大括号中
#  -start num  选项  指定查找匹配开始的位置
#  -all  选项告诉regexp在字符串中查找尽量多次的匹配，返回总的匹配次数
#  -inline  让regexp把匹配变量返回为一个数据列表
#  --   习惯上用其作为option的结束标志，以免发生错误
regexp {^[0-9]+$}  510  ;# 匹配纯数字，得到1
regexp {([0-9]+) *([a-z]+)} "Walk 10 km" a b c   ;#  a=10km, b=10, c=km
regexp -indices {([0-9])+ *([a-z]+)} "Walk 10 km" ;# a="5 9", b="5 6", c="8 9"

命令：regsub
regsub ?options? exp string subSpec ?varName?
#  检查正则表达式exp是否与string全部匹配或者部分匹配
#  把string复制到名为varName的变量中，同时把string中匹配部分替换为subSpec
#  不给定varName，返回值就是替换的结果
#  不管替换是否成功，给出varName的话都会被赋值，匹配失败赋值原字符串
#  -all 全局替换
#  替换字符包含&或者\0，其中的&或\0会被替换为匹配的子字符串
regsub there "They live there lives" their x ;#  x="They live their lives"  同时返回1
regsub a ababa zz x   ;#  x="zzbaba"
regsub -all a ababa zz x  ;# x="zzbzzbzz"
regsub -all -- a|b axaab && x  ;#  x="aaxaaaabb"
regsub -all -- (a+)(ba*) aabaabxab {z\2} x   ;# x="zbaabxzb"，注意到默认是贪婪匹配

命令：string range
string range string first last
#  返回string中从first(包含)到last(包含)之间所有的子字符串
string range "Sample string" 3 7  ;#得到  ple s

命令：string repeat
string repeat string count
#  返回把string重复count次所得到的字符串
string repeat "abc" 2   ;#  得到 abcabc

命令：string replace
string replace string first last ?newstring?
#  把string中从first(包含)到last(包含)之间所有的字符移除，设定了newstring则为置换
string replace "San Diego, California" 4 8 "Francisco"

命令：string map
string map ?-nocase? mapping string
#  基于mapping字典对string进行子字符串替换，返回新的字符串
#  凡在string中出现的mapping关键字都替换为字典中对应的值

命令：string tolower 
string tolower string ?first? ?last?
#  大写全换小写

命令：string totitle
string totitle string ?first ?last
#  转成标题格式，即第一个字符大写，其他全小写

命令：string toupper
string to toupper string ?first ?last
#  大写全换小写

命令：string trim/trimleft/trimright
string trim/trimleft/trimright string ?chars?
#  去掉开头/结尾的所有chars，默认为空白
string trim aaxxxbab abc   ;# 得到 xxx

命令：string compare
string compare ?-nocase ?-length num? string1 string2
#  比较string1和string2，-1、0,、1 分别对应 小于，等于，大于
#  -nocase忽略大小写，-length从头开始的前num个字符进行比较
string compare Michigan Minnesota

命令：string equal
string equal ?-nocase ?-length num? string1 string2
#  简单比较，严格相同返回1，否则返回0，可设置对前length个字符进行比较
string equal -length 3 catalyst cataract   ;# 得到1

命令：string match
string match ?-nocase? pattern string
#  根据通配符样式匹配规则(*, ?, [], \)比较，如果string与pattern匹配则返回1
string match a* bat          ;# 得到0
string match {[ab]*} brown   ;# 得到1
string match {*\?} "What?"   ;# 得到1

命令：string first
string first string1 string2 ?startIndex?
#  返回string2中第一次出现与string1完全相同的子字符串的开头字符的索引，无则返回-1
#  指定startIndex，则从string2的索引为startIndex的字符开始搜索
string first th "There is the tub" 2    ;#  得到 9

命令：string last 
string last string1 string2 ?lastIndex?
#  返回string2中与string1精确匹配的最右边的子字符型的开头字符的索引
string last th "There is the tub"

命令：string index 
string index string charIndex
#  返回string中所因为charIndex的字符，没有返回空字符串，索引从0开始
string index "Sample string" 3   ;#  得到 p

命令：string length
string length string
#  返回string中的字符个数
string length "Sample string"    ;#得到  13

命令：string is digit/control/Alnum/alpha/list/lower
#  确定字符串的类型是否正确，正确返回1，否则返回0
string is digit 1234
string is control ""   ;# 返回1
string is control -strict ""   ;# 返回0
string is digit -failindex idex "123c5"  ;#  idex设置为3


                    #############       列表操作         #############
					
命令：in：
#  字符串是列表元素时，返回1，否则返回0
if {"Los Angeles" in $cities} {}   ;#  查看Los Angeles是否是cities列表的一个元素

命令：ni：
#  字符串不是列表元素时，返回1

命令：list 
list ?value value ...?
#  返回一个列表，其元素就是value参数
list {a b c} {d e} f {g h i}     ;#  得到一个四元素的列表

命令：concat
concat ?list list ...?
#  将多个列表合并成一个列表(各个list作为结果列表的元素)
concat {a b c} {d e} f {g h i}    ;#  得到一个9元素的列表
concat {a b} {c {d e f}}          ;#  得到一个4元素的列表

命令：lrepeat
lrepeat number value ?value...?
#  value作为元素，重复number次得到列表并返回
lrepeat 4 a b c       ;#  得到  a b c a b c a b c a b c 

命令：join
join list ?joinString?
#  把joinString作为分隔符，把列表匀速串接起来，默认为空格

命令：lappend
lappend varname value ?value...?
#  把value作为列表元素添加到varName变量中，然后返回变量的新值

命令：lassign
lassign list varName ?varName...?
#  把list中的元素顺次赋给名为varName的变量，变量多则设为空
#  元素多则由多的组成新列表作为返回值

命令：lindex
lindex list ?index ...?
#  使用list的第index个元素。使用多个索引值时，可以是分别独立的变量，也可以是一个列表
#  每个索引一次对前一个索引的结果进行操作，从而访问嵌套的列表元素
lindex {John Anne Mary Jim} 1   ;#  得到 Anne
lindex {a b {c d e} f} 2        ;#  得到 c d e   ;列表可嵌套的层数没有限制
lindex {{a b} {c {d e f}} g} 1 1 2   ;#  得到f   嵌套访问

命令：linsert
linsert list index value ?value...?
#  将所有的value作为列表元素插入list中第index个元素之前。返回新列表

命令：llength
llength list
#  返回list中元素个数
llength {a b c d}    ;#  4
llength a            ;#  1

命令：lrange
lrange list first last
#  返回一个列表，由list列表中first到last的元素组成
lrange {a b {d d} e} 1 3       ;#  得到  b {c d} e


命令：lreplace
lreplace list first last ?value ...?
#  把list中first到last的元素替换为0个或多个元素

命令：lsearch
lsearch ?option...? list pattern
#  在list中搜索与pattern匹配的一个或多个元素
#  option选项控制匹配方式(-exact -glob -regexp)
#  -inline  返回元素值，否则返回索引
#  -all  搜索所有匹配  否则搜索最先出现的匹配
#  默认进行通配符匹配，返回第一处匹配的索引，没有匹配时返回-1

命令：lset
lset varName ?index ...? newValue
#  将varName列表中由index索引指向的元素值设为newValue，返回存储在varName中的新列表

命令：lsort
lsort ?option..? list
#  对list中元素排序，返回排序后的列表。

命令：split
split string ?splitChars?
#  以splitChars分割string，组成一个新列表返回


                    #############       子函数操作         #############
命令：proc
proc test {argv} {}
#  定义一个子例程，test是名字，接参数列表{}和子例程实体