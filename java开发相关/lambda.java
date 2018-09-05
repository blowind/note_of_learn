
/**********************************************************************************************************************/
/****                                      java 8 函数式编程（lambda表达式）                                       ****/
/**********************************************************************************************************************/

具体的lambda表达式是在一个线程中执行的

函数式变成范式的基石：
1、没有共享的可变数据（常量是可以共享的）;
2、将方法或函数传递给其他方法，即用行为参数化把代码传递给方法;


集合元素比较并排序的老式写法(业务域为根对inventory中的苹果按照重量进行排序)
Collections.sort(inventory, new Comparator<Apple>() {
	public int compare(Apple a1, Apple a2) {
		return a1.getWeight().compareTo(a2.getWeight());
	}
});
lambda表达式写法
inventory.sort(comparing(Apple::getWeight));   // 方法引用的lambda表达式写法


lambda表达式使用场景：
所有函数式接口上都可以使用lambda表达式

函数式接口：只定义一个抽象方法的接口（注意强调的是抽象方法只能由一个，默认方法是可以有多个的）
Java API中典型示例：
public interface Comparator<T> {   /* java.util.Comparator */
	int compare(T o1, T o2);
}
public interface Runnable{  /* java.lang.Runnable */
	void run();
}
public interface ActionListener extends EventListener {    /* java.awt.event.ActionListener */
	void actionPerformed(ActionEvent e);
}
public interface Callable<V> {       /*  java.util.concurrent.Callable */
	V call();
}
public interface PrivilegedAction<V> {   /* java.security.PrivilegedAction */
	V run();
}

/*********************************          FunctionalInterface          *****************************************/

@FunctionalInterface 
注解标注一个接口，表示该接口会设计成函数式接口，这样在接口定义多个抽象方法（包括通过继承接口的方式）时会产生编译告警
虽然@FunctionInterface是非强制的，但建议每个用作函数接口的接口（即调用lambda表达式的函数设计时声明的类型），都应该添加这个注释声明

示例：将文件处理操作参数化
@FunctionalInterface
public interface BufferedReaderProcessor {
	String process(BufferedReader b) throws IOException;
}
public static String processFile(BufferedReaderProcessor p) throws IOException {
	try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
		return p.process(br);
	}
}
String oneLine = processFile( (BufferedReader br) -> br.readLine() );
String twoLine = processFile( (BufferedReader br) -> br.readLine() + br.readLine() ); 


/*********************************          默认方法          *****************************************/

备注：Java 8可以在接口内声明静态方法了

默认方法： 
1、当具体实现类中存在方法与接口中默认方法签名相同的冲突时，优先选择实现类中的方法，
2、默认方法的优先级与虚方法类似，即具体实现类没有Override接口中的默认方法时，实现类对象调用时使用接口默认方法，若Override接口中的默认方法，则使用实现类中的方法

产生背景：
实现Stream流时，要给集合接口Collection<T>及其实现类添加stream和parallelStream流化方法，
为了明显接口添加新方法后implement接口的所有现存实现代码都要修改，在接口中添加默认方法来给出默认实现

public interface Parent {
	public void message(String body);
	
	public default void welcome() {  message("Parent: Hi!"); }     //  此处在父接口里面声明了默认方法
	
	public String getLastMessage();
}

@Test
public void parentDefaultUsed() {
	Parent parent = new ParentImpl();              //  此处调用接口的具体实现类生成对象
	parent.welcome();                   //    对象自然能使用接口中定义的默认方法
}

/* 注意此处是接口继承 */
public interface Child extends  Parent {      //  子接口覆盖了父接口的默认方法
	@Override
	public default void welcome() {  message("Child: Hi!"); }
}

@Test
public void childOverrideDefault() {
	Child child = new ChildImpl();          //  此处调用子接口的具体实现类生成对象
	child.welcome();                         //  对象默认能使用接口中覆盖过的默认方法
}

多接口继承中方法签名冲突时，默认编译错误，可以通过类中实现冲突方法解决这个问题
public interface Jukebox {
	public default String rock() {
		return "... all over the world!";
	}
}

public interface Carriage {
	public default String rock() {
		return "... from side to side";
	}
}

// 语法 InterfaceName.super指向继承自父接口的方法
public class MusicalCarriage implements Carriage, Jukebox {
	@Override
	public String rock() {
		return Carriage.super.rock();     //  增强的super语法，指明使用接口Carriage中定义的默认方法
	}
}

默认方法的三定律：
1. 类胜于接口。如果在继承链中有方法体或抽象的方法声明，那么就可以忽略接口中定义的方法。
2. 子类胜于父类。如果一个接口继承了另一个接口，且两个接口都定义了一个默认方法，那么子类中定义的方法胜出。
3. 没有规则三。如果上面两条规则不适用，子类要么需要实现该方法，要么将该方法声明为抽象方法。
int count = Stream.of(1, 2, 3).reduce(0, (acc, element) -> acc + element);


public interface Performance {
	public String getName();

	public Stream<Artist> getMusicians();

	/**
	 *  添加 getAllMusicians 方法，该方法返回包含所有艺术家名字的 Stream ，如果对象是乐队，则返回乐队名和每个乐队成员的名字
	 */
	public default Stream<Artist> getAllMusicians() {
		return getMusicians().flatMap(artist -> Stream.concat(Stream.of(artist), artist.getMembers()));
	}
}


/*********************************          Optional          *****************************************/

Optional的使用： 该对象相当于一个值的容器，是一个容器对象
Optional<String> a = Optional.of("a");   
assertEquals("a", a.get());            //  可以通过get方法获取容器中的值

Optional emptyOptional = Optional.empty();           //  通过工厂方法得到为空的Optional对象
Optional alsoEmpty = Optional.ofNullable(null);      //  讲空值转换成Optional对象，最终效果同上

assertFalse(emptyOptional.isPresent());       //     isPresent() 方法判断Optional对象中是否有值
assertTrue(a.isPresent());                    //   同上

assertEquals("b", emptyOptional.orElse("b"));          //   orElse() 方法在Optional为空时提供备选值
assertEquals("c", emptyOptional.orElseGet(() -> "c"));   //  为空时接受一个Supplier对象并调用


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


/*********************************          方法引用          *****************************************/

方法引用：主要用做lambda表达式所在地方的简写，体现了将方法作为值的思想，让方法变成像值一样的一等公民 
可以把方法引用看做针对仅仅涉及单一方法的Lambda的语法糖


方法引用主要有三类：
1、指向静态方法的方法引用                如 Integer::parseInt
2、指向任意类型实例方法的方法引用        如 String::length
3、指向现有对象的实例方法的方法引用      如 this::getColor

对应的可以用来重构lambda表达式的三中方法
1、(args) -> ClassName.staticMethod(args)      重构为    ClassName::staticMethod
2、(arg0, rest) -> arg0.instanceMethod(rest)   重构为    ClassName::instanceMethod   arg0是ClassName类型
3、(args) -> expr.instanceMethod(args)         重构为    expr::instanceMethod        expr是一个外部对象

构造函数引用
Supplier<Apple> c1 = Apple::new;   /* 默认构造函数的情况 */
Apple a1 = c1.get();  /*  调用Supplier的get方法产生一个新的Apple对象实例  */

Function<Integer, Apple> c2 = Apple::new;   /*  一参数Apple(Integer weight)构造函数的情况 */
Apple a2 = c2.apply(110);        /*  调用Function函数的apply方法产生一个新的Apple对象实例 */

BiFunction<String, Integer, Apple> c3 = Apple::new; /* 两参数Apple(String color, Integer weight)构造函数的情况 */
Apple a3 = c3.apply("green", 110);  /*  调用BiFunction函数的apply方法产生一个新的Apple对象实例 */

好玩的应用：根据名字和关键参数生成对象(工厂模式的味道)
static Map<String, Function<Integer, Fruit>> map = new HashMap<>();
static {
	map.put("apple", Apple::new);
	map.put("orange", Orange::new);
	...
}
public static Fruit giveMeFruit(String fruit, Integer weight) {
	return map.get(fruit.toLowerCase()).apply(weight);   /* 使用apply方法生成对象 */
}


示例：
lambda表达式写法                                方法引用写法
(Apple a) -> a.getWeight()                      Apple::getWeight
() -> Thread.currentThread().dumpStack()        Thread.currentThread()::dumpStack
(str, i) -> str.substring(i)                    String::substring
(String s) -> System.out.println(s)             System.out::println


artist  ->  artist.getName()      形如左边的lambda表达式可以简写成，注意此处没有小括号       Artist::getName  
(name, nationality) -> new Artist(name, nationality)  构造函数lambda表达式简写    Artist::new


/*********************************          提炼演进过程          *****************************************/

提炼演进过程：
/* 层次一：通过传值来实现过滤，最基本的功能封装 */
public static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
	List<Apple> result = new ArrayList<Apple>();
	for(Apple apple : inventory) {
		if(apple.getColor().equals(color)) {
			result.add(apple);
		}
	}
	return result;
}

/* 层次二：通过传值和筛选方案标记来实现过滤，最烂的方案，对内对外都不友好 */
public static List<Apple> filterApples(List<Apple> inventory, String color, int weight, boolean flag) {
	List<Apple> result = new ArrayList<Apple>();
	for(Apple apple : inventory) {
		if( (flag && apple.getColor().equals(color)) ||
				(!flag && apple.getWeight() > weight) ){
			result.add(apple);
		}
	}
	return result;
}

/*层次三：提取动作谓词，将行为参数化后作为参数传入，缺点是所有的参数化行为都需要使用类进行封装*/
/*此处有策略模式的思想*/
public interface ApplePredicate{
	boolean test(Apple apple);
}
public class AppleHeavyWeightPredicate implements ApplePredicate {
	public boolean test(Apple apple) {
		return apple.getWeight() > 150;
	}
}
public class AppleGreenColorPredicate implements ApplePredicate {
	public boolean test(Apple apple) {
		return "green".equals(apple.getColor());
	}
}
public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
	List<Apple> result = new ArrayList<Apple>();
	for(Apple apple : inventory) {
		if(p.test(apple)) {
			result.add(apple);
		}
	}
	return result;
}
List<Apple> greenApples = filterApples(inventory, new AppleGreenColorPredicate());

/*层次四：使用匿名类省略定义封装动作的参数类,进一步优化*/
List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
	public boolean test(Apple apple) {
		return "red".equals(apple.getColor());
	}
});

/*层次五：使用lambda表达式，完全的行为分离*/
List<Apple> result = filterApples(inventory, (Apple apple) -> "red".equals(apple.getColor()));

/*层次六：将业务类泛化，抽取行为模板使得操作可以通用*/
public interface Predicate<T> {
	boolean test(T t);
}
public static <T> List<T> filter(List<T> list, Predicate<T> p) {
	List<T> result = new ArrayList<T>();
	for(T e: list) {
		if(p.test(e)) {
			result.add(e);
		}
	}
	return result;
}
List<Apple> redApples = filter(inventory, (Apple apple) -> "red".equals(apple.getColor()));
List<Integer> evenNumbers = filter(numbers, (Integer i) -> i % 2 == 0);




一般形式："   参数列表  ->   函数体     " 其中->用于分隔参数列表与函数体，参数类型一般可以省略，javac自己进行类型推断，偶尔不能推断时要指明，lambda表达式常见示例如下：
Runnable  noArguments = () -> System.out.println("hello lambda");
ActionListener oneArgument = event -> System.out.println("button clicked");
Runnable multiStatement = () -> {
									System.out.println("hello");
									System.out.println(" lambda");
};
BinaryOperator<Long> add = (x, y) -> x + y;
BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y;

java.util.function包中定义的新函数式接口:
接口                     参数            返回类型      描述符             抽象方法(不重要)
Predicate<T>              T               boolean    T->boolean          boolean test(T t);
Consumer<T>               T                void        T->void            void accept(T t);
Function<T,R>             T                 R           T->R                R apply(T t);    
Supplier<T>              None               T          ()->T                T get();                           
UnaryOperator<T>          T                 T           T->T            
BinaryOperator<T>       (T,T)               T         (T,T)->T
BiPredicate<L,R>        (L,R)             boolean    (L,R)->boolean
BiConsumer<T,U>         (T,U)              void      (T,U)->void
BiFunction(T,U,R)       (T,U)               R         (T,U)->R

上述接口除了BiPredicate<L,R>之外，其他都有优化装箱/拆箱的变种


由于lambda表达式的目标类型是由编译器自动检查匹配的，因此同一个lambda表达式可以用于多个不同的函数式接口
Callable<Integer> c = () -> 42;
PrivilegedAction<Integer> p = () -> 42;

Comparator<Apple> c1 = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2,getWeight());
ToIntBiFunction<Apple, Apple> c2 = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2,getWeight());
BiFunction<Apple, Apple, Integer> c3 = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2,getWeight());


特殊的void兼容规则：如果一个Lambda的主题是一个语句表达式，它就和一个返回void的函数描述符兼容（需要参数列表也兼容）。如下，虽然add方法返回一个boolean，但是根据特殊兼容规则也合法
Pridicate<String> p = s -> list.add(s);    /* Predicate返回了一个boolean */
Consumer<String> p = s -> list.add(s);     /* Consumer返回了一个void */



复合lambda表达式：   Comparator接口的默认方法
1、比较器复合   java.util.Comparator.comparing静态方法，用于生成lambda表达式比较器
	Comparator<Apple> c = Comparator.comparing(Apple::getWeight);      (T,T)->R类型的比较器生成，常规升序
	inventory.sort(c);
	inventory.sort(comparing(Apple::getWeight));
	
	inventory.sort(comparing(Apple::getWeight).reversed());    // 逆序，按降序返回结果
	// 比较器链，当第一个比较得出相同结果时，应用第二个比较
	inventory.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getCountry));

2、谓词复合   Predicate接口的默认方法

	Predicate<Apple> redApple = e -> "red".equals(apple.getColor());
	// 取非
	Predicate<Apple> notRedApple = redApple.negate();          
	// 与，链接两个谓词
	Predicate<Apple> redAndHeavyApple = redApple.and(a -> a.getWeight() > 150);   
	// 或，链接两个谓词 此处等价于  ( c == 'red' && w > 150) || c == 'green'
	Predicate<Apple> redAndHeavyAppleOrGreen = redApple.and(a -> a.getWeight() > 150) 
													   .or(a -> "green".equals(a.getColor())); 
	注意： and和or方法是按照在表达式链中的位置，从左到右确定优先级的，即从左到右层层加括号
	a.or(b).and(c)  等价于  (a || b) && c
	
3、函数复合   Function接口的默认方法
	
	Function<Integer, Integer> f = x -> x + 1;
	Function<Integer, Integer> g = x -> x * 2;   
	Function<Integer, Integer> h = f.andThen(g);   // 数学上等价于g(f(x)), andThen表示当前函数应用后接着应用
	int result = h.apply(1);      // 得到4
	
	Function<Integer, Integer> f = x -> x + 1;
	Function<Integer, Integer> g = x -> x * 2;   
	Function<Integer, Integer> h = f.compose(g);  // 数学上等价于f(g(x)), compose表示当前函数作用于后面函数的结果
	int result = h.apply(1);      // 得到3
	

/*********************************          Stream 流          *****************************************/
	
Stream 流: 从支持数据处理操作的源生成的元素序列
元素序列： 流提供了一个接口，可以访问特定元素类型的一组有序值。集合侧重于数据/存储，流侧重于计算
源：流使用的提供数据的源，如集合、数组或输入/输出资源。从有序集合生成流时会保留原有的顺序。有列表生成的流，元素顺序与列表一致。
数据处理操作：支持类似于数据库的而操作，以及函数式编程语言中的常用操作。

// 由值创建流
Stream<String> stream = stream.of("Java 8", "Lambdas", "Action");

Stream<String> emptyStream = Stream.empty();  // 得到一个空流

// 由数组创建流
int[] numbers = {2, 3, 5, 7, 11, 13};
int sum = Arrays.stream(numbers).sum();

// 由文件生成流    
// 示例：统计一个文件中有多少个不同的词
long uniqueWords = 0;
try(Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset())) {   // 读取文件并生成流
	uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))        // 生成单词流
					   .distinct()          // 删除重复项
					   .count();       // 统计数值
}catch(IOException e) {}
	
	
/*********************************          流处理/过滤方法示例          *****************************************/
	
惰性求值方法： 用于设置过滤条件，常见的有       of, filter, map, flatMap, min, max, limit, sorted, distinct
及早求值方法： 用来执行具体过滤条件并输出结果   collect, forEach, count, get, reduce

// 筛选各异的元素  distinct（根据元素的hashCode和equals方法实现）
List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
numbers.stream()
		.filter(i -> i%2 == 0)      // 筛选偶数
		.distinct()   // 去重
		.forEach(System.out::println);


// 个数统计
long count = allArtists.stream()
					   .filter(artist -> artist.isFrom("London"))
					   .count();

//  截短流（取部分数据），如果流是有序的，最多返回前n个元素
List<String> topThree = menu.stream()
						    .filter(d -> d.getCalories() > 300)   // 筛选卡路里超过300的
							.map(Dish::getName)       ///  获取菜名
							.limit(3)       // 获取前三
							.collect(Collectors.toList());      // 结果存储到集合
							
//  跳过元素（和limit功能互补）
List<Dish> dished = menu.stream()
						.filter(d -> d.getCalories() > 300)    // 筛选卡路里超过300的
						.skip(2)   // 跳过前两个元素
						.collect(toList());

// 返回有序流  下面三者等价
List<Integer> sameOrder = numbers.stream().sorted().collect(Collectors.toList());
List<Integer> sameOrder = numbers.stream().sorted((x, y) -> x-y).collect(Collectors.toList());
List<Integer> sameOrder = numbers.stream().sorted(comparing(x -> x)).collect(Collectors.toList());
						
//  调用Stream类的静态方法生成stream流，然后通过collect方法转换成List
List<String> collected = Stream.of("a", "b", "c")
							   .collect(Collectors.toList());   

//  使用map方法依次操作流的每个元素后再输出成List
List<String> collected = Stream.of("a", "b", "hello")
                               .map(String::toUpperCase)
							   .collect(Collectors.toList());

// 接收一个数组产生一个流，流元素的类型还是数组元素的类型
String[] arrayOfWords = {"Goodbye", "World"};
Stream<String> streamOfWords = Arrays.stream(arrayOfWords);
							   
//  使用filter过滤流中符合条件的数据
List<String> collected = Stream.of("a", "1bc", "abc1")
                               .filter(value -> isDigit(value.charAt(0)))
							   .collect(Collectors.toList());

// 合并多个stream，flatMap方法的函数接口与map一样，都是Function接口，不过方法的返回值限制为Stream类型
// flatMap的作用相当于把之前的元素进一步打碎，组成一个统一的新流
// 示例：多数组元素合并
List<Integer> together = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4))
                               .flatMap(numbers -> numbers.stream())
							   .collect(Collectors.toList());
// 示例：找出单词里面所有出现过的字符							   
List<String> uniqueCharacters = Arrays.asList("Java 8", "Lambdas", "In")
									  .stream()
									  .map(w -> w.split(""))  // 将每个单词转化成有字符构成的数组
									  // 区别于 map(Arrays::stream) 每个数组不是映射成一个流，
									  // 而是映射成流的内容，等于合并成一个流
									  // 即使用map时每个元素是Stream<String>类型，而使用flatMap每个元素是String类型
									  .flatMap(Arrays::stream) 
									  .distinct()
									  .collect(Collectors.toList());
// 示例：给定两个数字列表，返回所有可能的数对（笛卡尔积？）
List<Integer> numbers1 = Arrays.asList(1, 2, 3);
List<Integer> numbers2 = Arrays.asList(3, 4);
List<int[]> pairs = numbers1.stream()
							.flatMap(i -> numbers2.stream()
												  .map(j -> new int[]{i, j})
									)
							.collect(toList());
// 示例：给定两个数字列表，返回所有可能的和能被3整除的数对（笛卡尔积？）
List<int[]> pairs = numbers1.stream()
							.flatMap(i -> numbers2.stream()
												  .filter(j -> (i + j) % 3 == 0)
												  .map(j -> new int[]{i, j})
									)
							.collect(toList());
							   
// 是否至少匹配一个元素      
// 查看菜单是否有素食可选
if(menu.stream().anyMatch(Dish::isVegetarian()) {
	...  
}

// 是否匹配所有元素
// 检查所有菜的热量是否都低于1000卡路里
boolean isHealthy = menu.stream().allMatch(d -> d.getCalories() < 1000);

// 是否没有任何元素匹配
boolean isHealthy = menu.stream().noneMatch(d -> d.getCalories() >= 1000);

// 查找任意一个符合条件的元素   有短路逻辑，即找到第一个符合要求的结果时立即结束
Optional<Dish> dish = menu.stream().filter(Dish::isVegetarian).findAny();

// 返回第一个符合条件的元素  与findAny的差异主要在并行，找第一个元素在并行上有很多限制
Optional<Integer> firstRequiredNum = someNumbers.stream()
												.map(x -> x * x)
												.filter(x -> x%3 == 0)
												.findFirst();
							   
//   通过传入Comparator函数接口查找最小元素，同理还有max方法
Track shortestTrank = tracks.stream()
                            .min(Comparator.comparing(track -> track.getLength()))
							.get();

//  使用reduce进行累积操作，两个参数，第一个参数是初始值，第二个参数是一个lambda表达式
int sum = Stream.of(1, 2, 3)
                  .reduce(0, (acc, element) -> acc + element);
// 简略写法
int sum = numbers.stream().reduce(0, Integer::sum);
// 无初始值的变种，考虑到流中无元素的情况，因此返回值包裹在Optional中
Optional<Integer> sum = numbers.stream().reduce(Integer::sum);
// 应用扩展，求流中最大/最小值，每次归约时，返回更大/更小的那个值
Optional<Integer> max = numbers.stream().reduce(Integer::max);
Optional<Integer> min = numbers.stream().reduce(Integer::min);

//  对整形，长整形，双浮点行调用对应的函数接口提高效率，
//  诸如此处输出的IntStream对象还包含了额外的summaryStatistics()方法，可以进行方便的统计处理
IntSummaryStatistics trackLengthStats = album.getTracks()
                                             .mapToInt(track -> track.getLength())
											 .summaryStatistics();


数值流：  为解决数值类型对象化造成的装箱/拆箱开销
引入三个原始类型特化流： IntStream、DoubleStream、LongStream
特化流特有的惰性求值方法：  sum()  max()  min()  average()

// 映射到数值流  mapToInt  mapToDouble  mapToLong
int calories = menu.stream().mapToInt(Dish::getCalories).sum(); 

// 转换回对象流
IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
Stream<Integer> stream = intStream.boxed();  // 数值流转化为是Stream

// 默认值OptionalInt
// 因为最大值可能不存在（流为空），因此不能直接返回int
OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max(); 
int max = maxCalories.orElse(1);        // 没有最大值的话，就提供一个默认最大值

// 获取范围内数值
// IntStream和LongStream的两个静态方法 range和rangeClosed，区别是后者包含截止数
// 求范围数列时，range的效率比iterate高
IntStream evenNumbers = IntStream.rangeClosed(1, 100).filter(n -> n%2 == 0);

// 生成勾股数的三元组
Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
											.flatMap(a -> IntStream.rangeClosed(a, 100)  // 把所有生成的三元数流扁平化成一个流
																   .filter(b -> Math.sqrt(a*a+b*b) % 1 == 0)  // 开根为整数
																   .mapToObj(b -> new int[]{a, b, (int)Math.sqrt(a*a+b*b)})
													);
// 优化后结果
Stream<double[]> pythagoreanTriples2 = IntStream.rangeClosed(1, 100).boxed()
												.flatMap(a -> IntStream.rangeClosed(a, 100)
																.mapToObj(b -> new double[]{a, b, Math.sqrt(a*a+b*b)})
																.filter(t -> t[2] % 1 == 0));
											 
											 
public class StringExercises {
	/**  计算一个字符串中小写字母的个数 **/
	public static int countLowercaseLetters(String string) {
		return (int) string.chars().filter(Character::isLowerCase).count();
	}
	/**  在一个字符串列表中，找出包含最多小写字母的字符串。对于空列表，返回 Optional<String> 对象 **/
	public static Optional<String> mostLowercaseString(List<String> strings) {
		return strings.stream().max(Comparator.comparing(StringExercises::countLowercaseLetters));
	}

	public static void main(String[] argv) {
		List<String> list = Arrays.asList("a", "ab", "abc", "abcd");
		System.out.println(StringExercises.mostLowercaseString(list));
	}
}

/**  使用reduce实现map的功能   **/
public class MapUsingReduce {

    public static <I, O> List<O> map(Stream<I> stream, Function<I, O> mapper) {
        return stream.reduce(new ArrayList<O>(), (acc, x) -> {
        	// We are copying data from acc to new list instance. It is very inefficient,
        	// but contract of Stream.reduce method requires that accumulator function does
        	// not mutate its arguments.
        	// Stream.collect method could be used to implement more efficient mutable reduction,
        	// but this exercise asks to use reduce method.
        	List<O> newAcc = new ArrayList<>(acc);
        	newAcc.add(mapper.apply(x));
            return newAcc;
        }, (List<O> left, List<O> right) -> {
        	// We are copying left to new list to avoid mutating it. 
        	List<O> newLeft = new ArrayList<>(left);
        	newLeft.addAll(right);
            return newLeft;
        });
    }
}

/**  使用reduce实现filter的功能  **/
public class FilterUsingReduce {

    public static <I> List<I> filter(Stream<I> stream, Predicate<I> predicate) {
        List<I> initial = new ArrayList<>();
        return stream.reduce(initial,
                             (List<I> acc, I x) -> {
                                if (predicate.test(x)) {
                                	// We are copying data from acc to new list instance. It is very inefficient,
                                	// but contract of Stream.reduce method requires that accumulator function does
                                	// not mutate its arguments.
                                	// Stream.collect method could be used to implement more efficient mutable reduction,
                                	// but this exercise asks to use reduce method explicitly.
                                	List<I> newAcc = new ArrayList<>(acc);
                                    newAcc.add(x);
                                    return newAcc;
                                } else {
                                	return acc;
                                }
                             },
                             FilterUsingReduce::combineLists);
    }
    private static <I> List<I> combineLists(List<I> left, List<I> right) {
    	// We are copying left to new list to avoid mutating it. 
    	List<I> newLeft = new ArrayList<>(left);
    	newLeft.addAll(right);
        return newLeft;
    }
}

List<Integer> numbers = Arrays.asList(1, 2 ,3 ,4);
List<Integer> sameOrder = numbers.stream().collect(Collectors.toList());       //  流保持原有顺序

Set<Integer> numbers = new HashSet<Integer>(Arrays.asList(4, 3, 2, 1));
List<Integer> sameOrder = numbers.stream().sorted().collect(Collectors.toList());  //  通过排序使无序变有序


/*********************************          收集器          *****************************************/


【使用收集器】    以下大部分情况默认引入了Collectors的静态方法，即 import static java.util.stream.Collectors.*
1、转换成指定集合
//  除了Collectors传统的toList(),  toSet(),  还有  toCollection(),  toMap()   
例如：
numbers.stream().collect(Collectors.toCollection(TreeSet::new));          //  指定生成的集合的类型，此处为TreeSet

// 把流中所有项目收集到给定的供应源创建的集合
Collection<Dish> dishes = menuStream.collect(toCollection(), ArrayList::new);

emps = IntStream.range(0, 10).mapToObj(x -> new Emp(x % 3, x % 5 == 0 ? null : "name" + x)).toArray(Emp[]::new);
Stream.of(emps).collect(Collectors.toMap(Emp::getId, Emp::getName,(k,v)->v));
Stream.of(emps).collect(Collector.of(HashMap::new, 
                                     (m,emp) -> m.put(emp.getId(),emp.getName()), 
									 (k,v) -> { k.putAll(v); return k; },       // 此处为了防止parallel下数据丢失，非parallel下(k,v)->v即可
									 Characteristics.IDENTITY_FINISH));
Stream.of(emps).collect(HashMap::new, (m, emp) -> m.put(emp.getId(), emp.getName()), HashMap::putAll);


2、转换成值   maxBy,  minBy,   averagingInt,   summingInt
public Optional<Artist> biggestGroup(Stream<Artist> artists) {
	Function<Artist, Long> getCount = artist -> artist.getMembers().count(); //   生成一个函数接口对象
	return artists.collect(maxBy(comparing(getCount)));    //  将比较器传入 maxBy 收集器
}

public double averageNumberOfTracks(List<Album> albums) {
	//  使用收集器averagingInt计算平均值
	return albums.stream().collect(averagingInt(album -> album.getTrackList().size())); 
	//  还可以使用 summingInt 求和
}

// 统计个数
long howManyDished = menu.stream().collect(Collectors.counting());
long howManyDished = menu.stream().count()   // 更高效的写法

// 查找流中最大/最小值  注意为了防止流为空的情况，结果使用Optional接收
Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
Optional<Dish> mostCalorieDish = menu.stream().collect(maxBy(dishCaloriesComparator));  // 查找最大值，需要传入比较器
Optional<Dish> mostCalorieDish = menu.stream().collect(minBy(dishCaloriesComparator));

// 求和   summingInt/summingLong/summingDouble
int totalCalories = menu.stream().collect(summingInt(Dish::getCalories)); 

// 求平均值
double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories)); 

// 求统计值      可以得到流的元素个数、元素和、最小值/最大值、平均值
//summarizingInt/summarizingLong/summarizingDouble对应返回类型IntSummaryStatistics/LongSummaryStatistics/DoubleSummaryStatistics
IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));

3、字符串
String shortMenu = menu.stream.map(Dish::getName).collect(joining());  // 无参版本使用空格连接字符串
String shortMenu = menu.stream.collect(joining());  // 如果Dish的toString方法返回菜名，无需提取
String shortMenu = menu.stream.map(Dish::getName).collect(joining(", "));  // 一参版本指定连接字符串

//  提取所有艺术家的名字并作为一个[]框起来、并用逗号分隔的字符串返回
String result = artists.stream().map(Artist::getName()).collect(Collectors.joining(", ", "[", "]"));


广义的规约汇总：即所有求值/合并字符串操作的通用方法
// 求和
// 三参数版本：起始值，转换函数，BinaryOperator
int totalCalories = menu.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i + j)); 
int totalCalories = menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));   // 累积函数使用方法引用
// 以下写法有限制，流元素个数必须不为空，因为reduce操作本质上得到Optional对象，由于流元素不为空，保证get()能获取到值
int totalCalories = menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();  
int totalCalories = menu.stream().mapToInt(Dish::getCalories).sum();    // 确认元素不为空后使用特殊流的更简洁写法

// 统计个数
long howManyDished = menu.stream().collect(reducing(0L, e -> 1L, Long::sum);

// 获取流中最大/最小值   一参数版本：把流中第一个元素作为起点，返回自身的恒等函数作为转换函数
Optional<Dish> mostCalorieDish = menu.stream().collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));

// 连接字符串
String shortMenu = menu.stream.map(Dish::getName).collect(reducing((s1, s2) -> s1 + s2)).get();  // 要求流元素不为空
String shortMenu = menu.stream.collect(reducing("", Dish::getName, (s1, s2) -> s1 + s2));


4、数据分组
//  根据主唱对专辑分组，groupingBy 收集器根据接受一个分类函数，用来对数据进行分组
public Map<Artist, List<Album>> albumsByArtist(Stream<Album> albums) {
	return albums.collect(groupingBy(album -> album.getMainMusician()));
}

// groupingBy中传入分类函数，把分类函数的值作为键，流中所有具有这个分类值得项目列表作为映射值
// 单参数版本是 groupingBy(f, toList()) 的简便写法
Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));

// 自定义根据卡路里含量进行分组
public enum CaloricLevel { DIET, NORMAL, FAT }
Map<CaloricLevel, List<Dish>> dishedByCaloricLevel = menu.stream().collect(
					groupingBy( dish -> {
								if (dish.getCalories() <= 400) return CaloricLevel.DIET;
								else if(dis.getCalories() <= 700) return CaloricLevel.NORMAL;
								else return CaloricLevel.FAT;
					}));

5、组合收集器				
// 多级分组  给groupingBy传入collector类型的第二个参数，可以用类似的格式无限向下细分
Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = 
	menu.stream.collect(groupingBy(Dish::getType, groupingBy( 
	                                          dish -> { if (dish.getCalories() <= 400) return CaloricLevel.DIET;
											  else if(dis.getCalories() <= 700) return CaloricLevel.NORMAL;
											  else return CaloricLevel.FAT;
						})));
	
// 按子组收集数据   示例：统计子组元素个数  结合counting求个数
Map<Dish.Type, Long> typesCount = menu.stream().collect(groupingBy(Dish::getType, counting()));

// 查找菜单中热量最高的菜肴，此处Optional类型是maxBy的返回结果，冗余，因为不存在的类型不会成为map的key
// 结合 maxBy和comparingInt 求极值
Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream()
													   .collect(groupingBy(Dish::getType,
																	maxBy(comparingInt(Dish::getCalories))));
																	
// collectingAndThen把收集器的结果转成另一种类型，参数一为要转换的收集器，参数二为收集函数，然后返回一个收集器
// 结合 collectingAndThen 对结果进行转换
Map<Dish.Type, Dish> mostCaloricByType = menu.stream().collect(groupingBy(
													Dish::getType,
													collectingAndThen(maxBy(comparingInt(Dish::getCalories)),
																	  Optional::get)));

// 获取各类菜的热量总值  结合summingInt求和
Map<Dish.Type, Integer> totalCaloriesByType = menu.stream().collect(
															groupingBy(Dish::getType,
																	   summingInt(Dish::getCalories)));
																	   
// 获取各类菜的热量水平  结合mapping做归类
Map<Dish.Type, Set<CaloricLevel>> caloricLevelByType = menu.stream().collect(
											groupingBy(Dish:getType, mapping( 
											dish -> { if (dish.getCalories() <= 400) return CaloricLevel.DIET;
													  else if(dis.getCalories() <= 700) return CaloricLevel.NORMAL;
											          else return CaloricLevel.FAT; },
											toSet() )));
															

public Map<Artist, Long> numberOfAlbums(Stream<Album> albums) {
	//  使用收集器counting() 对 groupingBy()收集器 分块的内容进一步处理，得到每个艺术家专辑的数量
	return albums.collect(groupingBy(album -> album.getMainMusician(), counting()));
}

public Map<Artist, List<String>> nameOfAlbums(Stream<Album> albums) {
	//  使用收集器 mapping() 对 groupingBy()收集器 分块的内容进一步处理，得到每个艺术家专辑名字的列表
	return albums.collect(groupingBy(Album::getMainMusician, mapping(Album::getName, toList())));
}


// 	给定上限，将其内整数按照质数和非质数分区，此处判断没有太多剪枝操作，就是挨个元素判断，参见自己构造收集器
public boolean isPrime(int candidate) {
	int candidateRoot = (int)Math.sqrt((double)candidate);
	return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
}
public Map<Boolean, List<Integer>> partitionPrimes(int n) {
	return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(candidate -> isPrime(candidate)));
}
								
																	  
6、数据分块/分区（只能根据true或者false分成两组）   分区是分组的特殊情况，由一个谓词作为分类函数
// 使用Predicate对象判断一个元素应该属于哪个部分，并根据布尔值返回一个Map到列表
public Map<Boolean, List<Artist>> bandsAndSolo(Stream<Artist> artists) {
	return artists.collect(partitioningBy(artist -> artisit.isSolo()));
	// 等价于  return artists.collect(partitioningBy(Artist::isSolo));
}

// 按是否是素餐来分区  一参数版本
Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(partitioningBy(Dish::isVegetarian));

// 二参数版本，进一步分类
Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = menu.stream().collect(
																	partitioningBy(Dish::isVegetarian,
																				   groupingBy(Dish::getType)));
// 二参数版本，获取两类菜品中各自热量最高的菜品																			   
Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = menu.stream().collect(
														 partitioningBy(Dish::isVegetarian,
																		collectingAndThen(maxBy(comparingInt(Dish::getCalories)),
																						  Optional::get)));
// 得到荤菜/素菜各自的数目													  
menu.stream().collect(partitioningBy(Dish::isVegetarian, counting()));




实现Collector接口实现自己的收集器举例：
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
/** 接口三个参数，
 *  第一个是流中要收集的项目的泛型地方，
 *  第二个是累加器的类型，
 *  第三个是收集操作得到的对象的类型 */
public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

	// 返回一个无参函数，该函数创建一个空的累加器
	@Override
	public Supplier<List<T>> supplier() {
		return ArrayList::new;
	}

	// 返回一个执行规约规约操作的函数
	// 函数两个形参，参数一为保存规约结果的累加器，参数二为本次规约元素
	@Override
	public BiConsumer<List<T>, T> accumulator() {
		return List::add;
	}

	// 返回一个累积结束后最后调用的函数，用于将累加器对象转换为目标结果
	@Override
	public Function<List<T>, List<T>> finisher() {
		return Function.identity();  // 本函数是接口自带方法，表示累加器本身就是最终结果
	}

	// 返回一个供归约操作使用的函数，定义了并行处理时各个子部分的累加器要如何合并
	// 前三个方法已经实现了流功能，但并行的实现要依赖本方法实现
	@Override
	public BinaryOperator<List<T>> combiner() {
		return (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		};
	}

	// 定义收集器的行为，有三种相互独立的标记：UNORDERED，CONCURRENT，IDENTITY_FINISH
	// UNORDERED： 归约结果不受流中项目的遍历和累积顺序的影响
	// CONCURRENT： 标记支持并行，对于标记为UNORDERED的流，仅在用于无序数据源时才可以并行归约
	// IDENTITY_FINISH： 表明finisher方法返回的函数是一个恒等函数
	@Override
	public Set<Characteristics> characteristics() {
		return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
	}
}
使用：
List<Dish> dishes = menuStream.collect(new ToListCollector<Dish>());

使用collect的重载方法快速实现上述功能：（只需提供supplier，accumulator，combiner）
// 由于不能传递Characteristics参数，所以他永远都是一个 IDENTITY_FINISH和CONCURRENT的收集器
List<Dish> dishes = menuStream.collect(ArrayList::new, List::add, List::addAll); // 注意和HashMap的使用对比

/*   构造复杂的收集器，收集给定范围内的素数和合数，分类存放  */
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
public class PrimeNumbersCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {
	// 剪枝，从给定列表中，取出前N个符合谓词条件P的元素
	public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
		int i = 0;
		for(A item: list) {
			if(!p.test(item)) {
				return list.subList(0, i);
			}
			i++;
		}
		return list;
	}
	// 从给定素数表中，取出小于目标数的平方根值的素数
	public static boolean isPrime(List<Integer> primes, int candidate) {
		int candidateRoot = (int)Math.sqrt((double)candidate);
		return takeWhile(primes, i -> i <= candidateRoot)
				.stream()
				.noneMatch(p -> candidate % p == 0);
	}

	@Override
	public Supplier<Map<Boolean, List<Integer>>> supplier() {
		return () -> new HashMap<Boolean, List<Integer>>() {{
			put(true, new ArrayList<>());
			put(false, new ArrayList<>());
		}};
	}

	@Override
	public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
		return (acc, candidate) ->
			acc.get(isPrime(acc.get(true), candidate)).add(candidate);
	}

	@Override
	public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
		return Function.identity();
	}

	// 由于算法本身是顺序的（返回的质数从小到大，而且被用于质数判断），因此此处并行的方法实际上不可行
	// 一般正常来说抛出一个UnsupportedOperationException异常，此处实现是为了展示
	@Override
	public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
		return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> {
			map1.get(true).addAll(map2.get(true));
			map1.get(false).addAll(map2.get(false));
			return map1;
		};
	}

	@Override
	public Set<Characteristics> characteristics() {
		return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
	}

	public static void main(String[] argv) {
		// 效率还是有点
		Map<Boolean, List<Integer>> paritionPrimesWithCustomCollector =
				IntStream.rangeClosed(2, 1000000).boxed().collect(new PrimeNumbersCollector());
				
		for(Integer i : paritionPrimesWithCustomCollector.get(true)) {
			System.out.print(i + " ");
		}
	}
}
使用collect的重载方法快速实现上述功能：
public Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
	IntStream.rangeClosed(2, n).boxed().collect(
			() -> new HashMap<Boolean, List<Integer>>() {{
				put(true, new ArrayList<Integer>());
				put(false, new ArrayList<Integer>());
			}},
			(acc, candidate) -> {
				acc.get(isPrime(acc.get(true), candidate)).add(candidate);
			},
			(map1, map2) -> {
				map1.get(true).addAll(map2.get(true));
				map1.get(false).addAll(map2.get(false));
			});
}

// 下面是lambda表达式给 Map 操作带来的一些语法糖

//  Map作为本地缓存使用，有键值对时直接返回，无键值对查找填入并返回的的场景
//  当前可以使用 computeIfPresent 直接实现
Map<String, String> artistCache = new HashMap<>();
// 此处调用本地封装类中的 readArtistFromDB 方法，然后将readArtistFromDB方法返回结果放入Map并作为computeIfAbsent结果返回
artistCache.computeIfAbsent(name, this::readArtistFromDB);  

//  Map中value为List，计算List元素做统计分析的场景，forEach接收一个BiConsumer对象，该对象接受两个参数，返回void
Map<Artist, Integer> countOfAlbums = new HashMap<>();
albumsByArtist.forEach( (artist, albums) -> { countOfAlbums.put(artist, albums.size()); } );


并行数据处理： 
流的处理过程中使用parallel()并行化，使用sequential()串行化，
不能同时使用，以流操作中最后出现的并行/串行调用为整个流的标记（该调用本质上是在内部设置了一个boolean标志）

// 求给定范围内所有整数的和
public static long parallelSum(long n) {
	return Stream.iterate(1L, i -> i + 1)
				 .limit(n)
				 .parallel()
				 .reduce(0L, Long::sum);
}


//  并行化数组操作
public static double[] parallelInitialize(int size) {
	double[] values = new double[size];
	Arrays.parallelSetAll(values, i -> i);
	return values;
}

串行计算曲目长度
public int serialArraySum() {
	return albums.stream().flatMap(Album::getTracks).mapToInt(Track::getLength).sum();
}
并行计算曲目长度
public int parallelArraySum() {
	return albums.parallelStream().flatMap(Album::getTracks).mapToInt(Track::getLength).sum();
}

并行化数组操作，这类操作都在工具类Arrays中
@Test
public void parallelaaa() {
	int[] values = new int[11];
	/* 将数组中每个元素的值设置为其下标值 */
	Arrays.parallelSetAll(values, i -> i);
	for(int a : values) {
		System.out.print(a + " ");
	}
	System.out.println();
	
	/* 将数组中当前元素与前一个元素进行处理，此处将两个元素相加后将结果存入当前元素所在位置 */
	Arrays.parallelPrefix(values, (x, y) -> x + y);
	for(int a : values) {
		System.out.print(a + " ");
	}
	System.out.println();
	
	int[] unsorted = new int[10];
	Random r = new Random();
	for(int i=0; i<10; i++) {
		unsorted[i] = r.nextInt(100);
	}
	for(int a : unsorted) {
		System.out.print(a + " ");
	}
	System.out.println();
	/*  将数组元素排序后写回原数组  */
	Arrays.parallelSort(unsorted);
	for(int a : unsorted) {
		System.out.print(a + " ");
	}
}

计算简单的滑动平均数，例如，对于数组values，计算滑动窗口为3的平均数，即窗口内数据之和除以3的均值
@Test
public void simpleMovingAverage() {
	double[] values = {0, 1, 2, 3, 4, 3.5};
	int n = 3;
	double[] sums = Arrays.copyOf(values, values.length);
	//  执行并行操作，sums中保存了两两求和结果 0.0 1.0 3.0 6.0 10.0 13.5
	Arrays.parallelPrefix(sums, Double::sum);     
	int start = n - 1;
	double[] ret = IntStream.range(start, sums.length)  // 取得下标范围 2,3,4,5，此处与sums无关，取范围的静态方法
			.mapToDouble(i -> {
				double prefix = i == start ? 0 : sums[i - n];  // 此处判断过滤掉下标2边界情况
				return (sums[i] - prefix) / n;     //  sum当前位置是累加和，减去往前数第3个位置存储的累加和
			}).toArray();          //  将结果从流转换为数据
	for(double a : ret) {
		System.out.print(a + " ");  // 得到  1.0 2.0 3.0 3.5 
	}
}


/*********************************          分支/合并框架          *****************************************/

分支/合并框架：


/*********************************              调试          *****************************************/

调试：
1、调用栈显示的异常一般都是at Debugging.lambda$main$0(Debugging.java:6)这种，没啥鸟用，看看异常名称确认吧
2、使用peek输出中间结果，peek的设计初衷就是在流的每个元素恢复运行之前，插入执行一个动作
	List<Integer> result = numbers.stream()
							.peek(x -> System.out.println("from stream: " + x))
							.map(x -> x + 17)
							.peek(x -> System.out.println("after map: " + x))
							.filter(x -> x % 2 == 0)
							.peek(x -> System.out.println("after filter: " + x))
							.limit(3)
							.peek(x -> System.out.println("after limit: " + x))
							.collect(toList());