
lua中可以用;也可以不使用，换行也没有分隔语句的作用 a=1 b=a*2也是合法语句

#!/usr/local/bin/lua    unix系统中用法

%lua -i prog                   --  运行程序prog之后开启一个交互会话

%lua -e "print(math.sin(12))"  -- 在命令行直接运行一个lua代码

%lua -i -llib -e "x=10"  -- 导入lib库，执行x=10，最后打开一个交互会话

%lua -e "sin=math.sin" script a b    -- 执行语句和script脚本，带a b两个参数
arg[-3] = "lua"
arg[-2] = "-e"
arg[-1] = "sin=math.sin"
arg[0] = "script"
arg[1] = "a"
arg[2] = "b"

注释技巧：
--  普通注释

--[[
  块注释
--]]

---[[
  解除注释
--]]

--[=[    
  -- 应对注释内还有注释的情况  [[  ]]
--]=]


--------------------------    交互界面的操作   ------------------------------

> dofile("lib1.lua")  --  在交互界面导入lib1.lua库，可以调用库内函数

> = math.sin(3)   -- 在交互界面打印表达式的值



--------------------------    基本语法   ------------------------------

所有类型：
print(type("Hello world")) --> string
print(type(10.4*3)) --> number
print(type(print)) --> function
print(type(type)) --> function
print(type(true)) --> boolean
print(type(nil)) --> nil
print(type(type(X))) --> string  type函数调用完之后返回的是string
还有  userdata, thread, table三种类型

布尔类型：  true false  在lua中0和空字符串是 true

数字的表现方式：  4   0.4   4.57e-3   0.3e12   5E+20
0xff (16进制:255)  0x1A3(419)  0x0.2(0.125)  0x1p-1(二进制：0.5，p表示二进制)

b = 10   -- 变量赋值，变量使用前不用初始化
b = nil  -- 类似删除一个变量
b = "a string"
b = print   --  b是一个函数
b(type(b))  --  得到字符串 function


字符串：     字符串是不可修改的， #放在字符串前面可以获得字符串长度
a = "one string"
b = string.gsub(a, "one", "another")   -- ，创建了一个新字符串
print(#a)      -- 打印字符a的长度
print(#"good\0bye")   --  得到8
#  用来求得字符串的长度 或者 得到sequence的长度(sequence是key为连续数字的表)
print(a[#a])       --  打印序列中最后一个元素


长字符串：    用[[  ]] 包围的是长字符串，其忽略/的转义，当内容第一个字符为\n时忽略它
page = [[
<html>
<head>
<title>An HTML Page</title>
</head>
<body>
<a href="http://www.lua.org">Lua</a>
</body>
</html>
]]
write(page)

[===[  [[abc]]   ]===]  通过在外围方括号之间添加任意对齐的=来转义长字符串内部的]]

line = io.read()
n = tonumber(line)  -- 把读入的内容强制转换成数字
print(tostring(n))   -- 强制转换为字符串
print(10 .. n)   --   自动把n转为字符串

+   -   *   /   %(取余)   ^(指数)

x = math.pi
print(x - x%0.01)        --  实数也可以使用取余，此处表示保留小数点后两位

>   <   <=   >=   ==   ~=(不等于)

and 和 or 都使用短路逻辑，返回第一个使之求出表达式结果的参数
逻辑与：  and    print(false and 13)   -- 得到 false
逻辑或：  or     print(false or 5)     --  得到5，因为判断到5时得出结果
逻辑非：  not    print(not 0)          --  得到false，lua中仅仅false和nil表达否的意思


x=x or r    --  等价于  if not x then x = v end  当x不为空时赋值为v  要求x不为false
max = (x > y) and x or y   -- a and b or c的形式，等价于C语言中的 a?b:c  要求b不为非false

字符串拼接：     ..
print(0 .. 1)    --> 01
print(000 .. 01) --> 01


表结构：

a = {}   --  声明一个表
a["x"] = 10       -- key:x   value:10         等价于  a.x = 10  注意必须是字符串x
x = 100
a[x] = 19    -- x是变量不是字符串
a[20] = "great"    --  key:20  value:great
a = nil   -- 删除表

a= {}
for i = 1,10 do     --  数组下标习惯性从1开始
	a[i] = io.read()
end

对一个连续的序列(下标连续且值都不为空)，其长度可以通过 #a 得到(a是表名)

a = {};  a.a = a          --  映射到表自身  a.a.a.a不管几个a都是指向表地址自身

--  建立一个索引从1开始的表
建立表的方法一：   直接放入对应值，key为从1开始的升序的数值
days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"}
print(days[4]) --> Wednesday

建立表的方法二：   key=value形式
a = {x=10, y=20}   -- 等价于  a = {}; a.x=10; a.y=20  注意到此处x,y都是字符串

混合形式成立
polyline = {color="blue",
	thickness=2,
	npoints=4;              -- 表分隔符用;和,都可以，可以用;表示不同类型的分隔
	{x=0, y=0}, -- polyline[1]
	{x=-10, y=0}, -- polyline[2]
	{x=-10, y=1}, -- polyline[3]
	{x=0, y=1} -- polyline[4]
}
print(polyline[2].x) --> -10
print(polyline[4].y) --> 1


建立表的方法三：   用中括号括起来的key形式，[]里面的内容会被计算
opnames = {["+"] = "add", ["-"] = "sub", ["*"] = "mul", ["/"] = "div"}