
����Ļ���˼·��
����ģʽ�ϣ���������Subject��ɫ��RealSubject��ɫ��Proxy��ɫ�����У�Subject��ɫ������RealSubject��Proxy��ɫӦ��ʵ�ֵĽӿڣ�RealSubject��ɫ�����������ҵ������ܣ�Proxy��ɫ���������Request���󣬵���realsubject ��Ӧ��request������ʵ��ҵ���ܣ��Լ���������ҵ��



// �ӿڶ���
public interface Calculator {
    int add(int a, int b);
}

// Ŀ���ࣨ�����������ࣩ����
public class CalculatorImpl implements Calculator {
    @Override
    public int add(int a, int b) {
        System.out.println("Ŀ����󷽷�ִ��");
        return a + b;
    }
}

=============================    ��̬����ʾ��   ============================= 

�ϸ�������AspectJ�Ǿ�̬������Ϊ�����õ��Ǳ�����ֲ�롣
��AspectJ����Ҫд��Ӧ��xml���������棬֯���ȣ�Ȼ����AspectJ�ı�������������µ��ֽ����ļ����������Ǿ�̬����


/**
 *  ��̬����ʾ����ͨ�����캯���õ�Ŀ�����󣬰�װĿ���������з���
 *  ����ͨ��ҵ�����ʵ��ȫ�����������Ҵ���ͬ�������Ҫʵ�ֲ�ͬ�Ĵ�����
 */
public class StaticProxy implements Calculator {
    private Calculator calculator;

    public StaticProxy(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public int add(int a, int b) {
        System.out.println("����Ŀ����󷽷�ǰ�Ĳ���");
        int result =  this.calculator.add(a, b);
        System.out.println("����Ŀ����󷽷���Ĳ���");
        return result;
    }

    public static void main(String[] args) {
        CalculatorImpl target = new CalculatorImpl();
        StaticProxy proxy = new StaticProxy(target);
        System.out.println(proxy.add(3, 4));
    }
}

=============================    ��̬����ʾ��   ============================= 

����˼·��
���Լ��ķ������ܵ�ʵ�ֽ��� InvocationHandler��ɫ������Proxy��ɫ�е�ÿһ�������ĵ��ã�
Proxy��ɫ���ύ��InvocationHandler��������InvocationHandler����þ�������ɫ�ķ�����

����������ʵ��˼·��
1������һ�����ܽӿڣ�Ȼ����Proxy��RealSubject��ʵ������ӿڡ� JDK�Դ��Ķ�̬������õ�����˼·
2���Ƚ����޵ķ�ʽ������ͨ���̳С� CGLIB��Code Generation Library����������˼·

jdk��̬��������java�ڲ��ķ��������ʵ�ֵģ�cglib��̬����ײ����ǽ���asm��ʵ�ֵġ�
�ܵ���˵�����������������Ĺ����бȽϸ�Ч����asm��������֮������ִ�й����бȽϸ�Ч������ͨ����asm���ɵ�����л��棬�������asm��������̵�Ч���⣩��
jdk��̬�����Ӧ��ǰ�ᣬ������Ŀ�������ͳһ�Ľӿڡ����û������ǰ�ᣬjdk��̬������Ӧ�á��ɴ˿��Կ�����jdk��̬������һ���ľ����ԣ�cglib���ֵ��������ʵ�ֵĶ�̬����Ӧ�ø��ӹ㷺������Ч���ϸ������ơ�


========   JDK��̬����

JDK��̬������������
1.��ȡ RealSubject�ϵ����нӿ��б�
2.ȷ��Ҫ���ɵĴ������������Ĭ��Ϊ��com.sun.proxy.$ProxyXXXX ��
3.������Ҫʵ�ֵĽӿ���Ϣ���ڴ����ж�̬���� ��Proxy����ֽ��룻
4.����Ӧ���ֽ���ת��Ϊ��Ӧ��class ����
5.����InvocationHandler ʵ��handler����������Proxy���з������ã�
6.Proxy ��class���� �Դ�����handler����Ϊ������ʵ����һ��proxy����

ȱ�㣺 ֻ�ܴ�������ָ���Ĺ����ӿڵķ���

/**
 *  JDK��̬�������ʾ����ͨ��������JVM�����ɴ����࣬����ͬ������������һ��DynamicProxy���
 */
public class DynamicProxy {

    /**
     * ���ɴ������Ľ���ϸ�Ĺ���д������JVM�Լ������µ�Class����$proxy0�����ɸ���ʵ��������
     */
    private static Object getProxyDetail(final Object target) throws Exception {
        /*
         * ����1����Ŀ������ص��ڴ�ļ�����
         * ����2��Ŀ�����Ľ��
         * �������������һ��ʵ��Ŀ�����ӿڵ�Class�����ඨ�壩�������Դ�������
        */
        Class proxyClazz = Proxy.getProxyClass(target.getClass().getClassLoader(), target.getClass().getInterfaces());

        // �õ��вι����� com.sun.proxy.$Proxy0(InvocationHandler h)
        Constructor constructor = proxyClazz.getConstructor(InvocationHandler.class);

        /* ͨ���вι��������䴴������ʵ��������InvocationHandlerʵ����
        * ÿ�ε��ô������ķ���ʱ������ͨ��InvocationHandlerʵ����invoke��������
        * �����invoke������ҵ����Ҫ�Ķ����������
        *
        * ʵ���ڵ�һ�����ɵ�����proxyClazz�У��ḽ������һ���ֶ� protected invocationHandler;
        * ͬʱ������ʵ����Ŀ�����ӿڵ����з���������ÿ��������ִ��invocationHandler.invoke(...);
        * ���������ʵ���������д����InvocationHandler������ϵ����
        */
        Object proxy = constructor.newInstance(new InvocationHandler() {
            // method�����Ǵ�����󱾴α����õķ�������args�Ǹ÷�������ʱ��ʵ������
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName() + "������ʼִ��...");
                // ע�⵽�˴�����������ñ�����һ���Դ����Ŀ���������ã��������GC��Ŀ����󲻿���GC
                Object result = method.invoke(target, args);
                System.out.println(result);
                System.out.println(method.getName() + "��������ִ��...");
                // ����Ŀ�����ִ�еĽ�����˴���add()������ִ�н������return����ÿ��proxy�������add()ʱִ��
                return result;
            }
        });
        // �������ɵĴ�����󣬱�return����ÿ��getProxy()��������ʱִ��
        return proxy;
    }

    /**
     * ʵ�ʳ��õĴ����ȡ����д��
     */
    private static Object getProxy(final Object target) throws Exception {
        /* ͨ��newProxyInstance()����ֱ����� */
        Object proxy = Proxy.newProxyInstance(
                target.getClass().getClassLoader(), // �������
                target.getClass().getInterfaces(),  // ��Ŀ�����ʹ������ʵ����ͬ�ӿ�
                new InvocationHandler() {   // ��������Ӷ��⴦�����ĵط�
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println(method.getName() + "������ʼִ��.");
                        Object result = method.invoke(target, args);
                        System.out.println(result);
                        System.out.println(method.getName() + "��������ִ��.");
                        return result;
                    }
                });
        return proxy;
    }

    public static void main(String[] args) throws Throwable {
        CalculatorImpl target = new CalculatorImpl();
        Calculator proxy = (Calculator)getProxyDetail(target);
        System.out.println(proxy.add(1, 2));

        System.out.println(proxy.getClass().getName()); // ��ӡ����com.sun.proxy.$Proxy0
        System.out.println(proxy instanceof Calculator); // $Proxy0��Calculator�ӿڵ�����
		
		// ��JDK��̬�����������.class�ļ�
		generateClassFile(proxy.getClass(), "CalculatorProxy");
    }
	
	// ��JDK��̬������洢���ļ��Ĳ���
	public static void generateClassFile(Class clazz, String proxyName) {
        byte[] classFile = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());
        String path = DynamicProxy.class.getResource(".").getPath();
        System.out.println(path);
        FileOutputStream out = null;
        try{
            out = new FileOutputStream(path + proxyName + ".class");
            out.write(classFile);
            out.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                out.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

���ɵĶ�̬������CalculatorProxy��.class�ļ�ʹ��JD-GUI�鿴�ľ���Ч����
/*
* ���ɵĶ�̬���������֯ģʽ�Ǽ̳�Proxy�࣬Ȼ��ʵ����Ҫʵ�ִ�������ϵ����нӿڣ�
  ����ʵ�ֵĹ����У�����ͨ�������еķ�����������InvocationHandler������
  1��Proxy���ж�����protected InvocationHandler h;����
  2�����е����з�������final��
  3�����еķ������ܵ�ʵ�ֶ�ͳһ������InvocationHandler��invoke()����
*/
public final class CalculatorProxy extends Proxy implements Calculator
{
  private static Method m1;
  private static Method m2;
  private static Method m3;
  private static Method m0;
  
  public CalculatorProxy(InvocationHandler paramInvocationHandler)
  {
    super(paramInvocationHandler);
  }
  
  public final boolean equals(Object paramObject)
  {
    try {
      return ((Boolean)this.h.invoke(this, m1, new Object[] { paramObject })).booleanValue();
    } catch (Error|RuntimeException localError) {
      throw localError;
    } catch (Throwable localThrowable) {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final String toString()
  {
    try {
      return (String)this.h.invoke(this, m2, null);
    } catch (Error|RuntimeException localError) {
      throw localError;
    } catch (Throwable localThrowable) {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final int add(int paramInt1, int paramInt2)
  {
    try {
      return ((Integer)this.h.invoke(this, m3, new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) })).intValue();
    } catch (Error|RuntimeException localError) {
      throw localError;
    } catch (Throwable localThrowable) {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final int hashCode()
  {
    try {
      return ((Integer)this.h.invoke(this, m0, null)).intValue();
    } catch (Error|RuntimeException localError) {
      throw localError;
    } catch (Throwable localThrowable) {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  static {
    try
    {
      m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[] { Class.forName("java.lang.Object") });
      m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
      m3 = Class.forName("samples.Calculator").getMethod("add", new Class[] { Integer.TYPE, Integer.TYPE });
      m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
      return;
    } catch (NoSuchMethodException localNoSuchMethodException) {
      throw new NoSuchMethodError(localNoSuchMethodException.getMessage());
    } catch (ClassNotFoundException localClassNotFoundException) {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
  }
}


JDK��̬��������÷�����ʾ��һ��
��Ҫ��4���ࣺ 1�������ӿڣ�2���������ࣻ3��InvocationHandlerʵ���ࣻ4����̬������
interface MyInterface {
	void doSomething();
	void somethingElse(String arg);
}
class RealObject implements MyInterface {
	public void doSomething() { Print.print("doSomething"); }
	public void somethingElse(String arg) { Print.print("somethingElse" + arg); }
}
class DynamicProxyHandler implements InvocationHandler {
	private Object proxied;
	/* ��������Ǿ���Ҫ������Ķ��󣬴�����ִ�иö����ϵ����з������Ҵ��ݶ�Ӧ�Ĳ��� */
	public DynamicProxyHandler(Object proxied) { this.proxied = proxied; }
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		/* �˴��Ǵ������ԭ�ж���������з�װ�ĵط����˴�������ӡ���²���Ը���method�������ص��޸���������ģ� */
		Print.print("*** proxy: " + proxy.getClass() + ", method: " + method + ", args: " + args);
		if(args != null) {
			for(Object arg: args) {
				Print.print(" " + arg);
			}
		}
		return method.invoke(proxied, args);
	}
}
public class SimpleDynamicProxy {
	
	public static Object getProxy(MyInterface target) {
		/* ���þ�̬����������̬���� */
		return (MyInterface)Proxy.newProxyInstance(
		/* ��һ�������Ǹ�����������˴������еķ����Ľӿڵ�������� */ 
												MyInterface.class.getClassLoader(), 
		/* �ڶ��������Ǵ���ʵ�ֵĽӿ��б��˴�(������������)���˴�������������ɶ���壿������ */
												new Class[]{ MyInterface.class}, 
		/* ������������InvocationHandler�ӿڵ�һ��ʵ�֣� */
												new DynamicProxyHandler(target));
	}

	public static void main(String[] args) {
		MyInterface proxy = getProxy(new RealObject());
		proxy.doSomething();
		proxy.somethingElse("aaaaaaaaaaaaa");
	}
}

JDK��̬��������÷�����ʾ������
���������InvocationHandlerʵ����϶�Ϊһ���򻯴��룬��߷�װ��

public interface HelloWorld {
	public void sayHelloWorld();
}
public class HelloWorldImpl implements HelloWorld {
	@Override
	public void sayHelloWorld() {
		System.out.println("Hello World");
	}
}
public class JdkProxyExample implements InvocationHandler {
	/*�����ʵ����*/
	private Object target = null;

	/*�󶨴���������ʵ����Ĵ����ϵ�����ش������*/
	public Object bind(Object target) {
		// ������ʵ����target����������
		this.target = target;
		/*���������ɴ������
		  ����1:ָ��target��������������Ϊ�������
		  ����2:ָ����̬���������صĽӿڣ��˴�Ϊtargetʵ�ֵĽӿ�
		  ����3:����ʵ�ַ����߼��Ĵ����࣬�˴�Ϊ��ǰ���󣬸ö������ʵ��InvocationHandler�ӿ�*/
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}

	@Override
	/*ʵ�ִ����߼�����
	* ����1:������󣬼�ǰ��bind�������ɵĶ���
	* ����2:��ǰ���ȵķ���
	* ����3:���ȷ����Ĳ���*/
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("��������߼�����");
		System.out.println("�ڵ�����ʵ����֮ǰ�ķ���");
		Object obj = method.invoke(target, args);
		System.out.println("�ڵ�����ʵ����֮��ķ���");
		return obj;
	}
	/* ʹ�þ��� */
	public static void main(String[] argv) {
		JdkProxyExample jdk = new JdkProxyExample();
		HelloWorld proxy = (HelloWorld)jdk.bind(new HelloWorldImpl());
		proxy.sayHelloWorld();
	}
}

ʹ��JDK��̬����ʵ����������
public interface Interceptor {
	boolean before(Object proxy, Object target, Method method, Object[] args);
	void around(Object proxy, Object target, Method method, Object[] args);
	void after(Object proxy, Object target, Method method, Object[] args);
}
/*���ʹ�����ṩ��������ʵ�֣���ҵ���߼�ǿ���*/
public class MyInterceptor implements Interceptor {
	@Override
	public boolean before(Object proxy, Object target, Method method, Object[] args) {
		System.out.println("���䷽��ǰ�߼�");
		return false;
	}

	@Override
	public void after(Object proxy, Object target, Method method, Object[] args) {
		System.out.println("���䷽�����߼�");
	}

	@Override
	public void around(Object proxy, Object target, Method method, Object[] args) {
		System.out.println("ȡ�����������ķ���");
	}
}
/*�����߼�ʵ�֣��ײ����ṩ�ߵ�����*/
public class InterceptorJdkProxy implements InvocationHandler {
	private Object target;
	private String interceptorClass = null;

	public InterceptorJdkProxy(Object target, String interceptorClass) {
		this.target = target;
		this.interceptorClass = interceptorClass;
	}

	/*�󶨶��ƻ�������������һ������ռλ*/
	public static Object bind(Object target, String interceptorClass) {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InterceptorJdkProxy(target, interceptorClass));
	}

	@Override
	/*�������������߼����ڴ˴�ʵ��*/
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		/*���δע���Զ���������(����������)����ֱ�ӷ���ԭ�з���*/
		if(interceptorClass == null) {
			return method.invoke(target, args);
		}

		Object result = null;
		/*ͨ����������������*/
		Interceptor interceptor = (Interceptor)Class.forName(interceptorClass).newInstance();
		/*����ǰ�÷���������true����ԭ�з���������ִ��around���ط���*/
		if(interceptor.before(proxy, target, method, args)) {
			result = method.invoke(target, args);
		}else{
			interceptor.around(proxy, target, method, args);
		}
		/*ͳһ���ú��÷���*/
		interceptor.after(proxy,  target, method, args);
		return result;
	}

	public static void main(String[] args) {
		HelloWorld proxy = (HelloWorld)InterceptorJdkProxy.bind(new HelloWorldImpl(), "com.zxf.zxfbatis.simple.MyInterceptor");
		proxy.sayHelloWorld();
	}
}


CGLIB��̬������������
1.����A�ϵ����з�final ��public���͵ķ������壻
2.����Щ�����Ķ���ת�����ֽ��룻
3.����ɵ��ֽ���ת������Ӧ�Ĵ����class����
4.ʵ�� MethodInterceptor�ӿڣ���������Դ����������з�������������ӿں�JDK��̬����InvocationHandler�Ĺ��ܺͽ�ɫ��һ���ģ�

cglib�����ֿ�ѡ��ʽ���̳к����á�
��һ���ǻ��ڼ̳�ʵ�ֵĶ�̬�������Կ���ֱ��ͨ��super����target������
�������ַ�ʽ��spring���ǲ�֧�ֵģ���Ϊ�����Ļ������target����Ͳ��ܱ�spring������
����cglib���ǲ�������jdk�ķ�ʽ��ͨ������target�������ﵽ���ط�����Ч����


ȱ�㣺��final���ε���ֻ��ʹ��JDK��̬������Ϊ��final���ε��಻�ܱ��̳У���Cglib�������õļ̳�ԭ��ʵ�ִ����

����POM��
<dependency>
	<groupId>cglib</groupId>
	<artifactId>cglib</artifactId>
	<version>3.2.12</version>
</dependency>


CGLIB��̬��������÷�����ʾ��һ��
public class Programer {
    public void code() {
        System.out.println("I'm a programer, Just coding...");
    }
}
public class Hacker implements MethodInterceptor {
    @Override
	// ����1��������󣻲���2�����ط���������3�����ط����Ĳ���������4��Method��Ĵ�����
    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("**** I am a hacker,Let's see what the poor programmer is doing Now...");
		/* ԭ���ķ�������ͨ��ʹ��java.lang.reflect.Method�����һ�㷴����ã�
		*  ����ʹ�� net.sf.cglib.proxy.MethodProxy������á�
		*  net.sf.cglib.proxy.MethodProxyͨ������ѡʹ�ã���Ϊ������
		*/
        proxy.invokeSuper(target, args);
        System.out.println("****  Oh,what a poor programmer.....");
        return null;
    }
}
public class ProgrammerProxy {
    public static void main(String[] args) {
        Programer programer = new Programer();
        Hacker hacker = new Hacker();
		//cglib�м�ǿ��������������̬����
        Enhancer enhancer = new Enhancer();
		//����Ҫ������̬�������
        enhancer.setSuperclass(programer.getClass());
		// ���ûص��������൱���Ƕ��ڴ����������з����ĵ��ã��������CallBack��
		// ��Callback����Ҫʵ��intercept()������������
        enhancer.setCallback(hacker);
        Programer proxy = (Programer)enhancer.create();
        proxy.code();
		
		generateClassFile(proxy.getClass(), "CglibProxy");
    }
	
	public static void generateClassFile(Class clazz, String proxyName) {
        byte[] classFile = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());
        String path = ProgrammerProxy.class.getResource(".").getPath();
        System.out.println(path);
        FileOutputStream out = null;
        try{
            out = new FileOutputStream(path + proxyName + ".class");
            out.write(classFile);
            out.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                out.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

���ɵĶ�̬������CglibProxy��.class�ļ�ʹ��JD-GUI�鿴�ľ���Ч����
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Factory;
/*
* 1�����д����඼�̳�Proxy�࣬ʵ��cglib��Factory�ӿ�
  2�����з���ȫ����final��
*/
public final class CglibProxy extends Proxy implements Factory
{
  private static Method m1;
  private static Method m4;
  private static Method m9;
  private static Method m6;
  private static Method m2;
  private static Method m3;
  private static Method m7;
  private static Method m8;
  private static Method m0;
  private static Method m5;
  
  public CglibProxy(InvocationHandler paramInvocationHandler) {
    super(paramInvocationHandler);
  }
  
  public final int hashCode() {
    try {
      return ((Integer)this.h.invoke(this, m0, null)).intValue();
    } catch (Error|RuntimeException localError) {
      throw localError;
    } catch (Throwable localThrowable) {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final boolean equals(Object paramObject) {
    try {
      return ((Boolean)this.h.invoke(this, m1, new Object[] { paramObject })).booleanValue();
    } catch (Error|RuntimeException localError) {
      throw localError;
    } catch (Throwable localThrowable) {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final String toString() {
    try {
      return (String)this.h.invoke(this, m2, null);
    } catch (Error|RuntimeException localError) {
      throw localError;
    } catch (Throwable localThrowable) {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final Object newInstance(Class[] paramArrayOfClass, Object[] paramArrayOfObject, Callback[] paramArrayOfCallback) {
    try {
      return (Object)this.h.invoke(this, m3, new Object[] { paramArrayOfClass, paramArrayOfObject, paramArrayOfCallback });
    } catch (Error|RuntimeException localError) {
      throw localError;
    } catch (Throwable localThrowable) {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final Object newInstance(Callback[] paramArrayOfCallback) {
    try {
      return (Object)this.h.invoke(this, m4, new Object[] { paramArrayOfCallback });
    } catch (Error|RuntimeException localError) {
      throw localError;
    } catch (Throwable localThrowable) {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final Object newInstance(Callback paramCallback) {
    try {
      return (Object)this.h.invoke(this, m5, new Object[] { paramCallback });
    } catch (Error|RuntimeException localError) {
      throw localError;
    } catch (Throwable localThrowable) {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final void setCallbacks(Callback[] paramArrayOfCallback) {
    try {
      this.h.invoke(this, m9, new Object[] { paramArrayOfCallback });
      return;
    } catch (Error|RuntimeException localError) {
      throw localError;
    } catch (Throwable localThrowable) {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final void setCallback(int paramInt, Callback paramCallback) {
    try {
      this.h.invoke(this, m6, new Object[] { Integer.valueOf(paramInt), paramCallback });
      return;
    } catch (Error|RuntimeException localError) {
      throw localError;
    } catch (Throwable localThrowable) {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final Callback getCallback(int paramInt) {
    try {
      return (Callback)this.h.invoke(this, m7, new Object[] { Integer.valueOf(paramInt) });
    } catch (Error|RuntimeException localError) {
      throw localError;
    } catch (Throwable localThrowable) {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  public final Callback[] getCallbacks() {
    try {
      return (Callback[])this.h.invoke(this, m8, null);
    } catch (Error|RuntimeException localError) {
      throw localError;
    } catch (Throwable localThrowable) {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
  
  static {
    try {
      m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[] { Class.forName("java.lang.Object") });
      m4 = Class.forName("net.sf.cglib.proxy.Factory").getMethod("newInstance", new Class[] { Class.forName("[Lnet.sf.cglib.proxy.Callback;") });
      m9 = Class.forName("net.sf.cglib.proxy.Factory").getMethod("setCallbacks", new Class[] { Class.forName("[Lnet.sf.cglib.proxy.Callback;") });
      m6 = Class.forName("net.sf.cglib.proxy.Factory").getMethod("setCallback", new Class[] { Integer.TYPE, Class.forName("net.sf.cglib.proxy.Callback") });
      m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
      m3 = Class.forName("net.sf.cglib.proxy.Factory").getMethod("newInstance", new Class[] { Class.forName("[Ljava.lang.Class;"), Class.forName("[Ljava.lang.Object;"), Class.forName("[Lnet.sf.cglib.proxy.Callback;") });
      m7 = Class.forName("net.sf.cglib.proxy.Factory").getMethod("getCallback", new Class[] { Integer.TYPE });
      m8 = Class.forName("net.sf.cglib.proxy.Factory").getMethod("getCallbacks", new Class[0]);
      m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
      m5 = Class.forName("net.sf.cglib.proxy.Factory").getMethod("newInstance", new Class[] { Class.forName("net.sf.cglib.proxy.Callback") });
      return;
    } catch (NoSuchMethodException localNoSuchMethodException) {
      throw new NoSuchMethodError(localNoSuchMethodException.getMessage());
    } catch (ClassNotFoundException localClassNotFoundException) {
      throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
    }
  }
}



CGLIB��̬��������÷�����ʾ������
�������MethodInterceptorʵ����϶�Ϊһ���򻯴��룬��߷�װ��

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;
public class CglibProxyExample implements MethodInterceptor {
	/*����CGLIB�������ע�������������̲��漰����ӿ�*/
	public Object getProxy(Class cls) {
		/*CGLIB enhaner��ǿ�����*/
		Enhancer enhancer = new Enhancer();
		/*������ǿ���ͣ������Ͼ������ñ�����Ķ���*/
		enhancer.setSuperclass(cls);
		/*��������߼�����Ϊ��ǰ���󣬼���ǰ����Ϊ������
		  Ҫ��ǰ����ʵ��MethodInterceptor�ӿ�*/
		enhancer.setCallback(this);
		/*���ش������*/
		return enhancer.create();
	}

	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		System.out.println("������ʵ����ǰ");
		Object result = methodProxy.invokeSuper(proxy, args);
		System.out.println("������ʵ�����");
		return result;
	}

	public static void main(String[] argv) {
		CglibProxyExample cpe = new CglibProxyExample();
		ReflectServiceImpl obj = (ReflectServiceImpl)cpe.getProxy(ReflectServiceImpl.class);
		obj.sayHello("����");
	}
}


Spring AOP������Ҫ��������������ǿ(Advice)������(Advisor)��
��ǿ��֯�뵽Ŀ�������ӵ��ϵ�һ�γ�����룬�������Ҫ��ʲô���ʲô����ʵʩ��ǿ��
����ô�������ض�����ʵʩ��ǿ���أ���invoke�����е�method#name�����жϡ�

Spring�ṩ��ProxyFactoryBean��������������BeanPostProcessorʵ���Զ���������

Spring��������������JDK��CGLib��������
���ǻ�������������ڼ�ͨ���ֽ���༭�ļ�����������֯�뵽Ŀ�����У�����֯�뷽ʽ��ΪLTW(Load Time Weaving)��
SpringΪLTW�Ĺ����ṩ��ϸ���ȵĿ��ƣ���֧���ڵ���ClassLoader��Χ��ʵʩ���ļ�ת���������ø�Ϊ�򵥡�
