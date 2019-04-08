		
/****                                               多线程                                        ****/

通常用法：
1、每个业务创建1个Task，1个Task跑在1个或者多个线程Thread上，共享状态/互斥资源在每个Thread上运行的Task实例之间竞争/共用；
2、业务Task一般实现Runable或Callable接口，然后使用具体线程（直接Thread创建或者Executors线程池给出）去运行该Task，Thread子类实现Task可行本质上也是因为Thread实现了Runable接口；
3、ThreadLocal绑定一个对象到Thread，该对象可以在多个Task之间共享使用且一定线程完全（因为归属1个线程，任何Task中都是先获取线程再获取value，不会被其他线程获取，单线程运行永远是线程安全的）；






【内置锁】（监视器锁）：
即synchronized同步代码块机制，使用Java对象作为实现同步的锁，由作为锁的对象引用和锁保护的代码块两部分组成。
实例synchronized方法的锁是实例对象，类synchronized方法的的锁是类对象。
内置锁是可重入的。



【可重入】
对已经获得锁资源的线程，再次调用该锁锁定的代码块时依然成功，就表示该锁是可重入锁。
重入意味着获取锁的操作粒度是“线程”而不是“调用”。
重入的一种实现方式是为每个所关联一个获取计数值和所有者线程引用，多次重入时计数值递增，线程退出同步代码块时计数器递减，减到0时释放锁。


public class Widget {
	public synchronized void doSomething() {
		...
	}
}
public class LoggingWidget extends Widget {
	// 如果synchronized对象锁不是可重入的，则进入子类doSomething()时已经获取LoggingWidget对象锁，
	// 调用父类doSomething()方法再次获取LoggingWidget对象锁对象锁就会失败，导致死锁
	public synchronized void doSomething() {
		super.doSomething();    
	}
}




加锁机制既可以确保可见性又可以确保原子性，而volatile变量只能确保可见性。



当且仅当满足以下所有条件时，才应该使用volatile变量：
1、对变玲啊的写入操作不依赖变量的当前值，或者能确保只有单个线程更新变量的值；
2、变量不会与其他状态变量一起纳入不变性条件中；
3、在访问变量时不需要加锁。


多线程下使用的随机数生成器
Random r = ThreadLocalRandom.current();
r.nextInt(10);


1、Java的线程锁是可重入锁，即对象A调用synchronized方法A的时候，在A内部也可以同时调用synchronized方法B，锁计数会加1，如果不可重入，则会造成死锁，同时可重入锁在继承性环境同样成立，即子类synchronized方法A可以调用父类synchronized方法B
2、出现异常时，当前持有的锁会自动释放
3、synchronized 标记不具有继承性，子类中override的方法必须依然加 synchronized 标记
4、数据类型String的常量池特性，synchronized(String)时，需要注意所有字符常量都是同一个对象，要注意锁死误伤，不要用String当锁
5、要避免多次调用锁的死锁问题，如 方法A的synchronizedX块中有synchronizedY块，同时方法B的synchronizedY块中有synchronizedX块
	jdk安装目录下的bin文件，输入“jps”查看当前运行java线程，再输入“jstack -l threadId”查看指定线程状态
6、volatile关键字仅修饰变量，保证变量的可见性，但不保证变量的原子性；但变量的使用并不具有原子性，因此不能带来同步性；
7、关键字synchronized代码块有volatile同步的功能；即保证互斥性和可见性；
8、方法wait()执行后释放锁，notify()执行后不释放锁，而是等到所在synchronized代码块执行完毕退出后释放
9、当线程处于wait()状态时，调用该线程对象的interrupt()方法会出现 InterruptedException 异常
10、使用synchronized的代码里面，传入的锁对象既要当ReentrantLock使用，也要当Condition使用



一般多线程的写法是定义本身处理业务的类 MyTask，然后定义一个 Thread 类的子类，内部包含 MyTask 对象(策略模式)，在 run 函数的 override 中调用 MyTask 对象：
public class MyTask {
	/* 任务类内部实例变量若没有其他地方保证(调用方法保证)，是非线程安全的，因为多个线程共用任务对象  */
	private int notSafe = 5;   

	private Object otherObj = new Object();
	/* 加了synchronized就是线程安全的了，不加notSafe变量值不是线程安全 */
	/* 注意synchronized是用来锁对象的，默认是this，相同的对象才能共享一把锁(对象监控器)，或者锁类也行，但杀伤面太大 */
	synchronized public void changeVar() {
		if(stateA) 
			notSafe = 11;
		else 
			notSafe = 22;
	}
	
	/* 同变量的设置/获取方法需要用同一个锁同时锁住，否则set过程中切换CPU资源后，可能出现脏读 */
	synchronized public void setVar(int a) {};
	synchronized public int  getVar() {};
	
	/* 同步代码块，此种用法最重要的是确认不要漏互斥操作 */
	public void partSync() {
		doSomethingOne;
		synchronized(this) {
			doSomethingTwo;
		}
		doSomethingThree;
	}
	
	/* 锁其他对象，这样保证本类中仅锁otherObj对象的互斥区是互斥的，锁this或者其他对象的代码依然是异步的，优化提高运行效率  */
	public void otherObjLock() {
		synchronized(otherObj) { doSomethingTwo; }
	}
	
	/* 静态同步synchronized方法，锁的是当前的类，类锁可以对类的所有对象起作用 */
	synchronized public static void staticMethod() { }
	/* 静态同步synchronized代码块，也是锁类 */
	public static void staticBlock() {
		synchronized(MyTask.class) {}
	}
	
	
	/* 非synchronized方法不是独占的，即使一个对象的synchronized方法正在被调用，非synchronized方法也可以同时被调用  */
	public void safeVar() {
		int isSafe = 9;   //  方法内部的变量都是线程安全的，因此每次方法调用在调用栈上实时生成的，每个线程独有
	}
}

/* ReentrantLock 具有完全互斥排他效果，即同一时间只有一个线程在执行ReentrantLock.lock()后面的代码，效率较低下 */
public MyTaskTwo {
	/* 可重入锁，能够进行更精细化的管理，该Lock对象本身就是对象监控器，不像synchronized还要传个参数(哪怕默认参数) */
	private Lock lock;
	private Condition conditionAB;
	private Condition conditionCD;
	
	public MyTaskTwo(boolean isFair) {
		super();
		/* 公平锁表示线程获取锁的顺序是按照线程加锁的顺序来分配的，即FIFO顺序；非公平锁采用抢占机制获取锁，随机获取 */
		/* 不传参数的ReentrantLock默认构造函数是非公平锁 */
		lock = new ReentrantLock(isFair);   // 创建公平锁，传入true表示公平锁，false表示非公平锁
		conditionAB = lock.newCondition();
		conditionCD = lock.newCondition();
	}
	
	public void methodA() {
		try {
			lock.lock();
			doSomething();
			conditionAB.await();   //  相当于 Object.wait();
			// conditionAB.await(long);  相当于 Object.wait(long);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public void methodB() {
		try {
			lock.lock();
			conditionAB.signal();   // 相当于 Object.notify();
			// conditionAB.signalAll();   相当于 Object.notifyAll();
		}finally{
			lock.unlock();
		}
	}
	
	public void methodC() {
		try {
			lock.lock();
			doSomething();
			conditionCD.await();   //  相当于 Object.wait();
			// conditionCD.await(long);  相当于 Object.wait(long);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public void methodD() {  // 可以在客户代码里被调用，用来唤醒等待的线程
		try {
			lock.lock();
			conditionCD.signal();   // 相当于 Object.notify();
			// conditionCD.signalAll();   相当于 Object.notifyAll(); 唤醒所有 await 线程
		}finally{
			lock.unlock();
		}
	}
	
	public void otherMethod() {
		int myCount = 0;
		boolean isTrue = 0;
		
		// 查询当前现成保持此锁定的个数，也就是调用lock()方法的次数
		myCount = lock.getHoldCount();   
		// 获得正等待获取此锁定的线程估计数，如5个线程在await()状态，其中一个获得锁后往下走，此时本函数返回4，有4个线程等待锁
		myCount = lock.getQueueLength();
		// 获得等待相关的给定条件Condition的线程估计数，比如10个线程都在某condition.await()，则返回10
		myCount = lock.getWaitQueueLength(conditionCD);
		
		// 查询指定线程是否正在等待获取此锁，在客户代码调用，此处仅是示例
		isTrue = lock.hasQueuedThread(threadA);
		// 查询是否有线程正在等待与此锁有关的condition条件
		isTrue = lock.hasWaiters(conditionAB);
		// 判断是不是公平锁
		isTrue = lock.isFair();
		// 判断当前线程是否保持该锁锁定状态，即lock()调用后状态
		isTrue = lock.isHeldByCurrentThread();
		// 判断此锁是不是由任意线程保持，即最低有一个线程调用过lock()且还未调用unlock()，则返回true
		isTrue = lock.isLocked();
		// 当前线程未被中断，则获取锁定，如果已经被中断，则出现异常；
		lock.lockInterruptibly(); 
		// 在调用时锁未被其他线程保持的情况下，获取锁
		if(lock.tryLock())
			System.out.println("get it");
		// 锁在给定时间内没被其他线程获取，且当前线程未被中断，获取该锁
		isTrue = lock.tryLock(3, TimeUnit.SECONDS);
		
	}
}
/* 读写锁ReentrantReadWriteLock类，内部有一个读操作相关的锁，也叫共享锁；一个写相关的锁，也叫排他锁  */
public MyTaskTwo {
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	public void read() {
		try {
			//  读锁，读读时可异步访问
			lock.readLock().lock();
			doSomething();
		}finally{
			lock.readLock().unlock();
		}
	}
	
	public void write() {
		try {
			//  写锁，读写互斥，写读互斥，写写互斥；
			lock.writeLock().lock();
			doSomething();
		}finally{
			lock.writeLock().unlock();
		}
	}
}

public class MyThread extends Thread {
	//  线程内部的私有数据，存储在线程独占的内存内，因此是多线程安全的（本线程独有），注意不能是static，每次new要新生成
	private int count = 5;  
	private MyTask task;
	
	/* 每个线程自己的变量，在线程内自己共享，即count是每个MyThread线程对象独有，t1是所有MyThread对象共有  */
	public static ThreadLocal t1 = new ThreadLocal();
	t1.set(anyObj)   // 放入对象
	if (t1.get() != null)
		correspondingType = ti.get();     // 取出对象，类型要用户自己掌控
	
	MyThread(MyTask task) {
		super();
		this.task = task;
	}
	
	@override
	public void run() {
		super.run();
		//  Thread.currentThread() 获取当前语句所在线程的引用，  getName() 获得线程名（一般new同语句块中的setName中设置）
		System.out.println("run方法的打印：" + Thread.currentThread().getName());
		
		/*  当前线程放弃CPU资源，让给其他排队的线程，当然可能下个线程还是自身，CPU资源被自己抢到。注意本调用不会释放这个线程拥有的锁，因此不要在同步代码块里执行本操作，否则所有等待线程很可能只能本线程能执行，失去调用意义*/
		Thread.yield();  
		
		/* 休眠时外部interrupt中止线程，会让线程产生InterruptedException中断，不论休眠调用前后的interrupt。进入休眠的线程不会释放其获得的锁。因此尽量避免在同步块内休眠  */
		Thread.sleep(50000);    
		/*  通过抛出异常的方法来在代码中中止线程，仅仅在外部调用 mythread.interrupt() 并不必然让线程中止 */
		if( this.interrupted() ) {
			throw new InterruptedException();
		}
		
	}
}	

ThreadLocal:
设计目的是用于线程间的数据隔离，通常用于防止对可变的单例对象或全局变量进行共享，即将单例和static变量转为线程私有。
在java中，除了static和单例是共享变量，其余的都是线程私有的。
跨任务Task但是不跨线程。

【需求背景】
需要将某个对象与线程绑定（Task栈封闭的局部变量是与Task任务类绑定在一起的）而不是与具体任务绑定时。
由于业务开发中一般是通过实现了run()方法的任务类来开发线程程序，而不是通过实现Thread的子类来实现业务，因此无法通过在Thread类中使用局部变量存储对象达到同样的效果。

【具体实现】
实现了一个ThreadLocalMap内部类，其中key是具体ThreadLocal对象，value是需要存储的值
通过下面的函数将新生成的ThreadLocalMap存储在每个线程的threadLocals变量中，下面的函数位于ThreadLocal类定义中
void createMap(Thread t, T firstValue) {
	t.threadLocals = new ThreadLocalMap(this, firstValue);
}
每次threadLocals.get()调用是通过Thread.currentThread()获取所在线程，在获取该线程存储的ThreadLocalMap，进而获取value
所以哪怕对同一个Task业务实现，同一块代码中如果set()和get()中间进行了线程切换导致两个操作运行在不同的线程上，取出的也不是存入的那个对象


【设计思路】
具体来说ThreadLocal变量将一个非线程安全的对象存储到一个具体的线程上，
当使用ThreadLocal维护变量时，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
比如数据库连接Connection就是非线程安全的，但是如果Connection从头到尾只被一个线程使用，则这种多线程不安全性就毫无影响，实际使用时可以每次获取Connection后放入线程的ThreadLocal变量中，在线程关闭后释放

【注意事项】
1、通过子类覆写initialValue()方法，提供默认初始值，覆写的方法尽量保证初始值不被其他地方引用
2、通过set(T value)方法设置值，被设置的值不要在其他地方被引用

【使用限制】
每个ThreadLocal对象只能通过set(value)/get()存储一个值，
而且由于ThreadLocalMap保存在对应线程的threadLocals变量里，每个线程实际上也只能有一个ThreadLocalMap（ThreadLocal实际上是ThreadLocalMap的封装工具类，本身不存储数据）


【用法举例】
public class DateUtil {
	public static final ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	}
}


/* 使得ThreadLocal对象有默认值的方法，即子类继承后重载initialValue()函数，注意需要包装类包装静态ThreadLocalExt对象后使用
   例如 Tools.t1.get() 在main里面调用就是main里面存放的值，在mythread里面调用就是mythread里存放的值 */
public class ThreadLocalExt extends ThreadLocal {
	@override
	protected Object initialValue() { return "I am the default value"; }  //  注意这个方法只能被重载，不能在外部调用
}
/* 让子线程从父线程取得值，注意下面两个方法都是重载已有方法  */
public class InheritableThreadLocalExt extends InheritableThreadLocal {
	@override
	protected Object initialValue() { return new Date().getTime(); }  // 通过本函数取得父线程的值
	@override
	// 通过本函数取得父线程的对象并进一步加工，此处对象是initialValue()的返回值
	protected Object childValue(Object parentValue) { return parentValue + "my add thing"; } 
}

public class Test {
	public static void main(String[] args) {
		MyTask mytask = new MyTask();
		MyThread mythread = new MyThread(mytask);
		mythread.setName("A");   //  设置线程名
		//  mythread.run()     //  直接在main 线程中调用函数，没有启用新线程调用
		
		/* 后台线程里( run()里面 )派生的线程仍然是后台线程，
		   后台线程里的finally字句不会执行，因为程序强行关闭后台线程，不给执行的机会 */
		mythread.setDaemon(true);    //  将当前线程设置为守护线程，当所有非守护线程结束后，守护线程自动结束
		
		mythread.start();     //   将mythread线程放入线程队列，准备执行
		mythread.isAlive();   //   判断mythread线程是否处于活动状态，start() 之前是false，之后是true
		mythread.getId();     //   获取mythread线程的唯一标识
		
		/* 不建议使用线程暂停，因为线程如果执行时占用着互斥资源，则暂停后不释放资源锁，该资源无法被其他线程调用
			典型的如println() 之类的I/O资源特别容易被无意识的独占：
			println()内部有锁，子线程正在println()的时候被暂停，其他所有线程都别想再调用println()
			此外，暂停有可能导致对象状态不同步，比如正在修改对象3个状态值的过程中被暂停，此时打印对象三个状态就会不同步
		*/
		mythread.suspend();   //  暂停线程，不建议使用
		Thread.sleep(500);    //    在当前所在线程中休眠，单位是毫秒
		mythread.resume();    //  恢复暂停的线程，不建议使用
		
	/* join在内部使用wait()方法进行等待，synchronized使用“对象监视器”原理做同步，join过程中当前线程被中断，当前线程抛出异常 */
		mythread.join();      //  当前线程无限期阻塞，等待mythread线程销毁（正常或异常都可）
		// 内部使用wait(long)实现，具有释放锁的特点，其他线程可以调用本对象方法，而sleep(long)不释放锁
		mythread.join(1000);  //  当前线程阻塞等待最多1秒，等待到期后需要抢锁，因此是异步的，与其他线程谁先调用不确定
		
		mythread.interrupt();   //  中止线程mythread，但不是立即中止
		/*  测试当前语句所在对象所属线程是否中止，注意 interrupted()是个静态方法 public static boolean interrupted()，
		    因此实际上等同于调用的 Thread.currentThread().interrupted()，
			测试的时候当前语句所在对象，因此此处是对象是main而不是mythread，
			注意：该方法会清除中断状态，因此连续两次调用interrupted()会哪怕第一次是false，第二次也必然是true
		*/
		mythread.interrupted();  
		/*  测试线程对象是否已经中止，此处测试的是mythread，并且没有清除中断状态效果 */
		mythread.isInterrupted();
		
		/* 线程优先级具有继承性，即子线程继承父线程的优先级，除非优先级差别非常大，例如10和1的差距，否则表现不明显  */
		/* 不要在构造器中设置优先级，在run() 方法起始位置设置 */
		Thread.currentThread().setPriority(6);   // 设置当前线程优先级为6（默认是5）
		Thread.currentThread().setPriority(Thread.NORM_PRIORITY + 3);   // 设置当前线程优先级为8，此处使用了库中优先级枚举
		Thread.currentThread().getPriority();   //  获取当前线程优先级
		
		/* 获取线程的状态，主要有 NEW RUNNABLE TERMINATED BLOCKED WAITING TIMED_WAITING */
		Thread.currentThread().getState();
		mythread.getState();
	}
}

/***   生产者/消费者    ***/
//  包装MyStack对象的线程的Run()方法中有个死循环在不停的调用push()和pop()方法，否则方法执行一遍就退出了，线程有两种类型，一个不停调用push()，另一种类型不停调用pop()
public class MyStack {            
	private List list = new ArrayList();
	synchronized public void push() {
		try {
			/* 不能用if，必须用while。
			进入后就wait处停止了，若有notify通知了所有wait状态线程都被唤醒，有可能导致异常
			*/
			while(list.size() == 1) { this.wait();}   // 把自身当锁，此处死循环用于等待消费者消费资源
			list.add("anyString=" + Math.random());
			this.notify();   //  notify调用之后，要等到synchronized块执行完才会释放锁
			System.out.println("push=" + list.size());
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	synchronized public String pop() {
		String returnValue = "";
		try {
			/* 不能用if，必须用while。
			进入后就wait处停止了，若有notify通知了所有wait状态线程都被唤醒，list.remove(0)会导致异常
			*/
			while(list.size() == 0) { this.wait(); }   //  此处死循环用于等待生产者生产资源
			returnValue = "" + list.get(0);
			list.remove(0);
			this.notify();  //  notify调用之后，要等到synchronized块执行完才会释放锁
			System.out.println("pop=" + list.size());
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		return returnValuel
	}
}


Executor的使用，注意每个线程的异常都只能自己处理，不能传回到启动该线程的线程（例如main所在线程），要传回来要使用新接口
import java.util.concurrent.*;
public class CachedThreadPool {
	public static void main(String[] args) {
		/* ExecutorService是具有服务生命周期的Executor ，注意此处是静态类创建的，类似工厂方法*/
		/* newCachedThreadPool 一般会创建与所需数量相同的线程 */
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0; i<5; i++) 
			exec.execute(new MyTask());
		/* shutdown() 防止新任务被提交给Executor */
		exec.shutdown();
		
		/* 使用有限的线程来执行所提交的任务，此处限定为5个线程，一次性完成线程分配动作，节省了总的创建线程的高昂开销 */
		ExecutorService execB = Executors.newFixedThreadPool(5);
		for(int i=0; i<5; i++) 
			execB.execute(new MyTask());
		execB.shutdown();
		
		/* 起单线程，顺序执行所有的Task，内部自己维护一个FIFO的任务队列，低要求下的文件系统处理的好选择 */
		ExecutorService execC = Executors.newSingleThreadExecutor();
		for(int i=0; i<5; i++) 
			execC.execute(new MyTask());
		execC.shutdown();
	}
}

从任务中产生返回值， 使用callable 接口代替 runnable 接口
import java.util.concurrent.*;
import java.util.*;
/* 接口中的泛型表明返回值的类型 */
class TaskWithResult implements Callable<String> {
	private int id;
	public TaskWithResult(int id) {
		this.id = id;
	}
	/* 线程调用的 call() 方法是有返回类型的，与接口里指明的泛型一致 */
	public String call() {
		try	{
			Thread.sleep(1000*id);  //  由于 get() 的阻塞效果，虽然所有线程一起启动，但是会产生隔一秒依次显示的效果
	/* 第一个线程休眠时间最久，尽管每个线程休眠时间不一，第一个线程的get() 依然会阻塞其他线程执行完毕后的结果打印
		//  Thread.sleep(1000*(10 - id));  
	*/
		// TimeUnit.MILLISECONDS.sleep(1000);   //  Java 1.5/6 的新风格
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		return "result of TaskWithResult " + id;
	}
}
public class CallableDemo {
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		/* Future<T> 用来存储具体的返回结果  */
		ArrayList<Future<String>> results = new ArrayList<Future<String>>();
		
		for(int i=0; i<10; i++) {
			/* submit() 提交并执行一个任务给线程，返回结果会产生Future对象 */
			results.add(exec.submit(new TaskWithResult(i)));
		}
		for(Future<String> fs : results)
			try{
				/* 通过get() 来获取结果， 可以调用isDone() 来检查Future是否完成。直接调用 get()会阻塞，直到结果准备就绪 */
				System.out.println(fs.get());
			}catch(InterruptedException e) {
				e.printStackTrace();
			}catch(ExecutionException e) {
				e.printStackTrace();
			}finally{
				exec.shutdown();      //  关闭线程池，不让添加新任务
			}
	}
}

异构任务并行化实现 及 同构任务的并行分解和结果处理
// 此处实现一个页面渲染程序，在渲染文字和渲染图片之间实现异构并行，对每张图片，实行并行渲染及加载
//  ExecutorCompletionService 比一般的ExecutorService相当于实现了一个缓存结果的BlockingQueue，用于缓存所有任务线程的返回结果
//  感觉ExecutorCompletionService 的具体实现是个装饰器，对返回结果进一步进行了封装处理
public class Renderer {
	private final ExecutorService executor;
	Renderer(ExecutorService executor) { this.executor = executor; }      //  需要外部给传入一个线程池
	void renderPage(charSequence source) {
		List<ImageInfo> info = scanForImageInfo(source);
		//  ExecutorCompletionService在构造函数中创建一个Blockingqueue来保存计算完成的结果
		CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executor);
		for(final ImageInfo imageInfo : info)
			completionService.submit(new Callable<ImageData>() {     // 典型的
											public ImageData call() { return imageInfo.downloadImage(); }
										});
		renderText(source);     //  渲染文字，此处是异构并发
		try{
			for(int t=0, n=info.size(); t<n; t++) {
				Future<ImageData> f = completionService.take(); // 在得出结果之前阻塞，有结果就取出来跑循环，没结果就阻塞等待
				ImageData imageData = f.get();    //  将得到的结果取出
				renderImage(imageData);    // 渲染单张图片，此处是同构并发
			}
		}catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}catch(ExecutionException e) {
			throw launderThrowable(e.getCause());
		}
	}
										
}

构建高效可伸缩的结果缓存
public class Memoizer<A, V> implements Computable<A, V> {
	private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();  // 使用并发HashMap
	private final Computable<A, V> c;
	public Memoizer(Computable<A, V> c) { this.c =c; }
	public V compute(final A arg) throws InterruptedException {
		while(true) {
			Future<V> f = cache.get(arg);        //  获取HashMap中缓存的 Future<V> 值，若有则直接返回，若没有则返回 null
			if(f == null) {
				Callable<V> eval = new Callable<V>() {
										public V call() throws InterruptedException { return c.compute(arg); }
										};
				FutureTask<V> ft = new FutureTask<V>(eval);    //  生成一个带返回结果的Task任务，
				// 看ft任务在缓存中是否存在，存在则返回相关Future<V>，不存在返回 null，关键是查看key(即arg参数)
				f = cache.putIfAbsent(arg, ft);     
				// ft任务不存在，跑线程等出结果，此处调用本来就是外部起了线程在跑，所以用run
				if(f == null) { f = ft; ft.run(); }; 
			}
			try{
				return f.get();
			}catch(CancellationException e) {
				cache.remove(arg, f);
			}catch(ExecutionException e) {
				throw launderThrowable(e.getCause());
			}
		}
	}
}


5种同步工具类：阻塞队列(BlockingQueue)、闭锁(latch)、阻塞等待返回值的任务(FutureTask)、信号量(Semaphore)、栅栏(Barrier)

闭锁(latch)的使用，通过等待/开闸的操作，使一个或者多个线程等待一组事件发生。
闭锁状态包括一个计数器，该计数器被初始化为一个正数，表示需要等待的事件数量。
countDown()方法递减计数器，表示有一个事件发生。计数器值非0，await()会一直阻塞到计数器为零，或者线程中断或者线程超时
public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
	final CountDownLatch startGate = new CountDownLatch(1);   //  创建等待计数为1的线程
	final CountDownLatch endGate = new CountDownLatch(nThreads); // 创建等待计数为nThreads的线程
	
	for(int i=0; i<nThreads; i++) {
		Thread t = new Thread() {    //  起n个线程实例，每个线程实例运行一个task任务
			public void run() {
				try {
					startGate.await();          //  等待本主线程开闸
					try {
						task.run();            //   开闸后跑主业务
					}finally {
						endGate.countDown();        //  跑完主业务，减掉任务计数，变相通知主线程任务完成
					}
				}catch(InterruptedException ignored){
					
				}
			}
		};
		t.start();            //  启动线程
	}
	long start = System.nanoTime();         //  主线程开闸前记录时间
	startGate.countDown();                  //  主线程开闸，此时所有等待的任务线程从await()状态往下跑
	endGate.await();         //  等待最后一个任务线程开闸，即所有任务线程执行完毕，计数器到零后，往下运行
	long end = System.nanoTime();          //    记录最后一个任务线程执行完毕的时间
	return end - start;                    //  将所有并行任务线程从启动到结束的运行时间作为结果返回
}

阻塞等待返回值的任务(FutureTask)，通过Callable实现，相当于可生成结果的Runnable。
Future.get的行为取决于任务状态，如果任务已经完成，get会立即返回结果，否则get阻塞直到任务完成，或者抛出异常。结果进行跨线程传递
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
//  强制将未检查的Throwable转换为RuntimeException
public static RuntimeException launderThrowable(Throwable t) {
	if(t instanceof RuntimeException)
		return (RuntimeException) t;
	else if(t instanceof Error) 
		throw (Error) t;
	else
		throw new IllegalStateException("Not unchecked", t);
}
public class Preloader {
	private final Future<ProductInfo> future =  //  创建一个任务，该任务返回ProductInfo对象
			new Future<productInfo>(new Callable<ProductInfo> {
				public ProductInfo call() throws DataLoadException {
					return loadProductInfo();
				}
			});
	private final Thread thread = new Thread(future);   //  启用子线程调用该任务
	public void start()	 { thread.start(); }            //  提供start()方法给外部启动线程
	public ProductInfo get() throws DataLoadExceptioin, InterruptedException {
		try {
			return future.get();        //  阻塞等待任务在子线程中完成并返回结果ProductInfo对象
		}catch(ExecutionException e) {
			Throwable cause = e.getCause();
			if(cause instanceof DataLoadException)
				throw (DataLoadException) cause;
			else
				throw launderThrowable(cause);
		}
	}		
}

信号量：计数信号量用来控制同时访问某个特定资源的操作数量，或者同时执行某个指定操作的数量。也可以用来实现某种资源池，或者对容器施加边界
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
public class BoundedHashSet<T> {  // 实现一个有界的阻塞容器，信号量的计数值会初始化成容器容量的最大值
	private final Set<T> set;
	private final Semaphore sem;       //  声明一个信号量变量
	
	public BoundedHashSet(int bound) {
		this.set = Collections.synchronizedSet(new HashSet<T>());   //  装饰模式，将HashSet使用synchronizedSet装饰，支持并发
		sem = new Semaphore(bound);    //  初始化信号量，有bound个信号量资源
	}
	public boolean add(T o) throws InterruptedException {
		sem.acquire();                //  占用一个信号量资源，如果当前没有信号量资源，则阻塞在此
		boolean wasAdded = false;
		try {
			wasAdded = set.add(o);
			return wasAdded;             //  将添加结果状态返回
		}finally {
			if(!wasAdded)
				sem.release();         //  如果添加元素失败，释放信号量资源
		}
	}
	public boolean remove(Object o) {
		boolean wasRemoved = set.remove(o);
		if(wasRemoved) 
			sem.release();
		return wasRemoved;
	}
}

栅栏：类似于闭锁，阻塞一组线程直到某个事件发生。栅栏与闭锁的关键区别在于，所有线程必须同时到达栅栏位置，才能继续执行。闭锁用于等待事件，而栅栏用于等待其他线程。
public class CellularAutomata {
	private final Board mainBoard;
	private final CyclicBarrier barrier;
	private final Worker[] workers;
	/* 要执行的任务，本任务在线程中执行完毕后，在wait()处等待，直到所有线程执行的任务都到达await()之后，所有线程再往下走 */
	private class Worker implements Runnable {
		private final Board board;
		public Worker(Board board) { this.board = board; }
		public void run() {
			while(!board.hasConverged()) {
				for(int x=0; x<board.getMaxX(); x++)
					for(int y=0; y<board.getMaxY(); y++)
						board.setNewValue(x, y, computeValue(x, y);        //  任务的具体工作，根据业务需要实现
				try {
					barrier.await();          //  线程集结等待处
				}catch(InterruptedException e) {
					return;
				}catch(BrokenBarrierException e) {
					return;
				}
			}
		}
	}
	public CellularAutomata(Board board) {
		this.mainBoard = board;
		int count = Runtime.getRuntime().availableProcessors();  //  获取当前Cpu的个数，作为线程数依据(子问题个数)
		/****  生成栅栏，第一个参数是集合的线程数，这样await()才知道是否可以开栏，
		                 第二个参数是栅栏开启后可选执行的子任务线程，用于进行一些收尾工作 ***/
		this.barrier = new CyclicBarrier(count, new Runnable() { public void run() { mainBoard.commitNewValues(); } });
		this.workers = new Worker[count];
		for(int i=0; i<count; i++)
			workers[i] = new Worker(mainBoard.getSubBoard(count, i));  //  初始化子任务集合
	}
	public void start() {
		for(int i=0; i<workers.length; i++)
			new Thread(workers[i].start();       //  启动子任务
		mainBoard.waitForConvergence();
	}
}



/*  多线程情况下构造函数中使用匿名类导致外部对象逸出的情况，因为非静态内部对象含有外部对象的this引用，
    此处source中的代码在registerListener()之后可以马上使用EventListener对象隐含发布的ThisEscape对象(通过ThisEscape.this)，
	而ThisEscape对象此时的构造过程都还没有跑完  */
public class ThisEscape {
	public ThisEscape(EventSource source) {
		source.registerListener(
				new EventListener() {
					public void onEvent(Event e) {
						doSomething(e);
					}
				});
	}
}
解决方法：使用私有构造函数配合工厂方法返回类实例

public class SafeListener {
	private final EventListener listener;
	/* 私有构造函数，对外封闭了构造过程 */
	private SafeListener() {
		listener = new EventListener() {
			public void onEvent(Event e) {
				doSomething(e);
			}
		};
	}
	
	public static SafeListener newInstance(EventSource source) {
		SafeListener safe = new SafeListener();
		/* 运行到此处时，SafeListener的构造函数肯定运行完毕，然后通过registerListener()函数对外发布 */
		source.registerListener(safe.listener);
		// 返回SafeListener对象的的引用给外部
		return safe;
	}
}
