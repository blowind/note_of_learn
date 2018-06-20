
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
cflow(move())                       //  使用cflow定义切点，指向所有move切点开始调用和返回之间的点

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
	
	
                       /******************               Advice举例                  ******************/
					   
// before原语定义move()调用前的建言
before(): move() {
	Sysout.out.println("about to move");
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

// 使用thisJoinPoint特殊变量
aspect SimpleTracing {
    pointcut tracedCall():
        call(void FigureElement.draw(GraphicsContext));

    before(): tracedCall() {
        System.out.println("Entering: " + thisJoinPoint);  // 输出： Entering: call(void FigureElement.draw(GraphicsContext))
    }
}

                    /******************               Inter-type declarations内部变量声明                  ******************/
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