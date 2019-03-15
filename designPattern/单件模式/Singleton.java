
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *  @ClassName: Singleton
 *  @Description: 单例模式
 *  @Author: ZhangXuefeng
 *  @Date: 2019/3/12 10:12
 *  @Version: 1.0
 **/
public class Singleton {

	/********************************       全局引用场景        ************************/

	/** 饿汉式：使用静态局部变量，在类加载时就实例化
	 *  优点：1、支持多线程并发
	 *  缺点：1、不是懒加载模式（lazy initialization），类加载时就实例化，如果对象很大且使用不多时浪费资源
	 *       2、有些场景无法使用：譬如 Singleton 实例的创建是依赖参数或者配置文件的，在外部调用getInstance()之前必须配置一些参数
	 *
	 */

	private static final Singleton instance = new Singleton();  // 声明的同时实例化

	private Singleton() {}
	public static Singleton getInstance() { return instance; }

	/** 饿汉式：使用静态内部类
	 *  优点：1、同上，使用JVM本身机制保证线程安全性；
	 *  	 2、lazy loading，私有内部类对外不可见，仅在getInstance()第一次被调用创建实例
	 *
	 */
	private static class SingletonHolder {
		private static final Singleton INSTANCE = new Singleton(); // 声明的同时实例化
	}

	private Singleton() {}
	public static final Singleton getInstance() { return SingletonHolder.INSTANCE; }

	/** 饱汉式： 声名狼藉，不建议使用
	 *  优点：1、双重判空支持多线程并发
	 *       2、按需加载，仅在使用单例对象时分配资源初始化对象
	 *       3、可以参数化配置
	 *
	 *  注意点：
	 *  volatile的意义：主要在于instance = new Singleton()并非是一个原子操作，主要有下面三步
	 *  1、给 instance 分配内存
	 *  2、调用 Singleton 的构造函数来初始化成员变量
	 *  3、将instance对象指向分配的内存空间
	 *
	 *  但是在 JVM 的即时编译器中存在指令重排序的优化。也就是说上面的第2步和第3步的顺序是不能保证的，
	 *  最终的执行顺序可能是 1-2-3 也可能是 1-3-2
	 *  。如果是后者，则在 3 执行完毕、2 未执行之前，被线程二抢占了
	 *  ，这时 instance 已经是非 null 了（但却没有初始化），
	 *  所以线程二会直接返回 instance，然后使用，然后顺理成章地报错。
	 *
	 *  声明为volatile，使用其一个特性：禁止指令重排序优化。
	 *  在 volatile 变量的赋值操作后面会有一个内存屏障（生成的汇编代码上），读操作不会被重排序到内存屏障之前。
	 */
	private volatile static Singleton instance = NULL;  // 仅声明不实例化，因此必须声明成volatile

	private Singleton() {}
	public static Singleton getInstance() {
		if(instance == null) {
			synchronized (Singleton.class) {
				if(instance == null) {
					instance = new Singleton();
				}
			}
		}
		return instance;
	}

	/********************************       间接引用情景        ************************/
	/**
	 * 需要创建一次的对象不是直接被全局的引用所引用，而是间接地被引用。
	 * 经常有这种情况，全局维护一个并发的ConcurrentMap, Map的每个Key对应一个对象，这个对象需要只创建一次
	 */

	/*纯样例*/
	private class InstanceObject {
		private String param;
		InstanceObject(String key) { param = key; }
	}

	/** CAS
	 *  缺点：1、很可能会产生多个InstanceObject对象，但最终只有一个InstanceObject有用，
	 *       没有达到仅创建一个实例的情况，对创建成本很高的大对象缓存不适用
	 */
	private final ConcurrentMap<String, InstanceObject> cache = new ConcurrentHashMap<>();

	public InstanceObject get(String key) {
		InstanceObject single = cache.get(key);
		if(single == null) {
			InstanceObject instanceObject = new InstanceObject(key);
			// 返回值是key中被替换的值，如果不存在该key值，则为null
			single = cache.putIfAbsent(key, instanceObject);
			if(single == null) {
				single = instanceObject;
			}
		}
		return single;
	}

	/** 影子类：
	 *  优点：使用Future代替真是对象，多次创建Future代价比创建缓存大对象小
	 *
	 */
	private final ConcurrentMap<String, Future<InstanceObject>> cache2 = new ConcurrentHashMap<>();

	public InstanceObject get(final String key) {
		Future<InstanceObject> future = cache2.get(key);
		if(future == null) {
			Callable<InstanceObject> callable = () -> { return new InstanceObject(key); };
			FutureTask<InstanceObject> task = new FutureTask<>(callable);

			future = cache2.putIfAbsent(key, task);
			if(future == null) {
				future = task;
				task.run();
			}
		}

		try{
			return future.get();
		}catch (Exception e) {
			cache2.remove(key);
			throw new RuntimeException(e);
		}
	}

	/** 自旋锁
	 *  优点：使用AtomicBoolean，相比Future对象更轻量化，主要使用的还是volatile的特性
	 *  缺点：需要多使用一个ConcurrentHashMap用于自旋锁
	 */
	private final ConcurrentMap<String, InstanceObject> cache3 = new ConcurrentHashMap<>();
	private final ConcurrentMap<String, AtomicBoolean> spinCache = new ConcurrentHashMap<>();

	public InstanceObject getAtomic(final String key) {
		InstanceObject single = cache3.get(key);
		if(single == null) {
			AtomicBoolean newBoolean = new AtomicBoolean(false);
			/* 由于spinCache操作的原子性，仅第一个线程放入的newBoolean后获取的oldboolean为null，
			 * 其他线程获取的都是第一个线程放入的newBoolean，并且在这个所谓的oldboolean上自旋 */
			AtomicBoolean oldboolean = spinCache.putIfAbsent(key, newBoolean);
			if(oldboolean == null) {
				cache3.put(key, new InstanceObject(key));
				newBoolean.set(true);
			}else{
				//除首个执行putIfAbsent的线程外，其他线程在自旋状态上自旋，等待被释放
				while(!oldboolean.get());
			}
			single = cache3.get(key);
		}
		return single;
	}

}



/** 使用枚举实现：
 *  优点：1、枚举限制构造方法为私有，保证外部无法实例化
 *       2、仅在访问枚举实例时执行构造方法，懒加载
 *       3、每个枚举实例都是static final，只会被实例化一次
 *       4、Enum抽象类默认实现Serializable接口，满足网络传输等操作的序列化要求
 */
public enum SingletonEnum {
	INSTANCE(1, 2, 3);

	private Resourse resourse;

	SingletonEnum(int param1, int param2, int param3) {
		resourse = new Resource(param1, param2, param3);
	}

	public Resource getInstance() {
		return resourse;
	}
}

