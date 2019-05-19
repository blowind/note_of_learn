
代理的基本思路：
代理模式上，基本上有Subject角色，RealSubject角色，Proxy角色。其中：Subject角色负责定义RealSubject和Proxy角色应该实现的接口；RealSubject角色用来真正完成业务服务功能；Proxy角色负责将自身的Request请求，调用realsubject 对应的request功能来实现业务功能，自己不真正做业务。



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

严格意义上AspectJ是静态代理，因为它采用的是编译器植入。
用AspectJ，需要写相应的xml，定义切面，织入点等，然后由AspectJ的编译器来编译出新的字节码文件，这明显是静态代理。


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

基本思路：
将自己的方法功能的实现交给 InvocationHandler角色，外界对Proxy角色中的每一个方法的调用，
Proxy角色都会交给InvocationHandler来处理，而InvocationHandler则调用具体对象角色的方法。

具体有两种实现思路：
1、定义一个功能接口，然后让Proxy和RealSubject来实现这个接口。 JDK自带的动态代理采用的这种思路
2、比较隐晦的方式，就是通过继承。 CGLIB（Code Generation Library）采用这种思路

jdk动态代理是由java内部的反射机制来实现的，cglib动态代理底层则是借助asm来实现的。
总的来说，反射机制在生成类的过程中比较高效，而asm在生成类之后的相关执行过程中比较高效（可以通过将asm生成的类进行缓存，这样解决asm生成类过程低效问题）。
jdk动态代理的应用前提，必须是目标类基于统一的接口。如果没有上述前提，jdk动态代理不能应用。由此可以看出，jdk动态代理有一定的局限性，cglib这种第三方类库实现的动态代理应用更加广泛，且在效率上更有优势。


========   JDK动态代理

JDK动态代理具体操作：
1.获取 RealSubject上的所有接口列表；
2.确定要生成的代理类的类名，默认为：com.sun.proxy.$ProxyXXXX ；
3.根据需要实现的接口信息，在代码中动态创建 该Proxy类的字节码；
4.将对应的字节码转换为对应的class 对象；
5.创建InvocationHandler 实例handler，用来处理Proxy所有方法调用；
6.Proxy 的class对象 以创建的handler对象为参数，实例化一个proxy对象

缺点： 只能代理所有指定的公共接口的方法

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
		
		// 将JDK动态代理类输出到.class文件
		generateClassFile(proxy.getClass(), "CalculatorProxy");
    }
	
	// 将JDK动态代理类存储到文件的操作
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

生成的动态代理类CalculatorProxy的.class文件使用JD-GUI查看的具体效果：
/*
* 生成的动态代理类的组织模式是继承Proxy类，然后实现需要实现代理的类上的所有接口，
  而在实现的过程中，则是通过将所有的方法都交给了InvocationHandler来处理
  1、Proxy类中定义了protected InvocationHandler h;变量
  2、类中的所有方法都是final的
  3、所有的方法功能的实现都统一调用了InvocationHandler的invoke()方法
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


JDK动态代理基本用法完整示例一：
主要有4个类： 1、公共接口；2、被代理类；3、InvocationHandler实现类；4、动态代理类
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
	
	public static Object getProxy(MyInterface target) {
		/* 调用静态方法创建动态代理 */
		return (MyInterface)Proxy.newProxyInstance(
		/* 第一个参数是个类加载器，此处用运行的方法的接口的类加载器 */ 
												MyInterface.class.getClassLoader(), 
		/* 第二个参数是代理实现的接口列表，此处(不是类或抽象类)，此处类引用数组有啥意义？？？？ */
												new Class[]{ MyInterface.class}, 
		/* 第三个参数是InvocationHandler接口的一个实现， */
												new DynamicProxyHandler(target));
	}

	public static void main(String[] args) {
		MyInterface proxy = getProxy(new RealObject());
		proxy.doSomething();
		proxy.somethingElse("aaaaaaaaaaaaa");
	}
}

JDK动态代理基本用法完整示例二：
将代理类和InvocationHandler实现类合二为一，简化代码，提高封装性

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


CGLIB动态代理具体操作：
1.查找A上的所有非final 的public类型的方法定义；
2.将这些方法的定义转换成字节码；
3.将组成的字节码转换成相应的代理的class对象；
4.实现 MethodInterceptor接口，用来处理对代理类上所有方法的请求（这个接口和JDK动态代理InvocationHandler的功能和角色是一样的）

cglib有两种可选方式，继承和引用。
第一种是基于继承实现的动态代理，所以可以直接通过super调用target方法，
但是这种方式在spring中是不支持的，因为这样的话，这个target对象就不能被spring所管理，
所以cglib还是采用类似jdk的方式，通过持有target对象来达到拦截方法的效果。


缺点：被final修饰的类只能使用JDK动态代理，因为被final修饰的类不能被继承，而Cglib则是利用的继承原理实现代理的

引入POM：
<dependency>
	<groupId>cglib</groupId>
	<artifactId>cglib</artifactId>
	<version>3.2.12</version>
</dependency>


CGLIB动态代理基本用法完整示例一；
public class Programer {
    public void code() {
        System.out.println("I'm a programer, Just coding...");
    }
}
public class Hacker implements MethodInterceptor {
    @Override
	// 参数1：代理对象；参数2：拦截方法；参数3：拦截方法的参数；参数4：Method类的代理类
    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("**** I am a hacker,Let's see what the poor programmer is doing Now...");
		/* 原来的方法可能通过使用java.lang.reflect.Method对象的一般反射调用，
		*  或者使用 net.sf.cglib.proxy.MethodProxy对象调用。
		*  net.sf.cglib.proxy.MethodProxy通常被首选使用，因为它更快
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
		//cglib中加强器，用来创建动态代理
        Enhancer enhancer = new Enhancer();
		//设置要创建动态代理的类
        enhancer.setSuperclass(programer.getClass());
		// 设置回调，这里相当于是对于代理类上所有方法的调用，都会调用CallBack，
		// 而Callback则需要实行intercept()方法进行拦截
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

生成的动态代理类CglibProxy的.class文件使用JD-GUI查看的具体效果：
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Factory;
/*
* 1、所有代理类都继承Proxy类，实现cglib的Factory接口
  2、所有方法全部是final的
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



CGLIB动态代理基本用法完整示例二；
代理类和MethodInterceptor实现类合二为一，简化代码，提高封装性

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


Spring AOP里最重要的两个概念是增强(Advice)和切面(Advisor)，
增强是织入到目标类连接点上的一段程序代码，切面决定要给什么类的什么方法实施增强。
是怎么做到对特定方法实施增强的呢？对invoke方法中的method#name进行判断。

Spring提供了ProxyFactoryBean创建代理，及利用BeanPostProcessor实现自动创建代理。

Spring是在运行期利用JDK或CGLib创建代理，
我们还可以在类加载期间通过字节码编辑的技术，将切面织入到目标类中，这种织入方式称为LTW(Load Time Weaving)。
Spring为LTW的过程提供了细粒度的控制，它支持在单个ClassLoader范围内实施类文件转换，且配置更为简单。
