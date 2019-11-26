
// JDK1.7引入的用下划线_分割数字的写法，这些下划线不产生实质作用，仅仅用于增加数字可读性
public static final double ELECTRON_MASS = 9.109_383_56;


Set对每个值都只保存一个对象
Map允许将某些对象与其他一些对象关联起来形成关联数组

Arrays.asList接受一个数组或是一个用逗号分隔的元素列表
逗号分隔的元素列表：
Arrays.asList("second", "third", "forth", "fifth");

数组：
Arrays.asList( new int[]{1,2,3,4,5} );
int[] realArray = new int[]{5,6,7,8,9};
Arrays.asList(realArray);

// 创建一个newLength大小的新数组，使用System.arraycopy()将原original数组的中的内容都拷贝到新数组，
// 新数组容量更大时多的部分填null，新数组更小时只截取满足容量的前部
// 基本数据类型和类型不变的情况，不用指定第三个参数
Arrays.copyOf(U[] original, int newLength, Class<? extends T[]> newType)
使用:
Object[] elements = new Object[10];
elements = Arrays.copyOf(elements, 2*10);

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


字符串首字母大写写法
public static String captureName(String name) {
	char[] cs = name.toCharArray();
	cs[0] -= 32;
	return String.valueOf(cs);
}


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

PECS原则：Producer-extends, Consumer-super，即参数作为生产者给本类供给内容时使用extends扩展，作为消费者消费本类的元素时作为super扩展
返回类型不要使用PECS原则进行扩展，该返回什么类型就返回什么类型，否则等于强制使用代码也使用通配类型
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
// JDK1.8之后提升的lambda表达式写法，与上面的写法效果完全一致
private static UnaryFunction<Object> IDENTITY_FUNCTION = (t) -> t;

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
// 这种方法需要包装上一种写法来运行，因为 List<?>类型不能添加除null之外的元素，及仅list.add(null)不会报编译错误
// 此处的情况使用?的方法更好，因为他接受任何List类型，选取原则：如果类型在一个方法声明中仅出现一次，使用?替换那个类型E
public static void swap(List<?> list, int i, int j); 

实际实现：
public Swap {
	// 对外接口简洁，不需要根据每个具体的E声明一个接口，使用此处唯一对外通配的接口给用户代码使用
	public static void swap(List<?> list, int i, int j) {
		// list.set(i, list.set(j, list.get(i)));  报错，因为List<?>类型只能放null元素，放入其他元素都报错
		swapHelper(list, i, j);
	}
	
	// 内部封装了泛型E的方法类做具体实现，克服List<?>元素放入的限制
	private static <E> void swapHelper(List<E> list, int i, int j) {
		list.set(i, list.set(j, list.get(i)));
	}
}

// 类型安全的多态容器，这类容器不能传入泛型类型，因为不存在List<String>.class和List<Integer>.class的写法，仅有List.class，而该类型不建议使用
public class Favorites {
	// Map中的key是通配的，此处Class<?>编译时指代各种具体的Class<T>
	// Map的value都是Object，丢失了值的具体类型信息，但通过与键的关联找回
	private Map<Class<?>, Object> favorites = new HashMap<>();

	public <T> void putFavorite(Class<T> type, T instance) {
		// 此处的type.cast动态类型转换保证运行时类型安全，保证在外部用户代码错误使用时第一时间抛异常
		favorites.put(Objects.requireNonNull(type), type.cast(instance));
	}

	public <T> T getFavorite(Class<T> type) {
		// Map中存储的是Object类型，所以此处要做动态类型转换
		return type.cast(favorites.get(type));
	}
}

// 对Class<?>类型的数据，需要在参数为Class<? extends AAA>函数中使用的用法举例：
// 使用asSubclass()方法将参数转为AAA子类而不产生编译告警，Class类都有asSubclass方法
// 此处演示用的getAnnotation()方法，查看了下JDK1.8的源码并没有要求Class<? extends Annotation>参数，此处转换纯属多余，仅演示用
static Annotation getAnnotation(AnnotatedElement element, String annotationTypeName) {
	Class<?> annotationType = null;  // 无限制类型标识符
	try{
		annotationType = Class.forName(annotationTypeName);
	}catch (ClassNotFoundException cnfe) {
		throw new IllegalArgumentException(cnfe);
	}
	// 通过asSubclass()方法将Class<?>类型的annotationType转化为Class<? extends Annotation>的结果返回
	// 仅在annotationType确实是Annotation的子类时转换成功，否则抛出ClassCastException异常
	return element.getAnnotation(annotationType.asSubclass(Annotation.class));
}


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
/* 泛型与不定参数 */ 
public <T> void printMsg(T... args) {
	for(T t : args) {
	    System.out.println("t = " + t);
	}
}
printMsg("111", 222, "aaaa", "2323.4", 55.55);  // 此处可正常运行并输出结果

// 类中的泛型方法
class GenerateTest<T>{
	//在泛型类中声明了一个泛型方法，使用泛型T，注意这个T是一种全新的类型，可以与泛型类中声明的T不是同一种类型。
    public <T> void show_2(T t){
        System.out.println(t.toString());
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

基本使用：
public class ReflectServiceImpl {
	public void sayHello(String name) {
		System.out.println("Hello " + name);
	}
}
public class ReflectServiceImpl2 {
	private String name;
	public ReflectServiceImpl2(String name) {
		this.name = name;
	}
	public void sayHello() {
		System.out.println("hello " + name);
	}
}

public class Main {

	/*通过反射获取对象（无参构造函数版）*/
	public ReflectServiceImpl getInstance() {
		ReflectServiceImpl object = null;
		try{
			/*通过newInstance初始化一个类对象，不带参数的初始化方法*/
			object = (ReflectServiceImpl)Class.forName("com.zxf.zxfbatis.simple.ReflectServiceImpl").newInstance();
		}catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			ex.printStackTrace();
		}
		return object;
	}

	/*通过反射获取对象（有参构造函数版）*/
	public ReflectServiceImpl2 getInstance2() {
		ReflectServiceImpl2 object = null;
		try {
			object = (ReflectServiceImpl2)Class.forName("com.zxf.zxfbatis.simple.ReflectServiceImpl2").getConstructor(String.class).newInstance("张三");
		}catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
			ex.printStackTrace();
		}
		return object;
	}

	/*反射方法： 已有对象不确定类时的调用方法，static仅是为了main调用方便*/
	public static Object reflectMethod() {
		Object returnObj = null;
		ReflectServiceImpl target = new ReflectServiceImpl();
		try{
			Method method = ReflectServiceImpl.class.getMethod("sayHello", String.class);
			returnObj = method.invoke(target, "张三");
		}catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			ex.printStackTrace();
		}
		return returnObj;
	}

	/*反射方法： 没有对象但确定类时的调用方法，static仅是为了main调用方便*/
	public static Object reflect() {
		ReflectServiceImpl object = null;
		try {
			object = (ReflectServiceImpl)Class.forName("com.zxf.zxfbatis.simple.ReflectServiceImpl").newInstance();
			Method method = object.getClass().getMethod("sayHello", String.class);
			method.invoke(object, "张三");
		}catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException ex) {
			ex.printStackTrace();
		}
		return object;
	}

	public static void main(String[] argv) {
		reflectMethod();
		reflect();
	}
}



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

/****                                              JAVA网络编程                                       ****/
CookieManager manager = new CookieManager();       //  1.6实现了一个CookieHandler的默认子类
manager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);          //  设置cookie只接受第一坊cookie
CookieHandler.setDefault(manager);


/****                                              JAVA系统属性                                       ****/
http.keepAlive                       //   [true/false]    启用/禁用HTTP Keep-Alive
http.maxConnections                  //   [5]    希望同时保持打开的socket数，默认是5
http.keepAlive.remainingData         //   [false]   在丢弃链接后完成清理
sun.net.http.errorstream.enableBuffering  //   [false]   尝试缓冲400和500级响应的相对小的错误刘，从而能释放链接，以备稍后使用
sun.net.http.errorstream.bufferSize       //   [4096]    为缓冲错误流使用的字节数
sun.net.http.errorstream.timeout     //   [300]     读错误流超时前的毫秒数，默认为300毫秒


//  Map类型快速初始化的代码
private static final Map<String, String> templdateMap = new ConcurrentHashMap<String, String>()
{
	{
		put("1000", "尊敬的用户，您正在注册账号，验证码为：%s，10分钟内有效，请勿告诉他人。");
		put("1001", "尊敬的用户，您正在操作设置资金密码，验证码为：%s，10分钟内有效，请勿告诉他人。");
		put("1002", "尊敬的用户，您正在操作重置登录密码，验证码为：%s，10分钟内有效，请勿告诉他人。");
		put("1003", "您的短信验证码为:%s");
		put("1004", "有人给你下单了,帐号%s上,下了%s元,请尽快处理!");
		put("1005", "%s您好，您有新的订单%s，请及时处理订单");
		put("1006", "%s您好，您的订单%s已标记付款，请查询入账并及时处理订单");
	}
};

WeakHashMap实现了Map接口，是HashMap的一种实现，他使用弱引用作为内部数据的存储方案，
WeakHashMap可以作为简单缓存表的解决方案，当系统内存不够的时候，垃圾收集器会自动的清除没有在其他任何地方被引用的键值对。
如果需要用一张很大的HashMap作为缓存表，那么可以考虑使用WeakHashMap，当键值不存在的时候添加到表中，存在即取出其值。
所谓的强引用，即赋值给某个变量，所谓弱引用，即除了在WeakHashMap作为key，没有在其他地方被其他变量引用
public static void main(String[] args) {
	WeakHashMap<AA, People1> weakMap1 = new WeakHashMap<AA, People1>();
	String b = new String("louhang1");
	AA a = new AA(b);
	BB bb = new BB(a);
	People1 p1 = new People1(bb);
	weakMap1.put(p1.getB().getAA(), p1);
	p1.getB().setAA(null);// 去除对象a的强引用
	a = null;// 去除对象a的强引用,并让垃圾收集器回收AA对象在堆中的内存
	System.gc();
	Iterator i = weakMap1.entrySet().iterator();
	while (i.hasNext()) {
	Map.Entry en = (Map.Entry) i.next();
	System.out.println("weakMap:" + en.getKey() + ":" + en.getValue());  // 没有任何输出结果，因为gc后map中弱引用全部清除，没有元素了
	}
}
class AA {
  private String a;
  public AA(String a) {
    this.a = a;
  }
  public String getA() {
    return a;
  }
  public void setA(String a) {
    this.a = a;
  }
}
class BB {
  private AA a;
  public BB(AA a) {
    this.a = a;
  }
  public AA getAA() {
    return a;
  }
  public void setAA(AA a) {
    this.a = a;
  }
}
class People1 {
  private BB b;
  public People1(BB b) {
    this.b = b;
  }
  public BB getB() {
    return b;
  }
  public void setB(BB b) {
    this.b = b;
  }
}


/****                         Serializable序列化与反序列化                         ****/

序列化针对的是对象的状态，具体来说就是成员变量，不针对类方法
a）序列化时，只对对象的状态进行保存，而不管对象的方法；
b）当一个父类实现序列化，子类自动实现序列化，不需要显式实现Serializable接口；
c）当一个对象的实例变量引用其他对象，序列化该对象时也把引用对象进行序列化；
d）并非所有的对象都可以序列化，,至于为什么不可以，有很多原因了,比如：
1.安全方面的原因，比如一个对象拥有private，public等field，
   对于一个要传输的对象，比如写到文件，或者进行rmi传输等等，
   在序列化进行传输的过程中，这个对象的private等域是不受保护的。
2. 资源分配方面的原因，比如socket，thread类，如果可以序列化，进行传输或者保存，也无法对他们进行重新的资源分配

1、transient指定不被序列化的属性
2、writeObject()序列化时定制处理相关的属性，一般这些属性都是transient修饰的，否则同一个变量序列化存储两次(定制和非定制)，意义不大
3、readObject()反序列化时定制恢复相关的属性
4、writeReplace()在writeObject()之前执行，可以进行一些预处理动作
5、readResolve()在readObject()之后执行，对反序列化的对象进一步处理，一般在单例模式时用本地单例替换反序列化生成的对象，达到JVM中唯一单例目的
6、static修饰符修饰的属性不会参与序列化和反序列化，因为是类的属性，不随对象走


Java Serializable序列化功能缺点：
1、无法跨语言，因为Java序列化技术是Java语言内部的私有协议，其他语言不支持；
2、序列化后的码流太大；
3、序列化性能太低；

// 示例1：一般序列化及使用
public static void main(String[] args) throws Exception {
	// Person对象文件路径
	String path = "e:/person.dat";
	// 创建一个Person对象
	Person p1 = new Person("张三", 18);

	// 序列化Person对象
	ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
	out.writeObject(p1);
	out.close();
	
	Person.level = 999; // 修改static属性，反序列化的值也会随之变化为999

	/**
	 * 使用new初始化时调用本构造方法，但是反序列化时不调用本构造方法直接生成，
	 * 此时JVM里存在两个内容一样的Person对象
	 */
	// 反序列化
	ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
	Person p2 = (Person) in.readObject();
	in.close();
	// 打印反序列化的结果
	// 正常处理时age是18，transient标记时age是0，
	// transient标记加writeObject处理但readObject不恢复时age是28
	System.out.println("p2: " + p2);
	// 反序列化后对象的内存地址和原来的地址不同，是深拷贝。此处返回 p1 == p2 is: false
	System.out.println("p1 == p2 is: " + (p1 == p2));
}

public class Person implements Serializable {
	private String name;

	/** transient用于序列化中的诸如密码等隐私字段，
	*   被修饰的变量不会被序列化，不做额外处理时反序列化后值为基本类型默认值，此处age反序列化后得到0
	*/
	private transient int age;
	
	/* 静态变量属于类，也不会参与序列化与反序列化 */
	public static int level = 7;

	public Person() {
		System.out.println("I am constructor");
	}

	public Person(String name, int age) {
		System.out.println("I am constructor, too.");
		this.name = name;
		this.age = age;
	}

	/**
	 * 序列化transient修饰的字段,通过ObjectOutputStream把想要序列化的transient字段写出去。
	 * 访问控制符必须是private，
	 * 一定要先执行out.defaultWriteObject();把其他正常序列化的字段内容写入
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		// 此处单独处理age字段，一般是对敏感数据做加密处理，此处是在值上加10扰乱数据
		out.writeInt(age + 10);
	}

	/**
	 * 反序列化transient修饰的字段，通过ObjectInputStream把transient字段读出来
	 * 访问控制符必须是private，
	 * 一定要先执行in.defaultReadObject();把其他正常序列化的字段内容读出
	 *
	 */
	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		// 单独处理age字段，读取出来后要在加密数据上做解密，此处减去用于扰乱的增量10
		age = in.readInt() - 10;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", age=" + age +
				", level=" + level + "}";
	}
}

// 示例2：单例序列化及使用
public static void main(String[] args) throws IOException,ClassNotFoundException {
	String path = "e:/singleton.dat";
	Singleton singleton = Singleton.getInstance();

	// 序列化Singleton对象
	ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
	out.writeObject(singleton);
	out.close();

	// 反序列化
	ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
	Singleton Singleton2 = (Singleton) in.readObject();
	in.close();

	// 反序列化后对象的内存地址和原来的地址相同。
	System.out.println(singleton == Singleton2);
	
	// 执行结果：
	// writeReplace
	// writeObject
	// readObject
	// readResolve
	// true
}
public class Singleton implements Serializable {
	private static final Singleton INSTANCE = new Singleton();
	private Singleton() {}

	public static Singleton getInstance() {
		return INSTANCE;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		System.out.println("writeObject");
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		System.out.println("readObject");
	}

	/**
	 * writeReplace方法会在writeObject方法之前执行。
	 * ObjectOutputStream会把writeReplace方法返回的对象序列化写出去。
	 * 注意本方法必须是private
	 */
	private Object writeReplace() throws ObjectStreamException {
		System.out.println("writeReplace");
		return INSTANCE;
	}

	/**
	 * readResolve方法会在readObject方法之后执行，可以再次修改readObject方法返回的对象数据。
	 * 注意本方法必须是private
	 */
	private Object readResolve() throws ObjectStreamException {
		System.out.println("readResolve");
		// 本质上是此处用本地的INSTANCE替换了反序列化生成的Singleton对象，这样保证了JVM里面单例的唯一性
		// 因为所有使用该单例的JVM在反序列化之前已经加载单例CLASS，此时单例的实例已经生成
		return INSTANCE;
	}
}

/****                        Comparable和Comparator的区别                        ****/

package java.lang;
public interface Comparable<T> {
    public int compareTo(T o);
}

package java.util;
@FunctionalInterface
public interface Comparator<T> {
    int compare(T o1, T o2);
    boolean equals(Object obj);
	...
}

Comparable<T>
1、内部比较器，由需要比较的类implements该接口并@Override compareTo方法，主要使类有自比较的特性；当然由于泛型T的存在也可以是无关类型的比较，这种使用很少见；
2、一般有大小关系的值类都建议实现，这样可以直接使用集合的排序功能，或者可以作为有序映射TreeMap的键或者有序集合TreeSet的元素直接使用，扩大使用范围；

Comparator<T>
1、外部比较器，实现类一般命名为 待比较实体类ClassName + Comparator， compare(T o1, T o2)方法签名限制了只能在同一个类型上比较，equals可以不覆写沿用公共基类Object的
2、多用于（1）没有实现自比较功能的类 （2）已有比较关系不满足要求的类 （3）特定场景下比较方法需要定制的类
3、典型的策略模式，不浸入被比较的类从而不影响该类的实现，有点C++泛型算法的意思

public class Student implements Comparable<Student>{
	private String name;
	private int age;

	public Student(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "name: " + name + ", age: " + age;
	}

	@Override
	public int hashCode() {
		return name.hashCode() + age*34;
	}

	@Override  // 名字和年纪一样就认为是同一个人
	public boolean equals(Object obj) {
		if(!(obj instanceof Student)) {
			throw new ClassCastException("类型不匹配");
		}
		Student s = (Student)obj;
		return this.name.equals(s.name) && this.age == age;
	}

	@Override // 先按年纪排序，再按名字排序，区分大小写
	public int compareTo(Student o) {
		int num = new Integer(this.age).compareTo(new Integer(o.age));
		if(num == 0) {
			return this.name.compareTo(o.name);
		}else{
			return num;
		}
	}

	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
// 认为Student自带的比较方法不好或者不适用时自定义比较器
public class StudentComparator implements Comparator<Student> {
	@Override  // 先不区分大小写的比较名字，再比较年纪
	public int compare(Student s1, Student s2) {
		int num = s1.getName().compareToIgnoreCase(s2.getName());
		if(num == 0) {
			return s1.getAge() - s2.getAge();
		}else{
			return num;
		}
	}
}
使用：
List<Student> listStudent = Arrays.asList(...);
Collections.sort(listStudent);  // 使用Comparable的比较方法
Arrays.sort(listStudent);  // 同上，进行数组内部的比较重排

Collections.sort(listStudent, new StudentComparator());  // 使用Comparator的比较方法

/****                        枚举Enum                        ****/

任意枚举类型可用的随机获取器，只包含静态属性和静态方法，做成了完全的工具类
public class Enums {
    private static Random rand = new Random(47);
	// 此方法主要对外，指定具体枚举类型后就获取到该类型的随机值
    public static <T extends Enum<T>> T random(Class<T> ec) {
        // 利用所有枚举类都有的getEnumConstants()获得具体枚举的所有实例
        return random(ec.getEnumConstants());
    }
	// 此方法主要对内，应该外部很少会传入一个枚举的全部值。
    public static <T> T random(T[] values) {
        return values[rand.nextInt(values.length)];
    }
}

使用接口管理枚举，使用Food接口将所有的食物归总，然后按食物的细类继续划分各个枚举，在外部可以使用Food存放所有具体食物
public enum Course {
    APPRTIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class),
    COFFEE(Food.Coffee.class);

    private Food[] values;
    private Course(Class<? extends Food> kind) {
        values = kind.getEnumConstants();
    }
    public Food randomSelection() {
        return Enums.random(values);
    }

    public interface Food {
        enum Appetizer implements Food {
            SALAD, SOUP, SPRING_ROLLS;
        }
        enum MainCourse implements Food {
            LASAGNE,BURRITO, PAD_THAI,
            LENTILS, HUMMOUS, VINDALOO;
        }
        enum Dessert implements Food {
            TIRAMISU, GELATO, BLACK_FOREST_CAKE,
            FRUIT, CREME_CARAMEL;
        }
        enum Coffee implements Food {
            BLACK_COFFEE, DECAF_COFFEE, ESPRESSO,
            LATTE, CAPPUCCINO, TEA, HERB_TEA;
        }
    }
    
    public static void main(String[] args) {
        for(int i = 0; i < 5; i++) {
            for(Course course : Course.values()) {
                Food food = course.randomSelection();
                System.out.println(food);
            }
            System.out.println("======");
        }
    }
}

使用接口中带枚举的方式，可以将export到外部的门面接口里面，限定该接口专用的部分枚举的作用范围，比如常用的Facade中带相关枚举

EnumSet和EnumMap使用举例
public enum AlarmPoints {
    STAIR1, STAIR2, LOBBY, OFFICE1, OFFICE2, OFFICE3,
    OFFICE4, BATHROOM, UTILITY, KITCHEN
}
// EnumSet
public class EnumSets {
	// EnumSet中输出元素的次序完全按照对应存储的enum定义时的次序，与元素add的顺序无关
    public static void main(String[] args) {
        EnumSet<AlarmPoints> points = EnumSet.noneOf(AlarmPoints.class);
        points.add(BATHROOM);
        System.out.println(points);
        points.addAll(EnumSet.of(STAIR1, STAIR2, KITCHEN));
        System.out.println(points);
        points = EnumSet.allOf(AlarmPoints.class);
        points.removeAll(EnumSet.of(STAIR1, STAIR2, KITCHEN));
        System.out.println(points);
        points.removeAll(EnumSet.range(OFFICE1, OFFICE4));
        System.out.println(points);
        points = EnumSet.complementOf(points);
        System.out.println(points);
    }
}
// EnumMap
@FunctionalInterface
interface Command { void action(); }
public class EnumMaps {
    public static void main(String[] args) {
		// 所有的枚举类型实例都会在EnumMap作为键存在，只是没有put操作的话，对应的值都为NULL，强行调用返回空指针异常
        EnumMap<AlarmPoints, Command> em = new EnumMap<>(AlarmPoints.class);
        em.put(KITCHEN, () -> System.out.println("Kitchn fire!"));
        em.put(BATHROOM, () -> System.out.println("Bathroom alert!"));
        for(Map.Entry<AlarmPoints, Command> e : em.entrySet()) {
            System.out.print(e.getKey() + ": ");
            e.getValue().action();
        }
    }
}


/*根据花园里所有植物统计各种生命周期的植物有哪些*/	
public class Plant {
	enum LifeCycle {ANNUAL, PERENNIAL, BIENNIAL}

	final String name;
	final LifeCycle lifeCycle;

	Plant(String name, LifeCycle lifeCycle) {
		this.name = name;
		this.lifeCycle = lifeCycle;
	}

	public static void main(String[] args) {
		/* 方案一：生成Map和Set统计结构，使用for循环遍历放入数据 */
		Map<LifeCycle, Set<Plant>> plantsByLifeCycle = new EnumMap<>(Plant.LifeCycle.class);
		for(Plant.LifeCycle lc : Plant.LifeCycle.values()) {
			plantsByLifeCycle.put(lc, new HashSet<>());
		}
		for(Plant p : garden) {
			plantsByLifeCycle.get(p.lifeCycle).addAll(p);
		}
		/* 方案二：使用stream，但此处使用普通Map，效率比较低 */
		Arrays.stream(garden).collect(groupingBy(p -> p.lifeCycle));
		/* 方案三：使用stream并指定使用EnumMap，效率最高 */
		Arrays.stream(garden).collect(groupingBy(p -> p.lifeCycle,
				() -> new EnumMap<>(LifeCycle.class), toSet()));
	}
}

/* 对于多个常量级联的多重数组形式的映射，用EnumMap实现比用多维数组实现更安全易扩展（直接新枚举值，不需要修改数组映射处理的其他代码）
   此处实现一个固液气三态转换的名称映射表 */
public enum Phase {
	SOLID, LIQUID, GAS;

	public enum Transition {
		MELT(SOLID, LIQUID),
		FREEZE(LIQUID, SOLID),
		BOIL(LIQUID, GAS),
		CONDENSE(GAS, LIQUID),
		SUBLIME(SOLID, GAS),
		DEPOSIT(GAS, SOLID);

		private final Phase from;
		private final Phase to;

		Transition(Phase from, Phase to) {
			this.from = from;
			this.to = to;
		}

		private static final Map<Phase, Map<Phase, Transition>> m =
			Stream.of(values()).collect(
				groupingBy(t -> t.from, ()->new EnumMap<>(Phase.class),
						// 此处(x, y)->y无意义，只是为了满足toMap的参数要求
						toMap(t->t.to, t->t, (x, y)->y, ()->new EnumMap<>(Phase.class))));

		public static Transition from(Phase from, Phase to) {
			return m.get(from).get(to);
		}
	}
}



通过为enum定义一个或多个abstract方法让每个enum实例定制自己的行为
public enum ConstantSpecificMethod {
    DATE_TIME {
        String getInfo() {
            return DateFormat.getDateInstance().format(new Date());
        }
    },
    CLASSPATH {
        String getInfo() {
            return System.getenv("CLASSPATH");
        }
    },
    VERSION {
        String getInfo() {
            return System.getProperty("java.version");
        }
    };
    abstract String getInfo();
    public static void main(String[] args) {
        for(ConstantSpecificMethod csm : values()) {
            System.out.println(csm.getInfo());
        }
    }
}
定义普通方法并针对部分枚举实例进行覆写
public enum OverrideConstantSpecific {
    NUT, BOLT,
    WASHER {
        void f() {
            System.out.println("Overridden method");
        }
    };
    void f() {
        System.out.println("default behavior");
    }
    public static void main(String[] args) {
        for(OverrideConstantSpecific ocs : values()) {
            System.out.print(ocs + ": ");
            ocs.f();
        }
    }
}
/* 定义一个计算一周中每日工资的枚举，其中工作日和双休的加班工资计算公式不同
   如果每个枚举值直接附上加班工资计算函数，重复样板太多；
   通过switch函数综合处理则添加新枚举值时会忘记补充该枚举的case；
   此处通过内部枚举抽取共性方法达到消除重复代码同时在添加新枚举值不忘记指定处理函数的效果 */
public enum PayrollDay {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY(PayType.WEEKEND),
    SUNDAY(PayType.WEEKEND);

    private final PayType payType;
    PayrollDay(PayType payType) {
        this.payType = payType;
    }
    PayrollDay() {
        this(PayType.WEEKDAY);
    }

    int pay(int minutesWorked, int payRate) {
        return payType.pay(minutesWorked, payRate);
    }

    private enum PayType {
        WEEKDAY {
            int overtimePay(int minsWorked, int payRate) {
                return minsWorked <= MINS_PER_SHIFT ? 0 :
                        (minsWorked - MINS_PER_SHIFT) * payRate / 2;
            }
        },
        WEEKEND {
            int overtimePay(int minsWorked, int payRate) {
                return minsWorked * payRate / 2;
            }
        };

        abstract int overtimePay(int mins, int payRate);
        private static final int MINS_PER_SHIFT = 8*60;

        int pay(int minsWorked, int payRate) {
            int basePay = minsWorked * payRate;
            return basePay + overtimePay(minsWorked, payRate);
        }
    }
}


/****                        接口Interface                        ****/

接口本质上是表示一个类还能做什么，是除去类的主要特性（定义类之所以为类的核心内容）之外的附加行为特性(Optional Function)
// 接口的基本内容
public interface A {
	/**  接口内部变量是没什么实质意义的功能，反而暴露了一个永远要维护的对外变量，不建议使用  **/
	// 在接口中定义成员变量，所有接口中的数据成员自动用public static final修饰，所以必须初始化，本质上都是常量而不是变量
	String str = "Hello";
	int FEBRUARY = 2;
	// 因为成员变量都是static，所以只会在加载时初始化，后续不会再变化，此处VAL_A值在加载后永不改变直到JVM重新运行
	int VAL_A = (int)(Math.random() * 10);

	// 最原始的接口，所有方法都是虚的，没有实现，JDK1.8之前全部是public abstract
	void X();

	// 默认方法，JDK1.8加入，只能具体实例对象调用，不允许定义与Object对象的equals、hashCode和toString方法同样签名的默认方法
	default String defaultMethod(String aa) {
		System.out.println("default method in interface after JDK1.8");
		return "ok";
	}

	// 默认方法，JDK1.8加入，只能所在的接口调用
	static void staticMethod(String bb) {
		System.out.println("static method in interface after JDK1.8, get: " + bb);
	}
}

// 接口继承和内部接口，待补充
public interface B extends A, D {
	String str = "World";  // 会屏蔽接口A里面的同名变量
	interface C {
		static void methodOfC() {
			System.out.println("Method of interface C");
		}
	}
}

public static void main(String[] args) {
	System.out.println(A.FEBRUARY * 11);
	System.out.println(A.str);
	for(int i = 0; i < 10; i++) {
		System.out.println(A.VAL_A);
	}
	A.staticMethod("call");
	System.out.println(B.str);
	B.C.methodOfC();
	// B.staticMethod("call");  // 不能使用子接口调用父接口的静态方法

}


/****                        内部类                        ****/

总共四种内部类：
1、静态成员类 static member class
2、非静态成员类  nonstatic member class
3、匿名类    anonymous class  // 匿名类里面的this指向匿名对象，而lambda表达式中的this指向包裹lambda的对象
4、局部类    local class

外部类的所有成员和函数（包括私有的）都对内部类可见，内部类一般用做外部类的辅助类
如果内部类不需要操作外部类的属性，一般建议使用静态成员类，不建议使用非静态成员类，

【【【因为非静态成员类的实例与外部实例绑定，可以获得外部类的引用，操作不当可能导致GC无法回收内存导致内存泄漏】】】
即在内部类（不管匿名与否）里面永远有个隐藏的域OuterClass.this 指向包裹内部类对象的外部实例
解决方法：
1、将内部类定义为static
2、用static的变量引用匿名内部类的实例或将匿名内部类的实例化操作放到外部类的静态方法中
所有的静态上下文，都不会持有外部对象的this隐式引用，毕竟静态对象/方法是可以通过类直接调用的，这时候都不存在外部类对象

// 非静态成员类的初始化
public class A {
	public class B {
	}
}
A a = new A();
A.B ab = a.new B();  // 必须使用外部类的实例来new一个内部类实例，因为非静态内部类实例有归属问题



/* 匿名内部类引用外部对象的示例及分析 */
public class Test {
	interface Action {
		void action();
	}
	class Speaker {
		void handleAction(Action action) {
			action.action();
		}
	}

	public void report() {
		System.out.println("I'm invoked!");
	}

	public void perform() {
		/* 此处new Action()是匿名内部类，在编译的时候会给他一个诸如Test$1的类名
           一般来说匿名内部类的生成语句会有{}，
		   此处new Action(){...} 是在Test对象的作用域里创建的，所以它的外部类是Test	*/
		new Speaker().handleAction(new Action() {
			@Override
			public void action() {
				/* 打印匿名内部类的类名 */
				System.out.println(this.getClass());
				/* 打印匿名内部类对外部对象的引用 */
				System.out.println(Test.this);
				/* 通过匿名内部类对外部对象的引用调用外部方法 */
				Test.this.report();
				/* 直接调用外部方法 */
				report();
			}
		});
	}
	/*  获得匿名内部类对象引用 */
	public Action getAction() {
		return new Action() {
			@Override
			public void action() {
				/* 通过匿名内部类对象调用外部类的实例方法 */
				Test.this.report();
			}
		};
	}

	public static void main(String[] args) {
		Test t = new Test();
		t.perform();
		t.getAction().action();  // 通过内部类对象调用外部对象的实例方法
	}
}

匿名内部类也有构造器，而且和普通类的构造器有点不一样，编译的时候会在匿名内部类的构造器的参数列表之前再插入一个参数，这个参数是外部类的对象的引用，编译之后这个类长这样
Test$1 implements Action {
    final T this$0;
    Test$1(T this$0){
        this.this$0 = this$0;
    }
    
    @Override
    public void action() {
        this$0.report();
    }
}

new Action(){...}实际上是创建了Test$1，并且通过构造器把test对象引用传给Test$1
public void perform(){
	new Speaker().handleAction(new Test$1(this));
}
所以匿名内部类持有外部类的引用(通过Test.this)，且可以调用外部类的方法(通过Test.this.report())




public class Outside() {
	public class Inside() {
		// 非静态成员类中通过内部类获取外部类实例对象
		public Outside getOutside() {
			return Outside.this;
		}
	}
}
Outside o = new Outside();
o.new Inside();   // 创建非静态成员类对象的一种方式，可行，不推荐


/*********************************          Optional          *****************************************/


语义上的作用：定义一个变量类型，该类型表示变量包含一个可能的封装类型结果，也可能不包含。
对物理世界与代码的认知提升：通过类型系统让域模型中隐藏的知识显示的体现在代码中，比如有些域变量在业务上可能为空，有些不允许为空

注意：Optional没有实现序列化，所以如果作为修饰类型应用到的域变量可能会被序列化，可能引发程序故障

Optional的使用： 该对象相当于一个值的容器，是一个容器对象
Optional<String> a = Optional.of("a");    // 通过of工厂方法包装对象创建一个Optional实例
assertEquals("a", a.get());            //  可以通过get方法获取容器中的值

例如：
public class Person {
	/* 一个人可能拥有一辆车，也可能没有，实际代码效果可能等同于null的场景，
	   但是避免了NullPointerException异常并将这种不存在的可能体现在了代码中  */
	private Optional<Car> car;   
	public Optional<Car> getCar() { return car; }
}

public class Car {
	private Optional<Insurance> insurance;
	public Optional<Insurance> getInsurance() { return insurance; }
}

public class Insurance {
	private String name;
	public String getName() { return name; }
}

生成Optional对象：
【empty】返回一个空的 Optional 实例
Optional<Car> optCar = Optional.empty();


【of】将指定值用Optional封装之后返回，如果该值为 null ，则抛出一个NullPointerException异常
Optional<Car> optCar = Optional.of(car);


【ofNullable】将指定值用 Optional 封装之后返回，如果该值为 null ，则返回一个空的 Optional 对象
Car car = null;
Optional<Car> optCar = Optional.ofNullable(car);  // ofNullable工厂方法允许创建一个空值对象


处理Optional对象：
【map】如果值存在，就对该值执行提供的 mapping函数调用，如果值为null，就什么都不做直接返回Optional.empty()
Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
Optional<String> name = optInsurance.map(Insurance::getName);  // 使用map从Optional中提取和转换值，注意接收者类型也是Optional


【flatMap】
对于一个Optional的对象，如果包装的值存在，就对该值执行提供的mapping函数调用，返回一个Optional类型的值，
否则直接就返回一个空的Optional对象，不执行中间的lambda表达式，同时将嵌套的多层Optional展开摊平成一层Optional

注意: 此处的可以为null其实是person要么Optional.empty()，要么非null。若person非null，连带着要求类嵌套的car -> insurance 域变量内容都不为null，若这两个域变量未赋值，还是会抛出NullPointerException
public String getCarInsuranceName(Optional<Person> person) {
	// 对应的无Optional非空强约束写法  person.getCar().getInsurance().getName(); 链中任意元素为null就抛NullPointerException异常
	// 注意person是Optional类型
	return person.flatMap(Person::getCar)    // Optional<Person>有值的时候，调用Person::getCar方法，返回Optional<Car>并在返回时将传入的Optional和返回的Optional合二为一
				 .flatMap(Car::getInsurance)  // Optional<Car>有值的时候，调用Car::getInsurance方法，返回Optional<Insurance>并在返回时将传入的Optional和返回的Optional合二为一
				 .map(Insurance::getName)     // getName返回String，所以不存在两个Optional需要合二为一的问题，传入Optional然后返回Optional
				 .orElse("Unknown");
}

		

【filter】如果值存在并且满足提供的谓词，就返回包含该值的 Optional 对象；否则返回一个空的Optional对象
Optional<Insurance> optInsurance = ...;
// 通过filter过滤Optional对象，如果为true返回原对象，如果为false将Optional置空
optInsurance.filter(insurance -> "CambridgeInsurance".equals(insurance.getName()))
			.ifPresent(x -> System.out.println("ok"));
			
public String getCarInsuranceName(Optional<Person> person, int minAge) {
	return person.filter(p -> p.getAge() >= minAge)  // 谓词操作为true不做任何操作直接返回输入，否则将该值直接过滤掉将Optional置为空
				.flatMap(Person::getCar)
				.flatMap(Car::getInsurance)
				.map(Insurance::getName)
				.orElse("Unknown");
}
			
			
【isPresent】如果值存在就返回 true ，否则返回 false
public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
	if(person.isPresent() && car.isPresent()) {
		return Optional.Of(findCheapestInsurance(person.get(), car.get());
	} else {
		return Optional.empty();
	}
}
等效改写：
public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
	return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));  // 利用Optional元素内容为空时不执行flatMap/map包含的lambda表达式直接返回Optional.empty()的特性简化
}


				 
解引用Optional对象：
【get】如果该值存在，将该值用 Optional封装返回，否则抛出一个NoSuchElementException异常
Car car = optCar.get();  // 简单不安全，除非确定不会为null，否则不建议使用


【orElse】如果有值则将其返回，否则返回一个默认值


【orElseGet(Supplier<? extends T> other)】如果有值则将其返回，否则返回一个由指定的 Supplier 接口生成的值
orElse方法延迟调用版，创建默认返回值耗费比较大时使用本版本提升性能


【orElseThrow(Supplier<? extends X> exceptionSupplier)】 如果有值则将其返回，否则抛出一个由指定的 Supplier 接口生成的异常
一般用于定制抛出的异常类型


【ifPresent(Consumer<? super T>)】如果值存在，就执行使用该值的方法调用，否则什么也不做



Optional emptyOptional = Optional.empty();           //  通过工厂方法得到为空的Optional对象
Optional alsoEmpty = Optional.ofNullable(null);      //  将空值转换成Optional对象，最终效果同上

assertFalse(emptyOptional.isPresent());       //     isPresent() 方法判断Optional对象中是否有值
assertTrue(a.isPresent());                    //   同上

assertEquals("b", emptyOptional.orElse("b"));          //   orElse() 方法在Optional为空时提供备选值
assertEquals("c", emptyOptional.orElseGet(() -> "c"));   //  为空时接受一个Supplier对象并调用



遗留代码转化为Optional代码示例：从键值类Properties中根据key取出value，当值是正数字符串时转为数字返回，其他情况返回0
遗留代码：
public int readDuration(Properties props, String name) {
	String value = props.getProperty(name);
	if(value != null) {
		try{
			int i = Integer.parseInt(value);
			if(i > 0) {
				return i;
			}
		}catch (NumberFormatException nfe) {}
	}
	return 0;
}
改造后的代码：
public int readDuration(Properties props, String name) {
	return Optional.ofNullable(props.getProperty(name))
					.flatMap(OptionalUtility::stringToInt) // 自有的将String转为Optional(Integer)的工具类，抛异常时返回Optional.empty()
					.filter(i -> i > 0)
					.orElse(0);
}



基础类型的Optional对象(不推荐使用):  OptionalInt, OptionalLong, OptionalDouble 
因为基础类型的Optional对象不支持map、flatMap、filter方法，而这些事Optional类最有用的方法

/* 使用Optional改造已有的方法  */
public class Artists {
	private List<Artist> artists;
	public Artists(List<Artist> artists) {
		this.artists = artists;
	}
//	public Artist getArtist(int index) {
//		if(index < 0 || index >= artists.size()) {
//			indexExcetipn(index);
//		}
//		return artists.get(index);
//	}

//	public void indexExcetipn(int index) {
//		throw new IllegalArgumentException(index + "doesn't correspond to an Artist");
//	}
//
//	public String getArtistName(int index) {
//		try{
//			Artist artist = getArtist(index);
//			return artist.getName();
//		}catch (IllegalArgumentException e) {
//			return "unknown";
//		}
//	}
	public Optional<Artist> getArtist(int index) {
		if(index < 0 || index >= artists.size()) {
			return Optional.empty();
		}else{
			return Optional.of(artists.get(index));
		}
	}
	public String getArtistName(int index) {
		Optional<Artist> artist = getArtist(index);
		return artist.map(Artist::getName).orElse("unknown");
	}
}


/****                                               时间处理                                        ****/
老时间接口和类的问题；
jdk1.0引入的Date  年份起始选择是1900年，月份从0开始计数，如 Date date = new Date(114, 2, 18); 表示2014/03/18，DateFormat方法只适合Date并且线程不安全
jdk1.1引入的Calendar  月份依然是从0开始计算


【LocalDate】 提供了简单的日期功能（不包含时间），不带时区，不可变对象/线程安全

LocalDate date = LocalDate.of(2019, 4, 18);  // 工厂方法生成日期
LocalDate today = LocalDate.now();   // 获得今天日期的工厂方法
LocalDate date2 = LocalDate.parse("2019-04-25");  // 通过字符串生成日期


int year = date.getYear();     //  2019
Month month = date.getMonth();       // 4月
int day = date.getDayOfMonth();         // 18
DayOfWeek dow = date.getDayOfWeek();  // THURSDAY
int len = date.lengthOfMonth();      // 30  指定月的天数
boolean leap = date.isLeapYear();   //  是否闰年


// 使用TemporalField接口获取具体字段，ChronoField枚举是该接口的实现之一
int year2 = date.get(ChronoField.YEAR);
int month2 = date.get(ChronoField.MONTH_OF_YEAR);
int day2 = date.get(ChronoField.DAY_OF_MONTH);


【LocalTime】 提供时间功能（不包含日期），不可变对象/线程安全
LocalTime time = LocalTime.of(13,45, 20);
LocalTime time2 = LocalTime.parse("09:38:21");


int hour = time.getHour();
int minute = time.getMinute();
int second = time.getSecond();


【LocalDateTime】 提供日期和时间功能，不带时区信息，不可变对象/线程安全
LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 15, 20);  // 完成的赋值
LocalDateTime dt2 = LocalDateTime.of(date, time);   
LocalDateTime dt3 = date.atTime(12, 34, 56);   // 只修改参数指定的部分，其他保留
LocalDateTime dt4 = date.atTime(time);         // 只修改参数指定的部分，其他保留
LocalDateTime dt5 = time.atDate(date);         // 只修改参数指定的部分，其他保留

LocalDate date3 = dt1.toLocalDate();   // 抽取日期
LocalTime time3 = dt1.toLocalTime();   // 抽取时间


【Instant】 机器时间，从1970-01-01T00:00:00开始计数的秒，本类除了域变量秒之外还有纳秒，用于精确修正秒的值  ，不可变对象/线程安全
下面3个示例都表示1970-01-01T00:00:03  其中第二个参数是用于修正的纳秒
Instant.ofEpochSecond(3);
Instant.ofEpochSecond(3, 0);
Instant.ofEpochSecond(2, 1_000_000_000);
Instant.ofEpochSecond(4, -1_000_000_000);


【Duration】计算时间差（秒级或者纳秒级） 用于LocalTime对象、LocalDateTime对象、Instant对象内部算差值，不能混用，不能用于LocalDate
计算差值：
Duration d = Duration.between(time, time2);   // PT-4H-6M-59S
Duration d2 = Duration.between(dt1, dt2);     // PT44400H30M


通过工厂方法生成差值实例：
Duration threeMinutes = Duration.ofMinutes(3);
Duration threeMinutes2 = Duration.of(3, ChronoUnit.SECONDS);

【Period】年月日度量的差值建模，可用于LocalDate
计算差值：
Period tenDays = Period.between(LocalDate.of(2014, 3, 8), LocalDate.of(2014, 3, 18));


通过工厂方法生成差值实例：
Period tenDays2 = Period.ofDays(10);
Period threeWeeks = Period.ofWeeks(3);
Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);


【操纵】
使用with开头的方法获取指定新时间，注意这些方法都创建对象的新副本，不会修改原对象。通过get/with两大类方法租到读取/修改分离
LocalDate date4 = LocalDate.of(2014, 3, 19);
LocalDate date5 = date4.withYear(2011);
LocalDate date6 = date5.withDayOfMonth(25);
LocalDate date7 = date6.with(ChronoField.MONTH_OF_YEAR, 9);

以相对变量方式获取新值，主要是plus/minus前缀方法，同样新对象都是原对象的副本，线程安全
LocalDate date8 = date4.plusWeeks(1);
LocalDate date9 = date8.minusYears(3);
LocalDate date10 = date9.plus(6, ChronoUnit.MONTHS);


通过 TemporalAdjusters 实现类的静态工厂方法调整时间，主要是按照时间节点特性获取具体时间
LocalDate date11 = LocalDate.of(2019, 4, 18);
LocalDate date12 = date11.with(nextOrSame(DayOfWeek.SUNDAY));
LocalDate date13 = date12.with(lastDayOfMonth());


通过实现TemporalAdjuster接口定制日期修改类
public class NextWorkingDay implements TemporalAdjuster {
	@Override
	public Temporal adjustInto(Temporal temporal) {
		DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
		int dayToAdd = 1;
		if(dow == DayOfWeek.FRIDAY) {
			dayToAdd = 3;
		}else if(dow == DayOfWeek.SATURDAY) {
			dayToAdd = 2;
		}
		return temporal.plus(dayToAdd, ChronoUnit.DAYS);
	}

	public static void main(String[] args) {
		LocalDate date = LocalDate.of(2019, 4, 19);
		System.out.println(date.with(new NextWorkingDay()));
	}
}

通过 TemporalAdjusters 类的静态工厂方法通过lambda表达式生成 TemporalAdjuster 对象
TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(
		temporal -> {
			DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
			int dayToAdd = 1;
			if(dow == DayOfWeek.FRIDAY) {
				dayToAdd = 3;
			}else if(dow == DayOfWeek.SATURDAY) {
				dayToAdd = 2;
			}
			return temporal.plus(dayToAdd, ChronoUnit.DAYS);
		}
);
LocalDate date = LocalDate.of(2019, 4, 19);
System.out.println(date.with(nextWorkingDay));


【解析/格式化日期】使用DateTimeFormatter，该类所有实例都是线程安全的，所以可以使用单例模式构建格式器
// 按照类库提供的指定格式转化为时间字符串
String s1 = date11.format(DateTimeFormatter.BASIC_ISO_DATE);  //  20190418
String s2 = date11.format(DateTimeFormatter.ISO_LOCAL_DATE);  //  2019-04-18

// 按照指定格式解析字符串到LocalDate
LocalDate date14 = LocalDate.parse("20190419", DateTimeFormatter.BASIC_ISO_DATE);
LocalDate date15 = LocalDate.parse("2019-04-19", DateTimeFormatter.ISO_LOCAL_DATE);

// 按照指定格式在时间字符串和LocalDate之间转换
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
String formattedDate = date11.format(formatter);
LocalDate date16 = LocalDate.parse(formattedDate, formatter);

// 创建本地化的DateTimeFormatter
DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);

// 通过细粒度的设置创建同上的DateTimeFormatter
DateTimeFormatter italianFormatter2 = new DateTimeFormatterBuilder()
						.appendText(ChronoField.DAY_OF_MONTH)
						.appendLiteral(". ")
						.appendText(ChronoField.MONTH_OF_YEAR)
						.appendLiteral(" ")
						.appendText(ChronoField.YEAR)
						.parseCaseInsensitive()
						.toFormatter(Locale.ITALIAN);
						
【时区】ZoneId类，包含40个实例

ZoneId romeZone = ZoneId.of("Europe/Rome");  // 根据字符串获取指定地区ZoneId
ZoneId zoneId = TimeZone.getDefault().toZoneId();  // 根据老的TimeZone对象获取对应的ZoneId对象

LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
ZonedDateTime zdt1 = date.atStartOfDay(romeZone);  // LocalDate设置时区

LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
ZonedDateTime zdt2 = dateTime.atZone(romeZone);   // LocalDateTime设置时区

Instant instant = Instant.now();
ZonedDateTime zdt3 = instant.atZone(romeZone);   // Instant设置时区

// 通过ZoneId做LocalDateTime和Instant类型的相互转换
LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
Instant instantFromDateTime = dateTime.toInstant(romeZone);

Instant instant = Instant.now();
LocalDateTime timeFromInstant = LocalDateTime.ofInstant(instant, romeZone);

// ZoneOffset是ZoneId的子类，用于表示时间差
ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");  
LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
OffsetDateTime dateTimeInNewYork = OffsetDateTime.of(dateTime, newYorkOffset);

使用其他的日志系统，主要有 ThaiBuddhistDate 、MinguoDate 、 JapaneseDate 、 HijrahDate
LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
JapaneseDate japaneseDate = JapaneseDate.from(date);

// 为某个Locale显示的创建日历系统， Chronology 接口建模了一个日历系统，使用ofLocale工厂方法得到一个实例
// 建议使用LocalDate而不要使用ChronoLocalDate，主要是自定义的日历系统维护成本高
Chronology japaneseChronology = Chronology.ofLocale(Locale.JAPAN);
ChronoLocalDate now = japaneseChronology.dateNow();