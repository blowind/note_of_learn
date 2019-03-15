package com.zxf;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *  @ClassName: Main
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2019/3/15 15:51
 *  @Version: 1.0
 **/
public class Main {

	public static void main(String[] args) {
		/* 字符串判空 */
		String input = "";
		boolean isNullOrEmpty = Strings.isNullOrEmpty(input);
		System.out.println("is null : " + isNullOrEmpty);

		/* 获取字符串公共头或者公共尾 */
		String a = "com.jd.coo.Hello";
		String b = "com.jd.coo.Hi";
		System.out.println("common prefix: " + Strings.commonPrefix(a, b));

		String c = "com.google.Hello";
		String d = "com.jd.Hello";
		System.out.println("common suffix: " + Strings.commonSuffix(c, d));

		/* 字符串收尾补全 */
		int minLength = 8;
		System.out.println("pad End : " + Strings.padEnd("123", minLength, '0'));
		System.out.println("pad Start : " + Strings.padStart("123", minLength, 'a'));

		/* 拆分字符串，可以添加二次拆分，支持正则表达式 */
		Iterable<String> splitRet = Splitter.onPattern("[,，]{1,}")
				.trimResults()
				.omitEmptyStrings()
				.split("hello,word,,世界，水平");

		for(String item: splitRet) {
			System.out.println(item);
		}

		String toSplitString = "a=b;c=d,e=f";
		Map<String, String> kvs = Splitter.onPattern("[,;]{1,}").withKeyValueSeparator('=').split(toSplitString);
		for(Map.Entry<String, String> entry : kvs.entrySet()) {
			System.out.println(String.format("%s=%s", entry.getKey(), entry.getValue()));
		}

		/* 连接字符串 */
		String joinResult = Joiner.on(" - ").join(new String[] {"Hello", "world"});
		System.out.println(joinResult);

		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "b");
		map.put("c", "d");
		String mapJoinRet = Joiner.on(",").withKeyValueSeparator("=").join(map);
		System.out.println(mapJoinRet);

		/* 对象比较 */
		Object oa = null;
		Object ob = new Object();
		Object oc = new Object();
		System.out.println("is Object equal : " + Objects.equal(a, b));
		System.out.println("is Object equal : " + Objects.equal(b, c));

		/**
		 * 不可变集合的意义：
		 * 1、当对象被不可信的库调用时，不可变形式是安全的；
		 * 2、不可变对象被多个线程调用时，不存在竞态条件问题
		 * 3、不可变集合不需要考虑变化，因此可以节省时间和空间。所有不可变的集合都比它们的可变形式有更好的内存利用率（分析和测试细节）；
		 * 4、不可变对象因为有固定不变，可以作为常量来安全使用。
		 *
		 * JDK自带Collections.unmodifiableXXX方法的缺点：
		 * 1、笨重而且累赘：不能舒适地用在所有想做防御性拷贝的场景；
		 * 2、不安全：要保证没人通过原集合的引用进行修改，返回的集合才是事实上不可变的；
		 * 3、低效：包装过的集合仍然保有可变集合的开销，比如并发修改的检查、散列表的额外空间，等等。
		 */
		// 创建不可变集合方法一
		Set<String> immutableNamedColors = ImmutableSet.<String>builder()
								.add("red", "green", "black", "white", "grey")
								.build();
		for(String color : immutableNamedColors) {
			System.out.println(color);
		}

		// 创建不可变集合方法二
		ImmutableSet.of("red", "green", "black", "white", "grey");

		// 创建不可变集合方法三
		ImmutableSet.copyOf(new String[] {"red", "green", "black", "white", "grey"});

		/**
		 * 带有计数器功能的Multiset，所有添加的重复元素都会让计数器递增
		 * HashMultiset: 元素存放于 HashMap
		 * LinkedHashMultiset: 元素存放于 LinkedHashMap，即元素的排列顺序由第一次放入的顺序决定
		 * TreeMultiset:元素被排序存放于TreeMap
		 * EnumMultiset: 元素必须是 enum 类型
		 * ImmutableMultiset: 不可修改的 Mutiset
		 */
		Multiset multiset = HashMultiset.create();
		String sentences = "this is a story, there is a good girl in the story.";
		Iterable<String> words = Splitter.onPattern("[^a-z]{1,}").omitEmptyStrings().trimResults().split(sentences);
		for(String word : words) {
			multiset.add(word);
			// 指定本次添加元素操作给元素计数器的增加值
			// multiset.add(word, 100);
		}
		for(Object elm : multiset.elementSet()) {
			System.out.println((String)elm + ":" + multiset.count(elm));
		}

		/**
		 * BiMap： 双向Map，既提供键到值的映射，也提供值到键的映射
		 * HashBiMap: key 集合与 value 集合都有 HashMap 实现
		 * EnumBiMap: key 与 value 都必须是 enum 类型
		 * ImmutableBiMap: 不可修改的 BiMap
		 */
		BiMap<String,String> weekNameMap = HashBiMap.create();
		weekNameMap.put("星期一","Monday");
		weekNameMap.put("星期二","Tuesday");
		weekNameMap.put("星期三","Wednesday");
		weekNameMap.put("星期四","Thursday");
		weekNameMap.put("星期五","Friday");
		weekNameMap.put("星期六","Saturday");
		weekNameMap.put("星期日","Sunday");

		System.out.println("星期日的英文名是" + weekNameMap.get("星期日"));
		System.out.println("Sunday的中文是" + weekNameMap.inverse().get("Sunday"));

		/**
		 * Multimaps：一键多值的Map  数据类型Map<String,Collection<String>>，在使用上又会把Collection展开
		 * 实现	                   Key实现         	Value实现
		 * ArrayListMultimap	    HashMap	        ArrayList
		 * HashMultimap      	   HashMap	        HashSet
		 * LinkedListMultimap	   LinkedHashMap	LinkedList
		 * LinkedHashMultimap   	LinkedHashMap	LinkedHashSet
		 * TreeMultimap	TreeMap  	TreeSet
		 * ImmutableListMultimap	ImmutableMap	ImmutableList
		 * ImmutableSetMultimap   	ImmutableMap	ImmutableSet
		 */
		Multimap<String, String> myMultimap = ArrayListMultimap.create();

		// 添加键值对
		myMultimap.put("Fruits", "Bannana");
		//给Fruits元素添加另一个元素
		myMultimap.put("Fruits", "Apple");
		myMultimap.put("Fruits", "Pear");
		myMultimap.put("Vegetables", "Carrot");

		// 获得multimap的size
		int size = myMultimap.size();
		System.out.println(size);  // 4

		// 获得Fruits对应的所有的值
		Collection<String> fruits = myMultimap.get("Fruits");
		System.out.println(fruits); // [Bannana, Apple, Pear]

		Collection<String> vegetables = myMultimap.get("Vegetables");
		System.out.println(vegetables); // [Carrot]

		//遍历Mutlimap
		for(String value : myMultimap.values()) {
			System.out.println(value);
		}

		// Removing a single value
		myMultimap.remove("Fruits","Pear");
		System.out.println(myMultimap.get("Fruits")); // [Bannana, Pear]

		// Remove all values for a key
		myMultimap.removeAll("Fruits");
		System.out.println(myMultimap.get("Fruits")); // [] (Empty Collection!)

		/**
		 * Table: 实现二维矩阵的数据结构，可以是稀溜矩阵
		 *
		 */
		// 通过HashBasedTable创建了一个行类型为Integer，列类型也为Integer，值为String的Table
		Table<Integer, Integer, String> table = HashBasedTable.create();
		for (int row = 0; row < 10; row++) {
			for (int column = 0; column < 5; column++) {
				table.put(row, column, "value of cell (" + row + "," + column + ")");
			}
		}
		for (int row=0;row<table.rowMap().size();row++) {
			Map<Integer,String> rowData = table.row(row);
			for (int column =0;column < rowData.size(); column ++) {
				System.out.println("cell(" + row + "," + column + ") value is:" + rowData.get(column));
			}
		}

		/**
		 * Iterators是Guava中对Iterator迭代器操作的帮助类，看了下相关操作都可以用lambda表达式实现
		 */

	}

	/**
	 *  字符串入参判断，只是省略了省略了抛异常的代码，感觉意义不大
	 */
	public void doSth(String name, int age, String desc) {
		/* 为null则会抛出NullPointerException空指针异常 */
		Preconditions.checkNotNull(name, "name must not be null");

		/* checkArgument不符合条件时会抛出IllegalArgumentException异常 */
		Preconditions.checkArgument(age >= 18 && age < 99, "age must int range(18, 99)");

		/* checkState 和checkArgument参数和实现基本相同,状态不正确会抛出IllegalStateException异常 */
		Preconditions.checkState(desc != null, "desc must not be null");
	}
}
