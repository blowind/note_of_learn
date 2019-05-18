
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
    }
}


����̬����
JDK��̬�������ʹ��ʾ���������ƣ����õĵط�����Ҫ�ṩ����Ľӿڲ���ʹ�ã�����˴���HelloWorld�ӿڣ�
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

CGLIB��̬����
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



import java.lang.reflect.*;
interface MyInterface {
	void doSomething();
	void somethingElse(String arg);
}
class RealObject implements MyInterface {
	public void doSomething() { Print.print("doSomething"); }
	public void somethingElse(String arg) { Print.print("somethingElse" + arg); }
}
/* override invoke()���� */
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
	/* consumerչʾ�˴���ľ���ʹ�ã���ʵûɶ���壬������֪�ӿ���ķ���ֱ�ӵ��þͺ� */
	public static void consumer(MyInterface mi) {
		mi.doSomething();
		mi.somethingElse("bonobo");
	}

	public static void main(String[] args) {
		RealObject ro = new RealObject();
		consumer(ro);
		/* ���þ�̬����������̬���� */
		MyInterface proxy = (MyInterface)Proxy.newProxyInstance(
		/* ��һ�������Ǹ�����������˴������еķ����Ľӿڵ�������� */ 
												MyInterface.class.getClassLoader(), 
		/* �ڶ��������Ǵ���ʵ�ֵĽӿ��б��˴�(������������)���˴�������������ɶ���壿������ */
												new Class[]{ MyInterface.class}, 
		/* ������������InvocationHandler�ӿڵ�һ��ʵ�֣� */
												new DynamicProxyHandler(ro));
		consumer(proxy);
		proxy.doSomething();
		proxy.somethingElse("aaaaaaaaaaaaa");
		
		/*  */
		Print.print(new Class[]{ MyInterface.class });
	}
}
