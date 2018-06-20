
                         /******************               Pointcut����                  ******************/

/*
pointcut �е�join points��Ҫ�������¼���: 
	1���������ã�                           call(void Point.setX(int))
	2������ִ�У�                           execution(void Point.setX(int))
	3�������ʼ����
	4�����캯��ִ�У�                       call(*.new(int, int))
	5����������ã�
	6���쳣����                           handler(ArrayOutOfBoundsException)
	7����ǰ���еĶ�������SomeType��         this(SomeType)
	8�������õĶ�������SomeType��           target(SomeType)
	9��ִ�еĴ���λ��MyClass���ڲ�          within(MyClass)
	10��λ��Test��main�������õĿ�������    cflow(call(void Test.main()))
*/

call(void Point.setX(int)   //  �����Point���һ����setX�����ĵ���Ϊ�е�
call(void Point.setx(int)) || call(void Line.setP1(Point)  //  ʹ�� && , || , ! ����е㣬���е�������ڲ�ͬ����

/*  �е��ģ��ƥ�䶨��  */	
call(void Figure.make*(..))         //  ����һ���е㣬Figure��/�ӿ�����make��ͷ����������ķ������䷵��ֵ��void
call(public * Figure.*(..))         //  ����һ���е㣬ָ��Figure��/�ӿڵ����й�������
cflow(move())                       //  ʹ��cflow�����е㣬ָ������move�е㿪ʼ���úͷ���֮��ĵ�

// �������þ������˴�ָ��Point���setX��setY��������
pointcut setter(): target(Point) && (call(void setX(int)) || call(void setY(int)));

// �쳣����������˴�ָ��MyClass�ڲ�IOException�쳣�����֣�ע�����쳣����ĵط������쳣�׳��ĵط�
pointcut ioHandler(): within(MyClass) && handler(IOException);

// ���е���Point�����Ϸ���int���͵��޲η����ĵط�
pointcut a(): target(Point) && call(int *());

// Line��Point���ڲ����ⷽ������
pointcut b(): call(* *(..)) && (within(Line) || within(Point));

// �����Ƶ��õ㣬int��һ�������캯��ִ��ʱ
pointcut c(): within(*) && execution(*.new(int));

// ��Point����ķ���ֵΪint�ĺ���ִ��ʱ
pointcut d(): !this(Point) && call(int *(..));

// ���зǾ�̬����ִ��ʱ
pointcut e(): execution(public !static * *(..));

/* �Զ���һ���е㣬����ʹ��move()��ʾһ���е� */
pointcut move() :
	call(void FigureElement.setXY(int, int) ||   //  �˴��ǽӿں���
	call(void Point.setX(int))              ||
	call(void Line.setP1(int));
	
	
                       /******************               Advice����                  ******************/
					   
// beforeԭ�ﶨ��move()����ǰ�Ľ���
before(): move() {
	Sysout.out.println("about to move");
}
// afterԭ�ﶨ��move()���ú�Ľ��ԣ���������return���쳣exception���أ��˴�ʹ������return
after() returning: move() {
    System.out.println("just successfully moved");
}

// ������ʹ�ò������������е㴫�룬���Էֱ�ʹ��this, target, args����ԭ�ﴫ�룬�˴�ʹ��������
after(FigureElement fe, int x, int y) returning:
        call(void FigureElement.setXY(int, int))
        && target(fe)
        && args(x, y) {
    System.out.println(fe + " moved to (" + x + ", " + y + ")");
}

//  �������Ե���һ��д������Pointcut�������򻯺���ʹ��
pointcut setXY(FigureElement fe, int x, int y):
    call(void FigureElement.setXY(int, int))
    && target(fe)
    && args(x, y);
after(FigureElement fe, int x, int y) returning: setXY(fe, x, y) {
    System.out.println(fe + " moved to (" + x + ", " + y + ").");
}

// ʹ��thisJoinPoint�������
aspect SimpleTracing {
    pointcut tracedCall():
        call(void FigureElement.draw(GraphicsContext));

    before(): tracedCall() {
        System.out.println("Entering: " + thisJoinPoint);  // ����� Entering: call(void FigureElement.draw(GraphicsContext))
    }
}

                    /******************               Inter-type declarations�ڲ���������                  ******************/
// ��������������ڲ��������˴���Point����observers�������ñ������ڴ洢���Point�仯��Screen����
// ���ּ�ع�ϵ��ȫ����Ҫ��Point���Screen�������д��ش�����룬���о����������ȫ�����뱾Aspect����
aspect PointObserving {
    private List<Screen> Point.observers = new List<Screen>();  // private�����Խ���PointObserving����ɼ�

    public static void addObserver(Point p, Screen s) {
        p.observers.add(s);
    }
    public static void removeObserver(Point p, Screen s) {
        p.observers.remove(s);
    }
	
	pointcut changes(Point p): target(p) && call(void Point.set*(int));  // �����е㣬����Point�����һ����set����

    after(Point p): changes(p) {          // ���彨�ԣ�����set��������ʱ�����������updateObserver����
        Iterator iter = p.observers.iterator();
        while ( iter.hasNext() ) {
            updateObserver(p, (Screen)iter.next());
        }
    }
	
	static void updateObserver(Point p, Screen s) {
        s.display(p);
    }
}

                        /******************               Aspects����                  ******************/
// aspect�����ʵ��������AspectJ����Ĭ���ǵ����ģ���˿����оֲ������ͷǾ�̬������ʹ��
aspect Logging {
    OutputStream logStream = System.err;

    before(): move() {
        logStream.println("about to move");
    }
}

// ��������ܵ����÷�
aspect SetsInRotateCounting {
    int rotateCount = 0;
    int setCount = 0;

	// ����Line������rotate���������õĴ���
    before(): call(void Line.rotate(double)) {
        rotateCount++;
    }

	// ����Line.rotate�����ڲ�Point�����set*���������õĴ���
    before(): call(void Point.set*(int))
              && cflow(call(void Line.rotate(double))) {
        setCount++;
    }
}

//  ������������÷�(ͬ����Խ���������)�����Ծ�ȷ���Ʋ�ͬ�����ط���
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

//  ������쳣����÷�������ʶ�𲻺���ĺ�������
aspect RegistrationProtection {

    pointcut register(): call(void Registry.register(FigureElement));  // FigureElement����ע��ĵ��õ�
	// ʹ��withincodeԭ��ָ��FigureElement��make���������ڲ������еط�Ϊ�е�
    pointcut canRegister(): withincode(static * FigureElement.make*(..));  // 

    before(): register() && !canRegister() {
        throw new IllegalAccessException("Illegal call " + thisJoinPoint);
    }
}
// ͬ�ϣ�����ͨ������ʱ�׳��쳣������ͨ��declareԭ��ָ���ڱ������׳�error�����ַ�ʽ��static�������õ��е������
aspect RegistrationProtection {

    pointcut register(): call(void Registry.register(FigureElement));
    pointcut canRegister(): withincode(static * FigureElement.make*(..));

    declare error: register() && !canRegister(): "Illegal call"
}

//  ����com.bigboxco����������public�����׸������ߵ�����Error������¼��־
aspect PublicErrorLogging {
    Log log = new Log();

    pointcut publicMethodCall():
        call(public * com.bigboxco.*.*(..)); // com.bigboxco�����������public����
	/*
    after() throwing (Error e): publicMethodCall() { // ���е��׳�Error֮��
        log.write(e);
    } */
	// ǰ��after�У��������public�������ñ���public��������Ჶ�񲢼�¼������־
	after() throwing (Error e):
        publicMethodCall() && !cflow(publicMethodCall()) {  //  �ų����ط������ڲ��������ʱ��μ�¼��־�����
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

	/* �����е㣬�˴�ָ������Ŀ����ΪServer���е�ΪServer������public����  */
	pointcut services(Server s): target(s) && call(public * *(..));

	before(Server s): services(s) {
		if (s.disabled) throw new DisabledException();
	}

	/*  ��services�е��׳�FaultException�쳣����� */
	after(Server s) throwing (FaultException e): services(s) {
		s.disabled = true;
		reportFault();
	}
}