Unix�Ͻ�׳�Ŀ�ͷ����

#!/bin/sh
# tcl ignores the next line but 'sh' doesn't 
exec wish "$0" "$@"

#!/bin/sh
# Tcl ignores the next line but 'sh' doesn't 
exec wish "$0" ${1+"$@"}

#!/usr/bin/env tclsh
puts "The command name is \"$argv0\""   ;##  �洢tcl�ű���
puts "There were $argc arguments: $argv"  ;## �洢�����в�������  �б���ʽ�洢�������в���
puts "Your home directory is $env(HOME)"  ;##  �洢��������


��ʽϸ�ڣ�
1.��дif for foreach while������ʱͨ�����һ��{�������ʽ}������ע�����if ��{}֮��Ҫ��һ���ո񣬷�����ܻ���ʾ����
2.tclsh�л����Ų��ܶ������У�����
if { ���ʽ1 }
{
������
}
�����ĸ�ʽʹ����ͨ������ģ�����д��
if { ���ʽ1 } {
������
}
���ָ�ʽ���С�

                    #############       ��������         #############
					
set a 44    ;###  ��ֵ
expr $a*4     ;###  ��ȡ����ֵ
set b [expr $a*4]    ;###  �����滻����������������Ϊ�����ű����������滻��������λ��'

set entities {            ###  �����ֵ� 
	& &amp;
	' &spos;
	> &gt;
	< &lt;
	\" &quot;
}


{}  �����������Ϊ��tcl�ı�����������������ݲ�ִ���滻
expr ��Ѻ�����������滻������Ϊ�ű�����

�����滻��
set kgrams 20; expr $kgrams*1.3;     ## �����滻
set kgrams 20; set lbs [expr $kgrams*2.2];    ## �����滻  []�������Ķ��ᵱ���ű�ִ��
set msg Eggs:\ \$2.18/dozen\n;          ##  ��б���滻�����пո�$���س�����Ҫת��

.canvas configure -width ${size}m  ;# �滻����ַ������

����չ����
file delete {*}[glob *.o]    ;###   ɾ������.o��β���ļ�
;###�������  {*}  ���� a.o b.o c.o ����Ϊ�������ļ������أ���ʱfile delete���Ҳ��������ļ�

eval file delete [glob *.o]   ;### ͬ����Ĺ��ܣ�eval�������Ǽ�һ�ν���
;###  eval�����е�����������������֮���ÿո������Ȼ��������ΪTcl�ű�����

Tcl�滻����
1��Tcl����һ������ʱ��ֻ�������ҽ���һ�Σ�����һ���滻��ÿ���ַ���ֻ�ᱻɨ��һ��
2��ÿ���ַ�ֻ�ᷢ��һ���滻��������滻��Ľ���ٽ���һ��ɨ���滻


�������飺  ��������Ԫ��������������ַ�����Tcl����������������ݽṹ
set earning(January) 97966
#   �˴���������earning  Ԫ������January ����������Ԫ����һ�㶼�����֣���������
#   ����Ԫ���������ַ���  earning(1)  ��  earning(1.0) �ǲ�ͬ��Ԫ�� 

set capital(New\ Jersey) Trenton    ;## ��Ԫ������ʹ�ÿո�
set "capital(South Dakota)" Pierre  ;## ͬ��
set state "New Mexico"; set capital($state) "Santa Fe" ; ## ͬ��

��ά���飺
set matrix(1,1) 140   ;## ��ʵ�������ַ�������1    
#  ע������֮����ò�Ҫ�пո񣬷���ָ��һ��
set matrix(1,2) 218   ;##
set i 1; set j 2; set cell $matrix($i,$j)  ;##��ȡ��ά�������� 

                    #############       ��������         #############

���append
append varName value ?value ...?
#  ��ÿ��value����������ӵ�����varName�У������Զ��ӿո����Լ���ʾ����
append x $piece    ;#�����ַ���
set x "$x$piece"   ;#ͬ�ϣ�����û����ķ���Ч�ʸ�

���incr
incr varName ?increment?
#  ��������ֵ����increment������ֵ��������������ȱʡincrementʱĬ��Ϊ1��increment�����ɸ�
incr x 12

���set
set varName ?value?
#  ������varName��ֵ

���unset
unset ?-nocomplain? ?--? varName ?varName varName ...?
#  ɾ��varNameָ���ı���������һ�����ַ���
#  ������������ڣ�û������-nocomplainʱ�ᱨ��
unset a earnings(January) b   ;#  ɾ������a b ������Ԫ�� January

���format
format "The square root of 10 is %.3f" [expr sqrt(10)]
#  ��һ�����ַ��������ʽ���������������
����ʾ����
puts "Integer ASCII"
for {set i 95} {$i <= 101} {incr i} {
	puts [format "%4d      %c" $i $i]
}
��һ��д����
puts "Integer ASCII"
for {set i 95} {$i <= 101} {incr i} {
	puts [format "%1$4d     %1$c" $i]
}

���scan
scan string format varName ?varName varName ...?
#  ����string����formatָ�����ֶΣ�����%����ƥ���ֵ������ΪvarName�ı�����
#  ����ֵ�ǳɹ��������ֶθ���
#  ���û���ṩvarName����������ֵ����ƥ����ֶ��б�
scan "16 units, 24.2% margin" "%d units, %f" a b    ;# a=16  b=24.2
#  ��һ�������Ǵ��������ַ������ڶ��������ǿ��ƽ�����ʽ�ĸ�ʽ�ַ���
#  ���������������洢ת������ֵ�ı���

���array exists
array exists arrayName
#  �ж���ΪarrayName�������Ƿ���ڣ����ز���ֵ

���array get
array get arrayName ?pattern?
#  ���ذ�����ΪarrayName����������ݵ��ֵ�
set a(head) hat
set a(hand) glove
set a(foot) shoe
set apparel [array get a]
#  �õ� foot shoe head hat hand glove   ���Կ���get�õ���ֵ��

���array set
array set arrayName dictionary
#  ��dictionary�����ݲ�����ΪarrayName�������С����ؿ��ַ���
array set b $apparel
lsort [array names b]
#  �õ� foot hand head

���array names
array names arrayName ?mode? ?pattern?
#  ������ΪarrayName�������Ԫ�����б��ж��ٷ��ض��٣����ո��Ԫ�����ô�����������
foreach i [array names a] {
	if {($a($i) == "") || ($a($i) == 0)} {
		unset a($i)
	}
}
#  ɾ������Ϊ�յļ�ֵ��

���array size
array size arrayName
#  ������ΪarrayName�������е�Ԫ�ظ���

���array statistics
array statistics arrayName
#  ������ΪarrayName������ڲ����õ�����

���array unset 
array unset arrayName ?pattern?
#  �Ƴ���ΪarrayName���������з���pattern��Ԫ�أ����ؿ��ַ���
#  û����pattern������������Ϊ��


                    #############       �ַ�������         #############
���regexp
regexp ?options? exp string ?matchVar? ?SubVar SubVar ...?
#  ���������ʽexp�Ƿ���stringȫ��ƥ����߲���ƥ��
#�����ƥ�䣬ƥ�䷶Χ����Ϣ����matchVar �� SubVar��
#  �������ǰ�������ʽ���ڴ�������
#  -start num  ѡ��  ָ������ƥ�俪ʼ��λ��
#  -all  ѡ�����regexp���ַ����в��Ҿ�����ε�ƥ�䣬�����ܵ�ƥ�����
#  -inline  ��regexp��ƥ���������Ϊһ�������б�
#  --   ϰ����������Ϊoption�Ľ�����־�����ⷢ������
regexp {^[0-9]+$}  510  ;# ƥ�䴿���֣��õ�1
regexp {([0-9]+) *([a-z]+)} "Walk 10 km" a b c   ;#  a=10km, b=10, c=km
regexp -indices {([0-9])+ *([a-z]+)} "Walk 10 km" ;# a="5 9", b="5 6", c="8 9"

���regsub
regsub ?options? exp string subSpec ?varName?
#  ���������ʽexp�Ƿ���stringȫ��ƥ����߲���ƥ��
#  ��string���Ƶ���ΪvarName�ı����У�ͬʱ��string��ƥ�䲿���滻ΪsubSpec
#  ������varName������ֵ�����滻�Ľ��
#  �����滻�Ƿ�ɹ�������varName�Ļ����ᱻ��ֵ��ƥ��ʧ�ܸ�ֵԭ�ַ���
#  -all ȫ���滻
#  �滻�ַ�����&����\0�����е�&��\0�ᱻ�滻Ϊƥ������ַ���
regsub there "They live there lives" their x ;#  x="They live their lives"  ͬʱ����1
regsub a ababa zz x   ;#  x="zzbaba"
regsub -all a ababa zz x  ;# x="zzbzzbzz"
regsub -all -- a|b axaab && x  ;#  x="aaxaaaabb"
regsub -all -- (a+)(ba*) aabaabxab {z\2} x   ;# x="zbaabxzb"��ע�⵽Ĭ����̰��ƥ��

���string range
string range string first last
#  ����string�д�first(����)��last(����)֮�����е����ַ���
string range "Sample string" 3 7  ;#�õ�  ple s

���string repeat
string repeat string count
#  ���ذ�string�ظ�count�����õ����ַ���
string repeat "abc" 2   ;#  �õ� abcabc

���string replace
string replace string first last ?newstring?
#  ��string�д�first(����)��last(����)֮�����е��ַ��Ƴ����趨��newstring��Ϊ�û�
string replace "San Diego, California" 4 8 "Francisco"

���string map
string map ?-nocase? mapping string
#  ����mapping�ֵ��string�������ַ����滻�������µ��ַ���
#  ����string�г��ֵ�mapping�ؼ��ֶ��滻Ϊ�ֵ��ж�Ӧ��ֵ

���string tolower 
string tolower string ?first? ?last?
#  ��дȫ��Сд

���string totitle
string totitle string ?first ?last
#  ת�ɱ����ʽ������һ���ַ���д������ȫСд

���string toupper
string to toupper string ?first ?last
#  ��дȫ��Сд

���string trim/trimleft/trimright
string trim/trimleft/trimright string ?chars?
#  ȥ����ͷ/��β������chars��Ĭ��Ϊ�հ�
string trim aaxxxbab abc   ;# �õ� xxx

���string compare
string compare ?-nocase ?-length num? string1 string2
#  �Ƚ�string1��string2��-1��0,��1 �ֱ��Ӧ С�ڣ����ڣ�����
#  -nocase���Դ�Сд��-length��ͷ��ʼ��ǰnum���ַ����бȽ�
string compare Michigan Minnesota

���string equal
string equal ?-nocase ?-length num? string1 string2
#  �򵥱Ƚϣ��ϸ���ͬ����1�����򷵻�0�������ö�ǰlength���ַ����бȽ�
string equal -length 3 catalyst cataract   ;# �õ�1

���string match
string match ?-nocase? pattern string
#  ����ͨ�����ʽƥ�����(*, ?, [], \)�Ƚϣ����string��patternƥ���򷵻�1
string match a* bat          ;# �õ�0
string match {[ab]*} brown   ;# �õ�1
string match {*\?} "What?"   ;# �õ�1

���string first
string first string1 string2 ?startIndex?
#  ����string2�е�һ�γ�����string1��ȫ��ͬ�����ַ����Ŀ�ͷ�ַ������������򷵻�-1
#  ָ��startIndex�����string2������ΪstartIndex���ַ���ʼ����
string first th "There is the tub" 2    ;#  �õ� 9

���string last 
string last string1 string2 ?lastIndex?
#  ����string2����string1��ȷƥ������ұߵ����ַ��͵Ŀ�ͷ�ַ�������
string last th "There is the tub"

���string index 
string index string charIndex
#  ����string������ΪcharIndex���ַ���û�з��ؿ��ַ�����������0��ʼ
string index "Sample string" 3   ;#  �õ� p

���string length
string length string
#  ����string�е��ַ�����
string length "Sample string"    ;#�õ�  13

���string is digit/control/Alnum/alpha/list/lower
#  ȷ���ַ����������Ƿ���ȷ����ȷ����1�����򷵻�0
string is digit 1234
string is control ""   ;# ����1
string is control -strict ""   ;# ����0
string is digit -failindex idex "123c5"  ;#  idex����Ϊ3


                    #############       �б����         #############
					
���in��
#  �ַ������б�Ԫ��ʱ������1�����򷵻�0
if {"Los Angeles" in $cities} {}   ;#  �鿴Los Angeles�Ƿ���cities�б��һ��Ԫ��

���ni��
#  �ַ��������б�Ԫ��ʱ������1

���list 
list ?value value ...?
#  ����һ���б���Ԫ�ؾ���value����
list {a b c} {d e} f {g h i}     ;#  �õ�һ����Ԫ�ص��б�

���concat
concat ?list list ...?
#  ������б�ϲ���һ���б�(����list��Ϊ����б��Ԫ��)
concat {a b c} {d e} f {g h i}    ;#  �õ�һ��9Ԫ�ص��б�
concat {a b} {c {d e f}}          ;#  �õ�һ��4Ԫ�ص��б�

���lrepeat
lrepeat number value ?value...?
#  value��ΪԪ�أ��ظ�number�εõ��б�����
lrepeat 4 a b c       ;#  �õ�  a b c a b c a b c a b c 

���join
join list ?joinString?
#  ��joinString��Ϊ�ָ��������б����ٴ���������Ĭ��Ϊ�ո�

���lappend
lappend varname value ?value...?
#  ��value��Ϊ�б�Ԫ����ӵ�varName�����У�Ȼ�󷵻ر�������ֵ

���lassign
lassign list varName ?varName...?
#  ��list�е�Ԫ��˳�θ�����ΪvarName�ı���������������Ϊ��
#  Ԫ�ض����ɶ��������б���Ϊ����ֵ

���lindex
lindex list ?index ...?
#  ʹ��list�ĵ�index��Ԫ�ء�ʹ�ö������ֵʱ�������Ƿֱ�����ı�����Ҳ������һ���б�
#  ÿ������һ�ζ�ǰһ�������Ľ�����в������Ӷ�����Ƕ�׵��б�Ԫ��
lindex {John Anne Mary Jim} 1   ;#  �õ� Anne
lindex {a b {c d e} f} 2        ;#  �õ� c d e   ;�б��Ƕ�׵Ĳ���û������
lindex {{a b} {c {d e f}} g} 1 1 2   ;#  �õ�f   Ƕ�׷���

���linsert
linsert list index value ?value...?
#  �����е�value��Ϊ�б�Ԫ�ز���list�е�index��Ԫ��֮ǰ���������б�

���llength
llength list
#  ����list��Ԫ�ظ���
llength {a b c d}    ;#  4
llength a            ;#  1

���lrange
lrange list first last
#  ����һ���б���list�б���first��last��Ԫ�����
lrange {a b {d d} e} 1 3       ;#  �õ�  b {c d} e


���lreplace
lreplace list first last ?value ...?
#  ��list��first��last��Ԫ���滻Ϊ0������Ԫ��

���lsearch
lsearch ?option...? list pattern
#  ��list��������patternƥ���һ������Ԫ��
#  optionѡ�����ƥ�䷽ʽ(-exact -glob -regexp)
#  -inline  ����Ԫ��ֵ�����򷵻�����
#  -all  ��������ƥ��  �����������ȳ��ֵ�ƥ��
#  Ĭ�Ͻ���ͨ���ƥ�䣬���ص�һ��ƥ���������û��ƥ��ʱ����-1

���lset
lset varName ?index ...? newValue
#  ��varName�б�����index����ָ���Ԫ��ֵ��ΪnewValue�����ش洢��varName�е����б�

���lsort
lsort ?option..? list
#  ��list��Ԫ�����򣬷����������б�

���split
split string ?splitChars?
#  ��splitChars�ָ�string�����һ�����б���


                    #############       �Ӻ�������         #############
���proc
proc test {argv} {}
#  ����һ�������̣�test�����֣��Ӳ����б�{}��������ʵ��