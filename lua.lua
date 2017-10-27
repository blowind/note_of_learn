
lua�п�����;Ҳ���Բ�ʹ�ã�����Ҳû�зָ��������� a=1 b=a*2Ҳ�ǺϷ����

#!/usr/local/bin/lua    unixϵͳ���÷�

%lua -i prog                   --  ���г���prog֮����һ�������Ự

%lua -e "print(math.sin(12))"  -- ��������ֱ������һ��lua����

%lua -i -llib -e "x=10"  -- ����lib�⣬ִ��x=10������һ�������Ự

%lua -e "sin=math.sin" script a b    -- ִ������script�ű�����a b��������
arg[-3] = "lua"
arg[-2] = "-e"
arg[-1] = "sin=math.sin"
arg[0] = "script"
arg[1] = "a"
arg[2] = "b"

ע�ͼ��ɣ�
--  ��ͨע��

--[[
  ��ע��
--]]

---[[
  ���ע��
--]]

--[=[    
  -- Ӧ��ע���ڻ���ע�͵����  [[  ]]
--]=]


--------------------------    ��������Ĳ���   ------------------------------

> dofile("lib1.lua")  --  �ڽ������浼��lib1.lua�⣬���Ե��ÿ��ں���

> = math.sin(3)   -- �ڽ��������ӡ���ʽ��ֵ



--------------------------    �����﷨   ------------------------------

�������ͣ�
print(type("Hello world")) --> string
print(type(10.4*3)) --> number
print(type(print)) --> function
print(type(type)) --> function
print(type(true)) --> boolean
print(type(nil)) --> nil
print(type(type(X))) --> string  type����������֮�󷵻ص���string
����  userdata, thread, table��������

�������ͣ�  true false  ��lua��0�Ϳ��ַ����� true

���ֵı��ַ�ʽ��  4   0.4   4.57e-3   0.3e12   5E+20
0xff (16����:255)  0x1A3(419)  0x0.2(0.125)  0x1p-1(�����ƣ�0.5��p��ʾ������)

b = 10   -- ������ֵ������ʹ��ǰ���ó�ʼ��
b = nil  -- ����ɾ��һ������
b = "a string"
b = print   --  b��һ������
b(type(b))  --  �õ��ַ��� function


�ַ�����     �ַ����ǲ����޸ĵģ� #�����ַ���ǰ����Ի���ַ�������
a = "one string"
b = string.gsub(a, "one", "another")   -- ��������һ�����ַ���
print(#a)      -- ��ӡ�ַ�a�ĳ���
print(#"good\0bye")   --  �õ�8
#  ��������ַ����ĳ��� ���� �õ�sequence�ĳ���(sequence��keyΪ�������ֵı�)
print(a[#a])       --  ��ӡ���������һ��Ԫ��


���ַ�����    ��[[  ]] ��Χ���ǳ��ַ����������/��ת�壬�����ݵ�һ���ַ�Ϊ\nʱ������
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

[===[  [[abc]]   ]===]  ͨ������Χ������֮�������������=��ת�峤�ַ����ڲ���]]

line = io.read()
n = tonumber(line)  -- �Ѷ��������ǿ��ת��������
print(tostring(n))   -- ǿ��ת��Ϊ�ַ���
print(10 .. n)   --   �Զ���nתΪ�ַ���

+   -   *   /   %(ȡ��)   ^(ָ��)

x = math.pi
print(x - x%0.01)        --  ʵ��Ҳ����ʹ��ȡ�࣬�˴���ʾ����С�������λ

>   <   <=   >=   ==   ~=(������)

and �� or ��ʹ�ö�·�߼������ص�һ��ʹ֮������ʽ����Ĳ���
�߼��룺  and    print(false and 13)   -- �õ� false
�߼���  or     print(false or 5)     --  �õ�5����Ϊ�жϵ�5ʱ�ó����
�߼��ǣ�  not    print(not 0)          --  �õ�false��lua�н���false��nil�������˼


x=x or r    --  �ȼ���  if not x then x = v end  ��x��Ϊ��ʱ��ֵΪv  Ҫ��x��Ϊfalse
max = (x > y) and x or y   -- a and b or c����ʽ���ȼ���C�����е� a?b:c  Ҫ��b��Ϊ��false

�ַ���ƴ�ӣ�     ..
print(0 .. 1)    --> 01
print(000 .. 01) --> 01


��ṹ��

a = {}   --  ����һ����
a["x"] = 10       -- key:x   value:10         �ȼ���  a.x = 10  ע��������ַ���x
x = 100
a[x] = 19    -- x�Ǳ��������ַ���
a[20] = "great"    --  key:20  value:great
a = nil   -- ɾ����

a= {}
for i = 1,10 do     --  �����±�ϰ���Դ�1��ʼ
	a[i] = io.read()
end

��һ������������(�±�������ֵ����Ϊ��)���䳤�ȿ���ͨ�� #a �õ�(a�Ǳ���)

a = {};  a.a = a          --  ӳ�䵽������  a.a.a.a���ܼ���a����ָ����ַ����

--  ����һ��������1��ʼ�ı�
������ķ���һ��   ֱ�ӷ����Ӧֵ��keyΪ��1��ʼ���������ֵ
days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"}
print(days[4]) --> Wednesday

������ķ�������   key=value��ʽ
a = {x=10, y=20}   -- �ȼ���  a = {}; a.x=10; a.y=20  ע�⵽�˴�x,y�����ַ���

�����ʽ����
polyline = {color="blue",
	thickness=2,
	npoints=4;              -- ��ָ�����;��,�����ԣ�������;��ʾ��ͬ���͵ķָ�
	{x=0, y=0}, -- polyline[1]
	{x=-10, y=0}, -- polyline[2]
	{x=-10, y=1}, -- polyline[3]
	{x=0, y=1} -- polyline[4]
}
print(polyline[2].x) --> -10
print(polyline[4].y) --> 1


������ķ�������   ����������������key��ʽ��[]��������ݻᱻ����
opnames = {["+"] = "add", ["-"] = "sub", ["*"] = "mul", ["/"] = "div"}