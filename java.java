Set对每个值都只保存一个对象
Map允许将某些对象与其他一些对象关联起来形成关联数组

Arrays.asList接受一个数组或是一个用逗号分隔的元素列表
逗号分隔的元素列表：
Arrays.asList("second", "third", "forth", "fifth");

数组：
Arrays.asList( new int[]{1,2,3,4,5} );
int[] realArray = new int[]{5,6,7,8,9};
Arrays.asList(realArray);

//  定义一个静态方法的内部匿名类，注意filter的参数必须是final，因为匿名类内部使用来自该类范围之外的对象必须符合这个要求
public static FilenameFilter filter(final String regex) {
	return new FilenameFilter() {
		private Pattern pattern = Pattern.compile(regex);
		public boolean accept(File dir, String name) {
			return pattern.matcher(name).matches();
		}
	};
}

//  给list赋初值，StringAddress就一个私有化的String属性和相应的构造器，list中有4个元素，其字符串变量均为Hello字符串
List<StringAddress> list = new ArrayList<StringAddress>(Collections.nCopies(4, new StringAddress("Hello")));
// list中有4个元素，其字符串变量均为World字符串，之前的结果被覆盖，fill只能替换已有元素，不能添加新元素
Collections.fill(list, new StringAddress("World"));


@SuppressWarning "unchecked"   //  屏蔽编译告警，使用在尽量小的单位上，例如 如果仅对方法屏蔽，就不要将词标签放到类之前

/****                                               容器                                        ****/

List
List<Pet> pets = new ArrayList<Pet>();
添加元素
pets.add(newPet);
批量添加元素
pets.addAll(Arrays.asList(pet1, pet2, pet3, pet4));
删除元素
pets.remove(oldPet);
获取元素索引：
int index = pets.indexOf(pet3);  //  得到2
获取子列表
List<Pet> sub = pets.subList(1,2);  // 获取第二个元素组成的List
判断子列表是否是子集：
boolean isTrue = pets.containsAll(sub);

ArrayList<T> 的使用
import java.util.ArrayList;
class Apple {
	private static long counter;
	private final long id = counter++;
	public long id() { return id; }
}
class GrannySmith extends Apple {}
class Gala extends Apple {}
class Fuji extends Apple {}
public class GenericsAndUpcasting {
	public static void main(String[] args) {
		ArrayList<Apple> apples = new ArrayList<Apple>();   //  ArrayList<T> 被向上转型为 List<T>
		//  容器添加元素并向上转型
		apples.add(new GrannySmith());
		apples.add(new Gala());
		apples.add(new Fuji());
		for(Apple c : apples) 
			System.out.println(c);
	}
}
List的基本用法演示  主要是ArrayList<T> 在随机访问时候性能最优，插入/删除元素较慢
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
class Apple {
	private static long counter;
	private final long id = counter++;
	public long id() { return id; }
}
class GrannySmith extends Apple {}
class Gala extends Apple {}
class Fuji extends Apple {}
public class GenericsAndUpcasting {
	public static void main(String[] args) {
		ArrayList<Apple> apples = new ArrayList<Apple>();
		Apple tmp = new GrannySmith();
		/* 直接使用Arrays.asList的输出时，底层实际还是数组，不能调整尺寸，添加/删除元素时会抛出异常 */
		/* 从集合中添加元素  addAll() */
		apples.addAll(Arrays.asList(tmp, new Gala(), new Fuji()));
		apples.addAll(1, Arrays.asList(new Fuji()));   //  在第一个元素之后加入一个新的List
		/* 使用迭代器遍历元素 */
		for(Apple c : apples) 
			System.out.println(c);
		/* 删除不存在的元素，返回 false */
		Print.print(apples.remove(new GrannySmith()));
		/* 删除指定元素，返回 true */
		Print.print(apples.remove(tmp));
		/* 删除索引指定的元素，返回 true，此时集合还剩一个元素 */
		apples.remove(0);
		
		for(Apple c : apples) 
			System.out.println(c);
		/* 添加单个元素 */
		apples.add(tmp);
		/* 索引某个元素的下标，因为tmp刚被加入，因此返回最后一个元素的下标 1 */
		Print.print(apples.indexOf(tmp));
		/* 获取第一个元素，此处是 Fuji() 对象 */
		Print.print(apples.get(0));
		/* 将第一个元素修改成 gala() */
		apples.set(0, new Gala());
		
		
		/* 获取List的子集， 此处得到包含最后一个元素的List */
		/* 所产生的列表的幕后就是初始列表，因此对sub的修改都会反映到初始列表中 */
		List<Apple> sub = apples.subList(0, 1);
		Print.print(sub);
		/* 取apples和sub的交集并最终放入apples */
		apples.retainAll(sub);
		/*  apples是否包含元素tmp，返回true */
		apples.contains(tmp);
		/* 返回 true ， sub是apples的子集 */
		Print.print(apples.containsAll(sub));
		/* 使用Collections批量加入元素，要注意最后有个s，不带s的Collection只能操作Collection对象，限制太大 */
		Collections.addAll(apples, new Fuji(), new Fuji(), new Fuji(), new Apple());
		for(Apple c : apples) 
			System.out.print(c + " ");
		
		/* 给apples排序，此处调用失败，因为Apple对象未实现 Comparable 接口 */
		//  Collections.sort(apples);
		/* 打乱apples的顺序 */
		Collections.shuffle(apples);
		
		/* 清空所有元素 */
		apple.clear()
		/* 判断当前List是否为空 */
		apple.isEmpty();
		
		/* 直接使用Arrays的asList()函数，类型提升时候需要 显示类型参数说明 */
		List<Apple> as = Arrays.<Apple>asList(new Gala(), new Fuji());
		for(Apple c : as) 
			System.out.print(c + " ");
	}
}
LinkedList<T>  强于插入/删除，弱于查找，适合当做队列和栈
public static void main(String[] args) {
	LinkedList<String> numArray = new LinkedList<String>(Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight"));
	
	Print.print(numArray);
	/* 获取第一个元素，并不移除，如果List为空抛出NoSuchElementException异常 */
	Print.print(numArray.getFirst());
	/* 获取第一个元素，功能完全同上*/
	Print.print(numArray.element());
	/* 功能完全同上，只是在List为空时不抛出异常，而是返回null */
	Print.print(numArray.peek());
	
	/* 移除第一个元素并将其作为返回值，如果List为空抛出NoSuchElementException异常 */
	Print.print(numArray.remove());
	/* 移除第一个元素并将其作为返回值，功能完全同上 */
	Print.print(numArray.removeFirst());
	/* 移除第一个元素并将其作为返回值，功能完全同上，只是在List为空时不抛出异常，而是返回null */
	Print.print(numArray.poll());
	/* 移除最后一个元素 */
	Print.print(numArray.removeLast());
	
	/* 在List头添加元素 */
	numArray.addFirst("zero");
	/* 在列表末尾添加一个元素 */
	numArray.offer("nine");
	/* 在列表末尾添加一个元素，功能完全同上 */
	numArray.add("ten");
	/* 在列表末尾添加一个元素，功能完全同上 */
	numArray.addLast("eleven");
	
	/*  [zero, three, four, five, six, seven, nine, ten, eleven]   */
	Print.print(numArray);
}
Stack<T>类型，不要使用java自带Stack类型，实现的不好，使用LinkedList代替，自己封装
public class MyStack<T> {
	private LinkedList<T> storage = new LinkedList<T>();
	public void push(T e) { storage.addFirst(e); }
	/* 返回第一个元素，但是不删除  */
	public T peek() { return storage.getFirst(); }
	/* 删除第一个元素并将其作为返回值返回  */
	public T pop() { return storage.removeFirst(); }
	public boolean empty() { return storage.isEmpty(); }
	public String toString() { return storage.toString(); }
}
Set<T>类型，主要用来去重/查重，HashSet对查找有优化，除了都使用HashSet，没啥好纠结的，与Collection有完全一样的接口
public static void main(String[] args) {
	Random rand = new Random(47);
	/* HashSet使用散列函数 */
	Set<Integer> intSet = new HashSet<Integer>();
	for(int i=0; i<10; i++) {
		intSet.add(rand.nextInt(30));
	}
	Print.print(intSet.contains(9));
	Print.print(intSet);
}
TreeSet<T> 将元素存储在红黑树中，这样得到了排序的映射/字典
public static void main(String[] args) {
	Random rand = new Random(47);
	/* 使用红黑树的 TreeSet，得到的字典是排序的，不过没有get函数获取内容 */
	Set<Integer> intSet = new TreeSet<Integer>();
	for(int i=0; i<10000; i++) {
		intSet.add(rand.nextInt(30));
	}
	 // [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
	System.out.println(intSet); 
	
	Set<String> words = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
}
LinkedHashSet 为了查询速度也使用了散列，同时使用了列表维护元素的插入顺序

HashMap<T, T>
public static void main(String[] args) {
	Random rand = new Random(47);
	Map<Integer, Integer> m = new HashMap<Integer, Integer>();
	for(int i=0; i<10000; i++) {
		int r = rand.nextInt(20);
		/*  Map里没有这个元素时，会返回null */
		Integer freq = m.get(r);
		m.put(r, freq == null ? 1 : 1 + freq);
	}
	/*  查看m中是否包含 key为3的元素 */
	Print.print(m.containsKey(3));
	/*  查看m中是否包含 value为500的元素 */
	Print.print(m.containsValue(500));
	/*  获得所有的 key 集合 */
	Print.print(m.keySet());
	/*  获得所有的 value 集合 */
	Print.print(m.values());
	/* 迭代遍历所有的keys */
	for(Integer k : m.keySet()) {
			Print.print(k + " happens " + m.get(k) + "times");
	}
}

Queue的基本实现和用法 LinkedList实现了Queue接口，通过将LinkedList向上转型为Queue，用来当做Queue使用
Queue<Integer> queue = new LinkedList<Integer>();
/* 添加元素，查看LinkedList知道是在队尾添加的 */
queue.offer(333);
// 获取元素，获取后不删除，可以调用 queue.remove() 删除
queue.peek();  //  返回队头， 队列为空时返回 null
queue.element();  //  返回队头， 队列为空时返回NoSuchElementException异常

queue.poll()    //  删除并返回队头，队列为空时返回 null
queue.remove()  // 删除并返回队头，队列为空时返回NoSuchElementException异常

PriorityQueue 优先级队列，下一个弹出的元素是具有最高优先级的元素，这个优先级是放入的对象实现的 Comparator 来得到
确保调用peek()、poll()、remove()方法时，获取的元素将是队列中优先级最高的元素
优先级队列算法会在插入时排序（维护一个堆），但是它们也可能在移除时选择最重要的元素，这个依赖于具体实现。如果对象的优先级在它的队列中等待时可以修改，那么算法的选择就显得很重要了
public static void main( String[] args )
{
	PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
	Random rand = new Random(47);
	for(int i=0; i<10; i++) {
		int tmp = rand.nextInt(i+10);
		/* 得到的是个随机序列 [8 1 1 1 5 14 3 1 0 1]   */
		System.out.print(tmp + " ");
		pq.offer(tmp);
	}
	while(pq.peek() != null) {
		/* 经过优先级队列排序后，严格按照递增顺序出队  [0 1 1 1 1 1 3 5 8 14 ] */
		System.out.print(pq.remove() + " ");
	}
	
	List<Integer> ints = Arrays.asList(25, 22, 20, 18, 14, 9, 3, 1, 2, 3, 9, 14, 25);
	/* 初始化时指明排序序列，按照降序排列出队 */
	pq = new PriorityQueue<Integer>(ints.size(), Collections.reverseOrder());
	pq.addAll(ints);
	/* 得到 [ 25 25 22 20 18 14 14 9 9 3 3 2 1 ] */
	while(pq.peek() != null) {
		System.out.print(pq.remove() + " ");
	}
}

直接实现迭代器，关键是实现 public Iterator<String> iterator() {} 函数，在自己类内部怎么都有一个集合
import java.util.*;
class InstanceSequence {
	protected String[] des = new String[] {"one", "two", "three", "four", "five", "six", "seven"}; 
}
/* 通过继承得到已有的字符串数组 */
public class NonCollectionSequence extends InstanceSequence {
	/* 通过实现迭代器函数 Iterator<T> iterator() 使得本类具有迭代功能，该接口下的 hasNext() next() remove() 一个都不能少*/
	public Iterator<String> iterator() {
		/* 通过匿名类实现，每次返回迭代器，这样匿名类内部的index每次都是从0开始，体会一下这个最简单的实现 */
		return new Iterator<String>() {
			private int index = 0;
			public boolean hasNext() {
				return index < des.length;
				 
			}
			public String next() {
				return des[index++];
			}
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	public static void main(String[] args) {
		NonCollectionSequence ncs = new NonCollectionSequence();
		Iterator<String> it = ncs.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		//  因为实现的不是 Iterable<T> 接口，因此不能在 foreach 循环中使用
		// for(String s: ncs) {
			// System.out.println(it.next());
		// }
	}
}
实现JSE5新的 Iterable 接口，该接口被foreach用来在序列中移动，下面实现了一个反转遍历的例子
package com.zxf.my;
import java.util.*;
class ReversibleArrayList<T> extends ArrayList<T> {
	public ReversibleArrayList(Collection<T> c) { super(c); }
	/* 最终for里面使用的时候，是调用的  Iterable<T>.iterator()  即Iterable<T>对象的iterator()方法，该方法返回Iterator<T>对象 */
	/* 最终本质上还是要返回 接口泛型对象，注意此处不是 Iterator<T> */
	public Iterable<T> reversed() {
		/* 实例化一个接口泛型的对象，此处是一个匿名类 */
		return new Iterable<T>() {
			/* 匿名类中的方法 public Iterator<T> iterator() 返回一个迭代子  */
			public Iterator<T> iterator() {
				/* 返回迭代子的方法内部，本质上是创建一个迭代器对象，该对象实现了一个匿名类 */
				return new Iterator<T>()  {
					int current = size() - 1;
					public boolean hasNext() { return current > -1; }
					/*  调用get冲已有的集合ArrayList中获取对象 */
					public T next() { return get(current--); }
					public void remove() { throw new UnsupportedOperationException(); }
				};
			}
		};
	}
}
public class AdapterMethodIdiom {
	public static void main(String[] args) {
		ReversibleArrayList<String> ral = new ReversibleArrayList<String>(
							Arrays.asList("To be or not to be".split(" ")));
		for(String s: ral) 
			System.out.print(s + " ");  // 得到 To be or not to be
		System.out.println();
		for(String s: ral.reversed())
			System.out.print(s + " ");	 //  得到 be to not or be To 
	}
}

/****                                      泛型的使用                                     ****/
/* 定义一个基本泛型，本质上一个泛型类就是定义一个新类，该类生成了一票新方法对泛型T进行处理，与基本类的声明没有本质区别
   将NewClass<T>当做NewClass看待就好，除了初始化时需要通过<>加一些类型确认之外，与NewClass在使用上没本质区别
   而诸如List<T>之类的泛型集合，就是在泛型T上加了一票集合操作方法，功能上就是一个List类的功能
   */
class Decorate<T> {   // 声明一个支持泛型的新类
	private T s;
	public Decorate(T outS) { s = outS; } 
	public String toString() {   return "Decorate<" + s + ">";   }
	public void setValue(T t) {  s = t;  }
}
//  泛型新类的集合的使用
public static void main(String[] args) {
/* 此处初始化时看起来有三层嵌套，其实本质上是一个Decorate<String>对象，
   简单的看成Decorate即可，明白该对象内部操作String就好                    */
	List<Decorate<String>> testOne = new ArrayList<Decorate<String>>(  
												Arrays.asList( new Decorate<String>("I"),   // 元素
															   new Decorate<String>("II")));
	System.out.println(testOne);  // 得到  [Decorate<I>, Decorate<II>]
	
	/* 注意此处是ArrayList里面存储的元素引用的拷贝，可以理解成所有指向Decorate<String>对象的指针的拷贝  */
	List<Decorate<String>> testTwo = new ArrayList<Decorate<String>>(testOne);
	//  修改testTwo集合中的第二个元素位置存储的引用为一个新的对象的引用
	testTwo.set(1, new Decorate<String>("III"));
	System.out.println(testTwo);  // 得到 [Decorate<I>, Decorate<III>]
	System.out.println(testOne);  // 得到 [Decorate<I>, Decorate<II>]
	
/*  注意到此处获取了testTwo第一个元素存储的引用，该引用与testOne第一个元素存储的引用一样，
    即都指向同一个Decorate<String>对象                                                     */
	Decorate<String> tmp = testTwo.get(0);
	tmp.setValue("IV");
	System.out.println(testTwo);   // 得到  [Decorate<IV>, Decorate<III>]
	System.out.println(testOne);   // 得到  [Decorate<IV>, Decorate<II>]



/****                                      使用泛型包装类/接口对外提供功能                                     ****/
import java.util.Arrays;
/* 注意，此处是泛型类，具体的泛型表示放到类名上，内部的方法就不用再放<T>了 */
class ClassParameter<T> {  
	public T[] f(T[] arg) { return arg; }
}
/* 注意，此处是泛型方法，类名声明的时候不带泛型，具体的方法最前面，要带一个表示类型的<T> */
class MethodParameter {
	public static <T> T[] f(T[] arg) { return arg; }  
}
public class ParameterizedArrayType {
	public static void main(String[] args) {
		Integer[] ints = {1, 2, 3, 4 ,5};
		Double[] doubles = {1.1, 2.2, 3.3, 4.4, 5.5};
		
		Integer[] ints2 = new ClassParameter<Integer>().f(ints);    // 参数化类的具体T必须在声明时指定
		Double[] doubles2 = MethodParameter.f(doubles);         // 参数化方法的具体T类型匹配到调用的时候自己识别，即类型推断
		
		System.out.println(Arrays.deepToString(ints2));
		System.out.println(Arrays.deepToString(doubles2));
	}
}

Set<Object> so 是个参数化类型， 表示可以包含任何类型的集合，因此 so.add("adadfs");   so.add(123);  操作都合法

Set<?> 表示一个通配符类型，表示只能包含某种未知对象类型的一个集合，该类型只能用来接收参数，不能用new实例化，做了形参也不能添加/删除元素(null除外，可以添加null，唯一可以添加的元素)，只能clear() 清空

Set 原生态类型，弃用   注意 Set<String> 和 Set<Object> 都是 Set的子类 ，但是相互之间没有父子关系，是平级的

/* 无限制的通配符类型，只能用来接收参数  */
static int numElementsInCommon(Set<?> s1, Set<?> s2) {  // Set<?>本质上与Set没有差别，但通配符是安全的
	int ret = 0;
	for(Object o : s1)    
		if(s2.contains(o))  //  不知道这块能不能用containsAll()，有空试一下
			result++;
	return ret;
}	
无法将任何类型放到 Collection<?> c中  任何类似 c.add("aaa") 的调用都会失败

不要使用原生态集合（诸如List，Set，Collection），要使用带类型List<String>之类，但是有两种场景除外：

场景一：
在类文字中必须使用原生态类型，规范不允许使用参数化类型（数组类型和基本类型除外） 即
List.class   String[].class  int.class 是合法的  但是List<String>.class    List<?>.class 不合法
场景二：
if( o instanceof Set ) { Set<?> m = (Set<?>)o; }   // 一旦确定 o 是Set，必须将他转换成通配符类型Set<?>
由于泛型信息运行时被擦除，因此带参数的 instanceof 操作非法

没有所谓的泛型数组  new List<String>[];  都是不合法的  只能创建 new List<String>();

/* 泛型类的一般实现，无法避免使用数组时的用法，此时一定要在外部保证不会传入不同类型的对象 */
public class Stack<E> {
	private E[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	@SuppressWarnings("unchecked")
	public Stack() {
		elements = (E[])new Object[DEFAULT_INITIAL_CAPACITY];   // 此处初始化时进行类型强转，但是数组类型的强转通常更危险
	}
	public push(E e) {
		ensureCapacity();
		elements[size++] = e;
	}
	public E pop() {
		if( size == 0 ) throw new EmptyStackException();
		E ret = elements[--size];
		elements[size] = null;
		return ret;
	}
}
public class Stack<E> {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	public Statck() {
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}
	public push(E e) {
		ensureCapacity();
		elements[size++] = e;
	}
	public E pop() {
		if( size == 0 )  throw new EmptyStackException();
		@SuppressWarnings("unchecked") E ret = (E)elements[--size];  // 每次取出对象时进行类型强转，强转次数多
		elements[size] = null;
		return ret;
	}
}
/* 使用有限通配符提升API的灵活性  PECS原则 */
public class Stack<E> {
	//  传入一个集合元素，集合的元素只需要是E的子类型(包括E)就可以放入，放入的集合是生产者
	public pushAll(Iterable<? extends E> src) { ... }
	//  将元素从栈里返回到一个集合里，集合的元素要是E的超集，取出的集合是消费者
	public void popAll(Collection<? super E> dst) { ... }
	//  如果外部的集合作为方法的入参时，即可能当生产者又可能当消费者，则不适合用通配符，老老实实用E就可以了
	// 不要用通配符做返回类型
}
//  合并两个集合，参数都是生产者
public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2)  { ... }
Set<Integer> integers = new HashSet<Integer>();
Set<Double> doubles = new HashSet<Double>();
Set<Number> numbers = Union.<Number>union(integers, doubles);  //  运行跑不过，此处有问题

泛型单例
interface UnaryFunction<T> { T apply(T arg); }
// 定义一个静态变量，每次调用时实例化一个UnaryFunction<Object>类型的对象，该对象唯一的方法是返回自身
private static UnaryFunction<Object> IDENTITY_FUNCTION = new UnaryFunction<Object>() { 
																	public Object apply(Object arg) { return arg; }
};
@SuppressWarnings("unchecked")
// 泛型静态方法作为类型，调用其静态方法apply返回入参本身，同时由于类型推断，变量声明时的类型限制了传入参数的类型
public static <T> UnaryFunction<T> identityFunction() {  
	return (UnaryFunction<T>) IDENTITY_FUNCTION;
} 
//  使用方法   没看出来这个泛型单例有啥用，给他传啥他输出啥（与泛型类型声明一致）
UnaryFunction<String> mys = identityFunction();
mys.apply("112233") == "112233"  // 返回 true

//  <T extends Comparable<T>> 读作“针对可以与自身比较的每个类型T”
// 求一个表里面的最大元素的泛型方法，传入的元素可以是某类的子类
public static <T extends Comparable<T>> T max(List<T> list) {
	Iterator<T> i = list.iterator();
	T ret = i.next();
	while(i.hasNext()) {
		T tmp = i.next();
		if(t.compareTo(result) > 0) ret = tmp;   //  因为能与自身比较，此处才能调用
	}
	return ret;
}
使用通配符放缩之后的效果
/* 只要T的父类可以比较，则符合比较结果，可比较性是消费者使用的，即后面的实现需要调用 compareTo() 函数进行具体比较
   拿来取最大值的列表是生产者，因此是T的子类，注意到迭代器也要同步修改 */
/*  定义一个取列表最大值的泛型函数，其返回值可以时候当前泛型类的子类，并且当前泛型类的父类必须实现了Compatable()接口，其入参必须是当前泛型类的子类的集合
<T extends Comparable*****>  表示T必须继承实现了某个类/接口
<? super T>                  表示实现比较接口的类是T的父类
<? extends T>                表示入参必须是T的子类    */
public static <T extends Comparable<? super T>> T max(List<? extends T> list) {
	Iterator<? extends T> i = list.iterator();
	T ret = i.next();
	while(i.hasNext()) {
		T tmp = i.next();
		if(t.compareTo(result) > 0) ret = tmp;   
	}
	return ret;
}
// 交换方法的两种泛型写法，第一种使用类型参数，第二种使用通配符
public static <E> void swap(List<E> list, int i, int j) { list.set(i, list.set(j, list.get(i))); }
// 这种方法需要包装上一种写法来运行，因为 List<?>类型不能添加除null之外的元素
public static void swap(List<?> list, int i, int j); 



/****                                               类的泛型                                        ****/

import java.util.*;
public class RandomList<T> {      //  参数里面带尖括号的类型，可以有多个，如 RandomList<A, B, C>
	private ArrayList<T> storage = new ArrayList<T>();   //  使用了已有的类型做内部存储，已有集合也用泛型T表示
	private Random rand = new Random(47);
	public void add(T item) { storage.add(item); }      //  入参是泛型，类型用T表示
	public T select() {                                 //  返回值是泛型，类型用T表示
		return storage.get(rand.nextInt(storage.size()));
	}
}

/****                                               接口的泛型                                        ****/
class Coffee {
	public static long counter = 0;
	public final long id = counter++;
	public String toString() {
		return "Coffee<" + id + ">";
	}
}
interface Generator<T> { T next(); }        //  生成器的接口定义，不能少
import java.util.*;
public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee> {  //  实现生成器接口 和 迭代接口（给foreach用）
	private Class type = Coffee.class;         //   获得类的绝对路径名，如“class com.zxf.comp.Coffee”，可用之创建对象，见后文
	public CoffeeGenerator() {};            //  默认构造函数，用于创建默认对象，调用next() 获得Coffee元素
	public Coffee next() {                  //  普通实现，通过前述构造器得到生成器对象后，调用 next() 不断获得元素
		try {  return (Coffee)type.newInstance();  }
		catch(Exception e){	throw new RuntimeException(e); }
	}
	/* 适合 foreach 的迭代模式实现，通过实现迭代器  */
	private int size = 0;                         
	public CoffeeGenerator(int sz) { size = sz; }   //  迭代器实现调用此构造器，因为迭代器的元素个数是明确的，否则无限迭代了
	class CoffeeIterator implements Iterator<Coffee> {   //  内部类，用来辅助生成迭代器
		int count = size;
		public boolean hasNext() { return count > 0; }
		public Coffee next() {
			count--;
			return CoffeeGenerator.this.next();       //  调用当前生成迭代器的CoffeeGenerator对象的本身的next() 函数
		}
		public void remove() { throw new UnsupportedOperationException(); }
	};
	public Iterator<Coffee> iterator() { return new CoffeeIterator(); }
}
public static void main(String[] args) {
	CoffeeGenerator cg = new CoffeeGenerator();
	System.out.println(cg.next());    //  生成器的用法
	for(Coffee cc : new CoffeeGenerator(10)) {  System.out.println(cc); }   // 迭代器的用法
}

/****                                               泛型方法                                        ****/
使用泛型方法时，对应类生成时不必指定参数类型，编译器会为我们找出具体类型。称之为类型参数推断。
public class GenericMethods {   //  类并不是参数化的，不带 <T>
	public <T> void f(T x) {            //  将泛型参数列表置于返回值之前
		System.out.println(x.getClass().getName());
	}
}
public class GenericVarargs {
	public static <T> List<T> makelist(T... args) {   //  泛型方法配合可变参数，此处参数类型都是T
		List<T> result = new ArrayList<T>();
		for(T item: args)
			result.add(item);
		return result;
	}
}

/****                                               通配符（向上/向下转型）                                        ****/
class Fruit {}
class Apple extends Fruit {}
class Jonathan extends Apple {}
class Orange extends Fruit {}

/**   以下是客户代码，即位于main函数中，如果没有对基本的泛型类进行包装，则只能取不能放，因为泛型的类型判断是在编译阶段，
       运行阶段类型已擦除，放的时候不确定是不是放的正确类型，因此在编译阶段卡死
**/
List<? extends Fruit> flist = new ArrayList<Apple>();
// flist.add(new Apple());   编译错误
// flist.add(new Fruit());   编译错误，因此只确定 flist存储的是Fruit的某个子类型，但不确定具体是哪个，可能导致类型不匹配
// flist.add(new Object());  编译错误
List<? extends Fruit> flist = Arrays.asList(new Apple());
Apple a = (Apple)flist.get(0);  //  可以 get 元素，但是不能 add 元素，因为添加时不确定元素类型，编译器
flist.contains(new Apple());  //  contains 方法将接收的参数转化为了 Object
flist.indexOf(new Apple());   //  indexOf   方法将接收的参数转化为了 Object

// 往集合里面放的时候，集合可以是父类的集合，将子类元素放入，此时用super
public class GenericWriting {
	static <T> void writeExact(List<T> list, T item) { list.add(item); }  //  无通配符
	static List<Apple> apples = new ArrayList<Apple>();
	static List<Fruit> fruit = new ArrayList<Fruit>();
	static void f1() {
		writeExact(apples, new Apple());
		// writeExact(fruit, new Apple());  编译错误，不兼容的类型，found Fruit, required Apple
	}
	// 在被调用的函数中使用 super，表示接收的第一个参数其类型可以是  T类型的父类的集合；
	//  即 装T通用类型的  集合List 的确定类型可以是 List<T的父类>，用这种方式理解何时用 super
	// 此处 T和对应的集合在同一层，需要T的父类的集合接受具体的T元素
	static <T> void writeWithWildcard(List<? super T> list, T item) { list.add(item); }
	staic void f2() {
		writeWithWildcard(apples, new Apple());
		writeWithWildcard(fruit, new Apple());
	}
}
//  从集合里面取的时候，集合可以是子类的集合，取出来转换成父类元素，此时用extends
public class GenericReading {
	static <T> T readExact(List<T> list) { return list.get(0) }
	static List<Apple> apples = Arrays.asList(new Apple());
	static List<Fruit> fruit = Arrays.asList(new Fruit());
	static void f1() {
		Apple a = readExact(apples);
		Fruit f = readExact(fruit);
		f = readExact(apples);      //  正常的泛型方法可以正确运行，取出来的Apple类型会进行类型转换
		
	static class Reader<T> {    //  定义了一个泛型类，该类拥有一个名为 readExact 的泛型方法
		T readExact(List<T> list) { return list.get(0); }  
	}
	static void f2() {
		Reader<Fruit> fruitReader = new Reader<Fruit>();
		Fruit f = fruitReader.readExact(fruit);
		//  由于类类型已经定义了本类只能处理T这一个类型，因此对它的扩大/缩写类范围操作皆不可行
		// Fruit a = fruitReader.readExact(apples);  编译错误，List<Fruit> 类型不能应用于 List<Apple> 类型
		
	static class CovariantReader<T> {  //  定义了一个泛型类，该类拥有一个名为 readExact 的泛型方法，但方法入参进行了调整
		T readCovariant(List<? extends T> list) { return list.get(0); } // 方法入参接受所有T的子类型作为参数
		//  此处方法的入参 List 与 T 不在同一层，意思是接受 T的子类的集合 作为入参，返回T类型的参数
	}
	static void f3() {
		CovariantReader<Fruit> fruitReader = new CovariantReader<Fruit>();
		Fruit f = fruitReader.readCovariant(fruit);
		Fruit a = fruitReader.readCovariant(apples);
	}
	
	
/****                                               反射                                        ****/
类方法提取器
import java.lang.reflect.*;
import java.util.regex.*;
public class ShowMethods {
	/* 使用 java ShowMethods className 提取类的所有方法和构造函数  或者 
	   使用 java ShowMethods className methodName 提取类中包含方法名methodName的函数  */
	private static String usage = "Usage:\n" +
								  "ShowMethods qualified.class.name\n" +
								  "To show all methods in class or:\n" +
								  "ShowMethods qualified.class.name word\n" +
								  "To search for methods involving 'word'";
	/* 表示匹配所有 . 之前有多个单词类型(w+)的模式 */
	private static Pattern p = Pattern.compile("\\w+\\."); 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length < 1) {
			System.out.print(usage);
			System.exit(0);
		}
		int lines = 0;
		try{
			Class<?> c = Class.forName(args[0]);  /* 先通过类名获得类引用 */
			Method[] methods = c.getMethods();    // 获得该类的所有方法 
			Constructor[] ctors = c.getConstructors();  //  获得该类的所有构造函数
			
			if(args.length == 1) {
				for(Method m : methods) {
					/* 返回 public static void ShowMethods.main(java.lang.String[]) */
					Print.print(m.toString());
					/* 返回 public static void main(String[]) */
					/* 注意此处正则表达式查找到所有.之前的单词之后，调用replaceAll("")将w全部删除了 */
					Print.print(p.matcher(m.toString()).replaceAll(""));
				}
				for(Constructor ct : ctors)
					/* 返回 public ShowMethods()  注意此处仅有一个编译器自动加上的默认构造函数 */
					Print.print(p.matcher(ct.toString()).replaceAll(""));
				
				lines = methods.length + ctors.length;
			}else{
				for(Method m : methods)
					if(m.toString().indexOf(args[1]) != -1) {
						Print.print(p.matcher(m.toString()).replaceAll(""));
						lines++;
					}
				
				for(Constructor ct : ctors) {
					if(c.toString().indexOf(args[1]) != -1) {
						Print.print(p.matcher(ct.toString()).replaceAll(""));
						lines++;
					}
				}
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

动态代理
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

		
/****                                               多线程                                        ****/
1、Java的线程锁是可重入锁，即对象A调用synchronized方法A的时候，在A内部也可以同时调用synchronized方法B，锁计数会加1，如果不可重入，则会造成死锁，同时可重入锁在继承性环境同样成立，即子类synchronized方法A可以调用父类synchronized方法B
2、出现异常时，当前持有的锁会自动释放
3、synchronized 标记不具有继承性，子类中override的方法必须依然加 synchronized 标记
4、数据类型String的常量池特性，synchronized(String)时，需要注意所有字符常量都是同一个对象，要注意锁死误伤，不要用String当锁
5、要避免多次调用锁的死锁问题，如 方法A的synchronizedX块中有synchronizedY块，同时方法B的synchronizedY块中有synchronizedX块
	jdk安装目录下的bin文件，输入“jps”查看当前运行java线程，再输入“jstack -l threadId”查看指定线程状态
6、volatile关键字仅修饰变量，保证变量的可见性，但不保证变量的原子性；但变量的使用并不具有原子性，因此不能带来同步性；
7、关键字synchronized代码块有volatile同步的功能；即保证互斥性和可见性；
8、方法wait()执行后释放锁，notify()执行后不释放锁，而是等到所在synchronized代码块执行完毕退出后释放
9、当线程处于wait()状态时，调用该线程对象的interrupt()方法会出现 InterruptedException 异常
10、使用synchronized的代码里面，传入的锁对象既要当ReentrantLock使用，也要当Condition使用



一般多线程的写法是定义本身处理业务的类 MyTask，然后定义一个 Thread 类的子类，内部包含 MyTask 对象(策略模式)，在 run 函数的 override 中调用 MyTask 对象：
public class MyTask {
	/* 任务类内部实例变量若没有其他地方保证(调用方法保证)，是非线程安全的，因为多个线程共用任务对象  */
	private int notSafe = 5;   

	private Object otherObj = new Object();
	/* 加了synchronized就是线程安全的了，不加notSafe变量值不是线程安全 */
	/* 注意synchronized是用来锁对象的，默认是this，相同的对象才能共享一把锁(对象监控器)，或者锁类也行，但杀伤面太大 */
	synchronized public void changeVar() {
		if(stateA) 
			notSafe = 11;
		else 
			notSafe = 22;
	}
	
	/* 同变量的设置/获取方法需要用同一个锁同时锁住，否则set过程中切换CPU资源后，可能出现脏读 */
	synchronized public void setVar(int a) {};
	synchronized public int  getVar() {};
	
	/* 同步代码块，此种用法最重要的是确认不要漏互斥操作 */
	public void partSync() {
		doSomethingOne;
		synchronized(this) {
			doSomethingTwo;
		}
		doSomethingThree;
	}
	
	/* 锁其他对象，这样保证本类中仅锁otherObj对象的互斥区是互斥的，锁this或者其他对象的代码依然是异步的，优化提高运行效率  */
	public void otherObjLock() {
		synchronized(otherObj) { doSomethingTwo; }
	}
	
	/* 静态同步synchronized方法，锁的是当前的类，类锁可以对类的所有对象起作用 */
	synchronized public static void staticMethod() { }
	/* 静态同步synchronized代码块，也是锁类 */
	public static void staticBlock() {
		synchronized(MyTask.class) {}
	}
	
	
	/* 非synchronized方法不是独占的，即使一个对象的synchronized方法正在被调用，非synchronized方法也可以同时被调用  */
	public void safeVar() {
		int isSafe = 9;   //  方法内部的变量都是线程安全的，因此每次方法调用在调用栈上实时生成的，每个线程独有
	}
}

/* ReentrantLock 具有完全互斥排他效果，即同一时间只有一个线程在执行ReentrantLock.lock()后面的代码，效率较低下 */
public MyTaskTwo {
	/* 可重入锁，能够进行更精细化的管理，该Lock对象本身就是对象监控器，不像synchronized还要传个参数(哪怕默认参数) */
	private Lock lock;
	private Condition conditionAB;
	private Condition conditionCD;
	
	public MyTaskTwo(boolean isFair) {
		super();
		/* 公平锁表示线程获取锁的顺序是按照线程加锁的顺序来分配的，即FIFO顺序；非公平锁采用抢占机制获取锁，随机获取 */
		/* 不传参数的ReentrantLock默认构造函数是非公平锁 */
		lock = new ReentrantLock(isFair);   // 创建公平锁，传入true表示公平锁，false表示非公平锁
		conditionAB = lock.newCondition();
		conditionCD = lock.newCondition();
	}
	
	public void methodA() {
		try {
			lock.lock();
			doSomething();
			conditionAB.await();   //  相当于 Object.wait();
			// conditionAB.await(long);  相当于 Object.wait(long);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public void methodB() {
		try {
			lock.lock();
			conditionAB.signal();   // 相当于 Object.notify();
			// conditionAB.signalAll();   相当于 Object.notifyAll();
		}finally{
			lock.unlock();
		}
	}
	
	public void methodC() {
		try {
			lock.lock();
			doSomething();
			conditionCD.await();   //  相当于 Object.wait();
			// conditionCD.await(long);  相当于 Object.wait(long);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public void methodB() {  // 可以在客户代码里被调用，用来唤醒等待的线程
		try {
			lock.lock();
			conditionCD.signal();   // 相当于 Object.notify();
			// conditionCD.signalAll();   相当于 Object.notifyAll(); 唤醒所有 await 线程
		}finally{
			lock.unlock();
		}
	}
	
	public void otherMethod() {
		int myCount = 0;
		boolean isTrue = 0;
		
		// 查询当前现成保持此锁定的个数，也就是调用lock()方法的次数
		myCount = lock.getHoldCount();   
		// 获得正等待获取此锁定的线程估计数，如5个线程在await()状态，其中一个获得锁后往下走，此时本函数返回4，有4个线程等待锁
		myCount = lock.getQueueLength();
		// 获得等待相关的给定条件Condition的线程估计数，比如10个线程都在某condition.await()，则返回10
		myCount = lock.getWaitQueueLength(conditionCD);
		
		// 查询指定线程是否正在等待获取此锁，在客户代码调用，此处仅是示例
		isTrue = lock.hasQueuedThread(threadA);
		// 查询是否有线程正在等待与此锁有关的condition条件
		isTrue = lock.hasWaiters(conditionAB);
		// 判断是不是公平锁
		isTrue = lock.isFair();
		// 判断当前线程是否保持该锁锁定状态，即lock()调用后状态
		isTrue = lock.isHeldByCurrentThread();
		// 判断此锁是不是由任意线程保持，即最低有一个线程调用过lock()且还未调用unlock()，则返回true
		isTrue = lock.isLocked();
		// 当前线程未被中断，则获取锁定，如果已经被中断，则出现异常；
		lock.lockInterruptibly(); 
		// 在调用时锁未被其他线程保持的情况下，获取锁
		if(lock.tryLock())
			System.out.println("get it");
		// 锁在给定时间内没被其他线程获取，且当前线程未被中断，获取该锁
		isTrue = lock.tryLock(3, TimeUnit.SECONDS);
		
	}
}
/* 读写锁ReentrantReadWriteLock类，内部有一个读操作相关的锁，也叫共享锁；一个写相关的锁，也叫排他锁  */
public MyTaskTwo {
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	public void read() {
		try {
			//  读锁，读读时可异步访问
			lock.readLock().lock();
			doSomething();
		}finally{
			lock.readLock().unlock();
		}
	}
	
	public void write() {
		try {
			//  写锁，读写互斥，写读互斥，写写互斥；
			lock.writeLock().lock();
			doSomething();
		}finally{
			lock.writeLock().unlock();
		}
	}
}

public class MyThread extends Thread {
	//  线程内部的私有数据，存储在线程独占的内存内，因此是多线程安全的（本线程独有），注意不能是static，每次new要新生成
	private int count = 5;  
	private MyTask task;
	
	/* 每个线程自己的变量，在线程内自己共享，即count是每个MyThread线程对象独有，t1是所有MyThread对象共有  */
	public static ThreadLocal t1 = new ThreadLocal();
	t1.set(anyObj)   // 放入对象
	if (t1.get() != null)
		correspondingType = ti.get();     // 取出对象，类型要用户自己掌控
	
	MyThread(MyTask task) {
		super();
		this.task = task;
	}
	
	@override
	public void run() {
		super.run();
		//  Thread.currentThread() 获取当前语句所在线程的引用，  getName() 获得线程名（一般new同语句块中的setName中设置）
		System.out.println("run方法的打印：" + Thread.currentThread().getName());
		
		Thread.yield();   //  当前现成放弃CPU资源，让给其他排队的线程，当然可能下个线程还是自身，CPU资源被自己抢到
		
		/* 休眠时外部interrupt中止线程，会让线程产生InterruptedException中断，不论休眠调用前后的interrupt  */
		Thread.sleep(50000);    
		/*  通过抛出异常的方法来在代码中中止线程，仅仅在外部调用 mythread.interrupt() 并不必然让线程中止 */
		if( this.interrupted() ) {
			throw new InterruptedException();
		}
		
	}
}	
/* 使得ThreadLocal对象有默认值的方法，即子类继承后重载initialValue()函数，注意需要包装类包装静态ThreadLocalExt对象后使用
   例如 Tools.t1.get() 在main里面调用就是main里面存放的值，在mythread里面调用就是mythread里存放的值 */
public class ThreadLocalExt extends ThreadLocal {
	@override
	protected Object initialValue() { return "I am the default value"; }  //  注意这个方法只能别重载，不能在外部调用
}
/* 让子线程从父线程取得值，注意下面两个方法都是重载已有方法  */
public class InheritableThreadLocalExt extends InheritableThreadLocal {
	@override
	protected Object initialValue() { return new Date().getTime(); }  // 通过本函数取得父线程的值
	@override
	// 通过本函数取得父线程的对象并进一步加工，此处对象是initialValue()的返回值
	protected Object childValue(Object parentValue) { return parentValue + "my add thing"; } 
}

public class Test {
	public static void main(String[] args) {
		MyTask mytask = new MyTask();
		MyThread mythread = new MyThread(mytask);
		mythread.setName("A");   //  设置线程名
		//  mythread.run()     //  直接在main 线程中调用函数，没有启用新线程调用
		
		/* 后台线程里( run()里面 )派生的线程仍然是后台线程，
		   后台线程里的finally字句不会执行，因为程序强行关闭后台线程，不给执行的机会 */
		mythread.setDaemon(true);    //  将当前线程设置为守护线程，当所有非守护线程结束后，守护线程自动结束
		
		mythread.start();     //   将mythread线程放入线程队列，准备执行
		mythread.isAlive();   //   判断mythread线程是否处于活动状态，start() 之前是false，之后是true
		mythread.getId();     //   获取mythread线程的唯一标识
		
		/* 不建议使用线程暂停，因为线程如果执行时占用着互斥资源，则暂停后不释放资源索，该资源无法被其他线程调用
			典型的如println() 之类的I/O资源特别容易被无意识的独占：
			println()内部有锁，子线程正在println()的时候被暂停，其他所有线程都别想再调用println()
			此外，暂停有可能导致对象状态不同步，比如正在修改对象3个状态值的过程中被暂停，此时打印对象三个状态就会不同步
		*/
		mythread.suspend();   //  暂停线程，不建议使用
		Thread.sleep(500);    //    在当前所在线程中休眠，单位是毫秒
		mythread.resume();    //  恢复暂停的线程，不建议使用
		
	/* join在内部使用wait()方法进行等待，synchronized使用“对象监视器”原理做同步，join过程中当前线程被中断，当前线程抛出异常 */
		mythread.join();      //  当前线程无限期阻塞，等待mythread线程销毁（正常或异常都可）
		// 内部使用wait(long)实现，具有释放锁的特点，其他线程可以调用本对象方法，而sleep(long)不释放锁
		mythread.join(1000);  //  当前线程阻塞等待最多1秒，等待到期后需要抢锁，因此是异步的，与其他线程谁先调用不确定
		
		mythread.interrupt();   //  中止线程mythread，但不是立即中止
		/*  测试当前语句所在对象所属线程是否中止，注意 interrupted()是个静态方法 public static boolean interrupted()，
		    因此实际上等同于调用的 Thread.currentThread().interrupted()，
			测试的时候当前语句所在对象，因此此处是对象是main而不是mythread，
			注意：该方法会清除中断状态，因此连续两次调用interrupted()会哪怕第一次是false，第二次也必然是true
		*/
		mythread.interrupted();  
		/*  测试线程对象是否已经中止，此处测试的是mythread，并且没有清除中断状态效果 */
		mythread.isInterrupted();
		
		/* 线程优先级具有继承性，即子线程继承父线程的优先级，除非优先级差别非常大，例如10和1的差距，否则表现不明显  */
		/* 不要在构造器中设置优先级，在run() 方法起始位置设置 */
		Thread.currentThread().setPriority(6);   // 设置当前线程优先级为6（默认是5）
		Thread.currentThread().setPriority(Thread.NORM_PRIORITY + 3);   // 设置当前线程优先级为8，此处使用了库中优先级枚举
		Thread.currentThread().getPriority(6);   //  获取当前线程优先级
		
		/* 获取线程的状态，主要有 NEW RUNNABLE TERMINATED BLOCKED WAITING TIMED_WAITING */
		Thread.currentThread().getState();
		mythread.getState();
	}
}

/***   生产者/消费者    ***/
//  包装MyStack对象的线程的Run()方法中有个死循环在不停的调用push()和pop()方法，否则方法执行一遍就退出了，线程有两种类型，一个不停调用push()，另一种类型不停调用pop()
public class MyStack {            
	private List list = new ArrayList();
	synchronized public void push() {
		try {
			/* 不能用if，必须用while。
			进入后就wait处停止了，若有notify通知了所有wait状态线程都被唤醒，有可能导致异常
			*/
			while(list.size() == 1) { this.wait();}   // 把自身当锁，此处死循环用于等待消费者消费资源
			list.add("anyString=" + Math.random());
			this.notify();   //  notify调用之后，要等到synchronized块执行完才会释放锁
			System.out.println("push=" + list.size());
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	synchronized public String pop() {
		String returnValue = "";
		try {
			/* 不能用if，必须用while。
			进入后就wait处停止了，若有notify通知了所有wait状态线程都被唤醒，list.remove(0)会导致异常
			*/
			while(list.size() == 0) { this.wait(); }   //  此处死循环用于等待生产者生产资源
			returnValue = "" + list.get(0);
			list.remove(0);
			this.notify();  //  notify调用之后，要等到synchronized块执行完才会释放锁
			System.out.println("pop=" + list.size());
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		return returnValuel
	}
}


Executor的使用，注意每个线程的异常都只能自己处理，不能传回到启动该线程的线程（例如main所在线程），要传回来要使用新接口
import java.util.concurrent.*;
public class CachedThreadPool {
	public static void main(String[] args) {
		/* ExecutorService是具有服务生命周期的Executor ，注意此处是静态类创建的，类似工厂方法*/
		/* newCachedThreadPool 一般会创建与所需数量相同的线程 */
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0; i<5; i++) 
			exec.execute(new MyTask());
		/* shutdown() 防止新任务被提交给Executor */
		exec.shutdown();
		
		/* 使用有限的线程来执行所提交的任务，此处限定为5个线程，一次性完成线程分配动作，节省了总的创建线程的高昂开销 */
		ExecutorService execB = Executors.newFixedThreadPool(5);
		for(int i=0; i<5; i++) 
			execB.execute(new MyTask());
		execB.shutdown();
		
		/* 起单线程，顺序只有所有的Task，内部自己维护一个FIFO的任务队列，低要求下的文件系统处理的好选择 */
		ExecutorService execC = Executors.newSingleThreadExecutor();
		for(int i=0; i<5; i++) 
			execC.execute(new MyTask());
		execC.shutdown();
	}
}

从任务中产生返回值， 使用callable 接口代替 runnable 接口
import java.util.concurrent.*;
import java.util.*;
/* 接口中的泛型表明返回值的类型 */
class TaskWithResult implements Callable<String> {
	private int id;
	public TaskWithResult(int id) {
		this.id = id;
	}
	/* 线程调用的 call() 方法是有返回类型的，与接口里指明的泛型一致 */
	public String call() {
		try	{
			Thread.sleep(1000*id);  //  由于get() 的阻塞效果，虽然所有线程一起启动，但是会产生隔一秒依次显示的效果
	/* 第一个线程休眠时间最久，尽管每个线程休眠时间不一，第一个线程的get() 依然会阻塞其他线程执行完毕后的结果打印
		//  Thread.sleep(1000*(10 - id));  
	*/
		// TimeUnit.MILLISECONDS.sleep(1000);   //  Java 1.5/6 的新风格
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		return "result of TaskWithResult " + id;
	}
}
public class CallableDemo {
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		/* Future<T> 用来存储具体的返回结果  */
		ArrayList<Future<String>> results = new ArrayList<Future<String>>();
		
		for(int i=0; i<10; i++) {
			/* submit() 提交并执行一个任务给线程，返回结果会产生Future对象 */
			results.add(exec.submit(new TaskWithResult(i)));
		}
		for(Future<String> fs : results)
			try{
				/* 通过get() 来获取结果， 可以调用isDone() 来检查Future是否完成。直接调用 get()会阻塞，直到结果准备就绪 */
				System.out.println(fs.get());
			}catch(InterruptedException e) {
				e.printStackTrace();
			}catch(ExecutionException e) {
				e.printStackTrace();
			}finally{
				exec.shutdown();      //  关闭线程池，不让添加新任务
			}
	}
}

异构任务并行化实现 及 同构任务的并行分解和结果处理
// 此处实现一个页面渲染程序，在渲染文字和渲染图片之间实现异构并行，对每张图片，实行并行渲染及加载
//  ExecutorCompletionService 比一般的ExecutorService相当于实现了一个缓存结果的BlockingQueue，用于缓存所有任务线程的返回结果
//  感觉ExecutorCompletionService 的具体实现是个装饰器，对返回结果进一步进行了封装处理
public class Renderer {
	private final ExecutorService executor;
	Renderer(ExecutorService executor) { this.executor = executor; }      //  需要外部给传入一个线程池
	void renderPage(charSequence source) {
		List<ImageInfo> info = scanForImageInfo(source);
		//  ExecutorCompletionService在构造函数中创建一个Blockingqueue来保存计算完成的结果
		CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executor);
		for(final ImageInfo imageInfo : info)
			completionService.submit(new Callable<ImageData>() {     // 典型的
											public ImageData call() { return imageInfo.downloadImage(); }
										});
		renderText(source);     //  渲染文字，此处是异构并发
		try{
			for(int t=0, n=info.size(); t<n; t++) {
				Future<ImageData> f = completionService.take(); // 在得出结果之前阻塞，有结果就取出来跑循环，没结果就阻塞等待
				ImageData imageData = f.get();    //  将得到的结果取出
				renderImage(imageData);    // 渲染单张图片，此处是同构并发
			}
		}catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}catch(ExecutionException e) {
			throw launderThrowable(e.getCause());
		}
	}
										
}

构建高效可伸缩的结果缓存
public class Memoizer<A, V> implements Computable<A, V> {
	private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();  // 使用并发HashMap
	private final Computable<A, V> c;
	public Memoizer(Computable<A, V> c) { this.c =c; }
	public V compute(final A arg) throws InterruptedException {
		while(true) {
			Future<V> f = cache.get(arg);        //  获取HashMap中缓存的 Future<V> 值，若有则直接返回，若没有则返回 null
			if(f == null) {
				Callable<V> eval = new Callable<V>() {
										public V call() throws InterruptedException { return c.compute(arg); }
										};
				FutureTask<V> ft = new FutureTask<V>(eval);    //  生成一个带返回结果的Task任务，
				// 看ft任务在缓存中是否存在，存在则返回相关Future<V>，不存在返回 null，关键是查看key(即arg参数)
				f = cache.putIfAbsent(arg, ft);     
				// ft任务不存在，跑线程等出结果，此处调用本来就是外部起了线程在跑，所以用run
				if(f == null) { f = ft; ft.run() }; 
			}
			try{
				return f.get();
			}catch(CancellationException e) {
				cache.remove(arg, f);
			}catch(ExecutionException e) {
				throw launderThrowable(e.getCause());
			}
		}
	}
}


5种同步工具类：阻塞队列(BlockingQueue)、闭锁(latch)、阻塞等待返回值的任务(FutureTask)、信号量(Semaphore)、栅栏(Barrier)

闭锁(latch)的使用，通过等待/开闸的操作，使一个或者多个线程等待一组事件发生。
闭锁状态包括一个计数器，该计数器被初始化为一个正数，表示需要等待的事件数量。
countDown()方法递减计数器，表示有一个事件发生。计数器值非，await()会一直阻塞到计数器为零，或者线程中断或者线程超时
public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
	final CountDownLatch startGate = new CountDownLatch(1);   //  创建等待计数为1的线程
	final CountDownLatch endGate = new CountDownLatch(nThreads); // 创建等待计数为nThreads的线程
	
	for(int i=0; i<nThreads; i++) {
		Thread t = new Thread() {    //  起n个线程实例，每个线程实例运行一个task任务
			public void run() {
				try {
					startGate.await();          //  等待本主线程开闸
					try {
						task.run();            //   开闸后跑主业务
					}finally {
						endGate.countDown();        //  跑完主业务，减掉任务计数，变相通知主线程任务完成
					}
				}catch(InterruptedException ignored){
					
				}
			}
		};
		t.start();            //  启动线程
	}
	long start = System.nanoTime();         //  主线程开闸前记录时间
	startGate.countDown();                  //  主线程开闸，此时所有等待的任务线程从await()状态往下跑
	endGate.await();         //  等待最后一个任务线程开闸，即所有任务线程执行完毕，计数器到零后，往下运行
	long end = System.nanoTime();          //    记录最后一个任务线程执行完毕的时间
	return end - start;                    //  将所有并行任务线程从启动到结束的运行时间作为结果返回
}

阻塞等待返回值的任务(FutureTask)，通过Callable实现，相当于可生成结果的Runnable。
Future.get的行为取决于任务状态，如果任务已经完成，get会立即返回结果，否则get阻塞知道任务完成，或者抛出异常。结果进行跨线程传递
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
//  强制将未检查的Throwable转换为RuntimeException
public static RuntimeException launderThrowable(Throwable t) {
	if(t instanceof RuntimeException)
		return (RuntimeException) t;
	else if(t instanceof Error) 
		throw (Error) t;
	else
		throw new IllegalStateException("Not unchecked", t);
}
public class Preloader {
	private final Future<ProductInfo> future =  //  创建一个任务，该任务返回ProductInfo对象
			new Future<productInfo>(new Callable<ProductInfo> {
				public ProductInfo call() throws DataLoadException {
					return loadProductInfo();
				}
			});
	private final Thread thread = new Thread(future);   //  启用子线程调用该任务
	public void start()	 { thread.start(); }            //  提供start()方法给外部启动线程
	public ProductInfo get() throws DataLoadExceptioin, InterruptedException {
		try {
			return future.get();        //  阻塞等待任务在子线程中完成并返回结果ProductInfo对象
		}catch(ExecutionException e) {
			Throwable cause = e.getCause();
			if(cause instanceof DataLoadException)
				throw (DataLoadException) cause;
			else
				throw launderThrowable(cause);
		}
	}		
}

信号量：计数信号量用来控制同时访问某个特定资源的操作数量，或者同时执行某个指定操作的数量。也可以用来实现某种资源池，或者对容器施加边界
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
public class BoundedHashSet<T> {  // 实现一个有界的阻塞容器，信号量的计数值会初始化成容器容量的最大值
	private final Set<T> set;
	private final Semaphore sem;       //  声明一个信号量变量
	
	public BoundedHashSet(int bound) {
		this.set = Collections.synchronizedSet(new HashSet<T>());   //  装饰模式，将HashSet使用synchronizedSet装饰，支持并发
		sem = new Semaphore(bound);    //  初始化信号量，有bound个信号量资源
	}
	public boolean add(T o) throws InterruptedException {
		sem.acquire();                //  占用一个信号量资源，如果当前没有信号量资源，则阻塞在此
		boolean wasAdded = false;
		try {
			wasAdded = set.add(o);
			return wasAdded;             //  将添加结果状态返回
		}finally {
			if(!wasAdded)
				sem.release();         //  如果添加元素失败，释放信号量资源
		}
	}
	public boolean remove(Object o) {
		boolean wasRemoved = set.remove(o);
		if(wasRemoved) 
			sem.release();
		return wasRemoved;
	}
}

栅栏：类似于闭锁，阻塞一组线程知道某个事件发生。栅栏与闭锁的关键区别在于，所有线程必须同时到达栅栏位置，才能继续执行。闭锁用于等待事件，而栅栏用于等待其他线程。
public class CellularAutomata {
	private final Board mainBoard;
	private final CyclicBarrier barrier;
	private final Worker[] workers;
	/* 要执行的任务，本任务在线程中执行完毕后，在wait()处等待，直到所有线程执行的任务都到达await()之后，所有线程再往下走 */
	private class Worker implements Runnable {
		private final Board board;
		public Worker(Board board) { this.board = board; }
		public void run() {
			while(!board.hasConverged()) {
				for(int x=0; x<board.getMaxX(); x++)
					for(int y=0; y<board.getMaxY(); y++)
						board.setNewValue(x, y, computeValue(x, y);        //  任务的具体工作，根据业务需要实现
				try {
					barrier.await();          //  线程集结等待处
				}catch(InterruptedException e) {
					return;
				}catch(BrokenBarrierException e) {
					return;
				}
			}
		}
	}
	public CellularAutomata(Board board) {
		this.mainBoard = board;
		int count = Runtime.getRuntime().availableProcessors();  //  获取当前Cpu的个数，作为线程数依据(子问题个数)
		/****  生成栅栏，第一个参数是集合的线程数，这样await()才知道是否可以开栏，
		                 第二个参数是栅栏开启后可选执行的子任务线程，用于进行一些收尾工作 ***/
		this.barrier = new CyclicBarrier(count, new Runnable() { public void run() { mainBoard.commitNewValues(); } });
		this.workers = new Worker[count];
		for(int i=0; i<count; i++)
			workers[i] = new Worker(mainBoard.getSubBoard(count, i));  //  初始化子任务集合
	}
	public void start() {
		for(int i=0; i<workers.length; i++)
			new Thread(workers[i].start();       //  启动子任务
		mainBoard.waitForConvergence();
	}
}

/****                                               NIO                                        ****/
NIO中主要Channel的实现：
FileChannel : 从文件中读写数据
DatagramChannel : 通过UDP读写网络中的数据
SocketChannel : 通过TCP读写网络中的数据
ServerSocketChannel : 通过监听新进来的TCP连接，像Web服务器那样，对每个新进来的连接都会创建一个SocketChannel

缓冲区本质上是一块可以写入数据，然后从中读取数据的内存。这块内存被包装成NIO对象，并提供了一组方法，用来方便的访问该块内存。
NIO中主要的Buffer实现：
ByteBuffer        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
CharBuffer        CharBuffer charBuffer = CharBuffer.allocate(1024);  
DoubleBuffer
FloatBuffer
IntBuffer
LongBuffer
ShortBuffer
MappedByteBuffer : 用于表示内存映射文件


// 初始化一个buffer并分配空间
ByteBuffer.allocate(48);
// 通过通道写入Buffer
inChannel.read(ByteBuffer);	
// 将数据从buffer写入通道
int writeSize = inChannel.write(buf);
// 调用put函数写入数据到buffer
buf.put(127);
// 使用get读取数据到本地变量
byte tmp = buf.get();
// 切换当前buffer的读/写模式，写切换到读时，将同时将limit设置成当前position值，position设置为0，
buf.flip();
// 将position设回0，可以重读buffer中的所有数据，limit保持不变
buf.rewind();
// 清空buffer，即将position设回0，同时limit设置成capacity(初始化时分配的大小)
buf.clear();
// 清除已经读过的数据，任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面，limit设置成capacity
buf.compact();
// capacity标记一个特定的position，使用reset()可以恢复到这个position
buf.mark();
buf.reset();
	

/* 示例 */	
public class UseNio {
	public static void useNio() {
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile("./1.txt", "rw");    // 定义文件对象
			FileChannel inChannel = aFile.getChannel();       // 从文件对象上获取通道  
			ByteBuffer byteBuffer = ByteBuffer.allocate(48);  // 创建容量为48字节的buffer
			int result = inChannel.read(byteBuffer);      // 读取48字节的数据，放入buffer
			
			while(result != -1) {
				System.out.println("Read: " + result);
				byteBuffer.flip();                            //  设置buffer切换模式为读模式
				
				while(byteBuffer.hasRemaining()) {
					System.out.println((char)byteBuffer.get());  // 每次读取1字节
				}
				
				/* 清空buffer，准备再次写入，clear()会清空整个缓冲区，compact()只会清空已经读取的部分，并将未读部分放到最开头
				*/
				byteBuffer.clear();  
				result = inChannel.read(byteBuffer);
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			try{
				aFile.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}

scatter/gather 描述从Channel中读取或者写入到Channel的操作。
scatter 在读操作时将读取的数据依次写入多个buffer中，例如将一个消息的消息头和消息体写入不同的buffer
/* 由于每次移动到下一个buffer前，必须填满当前buffer，因此不适用于动态消息(即消息格式大小不固定的包) */
public void scatter() {
	ByteBuffer header = ByteBuffer.allocate(128);
	ByteBuffer body = ByteBuffer.allocate(1024);
	ByteBuffer[] bufferArray = { header, body };
	/* 当一个buffer被写满后，channel紧接着向另一个buffer中写入 */
	channel.read(bufferArray);
}

gatter  在写操作时将多个buffer的数据写入同一个Channel
/* 写入position和limit之间的数据，因此能较好的处理动态消息，即需要编码者自己在各个buffer中放入合适的数据，然后依次写入通道 */
public void gather() {
	ByteBuffer header = ByteBuffer.allocate(128);
	ByteBuffer body = ByteBuffer.allocate(1024);
	// 写入数据到buffer
	ByteBuffer[] bufferArray = { header, body };
	channel.write(bufferArray);
}

通道间数据传输（其中一个必须是文件通道 FileChannel）： 源通道可以是 SocketChannel
public void filePipeline() {
	RandomAccessFile fromFile = new RandomAccessFile("1.txt", "rw");
	FileChannel fromChannel = fromFile.getChannel();
	RandomAccessFile toFile = new RandomAccessFile("2.txt", "rw");
	FileChannel toChannel = toFile.getChannel();
	long position = 0;   //  被写入内容的起始地址
	long count = fromChannel.size();  // 最大传输的字节数
	/* 将数据从源通道传输到FileChannel中，注意此处源是fromChannel，目的是toChannel且必须是文件通道 */
	toChannel.transferFrom(position, count, fromChannel);
	/* 将数据从当前的文件通道传输到目的通道，因此 fromChannel 必须是文件通道 */
	fromChannel.transferTo(position, count, toChannel);
}

Selector 辅助单个线程管理多个通道，从而管理多个网络连接。 
鉴于Channel配合Selector时必须处于非阻塞模式，因此不能与FileChannel一起用
public void runSelector() {
	// 创建 selector
	Selector selector = Selector.open();
	/* 由于多个channel对一个selector，因此channel必须处于非阻塞模式，此处进行设置 */
	channel.configureBlocking(false);
	// 绑定channel，即将channel注册到selector上，第二个参数表示兴趣集合，表示selector对channel的什么感兴趣，
	/* 主要有 Connect/Accept/Read/Write  4种类型的事件，分别表示通道的“连接就绪”，“接收就绪”，“读就绪”，“写就绪” */
	/* Selectionkey.OP_CONNECT, Selectionkey.OP_ACCEPT, Selectionkey.OP_READ, Selectionkey.OP_WRITE  可以用位操作符合并 */
	SelectionKey key = channel.register(selector, Selectionkey.OP_READ);
	
	key.attach(myObject); // 根据业务需要绑定相关对象到key，可选
	
	while(true) {
		/* 返回非0值，表明有对应数量的channel已经就绪 */
		int readyChannels = selector.select();
		if(readyChannels == 0) continue;
		/* 获取所有通道的SelectionKey，用来访问各个通道 */
		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		/* 获取通道的迭代器 */
		Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
		while(keyIterator.hasNext()) {
			selectedKey key = keyIterator.next();
			if(key.isAcceptable()) { 
				SocketChannel channel = (SocketChannel)key.channel();  /* 获取对应的channel，类型需要自己强转 */
				Selector sele = key.selector();  // 反获取selector
			}else if(key.isConnectable()) {
				Object attachedObj = selectionKey.attachment();  // 获取绑定到
			}else if(key.isReadable()) {
				doSomethingC();
			}else if(key.isWritable()) {
				doSomethingD();
			}
			/* selector不糊自己从已选键集中移除SelectionKey实例，需要自己动手移除，下次通道就绪时，会再次将其放入已选择键集 */
			keyIterator.remove();
		}
	}
	// 用完后关闭，使得注册其上的所有SelectionKey无效。相关通道本身不关闭
	selector.close();
}

文件通道 FileChannel 的常用操作
public void fileChannelUsage() {
	RandomAccessFile aFile = new RandomAccessFile("1.txt", "rw");
	FileChannel inChannel = aFile.getChannel();
	ByteBuffer buf = ByteBuffer.allocate(48);
	/* 从channel里面读数据到buf，result表示成功读取的字节数，返回-1表示读到了文件末尾  */
	int result = inChannel.read(buf);
	
	String newData = "a test string";
	ByteBuffer buf = ByteBuffer.allocate(96);
	/* 清空buf缓存 */
	buf.clear();
	/* 将字符串转换成字节放入buffer */
	buf.put(newData.getBytes());
	/* 切换当前读写模式，当前从buf写切换到buf读 */
	buf.flip();
	/* 不确定一次能向channel里面写入多少个字节，因此需要while循环写入 */
	while(buf.hasRemaining()) {
		channel.write(buf);
	}
	/* 获取通道的当前位置 */
	long pos = channel.position();
	/* 设置通道的当前位置 */
	channel.position(pos + 123);
	/* 返回当前通道关联的文件大小 */
	long fileSize = channel.size();
	/* 截取一个文件，指定长度后面的部分将被删除 */
	channel.truncate(1024);
	/* 将通道里尚未写入磁盘的数据强制写到磁盘上 */
	channel.force():
}

套接字通道 SocketChannel 用法
public void socketChannelUsage() {
	/* 打开SocketChannel  */
	SocketChannel sc = SocketChannel.open();
	sc.connect(new InetSocketAddress("http://jenkov.com", 80));
	/* 关闭SocketChannel */
	sc.close();
	/* 读取数据，返回-1表示读到了流的末尾，连接关闭了  */
	ByteBuffer buf = ByteBuffer.allocate(48);
	int result = sc.read(buf);
	/* SocketChannel写模式与文件通道FileChannel一模一样，不再列举 */
	
	
	/* 非阻塞模式，用来在异步模式下调用 connect() read() write() */
	sc.configureBlocking(false);
	sc.connect(new InetSocketAddress("http://jenkov.com", 80));
	/* 非阻塞模式下，可能在连接建立之前就返回了，需要确认连接是否建立 */
	while(!sc.finishConnect()) {
		doSomething();
	}
}

ServerSocketChannel 用法
public void serverSocketChannelUsage() {
	ServerSocketChannel ssc = ServerSocketChannel.open();
	ssc.socket().bind(new InetSocketAddress(9999));
	
	while(true) {
		/* 监听新来的连接，当accept()方法返回的时候，返回一个包含新进来连接的SocketChannel */
		/* 此处会一直阻塞到有新连接到达，通常不会仅仅只坚挺一个连接 */
		SocketChannel sc = ssc.accept();
		doSomething();
	}
	/* 非阻塞模式 */
	ssc.configureBlocking(false);
	while(true) {
		SocketChannel sc = ssc.accept();
		if(sc != null) { doSomething(); }
	}
	ssc.close();
}

DatagramChannel 用法
public void datagramChannelUsage()  {
	DatagramChannel dc = DatagramChannel.open();
	dc.socket().bind(new InetSocketAddress(9999));
	ByteBuffer buf = ByteBuffer.allocate(48);
	buf.clear();
	/* 将接收到的数据包复制到buf，如果buf容不下收到的数据，多出的数据将被丢弃 */
	dc.receive(buf);
	
	String newData = "a test string";
	ByteBuffer buf = ByteBuffer.allocate(96);
	buf.clear();
	buf.put(newData.getBytes());
	buf.flip();
	/* 将一串字符发送到jenkov.com服务器的UDP端口80，无法确认数据包是否已经收到，因为UDP不保证数据到达 */
	int result = dc.send(buf, new InetSocketAddress("jenkov.com", 80));
	/* 由于DCP是无连接的，连接到特定地址并不会像TCP通道那样创建一个连接，而是锁住DatagramChannel，让其只能从特定地址收发数据 */
	dc.connect(new InetSocketAddress("jenkov.com", 80));
	/* 绑定到特定地址后，就可以像传统通道一样操作，只是不保证数据确认到达 */
	int ret = dc.read(buf);
	int ret = dc.write(buf);
}

Pipe用法
public void pipeUsage() {
	Pipe p = Pipe.open();
	/* 向管道写数据，需要访问sink通道 */
	Pipe.SinkChannel sc = p.sink();
	/* 向sinkChannel写入数据，buf中数据来源与前面一样，不重复 */
	while(buf.hasRemaining()) {
		sc.write(buf);
	}
	/* 从管道读数据，需要访问source通道 */
	Pipe.SourceChannel sc = p.source();
	int ret = sc.read(buf);
}

Path类用法
import java.nio.file.Path;
import java.nio.file.Paths;
public void pathUsage() {
	/* 绝对路径 */
	Path path = Paths.get("c:\\data\\myfile.txt");
	/* 相对路径，其绝对路径是 c:\data\projects ，其实就是玩了一个多字符串拼接功能，linux下的. 和 .. 也是能识别的	*/
	Path p1 = Paths.get("c:\\data", "projects");
	/* 清理路径，主要是.和..带来的各种不必要的来回往复 */
	Path p2 = p1.normalize();
}

/****                                               RTTI                                        ****/
辅助接口和类
interface HasBattries {}
interface Waterproof {}
interface Shoots {}
class Toy {
	Toy() {}
	Toy(int i) {}
}
class FancyToy extends Toy implements HasBattries, Waterproof, Shoots {
	FancyToy() { super(1); }
}

不推荐下面这种直接使用Class的方式，推荐使用泛型
static void printInfo(Class cc) {
		/* 获取类名，如FancyToy，判断是否是接口 */
		Print.print("Class name: " + cc.getName() + " is interface? [" + cc.isInterface() + "]");
		/* 获取类名，如FancyToy */
		Print.print("Simple name: " + cc.getSimpleName());
		/* 获取完整路径的类名，如typeinfo.toys.FancyToy */
		Print.print("Canonical name:" + cc.getCanonicalName());
		Print.print("==================separator========================");
	}
	
public static void main(String[] args) {
	Class c = null;
	try{
		/* 获取给定类的Class对象，参数要是类似import的绝对路径，此处FancyToy放在default目录中 */
		/* 另一个更快捷的方式是使用类字面常量  如 FancyToy.class */
		c = Class.forName("FancyToy");
	}catch(ClassNotFoundException e) {
		e.printStackTrace();
	}
	printInfo(c);
	/* 获取该类实现的所有接口 */
	for(Class face: c.getInterfaces())
		printInfo(face);
	/* 获取当前类的直系父类 */
	Class up = c.getSuperclass();
	Object obj = null;
	try{
		/* 获取当前类对象指向类的具体实例，该类必须有默认构造器，否则抛出异常 */
		obj = up.newInstance();
	}catch(InstantiationException e){
		e.printStackTrace();
	}catch(IllegalAccessException e){
		e.printStackTrace();
	}
	/*  获取当前对象的类类型对象 并打印 */
	printInfo(obj.getClass());
}
	
建议采用下面的泛型写法
public static void main(String[] args) throws InstantiationException, IllegalAccessException {
	// 采用泛型的类引用类型存储具体类型
	Class<FancyToy> ftClass = FancyToy.class;
	//  ftClass = int.class  编译不过，因为已经用泛型限定了类型
	FancyToy fancyToy = ftClass.newInstance();
	// 存储FancyToy的超类的类引用，注意只是说明是超类，没说是哪个具体的超类，因此第二种写法编译不过
	Class<? super FancyToy> up = ftClass.getSuperclass();
	//Class<Toy> up2 = ftClass.getSuperclass();
	/* 因为声明的不是一种具体的类型，因此得到的变量也只能用Object类型表示 */
	Object obj = up.newInstance();
	
	Toy t = new FancyToy();
	Class<FancyToy> fancyToyType = FancyToy.class;
	/* cast() 方法将入参转型为类引用的类型，将Toy变量存储的FancyToy对象转换成完全匹配的调用的类引用的类型 */
	FancyToy ft = fancyToyType.cast(t);
	/*  效果等于上一句 */
	ft = (FancyToy)t;
	
	/* Class<?> 等价于原有的Class，没有类型声明因而没有约束，可以存储任何的类型，因此被推荐放弃使用 */
	Class<?> intClass = int.class;
	intClass = double.class;
}
	
	
/*  采用泛型的列表自动生成器的例子 */
class CounterInteger {
	private static long counter;
	/* 注意，此处id不能设置成static，否则所有的id值将变成0 */
	private final long id = counter++;
	public String toString() { return Long.toString(id); }
}
public class FilledList<T> {
	/* 此处表示存储一个类型T的Class类型的类引用变量  */
	private Class<T> type;
	/* 使用类引用类型做入参 */
	public FilledList(Class<T> type) { this.type = type; }
	
	public List<T> create(int nElements) {
		List<T> result = new ArrayList<T>();
		try{
			for(int i=0; i<nElements; i++) {
				/* T的Class类型类引用变量上，调用newInstance()，得到T的默认构造函数创建的实例变量 */
				T tmp = type.newInstance();
				result.add(tmp);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static void main(String[] args) {
		FilledList<CounterInteger> fl = new FilledList<CounterInteger>(CounterInteger.class);
		Print.print(fl.create(15));  // 得到 [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]
	}
}

/* 判断x是否是 Dog类型 或者 Dog类型的子类型 的实例，*/
if(x instanceof Dog) { ((Dog)x).bark(); }


/****                                      集合的使用                                       ****/
Iterator 和 ListIterator 的差异
1).Iterator只能单向移动，ListIterator可以双向移动；
2).ListIterator可以删除、替换或添加元素，而Iterator只能删除元素；
3).ListIterator可以返回当前（调用next()或previous()返回的）元素的索引，而Iterator不能。

Iterator 和 Enumeration 的差异
1).Iterator允许移除从底层集合的元素
2).Iterator的方法名是标准化的

/* 集合的基本使用 */
List<Integer> list = new ArrayList<Integer>(); 
/* 像List中添加元素 */
list.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10););
/* 注意迭代器的主要三个方法 .hasNext() .next() .remove()    
   Java 8 增加forEachRemaining方法，它可以实现对余下的所有元素执行指定的操作 */
for(Iterator<Integer> iter = list.iterator(); iter.hasNext();)
{
	int i = iter.next(); // 自动拆箱
	if(i == 3) iter.remove();  // 注意，此处使用迭代器移除当前元素，不能使用list.remove(i)，会破坏list当前结构，迭代抛出异常
}
System.out.println(list);  // 得到 [1, 2, 4, 5, 6, 7, 8, 9, 10]

ListIterator 是一个Iterator的子类型，只能用于List类的访问，
public static void main(String[] args) {
	List<String> numArray = new ArrayList<String>(Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight"));
	/* 获取listIterator的迭代器 */
	ListIterator<String> it = numArray.listIterator();
	/*
		zero,	0-1
		one,	1-2
		two,	2-3
		three,	3-4
		four,	4-5
		five,	5-6
		six,	6-7
		seven,	7-8
		eight,	8-9
	*/
	// 调用 next() 之后，it就进入输出元素后面的坑里，previousIndex()站输出元素，nextIndex()站坑后面的元素
	while(it.hasNext()) {
		System.out.println(it.next() + ",\t" + it.previousIndex() + "-" + it.nextIndex());
	}
	/*
		eight,	7-8
		seven,	6-7
		six,	5-6
		five,	4-5
		four,	3-4
		three,	2-3
		two,	1-2
		one,	0-1
		zero,	-1-0
	*/
	// 调用 previous() 之后，it就进入输出元素前面的坑里，previousIndex()站坑前面的元素，nextIndex()站输出元素
	while(it.hasPrevious()) {
		System.out.println(it.previous() + ",\t" + it.previousIndex() + "-" + it.nextIndex());
	}
	/* 放到指定下标的元素之前 */
	it = numArray.listIterator(3);
	/* [zero, one, two, NULL, NULL, NULL, NULL, NULL, NULL]，
		站下标3的元素前面的坑，然后根据输出元素所占位置修改元素，调用previous()同理 */
	while(it.hasNext()) {
		it.next();
		it.set("NULL");
	}
	System.out.println(numArray);
}

IdentityHashMap 和 HashMap 的差异
IdentityHashMap是Map接口的实现。不同于HashMap的，这里采用参考平等。
1)在HashMap中如果两个元素是相等的，则key1.equals(key2)
2)在IdentityHashMap中如果两个元素是相等的，则key1 == key2

/****                                      字符串                                       ****/
StringBuilder的一般用法，本类是JSE5引入，之前是 StringBuffer，StringBuffer是线程安全的，因此开销更大
import java.util.*;
public class UsingStringBuilder {
	public static Random rand = new Random(47);
	public String toString() {
		StringBuilder ret = new StringBuilder("[");
		for(int i=0; i<25; i++) {
			/* 通过 append()添加内容，注意此处如果将两个具体合并，编译器内部可能生成一个临时的StringBuilder执行拼接动作 */
			ret.append(rand.nextInt(100));
			ret.append(", ");
		}
		ret.delete(ret.length()-2, ret.length());
		ret.append("]");
		// ret.reverse()   在StringBuilder上原地将字符串翻转
		// ret.substring(10) 将ret上第10个字符之后的字符串作为返回值返回，ret原有内容不变
		return ret.toString();
	}
	public static void main(String[] args) {
		UsingStringBuilder usb = new UsingStringBuilder();
		System.out.println(usb);
	}
}
无意识的递归
public class InfiniteRecursion {
	public String toString() {
		/* 正确用法，调用 super.toString()   */
		return " InfiniteRecursion address" + super.toString() + "\n";
		/* 错误用法， this发现要拼接字符串是，会调用所指向类的toString()，此时就会出现死循环的调用了 */
		//return " InfiniteRecursion address" + this + "\n";
	}
}
正则表达式  Java中 \\转义一个\字符，如果\要配合其他形式，则需要诸如 \\d 这种形式
字符串支持正则表达式的三个函数  matches()  split()  replace()
"-1234".matches("-?\\d+");  // 字符串 matches() 函数自带正则功能，此处匹配 可能的 - 字符和一个以上的数字
s.split("\\W+");  // 以所有的非单词分隔
s.replaceFirst("f\\w", "located");  // 将f开头的第一个单词替换成 located
s.replaceAll("shrubbery|tree|herring", "banana")  // 将所有出现过的 shrubbery tree herring 都替换成 banana


Scanner对象，用来进行扫描输入，一般说的交互式输入，或者从文本中每次读一行并进行处理的操作，需结合文件IO类操作
Scanner in = new Scanner(FileChannel.input);
// 所有的读取都是Scanner自己给我们匹配
in.nextLine();   // 读取下一行    
in.nextInt();   // 读取下一个整数
in.nextDouble();  // 读取下一个浮点数

Scanner sc = new Scanner("12, 42, 78, 99, 42");
sc.useDelimiter("\\s*,\\s*");   //  设置定界符，也就是分隔符，此处是匹配任意空白中间有,的情况
while(sc.hasNextInt())
	System.out.println(scanner.nextInt());   得到  12 42 79 99 42

/****                                      数组                                       ****/
多维数组
int[][] a = {{1, 2, 3}, {4, 5, 6}};
System.out.println(Arrays.deepToString(a));   // 将多维数组转换为String

int[][][] a = new int[2][3][4];
int[][][] a = new int[rand.nextInt(7)][][];
for(int i=0; i<a.length; i++) {
	a[i] = new int[rand.nextInt(5)][];
	for(int j=0; j<a[i].length; j++) 
		a[i][j] = new int[rand.nextInt(5)];
}

int size = 6;
boolean[] a1 = new boolean[size];
Arrays.fill(a1, true);    // 使用 true填充整个数组
String[] a2 = new String[size];
Arrays.fill(a2, 3, 5, "hello");  // 填充3和4两个位置为 hello

