
// 接口定义
public interface Calculator {
    int add(int a, int b);
}

// 目标类（被代理对象的类）定义
public class CalculatorImpl implements Calculator {
    @Override
    public int add(int a, int b) {
        System.out.println("目标对象方法执行");
        return a + b;
    }
}

=============================    静态代理示例   ============================= 

/**
 *  静态代理示例，通过构造函数得到目标对象后，包装目标对象的所有方法
 *  具体通过业务代码实现全部方法，并且代理不同的类对象要实现不同的代理类
 */
public class StaticProxy implements Calculator {
    private Calculator calculator;

    public StaticProxy(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public int add(int a, int b) {
        System.out.println("调用目标对象方法前的操作");
        int result =  this.calculator.add(a, b);
        System.out.println("调用目标对象方法后的操作");
        return result;
    }

    public static void main(String[] args) {
        CalculatorImpl target = new CalculatorImpl();
        StaticProxy proxy = new StaticProxy(target);
        System.out.println(proxy.add(3, 4));
    }
}

=============================    动态代理示例   ============================= 

/**
 *  JDK动态代理详解示例，通过反射让JVM给生成代理类，代理不同的类对象可以用一个DynamicProxy完成
 */
public class DynamicProxy {

    /**
     * 生成代理对象的较详细的过程写法，从JVM自己生成新的Class对象$proxy0到生成该类实例并返回
     */
    private static Object getProxyDetail(final Object target) throws Exception {
        /*
         * 参数1：将目标类加载到内存的加载器
         * 参数2：目标对象的结果
         * 具体操作：生成一个实现目标对象接口的Class对象（类定义），该类自带构造器
        */
        Class proxyClazz = Proxy.getProxyClass(target.getClass().getClassLoader(), target.getClass().getInterfaces());

        // 得到有参构造器 com.sun.proxy.$Proxy0(InvocationHandler h)
        Constructor constructor = proxyClazz.getConstructor(InvocationHandler.class);

        /* 通过有参构造器反射创建代理实例，传入InvocationHandler实例，
        * 每次调用代理对象的方法时，都会通过InvocationHandler实例的invoke方法调用
        * 因此在invoke方法做业务需要的额外操作处理
        *
        * 实际在第一步生成的类型proxyClazz中，会附带生成一个字段 protected invocationHandler;
        * 同时该类型实现了目标对象接口的所有方法，并在每个方法中执行invocationHandler.invoke(...);
        * 因此与下面实例化操作中传入的InvocationHandler对象联系起来
        */
        Object proxy = constructor.newInstance(new InvocationHandler() {
            // method参数是代理对象本次本调用的方法对象，args是该方法调用时的实参数组
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName() + "方法开始执行...");
                // 注意到此处代理对象永久保留了一个对传入的目标对象的引用，代理对象不GC，目标对象不可能GC
                Object result = method.invoke(target, args);
                System.out.println(result);
                System.out.println(method.getName() + "方法结束执行...");
                // 返回目标对象执行的结果，此处是add()方法的执行结果，本return是在每次proxy对象调用add()时执行
                return result;
            }
        });
        // 返回生成的代理对象，本return是在每次getProxy()函数调用时执行
        return proxy;
    }

    /**
     * 实际常用的代理获取方法写法
     */
    private static Object getProxy(final Object target) throws Exception {
        /* 通过newProxyInstance()方法直接完成 */
        Object proxy = Proxy.newProxyInstance(
                target.getClass().getClassLoader(), // 类加载器
                target.getClass().getInterfaces(),  // 让目标对象和代理对象实现相同接口
                new InvocationHandler() {   // 代理中添加额外处理代码的地方
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println(method.getName() + "方法开始执行.");
                        Object result = method.invoke(target, args);
                        System.out.println(result);
                        System.out.println(method.getName() + "方法结束执行.");
                        return result;
                    }
                });
        return proxy;
    }

    public static void main(String[] args) throws Throwable {
        CalculatorImpl target = new CalculatorImpl();
        Calculator proxy = (Calculator)getProxyDetail(target);
        System.out.println(proxy.add(1, 2));

        System.out.println(proxy.getClass().getName()); // 打印类名com.sun.proxy.$Proxy0
        System.out.println(proxy instanceof Calculator); // $Proxy0是Calculator接口的子类
    }
}


【动态代理】
JDK动态代理基本使用示例：（限制：调用的地方必须要提供代理的接口才能使用，比如此处的HelloWorld接口）
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
	/*存放真实对象*/
	private Object target = null;

	/*绑定代理对象和真实对象的代理关系，返回代理对象*/
	public Object bind(Object target) {
		// 保存真实对象target到本对象里
		this.target = target;
		/*建立并生成代理对象
		  参数1:指定target本身的类加载器作为类加载器
		  参数2:指定动态代理对象挂载的接口，此处为target实现的接口
		  参数3:定义实现方法逻辑的代理类，此处为当前对象，该对象必须实现InvocationHandler接口*/
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}

	@Override
	/*实现代理逻辑方法
	* 参数1:代理对象，即前述bind方法生成的对象
	* 参数2:当前调度的方法
	* 参数3:调度方法的参数*/
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("进入代理逻辑方法");
		System.out.println("在调度真实对象之前的服务");
		Object obj = method.invoke(target, args);
		System.out.println("在调度真实对象之后的服务");
		return obj;
	}
	/* 使用举例 */
	public static void main(String[] argv) {
		JdkProxyExample jdk = new JdkProxyExample();
		HelloWorld proxy = (HelloWorld)jdk.bind(new HelloWorldImpl());
		proxy.sayHelloWorld();
	}
}

使用JDK动态代理实现拦截器：
public interface Interceptor {
	boolean before(Object proxy, Object target, Method method, Object[] args);
	void around(Object proxy, Object target, Method method, Object[] args);
	void after(Object proxy, Object target, Method method, Object[] args);
}
/*外层使用者提供的拦截器实现，与业务逻辑强相关*/
public class MyInterceptor implements Interceptor {
	@Override
	public boolean before(Object proxy, Object target, Method method, Object[] args) {
		System.out.println("反射方法前逻辑");
		return false;
	}

	@Override
	public void after(Object proxy, Object target, Method method, Object[] args) {
		System.out.println("反射方法后逻辑");
	}

	@Override
	public void around(Object proxy, Object target, Method method, Object[] args) {
		System.out.println("取代被代理对象的方法");
	}
}
/*拦截逻辑实现，底层框架提供者的任务*/
public class InterceptorJdkProxy implements InvocationHandler {
	private Object target;
	private String interceptorClass = null;

	public InterceptorJdkProxy(Object target, String interceptorClass) {
		this.target = target;
		this.interceptorClass = interceptorClass;
	}

	/*绑定定制化拦截器，返回一个代理占位*/
	public static Object bind(Object target, String interceptorClass) {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InterceptorJdkProxy(target, interceptorClass));
	}

	@Override
	/*拦截器的拦截逻辑放在此处实现*/
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		/*如果未注册自定义拦截类(设置拦截器)，则直接反射原有方法*/
		if(interceptorClass == null) {
			return method.invoke(target, args);
		}

		Object result = null;
		/*通过反射生成拦截器*/
		Interceptor interceptor = (Interceptor)Class.forName(interceptorClass).newInstance();
		/*调用前置方法，返回true则反射原有方法，否则执行around拦截方法*/
		if(interceptor.before(proxy, target, method, args)) {
			result = method.invoke(target, args);
		}else{
			interceptor.around(proxy, target, method, args);
		}
		/*统一调用后置方法*/
		interceptor.after(proxy,  target, method, args);
		return result;
	}

	public static void main(String[] args) {
		HelloWorld proxy = (HelloWorld)InterceptorJdkProxy.bind(new HelloWorldImpl(), "com.zxf.zxfbatis.simple.MyInterceptor");
		proxy.sayHelloWorld();
	}
}

CGLIB动态代理：
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;
public class CglibProxyExample implements MethodInterceptor {
	/*生成CGLIB代理对象，注意整个操作过程不涉及代理接口*/
	public Object getProxy(Class cls) {
		/*CGLIB enhaner增强类对象*/
		Enhancer enhancer = new Enhancer();
		/*设置增强类型，本质上就是设置被代理的对象*/
		enhancer.setSuperclass(cls);
		/*定义代理逻辑对象为当前对象，即当前对象为代理人
		  要求当前对象实现MethodInterceptor接口*/
		enhancer.setCallback(this);
		/*返回代理对象*/
		return enhancer.create();
	}

	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		System.out.println("调用真实对象前");
		Object result = methodProxy.invokeSuper(proxy, args);
		System.out.println("调用真实对象后");
		return result;
	}

	public static void main(String[] argv) {
		CglibProxyExample cpe = new CglibProxyExample();
		ReflectServiceImpl obj = (ReflectServiceImpl)cpe.getProxy(ReflectServiceImpl.class);
		obj.sayHello("张三");
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
/* override invoke()方法 */
class DynamicProxyHandler implements InvocationHandler {
	private Object proxied;
	/* 传入参数是具体要被代理的对象，代理能执行该对象上的所有方法并且传递对应的参数 */
	public DynamicProxyHandler(Object proxied) { this.proxied = proxied; }
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		/* 此处是代理进行原有对象操作进行封装的地方，此处仅做打印，猜测可以根据method函数名特点修改入参做更改？ */
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
	/* consumer展示了代理的具体使用，其实没啥意义，按照已知接口里的方法直接调用就好 */
	public static void consumer(MyInterface mi) {
		mi.doSomething();
		mi.somethingElse("bonobo");
	}

	public static void main(String[] args) {
		RealObject ro = new RealObject();
		consumer(ro);
		/* 调用静态方法创建动态代理 */
		MyInterface proxy = (MyInterface)Proxy.newProxyInstance(
		/* 第一个参数是个类加载器，此处用运行的方法的接口的类加载器 */ 
												MyInterface.class.getClassLoader(), 
		/* 第二个参数是代理实现的接口列表，此处(不是类或抽象类)，此处类引用数组有啥意义？？？？ */
												new Class[]{ MyInterface.class}, 
		/* 第三个参数是InvocationHandler接口的一个实现， */
												new DynamicProxyHandler(ro));
		consumer(proxy);
		proxy.doSomething();
		proxy.somethingElse("aaaaaaaaaaaaa");
		
		/*  */
		Print.print(new Class[]{ MyInterface.class });
	}
}
