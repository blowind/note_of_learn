
                         /******************               Pointcut举例                  ******************/

/*
pointcut 中的join points主要包括如下几种: 
	1、方法调用：                           call(void Point.setX(int))
	2、方法执行：                           execution(void Point.setX(int))
	3、对象初始化：
	4、构造函数执行：                       call(*.new(int, int))
	5、域变量引用：
	6、异常处理：                           handler(ArrayOutOfBoundsException)
	7、当前运行的对象属于SomeType类         this(SomeType)
	8、被调用的对象属于SomeType类           target(SomeType)
	9、执行的代码位于MyClass类内部          within(MyClass)
	10、位于Test类main方法调用的控制流中    cflow(call(void Test.main()))
*/

call(void Point.setX(int)   //  定义对Point类的一参数setX函数的调用为切点
call(void Point.setx(int)) || call(void Line.setP1(Point)  //  使用 && , || , ! 组合切点，各切点可以属于不同的类

/*  切点的模糊匹配定义  */	
call(void Figure.make*(..))         //  定义一个切点，Figure类/接口中以make开头的任意参数的方法，其返回值是void
call(public * Figure.*(..))         //  定义一个切点，指向Figure类/接口的所有公共方法
execution(public !static * *(..))   //  定义一个切点，指向公有非静态方法的执行
call(void m()) && withincode(void m())   //  定义一个在m()函数递归调用时的切点
execution(void m()) && withincode(void m())   //  定义m()函数的执行  等同于 execution(void m())
cflow(move())                       //  使用cflow定义切点，指向所有move切点开始调用和返回之间的点

// 无论修饰符、返回类型、类和方法名是什么，并且参数包含一个单值、后接任意数量任意类型的参数，都会捕获方法上的连接点
call(* *.*(*, ..))   
call(* *.*(..))               //   无论修饰符、返回类型、类、方法名以及方法的参数个数与类型是什么，都会捕获方法上的连接点
call(* *(..))                 //   同上，另一种写法
call(* mypackage..*.*(..))    //   捕获mypackage包和子包内的任何方法上的连接点
call(* MyClass+.*(..))        //   捕获MyClass和任何子类中的任何方法上的连接点

// 方法调用举例：此处指向Point类的setX和setY方法调用
pointcut setter(): target(Point) && (call(void setX(int)) || call(void setY(int)));

// 异常处理举例：此处指向MyClass内部IOException异常处理部分，注意是异常处理的地方不是异常抛出的地方
pointcut ioHandler(): within(MyClass) && handler(IOException);

// 所有调用Point对象上返回int类型的无参方法的地方
pointcut a(): target(Point) && call(int *());

// Line和Point类内部任意方法调用
pointcut b(): call(* *(..)) && (within(Line) || within(Point));

// 不限制调用点，int型一参数构造函数执行时
pointcut c(): within(*) && execution(*.new(int));

// 非Point对象的返回值为int的函数执行时
pointcut d(): !this(Point) && call(int *(..));

// 公有非静态函数执行时
pointcut e(): execution(public !static * *(..));

/* 自定义一个切点，可以使用move()表示一个切点 */
pointcut move() :
	call(void FigureElement.setXY(int, int) ||   //  此处是接口函数
	call(void Point.setX(int))              ||
	call(void Line.setP1(int));
	
// 一参的setter，参数p与右侧p变量名一致
pointcut setter(Point p): target(p) && (call(void setX(int)) || call(void setY(int)));
// 一参的testEquality，参数p与右侧args中的p一致，表明是equals的入参类型为Point的p
pointcut testEquality(Point p): target(Point) && args(p) && call(boolean equals(Object));
// 同时使用两个Point时，需要在右侧参数名上明确指定
pointcut testEquality(Point p1, Point p2): target(p1) && args(p2) && call(boolean equals(Object));
// 另一种场景，当二参数是int类型时
pointcut setter(Point p, int newval): target(p) && args(newval) && (call(void setX(int)) || call(void setY(int)));
	
	
                       /******************               Advice举例                  ******************/
					   
// before原语定义move()调用前的建言
before(): move() {
	Sysout.out.println("about to move");
}

// 带参数的匿名advice
before(Point p, int x): target(p) && args(x) && call(void setX(int)) {
  if (!p.assertX(x)) return;
}

// 带参数的匿名advice，此处处理包括正常返回和异常返回的情况
after(Point p, int x): target(p) && args(x) && call(void setX(int)) {
  if (!p.assertX(x)) throw new PostConditionViolation();
}
// 处理正常返回的情况，注意此处参数的位置
after(Point p) returning(int x): target(p) && call(int getX()) {
  System.out.println("Returning int value " + x + " for p = " + p);
}
// 处理异常返回的情况，此处处理调用时抛出Exception类型异常的情况，建言处理完毕后，会抛出处理过的异常给正常代码流程继续处理
after() throwing(Exception e): target(Point) && call(void setX(int)) {
  System.out.println(e);
}

// after原语定义move()调用后的建言，包括正常return和异常exception返回，此处使用正常return
after() returning: move() {
    System.out.println("just successfully moved");
}

// 建言中使用参数，参数有切点传入，可以分别使用this, target, args三个原语传入，此处使用了两种
after(FigureElement fe, int x, int y) returning:
        call(void FigureElement.setXY(int, int))
        && target(fe)
        && args(x, y) {
    System.out.println(fe + " moved to (" + x + ", " + y + ")");
}
//  上述建言的另一种写法，将Pointcut先提炼简化后再使用
pointcut setXY(FigureElement fe, int x, int y):
    call(void FigureElement.setXY(int, int))
    && target(fe)
    && args(x, y);
after(FigureElement fe, int x, int y) returning: setXY(fe, x, y) {
    System.out.println(fe + " moved to (" + x + ", " + y + ").");
}

// 使用 thisJoinPoint 特殊变量，该变量收集了切点相关的各方面信息
//  thisJoinPoint.getArgs()   获取切点的参数
//  thisJoinPoint.getStaticPart()  切点静态信息，例如行号，静态签名，更快捷的方式是 thisJoinPointStaticPart 变量
//  thisJoinPointStaticPart == thisJoinPoint.getStaticPart()
//  thisJoinPoint.getKind() == thisJoinPointStaticPart.getKind()
//  thisJoinPoint.getSignature() == thisJoinPointStaticPart.getSignature()
//  thisJoinPoint.getSourceLocation() == thisJoinPointStaticPart.getSourceLocation()

aspect SimpleTracing {
    pointcut tracedCall():
        call(void FigureElement.draw(GraphicsContext));

    before(): tracedCall() {
        System.out.println("Entering: " + thisJoinPoint);  // 输出： Entering: call(void FigureElement.draw(GraphicsContext))
    }
}
// thisJoinPointStaticPart 包含整个切点闭包的静态信息
before() : execution (* *(..)) {
	System.err.println(thisEnclosingJoinPointStaticPart.getSourceLocation())
}

// 使用proceed特殊函数，此处截取替换切点，然后通过proceed特殊函数继续执行切点的后续动作
void around(Point p, int x): target(p) && args(x) && call(void setX(int)) {
    if (p.assertX(x)) 
		proceed(p, x);
    p.releaseResources();
}

                    /******************               Inter-type declarations内部变量声明                  ******************/
// 在aspect内部声明Server类新增一个域变量disabled并且初始化为false，
// disabled变量仅在本aspect内部可见，对Server类和其他切面都不可见
private boolean Server.disabled = false;

// 对外可见的声明，若Point类有同名变量，则编译错误
public int Point.x = 0;

// 声明Point类的getX()方法，对外可见，因此如果Point有同名方法，会产生编译错误
public int Point.getX() { return this.x; }

//  声明Point类实现Comparable接口，若Point类本身定义了和Comparable接口同名的方法，则报错
declare parents: Point implements Comparable;

//  声明类继承
declare parents: Point extends GeometricObject;

//  原则上切面内只能声明一个类型作为目标内部类，但是如下方式可以规避该限制，即通过声明一个标记内部接口来实现多个内共用切面
aspect A {
	private interface HasName {}
	declare parents: (Point || Line || Square) implements HasName;

	private String HasName.name;
	public  String HasName.getName()  { return name; }
}
					
// 给已有类的新增内部变量，此处给Point新增observers变量，该变量用于存储监控Point变化的Screen对象
// 这种监控关系完全不需要在Point类和Screen类里面编写相关处理代码，所有具体关联处理全部放入本Aspect定义
aspect PointObserving {
    private List<Screen> Point.observers = new List<Screen>();  // private，所以仅对PointObserving切面可见

    public static void addObserver(Point p, Screen s) {
        p.observers.add(s);
    }
    public static void removeObserver(Point p, Screen s) {
        p.observers.remove(s);
    }
	
	pointcut changes(Point p): target(p) && call(void Point.set*(int));  // 定义切点，所有Point对象的一参数set方法

    after(Point p): changes(p) {          // 定义建言，即在set方法返回时，调用切面的updateObserver方法
        Iterator iter = p.observers.iterator();
        while ( iter.hasNext() ) {
            updateObserver(p, (Screen)iter.next());
        }
    }
	
	static void updateObserver(Point p, Screen s) {
        s.display(p);
    }
}

                         /******************               Aspects定义                  ******************/

						 // aspect切面的实例化都由AspectJ处理，默认是单例的，因此可以有局部变量和非静态函数的使用
aspect Logging {
    OutputStream logStream = System.err;

    before(): move() {
        logStream.println("about to move");
    }
}

// 切面的性能调研用法
aspect SetsInRotateCounting {
    int rotateCount = 0;
    int setCount = 0;

	// 计算Line对象中rotate方法被调用的次数
    before(): call(void Line.rotate(double)) {
        rotateCount++;
    }

	// 计算Line.rotate方法内部Point对象的set*方法被调用的次数
    before(): call(void Point.set*(int))
              && cflow(call(void Line.rotate(double))) {
        setCount++;
    }
}

//  切面的输入检查用法(同理可以进行输出检查)，可以精确控制不同类的相关方法
aspect PointBoundsChecking {

    pointcut setX(int x):
        (call(void FigureElement.setXY(int, int)) && args(x, *))
        || (call(void Point.setX(int)) && args(x));

    pointcut setY(int y):
        (call(void FigureElement.setXY(int, int)) && args(*, y))
        || (call(void Point.setY(int)) && args(y));

    before(int x): setX(x) {
        if ( x < MIN_X || x > MAX_X )
            throw new IllegalArgumentException("x is out of bounds.");
    }

    before(int y): setY(y) {
        if ( y < MIN_Y || y > MAX_Y )
            throw new IllegalArgumentException("y is out of bounds.");
    }
}

//  切面的异常检测用法，用于识别不合理的函数调用
aspect RegistrationProtection {

    pointcut register(): call(void Registry.register(FigureElement));  // FigureElement对象被注册的调用点
	// 使用withincode原语指向FigureElement类make工厂方法内部的所有地方为切点
    pointcut canRegister(): withincode(static * FigureElement.make*(..));  // 

    before(): register() && !canRegister() {
        throw new IllegalAccessException("Illegal call " + thisJoinPoint);
    }
}
// 同上，不是通过运行时抛出异常，而是通过declare原语指明在编译期抛出error，这种方式在static方法调用的切点里可用
aspect RegistrationProtection {

    pointcut register(): call(void Registry.register(FigureElement));
    pointcut canRegister(): withincode(static * FigureElement.make*(..));

    declare error: register() && !canRegister(): "Illegal call"
}

//  捕获com.bigboxco包下面所有public方法抛给调用者的所有Error，并记录日志
aspect PublicErrorLogging {
    Log log = new Log();

    pointcut publicMethodCall():
        call(public * com.bigboxco.*.*(..)); // com.bigboxco包下面的所有public方法
	/*
    after() throwing (Error e): publicMethodCall() { // 在切点抛出Error之后
        log.write(e);
    } */
	// 前述after中，如果本地public方法调用本地public方法，则会捕获并记录两次日志
	after() throwing (Error e):
        publicMethodCall() && !cflow(publicMethodCall()) {  //  排除本地方法的内部互相调用时多次记录日志的情况
		log.write(e);
	}
}

aspect FaultHandler {
	private boolean Server.disabled = false;

	private void reportFault() {
		System.out.println("Failure! Please fix it.");
	}

	public static void fixServer(Server s) {
		s.disabled = false;
	}

	/* 定义切点，此处指定操作目标类为Server，切点为Server类所有public方法  */
	pointcut services(Server s): target(s) && call(public * *(..));

	before(Server s): services(s) {
		if (s.disabled) throw new DisabledException();
	}

	/*  在services切点抛出FaultException异常后调用 */
	after(Server s) throwing (FaultException e): services(s) {
		s.disabled = true;
		reportFault();
	}
}