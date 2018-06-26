package com.zxf.aspectj;

public aspect TxAspect {
//	void around(): call(void Hello.sayHello()) {
//		System.out.println("开始切面advice...");
//		proceed();
//		System.out.println("结束切面advice...");
//	}
//
//	pointcut callPointCut(): call(* Hello.sayHello(..));
//
//	before(): callPointCut() {
//		System.out.println("before advice");
//		System.out.println("Signature: " + thisJoinPoint.getSignature());
//		System.out.println("Source Line: " + thisJoinPoint.getSourceLocation());
//	}

	/*******         切点相关示例           ******/

	// 捕获方法调用上传递的参数值
	pointcut callArgs(int a, float b): call(* Service1.test(int, float)) && args(a, b);
	before(int a, float b): callArgs(a, b) {
		System.out.println("================分割线================");
		System.out.println("【args】Before Advice");
		System.out.println("【args】Signature: " + thisJoinPoint.getSignature());
		System.out.println("【args】Source Line: " + thisJoinPoint.getSourceLocation());
		System.out.println("第一个参数是：" + a);
		System.out.println("第二个参数时：" + b);
	}

	//  捕获方法的执行
	pointcut executionPointcut(): execution(* Service1.test(int, float));
	before(): executionPointcut(){
		System.out.println("================分割线================");
		System.out.println("【exec】ExecutionPointCut Before Advice");
		System.out.println("【exec】Signature: " + thisJoinPoint.getSignature());
		System.out.println("【exec】Source Line: " + thisJoinPoint.getSourceLocation());
	}

	//  捕获方法调用的目标对象
	pointcut callTarget(Service1 s): call(* Service1.test(int, float)) && target(s);
	before(Service1 s): callTarget(s) {
		System.out.println("================分割线================");
		System.out.println("【target】Before Advice Target");
		System.out.println("【target】Signature Target: " + thisJoinPoint.getSignature());
		System.out.println("【target】Source Line Target: " + thisJoinPoint.getSourceLocation());
		System.out.println("【target】Service: " + s);
	}

	//  在执行方法时捕获this引用的值，此处两个s获取的对象是一样的
	pointcut executionAndThis(Service1 s):  execution(void Service1.test(..)) && this(s);
	pointcut executionAndTarget(Service1 s):execution(void Service1.test(..)) && target(s);
	pointcut callAndThis(Test1 t):          call(void Service1.test(..)) && this(t);
	pointcut callAndTarget(Service1 s):     call(void Service1.test(..)) && target(s);

	before(Service1 s): executionAndThis(s){
		System.out.println("================分割线================");
		System.out.println("【eAndThis】Signature: " + thisJoinPoint.getSignature());
		System.out.println("【eAndThis】Source Line: " + thisJoinPoint.getSourceLocation());
		System.out.println("【eAndThis】this Object:" + s);
	}

	before(Service1 s): executionAndTarget(s){
		System.out.println("================分割线================");
		System.out.println("【eAndTarget】Signature: " + thisJoinPoint.getSignature());
		System.out.println("【eAndTarget】Source Line: " + thisJoinPoint.getSourceLocation());
		System.out.println("【eAndTarget】target Object:" + s);
	}

	before(Test1 t): callAndThis(t){
		System.out.println("================分割线================");
		System.out.println("【cAndThis】Signature: " + thisJoinPoint.getSignature());
		System.out.println("【cAndThis】Source Line: " + thisJoinPoint.getSourceLocation());
		System.out.println("【cAndThis】this Object:" + t);
	}

	before(Service1 s): callAndTarget(s){
		System.out.println("================分割线================");
		System.out.println("【cAndTarget】Signature: " + thisJoinPoint.getSignature());
		System.out.println("【cAndTarget】Source Line: " + thisJoinPoint.getSourceLocation());
		System.out.println("【cAndTarget】target Object:" + s);
	}



	/*******         异常捕获相关示例           ******/

	//	使用通配符匹配所有Exception异常及其子类，捕获异常对象，通过this捕获处理异常的对象
	pointcut handlerPointcut(Exception e, Service2 s): handler(Exception+) && args(e) && this(s);

	before(Exception e, Service2 s): handlerPointcut(e, s) {
		System.out.println("================分割线================");
		System.out.println("MyException 切面处理...");
		System.out.println("【Exception】Signature: " + thisJoinPoint.getSignature());
		System.out.println("【Exception】Source Line: " + thisJoinPoint.getSourceLocation());
		System.out.println("【Exception】this Object:" + s);
		e.printStackTrace();
	}

	/*******         类和对象构造连接点捕获相关示例           ******/

	// 外层调用点，最先捕获
	pointcut constructorCall(): call(SuperService3+.new(..));
	// 类构造函数执行代码，第五优先级，父类先执行，子类后执行，发生在构造方法开始执行的时候
	pointcut constructorExecution():execution(SuperService3+.new(..));
	// 类初始化的代码开端，第四优先级，父类先执行，子类后执行，发生在构造方法执行之前
	pointcut initializationPointcut(): initialization(SuperService3+.new(..));
	// 类初始化之前的代码，第三优先捕获，其中子类（new指定类型）先执行，父类后执行，发生在进入构造方法之后，以及调用任何超类构造方法之前
	pointcut preInitializationPointcut(): preinitialization(SuperService3+.new(..));
	// 静态代码块初始化部分，第二优先捕获，其中父类的先执行，子类的后执行，发生在初始化切入点通知之前被执行
	pointcut staticInitializationPointcut(): staticinitialization(SuperService3+);

	before(): constructorCall(){
		System.out.println("================分割线================");
		System.out.println("切点调用点，调用Service3类构造函数之前...");
		System.out.println("Signature: " + thisJoinPoint.getSignature());
		System.out.println("Source Line: " + thisJoinPoint.getSourceLocation());
		System.out.println("***************结束分割线**************");
	}

	before(): constructorExecution() {
		System.out.println("================分割线================");
		System.out.println("构造函数内部，执行" + thisJoinPoint.getThis().getClass() + "类的构造函数之前...");
		System.out.println("Signature: " + thisJoinPoint.getSignature());
		System.out.println("Source Line: " + thisJoinPoint.getSourceLocation());
		System.out.println("***************结束分割线**************");
	}

	before(): initializationPointcut() {
		System.out.println("================分割线================");
		System.out.println("initializationPointcut在这里...");
		System.out.println("Signature: " + thisJoinPoint.getSignature());
		System.out.println("Source Line: " + thisJoinPoint.getSourceLocation());
		System.out.println("***************结束分割线**************");
	}

	before(): preInitializationPointcut() {
		System.out.println("================分割线================");
		System.out.println("preInitializationPointcut在这里...");
		System.out.println("Signature: " + thisJoinPoint.getSignature());
		System.out.println("Source Line: " + thisJoinPoint.getSourceLocation());
		System.out.println("***************结束分割线**************");
	}

	// 此处捕获结束点
	after(): staticInitializationPointcut() {
		System.out.println("================分割线================");
		System.out.println("staticInitializationPointcut在这里...");
		System.out.println("Signature: " + thisJoinPoint.getSignature());
		System.out.println("Source Line: " + thisJoinPoint.getSourceLocation());
		System.out.println("***************结束分割线**************");
	}

	/*******         捕获属性上的连接点           ******/

//	pointcut getNamePointcut(): get(String Service4.*name);
//
//	before():getNamePointcut(){
//		System.out.println("================分割线================");
//		System.out.println("Signature: " + thisJoinPoint.getSignature());
//		System.out.println("Source Line: " + thisJoinPoint.getSourceLocation());
//		System.out.println("***************结束分割线**************");
//	}
//
//	after() returning(String value):getNamePointcut(){
//		System.out.println("================get分割线================");
//		System.out.println("Signature: " + thisJoinPoint.getSignature());
//		System.out.println("Source Line: " + thisJoinPoint.getSourceLocation());
//		System.out.println("访问的属性值是：" + value);
//		System.out.println("***************结束get分割线**************");
//	}
//
//	pointcut setNamePointcut(String v): set(String Service4.*name) && args(v);
//
//	before(String v): setNamePointcut(v){
//		System.out.println("================set分割线================");
//		System.out.println("Signature: " + thisJoinPoint.getSignature());
//		System.out.println("Source Line: " + thisJoinPoint.getSourceLocation());
//		System.out.println("Value: " + v);
//		System.out.println("***************结束set分割线**************");
//	}

	pointcut withinService(): within(Service5);

	before(): withinService(){
		System.out.println("================within分割线================");
		System.out.println(thisJoinPoint.getKind());
		System.out.println("Signature: " + thisJoinPoint.getSignature());
		System.out.println("Source Line: " + thisJoinPoint.getSourceLocation());
	}

	// 要运行此处要屏蔽本此处所有切点定义，因为其他切点与此处重复定义，此外加切点的地方要排除切面定义本身，否则执行时报错
//	pointcut withinService2(): within(com.zxf.aspectj.*) && !within(TxAspect);
//
//	before(): withinService2(){
//		System.out.println("================within分割线================");
//		System.out.println(thisJoinPoint.getKind());
//		System.out.println("Signature: " + thisJoinPoint.getSignature());
//		System.out.println("Source Line: " + thisJoinPoint.getSourceLocation());
//	}

	/* 捕获main方法里面的所有连接点 */
//	pointcut withinCodePointcut(): withincode(* Main.main(*));
//
//	before(): withinCodePointcut(){
//		System.out.println("================withincode分割线================");
//		System.out.println(thisJoinPoint.getKind());
//		System.out.println("Signature: " + thisJoinPoint.getSignature());
//		System.out.println("Source Line: " + thisJoinPoint.getSourceLocation());
//	}


	/**
	 * 通过指定cflow(call(* A.methodA()))来捕获对A.methodA()方法调用控制流程内的所有连接点。
	 * 另外通过!within(CFlowAspect)排除了切面本身的连接点，
	 * 如果不这么做，会得到一个栈溢出的错误结果，这是因为我们的切面织入了methodA方法，那么切面自然也是methodA方法的一部分，
	 * 如果切面自己织入自己，就会无限递归下去，最后导致栈溢出
	 **/
	pointcut cflowPointcut(): cflow(call(public static void Service6A.methodA())) && !within(TxAspect);
	/*  cflowbelow与cflow的用法是一样的，唯一的区别是cflowbelow不包括初始连接点，而cflow包括初始连接点  */
//	pointcut cflowPointcut(): cflowbelow(call(public static void Service6A.methodA())) && !within(TxAspect);

	before(): cflowPointcut(){
		System.out.println("================分割线================");
		System.out.println(thisJoinPoint);
	}


}
