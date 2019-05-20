

		     ###############           模块相关               #################	

python使用特殊的命令import导入模块：
导入方式一：
import math       ## 基本数学库
math.ceil(32.9)    ##  得到浮点数33.0
math.sqrt(9)        ###  得到整数3
int(math.floor(32.9))     ##  得到浮点数32.0，然后转换到整数

import cmath           ###  处理虚数的库
cmath.sqrt(-1)            ###  得到虚数1j，python本身支持虚数

导入方式二：
from math import sqrt
sqrt(9)        ###  该形式不用模块名作为前缀
from __future__ import division           ###  引入future模块，变普通整数除法为浮点数除法
from math import pi

import sys
sys.path.append('c:/python')      ###  添加到python搜索路径，然后可以在这个路径下引入模块
sys.path.expanduser('~.python')   ###  自动化上面的操作
abc = reload(abc)                 ###   重载abc模块，在程序运行时动态更新模块很有效
import pprint
pprint.pprint(sys.path)                  ###  更加智能的输出path中存储的路径列表
或者在系统上设置  PYTHONPATH  环境变量

__name__   ### 所在模块的名字，在主程序中为  __main__
在模块内部编写的测试代码，通过
if __name__ == '__main__':  模块内函数()  或者  测试函数()
的方式来运行，直接运行模块时，就会执行这些测试代码

在模块所在的文件夹建立  __init__.py  文件，则这个文件夹就成为了一个包
import 文件夹名           ###  引入包，此时__init__.py里面的内容可用，其他模块内容不可用
import 文件夹名.模块名    ###　引入包内模块，只能通过  文件夹名.模块名.函数名  来使用内容
from 文件夹名 import 模块名        ###　　同上，可以通过  模块名.函数名  来使用内容

import copy
dir(copy)       ###  查看copy内所有特性
copy.__all__     ###  得到模块的公共接口
help(copy.copy)    ###  得到copy模块内copy函数的文档字符串
print range.__doc__    ###  查看range的参数和调用方式
print copy.__file__          ###  返回copy源代码所在的路径

import sys    ### 与python解释器联系紧密的变量和函数  argv, exit, modules, path, platform, stdin, stdout, stderr
import os     ### 访问多个操作系统服务的功能  environ, system, sep, pathsep, linesep, urandom
os.system("ls -al")  ### 运行外部程序
os.system('/usr/bin/firefox')  ##  unix系统调用外部程序推荐方法
os.startfile(r'c:\Program Files\Mozilla Firefox\firefox.exe')     
 ### windows系统调用外部程序推荐方法
import webbrowser
webbrowser.open('http://www.python.org')  ###  打开网站的好方法

import fileinput   ### 遍历文本文件的所有行  input, filename(), lineno(), filelineno(), isfirstline(), isstdin(), nextfile(), close()

import fileinput              ###   给某个代码文件每行加上行号
for line in fileinput.input(inplace=True):
	line = line.rstrip()
	num = fileinput.lineno()
	print '%-40s # %2i' % (line, num)
	
result = {}          ###  统计文件中某个特殊字段的出现的次数
for line in fileinput.input("./data.txt"):
	line = line.strip()
	out = line.split("/")
	if result.has_key(out[3]):             ## 此处统计的是第4个字段(下标是3)
		result[out[3]] = result[out[3]] + 1
	else: 
		result[out[3]] = 1
	print out[3]
print result.items()
	

	###  就是数学意义上的集合，不包含重复元素
a = set([1, 2, 3])   ## 集合，因为集合是可变的，所以不能作为字典的键
b = set([2, 3, 4])   ## 而集合本身只能包含不可变的成员，一般集合内是不能包含集合的 
a.union(b)    ### 得到集合 set([1,2,3,4])
c.issubset(a)
c.issuperset(a)
a.intersection(b)
a.difference(b)
a.symmetric_difference(b)
a.copy()
a.add(frozenset(b))    ###  使b不可变，这样就能把b当做成员加入a，因为集合的元素是不可变的

mySets = []              ###  这段代码查找并打印集合的并集
for i in range(10):
	mySets.append(set(range(i, i+5)))
reduce(set.union, mySets)       ### 讲mySets中的元素通过set.union函数进行合并


from heapq import *   ### 引入"堆"模块  heappush, heappop, heapify, heapreplace, nlargest, nsmallest
###  小顶堆结构
for n in (range(10)):        ####  其中heap队列只能是堆结构的队列，一般队列不能用于此处
	heappush(heap, n)	
heappush(heap, x)   ## x入堆
heappop(heap)         ##  堆中最小元素弹出
heapify(heap)          ## 将heap属性强制应用到任意一个列表，使其成为堆
heapreplace(heap, x)    ## 将堆中最小元素弹出，同时x入堆
nlargest(n, iter)      ##  返回iter中前n大的元素,  iter只需要是可迭代对象
nsmallest(n, iter)      ##  返回iter中前n小的元素

from collections import deque    ###  引入双端队列
q = deque(range(5))
q.append(5)            ###   在右端加入元素5   用于单个元素
q.appendleft(6)
q.pop()       ##  右端出队
q.popleft()
q.rotate(3)     ###  循环右移三位
q.rotate(-1)     ###  循环左移一位
q.extend(seq)     ###  在右端加入元素列表   用于列表
q.extendleft(seq)    ###  在左端加入元素列表，注意到seq中元素会反序出现在q中

from time import *        ###   时间模块
time.asctime([tuple])      ###  将时间元组转换为字符串
time.localtime([secs])     ###   将实数转换为本地时间的日期元组
time.gmtime([secs])         ### 同上，不过是全球统一时间
time.mktime(tuple)                ###  将日期元组转换为从新纪元(1970)开始计算的秒数，与localtime相反
time.sleep(secs)           ###  让解释器等待给定的秒数
time.strptime(string[, format])   ###  将字符串解析为时间元组

 
from random import *       ###  random模块
random.random()   ##  返回0~1之间的伪随机数n
random.uniform(a, b)   ##  返回在a~b之间的随机实数n(平均分布)
random.randrange([start, ]stop[, step])  ### 返回在start,stop之间的随机整数，不包括stop
random.choice(seq)     ## 从给定序列中均一地选择随机元素
random.shuffle(seq[, random])  ### 将给定序列的元素进行随机移位
sample(seq, n)           ###  从给定序列中选择给定数目的元素，保证元素不重复

import shelve    ###  在文件用存储数据
s = shelve.open('test.dat')
s['x'] = ['a', 'b', 'c']
s['x'] = s['x'].append('d')




### redis模块安装： 1、python安装目录Scripts下执行 .\easy_install.exe pip 安装pip； 2、Scripts目录下 .\pip2.7.exe install redis 安装redis模块
import redis
import fileinput

type=2
pool = redis.ConnectionPool(host='47.75.92.78', port=6379, db=0, password='7wtRY33AoWuun2pCLQ')
# host='localhost', port=6379, db=0, password=None, socket_timeout=None, connection_pool=None, charset='utf-8', errors='strict', unix_socket_path=Non
r = redis.Redis(connection_pool=pool)
print "start"
for line in fileinput.input("./mobile.txt"):
	line = line.strip()
	print 'insert: ' + line
	if len(line) > 0 :
		r.hset('mobile:type', line + ':' + str(type), '222222')



		     ###############           注意事项               #################	



1.0 // 2.0 ###   双斜线强制进行整除

2**3  ### 幂运算   -2**3 = -9

x, y, z = 1, 2, 4   ###　多个变量同时赋值

x, y = y, x   #### 交换两个变量的值

key, value = scoundrel.popitem()   ###   序列解包，注意序列解包左右元素个数要完全一致

a,b,rest* = [1, 2, 3, 4]   ### 仅限3.0，使用星号的变量放入剩下的所有元素

x = y = somefunction()   #### 链式赋值

python中使用冒号:来标识语句块的开始，块中每个语句都是缩进的
if name.endswith('Gumby'):
	if name.startswith('Mr.'):
		print 'Hello. Mr.Gumby'
	elif name.startswith('Mrs.'):
		print 'Hello, Mrs.Gumby'
	else:
		print 'Hello, Gumby'
else:
	print 'Hello, stranger'
	
	
while not name:
	name = raw_input('Please enter your name: ')
	
words = ['this', 'is', 'an', 'ex', 'parrot']
for word in words:
	print word
	
d = {'x':1, 'y':2, 'z':3}
for key in d:
	print key, 'corresponds to', d[key]

for key, value in d.items():        ###  循环中解包，可以得到键值对
	print key, 'corresponds to', value

会被看做为假的布尔表达式： False  None 0 "" () [] {}

x is y       ### 是同一个对象
x is not y   ## 是不同对象
x in y       ## 是容器y的成员
x not in y    ### 不是容器y的成员

assert断言：  类似  if not condition: carsh program  的意思
age = -1
assert 0<age<100, 'The age must be realistic'  ##  加上一个字符串解释断言


break    跳出循环

continue  结束当前迭代

循环中的else，不同于与if对应的else
####  注意到由于python是按缩进看程序块的，此处else与for地位相同，在for循环执行完毕后判断执行else内的部分
from math import sqrt
for n in range(99, 81, -1):
	root = sqrt(n)
	if root == int(root):
		print n
		break
else:
	print "Didn't find it!"

	
列表推导式：
[x*x for x in range(10)] ###  返回0(包含)到10之间所有数的平方
[x*x for x in range(10) if x % 3 == 0]  ### [0, 9, 36, 81]
[(x,y) for x in range(3) for y in range(3)]
####   得到 [(0,0), (0,1), (0,2), (1,0), (1,1), (1,2), (2,0), (2,1), (2,2)]

[b+'+'+g for b in boys for g in girls if b[0]==g[0]]   ###  boys和girls是两个列表
这个方法效率不高，下面是推荐方法之一：
girls = ['alice', 'bernice', 'clarice']
boys = ['chris', 'arnold', 'bob']
letterGirls = {}
for girl in girls:
	letterGirls.setdefault(girl[0], []).append(girl)
print [b+'+'+g for b in boys for g in letterGrils[b[0]]]
#### 先以单字母作为键，女孩名字组成的列表作为值建立字典。之后推导式循环整个男孩集合


pass  什么都不做，因为python中空代码块是非法的
if name == 'Ralph':
	print 'Welcome'
elif name == 'Enid':
	pass
elif name == 'Bill':
	print 'Access Denied'
	
exec  执行一个字符串语句
exec "print 'Hello,world!'"  ##  打印这个句子
from math import sqrt
scope = {}
exec 'sqrt = 1' in scope
sqrt(4)   ###  得到2.0
scope['sqrt']  ##  得到1

eval  计算python表达式并返回结果
eval(raw_input("Enter an arithmetic expression: "))  ## 简单计算器
scope = {}
scope['x'] = 2
scope['y'] = 3
eval('x * y', scope)  ###  得到6   提供命名空间从而计算

scope = {}
exec 'x = 2' in scope
eval('x*x', scope) 


def 用来定义函数
def hello(name):
	return 'hello, ' + name + '!'

def square(x):
	'Calculates the square of the number x.'   ###  文档字符串
	return x*x
	
square.__doc__          ###  访问文档字符串

def hello(greeting, name):
	print '%s, %s!' % (greeting, name)
hello(greeting='hello', name='world')  ###  关键字参数，代入形参名字\
def hello(greeting='hello', name='world')  ###  利用关键字参数提供默认参数，与字典有区别，因为greeting是个临时变量

def print_params(*params):     ###  收集参数，形成参数元组给内部使用
def print_params_2(**params):    ###  能处理关键字参数的收集参数，关键字参数用字典形式表示
def print_params_3(x, y, z=3, *pospar, **keypar):   ###   三合一
示例： print_params_3(1, 2, 3, 5, 6, 7, foo=1, bar=2)
得到：1  2  3  (5, 6, 7)   {'foo':1, 'bar':2}

def add(x, y): return x+y
params = (1, 2)
add(*params)     ###  反转过程，元组分拆后传入

params = {'name':'Sir Robin', 'greeting':'Well met'}
hello(**params)       ###  字典的反转过程


global  在局部作用域声明一个变量为外部的全局变量
x=1
def change_global():
	global x
	x += 1
	
	
###  生成器不是像return那样返回值，而是每次产生多个值，每次产生一个值，函数就会被冻结
###  即函数等在那里等待被激活，激活后就从停止的地方开始执行
def flatten(nested):
	try:
		try: nested + ''       ###  检查，不要迭代字符串类型的变量，否则进入死循环，字符串的第一个字符还是字符串
		except TypeError: pass
		else: raise TypeError
		for sublist in nested:
			for element in flatten(sublist):  ### 递归生成器，基本情况是函数被告知展开一个元素，从而引发一个TypeError
				yield element      ###  任何包含yield的语句的函数称为生成器
	except: TypeError:      
		yield nested        ###  
		
###  以上生成器的普通函(旧)版本
def flatten(nested):
	result = []
	try:
		try: nested + ''
		except TypeError: pass
		else: raise TypeError
		for sublist in nested:
			for element in flatten(sublist):
				result.append(element)
	except TypeError:
		result.append(element)
return result
		
		
def repeater(value):      ###  生成器方法，没看懂
	while True:
		new = (yield value)
		if new is not None: value = new
###  生成器还有throw和close两个方法

			
	
		     ###############           类相关               #################	
	

__metaclass__ = type  ### 确定使用新式类，此时可以用type(s)查看实例的类
class Person:                 ###  创建一个类
	def setName(self, name):
		self.name = name
	def getName(self):
		return self.name
	def __inaccessible(self):              ###  加上双下划线就成了私有方法，从外部无法访问
		print "Bet you can't see me"  
		
class Filter:
	def init(self):
		self.blocked = []
class SPAMFilter(Filter):    ##   定义超类
	def init(self):           ###  重写init
		self.blocked = ['SPAM']

SPAMFilter.__bases__      ###  查看类的基类
s = SPAMFilter()
isinstance(s, SPAMFilter)   ###   检查一个对象是否是一个类的实例
s.__class__                  ###   查看一个对象属于哪个类
s.__dict__                    ###  查看对象内所有存储的值

class FooBar:
	def __init__(self):     ###  创建一个构造方法
		self.somevar = 42
		
__del__   是析构方法，尽量避免使用

super函数的使用
__metaclass__ = type
class Bird:
	def __init__(self):
		self.hungry = True
	def eat(self):
		if self.hungry:
			print 'Aaaaa...'
			self.hungry = False
		else:
			print 'No, thanks!'
class SongBird:
	def __init__(self):
		super(SongBird, self).__init__()           ###　　调用超类的构造方法
		self.sound = 'Squawk!'
	def sing(self):
		print self.sound
		
__len__(self)    该方法返回集合中所含项目的数量
__getitem__(self, key)   该方法返回与所给键对应的值
__setitem__(self, key, value)   该方法按一定的方式存储和key相关的value
__delitem__(self, key)         删除对象和对应的键

__getattribute__(self, name)   当特性name被访问时自动被调用
__getattr__(self, name)        当特性name被访问且对象没有相应的特性时被自动调用
__setattr__(self, name, value)   当试图给特性name赋值时会自动被调用
__delattr__(self, name)        当试图删除特性name时被自动调用


__metaclass__ = type
class Rectangle:
	def __init__(self):
		self.width = 0
		self.height = 0
	def setSize(self, size):
		self.width, self.height = size
	def getSize(self):
		return self.width, self.height
	size = property(getSize, setSize)         #### 创建属性，两个访问器函数被当做参数
	
class MyClass:
	@staticmethod             #####  定义一个静态方法
	def smeth():
		print 'This is a static method'
	@classmethod                ####  定义一个类方法
	def cmeth(cls):
		print 'This is a class method' . cls
		

####   一个实现了__iter__方法的对象是可迭代的，一个实现了next方法的对象是迭代器		
class Fibs:
	def __init__(self):
		self.a = 0
		self.b = 1
	def next(self):
		self.a, self.b = self.b, self.a+self.b
		return self.a
	def __iter__(self):          ###  返回迭代器本身
		return self
		

		

		     ###############           异常相关               #################
		
		
raise Exception('hyperdrive overload')    ### 引发异常
raise  不带参数时，会将捕获的异常传递下去

try:                                         ####  捕获异常
	x = input('Enter the first number:')
	y = input('Enter the second number:')
	print x/y
except ZeroDivisionError:
	print "The second number can't be zero!"
except TypeError:
	print "That wasn't a number. Was it?"
except (IOError, AttributeError), e:           ####  多个异常合并在一起要放在元组里，要使用异常对象时加第二个参数
	print e
except:                               ###    捕获所有异常，不推荐
	print 'Something wrong happened'
else:                                  ### 没发生异常时执行
	print 'no exception'            
finally:
	print 'Cleaning up'            ###  最后肯定会执行的部分
		
		     ###############           列表相关操作               #################


序列用中括号包围：[1, 2, 3]
元组用圆括号包围：(1, 2, 3)
映射用大括号包围：d = {}        ###    详见   字典相关
包含一个元素的元组：(1,)
3*(40+2)   ## 得到126
3*(40+2,)  ## 得到(42, 42, 42)  因为加逗号之后变成元组

列表赋值
x = y   x和y指向同一个列表
x = y[:]  把y列表的内容复制到x的拷贝内

numbers[0:10:1]    分片
第一个参数为分片内容第一个元素索引，第二个参数为分片内容结束后第一个元素索引，第三个参数为步长
numbers[::2]
把索引为偶数的内容提取出来
numbers[::-2]
从右往左提取元素

name = list('Perl')   ##  得到字符列表 ['P', 'e', 'r', 'l']
name[1:0] = list('ython')   ##  得到字符列表  ['P', 'y', 't', 'h', 'o', 'n']

numbers = [1, 5]
numbers[1:1] = [2, 3, 4]   ## 插入三个元素，得到 [1, 2, 3, 4, 5]
numbers[1:4] = []    ###  通过空替换来删除元素，得到 [1, 5]  

sequence = [None]*10   初始化拥有十个元素的空列表

in   成员资格检查
x in 'six'

del names[2]   删除列表元素
del names[2:4]  删除两个列表元素

name[2:] = list('ar')    分片赋值，用a和r替换name中第三个元素开始的内容
numbers[1:1] = [2, 3, 4]  利用分片赋值变相进行插入操作
a[len(a):] = b     利用分片赋值变相连接两个列表

列表方法：

方法：append
用于在列表末尾追击新的对象，修改原有列表
lst.append(2)  ###  [1, 2, 3]  得到  [1, 2, 3, 2]


方法：count
统计某个元素在列表中出现的次数
x.count(1)     ### [1, 1, 1, [1, 2], [2, 1]] 得到 3
x.count([1,2])   ###  [1, ,1, [1, 2], [2, 1]] 得到 1


方法：extend
用新列表扩展原有列表，修改原有列表
a = [1, 2, 3]
b = [4, 5, 6]
a + b         ###  得到 [1, 2, 3, 4, 5, 6]，但a不变
a.extend(b)   ###  得到 新 a 为 [1, 2, 3, 4, 5, 6]
a[len(a):] = b   ### 得到同样的结果，但代码可读性差


方法：index
从列表中找出第一个匹配项的索引，如果该元素不存在于列表中则会引发一个异常
knights = ['we', 'are', 'the', 'knights', 'who', 'say', 'ni']
knights.index('who')   ## 得到 4


方法：insert
将对象插入列表
numbers = [1, 2, 3, 5, 6, 7]
numbers.insert(3, 'four')  ## 得到 [1, 2, 3, 'four', 5, 6, 7]


方法：pop
移除列表中的一个元素，要么指定，默认移除最后一个(pop方法是唯一一个既能修改列表又能返回元素值的列表方法)
x = [1, 2, 3]
x.pop()             ## 得到3，同时x 变为 [1, 2]
x.pop(0)            ## 得到0， 同时x变为[2]

方法：remove
移除列表中某个值的第一个匹配项，注意到这个方法没有返回值
x = ['to', 'be', 'or', 'not', 'to', 'be']
x.remove('be')


方法：reverse
将列表中的元素反向，改变原列表，但是不返回值
x = [1, 2, 3] 
x.reverse()   ## 得到 [3, 2, 1]


方法：sort
在原位置对列表进行排序， 所以没有返回值
x = [4, 6, 2, 1, 7, 9]
x.sort()   ## 得到 [1, 2, 4, 6, 7, 9]
要想获得排序后的副本，得用下面的方式
x = [4, 6, 2, 1, 7, 9]
y = x[:]       ##  拷贝副本，然后副本赋值给y
y.sort()

方法：sorted
排序后返回一个副本，不改变排序前列表
x = [4, 6, 2, 1, 7, 9]
y = sorted(x)   ###  x不变，y变为 [1, 2, 4, 6, 7, 9]
sorted('Python')    ## 得到字符列表 ['P', 'h', 'n', 'o', 't', 'y']

高级排序：
numbers.sort(cmp)   ## cmp为内建比较函数， x<y返回负数
x.sort(key=len)   ###  key用来为每个元素创建一个键，然后根据键来排序，此处根据元素长度来排序
x.sort(reverse=True)  ###  不是默认升序，反向后是降序排序


		     ###############           字符串相关               #################

长字符串：
跨多行写字符串的时候，用三个引号括起来，前后合计六个引号
"""  This
is
a long 
string  """

原始字符串：
以r开头的字符串，输出时按原样输出，原始字符串不能以\结尾
print r'c:\Programs Files\a\b\c\d'

Unicode字符串(Unicode对象)
u'Hello,world!'


最简单的字符串格式化输出：
format = "Hello, %s. %s enough for ya?"
values = ('world', 'Hot')
print format % values             ###　注意到中间这个百分号
输出： Hello world. Hot enough for ya?
#######    注意：只有元组和字典可以格式化一个以上的值，例如此处的元组。列表只会被解释为一个值

要在格式化字符串里使用%   必须使用%%来表示 

模板字符串：

from string import Template
s = Template('$x, gloriousr $x!')
s.substitute(x='slurm')   用传递进来的关键字参数foo替换字符串中的$foo

s = Template("it's ${x}tastic!")
s.substitute(x='slurm')    ###   替换的是单词的一部分

s = Template("Make $$ selling $x!")
s.substitute(x='slurm')
输出：Make $ selling slurm!     ###  注意美元符号的输出方法，通过两个美元符号输出一个美元符号

s = Template('A $thing must never $action')
d = {}            ####  声明一个映射
d['thing'] = 'gentleman'
d['action'] = 'show his socks' 
s.substitute(d)    ####  用字典变量提供值/名称对

safe_substitute不会因为缺少值或者不正确使用$字符而出错

字符串方法：

方法：find
在较长的字符串中查找子字符串，返回子串所在位置的最左端索引，没找到返回-1
title.find('python')
subject.find('$$$', 1)  ##  提供查找起点
subject.find('###', 0, 16)  ### 提供起始点和结束点

方法：rfind

方法：rindex

方法：count

方法：startswith

方法：endswith


方法：join
字符串方法，将队列内的字符串元素合并成一个字符串
se = ['1', '2', '3', '4', '5']
sep = '+'
sep.join(se)  或者 '+'.join(se)
dirs = '', 'usr', 'bin', 'env'    ###   生成元组
'/'.join(dirs)

方法：lower
返回字符串的小写字母版
name = 'Gumby'
names = ['gumby', 'smith', 'jones']
if name.lower() in names: print 'Found it!'

方法：replace
返回某字符串所有匹配项均被替换后的字符串
'This is a test'.replace('is', 'eez')

方法：split
将字符串分割成序列，默认把空格作为分隔符
'1+2+3+4+5'.split('+')

方法：strip
返回去除两侧空格的字符串
'  abc  '.strip()
'*! abc !'.strip(' *!')   ### 指定去除的三个字符，注意只去除两侧的

方法：translate
替换字符串中的某些部分，只处理单个字符
from string import maketrans   ## 引入模块
table = maketrans('cs', 'kz')   ##   将ascii中的cs替换为kz
'this is an incredible test'.translate(table)  ###  替换其中的c和s
'this is an incredible test'.translate(table, ' ')  ### 可选的第二个参数用于指定要删除的字符

		     ###############           字典相关               #################

phonebook = {'Alice':'2341', 'Beth':'9102', 'Cecil':'3258'}    ### 创建字典

len(d) 返回d中键值对的数目
d[k] 返回关联到键k上的值
d[k]=v 将值关联到键k上
del d[k] 删除键为k的项
k in d  检查d中是否有含有键为k的项

phone = {          ###  嵌套的字典
	'Alice' : {
		'phone' : '2341',
		'addr'  : 'Foo drive 23'
	},
	
	'Beth' : {
		'phone' : '9102',
		'addr'  : 'Bar street 42'
	},
	
	'Cecil' : {
		'phone' : '3158',
		'addr'  : 'Baz avenue 90'
	}
}


 ###  字典的格式化字符串
phonebook = {'Beth':'9102', 'Alice':'2341', 'Cecil':'3258'}
"cecil's phone number is %(Cecil)s." % phonebook  ##  在转换说明符%后面加上()括起来的键


字典方法：

方法：clear
清除字典中所有的项，原地操作，无返回值
d.clear()
x['key'] = 'value'
y = x    ## y和x 指向同一份内容
x = {}   ## 此时y还是指向{'key':'value'}
x = y
x.clear()   ##  此时y为{}


方法：copy
返回一个具有相同键值对的新字典，浅复制，也就是指向同样的字典
y = x.copy()
在副本y中替换值时，x不受影响，但是若修改y中的某个值，x也受影响
x = {'username':'admin', 'machines':['foo', 'bar', 'baz']}
y = x.copy()
y['username'] = 'mlh'
y['machines'].remove('bar')
###  此时y为 { 'username':'mlh', 'machines':['foo', 'baz'] }
###  此时x为 { 'username':'admin', 'machines':['foo', 'baz']}
###  类似指针的用法，修改键的指向时，则指向新内容，原内容不变；修改键指向的对象的值时，该对象本身发生变化，所有指向该对象的指针都受到影响

深复制：
from copy import deepcopy
c = d.copy()   ### 浅复制
dc = deepcopy(d)   ### 深复制
修改d的值，c受影响而dc不受影响


方法：fromkeys
用给定的键建立新字典，每个键对应的值为None
{}.fromkeys(['name', 'age'])
dict.fromkeys(['name', 'age'], '(unknown)')   ### 自己提供字符串 (unknown) 做默认值

方法：get
宽松的访问字典项，不存在键值对时返回None
d.get('name')
d.get('name', 'N/A')   ### 第二项自己指定默认返回值


方法：has_key
检查字典中是否含有给出的键。Python 3.0中不包含这个函数
d.has_key('name')   ###　等同于　'name' in d


方法：items
将所有的字典项以列表方式返回，即每一个键值形成一个元组，所有的元组组成列表
d.items()

方法：iteritems
作用大致同上，但是返回迭代器对象

方法：keys
将字典中的键以列表形式返回

方法：iterkeys
将字典中的键以迭代器对象的形式返回

方法：values
将字典中的值以列表的形式返回

方法：itervalues
将字典中的值以迭代器对象的形式返回

方法：pop
获得对应于给定键的值，并从字典中删除这个键值对
d.pop('x')

方法：popitem
随机弹出一个键值对，因为不用首先获得键的列表，所以用来一个个移除并处理项很有效
d.popitem()

方法：setdefault
如果键不存在，设置相应的键值对并返回，如果存在，返回对应值，不改变字典
d.setdefault('name')   ###  得到键值对  name:None
d.setdefault('name', 'N/A')    ### 设置默认值为N/A，第二项可选


方法：update
利用一个字典项更新另外一个字典
d.update(x)

###########################    函数，方法    ############################

类型：str
print str(10000L)      用于把值转换为合理形式的字符串      
###  输出10000

函数：bool
用来转化其他值为布尔值
bool('hello, world')

函数：input
x = input("the meaning of life:")    
### 输入里面是输出参数，返回输入内容，此处要求输入是合法的python表达式，比如字符串都要加引号

函数：raw_input
x = raw_input("Enter a number:")
###  请求输入，把所有输入当做原始数据，放入字符串中返回

函数：print
输出，默认用空格分隔各个参数
print greeting, salutation, name         ####  三个变量作为参数输出
print greeting + ',', salution, name     ###  三个变量加一个常量,作为输出，greeting和,之间没有空格
print 'hello,',     ### 注意此处多了个, 与下一个输出在同一行打印
print 'world!'

函数：import
从模块导入函数
from somemodule import somefunction, anotherfunction, yetanotherfunction
from somemodule import *   ###　从给定模块导入所有功能
import math as foobar  ### 给math模块提供别名foobar以区分同名
from math import sqrt as foobar  ### 给函数sqrt提供别名

函数：pow
pow(x, y[, z])
## 返回x的y次幂（所得结果对z取模）
pow(2, 3)   
##  返回2的3次幂8

函数：round
round(number[, ndigis])   ###  根据给定精度对数字进行四舍五入
round(1.0/2.0)
###  将括号里面的内容四舍五入为最接近的整数


函数：repr
print repr(10000L)    ## 创建一个字符串，以合法的python表达式的形式表示，即返回值的字符串表现形式
用于连接字符串和数字时
###  输出10000L

函数：len
返回序列中所包含元素的数量


函数：min
返回序列中最小元素

函数：max
返回序列中最大元素

函数：list
创建列表，适用所有类型的序列
list('Hello')   ### 得到五个字符组成的列表

函数：tuple
以一个序列作为参数并把它转换为元组返回
tuple([1, 2 ,3])
tuple('abc')

函数：dict         ### 其本质是一个类型
通过其他映射或者(键,值)这样的序列对建立字典
items = [('name', 'Gumby'), ('age', 42)]
d = dict(items)
或者
d = dict(name='Gumby', age=42)

函数：range
获得某个数字区间列表，包含下限，不包含上限
range(1, 101)   ###  到100为止共100个数字
range(10)   ###  默认下限为0，只需要提供上限
range(99, 0, -1)  ###  第三个参数表示步长，设置为负值用于反向迭代

函数：zip
进行并行迭代，把两个序列压缩在一起，然后返回一个元组的列表
可以作用于任意多的序列。可以应付不等长的序列
zip(names, ages)
for name, age in zip(names, ages):
	print name, 'is', age, 'years old'
zip(range(5), xrange(1000000000000))

函数：enumerate
在提供索引的地方迭代 索引-值对
for index, string in enumerate(strings):
for index, string in enumerate(strings):
	if 'xxx' in string:
		strings[index] = '[censored]'

函数：sorted
sorted(seq[, cmp][, key][, reverse])  ###  可选的cmp用于比较，reverse进行反转
作用于序列或可迭代对象上，返回排序后的版本，返回列表
sorted([4,3,6,8,3])

函数：reversed
作用于序列或可迭代对象上，返回翻转后的版本，返回可迭代对象
list(reversed('Hello,world!'))

函数：chr
chr(n)
出入序号n时，返回n所代表的包含一个字符的字符串

函数：ord
ord(c)
返回单字符字符串的int值

函数：callable
callable(object)         ###  确定对象是否可调用(比如函数或者方法)
判断函数是不是可用，在3.0版本中已经不支持
callable(math.sqrt)

函数：help
在交互式解释器中得到函数相关信息
help(sqrt)

函数：vars
返回当前作用域字典，不可修改
x=1
scope = vars()
scope['x']    ###  得到 1

函数：global
在局部作用域内获得全局变量值
def combine(parameter):
	print parameter + global()['parameter']      ####  + 后获得外部全局变量
	
函数：map
map(func, seq[, seq, ...])
将序列中的全部元素传给一个函数，即对序列中的每个元素应用函数
map(str, range(10))  ##  等同于 [str(i) for i in range(10)]  得到10个数字的字符串序列

函数：filter
filter(func, seq)
基于一个返回布尔值的函数对元素进行过滤，即返回其函数为真的元素的列表
def func(x)
	return x.isalnum()
seq = ['foo', 'x41', '*****']
filter(func, seq)     ###  得到['foo', 'x41']
filter(lambda x: x.isalnum(), seq)   ### 使用lambda表达式更快的解决问题

函数：reduce
reduce(func, seq[, initial])
将序列的前两个元素与给定的函数联合使用，将返回值与第三个元素联合使用，直到整个序列处理完毕
reduce(lambda x, y:x+y, numbers)

函数：issubclass
issubclass(A, B)
查看一个类是否是另一个的子类
issubclass(SPAMFilter, Filter)

函数：isinstance
isinstance(object, class)
确定对象是否是类的实例

函数：hasattr
hasattr(object, name)
确定对象是否有给定的特性
hasattr(tc, 'talk')       ###  查看tc对象有没有talk方法
hasattr(x, '__call__')        ###  查看x是否可调用

函数：getattr
getattr(object, name[, default]
查看对象是否有某个函数，允许提供默认值，没有时返回默认值
callable(getattr(tc, 'talk', None)        ###  查看tc对象是否含有talk方法(继而检查是否可调用)，没有时返回默认None

函数：setattr
setattr(object, name, value)
设置对象的特性
setattr(tc, 'name', 'Mr.Gumby') ###  设置特性name为Mr.Gumby

函数：dir
列出模块的内容
dir(exceptions)

函数：iter
从可迭代的对象中获得迭代器
it = iter([1, 2, 3])
it.next()       ###  得到1



读写二进制文件：

from struct import *
a = "1f 8b 08 00 e7 f9 96 45 4f a7 b6 3d 9d dc f8 74 c9 c6 17 5b 96 02 15 15 a5 16 97 e6 94 00 00 00"
writeFile = "./responseBin"
f = open(writeFile, "wb")
binList = a.split(" ")

for byteStr in binList:
	byte = int("0x" + str(byteStr), 16)
	print byte
	data = pack("B", byte)
	f.write(data)
