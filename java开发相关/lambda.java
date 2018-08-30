
/**********************************************************************************************************************/
/****                                      java 8 函数式编程（lambda表达式）                                       ****/
/**********************************************************************************************************************/

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
inventory.sort(comparing(Apple::getWeight));





一般形式："   参数列表  ->   函数体     " 其中->用于分隔参数列表与函数体，参数类型一般可以省略，javac自己进行类型推断，偶尔不能推断时要指明，lambda表达式常见示例如下：
Runnable  noArguments = () -> System.out.println("hello lambda");
ActionListener oneArgument = event -> System.out.println("button clicked");
Runnable multiStatement = () -> {
									System.out.println("hello");
									System.out.println(" lambda");
};
BinaryOperator<Long> add = (x, y) -> x + y;
BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y;

接口                     参数                   返回类型
Predicate<T>              T                      boolean
Consumer<T>               T                      void
Function<T,R>             T                      R
Supplier<T>              None                    T
UnaryOperator<T>          T                      T
BinaryOperator<T>       (T,T)                    T


惰性求值方法： 用于设置过滤条件，常见的有       filter, of, map, flatMap, min, max
及早求值方法： 用来执行具体过滤条件并输出结果   count, collect, get, reduce, forEach


long count = allArtists.stream().filter(artist -> artist.isFrom("London")).count();   //  将列表转为streasm

//  调用Stream类的静态方法生成stream流，然后通过collect方法转换成List
List<String> collected = Stream.of("a", "b", "c").collect(Collectors.toList());   

//  使用map方法依次操作流的每个元素后再输出成List
List<String> collected = Stream.of("a", "b", "hello").map(string -> string.toUpperCase()).collect(Collectors.toList());

//  使用filter过滤流中符合条件的数据
List<String> collected = Stream.of("a", "1bc", "abc1").filter(value -> isDigit(value.charAt(0))).collect(Collectors.toList());

//  合并多个stream，flatMap方法的函数接口与map一样，都是Function接口，不过方法的返回值限制为Stream类型
List<Integer> together = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4)).flatMap(numbers -> numbers.stream()).collect(Collectors.toList());

//   通过传入Comparator函数接口查找最小元素，同理还有max方法
Track shortestTrank = tracks.stream().min(Comparator.comparing(track -> track.getLength())).get();

//  使用reduce进行累积操作，两个参数，第一个参数是初始值，第二个参数是一个lambda表达式
int count = Stream.of(1, 2, 3).reduce(0, (acc, element) -> acc + element);

//  对整形，长整形，双浮点行调用对应的函数接口提高效率，
//  诸如此处输出的IntStream对象还包含了额外的summaryStatistics()方法，可以进行方便的统计处理
IntSummaryStatistics trackLengthStats = album.getTracks().mapToInt(track -> track.getLength()).summaryStatistics();


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


@FunctionInterface
//  每个用作函数接口的接口（即调用lambda表达式的函数设计时声明的类型），都应该添加这个注释声明


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


方法引用：主要用做lambda表达式所在地方的简写，提现了将方法作为值的思想，让方法变成像值一样的一等公民  
标准语法为  Classname::methodName
artist  ->  artist.getName()      形如左边的lambda表达式可以简写成，注意此处没有小括号       Artist::getName  
(name, nationality) -> new Artist(name, nationality)  构造函数lambda表达式简写    Artist::new

List<Integer> numbers = Arrays.asList(1, 2 ,3 ,4);
List<Integer> sameOrder = numbers.stream().collect(Collectors.toList());       //  流保持原有顺序

Set<Integer> numbers = new HashSet<Integer>(Arrays.asList(4, 3, 2, 1));
List<Integer> sameOrder = numbers.stream().sorted().collect(Collectors.toList());  //  通过排序使无序变有序


【使用收集器】
1、转换成指定集合
//  除了Collectors传统的toList(),  toSet(),  还有  toCollection(),  toMap()   
例如：
numbers.stream().collect(Collectors.toCollection(TreeSet::new));          //  指定生成的集合的类型，此处为TreeSet


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

3、数据分块（只能根据true或者false分成两组）
// 使用Predicate对象判断一个元素应该属于哪个部分，并根据布尔值返回一个Map到列表
public Map<Boolean, List<Artist>> bandsAndSolo(Stream<Artist> artists) {
	return artists.collect(partitioningBy(artist -> artisit.isSolo()));
	// 等价于  return artists.collect(partitioningBy(Artist::isSolo));
}

4、数据分组
//  根据主唱对专辑分组，groupingBy 收集器根据接受一个分类函数，用来对数据进行分组
public Map<Artist, List<Album>> albumsByArtist(Stream<Album> albums) {
	return albums.collect(groupingBy(album -> album.getMainMusician()));
}

5、字符串
//  提取所有艺术家的名字并作为一个[]框起来、并用逗号分隔的字符串返回
String result = artists.stream().map(Artist::getName()).collect(Collectors.joining(", ", "[", "]"));

6、组合收集器
public Map<Artist, Long> numberOfAlbums(Stream<Album> albums) {
	//  使用收集器counting() 对 groupingBy()收集器 分块的内容进一步处理，得到每个艺术家专辑的数量
	return albums.collect(groupingBy(album -> album.getMainMusician(), counting()));
}

public Map<Artist, List<String>> nameOfAlbums(Stream<Album> albums) {
	//  使用收集器 mapping() 对 groupingBy()收集器 分块的内容进一步处理，得到每个艺术家专辑名字的列表
	return albums.collect(groupingBy(Album::getMainMusician, mapping(Album::getName, toList())));
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