普通字符组：[...]
[0-9a-zA-Z]
[-abc]      要匹配-时
[\w0-9]
[\s\S]   匹配任意字符

排除型字符组：[^...]           排除型字符组必须匹配一个字符
[^0-9]
[^-abc]     要排除-时
[^\da-z]

除去字符组内部的-，其他元字符的转义都必须在字符之前添加反斜线
[]都作为匹配的一部分时，只有开方括号[需要转义
re.search(r"^\[012]$", "[012]") != None     # => true

\d    ==   [0-9]          反义：  \D
\w    ==   [0-9a-zA-Z_]         注意_不能掉          反义：  \W
\s    ==   [ \t\r\n\v\f]             反义：  \S


字符组运算（仅Java和.NET提供）
[[a-z]&&[^aeiou]]         匹配所有辅音字母 Java版
[a-z-[aeiou]]             匹配所有辅音字母 .NET版

量词匹配
\d{6}  数字出现六次
\d{4,6}  数字出现4到6次，注意,之后绝对不能有空格
\d{4,}   数字字符串长度必须在4个字符以上

匹配优先量词：   最长匹配
*  等价于  {0,}
+  等价于  {1,}
?  等价于  {0,1}             https?  匹配http或https协议头
{m,n}
{m,}
{,n}

忽略优先量词：    最短匹配
*?
+?
??
{m,n}?
{m,}?
{,n}?

量词的转义：
{n}      \{n}
{m,n}    \{m,n}
{m,}     \{m,}
{,n}     \{,n}
*        \*
+        \+
?        \?
*?       \*\?
+?       \+\?
??       \?\?

关键字字符的转义：
单个字符：
*  转义  \*
+  转义  \+
?  转义  \?
成对出现字符：只需要转义前一个
{6}   转义   \{6}
[a-z]  转义   \[a-z]
特殊字符：
与括号有关的三个元字符 ( ) | 都必须转义



<[^>]+>   匹配    html中的<head></head>等tag
"[^"]*"   匹配    双引号字符串，注意到引号内容可以为空

<[^/>][^>]*>   匹配   <head><b>类型的tag,  注意到[^/]占了一个字符的位置，所以用*  此解法暗含<br/>
<[^/>]([^>]*[^/])?>        仅匹配  <head><b>类型的tag
<('[^']*'|"[^"]*"|[^'">])+>    能匹配<input name=txt value=">"> 类型的tag

</[^>]+>      仅匹配   </head>类型的tag
<[^>/]+/>      仅匹配   <br/>类型的tag

<([^>/]+)>[\s\S]*?</\1>     利用反向引用匹配tag
<([a-zA-Z0-9]+)(\s[^>]+)?>[\s\S]+</\1>   利用反向引用匹配复杂的tag

括号(分组)的作用：
[1-9]\d{14}(\d{2}[0-9x])?      身份证的匹配

([1-9][0-9]{14} | [1-9][0-9]{16}[0-9x])  身份证的匹配，多选结构

[-\w.]{0,64}@([-a-zA-Z0-9]{1,63}\.)*[-a-zA-Z0-9]{1,63}       邮箱账号的匹配

((00)?[0-9]|0?[0-9]{2}|1[0-9][0-9]|2[0-4][0-9]|25[0-5])   匹配IP地址里面的数字

(0|\+86)?(13[0-9]|15[0-356]|18[025-9])\d{8}             匹配手机号

<a\s+href\s*=\s*["']?([^"'\s]+)["']?>([^<]+)</a>       提取html标签里的网址和内容  $1  $2

对于反向引用：
(\d{4})-(\d{2})-(\d{2})     匹配 2010-12-22时，第一个反向引用得到2010
(\d){4}-(\d{2})-(\d{2})     匹配 2010-12-22时，第一个反向引用得到0，因此第一个括号连续匹配四次，分别填入2,0,1,0

反向引用使用时，用 \1 \2 \3表示，注意没有\0  \0是ascii码

命名分组：
(?P<year>\d{4})-(?P<month>\d{2})-(?P<day>\d{2})  python中的匹配2012-09-21形式的命名分组，此时\1 \2 \3的数字分组依然可用

命名分组在进行反向引用的表达式：  (?P=name)  例如：  ^(?P<char>[a-z])(?P=char)$
在替换中的反向引用：\g<name>   

非捕获分组：
(?:...) 只限定量词的作用范围，不捕获任何文本，因而匹配效率高

单词边界：  \b
\bone\b  只会匹配单词one，honest不会被匹配

匹配任意纯单词：   \b\w+\b   
匹配带连字符的单词： \b[-\w]+\b