
                         /******************               Pointcut����                  ******************/

/*
pointcut �е�join points��Ҫ�������¼���: 
	1���������ã��е��������÷����ĵط��� call(void Point.setX(int))
	2������ִ�У��е����ڲ㷽��ִ�еĵط��� execution(void Point.setX(int))
	3�������ʼ����
	4�����캯��ִ�У�                       call(*.new(int, int))
	5����������ã�
	6���쳣����                           handler(ArrayOutOfBoundsException)
	7����ǰ���еĶ�������MyClass��          this(varName)     ע���������Ǳ�������������:���pointcutǩ����ָ��
	8�������е㷽���Ķ�������MyClass��      target(varName)   ע���������Ǳ�������������:���pointcutǩ����ָ��
	9��ִ�еĴ���λ��MyClass���ڲ�          within(MyClass)
	10��λ��Test��main�������õĿ�������    cflow(call(void Test.main()))
*/

/*
 *  ע�⣬this����ָ����һ������ȡ�������ӵ��λ�ã�
 *  ��call�������е�λ��ҵ������з������õ㣬��ʱthisָ��ҵ�������
 *  ��execution�������е�λ�ڷ���������з�������㣬��ʱthisָ�������������ʱcall - target���е㲶��Ķ�����һ����
 *  ������ʾ���˴�����ʹ��this��target����ͬһ����������
 *   pointcut executionAndThisPointcut(Service service):execution(void Test3.Service.test(..)) && this(service);
 *   pointcut executionAndTargetPointcut(Service service):execution(void Test3.Service.test(..)) && target(service);
 */

call(void Point.setX(int)   //  �����Point���һ����setX�����ĵ���Ϊ�е�
call(void Point.setx(int)) || call(void Line.setP1(Point)  //  ʹ�� && , || , ! ����е㣬���е�������ڲ�ͬ����

/*  �е��ģ��ƥ�䶨��  */	
call(void Figure.make*(..))         //  ����һ���е㣬Figure��/�ӿ�����make��ͷ����������ķ������䷵��ֵ��void
call(public * Figure.*(..))         //  ����һ���е㣬ָ��Figure��/�ӿڵ����й�������
execution(public !static * *(..))   //  ����һ���е㣬ָ���зǾ�̬������ִ��
call(void m()) && withincode(void m())   //  ����һ����m()�����ݹ����ʱ���е�
execution(void m()) && withincode(void m())   //  ����m()������ִ��  ��ͬ�� execution(void m())
cflow(move())                       //  ʹ��cflow�����е㣬ָ������move�е㿪ʼ���úͷ���֮��ĵ�

// �������η����������͡���ͷ�������ʲô�����Ҳ�������һ����ֵ��������������������͵Ĳ��������Ჶ�񷽷��ϵ����ӵ�
call(* *.*(*, ..))   
call(* *.*(..))               //   �������η����������͡��ࡢ�������Լ������Ĳ���������������ʲô�����Ჶ�񷽷��ϵ����ӵ�
call(* *(..))                 //   ͬ�ϣ���һ��д��
call(* mypackage..*.*(..))    //   ����mypackage�����Ӱ��ڵ��κη����ϵ����ӵ�
call(* MyClass+.*(..))        //   ����MyClass���κ������е��κη����ϵ����ӵ�

ע�⣬���������η���Ҫôָ����public֮�࣬Ҫôʡ�Ա�ʾ�������η����ɣ�������*��ʾȫ�����η�

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
	
// һ�ε�setter������p���Ҳ�p������һ�£�ע��ִ���е�ĵ��ö��������Point��ʵ��֪�ģ���ΪҪд�����Ĳ���������
pointcut setter(Point p): target(p) && (call(void setX(int)) || call(void setY(int)));
// һ�ε�testEquality������p���Ҳ�args�е�pһ�£�������equals���������ΪPoint��p
// ע�����в�������ָ��call/execution�����е����������Ĳ���
pointcut testEquality(Point p): target(Point) && args(p) && call(boolean equals(Object));
// ͬʱʹ������Pointʱ����Ҫ���Ҳ����������ȷָ��
pointcut testEquality(Point p1, Point p2): target(p1) && args(p2) && call(boolean equals(Object));
// ��һ�ֳ���������������int����ʱ
pointcut setter(Point p, int newval): target(p) && args(newval) && (call(void setX(int)) || call(void setY(int)));
	
//  �����췽���ĵ���
//  �ڰ�һ����ʵ������һ������ʱ������new�ؼ��ֵ�call(Signature)�����Ჶ�����ӵ�
pointcut [���������](<Ҫ����Ĳ���>): call(<���η�> ����.new(�����б�))

//  �����췽����ִ��
//  ��ִ����Ĺ��캯��ʱ������new�ؼ��ֵ�execution(Signature)�����Ჶ�����ӵ�
pointcut [���������](<Ҫ����Ĳ���>): execution(<���η�> ����.new(�����б�))

//  ��������ʼ��
//  ��1��initialization(Signature)�����������new�ؼ��֣�
//  ��2��initialization(Signature)����㲶�����ӵ㷢�����κγ���ĳ�ʼ��֮���Լ��ӹ��췽������֮ǰ��
//  ��3��Signature����������ض���Ĺ��췽���������Ǽ򵥵ķ�����
//  ��4��initialization(Signature)������ṩ�˱���ʱ��飬���ڼ�鹹�췽���Ƿ����ڱ����ã�
//  ��5������AspectJ�������е����ƣ�����around()֪ͨ����ʱ������ʹ��initialization(Signature)�����
pointcut [���������](<Ҫ����Ĳ���>): initialization(<���η�> ����.new(�����б�))

//  ��������Ԥ�ȳ�ʼ��
//  ��1��preinitialization(Signature)�����������new�ؼ��֣�
//  ��2��preinitialization(Signature)����㲶�����ӵ㷢���ڽ��빹�췽��֮���Լ������κγ��๹�췽��֮ǰ��
//  ��3��Signature���������һ�����췽����
//  ��4��preinitialization(Signature)������ṩ�˱���ʱ��飬���ڼ�鹹�췽���Ƿ����ڱ����ã�
//  ��5������AspectJ�������е����ƣ�����around()֪ͨ����ʱ������ʹ��preinitialization(Signature)�����
pointcut [���������](<Ҫ����Ĳ���>): preinitialization(<���η�> ����.new(�����б�))

//  ������ĳ�ʼ��
//  ��1����staticinitialization(TypePattern)�����ʹ�õĻ�����һЩ���ƣ�
//       ���ȣ�û�и����󴥷���ĳ�ʼ��������û��this���ã����⣬Ҳ���漰Ŀ����󣬹�û��targetĿ�����ã�
//  ��2��TypePattern���԰���ͨ���������ѡ��һϵ�в�ͬ���ࡣ
pointcut [���������](<Ҫ����Ĳ���>): staticinitialization(����)


//  ��������Եķ���   ��ע��˹��������ƻ���ķ�װ��������������Է��ʵ�protected��private���εı�����
//  ��1��get(Signature)�����Ჶ������Ե�ֱ�ӷ��ʣ�������ֻ�Ჶ�������getter�����������ĵ��á�
//  ��2��get(Signature)����㲻�ܲ���Գ����ķ��ʡ��˴�����ָfinalָ��������
//  ��3��Signature����������ض�������ԡ�
//  ��4��Signature���԰���ͨ���������ѡ��ͬ�����ϵ�һϵ�����ӵ㡣
pointcut [���������](��Ҫ����Ĳ���): get(<��ѡ�����η�> �������� ����.������)

//  ��������Ե��޸�
//  ��1��set(Signature)��������޸�����ʱ������
//  ��2��Signature����������ض�������ԡ�
//  ��3��Signature���԰���ͨ���������ѡ��ͬ�����ϵ�һϵ�����ӵ㡣
pointcut [���������](Ҫ��ȡ�Ĳ���): set(<��ѡ�����η�> �������� ����.������)


//  �������ӵ��������
//  within����ָ���������������ڰ��л�������
//  withincode����ͨ������ǩ���������ӵ���������ڷ�����
//  ��1��within(TypePattern)����㲶��ָ�����������е��������ӵ㡣
//  ��2��within(TypePattern)����㼫�ٵ���ʹ�ã���ͨ���������������ʹ�ã����ڼ��ٽ�Ҫ�������ӵ㡣
//  ��3��within(TypePattern)���԰���ͨ���������ѡ��ͬ���ϵ�һϵ�����ӵ㡣
pointcut [���������](��Ҫ��ȡ�Ĳ���): within(����)

//  ʹ��withincode(Signature)��������������ض�ǩ��ƥ��ķ����ڵ��������ӵ�
//  ��1��withincode(Signature)�����ָ�����ض������������ڵ��������ӵ㡣
//  ��2��withincode(Signature)�������ٵ���ʹ�á�һ���������������ʹ�ã����ڼ��ٽ�Ҫ�������ӵ㡣
//  ��3��Signature���԰���ͨ���������ѡ���Խ��ͬ�಻ͬ�����ϵ�һϵ�����ӵ㡣
//  ��4��withincode��within���������withincode����ָ����������within����ָ���ࡣ
pointcut [���������](Ҫ��ȡ�Ĳ���): withincode(<��ѡ�����η�> ����.������(�����б�))


//  ������ڿ������̵����ӵ�
//  cflow��cflowbelow�ṩ��һ�ֲ���һ��pointcut���������������ӵ�Ĺ��ܡ�
//  �����ڳ�������������������������ӵ㣬��Щ���ӵ㶼��ĳһ���ض������ӵ�֮�󣬿��Կ���ʹ��cflow��
//  ������������ǳ���ִ�й����е�ÿһ�д��룬׼ȷ��˵��ÿ�д���������ֽ��롣
//һ�������Ŀ��������������е�ÿһ�д��룬���������������ĵ��ã�����������ò���ж�������ڸ÷����Ŀ�������ֱ���������ء�
//  ��cflow(Pointcut)ָ�������ӵ���������ڣ��κ����������ӵ㶼�ᴥ��cflow(Pointcut)����㣬�����ù�����֪ͨ
// ��1��cflow(Pointcut)����㲶���ڳ�ʼ�ض������ӵ������������������������ӵ㣬�����ʼ���ӵ���ͨ����һ�������ѡ��ġ�
// ��2����������ӵ������ʼ���ӵ㡣
// ��3��cflow(Pointcut)��ʵ�ַ�ʽ����ɴ�����ϵͳ�������ڿ��ܵĵط������ȿ���ʹ��withincode(Signature)������cflow(Pointcut)��
pointcut [���������](��Ҫ����Ĳ���): cflow(��һ��pointcut)

//  cflowbelow��cflow���÷���һ���ģ�Ψһ��������cflowbelow��������ʼ���ӵ㣬��cflow������ʼ���ӵ�


	
                       /******************               Advice����                  ******************/
					   
// beforeԭ�ﶨ��move()����ǰ�Ľ���
before(): move() {
	Sysout.out.println("about to move");
}

// ������������advice
before(Point p, int x): target(p) && args(x) && call(void setX(int)) {
  if (!p.assertX(x)) return;
}

// ������������advice���˴���������������غ��쳣���ص������pָ�����getX������Point����xָ��setX�����
after(Point p, int x): target(p) && args(x) && call(void setX(int)) {
  if (!p.assertX(x)) throw new PostConditionViolation();
}
// �����������ص������ע��˴�������λ�ã�pָ�����getX������Point����xָ��getX�ķ���ֵ
after(Point p) returning(int x): target(p) && call(int getX()) {
  System.out.println("Returning int value " + x + " for p = " + p);
}
// �����쳣���ص�������˴��������ʱ�׳�Exception�����쳣����������Դ�����Ϻ󣬻��׳���������쳣�������������̼�������
after() throwing(Exception e): target(Point) && call(void setX(int)) {
  System.out.println(e);
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

// ʹ�� thisJoinPoint ����������ñ����ռ����е���صĸ�������Ϣ
//  thisJoinPoint.getArgs()   ��ȡ�е�Ĳ���
//  thisJoinPoint.getStaticPart()  �е㾲̬��Ϣ�������кţ���̬ǩ��������ݵķ�ʽ�� thisJoinPointStaticPart ����
//  thisJoinPointStaticPart == thisJoinPoint.getStaticPart()
//  thisJoinPoint.getKind() == thisJoinPointStaticPart.getKind()
//  thisJoinPoint.getSignature() == thisJoinPointStaticPart.getSignature()
//  thisJoinPoint.getSourceLocation() == thisJoinPointStaticPart.getSourceLocation()

aspect SimpleTracing {
    pointcut tracedCall():
        call(void FigureElement.draw(GraphicsContext));

    before(): tracedCall() {
        System.out.println("Entering: " + thisJoinPoint);  // ����� Entering: call(void FigureElement.draw(GraphicsContext))
    }
}
// thisJoinPointStaticPart ���������е�հ��ľ�̬��Ϣ
before() : execution (* *(..)) {
	System.err.println(thisEnclosingJoinPointStaticPart.getSourceLocation())
}

// ʹ��proceed���⺯�����˴���ȡ�滻�е㣬Ȼ��ͨ��proceed���⺯������ִ���е�ĺ�������
void around(Point p, int x): target(p) && args(x) && call(void setX(int)) {
    if (p.assertX(x)) 
		proceed(p, x);
    p.releaseResources();
}

                    /******************               Inter-type declarations�ڲ���������                  ******************/
// ��aspect�ڲ�����Server������һ�������disabled���ҳ�ʼ��Ϊfalse��
// disabled�������ڱ�aspect�ڲ��ɼ�����Server����������涼���ɼ�
private boolean Server.disabled = false;

// ����ɼ�����������Point����ͬ����������������
public int Point.x = 0;

// ����Point���getX()����������ɼ���������Point��ͬ��������������������
public int Point.getX() { return this.x; }

//  ����Point��ʵ��Comparable�ӿڣ���Point�౾�����˺�Comparable�ӿ�ͬ���ķ������򱨴�
declare parents: Point implements Comparable;

//  ������̳�
declare parents: Point extends GeometricObject;

//  ԭ����������ֻ������һ��������ΪĿ���ڲ��࣬�������·�ʽ���Թ�ܸ����ƣ���ͨ������һ������ڲ��ӿ���ʵ�ֶ���ڹ�������
aspect A {
	private interface HasName {}
	declare parents: (Point || Line || Square) implements HasName;

	private String HasName.name;
	public  String HasName.getName()  { return name; }
}
					
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
	/* ע��������֣�pointcut��advice������ð��:���Ĳ����������������η������Ҳ�args�в�����
	   ��ʹ��pointcut�ᴿ��ֱ��ģʽҲ�����  */
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

#####  �쳣��������
ע��㣺
1��hander(�쳣��)��������ӵ���catch��䡣Ҳ�����ڳ���׽�쳣�ĵط��������ӵ㣬�������������쳣�ĵط���
2���˴����쳣��ΪThrowable�������ࡣ
3��handler(�쳣��)ֻ��ʹ��before()ָ��ǰ��֪ͨ����֧��������ʽ��֪ͨ��/4���˴����쳣��Ҳ����ʹ��ͨ���������ѡ��ͬ���ϵ�һϵ�����ӵ㡣
   4.1 mypackage..*   ����mypackage�������Ӱ��е�������ӵ�
   4.2 MyClass+	      ����MyClass�༰���κ������е����ӵ�
   
#####  ������Ͷ������ϵ����ӵ�
1�������췽���ĵ��ã���call(Signature)����㣬���ж����new�ؼ�����Ϊǩ����һ����)
2�������췽����ִ�У���execution(Signature)����㣬���ж����new�ؼ�����Ϊǩ����һ���֣�
3����������ʼ������initialization(Signature)����㣩
4���������Ԥ�ȳ�ʼ������preinitialization(Signature)����㣩
5��������ĳ�ʼ������staticinitialization(TypePattern)����㣩��