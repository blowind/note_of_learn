

		     ###############           ģ�����               #################	

pythonʹ�����������import����ģ�飺
���뷽ʽһ��
import math       ## ������ѧ��
math.ceil(32.9)    ##  �õ�������33.0
math.sqrt(9)        ###  �õ�����3
int(math.floor(32.9))     ##  �õ�������32.0��Ȼ��ת��������

import cmath           ###  ���������Ŀ�
cmath.sqrt(-1)            ###  �õ�����1j��python����֧������

���뷽ʽ����
from math import sqrt
sqrt(9)        ###  ����ʽ����ģ������Ϊǰ׺
from __future__ import division           ###  ����futureģ�飬����ͨ��������Ϊ����������
from math import pi

import sys
sys.path.append('c:/python')      ###  ��ӵ�python����·����Ȼ����������·��������ģ��
sys.path.expanduser('~.python')   ###  �Զ�������Ĳ���
abc = reload(abc)                 ###   ����abcģ�飬�ڳ�������ʱ��̬����ģ�����Ч
import pprint
pprint.pprint(sys.path)                  ###  �������ܵ����path�д洢��·���б�
������ϵͳ������  PYTHONPATH  ��������

__name__   ### ����ģ������֣�����������Ϊ  __main__
��ģ���ڲ���д�Ĳ��Դ��룬ͨ��
if __name__ == '__main__':  ģ���ں���()  ����  ���Ժ���()
�ķ�ʽ�����У�ֱ������ģ��ʱ���ͻ�ִ����Щ���Դ���

��ģ�����ڵ��ļ��н���  __init__.py  �ļ���������ļ��оͳ�Ϊ��һ����
import �ļ�����           ###  ���������ʱ__init__.py��������ݿ��ã�����ģ�����ݲ�����
import �ļ�����.ģ����    ###���������ģ�飬ֻ��ͨ��  �ļ�����.ģ����.������  ��ʹ������
from �ļ����� import ģ����        ###����ͬ�ϣ�����ͨ��  ģ����.������  ��ʹ������

import copy
dir(copy)       ###  �鿴copy����������
copy.__all__     ###  �õ�ģ��Ĺ����ӿ�
help(copy.copy)    ###  �õ�copyģ����copy�������ĵ��ַ���
print range.__doc__    ###  �鿴range�Ĳ����͵��÷�ʽ
print copy.__file__          ###  ����copyԴ�������ڵ�·��

import sys    ### ��python��������ϵ���ܵı����ͺ���  argv, exit, modules, path, platform, stdin, stdout, stderr
import os     ### ���ʶ������ϵͳ����Ĺ���  environ, system, sep, pathsep, linesep, urandom
os.system("ls -al")  ### �����ⲿ����
os.system('/usr/bin/firefox')  ##  unixϵͳ�����ⲿ�����Ƽ�����
os.startfile(r'c:\Program Files\Mozilla Firefox\firefox.exe')     
 ### windowsϵͳ�����ⲿ�����Ƽ�����
import webbrowser
webbrowser.open('http://www.python.org')  ###  ����վ�ĺ÷���

import fileinput   ### �����ı��ļ���������  input, filename(), lineno(), filelineno(), isfirstline(), isstdin(), nextfile(), close()

import fileinput              ###   ��ĳ�������ļ�ÿ�м����к�
for line in fileinput.input(inplace=True):
	line = line.rstrip()
	num = fileinput.lineno()
	print '%-40s # %2i' % (line, num)
	
result = {}          ###  ͳ���ļ���ĳ�������ֶεĳ��ֵĴ���
for line in fileinput.input("./data.txt"):
	line = line.strip()
	out = line.split("/")
	if result.has_key(out[3]):             ## �˴�ͳ�Ƶ��ǵ�4���ֶ�(�±���3)
		result[out[3]] = result[out[3]] + 1
	else: 
		result[out[3]] = 1
	print out[3]
print result.items()
	

	###  ������ѧ�����ϵļ��ϣ��������ظ�Ԫ��
a = set([1, 2, 3])   ## ���ϣ���Ϊ�����ǿɱ�ģ����Բ�����Ϊ�ֵ�ļ�
b = set([2, 3, 4])   ## �����ϱ���ֻ�ܰ������ɱ�ĳ�Ա��һ�㼯�����ǲ��ܰ������ϵ� 
a.union(b)    ### �õ����� set([1,2,3,4])
c.issubset(a)
c.issuperset(a)
a.intersection(b)
a.difference(b)
a.symmetric_difference(b)
a.copy()
a.add(frozenset(b))    ###  ʹb���ɱ䣬�������ܰ�b������Ա����a����Ϊ���ϵ�Ԫ���ǲ��ɱ��

mySets = []              ###  ��δ�����Ҳ���ӡ���ϵĲ���
for i in range(10):
	mySets.append(set(range(i, i+5)))
reduce(set.union, mySets)       ### ��mySets�е�Ԫ��ͨ��set.union�������кϲ�


from heapq import *   ### ����"��"ģ��  heappush, heappop, heapify, heapreplace, nlargest, nsmallest
###  С���ѽṹ
for n in (range(10)):        ####  ����heap����ֻ���Ƕѽṹ�Ķ��У�һ����в������ڴ˴�
	heappush(heap, n)	
heappush(heap, x)   ## x���
heappop(heap)         ##  ������СԪ�ص���
heapify(heap)          ## ��heap����ǿ��Ӧ�õ�����һ���б�ʹ���Ϊ��
heapreplace(heap, x)    ## ��������СԪ�ص�����ͬʱx���
nlargest(n, iter)      ##  ����iter��ǰn���Ԫ��,  iterֻ��Ҫ�ǿɵ�������
nsmallest(n, iter)      ##  ����iter��ǰnС��Ԫ��

from collections import deque    ###  ����˫�˶���
q = deque(range(5))
q.append(5)            ###   ���Ҷ˼���Ԫ��5   ���ڵ���Ԫ��
q.appendleft(6)
q.pop()       ##  �Ҷ˳���
q.popleft()
q.rotate(3)     ###  ѭ��������λ
q.rotate(-1)     ###  ѭ������һλ
q.extend(seq)     ###  ���Ҷ˼���Ԫ���б�   �����б�
q.extendleft(seq)    ###  ����˼���Ԫ���б�ע�⵽seq��Ԫ�ػᷴ�������q��

from time import *        ###   ʱ��ģ��
time.asctime([tuple])      ###  ��ʱ��Ԫ��ת��Ϊ�ַ���
time.localtime([secs])     ###   ��ʵ��ת��Ϊ����ʱ�������Ԫ��
time.gmtime([secs])         ### ͬ�ϣ�������ȫ��ͳһʱ��
time.mktime(tuple)                ###  ������Ԫ��ת��Ϊ���¼�Ԫ(1970)��ʼ�������������localtime�෴
time.sleep(secs)           ###  �ý������ȴ�����������
time.strptime(string[, format])   ###  ���ַ�������Ϊʱ��Ԫ��

 
from random import *       ###  randomģ��
random.random()   ##  ����0~1֮���α�����n
random.uniform(a, b)   ##  ������a~b֮������ʵ��n(ƽ���ֲ�)
random.randrange([start, ]stop[, step])  ### ������start,stop֮������������������stop
random.choice(seq)     ## �Ӹ��������о�һ��ѡ�����Ԫ��
random.shuffle(seq[, random])  ### ���������е�Ԫ�ؽ��������λ
sample(seq, n)           ###  �Ӹ���������ѡ�������Ŀ��Ԫ�أ���֤Ԫ�ز��ظ�

import shelve    ###  ���ļ��ô洢����
s = shelve.open('test.dat')
s['x'] = ['a', 'b', 'c']
s['x'] = s['x'].append('d')




### redisģ�鰲װ�� 1��python��װĿ¼Scripts��ִ�� .\easy_install.exe pip ��װpip�� 2��ScriptsĿ¼�� .\pip2.7.exe install redis ��װredisģ��
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



		     ###############           ע������               #################	



1.0 // 2.0 ###   ˫б��ǿ�ƽ�������

2**3  ### ������   -2**3 = -9

x, y, z = 1, 2, 4   ###���������ͬʱ��ֵ

x, y = y, x   #### ��������������ֵ

key, value = scoundrel.popitem()   ###   ���н����ע�����н������Ԫ�ظ���Ҫ��ȫһ��

a,b,rest* = [1, 2, 3, 4]   ### ����3.0��ʹ���Ǻŵı�������ʣ�µ�����Ԫ��

x = y = somefunction()   #### ��ʽ��ֵ

python��ʹ��ð��:����ʶ����Ŀ�ʼ������ÿ����䶼��������
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

for key, value in d.items():        ###  ѭ���н�������Եõ���ֵ��
	print key, 'corresponds to', value

�ᱻ����Ϊ�ٵĲ������ʽ�� False  None 0 "" () [] {}

x is y       ### ��ͬһ������
x is not y   ## �ǲ�ͬ����
x in y       ## ������y�ĳ�Ա
x not in y    ### ��������y�ĳ�Ա

assert���ԣ�  ����  if not condition: carsh program  ����˼
age = -1
assert 0<age<100, 'The age must be realistic'  ##  ����һ���ַ������Ͷ���


break    ����ѭ��

continue  ������ǰ����

ѭ���е�else����ͬ����if��Ӧ��else
####  ע�⵽����python�ǰ������������ģ��˴�else��for��λ��ͬ����forѭ��ִ����Ϻ��ж�ִ��else�ڵĲ���
from math import sqrt
for n in range(99, 81, -1):
	root = sqrt(n)
	if root == int(root):
		print n
		break
else:
	print "Didn't find it!"

	
�б��Ƶ�ʽ��
[x*x for x in range(10)] ###  ����0(����)��10֮����������ƽ��
[x*x for x in range(10) if x % 3 == 0]  ### [0, 9, 36, 81]
[(x,y) for x in range(3) for y in range(3)]
####   �õ� [(0,0), (0,1), (0,2), (1,0), (1,1), (1,2), (2,0), (2,1), (2,2)]

[b+'+'+g for b in boys for g in girls if b[0]==g[0]]   ###  boys��girls�������б�
�������Ч�ʲ��ߣ��������Ƽ�����֮һ��
girls = ['alice', 'bernice', 'clarice']
boys = ['chris', 'arnold', 'bob']
letterGirls = {}
for girl in girls:
	letterGirls.setdefault(girl[0], []).append(girl)
print [b+'+'+g for b in boys for g in letterGrils[b[0]]]
#### ���Ե���ĸ��Ϊ����Ů��������ɵ��б���Ϊֵ�����ֵ䡣֮���Ƶ�ʽѭ�������к�����


pass  ʲô����������Ϊpython�пմ�����ǷǷ���
if name == 'Ralph':
	print 'Welcome'
elif name == 'Enid':
	pass
elif name == 'Bill':
	print 'Access Denied'
	
exec  ִ��һ���ַ������
exec "print 'Hello,world!'"  ##  ��ӡ�������
from math import sqrt
scope = {}
exec 'sqrt = 1' in scope
sqrt(4)   ###  �õ�2.0
scope['sqrt']  ##  �õ�1

eval  ����python���ʽ�����ؽ��
eval(raw_input("Enter an arithmetic expression: "))  ## �򵥼�����
scope = {}
scope['x'] = 2
scope['y'] = 3
eval('x * y', scope)  ###  �õ�6   �ṩ�����ռ�Ӷ�����

scope = {}
exec 'x = 2' in scope
eval('x*x', scope) 


def �������庯��
def hello(name):
	return 'hello, ' + name + '!'

def square(x):
	'Calculates the square of the number x.'   ###  �ĵ��ַ���
	return x*x
	
square.__doc__          ###  �����ĵ��ַ���

def hello(greeting, name):
	print '%s, %s!' % (greeting, name)
hello(greeting='hello', name='world')  ###  �ؼ��ֲ����������β�����\
def hello(greeting='hello', name='world')  ###  ���ùؼ��ֲ����ṩĬ�ϲ��������ֵ���������Ϊgreeting�Ǹ���ʱ����

def print_params(*params):     ###  �ռ��������γɲ���Ԫ����ڲ�ʹ��
def print_params_2(**params):    ###  �ܴ���ؼ��ֲ������ռ��������ؼ��ֲ������ֵ���ʽ��ʾ
def print_params_3(x, y, z=3, *pospar, **keypar):   ###   ����һ
ʾ���� print_params_3(1, 2, 3, 5, 6, 7, foo=1, bar=2)
�õ���1  2  3  (5, 6, 7)   {'foo':1, 'bar':2}

def add(x, y): return x+y
params = (1, 2)
add(*params)     ###  ��ת���̣�Ԫ��ֲ����

params = {'name':'Sir Robin', 'greeting':'Well met'}
hello(**params)       ###  �ֵ�ķ�ת����


global  �ھֲ�����������һ������Ϊ�ⲿ��ȫ�ֱ���
x=1
def change_global():
	global x
	x += 1
	
	
###  ������������return��������ֵ������ÿ�β������ֵ��ÿ�β���һ��ֵ�������ͻᱻ����
###  ��������������ȴ�����������ʹ�ֹͣ�ĵط���ʼִ��
def flatten(nested):
	try:
		try: nested + ''       ###  ��飬��Ҫ�����ַ������͵ı��������������ѭ�����ַ����ĵ�һ���ַ������ַ���
		except TypeError: pass
		else: raise TypeError
		for sublist in nested:
			for element in flatten(sublist):  ### �ݹ�����������������Ǻ�������֪չ��һ��Ԫ�أ��Ӷ�����һ��TypeError
				yield element      ###  �κΰ���yield�����ĺ�����Ϊ������
	except: TypeError:      
		yield nested        ###  
		
###  ��������������ͨ��(��)�汾
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
		
		
def repeater(value):      ###  ������������û����
	while True:
		new = (yield value)
		if new is not None: value = new
###  ����������throw��close��������

			
	
		     ###############           �����               #################	
	

__metaclass__ = type  ### ȷ��ʹ����ʽ�࣬��ʱ������type(s)�鿴ʵ������
class Person:                 ###  ����һ����
	def setName(self, name):
		self.name = name
	def getName(self):
		return self.name
	def __inaccessible(self):              ###  ����˫�»��߾ͳ���˽�з��������ⲿ�޷�����
		print "Bet you can't see me"  
		
class Filter:
	def init(self):
		self.blocked = []
class SPAMFilter(Filter):    ##   ���峬��
	def init(self):           ###  ��дinit
		self.blocked = ['SPAM']

SPAMFilter.__bases__      ###  �鿴��Ļ���
s = SPAMFilter()
isinstance(s, SPAMFilter)   ###   ���һ�������Ƿ���һ�����ʵ��
s.__class__                  ###   �鿴һ�����������ĸ���
s.__dict__                    ###  �鿴���������д洢��ֵ

class FooBar:
	def __init__(self):     ###  ����һ�����췽��
		self.somevar = 42
		
__del__   ��������������������ʹ��

super������ʹ��
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
		super(SongBird, self).__init__()           ###�������ó���Ĺ��췽��
		self.sound = 'Squawk!'
	def sing(self):
		print self.sound
		
__len__(self)    �÷������ؼ�����������Ŀ������
__getitem__(self, key)   �÷�����������������Ӧ��ֵ
__setitem__(self, key, value)   �÷�����һ���ķ�ʽ�洢��key��ص�value
__delitem__(self, key)         ɾ������Ͷ�Ӧ�ļ�

__getattribute__(self, name)   ������name������ʱ�Զ�������
__getattr__(self, name)        ������name�������Ҷ���û����Ӧ������ʱ���Զ�����
__setattr__(self, name, value)   ����ͼ������name��ֵʱ���Զ�������
__delattr__(self, name)        ����ͼɾ������nameʱ���Զ�����


__metaclass__ = type
class Rectangle:
	def __init__(self):
		self.width = 0
		self.height = 0
	def setSize(self, size):
		self.width, self.height = size
	def getSize(self):
		return self.width, self.height
	size = property(getSize, setSize)         #### �������ԣ�������������������������
	
class MyClass:
	@staticmethod             #####  ����һ����̬����
	def smeth():
		print 'This is a static method'
	@classmethod                ####  ����һ���෽��
	def cmeth(cls):
		print 'This is a class method' . cls
		

####   һ��ʵ����__iter__�����Ķ����ǿɵ����ģ�һ��ʵ����next�����Ķ����ǵ�����		
class Fibs:
	def __init__(self):
		self.a = 0
		self.b = 1
	def next(self):
		self.a, self.b = self.b, self.a+self.b
		return self.a
	def __iter__(self):          ###  ���ص���������
		return self
		

		

		     ###############           �쳣���               #################
		
		
raise Exception('hyperdrive overload')    ### �����쳣
raise  ��������ʱ���Ὣ������쳣������ȥ

try:                                         ####  �����쳣
	x = input('Enter the first number:')
	y = input('Enter the second number:')
	print x/y
except ZeroDivisionError:
	print "The second number can't be zero!"
except TypeError:
	print "That wasn't a number. Was it?"
except (IOError, AttributeError), e:           ####  ����쳣�ϲ���һ��Ҫ����Ԫ���Ҫʹ���쳣����ʱ�ӵڶ�������
	print e
except:                               ###    ���������쳣�����Ƽ�
	print 'Something wrong happened'
else:                                  ### û�����쳣ʱִ��
	print 'no exception'            
finally:
	print 'Cleaning up'            ###  ���϶���ִ�еĲ���
		
		     ###############           �б���ز���               #################


�����������Ű�Χ��[1, 2, 3]
Ԫ����Բ���Ű�Χ��(1, 2, 3)
ӳ���ô����Ű�Χ��d = {}        ###    ���   �ֵ����
����һ��Ԫ�ص�Ԫ�飺(1,)
3*(40+2)   ## �õ�126
3*(40+2,)  ## �õ�(42, 42, 42)  ��Ϊ�Ӷ���֮����Ԫ��

�б�ֵ
x = y   x��yָ��ͬһ���б�
x = y[:]  ��y�б�����ݸ��Ƶ�x�Ŀ�����

numbers[0:10:1]    ��Ƭ
��һ������Ϊ��Ƭ���ݵ�һ��Ԫ���������ڶ�������Ϊ��Ƭ���ݽ������һ��Ԫ������������������Ϊ����
numbers[::2]
������Ϊż����������ȡ����
numbers[::-2]
����������ȡԪ��

name = list('Perl')   ##  �õ��ַ��б� ['P', 'e', 'r', 'l']
name[1:0] = list('ython')   ##  �õ��ַ��б�  ['P', 'y', 't', 'h', 'o', 'n']

numbers = [1, 5]
numbers[1:1] = [2, 3, 4]   ## ��������Ԫ�أ��õ� [1, 2, 3, 4, 5]
numbers[1:4] = []    ###  ͨ�����滻��ɾ��Ԫ�أ��õ� [1, 5]  

sequence = [None]*10   ��ʼ��ӵ��ʮ��Ԫ�صĿ��б�

in   ��Ա�ʸ���
x in 'six'

del names[2]   ɾ���б�Ԫ��
del names[2:4]  ɾ�������б�Ԫ��

name[2:] = list('ar')    ��Ƭ��ֵ����a��r�滻name�е�����Ԫ�ؿ�ʼ������
numbers[1:1] = [2, 3, 4]  ���÷�Ƭ��ֵ������в������
a[len(a):] = b     ���÷�Ƭ��ֵ�������������б�

�б�����

������append
�������б�ĩβ׷���µĶ����޸�ԭ���б�
lst.append(2)  ###  [1, 2, 3]  �õ�  [1, 2, 3, 2]


������count
ͳ��ĳ��Ԫ�����б��г��ֵĴ���
x.count(1)     ### [1, 1, 1, [1, 2], [2, 1]] �õ� 3
x.count([1,2])   ###  [1, ,1, [1, 2], [2, 1]] �õ� 1


������extend
�����б���չԭ���б��޸�ԭ���б�
a = [1, 2, 3]
b = [4, 5, 6]
a + b         ###  �õ� [1, 2, 3, 4, 5, 6]����a����
a.extend(b)   ###  �õ� �� a Ϊ [1, 2, 3, 4, 5, 6]
a[len(a):] = b   ### �õ�ͬ���Ľ����������ɶ��Բ�


������index
���б����ҳ���һ��ƥ����������������Ԫ�ز��������б����������һ���쳣
knights = ['we', 'are', 'the', 'knights', 'who', 'say', 'ni']
knights.index('who')   ## �õ� 4


������insert
����������б�
numbers = [1, 2, 3, 5, 6, 7]
numbers.insert(3, 'four')  ## �õ� [1, 2, 3, 'four', 5, 6, 7]


������pop
�Ƴ��б��е�һ��Ԫ�أ�Ҫôָ����Ĭ���Ƴ����һ��(pop������Ψһһ�������޸��б����ܷ���Ԫ��ֵ���б���)
x = [1, 2, 3]
x.pop()             ## �õ�3��ͬʱx ��Ϊ [1, 2]
x.pop(0)            ## �õ�0�� ͬʱx��Ϊ[2]

������remove
�Ƴ��б���ĳ��ֵ�ĵ�һ��ƥ���ע�⵽�������û�з���ֵ
x = ['to', 'be', 'or', 'not', 'to', 'be']
x.remove('be')


������reverse
���б��е�Ԫ�ط��򣬸ı�ԭ�б����ǲ�����ֵ
x = [1, 2, 3] 
x.reverse()   ## �õ� [3, 2, 1]


������sort
��ԭλ�ö��б�������� ����û�з���ֵ
x = [4, 6, 2, 1, 7, 9]
x.sort()   ## �õ� [1, 2, 4, 6, 7, 9]
Ҫ���������ĸ�������������ķ�ʽ
x = [4, 6, 2, 1, 7, 9]
y = x[:]       ##  ����������Ȼ�󸱱���ֵ��y
y.sort()

������sorted
����󷵻�һ�����������ı�����ǰ�б�
x = [4, 6, 2, 1, 7, 9]
y = sorted(x)   ###  x���䣬y��Ϊ [1, 2, 4, 6, 7, 9]
sorted('Python')    ## �õ��ַ��б� ['P', 'h', 'n', 'o', 't', 'y']

�߼�����
numbers.sort(cmp)   ## cmpΪ�ڽ��ȽϺ����� x<y���ظ���
x.sort(key=len)   ###  key����Ϊÿ��Ԫ�ش���һ������Ȼ����ݼ������򣬴˴�����Ԫ�س���������
x.sort(reverse=True)  ###  ����Ĭ�����򣬷�����ǽ�������


		     ###############           �ַ������               #################

���ַ�����
�����д�ַ�����ʱ��������������������ǰ��ϼ���������
"""  This
is
a long 
string  """

ԭʼ�ַ�����
��r��ͷ���ַ��������ʱ��ԭ�������ԭʼ�ַ���������\��β
print r'c:\Programs Files\a\b\c\d'

Unicode�ַ���(Unicode����)
u'Hello,world!'


��򵥵��ַ�����ʽ�������
format = "Hello, %s. %s enough for ya?"
values = ('world', 'Hot')
print format % values             ###��ע�⵽�м�����ٷֺ�
����� Hello world. Hot enough for ya?
#######    ע�⣺ֻ��Ԫ����ֵ���Ը�ʽ��һ�����ϵ�ֵ������˴���Ԫ�顣�б�ֻ�ᱻ����Ϊһ��ֵ

Ҫ�ڸ�ʽ���ַ�����ʹ��%   ����ʹ��%%����ʾ 

ģ���ַ�����

from string import Template
s = Template('$x, gloriousr $x!')
s.substitute(x='slurm')   �ô��ݽ����Ĺؼ��ֲ���foo�滻�ַ����е�$foo

s = Template("it's ${x}tastic!")
s.substitute(x='slurm')    ###   �滻���ǵ��ʵ�һ����

s = Template("Make $$ selling $x!")
s.substitute(x='slurm')
�����Make $ selling slurm!     ###  ע����Ԫ���ŵ����������ͨ��������Ԫ�������һ����Ԫ����

s = Template('A $thing must never $action')
d = {}            ####  ����һ��ӳ��
d['thing'] = 'gentleman'
d['action'] = 'show his socks' 
s.substitute(d)    ####  ���ֵ�����ṩֵ/���ƶ�

safe_substitute������Ϊȱ��ֵ���߲���ȷʹ��$�ַ�������

�ַ���������

������find
�ڽϳ����ַ����в������ַ����������Ӵ�����λ�õ������������û�ҵ�����-1
title.find('python')
subject.find('$$$', 1)  ##  �ṩ�������
subject.find('###', 0, 16)  ### �ṩ��ʼ��ͽ�����

������rfind

������rindex

������count

������startswith

������endswith


������join
�ַ����������������ڵ��ַ���Ԫ�غϲ���һ���ַ���
se = ['1', '2', '3', '4', '5']
sep = '+'
sep.join(se)  ���� '+'.join(se)
dirs = '', 'usr', 'bin', 'env'    ###   ����Ԫ��
'/'.join(dirs)

������lower
�����ַ�����Сд��ĸ��
name = 'Gumby'
names = ['gumby', 'smith', 'jones']
if name.lower() in names: print 'Found it!'

������replace
����ĳ�ַ�������ƥ��������滻����ַ���
'This is a test'.replace('is', 'eez')

������split
���ַ����ָ�����У�Ĭ�ϰѿո���Ϊ�ָ���
'1+2+3+4+5'.split('+')

������strip
����ȥ������ո���ַ���
'  abc  '.strip()
'*! abc !'.strip(' *!')   ### ָ��ȥ���������ַ���ע��ֻȥ�������

������translate
�滻�ַ����е�ĳЩ���֣�ֻ�������ַ�
from string import maketrans   ## ����ģ��
table = maketrans('cs', 'kz')   ##   ��ascii�е�cs�滻Ϊkz
'this is an incredible test'.translate(table)  ###  �滻���е�c��s
'this is an incredible test'.translate(table, ' ')  ### ��ѡ�ĵڶ�����������ָ��Ҫɾ�����ַ�

		     ###############           �ֵ����               #################

phonebook = {'Alice':'2341', 'Beth':'9102', 'Cecil':'3258'}    ### �����ֵ�

len(d) ����d�м�ֵ�Ե���Ŀ
d[k] ���ع�������k�ϵ�ֵ
d[k]=v ��ֵ��������k��
del d[k] ɾ����Ϊk����
k in d  ���d���Ƿ��к��м�Ϊk����

phone = {          ###  Ƕ�׵��ֵ�
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


 ###  �ֵ�ĸ�ʽ���ַ���
phonebook = {'Beth':'9102', 'Alice':'2341', 'Cecil':'3258'}
"cecil's phone number is %(Cecil)s." % phonebook  ##  ��ת��˵����%�������()�������ļ�


�ֵ䷽����

������clear
����ֵ������е��ԭ�ز������޷���ֵ
d.clear()
x['key'] = 'value'
y = x    ## y��x ָ��ͬһ������
x = {}   ## ��ʱy����ָ��{'key':'value'}
x = y
x.clear()   ##  ��ʱyΪ{}


������copy
����һ��������ͬ��ֵ�Ե����ֵ䣬ǳ���ƣ�Ҳ����ָ��ͬ�����ֵ�
y = x.copy()
�ڸ���y���滻ֵʱ��x����Ӱ�죬�������޸�y�е�ĳ��ֵ��xҲ��Ӱ��
x = {'username':'admin', 'machines':['foo', 'bar', 'baz']}
y = x.copy()
y['username'] = 'mlh'
y['machines'].remove('bar')
###  ��ʱyΪ { 'username':'mlh', 'machines':['foo', 'baz'] }
###  ��ʱxΪ { 'username':'admin', 'machines':['foo', 'baz']}
###  ����ָ����÷����޸ļ���ָ��ʱ����ָ�������ݣ�ԭ���ݲ��䣻�޸ļ�ָ��Ķ����ֵʱ���ö��������仯������ָ��ö����ָ�붼�ܵ�Ӱ��

��ƣ�
from copy import deepcopy
c = d.copy()   ### ǳ����
dc = deepcopy(d)   ### ���
�޸�d��ֵ��c��Ӱ���dc����Ӱ��


������fromkeys
�ø����ļ��������ֵ䣬ÿ������Ӧ��ֵΪNone
{}.fromkeys(['name', 'age'])
dict.fromkeys(['name', 'age'], '(unknown)')   ### �Լ��ṩ�ַ��� (unknown) ��Ĭ��ֵ

������get
���ɵķ����ֵ�������ڼ�ֵ��ʱ����None
d.get('name')
d.get('name', 'N/A')   ### �ڶ����Լ�ָ��Ĭ�Ϸ���ֵ


������has_key
����ֵ����Ƿ��и����ļ���Python 3.0�в������������
d.has_key('name')   ###����ͬ�ڡ�'name' in d


������items
�����е��ֵ������б�ʽ���أ���ÿһ����ֵ�γ�һ��Ԫ�飬���е�Ԫ������б�
d.items()

������iteritems
���ô���ͬ�ϣ����Ƿ��ص���������

������keys
���ֵ��еļ����б���ʽ����

������iterkeys
���ֵ��еļ��Ե������������ʽ����

������values
���ֵ��е�ֵ���б����ʽ����

������itervalues
���ֵ��е�ֵ�Ե������������ʽ����

������pop
��ö�Ӧ�ڸ�������ֵ�������ֵ���ɾ�������ֵ��
d.pop('x')

������popitem
�������һ����ֵ�ԣ���Ϊ�������Ȼ�ü����б���������һ�����Ƴ������������Ч
d.popitem()

������setdefault
����������ڣ�������Ӧ�ļ�ֵ�Բ����أ�������ڣ����ض�Ӧֵ�����ı��ֵ�
d.setdefault('name')   ###  �õ���ֵ��  name:None
d.setdefault('name', 'N/A')    ### ����Ĭ��ֵΪN/A���ڶ����ѡ


������update
����һ���ֵ����������һ���ֵ�
d.update(x)

###########################    ����������    ############################

���ͣ�str
print str(10000L)      ���ڰ�ֵת��Ϊ������ʽ���ַ���      
###  ���10000

������bool
����ת������ֵΪ����ֵ
bool('hello, world')

������input
x = input("the meaning of life:")    
### ������������������������������ݣ��˴�Ҫ�������ǺϷ���python���ʽ�������ַ�����Ҫ������

������raw_input
x = raw_input("Enter a number:")
###  �������룬���������뵱��ԭʼ���ݣ������ַ����з���

������print
�����Ĭ���ÿո�ָ���������
print greeting, salutation, name         ####  ����������Ϊ�������
print greeting + ',', salution, name     ###  ����������һ������,��Ϊ�����greeting��,֮��û�пո�
print 'hello,',     ### ע��˴����˸�, ����һ�������ͬһ�д�ӡ
print 'world!'

������import
��ģ�鵼�뺯��
from somemodule import somefunction, anotherfunction, yetanotherfunction
from somemodule import *   ###���Ӹ���ģ�鵼�����й���
import math as foobar  ### ��mathģ���ṩ����foobar������ͬ��
from math import sqrt as foobar  ### ������sqrt�ṩ����

������pow
pow(x, y[, z])
## ����x��y���ݣ����ý����zȡģ��
pow(2, 3)   
##  ����2��3����8

������round
round(number[, ndigis])   ###  ���ݸ������ȶ����ֽ�����������
round(1.0/2.0)
###  �����������������������Ϊ��ӽ�������


������repr
print repr(10000L)    ## ����һ���ַ������ԺϷ���python���ʽ����ʽ��ʾ��������ֵ���ַ���������ʽ
���������ַ���������ʱ
###  ���10000L

������len
����������������Ԫ�ص�����


������min
������������СԪ��

������max
�������������Ԫ��

������list
�����б������������͵�����
list('Hello')   ### �õ�����ַ���ɵ��б�

������tuple
��һ��������Ϊ����������ת��ΪԪ�鷵��
tuple([1, 2 ,3])
tuple('abc')

������dict         ### �䱾����һ������
ͨ������ӳ�����(��,ֵ)���������жԽ����ֵ�
items = [('name', 'Gumby'), ('age', 42)]
d = dict(items)
����
d = dict(name='Gumby', age=42)

������range
���ĳ�����������б��������ޣ�����������
range(1, 101)   ###  ��100Ϊֹ��100������
range(10)   ###  Ĭ������Ϊ0��ֻ��Ҫ�ṩ����
range(99, 0, -1)  ###  ������������ʾ����������Ϊ��ֵ���ڷ������

������zip
���в��е���������������ѹ����һ��Ȼ�󷵻�һ��Ԫ����б�
�������������������С�����Ӧ�����ȳ�������
zip(names, ages)
for name, age in zip(names, ages):
	print name, 'is', age, 'years old'
zip(range(5), xrange(1000000000000))

������enumerate
���ṩ�����ĵط����� ����-ֵ��
for index, string in enumerate(strings):
for index, string in enumerate(strings):
	if 'xxx' in string:
		strings[index] = '[censored]'

������sorted
sorted(seq[, cmp][, key][, reverse])  ###  ��ѡ��cmp���ڱȽϣ�reverse���з�ת
���������л�ɵ��������ϣ����������İ汾�������б�
sorted([4,3,6,8,3])

������reversed
���������л�ɵ��������ϣ����ط�ת��İ汾�����ؿɵ�������
list(reversed('Hello,world!'))

������chr
chr(n)
�������nʱ������n������İ���һ���ַ����ַ���

������ord
ord(c)
���ص��ַ��ַ�����intֵ

������callable
callable(object)         ###  ȷ�������Ƿ�ɵ���(���纯�����߷���)
�жϺ����ǲ��ǿ��ã���3.0�汾���Ѿ���֧��
callable(math.sqrt)

������help
�ڽ���ʽ�������еõ����������Ϣ
help(sqrt)

������vars
���ص�ǰ�������ֵ䣬�����޸�
x=1
scope = vars()
scope['x']    ###  �õ� 1

������global
�ھֲ��������ڻ��ȫ�ֱ���ֵ
def combine(parameter):
	print parameter + global()['parameter']      ####  + �����ⲿȫ�ֱ���
	
������map
map(func, seq[, seq, ...])
�������е�ȫ��Ԫ�ش���һ�����������������е�ÿ��Ԫ��Ӧ�ú���
map(str, range(10))  ##  ��ͬ�� [str(i) for i in range(10)]  �õ�10�����ֵ��ַ�������

������filter
filter(func, seq)
����һ�����ز���ֵ�ĺ�����Ԫ�ؽ��й��ˣ��������亯��Ϊ���Ԫ�ص��б�
def func(x)
	return x.isalnum()
seq = ['foo', 'x41', '*****']
filter(func, seq)     ###  �õ�['foo', 'x41']
filter(lambda x: x.isalnum(), seq)   ### ʹ��lambda���ʽ����Ľ������

������reduce
reduce(func, seq[, initial])
�����е�ǰ����Ԫ��������ĺ�������ʹ�ã�������ֵ�������Ԫ������ʹ�ã�ֱ���������д������
reduce(lambda x, y:x+y, numbers)

������issubclass
issubclass(A, B)
�鿴һ�����Ƿ�����һ��������
issubclass(SPAMFilter, Filter)

������isinstance
isinstance(object, class)
ȷ�������Ƿ������ʵ��

������hasattr
hasattr(object, name)
ȷ�������Ƿ��и���������
hasattr(tc, 'talk')       ###  �鿴tc������û��talk����
hasattr(x, '__call__')        ###  �鿴x�Ƿ�ɵ���

������getattr
getattr(object, name[, default]
�鿴�����Ƿ���ĳ�������������ṩĬ��ֵ��û��ʱ����Ĭ��ֵ
callable(getattr(tc, 'talk', None)        ###  �鿴tc�����Ƿ���talk����(�̶�����Ƿ�ɵ���)��û��ʱ����Ĭ��None

������setattr
setattr(object, name, value)
���ö��������
setattr(tc, 'name', 'Mr.Gumby') ###  ��������nameΪMr.Gumby

������dir
�г�ģ�������
dir(exceptions)

������iter
�ӿɵ����Ķ����л�õ�����
it = iter([1, 2, 3])
it.next()       ###  �õ�1



��д�������ļ���

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
