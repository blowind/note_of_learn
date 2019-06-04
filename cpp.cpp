extern相关：
extern int i;        //   声明一个变量，但未定义
extern声明在函数外部时，才可以含有初始化式，此时声明被当做定义
extern double pi = 3.1415   //  合法，已定义并初始化

心得：extern声明多放在头文件a.h中，然后在对应的a.cpp文件中定义（可以包含赋值）该变量。在预处理的时候，所有包含该头文件的a.cpp, b.cpp等文件都会把头文件复制到该cpp文件中，所以如果在头文件中定义变量，则头文件展开之后会在a.cpp和b.cpp中都包含该变量定义，重复定义导致编译错误。但如果只是放入声明，头文件展开之后复制的只是声明，因为声明可以多次，而定义永远只在原来的a.cpp文件中，所以编译成功。此外，如果b.cpp不想包含头文件a.h而又想使用a.cpp文件中的变量 int i ，则直接把该变量的extern形式，即 extern int i; 放入b.cpp，此时相当于不预处理时扩展头文件的操作手动完成

extern "C" {   //  告诉编译器内部代码用C语言风格修饰，即名字前面加_
	int func(int);
	int var;
}
或者
extern "C" int var;

#ifdef __cplusplus          //  同一个文件即用C编译器又用C++编译器时解决名称修饰问题的办法
extern "C" {
#endif

void *memset (void *, int, size_t);

#ifdef __cplusplus	
}
#endif


register相关：
register int a;     //  寄存器变量是没有地址的.因为寄存器变量不是在内存中.它存在CPU中的寄存器中.
register关键字在c语言和c++中的差异 
在c++中：
(1)register 关键字无法在全局中定义变量，否则会被提示为不正确的存储类。
(2)register 关键字在局部作用域中声明时，可以用 & 操作符取地址，一旦使用了取地址操作符，被定义的变量会强制存放在内存中
在c中:
(1)register 关键字可以在全局中定义变量，当对其变量使用 & 操作符时，只是警告“有坏的存储类”。
(2)register 关键字可以在局部作用域中声明，但这样就无法对其使用 & 操作符。否则编译不通过。
建议不要用register关键字定义全局变量，因为全局变量的生命周期是从执行程序开始，一直到程序结束才会终止，而register变量可能会存放在cpu的寄存器中，如果在程序的整个生命周期内都占用着寄存器的话，这是个相当不好的举措。
用register关键字修饰的变量，在C语言中是不可以用&操作符取地址的，这是我已有的经验。
因为编译器如果接受了程序员的建议把变量存入寄存器，它是不存在虚拟地址的。但在C++中，用register修饰的变量可以用&操作符取地址，这是我在一段代码中发现的。如果程序中显式取了register变量的地址，编译器一定会将这个变量定义在内存中，而不会定义为寄存器变量。


volatile相关：
volatile int i = 10; 
volatile 关键字是一种类型修饰符，用它声明的类型变量表示可以被某些编译器未知的因素更改。
比如：操作系统、硬件或者其它线程等。遇到这个关键字声明的变量，编译器对访问该变量的代码就不再进行优化，从而可以提供对特殊地址的稳定访问。声明时语法：int volatile vInt; 当要求使用 volatile 声明的变量的值的时候，系统总是重新从它所在的内存读取数据，即使它前面的指令刚刚从该处读取过数据。而且读取的数据立刻被保存。 
volatile指出i是随时可能发生变化的，每次使用它的时候必须从i的地址中读取，因而编译器生成的汇编代码会重新从i的地址读取数据放在 b 中。而优化做法是，由于编译器发现两次从 i读数据的代码之间的代码没有对i进行过操作，它会自动把上次读的数据放在 b 中。而不是重新从 i 里面读。这样以来，如果 i是一个寄存器变量或者表示一个端口数据就容易出错，所以说 volatile 可以保证对特殊地址的稳定访问。
#include <stdio.h>
void main() {    // VS中debug模式下都输出10，release模式下输出10和32
	volatile int i = 10;
	int a = i;
	printf("i = %d\n", a);
	// 下面汇编语句的作用就是改变内存中 i 的值
	// 但是又不让编译器知道
	__asm
	{
		MOV DWORD PTR[EBP - 4], 20H
	}
	int b = i;
	printf("i = %d\n", b);
}
其实不只是“内嵌汇编操纵栈”这种方式属于编译无法识别的变量改变，另外更多的可能是多线程并发访问共享变量时，一个线程改变了变量的值，怎样让改变后的值对其它线程 visible。一般说来，volatile 用在如下的几个地方：
1) 中断服务程序中修改的供其它程序检测的变量需要加 volatile；
2) 多任务环境下各任务间共享的标志应该加 volatile；
3) 存储器映射的硬件寄存器通常也要加 volatile 说明，因为每次对它的读写都可能由不同意义。
volatile 指针：
和 const 修饰词类似，const 有常量指针和指针常量的说法，volatile 也有相应的概念：
修饰由指针指向的对象、数据是 const 或 volatile 的：
const char* cpch;
volatile char* vpch;
指针自身的值——一个代表地址的整数变量，是 const 或 volatile 的：
char* const pchc;
char* volatile pchv;
注意：
(1) 可以把一个非 volatile int 赋给 volatile int，但是不能把非 volatile 对象赋给一个 volatile 对象。
(2) 除了基本类型外，对用户定义类型也可以用 volatile 类型进行修饰。
(3) C++ 中一个有 volatile 标识符的类只能访问它接口的子集，一个由类的实现者控制的子集。用户只能用 const_cast 来获得对类型接口的完全访问。此外，  volatile 向 const 一样会从类传递到它的成员。
多线程下的 volatile ：
有些变量是用volatile关键字声明的。当两个线程都要用到某一个变量且该变量的值会被改变时，应该用volatile声明，该关键字的作用是防止优化编译器把变量从内存装入CPU寄存器中。如果变量被装入寄存器，那么两个线程有可能一个使用内存中的变量，一个使用寄存器中的变量，这会造成程序的错误执行。volatile的意思是让编译器每次操作该变量时一定要从内存中真正取出，而不是使用已经存在寄存器中的值，如下：
volatile BOOL bStop = FALSE; 
(1) 在一个线程中：
while( !bStop ) { ... } 
bStop = FALSE; 
return; 
(2) 在另外一个线程中，要终止上面的线程循环： 
bStop = TRUE; 
while( bStop ); //等待上面的线程终止
如果 bStop 不使用 volatile 申明，那么这个循环将是一个死循环，因为 bStop 已经读取到了寄存器中，寄存器中 bStop 的值永远不会变成 FALSE，加上 volatile，程序在执行时，每次均从内存中读出 bStop 的值，就不会死循环了。
这个关键字是用来设定某个对象的存储位置在内存中，而不是寄存器中。因为一般的对象编译器可能会将其的拷贝放在寄存器中用以加快指令的执行速度，例如下段代码中：        (简单的临界区实现？？？？？信号量机制？？？？？)
... 
int nMyCounter = 0; 
for(; nMyCounter<100;nMyCounter++) 
{ 
... 
} 
... 
在此段代码中，nMyCounter 的拷贝可能存放到某个寄存器中（循环中，对 nMyCounter 的测试及操作总是对此寄存器中的值进行），但是另外又有段代码执行了这样的操作：nMyCounter -= 1;这个操作中，对 nMyCounter 的改变是对内存中的 nMyCounter 进行操作，于是出现了这样一个现象：nMyCounter 的改变不同步。


const相关：
const int bufSize = 512;     //  把对象转化成一个常量，必须初始化，
在全局作用域声明的const变量(不加extern)是定义该对象的文件的局部变量，不能被其他文件访问，作用域等同于static
extern const int bufSize = 512;    //  声明并初始化一个可在其他文件中访问的const变量
extern const int bufSize;            //  声明bufSize变量，使其可以在当前文件被访问

const引用是指向const对象的引用
const int ival = 1024;
const int &refVal = ival;       //  const引用可以读取但不能修改
const int &r = 42;            // 合法，可以引用字面值常量
int i = 42;
const int &r2 = r + i;       //  合法，因为编译时可以将此值计算出来，编译期可以确定的值可以进行引用

int ival = 1024;
int &refVal = ival;         //  引用，引用必须初始化

typedef int exam_score         //  定义类型的同义词

enum open_modes {input, output, append};
enum Points { point2d = 2, point2w,               //此处point2w和point3d都是3
			  point3d = 3, point3w };
			  
用class关键字来定义类，定义在第一个访问标号前的任何成员都隐式指定为private
用struct关键字来定义类，定义在第一个访问标号前的任何成员都隐式指定为public


#ifndef SALESITEM_H         //  预防多次包含同一头文件
#define SALESITEM_H
#endif

using std::cin;             // 使用cin时不用加std命名空间前缀
在头文件中，必须总是使用完全限定的标准库名字，不能用上面的简写

标准库string类型
#include <string>
using std::string;
四种初始化方式：
string s1;     // 调用默认构造函数
string s2(s1);
string s3("value");
string s4(n, 'c');        //  初始化为字符c的n个副本

sizeof(string);   // sizeof string类型和实例的大小都是固定的32字节，不管该实例存储的字符串有多大，猜测里面应该有指针

string操作:
s.empty();    // s为空串返回true
s.size();     //  返回s中字符的个数
s.c_str();    // 将s转换成C风格字符串返回（结尾多了\0）
//      接下来对s的操作可能改变s，使返回数组失效，所以最好对转换的数组进行浅拷贝

string::size_type         //  string中用来进行索引的类内置类型，索引变量类型最好用这个声明

标准库vector类型
#include <vector>
using std::vector;
四种初始化方式：
vector<T> v1;
vector<T> v2(v1);
vector<T> v3(n, i);           //  包含n个值为i的元素
vector<T> v4(n);              //  n是个整数，定义含值初始化的元素的n个副本

vector操作:
v.empty();
v.size();
v.push_back(t);              //  在v的末尾添加一个值为t的元素

vector<int>::iterator iter = ivec.begin();   // 声明一个迭代器类型，并用容器对象初始化
*iter = 0;                       //  使用解引用操作符来访问迭代器所指向的元素
iter++;                          //  迭代器自增
vector<string>::const_iterator iter = text.begin();    // 声明一个const_iterator，只读
iter++;                          //    合法
////      const_iterator 自身的值可以改变，但不能改变其所指向的元素的值，用于只读迭代时
const vector<int>::iterator cit = nums.begin(); // cit指向的内容可以改变，但是自身不可更改
//  因此const 类型的迭代器没有意义，因为不能用来迭代
*cit = 1;                        //    合法

const size_t arr_size = 6;
int int_arr[arr_size] = {0, 1, 2, 3, 4, 5};
vector<int> ivec(int_arr, int_arr + arr_size); //使用数组初始化vector，要给出数组首尾地址

vector<int> array;
array.push_back(1);
array.push_back(6);
array.push_back(2);
array.push_back(6);
array.erase( remove( array.begin(), array.end(), 6), array.end());     //  删除迭代器内所有的6


标准库bitset类型
#include <bitset>
using std::bitset;
四种初始化方式：
bitset<n> b;           //  n位b，每位为0
bitset<n> b(u);        //  unsigned long型u的一个副本
bitset<n> b(s);        //   string对象s含有的位串的副本
bitset<n> b(s, pos, n);   //  b是s中从位置pos开始的n个位的副本

bitset<128> bitvec2(0xffff);            // 长度，低位32位为1，高位置0
string strval("1100");
bitset<32> bitvec4(strval);             //  0,1位置为0，2,3位置为1，其他高位置0

bitset操作:
b.any()        //  b中是否存在置为1的二进制位
b.none()       //  b中不存在置为1的二进制位
b.count()       //  b中置为1的二进制位的个数
b.size()            //  b中二进制位的个数
b.test[pos]      //  b中在pos处的二进制位是否为1
b.set()        //  b中所有二进制位都置为1
b.set(pos)        //  b中pos处的二进制位置为1
b.reset()          // b中所有二进制位置为0
b.flip()            //  b中所有二进制位取反
b.to_ulong()          // b中同样的二进制位返回一个unsigned long值
os << b                     //  b中位集输出到os流

const unsigned array_size = 3;
int ia[array_size] = {0, 1, 2};          // 合法

int *pi = 0;            //  初始化一个不指向任何对象的指针

void *pi  //  可以保存任何类型对象的地址，表明该指针与一地址值相关，但不清楚
double obj = 3.14;
void *pv = &obj;        //  合法

**操作符指派一个指针指向另一个指针
int ival = 1024;
int *pi = &ival;
int **ppi = &pi;       //  指向指针的指针
cout << ival << endl;   // 输出1024
cout << *pi << endl;   //  输出1024
cout << **ppi << endl;    // 两次解引用，输出1024

ptrdiff_t n = ip2 - ip;  //  指针间的距离，标准库类型，机器相关的类型，cstddef中定义,signed整型

// 指向const类型的指针          !!!!!!!!!!!!!!!!!!!!!
const double *cptr;      // cptr只能指向const double类型，但cptr本身是可修改的
//   const限定了cptr指针所指向的对象类型，而非cptr本身，其指向的内容为const，不可修改
//   可以给cptr重新赋值，使其指向另一个const对象
//   多用于内容不可修改的形参
const double pi = 3.14;
const double *cptr = &pi;     //  合法
const int universe = 42;
const void *cpv = &universe;    // 合法，必须使用const void*类型指针保存const对象地址

不能把const对象的地址赋给普通的指针，但是可以把非const对象的地址赋值给指向const对象的指针
此时不能通过这个指针修改指向的地址的内容

不能保证指向const的指针所指对象的值一定不可修改，因为指向const的指针可能指向非const对象

//  const指针
int errNumb = 0;
int *const curErr = &errNumb;    // 从左到右读作：指向int型对象的const指针
//  const指针的值不能修改，所以必须在定义时初始化
//  const指针指向的内容是否可修改完全看指向的对象是否是const

const double pi = 3.1415;
const double *const pi_ptr = &pi;     //  指向const对象的const指针

string s;
typedef string *pstring;        //   pstring 是指向string的指针
const pstring cstr1 = &s;  //const修饰的是pstring类型，这是个指针，所以是指向string类型的const指针
//       上式等价于 string *const cstr1;
pstring const cstr2 = &s; // 意义同上，把pstring当做普通的int理解，对于普通的const变量，const放在int之前还是之后没区别
string *const cstr3 = &s;   //  第三种写法 

const char *cp = "some value";     // cp指向C风格字符串
while(*cp) {
	++cp;
}
C风格字符串的标准库函数      这些函数处理的字符串必须以'\0'结束
#include <cstring>
strlen(s)   // 返回s长度，不包括结束符null
strcmp(s1, s2)    //  比较两个字符串，s1=s2返回0 s1>s2返回正数 s1<s2返回负数
strcat(s1, s2)    //  s2连接到s1后，返回s1
strcpy(s1, s2)    //  s2复制给s1，返回s1
strncat(s1, s2, n)   // s2前n个字符连接到s1后，返回s1
strncpy(s1, s2, n)   // s2前n个字符复制给s1，返回s1
使用后面两个更安全
char largeStr[16 + 18 + 2];
strncpy(largeStr, cp1, 17);    //  注意计算复制
strncat(largeStr, " ", 2);
strncat(largeStr, cp2, 19);

new 动态创建对象   delete  删除动态创建的对象
int *pi = new int;   // 动态创建对象时，只需指定数据类型，new只返回指向动态创建的对象的指针
int *pi = new int(1024);    // 初始化
int *pi = new int();         // 强制对动态创建的对象做值初始化
new返回分配的对象的地址，所以都是用指针存储的返回值，同时这个返回的地址是有类型的！！！
程序员必须显示的将动态创建的对象占用的内存返回给自己存储区，使用delete
如果指针指向不是用new分配的内存地址，则在该指针上使用delete是不合法的  
每次用new分配内存时，系统从空闲区块链表上获得一个空闲块，将此块分配给new出来的对象，同时将分配的大小计入该块的首地址内，将多余的部分作为一个链表块返回给系统，所以用delete删除（new分配内存的地方）时，首地址内有要释放的内容的大小，而不是用new分配的内存地址显然没有这个大小信息，所以delete不合法

delete p;
使用delete之后，指针p变成未定义，一般扔指向之前所存对象的地址，然而p所指向内存已释放，此时指针p变成悬垂指针

const int *pci = new const int(1024);   //  必须初始化 ，地址只能赋给指向const的指针
delete pci;            //  删除const对象

动态数组： 用new开辟的而不是直接声明的，在堆上分配空间
int *pia = new int[10];   // 由于是用new分配的内存，所以在堆上，new返回指向数组第一个元素的指针
//  在自由存储区(堆)上创建的数组对象是没有名字的，只能通过地址间接访问
string *psa = new string[10];     //  类类型用默认构造函数初始化
int *pia = new int[10];          //  内置类型默认不初始化
int *pia2 = new int[10]();        // 强制要求进行值初始化


size_t n = get_size();         //  通过计算得到n值
int* p = new int[n];    //  动态分配数组长度  必须用new，此时n在编译时是未知的
for(int* q = p; q != p+n; ++q)

char arr[0];     //  不合法，不能声明长度为0的数组
char *cp = new char[0];  //合法，调用new动态创建长度为0的数组合法，只是cp不能进行解引用操作
//   此时cp没有指向任何值
delete [] cp;    //  收回cp所指向的数组，对于动态创建的数组对象  []不能少
因为new出来的内存空间首地址会存类型大小，所以用[]提醒delete有多个元素，此处如何判断数组大小，未知，此处纯属猜测， 留待考证

//   使用new动态分配数组大小，动态的处理不同长度的字符串
const char *noerr = "success";
const char *err189 = "Error: a function declaration must specify a function return type";
const char *errorTxt;
if(errorFound)
	errorTxt = err189;
else
	errorTxt = noerr;
int dimension = strlen(errorTxt) + 1;         //  别忘了C风格字符串结尾的'\0'
char *errMsg = new char[dimension];               // 关键所在
strncpy(errMsg, errorTxt, dimension);


多维数组：
int ia[3][4] = {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9 ,10 ,11}}


后置自增/减(++, --)操作符相对于前置的有额外开销，养成使用前置操作的好习惯
++i  与  i++  前置++与后置++重载的写法
class Integer {
public:
	Integer(long data): m_data(data) {}         //  构造函数
	Integer& operator++() {
		m_data++;
		return *this;
	}
	Integer operator++(int) {
		Integer temp = *this;
		m_data++;
		return temp;       //   返回这个对象的旧值
	}
}
++i  与  i++   效率上的区别
内建数据类型的情况，转化成汇编代码之后前置++只有一句汇编代码，后置++有三句汇编代码（待验证??????????）
自定义数据类型的情况，++i效率较高。
考虑内建数据类型时，它们的效率差别不大（去除编译器优化的影响）。所以在这种情况下我们大可不必关心。
现在让我们再考虑自定义数据类型（主要是指类）的情况。此时我们不需要再做很多汇编代码的分析了，因为前缀式（++i）可以返回对象的引用，而后缀式（i++）必须产生一个临时对象保存更改前对象的值并返回(实现过自定义类型++运算符定义的就知道)，所以导致在大对象的时候产生了较大的复制开销，引起效率降低，因此处理使用者自定义类型（注意不是指内建类型）的时候，应该尽可能的使用前缀式地增/递减，因为他天生体质较佳。

*iter++  等价于  *(iter++)   *操作的是未自增前的内容

Sales_item *sp = &item1;
(*sp).same_isbn(item2);       //  括号不能少，因为.的优先级高

sizeof  返回一个对象或者类型名的长度，返回值类型size_t，长度单位是字节

sizeof(ia)/sizeof(*ia)    ia是数组名，此操作得到数组元素的个数
sizeof(ia)/sizeof(ia[0])   意思同上，另一种写法

throw  错误检测部分
try   错误处理部分

try {
	if(!item1.same_isbn(item2))
		throw runtime_error("Data must refer to same ISBN");   // 抛出异常
} catch (runtime_error err)  {
	cout << err.what();
}

使用预处理器进行调试
int main() {
	#ifndef NDEBUG
	cerr << "starting main" << endl;
	#endif
}
命令行编译时：
$ CC -DNDEBUG main.c

常用调试宏：
__FILE__  文件名
__LINE__  当前行号
__TIME__  文件被编译的时间
__DATA__  文件被编译的日期


函数相关：
Date &calendar(const char*);    //  返回一个指向Date类型的引用，形参是类似字面值常量的const类型
int *foo_bar(int *ip);        // 返回指针

void use_ptr(const int *p) {}    //  保护指针指向的值

void swap(int &v1, int &v2) {}    // 定义引用类型的形参

            //////          C++11的新特性                   ///////////
			
C++风格的类型转换提供了4种类型转换操作符来应对不同场合的应用。格式如：TYPE B = static_cast(TYPE)(a)
const_cast，字面上理解就是去const属性，去掉类型的const或volatile属性。

static_cast，命名上理解是静态类型转换。如int转换成char。
类似于C风格的强制转换。无条件转换，静态类型转换。用于：
　　1. 基类和子类之间转换：其中子类指针转换成父类指针是安全的;但父类指针转换成子类指针是不安全的。(基类和子类之间的动态类型转换建议用dynamic_cast)
　　2. 基本数据类型转换。enum, struct, int, char, float等。static_cast不能进行无关类型(如非基类和子类)指针之间的转换。
　　3. 把空指针转换成目标类型的空指针。
　　4. 把任何类型的表达式转换成void类型。
　　5. static_cast不能去掉类型的const、volitale属性(用const_cast)。
int n = 6;
double d = static_cast<double>(n); // 基本类型转换
double *d = static_cast<double *>(&n) //无关类型指针转换，编译错误
void *p = static_cast<void *>(pn); //任意类型转换成void类型

dynamic_cast，命名上理解是动态类型转换。如子类和父类之间的多态类型转换。
　有条件转换，动态类型转换，运行时类型安全检查(转换失败返回NULL)：
　　1. 安全的基类和子类之间转换。
　　2. 必须要有虚函数。基类必须有虚函数。保持多态特性才能使用dynamic_cast
　　3. 相同基类不同子类之间的交叉转换。但结果是NULL。

class BaseClass {
　　public:
　　int m_iNum;
　　virtual void foo(){};
　　//基类必须有虚函数。保持多态特性才能使用dynamic_cast
};
class DerivedClass: public BaseClass {
　　public:
　　char *m_szName[100];
　　void bar(){};
};
BaseClass* pb = new DerivedClass();
//   指向子类的指针转父类没有问题
DerivedClass *pd1 = static_cast<DerivedClass *>(pb);    //子类->父类，静态类型转换，正确但不推荐
DerivedClass *pd2 = dynamic_cast<DerivedClass *>(pb);    //子类->父类，动态类型转换，正确

BaseClass* pb2 = new BaseClass();
//   指向父类的指针转子类有很大问题，不过动态转换保证有一个有效的结果
DerivedClass *pd21 = static_cast<DerivedClass *>(pb2);     //父类->子类，静态类型转换，危险！访问子类m_szName成员越界
DerivedClass *pd22 = dynamic_cast<DerivedClass *>(pb2);     //父类->子类，动态类型转换，安全的。结果是NULL


reinterpreter_cast，仅仅重新解释类型，但没有进行二进制的转换。
	1.转换的类型必须是一个指针、引用、算术类型、函数指针或者成员指针。
　　2. 在比特位级别上进行转换。它可以把一个指针转换成一个整数，也可以把一个整数转换成一个指针(先把一个指针转换成一个整数，在把该整数转换成原类型的指针，还可以得到原先的指针值)。但不能将非32bit的实例转成指针。
　　3. 最普通的用途就是在函数指针类型之间进行转换。
　　4. 很难保证移植性。


函数的使用：
int cmp(const void* a, const void *b)
qsort(arraypoint, arraysize, sizeof(element), cmp);

用C++模拟实现静态构造函数：

template <typename T>
class static_constructable {
	private:
		struct helper{          //   私有类，只被调用一次
			helper() {
				std::cout << "2s" << std::endl;
				T::static_constructor();
			}
		}
	protected:
		static_constructable() {            //  基类构造函数在每次生成一个新实例的时候都调用
			std::cout << "1s" << std::endl;
			static helper placeholder;           //  静态变量，只实例化一次
		}
}

class A :static_constructable<A> {
	public:
		static void static_constructor() {
			std::cout << "3s" << std::endl;
			std::cout << "static constructor a" << std::endl;
			s_string = "abc";
		}
		static std::string s_string;
		A(std::string a1) {
			std::cout << "constructor " << a1 << std::endl;
		}
	private:
		int m_i;
}

std::string A::s_string;         // 这个不可少，不然链接出错，原因为：类的成员静态变量必须在外部进行初始化
int _tmain(int argc, _TCHAR* argv[])
{
	std::cout << "begining of main" << std::endl;
	assert(sizeof(A) == sizeof(int));       //  此时A只有4字节大小，推测为一个指针大小
	assert(A::s_string == "");              //  此时未调用静态构造函数，所以s_string为空
	A a1("a1");
	assert(A::s_string == "abc");            //  此时已调用静态构造函数，所以s_string为abc
	A a2("a2");
	std::cout << "end of main" << std::endl;
	int abc;
	std::cin >> abc;
	return 0;
}

输出：
beginning of main  
1s
2s
3s
static constructor a //创建A对象前自动调用静态构造方法，一次且仅一次  
constructor a1  
1s
constructor a2  
end of main 

单例模式：  利用私有化构造函数和析构函数
class OnlyHeapClass {
	public:
		static OnlyHeapClass* GetInstance() {
			//     以引用或者指针的形式将其返回（这里不以对象返回，主要是构造函数是私有的，外部不能创建临时对象
			return (new OnlyHeapClass); 
		}
	void Destory();      //   专门用于析构，因为delete只能作用在栈上创建的对象，而此处是堆上对象，要显示析构，又由于析构函数是private的，不能显示调用，所以需要这个成员函数进行析构(即只能在类里面delete，)
	private:
		OnlyHeapClass();
		~OnlyHeapClass();
};

int main()
{
	OnlyHeapClass *p = OnlyHeapClass::GetInstance();
	delete p;
	return 0;
}

//   这个例子使用了私有构造函数，GetInstance()作为OnlyHeapClass的静态成员函数来在内存中创建对象
//   由于要跨函数传递并且不能使用值传递方式，所以我们选择在堆上创建对象
//   这样即使getInstance()退出，对象也不会随之释放，可以手动释放。

//////构造函数私有化的类的设计保证了其他类不能从这个类派生或者创建类的实例，还有这样的用途：例如，实现这样一个class：它在内存中至多存在一个，或者指定数量个的对象（可以在class的私有域中添加一个static类型的计数器，它的初值置为0，然后在GetInstance()中作些限制：每次调用它时先检查计数器的值是否已经达到对象个数的上限值，如果是则产生错误，否则才new出新的对象，同时将计数器的值增1.最后，为了避免值复制时产生新的对象副本，除了将构造函数置为私有外，复制构造函数也要特别声明并置为私有。

析构函数private:
另外如何保证只能在堆上new一个新的类对象呢？只需把析构函数定义为私有成员。原因是C++是一个静态绑定的语言。在编译过程中，所有的非虚函数调用都必须分析完成。即使是虚函数，也需检查可访问性。因些，当在栈上生成对象时，对象会自动析构，也就说析构函数必须可以访问。而堆上生成对象，由于析构时机由程序员控制，所以不一定需要析构函数。保证了不能在栈上生成对象后，需要证明能在堆上生成它。这里OnlyHeapClass与一般对象唯一的区别在于它的析构函数为私有。delete操作会调用析构函数。所以不能编译。
那么如何释放它呢？答案也很简单，提供一个成员函数，完成delete操作。在成员函数中，析构函数是可以访问的。当然detele操作也是可以编译通过。 
void OnlyHeapClass::Destroy() { 
        delete this; 
} 
构造函数私有化的类的设计可以保证只能用new命令在堆中来生成对象，只能动态的去创建对象，这样可以自由的控制对象的生命周期。但是，这样的类需要提供创建和撤销的公共接口。
另外重载delete，new为私有可以达到要求对象创建于栈上的目的，用placement new也可以创建在栈上。

 1.为什么要自己调用呢？对象结束生存期时不就自动调用析构函数了吗？什么情况下需要自己调用析构函数呢？   
  　　比如这样一种情况，你希望在析构之前必须做一些事情，但是用你类的人并不知道，   
  那么你就可以重新写一个函数，里面把要做的事情全部做完了再调用析构函数。   
  这样人家只能调用你这个函数析构对象，从而保证了析构前一定会做你要求的动作。   
    
  2.什么情况下才用得着只生成堆对象呢？   
  　　堆对象就是new出来的，相对于栈对象而言。什么情况下要new，什么情况下在栈里面   
  提前分配，无非就是何时该用动态，何时该用静态生成的问题。这个要根据具体情况   
  具体分析。比如你在一个函数里面事先知道某个对象最多只可能10个，那么你就可以   
  定义这个对象的一个数组。10个元素，每个元素都是一个栈对象。如果你无法确定数   
  字，那么你就可以定义一个这个对象的指针，需要创建的时候就new出来，并且用list   
  或者vector管理起来。
  
   把析构函数定义为私有的，就阻止了用户在类域外对析构函数的使用。这表现在如下两个方面：
  1.   禁止用户对此类型的变量进行定义，即禁止在栈内存空间内创建此类型的对象。要创建对象，只能用   new   在堆上进行。
  2.   禁止用户在程序中使用   delete   删除此类型对象。对象的删除只能在类内实现，也就是说只有类的实现者才有可能实现对对象的delete，用户不能随便删除对象。如果用户想删除对象的话，只能按照类的实现者提供的方法进行。   
  可见，这样做之后大大限制了用户对此类的使用。一般来说不要这样做；通常这样做是用来达到特殊的目的，比如在   singleton的实现上。
  
malloc和free是C/C++语言的标准库函数，new/delete是C++的运算符，都可动态申请内存和释放内存
对于非内部数据类型的对象，malloc/free无法满足动态对象的要求。
由于malloc/free是库函数而不是运算符，不在编译器控制权限之内，不能够把执行构造函数和析构函数的任务强加于malloc/free

  
指针形参：
void reset(int *ip)               // 仅能用int *实参调用
void use_ptr(const int *p)         // 可以用int *也可以用const int *实参调用

const形参：
void fcn(const int i)   // 视为  void fcn(int i) 为了兼容C语言，此时再定义前述函数会报重复定义错误
const形参的调用范围比普通的非const形参调用范围广泛，例如
string::size_type find_char(string &s, char c)
find_char("hello world", 'o');  // 调用不合法，不支持字面值字符串
//   将形参定义为const可以在类似字符串字面值这种情况下调用，比非const形参的使用范围更广泛！！！！！！
如果使用引用形参的唯一目的是避免复制实参，则应将引用形参定义为const对象
如果在需要const引用时，将形参定义为普通引用，则会导致不能使用右值和const对象，以及需要进行类型转化的对象来调用该函数，从而不必要的限制了该函数的使用

引用形参：
void swap(int &v1, int &v2)
bool isShorter(const string &s1, const string &s2)
非const引用形参只能与完全同类型的非const对象关联
int incr(int &val)  以下调用皆错！！！
short v1 = 0;
const int v2 = 42;
incr(v1);   // 类型不对，因为是引用形参，所以强调类型完全一致
incr(v2);
incr(0);      // 字面值不是左值变量，也不行(不可修改)
incr(v1+v2);   // 加法结果不是一个左值变量

指向指针的引用：
void ptrswap(int *&v1, int *&v2)  // v1是一个引用，与指向int型对象的指针相关联

数组形参定义：
void printValues(int *) {}
void printValues(int[]) {}
void printValues(int[10]) {}    //  三个定义完全等价，其中最后一个10除了误导毫无意义

当函数需要处理数组且函数体不依赖于数组的长度时应使用指针形参，其他情况下应使用引用形参

通过引用传递数组：
void printValues(int (&arr)[10])   // 编译器不会将数组实参转化为指针，而是传递数组的引用本身
//   数组大小成为形参和实参类型的一部分，圆括号不可少
引用形参的优点是在函数体中依赖数组的长度是安全的；缺点是限制了可以传递的实参数组，只能使用长度匹配的实参数组来调用函数

多维数组的传递：  //  元素本身是数组，除第一维外所有维的长度都是元素类型的一部分，须指明
void printValues(int (matrix*)[10], int rowSize)  // matrix是指向含有10个int元素的数组的指针
void printValues(int matrix[][10], int rowSize)   //  声明为二维数组的形式，本质还是指针
指针形参的优点是可以明确的标示函数所操纵的是指向数组元素的指针，而不是数组本身，而且可以使用任意长度的实参数组来调用函数；缺点是函数体不能依赖于数组的长度，否则容易造成数组内存的越界访问，从而产生错误的结果或者导致程序崩溃。

省略符形参：
void foo(parm_list, ...); //显示声明的形参用对应的实参进行类型检查，与省略符对应的实参暂停类型检查

函数返回值，千万不要返回局部变量的引用，因为局部变量在函数运行结束后会被释放掉
引用返回左值：
char &get_val(string &str, string::size_type ix) {
	return str[ix];
}
const char &get_val()   // 此函数限制返回值不可被修改
int main() {
	string s("a value");
	get_val(s, 0) = 'A';  // 改变字符串首字母为A
}

默认实参：
//  如果一个形参具有默认实参，他后面所有的形参都必须有默认实参, 默认实参只能替换函数调用缺少的尾部实参
string screenInit(string:size_type height = 24, string::size_type width = 80, char background = ' ')
screenInit();   // 等价于  screenInit(24, 80, ‘ ’);
screenInit(66);  // 等价于  screenInit(66, 80, ‘ ’);
screenInit(66, 256); // 等价于  screenInit(66, 256, ' ');
screenInit(, , '?');   // error
//  设计时注意：使最少使用默认实参的形参排在最前面
//   可以在声明中也可以在定义里指定默认形参，但是只能选其一指定一次，通常在头文件的声明中指定

内联函数：
inline const string& shorterString(const string& s1, const string& s2)
//   内联函数应该在头文件中定义

类的成员函数：
编译器隐式的将在类内定义的成员函数当做内联函数
每个成员函数都有一个额外的、隐含的形参将该成员函数与调用该函数的类对象绑定在一起
const对象、指向const对象的指针或引用只能调用其const成员函数，调用非const成员函数报错
class Sales_item {
	public:
		double avg_price() const;     //   此处的const改变隐含的this形参的类型，将其变为const Sales_item* 类型
		bool same_isbn(const Sales_item &rhs) const  // 带const的成员函数成为常量成员函数
		{ return isbn = rhs.isbn; }
		Sales_item(): units_sold(0), revenue(0.0) {}  // 默认构造函数
		// 冒号和花括号之间的代码称为构造函数的初始化列表，为类的数据成员指定初值
	private:
		std::string isbn;
		unsigned units_sold;
		double revenue;
}
类外定义成员函数：
//   原本成员函数带const之后，是不能修改整个对象内容的
 //  在对const成员函数使用的成员变量用mutable修饰符后，就可以在下面的函数内修改这个成员变量了
 //  例如前述变量定义改为 mutable double revenue; 
double Sales_item::avg_price() const         
{
	if (units_sold)
		return revenue/units_sold;
	else
		return 0;
}

重载函数：同名不同形参的函数
Record lookup(Phone);
Record lookup(const Phone);   // 与上述声明重复，因为复制形参时并不考虑形参是否为const，结果编译器不知道传给哪个函数
//  上述错误仅适用于非引用形参


指向函数的指针：
bool (*pf)(const string&, const string&)  //  声明一个函数指针变量，注意到pf是个变量，不是个类型名
//   以下定义表示cmpFcn是一种指向函数的指针类型的名字，该指针类型为“指向返回bool类型并带有两个const string引用形参的函数的指针”，在需要使用这种函数指针类型时，只需直接使用cmpFcn即可
typedef bool (*cmpFcn)(const string&, const string&);  //  上述声明的简化，通行做法
bool lengthCompare(const string&, const string&);
cmpFcn pf = lengthCompare;
pf("hi", "bye");  // 可以不需要解引用操作符，直接通过指针调用函数
(*pf)("hi", "bye");   // 同样正确

函数指针形参：
void useBigger(const string&, const string&, bool(const string&, const string&));    //  第三个参数是函数指针
void useBigger(const string&, const string&, bool(*)(const string&, const string&));    // 同上

返回指向函数的指针：
int (*ff(int))(int*, int);   //  从声明的名字开始从里往外理解
ff(int)  ff声明为一个函数，带有一个int型的形参，该函数返回int (*)(int*, int);
//  更简单明确的写法
typedef int (*PF)(int*, int);
PF ff(int);

指向重载函数的指针：
指针类型必须与重载函数某个版本精确匹配，否则报编译错误
extern void ff(vector<double>);
extern void ff(unsigned int);
void (*pf1)(unsigned int) = &ff;   // 匹配ff(unsigned int)


//  指针数组
int *ptr[5];
//  数组指针
int (*ptr)[];
int Test[2][3] = {{1,2,3}, {4,5,6}};
int (*A)[3] = &Test[1];
cout << (*A)[0] << (*A)[1] << endl;

定义一个二维的指针
int **p = new int*[50];    // 一个指向指针数组的指针
for(int i=0; i<50; i++)     // 对指针数组的每个元素，指向某个数组
	p[i] = new int[100];


类的成员函数的初始化列表的初始化顺序是根据成员变量的声明顺序来执行的！！！所以初始化前后相关时，要注意声明顺序！！！

当父类的析构函数设为virtual时，所有的派生类的析构函数都将自动变为virtual型，

在C++中，只要原来的返回类型是指向类的指针或引用，新的返回类型是指向派生类的指针或引用，覆盖的方法就可以改变返回类型。
这样的类型称为协变返回类型（Covariant returns type).
覆盖要求函数  """具有完全相同的形参"""。
一般覆盖具有相同的返回值，否则会提示错误。这个规则对返回类型协变而言，则有所放松。覆盖的返回值不区分基类或派生类。从语意上理解，一个派生类也是一个基类。
Class ShapeEditor ...{……};
Class CircleEditor : public ShapeEditor...{ … };
Class Shape {
public:
virtual const ShapeEditor& getEditor () const = 0; //Factory Method  纯虚函数
};
Class Circle : Public Shape {
public:
const CircleEditor& getEditor ()const ;
};
在这个例子中，注意CircleEditor必须在Circle::getEditor的声明之前被完整地定义（而不能仅仅声明），
因为编译器必须知道CircleEditor对象的布局，才能执行适当的地址操纵，从而将一个CircleEditor引用
（或指针）转换为一个ShapeEditor引用（或指针）
协变返回类型的优势在于，总是可以在适当程度的抽象层面工作。若我们是处理Shape，将获得一个抽象的ShapeEditor；若正在处理某种具体的形状类型，比如Circle,我们就可以直接获得CiecleEditor.协变返回机制将我们从这样的一种处境解脱出来：不得不使用易于出错的转换操作来“重新”提供类型信息，而这种信息是一开始就不应该丢掉的：（那么，对于友元，派生的operator+，怎么样调用基类的operator+呢？）


有些成员变量的数据类型比较特别，它们的初始化方式也和普通数据类型的成员变量有所不同。这些特殊的类型的成员变量包括：
a.引用      b.常量       c.静态       d.静态常量(整型)         e.静态常量(非整型)
常量和引用，必须通过参数列表进行初始化。
静态成员变量的初始化也颇有点特别，是在类外初始化且不能再带有static关键字，其本质见文末。
参考下面的代码以及其中注释：
#include <iostream>
using namespace std;
class BClass
{
public:
 BClass() : i(1), ci(2), ri(i){} // 对于常量型成员变量和引用型成员变量，必须通过参数化列表的方式进行初始化
//普通成员变量也可以放在函数体里，但是本质其实已不是初始化，而是一种普通的运算操作-->赋值运算，效率也低
private:
 int i;                                  // 普通成员变量
 const int ci;                           // 常量成员变量
 int &ri;                                // 引用成员变量
 static int si;                          // 静态成员变量
 //static int si2 = 100;                 // error: 只有静态常量成员变量，才可以这样初始化
 static const int csi;                   // 静态常量成员变量
 static const int csi2 = 100;            // 静态常量成员变量的初始化(Integral type)    (1)
 static const double csd;                // 静态常量成员变量(non-Integral type)
 //static const double csd2 = 99.9;      // error: 只有静态常量整型数据成员才可以在类中初始化
};
//注意下面三行：不能再带有static
int BClass::si = 0; // 静态成员变量的初始化(Integral type)
const int BClass::csi = 1; // 静态常量成员变量的初始化(Integral type)
const double BClass::csd = 99.9; // 静态常量成员变量的初始化(non-Integral type)
// 在初始化(1)中的csi2时，根据著名大师Stanley B.Lippman的说法下面这行是必须的。
// 但在VC2003中如果有下面一行将会产生错误，而在VC2005中，下面这行则可有可无，这个和编译器有关。
const int BClass::csi2;
---------------------------------------------------------------------------------------------
静态成员属于类作用域，但不属于类对象，和普通的static变量一样，程序一运行就分配内存并初始化，生命周期和程序一致。
所以，在类的构造函数里初始化static变量显然是不合理的。
静态成员其实和全局变量地位是一样的，只不过编译器把它的使用限制在类作用域内（不是类对象，它不属于类对象成员），要在类的定义外（不是类作用域外）初始化。
1.static成员的所有者是类本身和对象，但是多有对象拥有一样的静态成员。从而在定义对象时不能通过构造函数对其进行初始化。
2.静态成员不能在类定义里边初始化，只能在class body外初始化。
3.静态成员仍然遵循public，private，protected访问准则。
4.静态成员函数没有this指针，它不能返回非静态成员，因为除了对象会调用它外，类本身也可以调用，所以不可以使用非静态成员变量。
C++静态成员变量是用static修饰的成员变量，不属于对象的一部分，而是类的一部分。
因此可以在没有实例化任何对象的时候使用静态成员变量。但是必须初始化它！！！！
由于静态变量只能被初始化一次，所以初始化成员变量不要放在如下地方：1.类的构造函数（构造函数可能多次被调用）；2.头文件中（头文件可能被包含入多个地方，也可能被执行多次）。应该放在应用程序中，类以外的任何地方初始化，例如：在main中，或全局函数中，或任何函数之外。
static成员变量是否是private对于初始化并没有影响，因为设定static成员变量初值时，不受任何存取权限的束缚！！！！不过要注意的是，static成员变量的类型要出现在初始化语句中，因为这是初始化操作，不是赋值操作。static成员变量是在初始化（而不是在类声明时候）才定义出来的。如果没有初始化操作，会产生链接错误。初始化格式如下：
类型  类名::静态成员变量名 = 初始化值;










   ///////////////////////////////            容器相关             ////////////////////////////
顺序容器
#include <vector>   // 快速随机访问
#include <list>     //  快速插入/删除
#include <deque>   //  双端队列


C c(c2);  // 创建容器c2的副本c    vector<int> c(c1)   c1要是vector<int>类型
C c(b, e);       // 创建c，元素是迭代器b和e标示的范围内的元素的副本   vector<int> c(a.begin(), a.end())
C c(n, e);    // 创建c，元素为n个t   vector<int> c(3, 10)    只适用于顺序容器  
C c(n);       // 创建c，有n个元素，进行值初始化， 只适用于顺序容器  
vector<vector<string> > lines;  // 空格不可省，否则便>>操作符
vector<string>::iterator mid = svec.begin() + svec.size()/2;
容器定义的类型别名：
size_type
iterator
const_iterator
reverse_iterator
const_reverse_iterator
difference_type
value_type
reference
const_reference
//  通用迭代器方法
c.begin()  c.end()  c.rbegin()  c.rend()  
c.push_back(a)  
c.insert(p, t)     // 在迭代器p指向的元素前面插入t，返回指向新元素的迭代器
c.insert(p, n, t)   //  在迭代器p指向的元素前插入n个t，返回void
c.insert(p, b, e)  //  在迭代器p指向的元素前插入迭代器b和e标记的元素
c.size()       //  返回元素个数  c::size_type类型
c.max_size()
c.empty()
c.resize(n)     // 调整容器c的长度大小，使其能容纳n个元素
c.resize(n, t)   //  调整容器c的长度大小，使其能容纳n个元素，新加元素值为t
c.back()       //  返回容器c的最后一个元素的引用
//  list<int>::reference last = *--ilist.end();
//  list<int>::reference last2 = ilist.back();
c.front()     //  返回容器c的第一个元素的引用
//  list<int>::reference val = *ilist.begin();
//  list<int>::reference val2 = ilist.front();
c.erase(p)   //  删除迭代器p指向的元素
c.erase(b,e)  //  删除迭代器b和e之间的元素
c.clear()   // 删除所有元素
c.pop_back()  // 删除最后一个元素，返回void
c1.swap(c2)   //  交换容器c1和c2的内容
c.assign(b, e)   // 重置c的元素，将迭代器b和e内所有元素复制到c中，b和e不能指向c中元素
c.assign(n, t)   // 重置c的元素为n个t

//  vector
c.capacity()   //  vector当前存放容量，一般比c.size()大
c.reserve(n)   //  强制整个vector总空间为n，包括使用空间和预留空间


//  只适用list deque 
c.push_front(a)
c.pop_front()   //  删除第一个元素，返回void


string类型：
#include <string>


sting s(cp);   // 用cp指向的c风格字符串初始化对象
getline(is, s)  // 从输入流is中读取一行字符，写入s
s.insert(p, t)
s.assign(b, e)
s.erase(p)
s.substr(pos, n)
s.append(args)
s.replace(pos, len, args)

s.find(args)       //  args四种类型：c,pos    s2,pos   cp,pos   cp,pos,n
s.rfind(args)
s.find_first_of(args)
s.find_last_of(args)
s.find_first_not_of(args)
s.find_last_not_of(args)



容器适配器：
#include <stack>     //  栈
#include <queue>     //  队列
默认stack和queue基于deque容器实现，priority_queue基于vector实现

栈适配器 stack<int> s;
s.empty()
s.size()
s.pop()       //  删除栈顶元素，但不返回
s.top()        //  返回栈顶元素，但不删除
s.push(item)

deque<int> deq;
stack<int> stk(deq);
stack< string, vector<string> > str_stk;  // 基于vector实现stack
stack< string, vector<string> > str_stk2(svec);  // 同上

队列适配器
q.empty()
q.size()
q.pop()
q.front()
q.back()
q.top()
q.push(item)

关联容器：
pair<T1, T2> p1;  //  pair<string, int>   //  pair<string, string> author("James", "Joyce")
author.first == "James";   author.second == "Joyce";

make_pair(v1, v2);
pair<string, string> next_auth;
while(cin >> first >> second)
	next_auth = make_pair(first, second);  // 等价于  next_auth = pair<string, string>(first, second);
	
map关联数组：
map<k, v> m;   //  健为k，值为v
map<K, V>::key_type  //  map容器中，用作索引的健的类型
map<K, V>::mapped_type  // map容器中，健所关联的值的类型
map<K, V>::value_type //一个pair类型，first元素具有 const map<K,V>::key_type类型，second元素则为map<K,V>::mapped_type

map<string, int>::iterator map_it = word_count.begin();
map_it->first; map_it->second;
word_count["Anna"] = 1;        //  不存在时插入  Anna

map<string, int> word_count;
string word;
while(cin >> word)
	++word_count[word];            //  单词计数程序，没则插入，有则加加

while(cin >> word) {
	pair<map<string, int>::iterator, bool> ret = word_count.insert(make_pair(word, 1));
	if(!ret.second)                    //  用插入方式重写单词计数器
		++ret.first->second;
}
	
word_count.insert(map<string, int>::value_type("Anna", 1));
word_count.insert(make_pair("Anna", 1));

m.count(k)        //  返回m中健k出现次数，不会插入元素，用下标检索时不存在的话会插入元素
m.find(k)           //  返回k的迭代器，不存在健K时返回末端迭代器
m.erase(k)         //  k为健
m.erase(p)        //  p为迭代器
m.erase(b, e)     //   范围删除

代码示例：
int main() {
	map<int, string> mapStudent;
	mapStudent.insert(pair<int, string>(1, "student_one"));             //  通过实例化一个pair添加
	mapStudent.insert(pair<int, string>(2, "student_two"));             //  如果已存在添加失败
	mapStudent.insert(pair<int, string>(3, "student_three"));
	mapStudent.insert(map<int, string>::value_type(4, "student_four"));          // 通过value_type添加
	mapStudent.insert(map<int, string>::value_type(5, "student_five"));          // 如果已存在添加失败
	mapStudent.insert(map<int, string>::value_type(6, "student_six"));
	// 用数组的方法插入时，值会首先设为缺省值，再赋值为显示的值，如果值元素是个对象，则开销比较大，建议用以上方法
	mapStudent[7] = "student_seven";    //  通过下标形式添加，主要到下标是关键字而不是数组下标，可以没有0
	mapStudent[8] = "student_eight";    //  此方式是覆盖添加，如果存在则覆盖
	
	pair<map<int, string>::iterator, bool> insertValue;
	insertValue = mapStudent.insert(pair<int, string>(3, "student_5"));  // 添加后返回一个迭代器和bool类型的pair
	if(insertValue.second) {
		cout << "insert successfully" << endl;
	}else{
		cout << "insert fail" << endl;
	}
	
	
	cout << mapStudent.size() << endl;
	for(map<int, string>::const_iterator it = mapStudent.begin(); it!=mapStudent.end(); it++) {   // 顺序迭代
		cout << it->first << " " << it->second << endl;
	}
	//  逆序迭代
	for(map<int, string>::const_reverse_iterator cit = mapStudent.rbegin(); cit!=mapStudent.rend(); cit++) {
		cout << cit->first << " " << cit->second << endl;
	}
	
	cout << "count: " << mapStudent.count(3) << endl;     // 出现次数，map中可用于判断是否含有某个元素
	
	//  通过map对象的方法获取的iterator数据类型是一个std::pair对象
	//  包括两个数据 iterator->first 和 iterator->second 分别代表关键字和存储的数据
	
	map<int, string>::iterator it = mapStudent.find(3);     // 查找某个元素，返回相应迭代器
	if(it == mapStudent.end()) {
		cout << "don't find" << endl;
	}else{
		cout << "find " << it->first << "->" << it->second << endl; 
	}
	
	string elem = mapStudent[1];     //  如果健存在也能返回一个值，副作用是如果不存在则会插入关键字1
	
	map<int,string>::iterator it1, it2;
	//  下界，map中如果存在元素则返回该元素的迭代器，不存在返回刚好比该值大的元素的 迭代器或者mapStudent.end()
	it1 = mapStudent.lower_bound(3); 
	cout << it1->first << " " << it1->second << endl;
	//  上届，存在不存在都返回刚好比该值大的元素的 迭代器或者mapStudent.end()
	it2 = mapStudent.upper_bound(5);
	cout << it2->first << " " << it2->second << endl;
	
	pair<map<int, string>::iterator, map<int, string>::iterator> mapPair;
	mapPair = mapStudent.equal_range(3);           //  返回一对迭代器，分别为 lower_bound 和 upper_bound
	if(mapPair.first == mapPair.second) {          //　相等就没找到
		cout << "don't find element" << endl;
	}else{
		cout << "find 3" << endl;
	}
	
	map<int, string>::iterator it3;
	it3 = mapStudent.find(3);
	mapStudent.erase(it3);            //  通过迭代器删除
	mapStudent.erase(4);              //  通过关键字删除
	mapStudent.erase(mapStudent.begin(), mapStudent.end());      //  通过范围迭代器删除
	mapStudent.clear();   //  等价于上面的迭代器范围删除
}

//   http://blog.sina.com.cn/s/blog_4b3c1f950100kgps.html  里面关于map的QA可以看看，其他内容浪费时间

set容器：
set<int> iset(ivec.begin(), ivec.end());
set<string> set1;
set1.insert("the");

代码示例：
int main() {
	set<int> setNum;
	for(int i=9; i>0; i--) {
		setNum.insert(i);       //  插入元素
	}

	set<int>::iterator it = setNum.find(5);    // 寻找元素5，成功返回有效迭代器，失败返回 end()
	if(it != setNum.end()) {
		cout << "find element\n";
	}else{
		cout << "don't find\n";
	}

	pair<set<int>::iterator, bool> vset = setNum.insert(5);    // 插入重复元素，失败返回一个pair
	if(vset.second) { // 注意不能通过判断pair的第一个元素是不是end()来判断插入成功与否，猜测不成功不设置第一个值
		cout << "insert successfully" << endl;
	}else{
		cout << "insert fail" << endl;
	}

	cout << setNum.size() << endl;         //   求解大小
	setNum.erase(5);                       //   删除set元素
	cout << setNum.size() << endl;
	
	vset = setNum.insert(5);               //  删除后插入成功
	if(vset.second) {
		cout << "insert successfully" << endl;
	}else{
		cout << "insert fail" << endl;
	}
	
	cout << setNum.count(6) << endl;      //  查看某个元素在不在set中，因为只可能出现一次，所以输出只有1或者0

	for(set<int>::iterator cit=setNum.begin(); cit!=setNum.end(); cit++) {   //　set内容的遍历 
		cout << *cit << " ";
	}

}


multimap容器：
m.lower_bound(k)    // 返回一个迭代器，指向健不小于k的第一个元素
m.upper_bound(k)     // 返回一个迭代器，指向健大于k的第一个元素
m.equal_range(k)     //  返回一个迭代器pair，其中first等价于m.lower_bound(k), second等价于m.upper_bound(k)

泛型算法：
#include <algorithm>
#include <numeric>

vector<int>::const_iterator result = find(vec.begin(), vec>end(), search_value);    // 返回迭代器
int *result = find(ia, ia+6, search_value);             //  返回指针

int sum = accumulate(vec.begin(), vec.end(), 42);        //  累加求和，42是累加初始值

list<string>::iterator it = find_first_of(roster1.begin(), roster1.end(), roster2.begin(), roster2.end());

fill(vec.begin(), vec.end(), 0);     //  将迭代器范围内的值修改为0(第三个参数)
fill_n(vec.begin(), 10, 0);       //  begin开始的十个元素置为0，必须保证有十个元素

#include <iterator>        //  迭代器相关的库
vector<int> vec;
fill_n(back_inserter(vec), 10, 0); // back_inserter是迭代器适配器(插入迭代器)，此处在容器vec上附加10个0

copy(ilst.begin(), ilst.end(), back_inserter(ivec));      // 第三个参数指定目标序列的一个元素，即被插入的地方
replace(ilst.begin(), ilst.end(), 0, 42);             //   将范围内所有0替换成42
replace_copy(ilst.begin(), ilst.end(), back_inserter(ivec), 0, 42);    
//  第三个参数指定调整后保存的位置，保存的是所有ilst范围内的值，其中0都替换成42

sort(words.begin(), words.end());   // 排序，单词按字典序排序

// 将“相邻”的重复元素移动到容器尾部，返回指向第一个重复值的迭代器
vector<string>::iterator end_unique = unique(words.begin(), words.end());  

bool isShorter(const string &s1, const string &s2) {
	return s1.size() < s2.size();
}
stable_sort(words.begin(), words.end(), isShorter);       //  保留相等元素的原始位置

bool GT6(const string &s) {
	return s.size()>=6;
}
vector<string>::size_type wc = count_if(words.begin(), words.end(), GT6);

插入迭代器：
back_inserter   //  创建使用push_back实现插入的迭代器
front_inserter    //   使用push_front实现插入
inserter         //  使用insert实现插入

list<int>::iterator it = find(ilst.begin(), ilst.end(), 42);
replace_copy(ivec.begin(), ivec.end(), inserter(ilst, it), 100, 0)

流迭代器：
istream_iterator<int> cin_it(cin);    //  从cin中读入int
istream_iterator<int> end_of_stream;
while(cin_it != end_of_stream)       // 空迭代器作为结束标志
	vec.push_back(*cin_it++);         //  挨个存入vec
//  写法2
vector<int> vec(cin_it, end_of_stream);     //  利用迭代器初始化vec，读到结尾或者非int的内容时结束

ofstream outfile;       //  输出文件流
ostream_iteraotr<Sales_item> output(outfile, " ");       //  每个输出元素用空格隔开 


反向迭代器：
vector<int>::reverse_iterator r_iter;
for(r_iter = vec.rbegin()); r_iter != vec.rend(); ++r_iter)
	cout << *r_iter << endl;



   ///////////////////////////////            编程小技巧             ////////////////////////////
   //       用于计算某数字二进制有几个1的代码
   int func(int x) {
		int count = 0;
		while(x) {
			count++;
			x = x&(x-1);       //  注意到此运算每次消一个1
		}
		return count;
   }
   
//   两个数求和的一半，不溢出的方法
int x,y;    //  只能是int，浮点数表示方法不一样
halfsum = (x&y)+((x^y)>>1);        //  x&y得到相同的位，即相同位的和的一半； x^y得到所有不同位，然后除以2

//   利用宏定义求取结构体里某个变量的相对偏移量
#define FIND(struc, e) (size_t)&(((struc *)0)->e)  // 把0地址转成stuc *类型，求取其e的地址即得到相对偏移
//   注意到这种宏定义使用的时候如果后面接上运算符超级容易出错，所以宏定义的这种使用方法不好!!!!!!!!

//  宏定义求最小值
#define MIN(A,B) ((A)<=(B)?(A):(B))     // 注意宏中所有参数都要用括号括起来

//  VC特征:  使用pack，强制结构体内容不对齐
#pragma pack(1)
struct aStruct {
	char cValue;
	int ivaule;
};
#pragma pack()

#pragma warning(disable: 4786)  //  去掉编译过程中的warning，但如果warning来自库，则必须放入库的头文件的include之前


        ///////////////////////////////            编程小技巧             ////////////////////////////
一般函数和头文件：
#include <fstream>
ifstream map_file(argv[1]);         // 用参数1中的文件名初始化map_file输入流
map_file >> key >> value;
string fileName;
ifstream infile(fileName.c_str());     //  c字符串
map_file.getline(a, 100);        //  从输入文件读一行字符到字符数组a，最多读100个

infile.good();      //  返回一个bool值，表示文件打开是否正确
infile.bad();       //  返回一个bool值，表示文件打开是否错误
infile.fail();      //  和bad相似，但没那么严重
infile.get();       //  每次返回一个字符
infile.peek();       //  返回文件的下一个字符，但并不实际读取他，用get()继续调用还是同一个字符
infile.ignore(int, char);    //   第一个是需要跳过的字符数，第二个是一个字符，遇到就停止
infile.eof();       //  判断是否到文件结尾

打开标志：
ios::app  添加到文件尾
ios::ate  把文件标志放在末尾而非起始
ios::trunc  默认，截断并覆写文件
ios::nocreate  文件不存在也不创建
ios::noreplace  文件存在则失败

// 使用二进制形式进行文件读写
ofstream fout("file.dat", ios::binary);     // 以二进制的方式打开文件
int number = 30;
fout.write((char *)(&number), sizeof(number));  // 第一个参数必须是char类型指针，第二个参数是存入对象的字节大小
//  二进制写入结构体比ascii有优势
struct OBJECT { int number; char letter;} obj;
obj.number = 15;
obj.char = 'M';
fout.write((char *)(&obj), sizeof(obj));
ifstream fin("file.dat", ios::binary);       //  以只读的方式打开二进制文件
fin.read((char *)(&obj), sizeof(obj));


#include <string>
ifstream input(argv[2]);
string line;
getline(input, line);            //  调用string库中的getline每次读入一行
input >> line;                   //  每次只能读入空格或换行符分隔的一部分

#include <sstream>           //  用于处理字符串的分段问题
istringstream stream(line);   // 将读入的行按字符串分段
string word;
stream >> word;

利用stringstream进行字符串到整数的转换：因为每个转换都要涉及到输入和输出操作
stringstream ss;
string result = "10000";
int n = 0;
ss << result;
stream >> n;      // 得到n等于10000

//      每次读入一行，行内按空格进行分割，一次输出分割的每一个部分
void getNeighborMatrix(const string &filename, int size) {
	ifstream map_file(filename);        // 打开输入文件      ifstream map_file; map_file.open(filename); 也可以
	string in_line;
	
	while(map_file >> in_line) {
		istringstream line_stream(in_line);
		string word;
		while(line_stream >> word) {
			stringstream ss;
			int i;
			ss << word;
			ss >> i;
			cout << i << endl;
		}
	}
}




//  多次转换使用同一个stringstream时，需要调用clear()方法 ss.clear();
//  使用ss.str("");更好，因为如果输入一个1.2234时第一次输出int型，则小数部分留在流内，再输出到float时会把小数部分输出
template<class out_type, class in_type>       // 把输入输出模板化了
out_type convert(const in_type &t) {
	stringstream ss;
	ss << t;                      //      关键是输入输出类型
	out_type result;
	ss >> result;
	return result;
}
convert<string>(2.345);      // 得到2.345的字符串形式     <>给定输出格式，输入格式在形参里面给出
convert<double>("2.345");     //   得到2.345的浮点数形式


//   指定程序所处的段，这是GCC提供的一个扩展机制，格式：__attribute__((section("name"))) name为.text .data这样的段名
__attribute__((section("FOO"))) int global = 42;    // 将变量global放入名为FOO的段中
__attribute__((section("BAR"))) void foo() {}        //  将函数foo放入名为BAR的段中

#pragma data_seg("FOO")           //  windows平台下将变量global放入FOO段，记得要恢复回去
int global = 1;
#pragma data_seg(".data")

__attribute__((weak))  weak2 = 2;           //  定义weak2为弱符号
__attrubute__((weakref)) void foo();        //   定义foo为弱引用

int global __attribute__((nocommon));       //  把未初始化的全局变量global不以common块形式处理，此时global是强符号

#include <stdio.h>
#include <pthread.h>
int pthread_create(pthread_t* const pthread_attr_t*, void* (*)(void*), void*) __attribute__((weak));
int main() {
	if(pthread_create) {
		printf("this is multi-thread version!\n");
	}else{
		printf("this is single-thread version!\n");
	}
}
在linux下不同的编译参数动态生成对应版本：
gcc pthread.c -o pt    运行后输出  this is single-thread version!
gcc pthread.c -lpthread -o pt  运行后输出  this is multi-thread version!

TinysHelloWorld.lds      // 简单的链接脚本的例子  用于链接a.o  b.o等文件
ENTRY(nomain)        //  指定程序入口
SECTIONS
{
	. = 0x08048000 + SIZEOF_HEADERS;          //　设置当前虚拟地址
	tinytext : { *(.text) *(.data) *(.rodata) }      // 将原来所有目标文件里的所有三个段依次合成一个新的tinytext段
	/DISCARD/ : { *(.comment) }       //　所有输入文件的.comment段丢弃
}
//  将目标文件调用TinysHelloWorld.lds脚本静态链接成一个可执行文件
ld -static -T TinysHelloWorld.lds -o TinysHelloWorld TinysHelloWorld.o


gcc 的  -ffunction-sections 和 -fdata-sections 选项将每个函数或者变量保存到独立的段中

gcc -fPIC -shared -o Lib.so Lib.c // -shared表示产生共享对象  -fPIC表示使用地址无关代码PIC(Position-independent Code)
gcc -o Program1 Program1.c ./Lib.so      //  将程序与动态库进行链接


gcc -E hello.c -o hello.i           //  只进行预编译
gcc -S hello.c -o hello.s          //   编译
as hello.s -o hello.o       //  汇编，但不链接
gcc -c SimpleSection.c -o SimpleSection.o //  只编译不链接
ld -static /usr/lib/crt1.o  hello.o    // 链接
ld a.o b.o -e main -o ab  // 将main作为函数入口，合并两个目标文件为一个
linux下，ELF可执行文件默认从地址0x08048000开始分配
gcc main.c b1.so b2.so -o main -XLinker -rpath ./     // -XLinker -rpath ./  链接器从当前路径寻找共享对象
gcc -o RunSoSimple RunSoSimple.c -ldl            //  使用Dynamical Loading库

file foobar.o          //  查看文件的格式信息
size SimpleSection.o    //  查看三个基本段的大小
nm SimpleSection.o        //  查看ELF文件的符号表
c++filt _ZN1N1C4funcEi     //  解析C++修饰之后的名称 分割为：_Z N 1N 1C 4func E i
ar -t libc.a                 //  查看库文件包含了哪些文件s       windows下是lib /LIST libcmt.lib
ldd Program1              // 查看程序主模块或者共享库依赖于哪些共享库

objdump -d a.o                //   查看.text段的二进制和反汇编代码
objdump -r a.o                // 查看目标文件的重定位表
objdump -h SimpleSection.o   // 查看各种目标文件的结构和内容
readelf -S SimpleSection.o   //  查段表等各个表的信息
readelf -sD Lib.so              //  查看elf文件的动态符号表和它的哈希表


       ///////////////////////////////            类定义相关             ////////////////////////////
	   
	   
//  在一个给定的源文件中，一个类只能被定义一次。如果在多个文件中定义一个类，每个文件中的类定义必须是完全相同的
//  所以将类定义放在头文件中，这样每个使用类的文件中都以同样的方式定义类了
	   
class AllInfo;   // 类的声明	   
//  只有当类定义体完成之后才能定义类，因此类不能具有自身类型的数据成员。然而，只要类名一出现就可以认为类已声明
//  此时，可以定义指向自身类型的指针或者引用

//  在类中声明而不定义成员函数是合法的，使用未定义成员的任何尝试将导致链接失败 LINKXXX之类的错误
//声明但不定义private复制构造函数：用户代码中的复制尝试将在编译时标记为错误，成员函数和友元中的复制尝试将在链接时报错
	   
class AllInfo {
private:
	int base;
	int ano_base;
	
	mutable int changable_base;    //  mutable使得本变量可以在常量成员函数里使用，就算对象是const时也可以改变这个值
	
	const int const_base;         //  必须通过参数列表进行初始化
	//   之所以如此是因为这些变量要在定义的同时初始化
	//   而一个类的构造函数调用时初始化列表的时候就是该类所有成员定义生成的时候
	int &ref_base;                //  必须通过参数列表进行初始化 
	
	//  类的static“数据成员”必须在类定义体的外部定义，此处不是定义只是声明
	//  static成员可以作为默认实参，因为在初始化新对象时，类的静态成员变量的值都已经知道了
	static int static_base;          //  静态成员变量只能在类外部初始化
	static const int static_const_int_base = 100;     //  静态常量成员"整型"变量的初始化可以直接初始化，也可以在外部
	static const double static_const_double_base;      //  静态常量成员"浮点型"变量只能在类外部初始化
	
	//  可以在类里面声明一个类自身的静态成员
	//  但不能声明自身的一个普通成员，只能声明指向类自身的指针
	//  因为此时类还未定义完成，生成对象时不知道分配多少大小空间，容易形成空间分配死循环
	//  由于静态成员根本就不放在对象空间里，所以声明普通静态成员不生成对象时空间的分配
	static AllInfo self_contain;
	
	//  对一个对象进行复制时，其指针成员变量执行普通的值复制，所指向的对象内容是同一个对象
	AllInfo *pAllInfo;              
	
	void do_display(std::ostream &os) const {
		os << base;
	}
public:

	//  类内定义别名，外部可以使用这个别名，因为放在public中
	//  定义的这个类型，外部可以用  AllInfo::index 来访问
	typedef std::string::size_type index;

     //显示定义默认构造函数，默认构造函数需要初始化内置类型成员变量，仅构造函数有初始化列表，仅在定义中而不是声明中
	 // 构造函数初始化式要比构造函数内部的语句执行的早
	 // 在执行内部语句之前，类的类类型数据成员已经用初始化式或者成员默认构造函数初始化过一次了
	 // 没有默认构造函数的类类型成员，const或者引用类型的成员，都必须在初始化列表里初始化，
	 // 对于后两者，不能在构造函数里对他们赋值
	 // 初始化列表中成员初始化的顺序依据成员定义的顺序，与初始化列表中的顺序无关
	 // 类类型成员的初始化式就是调用该类的某一种可用的构造函数
	 // 没有默认构造函数的类不能用作动态分配数组的元素类型
	AllInfo(): base(1), ano_base(2), changable_base(3)， const_base(4), ref_base(base)
	{}
	
	// 类的成员函数的初始化列表的初始化顺序是根据成员变量的声明顺序来执行的，所以先初始化base，再初始化ano_base
	// 函数重载只关注函数名，参数个数，参数类型，不关心返回类型
	// 此处构造函数含有默认实参之后，会和上面的构造函数冲突，生成对象时不知道调用哪个构造函数
	// 建议多使用带默认实参的构造函数，他包含不带参数的构造函数，从而减少了代码重复
	AllInfo(int i=1): ano_base(3), base(2)，const_base(4), ref_base(base) {}//  带参数的构造函数，重载了构造函数
	
	// 可以用单个实参来调用的构造函数定义了从形参类型到该类型的一个隐式转换
	//例如：AllInfo.issame("abc");  形参为AllInfo类型，则先用abc构造一个临时的AllInfo对象，再把这个临时对象传给issame
	// AllInfo(const string s): ano_base(3), base(2)，const_base(4), ref_base(base) {}
	// 使用explicit关键字防止在需要隐式转换的上下文中使用构造函数，explicit只能用于类内部的构造函数声明上！！！\
	// 当构造函数被声明为explicit时，编译器将不使用他作为转换操作符
	explicit AllInfo(const string &s): ano_base(3), base(2)，const_base(4), ref_base(base) {}
	
	
	// C++支持两种初始化形式，直接初始化和复制初始化，直接初始化将初始化式放在圆括号中，复制初始化使用=符号
	// 对于类类型对象，直接初始化直接调用与实参匹配的构造函数，复制初始化总是调用复制构造函数
	// 复制初始化首先使用指定构造函数创建一个"""临时对象"""，然后用复制构造将这个临时对象复制到正在创建的对象
	// vector<string> svec(5);  容器的这种构造方式使用了默认构造函数和复制构造函数，先创建临时变量，再复制到每个位置
	// AllInfo(const AllInfo&);       仅声明不定义，在类内部定义的函数默认都是inline的
	// 类的数据成员中有指针 或者 在构造函数中分配了其他资源 时，必须对复制对象时发生的事情进行控制
	AllInfo(const AllInfo& a) {}         //  复制(拷贝)构造函数
	
	// 第一个操作数隐式的绑定到this指针
	// 赋值操作符的实现中，要考虑对原有左值内容的释放，特别是如果类中有资源集 或者 指向数组的指针 的时候
	// 赋值操作符通常要做复制构造函数和析构函数也要完成的工作，一般通用工作应放在private实用函数中
	AllInfo& operator=(const AllInfo& a) {       //  赋值操作符，  =重载函数
		if(&a != this) {    // 这一步判断往往是必要的，因为自己定义的赋值操作符一般都要先删除再赋予
			AllInfo tmp(a);     //  调用AllInfo的复制构造函数初始化临时变量，只有有指针需要开辟内存的时候需要这么做
			AllInfo* pTmp = tmp.pAllInfo;          //  定义一个指针的临时变量
			tmp.pAllInfo = pAllInfo;           //  这么做的理由是做到异常安全性
			pAllInfo = pTmp;               //  类里面的指针变量要通过这种方式来赋值
			....                 //  其他类内部变量的赋值
		} 
		return *this;         //  因为tmp是函数内部临时变量，作用域结束后会调用析构函数释放指针指向的内存空间
	}        
	
	// 合成析构函数按对象创建时的逆序撤销每一个非static成员，即按成员在类中声明次序的逆序撤销成员
	// 没有返回值，没有形参
	~AllInfo() {}        //  析构函数
	
	
	// 除了函数调用操作符，重载操作符的形参数目(包括隐式的this指针)与操作符的操作数数目相同
	// 作为类成员的重载函数，其形参看起来比操作数数目少1，因为已经包含限定为第一个操作数的this形参
	// 函数调用操作符可以接受任意数目的操作数
	// 四个不能重载的操作符：    ::       .*       .       ?:
	// + - * & 即可做一元操作符又可做二元操作符的，有操作数数目决定定义的是几元的
	//重载操作符的优先级、结合性和操作数数目是固定的。心得：因为语法分析器是死的，编译器先进行语法分析再进行词法分析
	// 除了函数操作符以外，重载操作符使用默认实参是非法的
	// = [] () -> 四个操作符重载必须定义为成员函数，如果定义为非成员函数将在编译时报错
	// 复合赋值操作符通常定义为类成员函数 += -= *= /=
	//  改变对象状态 或 与给定类型紧密联系的 如 ++ -- 解引用，通常定义为类成员函数
	//  对称操作符，如算数操作符、相等操作符、关系操作符和位操作符，最好定义为普通非成员函数
	//  加号可声明为非成员操作符重载，因为他不对左边的操作数做任何修改，而是返回一个新对象
	//  AllInfo operator+(const AllInfo&, const AllInfo&);     //     可以用这种形式在类外声明
	//  AllInfo operator+(const AllInfo&);   //  重载加号，返回一个右值，不推荐，最好定义为普通非成员函数
	AllInfo& operator+=(const AllInfo&);  //  最好同时定义+=重载，返回一个左值，并且+=的实现可以用作+的内部实现
	//  除了 a += b;的正常调用，还可以显示的  a.operator+=(b);   进行调用
	
	//  这两个一般成对定义，定义完之后可以使用泛型算法如：find
	inline bool operator==(const AllInfo &lhs, const AllInfo &rhs) {}
	inline bool operator!=(const AllInfo &lhs, const AllInfo &rhs) { return !(lhs==rhs); }
	
	
	//  将某些操作符的重载定义为友元，因为此时操作符很可能需要访问内部私有变量
	//  不能将IO操作符定义为类的成员的理由是：如果这么做了，则左操作数只能是该类类型的对象，因为左操作数是this
	//  IOstream是没有复制构造函数的，所以不能复制，此类操作符重载一般声明为友元
	friend std::istream& operator>>(std::istream&, AllInfo&);    //  注意第一个形参和返回类型，这是惯例设置
	friend std::ostream& operator<<(std::ostream&, const AllInfo&);
	
	
	//  const对象、指向const对象的指针或引用只能调用其const成员函数，调用非const成员函数报错
	//  形参表之后的const将成员函数声明为常量，const必须同时出现在声明和定义中
	//  此处this的类型是：  const AllInfo& const this;   this定死不可改，如果返回本对象必须是const的
	//  可以对比返回引用的函数
	const AllInfo& workFunc() const {    
		//  定义常量成员函数，调用该函数的对象的内容不可修改，只可读，此处const修饰隐含的this
		changable_base = 1;            //  const成员函数使用的成员变量用mutable修饰符之后，
		return *this;
	}   
	
	// 基于const的重载，此种重载的好处是： a.display()调用时根据a是否是const进行选择
	// 在a.get().display()中依据get()的返回类型选择，  a.get().display().set()中显然会选择非const版本
	const AllInfo& display(std::ostream &os) {
		do_display(os);   return *this;     // 内部统一调用一致的私有函数，标准化处理
	}
	const AllInfo& display(std::ostream &os) const {   // 只有这一种定义时a.get().display().set()不合法
		do_display(os);   return *this;
	}
	
	
	
	//  隐含this的类型是：  AllInfo* const this;   不可以改变this保存的地址，但是可以改变this指向的内容
	//  从返回值也可以看出，返回的是普通引用
	AllInfo& get() {   //  返回引用的函数，可以形成连续调用的格式，例如  a.get().get().get();
		return *this;
	} 
	
	//  内联函数，inline总是放在所有参数最前面
	//  在类内部定义的成员函数，将自动作为inline处理
	//  int getBase();     只声明不定义
	//  声明为inline的函数在外部定义时可加可不加inline，在声明时不加inline而在定义时加inline也是合法的
	inline int getBase() {       
		return base;
	}
	
	
	// 静态成员函数没有this形参，可以直接方位类的static成员，但不能直接使用非static成员，显然静态成员函数没有const
	// 静态成员函数的调用，可以通过类名加作用域符直接调用，也可以通过对象，引用或者指向该类类型的指针间接调用
	static void workFunc() {}       //  静态函数，只能调用成员的静态变量
	
	//  友元机制允许一个类将对其非公有成员的访问权授予指定的函数或类，声明以关键字friend开始，只能出现在类定义内部
	//  友元可以是普通的非成员函数，或前面定义的其他类的成员函数，或者整个类
	//  如果友元类的成员函数有多个重载，则对所有想声明为友元类成员函数的都需要加friend声明
	friend outFunc(AllInfo&);
	friend NewInfoClass& NewInfoClass::get(AllInfo&);     //  其他类的成员函数
	friend NewInfoClass& NewInfoClass::get(int, AllInfo&);    // 类的成员函数的重载设为友元，同样要声明，不可少
	friend class NewInfoClass;         //  整个类，类成为友元时，整个类的所有成员函数都可以访问授予友元关系的类成员
};           //  注意此处结束的分号是必须的！！！！！！！！！！


//  此处是类的静态成员变量的定义，并且要在定义的同时初始化
//  此处必须加上类明和作用域符，此时静态变量定义在类中，可以直接使用类的所有私有函数和私有变量
//  比如：  int AllInfo::static_base = initialBase();  也是合法的
int AllInfo::static_base = 1;       //  静态成员变量的初始化
const double AllInfo::static_const_double_base = 1.1;     //  同上
AllInfo AllInfo::self_contain;       // 调用默认构造函数

AllInfo operator+(const AllInfo &lhs, const AllInfo &rhs) {         //  类外
	AllInfo ret(lhs);            //  复制构造函数进行赋值
	ret += rhs;                  //  调用复合操作符进行实质处理
	return ret;
}



//  下面的构造函数定义错误，不能在内的定义外部使用explicit，跟inline不同，跟static倒是很像
//  explicit AllInfo(const string &s = ""): ano_base(3), base(2)，const_base(4), ref_base(base) {}

//  初级C++程序员易犯错误：
AllInfo myinfo();       //  此时可以编译通过，但实际上此句被编译器解释为一个函数声明，因为带了 ()
info.get();        //  此处调用在编译时会立马报错，因为显然不能将一个成员访问符号作用与一个函数
正确的声明：
AllInfo myinfo;           //  默认构造函数初始化
AllInfo myinfo = new AllInfo();   //  先初始化一个AllInfo对象，再用它来按值初始化myinfo


//  对于没有定义构造函数并且全体数据成员均为public的类，可以采用与初始化数组元素相同的方式初始化其成员
struct data {
	int val;
	char *ptr;
};
Data val1 = {0, "abcdefg"}    // 合法，注意到必须根据数据成员声明的次序来初始化


///         智能指针相关：   用友元实现智能指针的方法
//     第一种方法不好的地方在于：对于每一个需要使用智能指针的类，都需要专门为他定义智能指针，在专用智能指针中声明友元
// 专用体现在两点：1、智能指针所有内容都是私有的，只能友元类使用 2、每个要使用智能指针的类都需要在智能指针中显示声明
//       Smart_Pointer  作为应用类的成员被包含，用来实现智能指针
class Smart_Pointer {           //  Smart_Pointer的所有成员都是私有的
	friend class HasPtr;        // HasPtr为实际的应用类，这个应用类作为友元可以直接处理Smart_Pointer的所有成员
	int *ip;                    //  指向同一个对象的指针
	size_t use;                 //　计数器
	Smart_Pointer(int *p):ip(p), use(1) {}
	~Smart_Pointer() { delete ip; }
};

class HasPtr {           //  这个应用类有指针，所以要用智能指针
public:
	//	 构造必须初始化第一个智能指针，因为这是第一个指向实参的，所以新生成一个智能指针，具体地址存在智能指针里面
	HasPtr(int *p, int i): ptr(new Smart_Pointer(p)), val(i) {}
	//   复制构造函数的实现，注意初始化列表赋值的同时，在内部对智能指针进行++
	HasPtr(const HasPtr &orig): ptr(orig.ptr), val(orig.val) {
		++ptr->use;
	}
	//   赋值构造函数
	HasPtr& operator=(const HasPtr &rhs) {
		++rhs.ptr->use;        //  先使新的计数加一，除了少运算之外，最重要的是如果是自身赋值，则整个操作无非++再--
		if(--ptr->use == 0) {    	//  检查老的计数减一之后是否需要删除
			delete ptr;
		}
		ptr = rhs.ptr;        //  已自加过，不用再加
		val = rhs.val;
		return *this;
	}
	//   析构函数，当计数为0时，删除智能指针，智能指针析构的时候，将存储的指针指向的对象一并delete掉了
	~HasPtr() {
		if(--ptr->use == 0) delete ptr;
	}
	
	int *get_ptr() { return ptr->ip; }
	void set_ptr(int *p) { ptr->ip = p; }
	
	int get_ptr_val() { return *(ptr->ip); }
	void set_ptr_val(int i) { *(ptr-ip) = i; }
	
private:
	Smart_Pointer *ptr;
	int val;
};

//   硬性的浅复制的智能指针（实际上已经没有智能指针了，就是都开辟空间各寸各的，互不相干），string对象就这么实现的
class HasPtr {
public:
	HasPtr(const int &p, int i): ptr(new int(p)), val(i) {}    //  此处是关键不同，直接new一个新空间放入存储值
	
	HasPtr(const HasPtr &orig): ptr(new int(*origin.ptr)), val(orig.val) {}  // 关键不同之二，复制时也另开空间
	
	HasPtr& operator=(const HasPtr &rhs) {        //  给本身赋值时，也是正确的
		*ptr = *rhs.ptr;             //  能被赋值的时候，内存空间肯定已经开辟，直接赋值就好
		val = rhs.val;
		return *this;
	}
	
	~HasPtr() { delete ptr; }
private:
	int *ptr;
	int val;
};


在C/C++中，当数组作为函数的参数进行传递时，数组就自动退化为同类型的指针



//     Linux下用C++调用Shell进行截屏 
#include <stdio.h>  
#include <cstdlib>  
int main()  
{  
 printf("I am a picture capture\n");  
//调用import命令截当前全屏  
//文件名是“1.jpg”  
 system("import -window root 1.jpg");  
 printf("Done!\n");  
 return 0;  
} 