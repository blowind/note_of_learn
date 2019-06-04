///     单例模式(Singleton Pattern)：单件模式确保程序中一个类最多只有一个实例。
//      我们在程序中会遇到这种情况如：线程池，缓存，对话框，打印机，显卡等设备驱动程序。
//      这些类对象只能有一个实例，如果制造多个实例，就会导致许多问题产生。
class Singleton {
private:
	static Singleton *unique;      //  类成员不可以初始化
	Singleton() {                   //  构造函数私有化，这样外部就不可能new一个新实例，只能内部调用
		base = 1;                           //  在构造函数里初始化正常行驶逻辑处理的功能变量
		cout << "consturct singleton" << endl;
	} 
	~Singleton() {                     //   防止外界删除已有实例，仅自己可以删除
		cout << "delete singleton" << endl;
	}

	Singleton(const Singleton&);       //  复制构造函数保护起来，防止外部进行复制
	Singleton& operator=(const Singleton&);   //  =重载函数保护起来，防止外部赋值  singleton *p = 的写法都不合法了

	int base;                          //  内部进行事务逻辑处理的功能变量

public:
	static Singleton& getInstance() {     //  唯一获得实例的方法，每次要取得实例必须通
		cout << "new singleton" << endl;
		if(unique == NULL) {              //  此方法通过判断保证仅一个实例
			unique = new Singleton();
		}
		return *unique;         //  必须返回一个实例的引用，不然外部无法获得这个实例，从而无法使用这个实例
	}
	static void release() {            //   对应生成函数，必然有清除函数，必然成对出现，通过它间接调用析构函数
		cout << "release singleton" << endl;
		if(unique != NULL) {
			delete unique;        //  调用析构函数
			unique = NULL;        //   必须重新赋空，否则产生悬垂指针，外部多次调用release函数时产生不可预料错误
		}
	}
	int workingFunc(int i) {       //  功能函数，用于处理内部的事务逻辑
		return base += i;
	}
};

Singleton* Singleton::unique = NULL;    //  必须在全局初始化“私有”静态变量，否则getInstance内部逻辑无法判断！！！！！
int main() {
	Singleton::getInstance();        //  调用构造函数实例化唯一一个对象
	Singleton::getInstance();        //  只进行判断，不调用构造函数
	cout << Singleton::getInstance().workingFunc(3) << endl;    //  通过getInstance获得对象进行正常逻辑处理
	cout << Singleton::getInstance().workingFunc(8) << endl;
	Singleton::release();           //  间接调用析构函数
	Singleton::release();           //  因为解决了悬垂指针问题，多次调用无害
	getchar();
}
//  上例每次都是通过判断unique == NULL来确实是否要实例化，这被称为延迟实例化（对应懒汉模式）
//  "懒汉模式"无法在多线程环境下运行，当两个线程前后脚先后运行到unique == NULL时，两个判断都为true，从而new了两次
//  下面介绍"饿汉模式"，饿汉模式无论是否调用该类的实例，在程序开始时就会产生一个该类的实例，并在以后返回此实例
//  通过声明内部静态变量而不是静态变量指针来实现，由静态初始化实例保证其线程安全性
//  因为静态实例初始化在程序开始时进入主函数之前就由主线程以单线程方式完成了初始化，不必担心过多的线程问题
//  注意到这种模式没有静态变量释放，释放是靠程序运行完系统来释放

class Singleton {
private:
	static Singleton m_Instance;      //  关键所在，声明为一个静态成员变量，而不是一个指针

	Singleton() {
		cout << "construct singleton" << endl;
		base = 1;
	}
	~Singleton() {
		cout << "delete singleton" << endl;
	}

	Singleton(const Singleton&);
	Singleton& operator=(const Singleton&);

	int base;

public:
	static Singleton* getInstance() {
		return &m_Instance;
	}
	int workingFunc(int i) {
		return base += i;
	}
};
Singleton Singleton::m_Instance;  //  在程序开始前实例化静态变量，此时是变量而不是指针
int main() {
	Singleton::getInstance();     //  此时已实例化，直接返回指针
	Singleton::getInstance();     //  此时已实例化，直接返回指针
	cout << Singleton::getInstance()->workingFunc(3) << endl;
	cout << Singleton::getInstance()->workingFunc(8) << endl;
	getchar();
}
//   这种模式的问题也很明显, 类现在是多态的, 但静态成员变量初始化顺序还是没保证。例如：
//   有两个单例模式的类 ASingleton 和 BSingleton, 某天你想在 BSingleton 的构造函数中使用 ASingleton 实例
//   因为 BSingleton m_pInstance 静态对象可能先 ASingleton 一步调用初始化构造函数
//   结果 ASingleton::Instance() 返回的就是一个未初始化的内存区域, 程序还没跑就直接崩掉.
class Singleton {
private:
	static Singleton* p_Instance;      //  声明一个指针，在需要时实例化

	Singleton() {
		cout << "construct singleton" << endl;
	}
	~Singleton() {
		cout << "delete singleton" << endl;
	}

	Singleton(const Singleton&);            //  去掉外部复制功能
	Singleton& operator=(const Singleton&);      //  去掉外部的 = 功能

public:

	Singleton& getInstance() {
		if(NULL == p_Instance) {        //  判断需不需要加锁，因为加锁是个消耗很大的操作
			Lock();                     //  借用其他的类来实现，例如boost
			if(NULL == p_Instance) {    //     此处判断不可少，因为两个线程可以同时进入第一个判断，少了得话还是new两次
				p_Instance = new Singleton();
				//  此处实际还是可能发生线程安全问题，原因在于实际上这句话执行了三个步骤
				//  1、分配内存   2、在内存位置调用构造函数   3、将内存的地址赋值给p_Instance
				// 其中2和3的顺序可以颠倒，如果由于CPU的乱序执行进行了颠倒，则外面判断时已经非空，而实际构造函数未执行
				//  此时使用返回的构造实例，就不知道会发生什么了
				//  改进方法，加上一条CPU提供的barrier指令，该指令组织CPU将之前的指令交换到barrier之后
				//  Singleton* temp = new Singleton();
				//  barrier();
				//  p_Instance = temp;
			}
			Unlock();                   //   借用其他类实现
		}
		return *p_Instance;
	}
	//     析构函数必须声明为虚函数，否则容易造成子类不调用父类对象析构函数，导致父类内存泄露
	//     另一种内存泄露情况是：父类指针指向子类的实例，delete该指针的时候，不会调用子类的析构函数，造成泄露
	virtual void release() {       //  不写一般情况下也可以，好点的系统会在程序退出时自动释放占用的系统资源
		if(NULL != p_Instance) {            
			Lock();
			if(NULL != p_Instance) {
				delete p_Instance;     //  析构的时候也需要加锁，否则前后脚执行这一步的时候，后一次执行释放悬垂指针
				p_Instance = NULL;
			}
			Unlock();
		}
	}
};
Singleton* Singleton::p_Instance = NULL;
//   处理大量数据时，以上的加锁解锁操作成为性能瓶颈
//   C++ 0x 以后，要求编译器保证内部静态变量的线程安全性，可以不加锁。但C++ 0x 以前仍需加锁。
class Singleton {
private:

	Singleton() {
		cout << "construct singleton" << endl;
		base = 1;
	}
	~Singleton() {
		cout << "delete singleton" << endl;
	}

	Singleton(const Singleton&);
	Singleton& operator=(const Singleton&);

	int base;

public:
	static Singleton& getInstance() {
		//	 静态变量的特性保证其只在第一次调用时被初始化
		//   达到动态初始化效果的同时保证了成员变量和singleton本身的初始化顺序
		static Singleton m_Instance;      //  关键所在，在静态函数内声明的静态变量编译器保证其线程安全性
		return m_Instance;      //  返回引用保证了调用者不需要检查返回值的有效性
	}
	int workingFunc(int i) {
		return base += i;
	}
};
int main() {
	//Singleton s = Singleton::getInstance();    // 此种声明要调用拷贝构造函数，
	Singleton& s = Singleton::getInstance();     //   使用引用的话运行没有问题
	Singleton::getInstance();
}


///  多线程导致的不正常执行
//   可重入函数：一个函数被称为可重入的，表明该函数被重入之后不会产生任何不良后果
//   一个函数要被重入的两种情况：1、多个线程同时执行这个函数   2、函数自身直接或者间接调用自身
int sqr(int x) {        //  可重入函数的例子
	return x*x;
}
可重入函数的特点：
1、不使用任何静态或全局的非const变量
2、不返回任何静态或全局的非const变量
3、仅依赖调用方提供的参数
4、不依赖任何单个资源的锁(mutex等)
5、不调用任何不可重入的函数

编译器过度优化导致的线程不安全性，例如：
 x = 0;
 Thread1   Thread2
 lock();   lock();
 x++;      x++;
 unlock(); unlock();
 貌似两个线程加锁之后x++是独立的，实际上可能因为编译器为了提高x访问速度，将x放入某个寄存器，每次读入从寄存器里读入，
 则Thread2正常执行，写回x[2]=1 之后很久，Thread1将寄存器里存储的x[1]=1 值写回(线程的寄存器是相互独立的)
  此种情况可以使用volatile关键字阻止过度优化  
 volatile效果：1、阻止编译器不将变量缓存从寄存器写回  2、阻止编译器调整volatile变量的指令顺序
 例子2：   
 x=y=0;
 Thread1   Thread2
 x=1;      y=1;
 r1=y;     r2=x;
 正常情况r1和r2不可能同时为0，但由于CPU的动态调度，为了提高效率交换两个不相关的相邻指令，则指令可能被调整成
 x=y=0;
 Thread1   Thread2
 r1=y;     y=1;
 x=1;      r2=x;
此处由于是CPU的优化，所以编译器完全无能为力，只能考虑是否能手动调整CPU

另一个例子参考singleton的双判断模式