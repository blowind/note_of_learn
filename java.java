Set��ÿ��ֵ��ֻ����һ������
Map����ĳЩ����������һЩ������������γɹ�������

Arrays.asList����һ���������һ���ö��ŷָ���Ԫ���б�
���ŷָ���Ԫ���б�
Arrays.asList("second", "third", "forth", "fifth");

���飺
Arrays.asList( new int[]{1,2,3,4,5} );
int[] realArray = new int[]{5,6,7,8,9};
Arrays.asList(realArray);

//  ����һ����̬�������ڲ������࣬ע��filter�Ĳ���������final����Ϊ�������ڲ�ʹ�����Ը��෶Χ֮��Ķ������������Ҫ��
public static FilenameFilter filter(final String regex) {
	return new FilenameFilter() {
		private Pattern pattern = Pattern.compile(regex);
		public boolean accept(File dir, String name) {
			return pattern.matcher(name).matches();
		}
	};
}

//  ��list����ֵ��StringAddress��һ��˽�л���String���Ժ���Ӧ�Ĺ�������list����4��Ԫ�أ����ַ���������ΪHello�ַ���
List<StringAddress> list = new ArrayList<StringAddress>(Collections.nCopies(4, new StringAddress("Hello")));
// list����4��Ԫ�أ����ַ���������ΪWorld�ַ�����֮ǰ�Ľ�������ǣ�fillֻ���滻����Ԫ�أ����������Ԫ��
Collections.fill(list, new StringAddress("World"));


@SuppressWarning "unchecked"   //  ���α���澯��ʹ���ھ���С�ĵ�λ�ϣ����� ������Է������Σ��Ͳ�Ҫ���ʱ�ǩ�ŵ���֮ǰ

/****                                               ����                                        ****/

List
List<Pet> pets = new ArrayList<Pet>();
���Ԫ��
pets.add(newPet);
�������Ԫ��
pets.addAll(Arrays.asList(pet1, pet2, pet3, pet4));
ɾ��Ԫ��
pets.remove(oldPet);
��ȡԪ��������
int index = pets.indexOf(pet3);  //  �õ�2
��ȡ���б�
List<Pet> sub = pets.subList(1,2);  // ��ȡ�ڶ���Ԫ����ɵ�List
�ж����б��Ƿ����Ӽ���
boolean isTrue = pets.containsAll(sub);

ArrayList<T> ��ʹ��
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
		ArrayList<Apple> apples = new ArrayList<Apple>();   //  ArrayList<T> ������ת��Ϊ List<T>
		//  �������Ԫ�ز�����ת��
		apples.add(new GrannySmith());
		apples.add(new Gala());
		apples.add(new Fuji());
		for(Apple c : apples) 
			System.out.println(c);
	}
}
List�Ļ����÷���ʾ  ��Ҫ��ArrayList<T> ���������ʱ���������ţ�����/ɾ��Ԫ�ؽ���
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
		/* ֱ��ʹ��Arrays.asList�����ʱ���ײ�ʵ�ʻ������飬���ܵ����ߴ磬���/ɾ��Ԫ��ʱ���׳��쳣 */
		/* �Ӽ��������Ԫ��  addAll() */
		apples.addAll(Arrays.asList(tmp, new Gala(), new Fuji()));
		apples.addAll(1, Arrays.asList(new Fuji()));   //  �ڵ�һ��Ԫ��֮�����һ���µ�List
		/* ʹ�õ���������Ԫ�� */
		for(Apple c : apples) 
			System.out.println(c);
		/* ɾ�������ڵ�Ԫ�أ����� false */
		Print.print(apples.remove(new GrannySmith()));
		/* ɾ��ָ��Ԫ�أ����� true */
		Print.print(apples.remove(tmp));
		/* ɾ������ָ����Ԫ�أ����� true����ʱ���ϻ�ʣһ��Ԫ�� */
		apples.remove(0);
		
		for(Apple c : apples) 
			System.out.println(c);
		/* ��ӵ���Ԫ�� */
		apples.add(tmp);
		/* ����ĳ��Ԫ�ص��±꣬��Ϊtmp�ձ����룬��˷������һ��Ԫ�ص��±� 1 */
		Print.print(apples.indexOf(tmp));
		/* ��ȡ��һ��Ԫ�أ��˴��� Fuji() ���� */
		Print.print(apples.get(0));
		/* ����һ��Ԫ���޸ĳ� gala() */
		apples.set(0, new Gala());
		
		
		/* ��ȡList���Ӽ��� �˴��õ��������һ��Ԫ�ص�List */
		/* ���������б��Ļ����ǳ�ʼ�б���˶�sub���޸Ķ��ᷴӳ����ʼ�б��� */
		List<Apple> sub = apples.subList(0, 1);
		Print.print(sub);
		/* ȡapples��sub�Ľ��������շ���apples */
		apples.retainAll(sub);
		/*  apples�Ƿ����Ԫ��tmp������true */
		apples.contains(tmp);
		/* ���� true �� sub��apples���Ӽ� */
		Print.print(apples.containsAll(sub));
		/* ʹ��Collections��������Ԫ�أ�Ҫע������и�s������s��Collectionֻ�ܲ���Collection��������̫�� */
		Collections.addAll(apples, new Fuji(), new Fuji(), new Fuji(), new Apple());
		for(Apple c : apples) 
			System.out.print(c + " ");
		
		/* ��apples���򣬴˴�����ʧ�ܣ���ΪApple����δʵ�� Comparable �ӿ� */
		//  Collections.sort(apples);
		/* ����apples��˳�� */
		Collections.shuffle(apples);
		
		/* �������Ԫ�� */
		apple.clear()
		/* �жϵ�ǰList�Ƿ�Ϊ�� */
		apple.isEmpty();
		
		/* ֱ��ʹ��Arrays��asList()��������������ʱ����Ҫ ��ʾ���Ͳ���˵�� */
		List<Apple> as = Arrays.<Apple>asList(new Gala(), new Fuji());
		for(Apple c : as) 
			System.out.print(c + " ");
	}
}
LinkedList<T>  ǿ�ڲ���/ɾ�������ڲ��ң��ʺϵ������к�ջ
public static void main(String[] args) {
	LinkedList<String> numArray = new LinkedList<String>(Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight"));
	
	Print.print(numArray);
	/* ��ȡ��һ��Ԫ�أ������Ƴ������ListΪ���׳�NoSuchElementException�쳣 */
	Print.print(numArray.getFirst());
	/* ��ȡ��һ��Ԫ�أ�������ȫͬ��*/
	Print.print(numArray.element());
	/* ������ȫͬ�ϣ�ֻ����ListΪ��ʱ���׳��쳣�����Ƿ���null */
	Print.print(numArray.peek());
	
	/* �Ƴ���һ��Ԫ�ز�������Ϊ����ֵ�����ListΪ���׳�NoSuchElementException�쳣 */
	Print.print(numArray.remove());
	/* �Ƴ���һ��Ԫ�ز�������Ϊ����ֵ��������ȫͬ�� */
	Print.print(numArray.removeFirst());
	/* �Ƴ���һ��Ԫ�ز�������Ϊ����ֵ��������ȫͬ�ϣ�ֻ����ListΪ��ʱ���׳��쳣�����Ƿ���null */
	Print.print(numArray.poll());
	/* �Ƴ����һ��Ԫ�� */
	Print.print(numArray.removeLast());
	
	/* ��Listͷ���Ԫ�� */
	numArray.addFirst("zero");
	/* ���б�ĩβ���һ��Ԫ�� */
	numArray.offer("nine");
	/* ���б�ĩβ���һ��Ԫ�أ�������ȫͬ�� */
	numArray.add("ten");
	/* ���б�ĩβ���һ��Ԫ�أ�������ȫͬ�� */
	numArray.addLast("eleven");
	
	/*  [zero, three, four, five, six, seven, nine, ten, eleven]   */
	Print.print(numArray);
}
Stack<T>���ͣ���Ҫʹ��java�Դ�Stack���ͣ�ʵ�ֵĲ��ã�ʹ��LinkedList���棬�Լ���װ
public class MyStack<T> {
	private LinkedList<T> storage = new LinkedList<T>();
	public void push(T e) { storage.addFirst(e); }
	/* ���ص�һ��Ԫ�أ����ǲ�ɾ��  */
	public T peek() { return storage.getFirst(); }
	/* ɾ����һ��Ԫ�ز�������Ϊ����ֵ����  */
	public T pop() { return storage.removeFirst(); }
	public boolean empty() { return storage.isEmpty(); }
	public String toString() { return storage.toString(); }
}
Set<T>���ͣ���Ҫ����ȥ��/���أ�HashSet�Բ������Ż������˶�ʹ��HashSet��ûɶ�þ���ģ���Collection����ȫһ���Ľӿ�
public static void main(String[] args) {
	Random rand = new Random(47);
	/* HashSetʹ��ɢ�к��� */
	Set<Integer> intSet = new HashSet<Integer>();
	for(int i=0; i<10; i++) {
		intSet.add(rand.nextInt(30));
	}
	Print.print(intSet.contains(9));
	Print.print(intSet);
}
TreeSet<T> ��Ԫ�ش洢�ں�����У������õ��������ӳ��/�ֵ�
public static void main(String[] args) {
	Random rand = new Random(47);
	/* ʹ�ú������ TreeSet���õ����ֵ�������ģ�����û��get������ȡ���� */
	Set<Integer> intSet = new TreeSet<Integer>();
	for(int i=0; i<10000; i++) {
		intSet.add(rand.nextInt(30));
	}
	 // [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
	System.out.println(intSet); 
	
	Set<String> words = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
}
LinkedHashSet Ϊ�˲�ѯ�ٶ�Ҳʹ����ɢ�У�ͬʱʹ�����б�ά��Ԫ�صĲ���˳��

HashMap<T, T>
public static void main(String[] args) {
	Random rand = new Random(47);
	Map<Integer, Integer> m = new HashMap<Integer, Integer>();
	for(int i=0; i<10000; i++) {
		int r = rand.nextInt(20);
		/*  Map��û�����Ԫ��ʱ���᷵��null */
		Integer freq = m.get(r);
		m.put(r, freq == null ? 1 : 1 + freq);
	}
	/*  �鿴m���Ƿ���� keyΪ3��Ԫ�� */
	Print.print(m.containsKey(3));
	/*  �鿴m���Ƿ���� valueΪ500��Ԫ�� */
	Print.print(m.containsValue(500));
	/*  ������е� key ���� */
	Print.print(m.keySet());
	/*  ������е� value ���� */
	Print.print(m.values());
	/* �����������е�keys */
	for(Integer k : m.keySet()) {
			Print.print(k + " happens " + m.get(k) + "times");
	}
}

Queue�Ļ���ʵ�ֺ��÷� LinkedListʵ����Queue�ӿڣ�ͨ����LinkedList����ת��ΪQueue����������Queueʹ��
Queue<Integer> queue = new LinkedList<Integer>();
/* ���Ԫ�أ��鿴LinkedList֪�����ڶ�β��ӵ� */
queue.offer(333);
// ��ȡԪ�أ���ȡ��ɾ�������Ե��� queue.remove() ɾ��
queue.peek();  //  ���ض�ͷ�� ����Ϊ��ʱ���� null
queue.element();  //  ���ض�ͷ�� ����Ϊ��ʱ����NoSuchElementException�쳣

queue.poll()    //  ɾ�������ض�ͷ������Ϊ��ʱ���� null
queue.remove()  // ɾ�������ض�ͷ������Ϊ��ʱ����NoSuchElementException�쳣

PriorityQueue ���ȼ����У���һ��������Ԫ���Ǿ���������ȼ���Ԫ�أ�������ȼ��Ƿ���Ķ���ʵ�ֵ� Comparator ���õ�
ȷ������peek()��poll()��remove()����ʱ����ȡ��Ԫ�ؽ��Ƕ��������ȼ���ߵ�Ԫ��
���ȼ������㷨���ڲ���ʱ����ά��һ���ѣ�����������Ҳ�������Ƴ�ʱѡ������Ҫ��Ԫ�أ���������ھ���ʵ�֡������������ȼ������Ķ����еȴ�ʱ�����޸ģ���ô�㷨��ѡ����Եú���Ҫ��
public static void main( String[] args )
{
	PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
	Random rand = new Random(47);
	for(int i=0; i<10; i++) {
		int tmp = rand.nextInt(i+10);
		/* �õ����Ǹ�������� [8 1 1 1 5 14 3 1 0 1]   */
		System.out.print(tmp + " ");
		pq.offer(tmp);
	}
	while(pq.peek() != null) {
		/* �������ȼ�����������ϸ��յ���˳�����  [0 1 1 1 1 1 3 5 8 14 ] */
		System.out.print(pq.remove() + " ");
	}
	
	List<Integer> ints = Arrays.asList(25, 22, 20, 18, 14, 9, 3, 1, 2, 3, 9, 14, 25);
	/* ��ʼ��ʱָ���������У����ս������г��� */
	pq = new PriorityQueue<Integer>(ints.size(), Collections.reverseOrder());
	pq.addAll(ints);
	/* �õ� [ 25 25 22 20 18 14 14 9 9 3 3 2 1 ] */
	while(pq.peek() != null) {
		System.out.print(pq.remove() + " ");
	}
}

ֱ��ʵ�ֵ��������ؼ���ʵ�� public Iterator<String> iterator() {} ���������Լ����ڲ���ô����һ������
import java.util.*;
class InstanceSequence {
	protected String[] des = new String[] {"one", "two", "three", "four", "five", "six", "seven"}; 
}
/* ͨ���̳еõ����е��ַ������� */
public class NonCollectionSequence extends InstanceSequence {
	/* ͨ��ʵ�ֵ��������� Iterator<T> iterator() ʹ�ñ�����е������ܣ��ýӿ��µ� hasNext() next() remove() һ����������*/
	public Iterator<String> iterator() {
		/* ͨ��������ʵ�֣�ÿ�η��ص������������������ڲ���indexÿ�ζ��Ǵ�0��ʼ�����һ�������򵥵�ʵ�� */
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
		//  ��Ϊʵ�ֵĲ��� Iterable<T> �ӿڣ���˲����� foreach ѭ����ʹ��
		// for(String s: ncs) {
			// System.out.println(it.next());
		// }
	}
}
ʵ��JSE5�µ� Iterable �ӿڣ��ýӿڱ�foreach�������������ƶ�������ʵ����һ����ת����������
package com.zxf.my;
import java.util.*;
class ReversibleArrayList<T> extends ArrayList<T> {
	public ReversibleArrayList(Collection<T> c) { super(c); }
	/* ����for����ʹ�õ�ʱ���ǵ��õ�  Iterable<T>.iterator()  ��Iterable<T>�����iterator()�������÷�������Iterator<T>���� */
	/* ���ձ����ϻ���Ҫ���� �ӿڷ��Ͷ���ע��˴����� Iterator<T> */
	public Iterable<T> reversed() {
		/* ʵ����һ���ӿڷ��͵Ķ��󣬴˴���һ�������� */
		return new Iterable<T>() {
			/* �������еķ��� public Iterator<T> iterator() ����һ��������  */
			public Iterator<T> iterator() {
				/* ���ص����ӵķ����ڲ����������Ǵ���һ�����������󣬸ö���ʵ����һ�������� */
				return new Iterator<T>()  {
					int current = size() - 1;
					public boolean hasNext() { return current > -1; }
					/*  ����get�����еļ���ArrayList�л�ȡ���� */
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
			System.out.print(s + " ");  // �õ� To be or not to be
		System.out.println();
		for(String s: ral.reversed())
			System.out.print(s + " ");	 //  �õ� be to not or be To 
	}
}

/****                                      ���͵�ʹ��                                     ****/
/* ����һ���������ͣ�������һ����������Ƕ���һ�����࣬����������һƱ�·����Է���T���д���������������û�б�������
   ��NewClass<T>����NewClass�����ͺã����˳�ʼ��ʱ��Ҫͨ��<>��һЩ����ȷ��֮�⣬��NewClass��ʹ����û��������
   ������List<T>֮��ķ��ͼ��ϣ������ڷ���T�ϼ���һƱ���ϲ��������������Ͼ���һ��List��Ĺ���
   */
class Decorate<T> {   // ����һ��֧�ַ��͵�����
	private T s;
	public Decorate(T outS) { s = outS; } 
	public String toString() {   return "Decorate<" + s + ">";   }
	public void setValue(T t) {  s = t;  }
}
//  ��������ļ��ϵ�ʹ��
public static void main(String[] args) {
/* �˴���ʼ��ʱ������������Ƕ�ף���ʵ��������һ��Decorate<String>����
   �򵥵Ŀ���Decorate���ɣ����׸ö����ڲ�����String�ͺ�                    */
	List<Decorate<String>> testOne = new ArrayList<Decorate<String>>(  
												Arrays.asList( new Decorate<String>("I"),   // Ԫ��
															   new Decorate<String>("II")));
	System.out.println(testOne);  // �õ�  [Decorate<I>, Decorate<II>]
	
	/* ע��˴���ArrayList����洢��Ԫ�����õĿ�����������������ָ��Decorate<String>�����ָ��Ŀ���  */
	List<Decorate<String>> testTwo = new ArrayList<Decorate<String>>(testOne);
	//  �޸�testTwo�����еĵڶ���Ԫ��λ�ô洢������Ϊһ���µĶ��������
	testTwo.set(1, new Decorate<String>("III"));
	System.out.println(testTwo);  // �õ� [Decorate<I>, Decorate<III>]
	System.out.println(testOne);  // �õ� [Decorate<I>, Decorate<II>]
	
/*  ע�⵽�˴���ȡ��testTwo��һ��Ԫ�ش洢�����ã���������testOne��һ��Ԫ�ش洢������һ����
    ����ָ��ͬһ��Decorate<String>����                                                     */
	Decorate<String> tmp = testTwo.get(0);
	tmp.setValue("IV");
	System.out.println(testTwo);   // �õ�  [Decorate<IV>, Decorate<III>]
	System.out.println(testOne);   // �õ�  [Decorate<IV>, Decorate<II>]



/****                                      ʹ�÷��Ͱ�װ��/�ӿڶ����ṩ����                                     ****/
import java.util.Arrays;
/* ע�⣬�˴��Ƿ����࣬����ķ��ͱ�ʾ�ŵ������ϣ��ڲ��ķ����Ͳ����ٷ�<T>�� */
class ClassParameter<T> {  
	public T[] f(T[] arg) { return arg; }
}
/* ע�⣬�˴��Ƿ��ͷ���������������ʱ�򲻴����ͣ�����ķ�����ǰ�棬Ҫ��һ����ʾ���͵�<T> */
class MethodParameter {
	public static <T> T[] f(T[] arg) { return arg; }  
}
public class ParameterizedArrayType {
	public static void main(String[] args) {
		Integer[] ints = {1, 2, 3, 4 ,5};
		Double[] doubles = {1.1, 2.2, 3.3, 4.4, 5.5};
		
		Integer[] ints2 = new ClassParameter<Integer>().f(ints);    // ��������ľ���T����������ʱָ��
		Double[] doubles2 = MethodParameter.f(doubles);         // �����������ľ���T����ƥ�䵽���õ�ʱ���Լ�ʶ�𣬼������ƶ�
		
		System.out.println(Arrays.deepToString(ints2));
		System.out.println(Arrays.deepToString(doubles2));
	}
}

Set<Object> so �Ǹ����������ͣ� ��ʾ���԰����κ����͵ļ��ϣ���� so.add("adadfs");   so.add(123);  �������Ϸ�

Set<?> ��ʾһ��ͨ������ͣ���ʾֻ�ܰ���ĳ��δ֪�������͵�һ�����ϣ�������ֻ���������ղ�����������newʵ�����������β�Ҳ�������/ɾ��Ԫ��(null���⣬�������null��Ψһ������ӵ�Ԫ��)��ֻ��clear() ���

Set ԭ��̬���ͣ�����   ע�� Set<String> �� Set<Object> ���� Set������ �������໥֮��û�и��ӹ�ϵ����ƽ����

/* �����Ƶ�ͨ������ͣ�ֻ���������ղ���  */
static int numElementsInCommon(Set<?> s1, Set<?> s2) {  // Set<?>��������Setû�в�𣬵�ͨ����ǰ�ȫ��
	int ret = 0;
	for(Object o : s1)    
		if(s2.contains(o))  //  ��֪������ܲ�����containsAll()���п���һ��
			result++;
	return ret;
}	
�޷����κ����ͷŵ� Collection<?> c��  �κ����� c.add("aaa") �ĵ��ö���ʧ��

��Ҫʹ��ԭ��̬���ϣ�����List��Set��Collection����Ҫʹ�ô�����List<String>֮�࣬���������ֳ������⣺

����һ��
���������б���ʹ��ԭ��̬���ͣ��淶������ʹ�ò��������ͣ��������ͺͻ������ͳ��⣩ ��
List.class   String[].class  int.class �ǺϷ���  ����List<String>.class    List<?>.class ���Ϸ�
��������
if( o instanceof Set ) { Set<?> m = (Set<?>)o; }   // һ��ȷ�� o ��Set�����뽫��ת����ͨ�������Set<?>
���ڷ�����Ϣ����ʱ����������˴������� instanceof �����Ƿ�

û����ν�ķ�������  new List<String>[];  ���ǲ��Ϸ���  ֻ�ܴ��� new List<String>();

/* �������һ��ʵ�֣��޷�����ʹ������ʱ���÷�����ʱһ��Ҫ���ⲿ��֤���ᴫ�벻ͬ���͵Ķ��� */
public class Stack<E> {
	private E[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	@SuppressWarnings("unchecked")
	public Stack() {
		elements = (E[])new Object[DEFAULT_INITIAL_CAPACITY];   // �˴���ʼ��ʱ��������ǿת�������������͵�ǿתͨ����Σ��
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
		@SuppressWarnings("unchecked") E ret = (E)elements[--size];  // ÿ��ȡ������ʱ��������ǿת��ǿת������
		elements[size] = null;
		return ret;
	}
}
/* ʹ������ͨ�������API�������  PECSԭ�� */
public class Stack<E> {
	//  ����һ������Ԫ�أ����ϵ�Ԫ��ֻ��Ҫ��E��������(����E)�Ϳ��Է��룬����ļ�����������
	public pushAll(Iterable<? extends E> src) { ... }
	//  ��Ԫ�ش�ջ�ﷵ�ص�һ����������ϵ�Ԫ��Ҫ��E�ĳ�����ȡ���ļ�����������
	public void popAll(Collection<? super E> dst) { ... }
	//  ����ⲿ�ļ�����Ϊ���������ʱ�������ܵ��������ֿ��ܵ������ߣ����ʺ���ͨ���������ʵʵ��E�Ϳ�����
	// ��Ҫ��ͨ�������������
}
//  �ϲ��������ϣ���������������
public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2)  { ... }
Set<Integer> integers = new HashSet<Integer>();
Set<Double> doubles = new HashSet<Double>();
Set<Number> numbers = Union.<Number>union(integers, doubles);  //  �����ܲ������˴�������

���͵���
interface UnaryFunction<T> { T apply(T arg); }
// ����һ����̬������ÿ�ε���ʱʵ����һ��UnaryFunction<Object>���͵Ķ��󣬸ö���Ψһ�ķ����Ƿ�������
private static UnaryFunction<Object> IDENTITY_FUNCTION = new UnaryFunction<Object>() { 
																	public Object apply(Object arg) { return arg; }
};
@SuppressWarnings("unchecked")
// ���;�̬������Ϊ���ͣ������侲̬����apply������α���ͬʱ���������ƶϣ���������ʱ�����������˴������������
public static <T> UnaryFunction<T> identityFunction() {  
	return (UnaryFunction<T>) IDENTITY_FUNCTION;
} 
//  ʹ�÷���   û������������͵�����ɶ�ã�������ɶ�����ɶ���뷺����������һ�£�
UnaryFunction<String> mys = identityFunction();
mys.apply("112233") == "112233"  // ���� true

//  <T extends Comparable<T>> ��������Կ���������Ƚϵ�ÿ������T��
// ��һ������������Ԫ�صķ��ͷ����������Ԫ�ؿ�����ĳ�������
public static <T extends Comparable<T>> T max(List<T> list) {
	Iterator<T> i = list.iterator();
	T ret = i.next();
	while(i.hasNext()) {
		T tmp = i.next();
		if(t.compareTo(result) > 0) ret = tmp;   //  ��Ϊ��������Ƚϣ��˴����ܵ���
	}
	return ret;
}
ʹ��ͨ�������֮���Ч��
/* ֻҪT�ĸ�����ԱȽϣ�����ϱȽϽ�����ɱȽ�����������ʹ�õģ��������ʵ����Ҫ���� compareTo() �������о���Ƚ�
   ����ȡ���ֵ���б��������ߣ������T�����࣬ע�⵽������ҲҪͬ���޸� */
/*  ����һ��ȡ�б����ֵ�ķ��ͺ������䷵��ֵ����ʱ��ǰ����������࣬���ҵ�ǰ������ĸ������ʵ����Compatable()�ӿڣ�����α����ǵ�ǰ�����������ļ���
<T extends Comparable*****>  ��ʾT����̳�ʵ����ĳ����/�ӿ�
<? super T>                  ��ʾʵ�ֱȽϽӿڵ�����T�ĸ���
<? extends T>                ��ʾ��α�����T������    */
public static <T extends Comparable<? super T>> T max(List<? extends T> list) {
	Iterator<? extends T> i = list.iterator();
	T ret = i.next();
	while(i.hasNext()) {
		T tmp = i.next();
		if(t.compareTo(result) > 0) ret = tmp;   
	}
	return ret;
}
// �������������ַ���д������һ��ʹ�����Ͳ������ڶ���ʹ��ͨ���
public static <E> void swap(List<E> list, int i, int j) { list.set(i, list.set(j, list.get(i))); }
// ���ַ�����Ҫ��װ��һ��д�������У���Ϊ List<?>���Ͳ�����ӳ�null֮���Ԫ��
public static void swap(List<?> list, int i, int j); 



/****                                               ��ķ���                                        ****/

import java.util.*;
public class RandomList<T> {      //  ��������������ŵ����ͣ������ж������ RandomList<A, B, C>
	private ArrayList<T> storage = new ArrayList<T>();   //  ʹ�������е��������ڲ��洢�����м���Ҳ�÷���T��ʾ
	private Random rand = new Random(47);
	public void add(T item) { storage.add(item); }      //  ����Ƿ��ͣ�������T��ʾ
	public T select() {                                 //  ����ֵ�Ƿ��ͣ�������T��ʾ
		return storage.get(rand.nextInt(storage.size()));
	}
}

/****                                               �ӿڵķ���                                        ****/
class Coffee {
	public static long counter = 0;
	public final long id = counter++;
	public String toString() {
		return "Coffee<" + id + ">";
	}
}
interface Generator<T> { T next(); }        //  �������Ľӿڶ��壬������
import java.util.*;
public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee> {  //  ʵ���������ӿ� �� �����ӿڣ���foreach�ã�
	private Class type = Coffee.class;         //   �����ľ���·�������硰class com.zxf.comp.Coffee��������֮�������󣬼�����
	public CoffeeGenerator() {};            //  Ĭ�Ϲ��캯�������ڴ���Ĭ�϶��󣬵���next() ���CoffeeԪ��
	public Coffee next() {                  //  ��ͨʵ�֣�ͨ��ǰ���������õ�����������󣬵��� next() ���ϻ��Ԫ��
		try {  return (Coffee)type.newInstance();  }
		catch(Exception e){	throw new RuntimeException(e); }
	}
	/* �ʺ� foreach �ĵ���ģʽʵ�֣�ͨ��ʵ�ֵ�����  */
	private int size = 0;                         
	public CoffeeGenerator(int sz) { size = sz; }   //  ������ʵ�ֵ��ô˹���������Ϊ��������Ԫ�ظ�������ȷ�ģ��������޵�����
	class CoffeeIterator implements Iterator<Coffee> {   //  �ڲ��࣬�����������ɵ�����
		int count = size;
		public boolean hasNext() { return count > 0; }
		public Coffee next() {
			count--;
			return CoffeeGenerator.this.next();       //  ���õ�ǰ���ɵ�������CoffeeGenerator����ı����next() ����
		}
		public void remove() { throw new UnsupportedOperationException(); }
	};
	public Iterator<Coffee> iterator() { return new CoffeeIterator(); }
}
public static void main(String[] args) {
	CoffeeGenerator cg = new CoffeeGenerator();
	System.out.println(cg.next());    //  ���������÷�
	for(Coffee cc : new CoffeeGenerator(10)) {  System.out.println(cc); }   // ���������÷�
}

/****                                               ���ͷ���                                        ****/
ʹ�÷��ͷ���ʱ����Ӧ������ʱ����ָ���������ͣ���������Ϊ�����ҳ��������͡���֮Ϊ���Ͳ����ƶϡ�
public class GenericMethods {   //  �ಢ���ǲ������ģ����� <T>
	public <T> void f(T x) {            //  �����Ͳ����б����ڷ���ֵ֮ǰ
		System.out.println(x.getClass().getName());
	}
}
public class GenericVarargs {
	public static <T> List<T> makelist(T... args) {   //  ���ͷ�����Ͽɱ�������˴��������Ͷ���T
		List<T> result = new ArrayList<T>();
		for(T item: args)
			result.add(item);
		return result;
	}
}

/****                                               ͨ���������/����ת�ͣ�                                        ****/
class Fruit {}
class Apple extends Fruit {}
class Jonathan extends Apple {}
class Orange extends Fruit {}

/**   �����ǿͻ����룬��λ��main�����У����û�жԻ����ķ�������а�װ����ֻ��ȡ���ܷţ���Ϊ���͵������ж����ڱ���׶Σ�
       ���н׶������Ѳ������ŵ�ʱ��ȷ���ǲ��Ƿŵ���ȷ���ͣ�����ڱ���׶ο���
**/
List<? extends Fruit> flist = new ArrayList<Apple>();
// flist.add(new Apple());   �������
// flist.add(new Fruit());   ����������ֻȷ�� flist�洢����Fruit��ĳ�������ͣ�����ȷ���������ĸ������ܵ������Ͳ�ƥ��
// flist.add(new Object());  �������
List<? extends Fruit> flist = Arrays.asList(new Apple());
Apple a = (Apple)flist.get(0);  //  ���� get Ԫ�أ����ǲ��� add Ԫ�أ���Ϊ���ʱ��ȷ��Ԫ�����ͣ�������
flist.contains(new Apple());  //  contains ���������յĲ���ת��Ϊ�� Object
flist.indexOf(new Apple());   //  indexOf   ���������յĲ���ת��Ϊ�� Object

// ����������ŵ�ʱ�򣬼��Ͽ����Ǹ���ļ��ϣ�������Ԫ�ط��룬��ʱ��super
public class GenericWriting {
	static <T> void writeExact(List<T> list, T item) { list.add(item); }  //  ��ͨ���
	static List<Apple> apples = new ArrayList<Apple>();
	static List<Fruit> fruit = new ArrayList<Fruit>();
	static void f1() {
		writeExact(apples, new Apple());
		// writeExact(fruit, new Apple());  ������󣬲����ݵ����ͣ�found Fruit, required Apple
	}
	// �ڱ����õĺ�����ʹ�� super����ʾ���յĵ�һ�����������Ϳ�����  T���͵ĸ���ļ��ϣ�
	//  �� װTͨ�����͵�  ����List ��ȷ�����Ϳ����� List<T�ĸ���>�������ַ�ʽ����ʱ�� super
	// �˴� T�Ͷ�Ӧ�ļ�����ͬһ�㣬��ҪT�ĸ���ļ��Ͻ��ܾ����TԪ��
	static <T> void writeWithWildcard(List<? super T> list, T item) { list.add(item); }
	staic void f2() {
		writeWithWildcard(apples, new Apple());
		writeWithWildcard(fruit, new Apple());
	}
}
//  �Ӽ�������ȡ��ʱ�򣬼��Ͽ���������ļ��ϣ�ȡ����ת���ɸ���Ԫ�أ���ʱ��extends
public class GenericReading {
	static <T> T readExact(List<T> list) { return list.get(0) }
	static List<Apple> apples = Arrays.asList(new Apple());
	static List<Fruit> fruit = Arrays.asList(new Fruit());
	static void f1() {
		Apple a = readExact(apples);
		Fruit f = readExact(fruit);
		f = readExact(apples);      //  �����ķ��ͷ���������ȷ���У�ȡ������Apple���ͻ��������ת��
		
	static class Reader<T> {    //  ������һ�������࣬����ӵ��һ����Ϊ readExact �ķ��ͷ���
		T readExact(List<T> list) { return list.get(0); }  
	}
	static void f2() {
		Reader<Fruit> fruitReader = new Reader<Fruit>();
		Fruit f = fruitReader.readExact(fruit);
		//  �����������Ѿ������˱���ֻ�ܴ���T��һ�����ͣ���˶���������/��д�෶Χ�����Բ�����
		// Fruit a = fruitReader.readExact(apples);  �������List<Fruit> ���Ͳ���Ӧ���� List<Apple> ����
		
	static class CovariantReader<T> {  //  ������һ�������࣬����ӵ��һ����Ϊ readExact �ķ��ͷ�������������ν����˵���
		T readCovariant(List<? extends T> list) { return list.get(0); } // ������ν�������T����������Ϊ����
		//  �˴���������� List �� T ����ͬһ�㣬��˼�ǽ��� T������ļ��� ��Ϊ��Σ�����T���͵Ĳ���
	}
	static void f3() {
		CovariantReader<Fruit> fruitReader = new CovariantReader<Fruit>();
		Fruit f = fruitReader.readCovariant(fruit);
		Fruit a = fruitReader.readCovariant(apples);
	}
	
	
/****                                               ����                                        ****/
�෽����ȡ��
import java.lang.reflect.*;
import java.util.regex.*;
public class ShowMethods {
	/* ʹ�� java ShowMethods className ��ȡ������з����͹��캯��  ���� 
	   ʹ�� java ShowMethods className methodName ��ȡ���а���������methodName�ĺ���  */
	private static String usage = "Usage:\n" +
								  "ShowMethods qualified.class.name\n" +
								  "To show all methods in class or:\n" +
								  "ShowMethods qualified.class.name word\n" +
								  "To search for methods involving 'word'";
	/* ��ʾƥ������ . ֮ǰ�ж����������(w+)��ģʽ */
	private static Pattern p = Pattern.compile("\\w+\\."); 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length < 1) {
			System.out.print(usage);
			System.exit(0);
		}
		int lines = 0;
		try{
			Class<?> c = Class.forName(args[0]);  /* ��ͨ��������������� */
			Method[] methods = c.getMethods();    // ��ø�������з��� 
			Constructor[] ctors = c.getConstructors();  //  ��ø�������й��캯��
			
			if(args.length == 1) {
				for(Method m : methods) {
					/* ���� public static void ShowMethods.main(java.lang.String[]) */
					Print.print(m.toString());
					/* ���� public static void main(String[]) */
					/* ע��˴�������ʽ���ҵ�����.֮ǰ�ĵ���֮�󣬵���replaceAll("")��wȫ��ɾ���� */
					Print.print(p.matcher(m.toString()).replaceAll(""));
				}
				for(Constructor ct : ctors)
					/* ���� public ShowMethods()  ע��˴�����һ���������Զ����ϵ�Ĭ�Ϲ��캯�� */
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

��̬����
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

		
/****                                               ���߳�                                        ****/
1��Java���߳����ǿ���������������A����synchronized����A��ʱ����A�ڲ�Ҳ����ͬʱ����synchronized����B�����������1������������룬������������ͬʱ���������ڼ̳��Ի���ͬ��������������synchronized����A���Ե��ø���synchronized����B
2�������쳣ʱ����ǰ���е������Զ��ͷ�
3��synchronized ��ǲ����м̳��ԣ�������override�ķ���������Ȼ�� synchronized ���
4����������String�ĳ��������ԣ�synchronized(String)ʱ����Ҫע�������ַ���������ͬһ������Ҫע���������ˣ���Ҫ��String����
5��Ҫ�����ε��������������⣬�� ����A��synchronizedX������synchronizedY�飬ͬʱ����B��synchronizedY������synchronizedX��
	jdk��װĿ¼�µ�bin�ļ������롰jps���鿴��ǰ����java�̣߳������롰jstack -l threadId���鿴ָ���߳�״̬
6��volatile�ؼ��ֽ����α�������֤�����Ŀɼ��ԣ�������֤������ԭ���ԣ���������ʹ�ò�������ԭ���ԣ���˲��ܴ���ͬ���ԣ�
7���ؼ���synchronized�������volatileͬ���Ĺ��ܣ�����֤�����ԺͿɼ��ԣ�
8������wait()ִ�к��ͷ�����notify()ִ�к��ͷ��������ǵȵ�����synchronized�����ִ������˳����ͷ�
9�����̴߳���wait()״̬ʱ�����ø��̶߳����interrupt()��������� InterruptedException �쳣
10��ʹ��synchronized�Ĵ������棬������������Ҫ��ReentrantLockʹ�ã�ҲҪ��Conditionʹ��



һ����̵߳�д���Ƕ��屾����ҵ����� MyTask��Ȼ����һ�� Thread ������࣬�ڲ����� MyTask ����(����ģʽ)���� run ������ override �е��� MyTask ����
public class MyTask {
	/* �������ڲ�ʵ��������û�������ط���֤(���÷�����֤)���Ƿ��̰߳�ȫ�ģ���Ϊ����̹߳����������  */
	private int notSafe = 5;   

	private Object otherObj = new Object();
	/* ����synchronized�����̰߳�ȫ���ˣ�����notSafe����ֵ�����̰߳�ȫ */
	/* ע��synchronized������������ģ�Ĭ����this����ͬ�Ķ�����ܹ���һ����(��������)����������Ҳ�У���ɱ����̫�� */
	synchronized public void changeVar() {
		if(stateA) 
			notSafe = 11;
		else 
			notSafe = 22;
	}
	
	/* ͬ����������/��ȡ������Ҫ��ͬһ����ͬʱ��ס������set�������л�CPU��Դ�󣬿��ܳ������ */
	synchronized public void setVar(int a) {};
	synchronized public int  getVar() {};
	
	/* ͬ������飬�����÷�����Ҫ����ȷ�ϲ�Ҫ©������� */
	public void partSync() {
		doSomethingOne;
		synchronized(this) {
			doSomethingTwo;
		}
		doSomethingThree;
	}
	
	/* ����������������֤�����н���otherObj����Ļ������ǻ���ģ���this������������Ĵ�����Ȼ���첽�ģ��Ż��������Ч��  */
	public void otherObjLock() {
		synchronized(otherObj) { doSomethingTwo; }
	}
	
	/* ��̬ͬ��synchronized�����������ǵ�ǰ���࣬�������Զ�������ж��������� */
	synchronized public static void staticMethod() { }
	/* ��̬ͬ��synchronized����飬Ҳ������ */
	public static void staticBlock() {
		synchronized(MyTask.class) {}
	}
	
	
	/* ��synchronized�������Ƕ�ռ�ģ���ʹһ�������synchronized�������ڱ����ã���synchronized����Ҳ����ͬʱ������  */
	public void safeVar() {
		int isSafe = 9;   //  �����ڲ��ı��������̰߳�ȫ�ģ����ÿ�η��������ڵ���ջ��ʵʱ���ɵģ�ÿ���̶߳���
	}
}

/* ReentrantLock ������ȫ��������Ч������ͬһʱ��ֻ��һ���߳���ִ��ReentrantLock.lock()����Ĵ��룬Ч�ʽϵ��� */
public MyTaskTwo {
	/* �����������ܹ����и���ϸ���Ĺ�����Lock��������Ƕ�������������synchronized��Ҫ��������(����Ĭ�ϲ���) */
	private Lock lock;
	private Condition conditionAB;
	private Condition conditionCD;
	
	public MyTaskTwo(boolean isFair) {
		super();
		/* ��ƽ����ʾ�̻߳�ȡ����˳���ǰ����̼߳�����˳��������ģ���FIFO˳�򣻷ǹ�ƽ��������ռ���ƻ�ȡ���������ȡ */
		/* ����������ReentrantLockĬ�Ϲ��캯���Ƿǹ�ƽ�� */
		lock = new ReentrantLock(isFair);   // ������ƽ��������true��ʾ��ƽ����false��ʾ�ǹ�ƽ��
		conditionAB = lock.newCondition();
		conditionCD = lock.newCondition();
	}
	
	public void methodA() {
		try {
			lock.lock();
			doSomething();
			conditionAB.await();   //  �൱�� Object.wait();
			// conditionAB.await(long);  �൱�� Object.wait(long);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public void methodB() {
		try {
			lock.lock();
			conditionAB.signal();   // �൱�� Object.notify();
			// conditionAB.signalAll();   �൱�� Object.notifyAll();
		}finally{
			lock.unlock();
		}
	}
	
	public void methodC() {
		try {
			lock.lock();
			doSomething();
			conditionCD.await();   //  �൱�� Object.wait();
			// conditionCD.await(long);  �൱�� Object.wait(long);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public void methodB() {  // �����ڿͻ������ﱻ���ã��������ѵȴ����߳�
		try {
			lock.lock();
			conditionCD.signal();   // �൱�� Object.notify();
			// conditionCD.signalAll();   �൱�� Object.notifyAll(); �������� await �߳�
		}finally{
			lock.unlock();
		}
	}
	
	public void otherMethod() {
		int myCount = 0;
		boolean isTrue = 0;
		
		// ��ѯ��ǰ�ֳɱ��ִ������ĸ�����Ҳ���ǵ���lock()�����Ĵ���
		myCount = lock.getHoldCount();   
		// ������ȴ���ȡ���������̹߳���������5���߳���await()״̬������һ��������������ߣ���ʱ����������4����4���̵߳ȴ���
		myCount = lock.getQueueLength();
		// ��õȴ���صĸ�������Condition���̹߳�����������10���̶߳���ĳcondition.await()���򷵻�10
		myCount = lock.getWaitQueueLength(conditionCD);
		
		// ��ѯָ���߳��Ƿ����ڵȴ���ȡ�������ڿͻ�������ã��˴�����ʾ��
		isTrue = lock.hasQueuedThread(threadA);
		// ��ѯ�Ƿ����߳����ڵȴ�������йص�condition����
		isTrue = lock.hasWaiters(conditionAB);
		// �ж��ǲ��ǹ�ƽ��
		isTrue = lock.isFair();
		// �жϵ�ǰ�߳��Ƿ񱣳ָ�������״̬����lock()���ú�״̬
		isTrue = lock.isHeldByCurrentThread();
		// �жϴ����ǲ����������̱߳��֣��������һ���̵߳��ù�lock()�һ�δ����unlock()���򷵻�true
		isTrue = lock.isLocked();
		// ��ǰ�߳�δ���жϣ����ȡ����������Ѿ����жϣ�������쳣��
		lock.lockInterruptibly(); 
		// �ڵ���ʱ��δ�������̱߳��ֵ�����£���ȡ��
		if(lock.tryLock())
			System.out.println("get it");
		// ���ڸ���ʱ����û�������̻߳�ȡ���ҵ�ǰ�߳�δ���жϣ���ȡ����
		isTrue = lock.tryLock(3, TimeUnit.SECONDS);
		
	}
}
/* ��д��ReentrantReadWriteLock�࣬�ڲ���һ����������ص�����Ҳ�й�������һ��д��ص�����Ҳ��������  */
public MyTaskTwo {
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	public void read() {
		try {
			//  ����������ʱ���첽����
			lock.readLock().lock();
			doSomething();
		}finally{
			lock.readLock().unlock();
		}
	}
	
	public void write() {
		try {
			//  д������д���⣬д�����⣬дд���⣻
			lock.writeLock().lock();
			doSomething();
		}finally{
			lock.writeLock().unlock();
		}
	}
}

public class MyThread extends Thread {
	//  �߳��ڲ���˽�����ݣ��洢���̶߳�ռ���ڴ��ڣ�����Ƕ��̰߳�ȫ�ģ����̶߳��У���ע�ⲻ����static��ÿ��newҪ������
	private int count = 5;  
	private MyTask task;
	
	/* ÿ���߳��Լ��ı��������߳����Լ�������count��ÿ��MyThread�̶߳�����У�t1������MyThread������  */
	public static ThreadLocal t1 = new ThreadLocal();
	t1.set(anyObj)   // �������
	if (t1.get() != null)
		correspondingType = ti.get();     // ȡ����������Ҫ�û��Լ��ƿ�
	
	MyThread(MyTask task) {
		super();
		this.task = task;
	}
	
	@override
	public void run() {
		super.run();
		//  Thread.currentThread() ��ȡ��ǰ��������̵߳����ã�  getName() ����߳�����һ��newͬ�����е�setName�����ã�
		System.out.println("run�����Ĵ�ӡ��" + Thread.currentThread().getName());
		
		Thread.yield();   //  ��ǰ�ֳɷ���CPU��Դ���ø������Ŷӵ��̣߳���Ȼ�����¸��̻߳�������CPU��Դ���Լ�����
		
		/* ����ʱ�ⲿinterrupt��ֹ�̣߳������̲߳���InterruptedException�жϣ��������ߵ���ǰ���interrupt  */
		Thread.sleep(50000);    
		/*  ͨ���׳��쳣�ķ������ڴ�������ֹ�̣߳��������ⲿ���� mythread.interrupt() ������Ȼ���߳���ֹ */
		if( this.interrupted() ) {
			throw new InterruptedException();
		}
		
	}
}	
/* ʹ��ThreadLocal������Ĭ��ֵ�ķ�����������̳к�����initialValue()������ע����Ҫ��װ���װ��̬ThreadLocalExt�����ʹ��
   ���� Tools.t1.get() ��main������þ���main�����ŵ�ֵ����mythread������þ���mythread���ŵ�ֵ */
public class ThreadLocalExt extends ThreadLocal {
	@override
	protected Object initialValue() { return "I am the default value"; }  //  ע���������ֻ�ܱ����أ��������ⲿ����
}
/* �����̴߳Ӹ��߳�ȡ��ֵ��ע�������������������������з���  */
public class InheritableThreadLocalExt extends InheritableThreadLocal {
	@override
	protected Object initialValue() { return new Date().getTime(); }  // ͨ��������ȡ�ø��̵߳�ֵ
	@override
	// ͨ��������ȡ�ø��̵߳Ķ��󲢽�һ���ӹ����˴�������initialValue()�ķ���ֵ
	protected Object childValue(Object parentValue) { return parentValue + "my add thing"; } 
}

public class Test {
	public static void main(String[] args) {
		MyTask mytask = new MyTask();
		MyThread mythread = new MyThread(mytask);
		mythread.setName("A");   //  �����߳���
		//  mythread.run()     //  ֱ����main �߳��е��ú�����û���������̵߳���
		
		/* ��̨�߳���( run()���� )�������߳���Ȼ�Ǻ�̨�̣߳�
		   ��̨�߳����finally�־䲻��ִ�У���Ϊ����ǿ�йرպ�̨�̣߳�����ִ�еĻ��� */
		mythread.setDaemon(true);    //  ����ǰ�߳�����Ϊ�ػ��̣߳������з��ػ��߳̽������ػ��߳��Զ�����
		
		mythread.start();     //   ��mythread�̷߳����̶߳��У�׼��ִ��
		mythread.isAlive();   //   �ж�mythread�߳��Ƿ��ڻ״̬��start() ֮ǰ��false��֮����true
		mythread.getId();     //   ��ȡmythread�̵߳�Ψһ��ʶ
		
		/* ������ʹ���߳���ͣ����Ϊ�߳����ִ��ʱռ���Ż�����Դ������ͣ���ͷ���Դ��������Դ�޷��������̵߳���
			���͵���println() ֮���I/O��Դ�ر����ױ�����ʶ�Ķ�ռ��
			println()�ڲ����������߳�����println()��ʱ����ͣ�����������̶߳������ٵ���println()
			���⣬��ͣ�п��ܵ��¶���״̬��ͬ�������������޸Ķ���3��״ֵ̬�Ĺ����б���ͣ����ʱ��ӡ��������״̬�ͻ᲻ͬ��
		*/
		mythread.suspend();   //  ��ͣ�̣߳�������ʹ��
		Thread.sleep(500);    //    �ڵ�ǰ�����߳������ߣ���λ�Ǻ���
		mythread.resume();    //  �ָ���ͣ���̣߳�������ʹ��
		
	/* join���ڲ�ʹ��wait()�������еȴ���synchronizedʹ�á������������ԭ����ͬ����join�����е�ǰ�̱߳��жϣ���ǰ�߳��׳��쳣 */
		mythread.join();      //  ��ǰ�߳��������������ȴ�mythread�߳����٣��������쳣���ɣ�
		// �ڲ�ʹ��wait(long)ʵ�֣������ͷ������ص㣬�����߳̿��Ե��ñ����󷽷�����sleep(long)���ͷ���
		mythread.join(1000);  //  ��ǰ�߳������ȴ����1�룬�ȴ����ں���Ҫ������������첽�ģ��������߳�˭�ȵ��ò�ȷ��
		
		mythread.interrupt();   //  ��ֹ�߳�mythread��������������ֹ
		/*  ���Ե�ǰ������ڶ��������߳��Ƿ���ֹ��ע�� interrupted()�Ǹ���̬���� public static boolean interrupted()��
		    ���ʵ���ϵ�ͬ�ڵ��õ� Thread.currentThread().interrupted()��
			���Ե�ʱ��ǰ������ڶ�����˴˴��Ƕ�����main������mythread��
			ע�⣺�÷���������ж�״̬������������ε���interrupted()�����µ�һ����false���ڶ���Ҳ��Ȼ��true
		*/
		mythread.interrupted();  
		/*  �����̶߳����Ƿ��Ѿ���ֹ���˴����Ե���mythread������û������ж�״̬Ч�� */
		mythread.isInterrupted();
		
		/* �߳����ȼ����м̳��ԣ������̼̳߳и��̵߳����ȼ����������ȼ����ǳ�������10��1�Ĳ�࣬������ֲ�����  */
		/* ��Ҫ�ڹ��������������ȼ�����run() ������ʼλ������ */
		Thread.currentThread().setPriority(6);   // ���õ�ǰ�߳����ȼ�Ϊ6��Ĭ����5��
		Thread.currentThread().setPriority(Thread.NORM_PRIORITY + 3);   // ���õ�ǰ�߳����ȼ�Ϊ8���˴�ʹ���˿������ȼ�ö��
		Thread.currentThread().getPriority(6);   //  ��ȡ��ǰ�߳����ȼ�
		
		/* ��ȡ�̵߳�״̬����Ҫ�� NEW RUNNABLE TERMINATED BLOCKED WAITING TIMED_WAITING */
		Thread.currentThread().getState();
		mythread.getState();
	}
}

/***   ������/������    ***/
//  ��װMyStack������̵߳�Run()�������и���ѭ���ڲ�ͣ�ĵ���push()��pop()���������򷽷�ִ��һ����˳��ˣ��߳����������ͣ�һ����ͣ����push()����һ�����Ͳ�ͣ����pop()
public class MyStack {            
	private List list = new ArrayList();
	synchronized public void push() {
		try {
			/* ������if��������while��
			������wait��ֹͣ�ˣ�����notify֪ͨ������wait״̬�̶߳������ѣ��п��ܵ����쳣
			*/
			while(list.size() == 1) { this.wait();}   // �����������˴���ѭ�����ڵȴ�������������Դ
			list.add("anyString=" + Math.random());
			this.notify();   //  notify����֮��Ҫ�ȵ�synchronized��ִ����Ż��ͷ���
			System.out.println("push=" + list.size());
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	synchronized public String pop() {
		String returnValue = "";
		try {
			/* ������if��������while��
			������wait��ֹͣ�ˣ�����notify֪ͨ������wait״̬�̶߳������ѣ�list.remove(0)�ᵼ���쳣
			*/
			while(list.size() == 0) { this.wait(); }   //  �˴���ѭ�����ڵȴ�������������Դ
			returnValue = "" + list.get(0);
			list.remove(0);
			this.notify();  //  notify����֮��Ҫ�ȵ�synchronized��ִ����Ż��ͷ���
			System.out.println("pop=" + list.size());
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		return returnValuel
	}
}


Executor��ʹ�ã�ע��ÿ���̵߳��쳣��ֻ���Լ��������ܴ��ص��������̵߳��̣߳�����main�����̣߳���Ҫ������Ҫʹ���½ӿ�
import java.util.concurrent.*;
public class CachedThreadPool {
	public static void main(String[] args) {
		/* ExecutorService�Ǿ��з����������ڵ�Executor ��ע��˴��Ǿ�̬�ഴ���ģ����ƹ�������*/
		/* newCachedThreadPool һ��ᴴ��������������ͬ���߳� */
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i=0; i<5; i++) 
			exec.execute(new MyTask());
		/* shutdown() ��ֹ�������ύ��Executor */
		exec.shutdown();
		
		/* ʹ�����޵��߳���ִ�����ύ�����񣬴˴��޶�Ϊ5���̣߳�һ��������̷߳��䶯������ʡ���ܵĴ����̵߳ĸ߰����� */
		ExecutorService execB = Executors.newFixedThreadPool(5);
		for(int i=0; i<5; i++) 
			execB.execute(new MyTask());
		execB.shutdown();
		
		/* ���̣߳�˳��ֻ�����е�Task���ڲ��Լ�ά��һ��FIFO��������У���Ҫ���µ��ļ�ϵͳ����ĺ�ѡ�� */
		ExecutorService execC = Executors.newSingleThreadExecutor();
		for(int i=0; i<5; i++) 
			execC.execute(new MyTask());
		execC.shutdown();
	}
}

�������в�������ֵ�� ʹ��callable �ӿڴ��� runnable �ӿ�
import java.util.concurrent.*;
import java.util.*;
/* �ӿ��еķ��ͱ�������ֵ������ */
class TaskWithResult implements Callable<String> {
	private int id;
	public TaskWithResult(int id) {
		this.id = id;
	}
	/* �̵߳��õ� call() �������з������͵ģ���ӿ���ָ���ķ���һ�� */
	public String call() {
		try	{
			Thread.sleep(1000*id);  //  ����get() ������Ч������Ȼ�����߳�һ�����������ǻ������һ��������ʾ��Ч��
	/* ��һ���߳�����ʱ����ã�����ÿ���߳�����ʱ�䲻һ����һ���̵߳�get() ��Ȼ�����������߳�ִ����Ϻ�Ľ����ӡ
		//  Thread.sleep(1000*(10 - id));  
	*/
		// TimeUnit.MILLISECONDS.sleep(1000);   //  Java 1.5/6 ���·��
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		return "result of TaskWithResult " + id;
	}
}
public class CallableDemo {
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		/* Future<T> �����洢����ķ��ؽ��  */
		ArrayList<Future<String>> results = new ArrayList<Future<String>>();
		
		for(int i=0; i<10; i++) {
			/* submit() �ύ��ִ��һ��������̣߳����ؽ�������Future���� */
			results.add(exec.submit(new TaskWithResult(i)));
		}
		for(Future<String> fs : results)
			try{
				/* ͨ��get() ����ȡ����� ���Ե���isDone() �����Future�Ƿ���ɡ�ֱ�ӵ��� get()��������ֱ�����׼������ */
				System.out.println(fs.get());
			}catch(InterruptedException e) {
				e.printStackTrace();
			}catch(ExecutionException e) {
				e.printStackTrace();
			}finally{
				exec.shutdown();      //  �ر��̳߳أ��������������
			}
	}
}

�칹�����л�ʵ�� �� ͬ������Ĳ��зֽ�ͽ������
// �˴�ʵ��һ��ҳ����Ⱦ��������Ⱦ���ֺ���ȾͼƬ֮��ʵ���칹���У���ÿ��ͼƬ��ʵ�в�����Ⱦ������
//  ExecutorCompletionService ��һ���ExecutorService�൱��ʵ����һ����������BlockingQueue�����ڻ������������̵߳ķ��ؽ��
//  �о�ExecutorCompletionService �ľ���ʵ���Ǹ�װ�������Է��ؽ����һ�������˷�װ����
public class Renderer {
	private final ExecutorService executor;
	Renderer(ExecutorService executor) { this.executor = executor; }      //  ��Ҫ�ⲿ������һ���̳߳�
	void renderPage(charSequence source) {
		List<ImageInfo> info = scanForImageInfo(source);
		//  ExecutorCompletionService�ڹ��캯���д���һ��Blockingqueue�����������ɵĽ��
		CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executor);
		for(final ImageInfo imageInfo : info)
			completionService.submit(new Callable<ImageData>() {     // ���͵�
											public ImageData call() { return imageInfo.downloadImage(); }
										});
		renderText(source);     //  ��Ⱦ���֣��˴����칹����
		try{
			for(int t=0, n=info.size(); t<n; t++) {
				Future<ImageData> f = completionService.take(); // �ڵó����֮ǰ�������н����ȡ������ѭ����û����������ȴ�
				ImageData imageData = f.get();    //  ���õ��Ľ��ȡ��
				renderImage(imageData);    // ��Ⱦ����ͼƬ���˴���ͬ������
			}
		}catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}catch(ExecutionException e) {
			throw launderThrowable(e.getCause());
		}
	}
										
}

������Ч�������Ľ������
public class Memoizer<A, V> implements Computable<A, V> {
	private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();  // ʹ�ò���HashMap
	private final Computable<A, V> c;
	public Memoizer(Computable<A, V> c) { this.c =c; }
	public V compute(final A arg) throws InterruptedException {
		while(true) {
			Future<V> f = cache.get(arg);        //  ��ȡHashMap�л���� Future<V> ֵ��������ֱ�ӷ��أ���û���򷵻� null
			if(f == null) {
				Callable<V> eval = new Callable<V>() {
										public V call() throws InterruptedException { return c.compute(arg); }
										};
				FutureTask<V> ft = new FutureTask<V>(eval);    //  ����һ�������ؽ����Task����
				// ��ft�����ڻ������Ƿ���ڣ������򷵻����Future<V>�������ڷ��� null���ؼ��ǲ鿴key(��arg����)
				f = cache.putIfAbsent(arg, ft);     
				// ft���񲻴��ڣ����̵߳ȳ�������˴����ñ��������ⲿ�����߳����ܣ�������run
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


5��ͬ�������ࣺ��������(BlockingQueue)������(latch)�������ȴ�����ֵ������(FutureTask)���ź���(Semaphore)��դ��(Barrier)

����(latch)��ʹ�ã�ͨ���ȴ�/��բ�Ĳ�����ʹһ�����߶���̵߳ȴ�һ���¼�������
����״̬����һ�����������ü���������ʼ��Ϊһ����������ʾ��Ҫ�ȴ����¼�������
countDown()�����ݼ�����������ʾ��һ���¼�������������ֵ�ǣ�await()��һֱ������������Ϊ�㣬�����߳��жϻ����̳߳�ʱ
public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
	final CountDownLatch startGate = new CountDownLatch(1);   //  �����ȴ�����Ϊ1���߳�
	final CountDownLatch endGate = new CountDownLatch(nThreads); // �����ȴ�����ΪnThreads���߳�
	
	for(int i=0; i<nThreads; i++) {
		Thread t = new Thread() {    //  ��n���߳�ʵ����ÿ���߳�ʵ������һ��task����
			public void run() {
				try {
					startGate.await();          //  �ȴ������߳̿�բ
					try {
						task.run();            //   ��բ������ҵ��
					}finally {
						endGate.countDown();        //  ������ҵ�񣬼����������������֪ͨ���߳��������
					}
				}catch(InterruptedException ignored){
					
				}
			}
		};
		t.start();            //  �����߳�
	}
	long start = System.nanoTime();         //  ���߳̿�բǰ��¼ʱ��
	startGate.countDown();                  //  ���߳̿�բ����ʱ���еȴ��������̴߳�await()״̬������
	endGate.await();         //  �ȴ����һ�������߳̿�բ�������������߳�ִ����ϣ��������������������
	long end = System.nanoTime();          //    ��¼���һ�������߳�ִ����ϵ�ʱ��
	return end - start;                    //  �����в��������̴߳�����������������ʱ����Ϊ�������
}

�����ȴ�����ֵ������(FutureTask)��ͨ��Callableʵ�֣��൱�ڿ����ɽ����Runnable��
Future.get����Ϊȡ��������״̬����������Ѿ���ɣ�get���������ؽ��������get����֪��������ɣ������׳��쳣��������п��̴߳���
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
//  ǿ�ƽ�δ����Throwableת��ΪRuntimeException
public static RuntimeException launderThrowable(Throwable t) {
	if(t instanceof RuntimeException)
		return (RuntimeException) t;
	else if(t instanceof Error) 
		throw (Error) t;
	else
		throw new IllegalStateException("Not unchecked", t);
}
public class Preloader {
	private final Future<ProductInfo> future =  //  ����һ�����񣬸����񷵻�ProductInfo����
			new Future<productInfo>(new Callable<ProductInfo> {
				public ProductInfo call() throws DataLoadException {
					return loadProductInfo();
				}
			});
	private final Thread thread = new Thread(future);   //  �������̵߳��ø�����
	public void start()	 { thread.start(); }            //  �ṩstart()�������ⲿ�����߳�
	public ProductInfo get() throws DataLoadExceptioin, InterruptedException {
		try {
			return future.get();        //  �����ȴ����������߳�����ɲ����ؽ��ProductInfo����
		}catch(ExecutionException e) {
			Throwable cause = e.getCause();
			if(cause instanceof DataLoadException)
				throw (DataLoadException) cause;
			else
				throw launderThrowable(cause);
		}
	}		
}

�ź����������ź�����������ͬʱ����ĳ���ض���Դ�Ĳ�������������ͬʱִ��ĳ��ָ��������������Ҳ��������ʵ��ĳ����Դ�أ����߶�����ʩ�ӱ߽�
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
public class BoundedHashSet<T> {  // ʵ��һ���н�������������ź����ļ���ֵ���ʼ�����������������ֵ
	private final Set<T> set;
	private final Semaphore sem;       //  ����һ���ź�������
	
	public BoundedHashSet(int bound) {
		this.set = Collections.synchronizedSet(new HashSet<T>());   //  װ��ģʽ����HashSetʹ��synchronizedSetװ�Σ�֧�ֲ���
		sem = new Semaphore(bound);    //  ��ʼ���ź�������bound���ź�����Դ
	}
	public boolean add(T o) throws InterruptedException {
		sem.acquire();                //  ռ��һ���ź�����Դ�������ǰû���ź�����Դ���������ڴ�
		boolean wasAdded = false;
		try {
			wasAdded = set.add(o);
			return wasAdded;             //  ����ӽ��״̬����
		}finally {
			if(!wasAdded)
				sem.release();         //  ������Ԫ��ʧ�ܣ��ͷ��ź�����Դ
		}
	}
	public boolean remove(Object o) {
		boolean wasRemoved = set.remove(o);
		if(wasRemoved) 
			sem.release();
		return wasRemoved;
	}
}

դ���������ڱ���������һ���߳�֪��ĳ���¼�������դ��������Ĺؼ��������ڣ������̱߳���ͬʱ����դ��λ�ã����ܼ���ִ�С��������ڵȴ��¼�����դ�����ڵȴ������̡߳�
public class CellularAutomata {
	private final Board mainBoard;
	private final CyclicBarrier barrier;
	private final Worker[] workers;
	/* Ҫִ�е����񣬱��������߳���ִ����Ϻ���wait()���ȴ���ֱ�������߳�ִ�е����񶼵���await()֮�������߳��������� */
	private class Worker implements Runnable {
		private final Board board;
		public Worker(Board board) { this.board = board; }
		public void run() {
			while(!board.hasConverged()) {
				for(int x=0; x<board.getMaxX(); x++)
					for(int y=0; y<board.getMaxY(); y++)
						board.setNewValue(x, y, computeValue(x, y);        //  ����ľ��幤��������ҵ����Ҫʵ��
				try {
					barrier.await();          //  �̼߳���ȴ���
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
		int count = Runtime.getRuntime().availableProcessors();  //  ��ȡ��ǰCpu�ĸ�������Ϊ�߳�������(���������)
		/****  ����դ������һ�������Ǽ��ϵ��߳���������await()��֪���Ƿ���Կ�����
		                 �ڶ���������դ���������ѡִ�е��������̣߳����ڽ���һЩ��β���� ***/
		this.barrier = new CyclicBarrier(count, new Runnable() { public void run() { mainBoard.commitNewValues(); } });
		this.workers = new Worker[count];
		for(int i=0; i<count; i++)
			workers[i] = new Worker(mainBoard.getSubBoard(count, i));  //  ��ʼ�������񼯺�
	}
	public void start() {
		for(int i=0; i<workers.length; i++)
			new Thread(workers[i].start();       //  ����������
		mainBoard.waitForConvergence();
	}
}

/****                                               NIO                                        ****/
NIO����ҪChannel��ʵ�֣�
FileChannel : ���ļ��ж�д����
DatagramChannel : ͨ��UDP��д�����е�����
SocketChannel : ͨ��TCP��д�����е�����
ServerSocketChannel : ͨ�������½�����TCP���ӣ���Web��������������ÿ���½��������Ӷ��ᴴ��һ��SocketChannel

��������������һ�����д�����ݣ�Ȼ����ж�ȡ���ݵ��ڴ档����ڴ汻��װ��NIO���󣬲��ṩ��һ�鷽������������ķ��ʸÿ��ڴ档
NIO����Ҫ��Bufferʵ�֣�
ByteBuffer        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
CharBuffer        CharBuffer charBuffer = CharBuffer.allocate(1024);  
DoubleBuffer
FloatBuffer
IntBuffer
LongBuffer
ShortBuffer
MappedByteBuffer : ���ڱ�ʾ�ڴ�ӳ���ļ�


// ��ʼ��һ��buffer������ռ�
ByteBuffer.allocate(48);
// ͨ��ͨ��д��Buffer
inChannel.read(ByteBuffer);	
// �����ݴ�bufferд��ͨ��
int writeSize = inChannel.write(buf);
// ����put����д�����ݵ�buffer
buf.put(127);
// ʹ��get��ȡ���ݵ����ر���
byte tmp = buf.get();
// �л���ǰbuffer�Ķ�/дģʽ��д�л�����ʱ����ͬʱ��limit���óɵ�ǰpositionֵ��position����Ϊ0��
buf.flip();
// ��position���0�������ض�buffer�е��������ݣ�limit���ֲ���
buf.rewind();
// ���buffer������position���0��ͬʱlimit���ó�capacity(��ʼ��ʱ����Ĵ�С)
buf.clear();
// ����Ѿ����������ݣ��κ�δ�������ݶ����Ƶ�����������ʼ������д������ݽ��ŵ�������δ�����ݵĺ��棬limit���ó�capacity
buf.compact();
// capacity���һ���ض���position��ʹ��reset()���Իָ������position
buf.mark();
buf.reset();
	

/* ʾ�� */	
public class UseNio {
	public static void useNio() {
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile("./1.txt", "rw");    // �����ļ�����
			FileChannel inChannel = aFile.getChannel();       // ���ļ������ϻ�ȡͨ��  
			ByteBuffer byteBuffer = ByteBuffer.allocate(48);  // ��������Ϊ48�ֽڵ�buffer
			int result = inChannel.read(byteBuffer);      // ��ȡ48�ֽڵ����ݣ�����buffer
			
			while(result != -1) {
				System.out.println("Read: " + result);
				byteBuffer.flip();                            //  ����buffer�л�ģʽΪ��ģʽ
				
				while(byteBuffer.hasRemaining()) {
					System.out.println((char)byteBuffer.get());  // ÿ�ζ�ȡ1�ֽ�
				}
				
				/* ���buffer��׼���ٴ�д�룬clear()�����������������compact()ֻ������Ѿ���ȡ�Ĳ��֣�����δ�����ַŵ��ͷ
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

scatter/gather ������Channel�ж�ȡ����д�뵽Channel�Ĳ�����
scatter �ڶ�����ʱ����ȡ����������д����buffer�У����罫һ����Ϣ����Ϣͷ����Ϣ��д�벻ͬ��buffer
/* ����ÿ���ƶ�����һ��bufferǰ������������ǰbuffer����˲������ڶ�̬��Ϣ(����Ϣ��ʽ��С���̶��İ�) */
public void scatter() {
	ByteBuffer header = ByteBuffer.allocate(128);
	ByteBuffer body = ByteBuffer.allocate(1024);
	ByteBuffer[] bufferArray = { header, body };
	/* ��һ��buffer��д����channel����������һ��buffer��д�� */
	channel.read(bufferArray);
}

gatter  ��д����ʱ�����buffer������д��ͬһ��Channel
/* д��position��limit֮������ݣ�����ܽϺõĴ���̬��Ϣ������Ҫ�������Լ��ڸ���buffer�з�����ʵ����ݣ�Ȼ������д��ͨ�� */
public void gather() {
	ByteBuffer header = ByteBuffer.allocate(128);
	ByteBuffer body = ByteBuffer.allocate(1024);
	// д�����ݵ�buffer
	ByteBuffer[] bufferArray = { header, body };
	channel.write(bufferArray);
}

ͨ�������ݴ��䣨����һ���������ļ�ͨ�� FileChannel���� Դͨ�������� SocketChannel
public void filePipeline() {
	RandomAccessFile fromFile = new RandomAccessFile("1.txt", "rw");
	FileChannel fromChannel = fromFile.getChannel();
	RandomAccessFile toFile = new RandomAccessFile("2.txt", "rw");
	FileChannel toChannel = toFile.getChannel();
	long position = 0;   //  ��д�����ݵ���ʼ��ַ
	long count = fromChannel.size();  // �������ֽ���
	/* �����ݴ�Դͨ�����䵽FileChannel�У�ע��˴�Դ��fromChannel��Ŀ����toChannel�ұ������ļ�ͨ�� */
	toChannel.transferFrom(position, count, fromChannel);
	/* �����ݴӵ�ǰ���ļ�ͨ�����䵽Ŀ��ͨ������� fromChannel �������ļ�ͨ�� */
	fromChannel.transferTo(position, count, toChannel);
}

Selector ���������̹߳�����ͨ�����Ӷ��������������ӡ� 
����Channel���Selectorʱ���봦�ڷ�����ģʽ����˲�����FileChannelһ����
public void runSelector() {
	// ���� selector
	Selector selector = Selector.open();
	/* ���ڶ��channel��һ��selector�����channel���봦�ڷ�����ģʽ���˴��������� */
	channel.configureBlocking(false);
	// ��channel������channelע�ᵽselector�ϣ��ڶ���������ʾ��Ȥ���ϣ���ʾselector��channel��ʲô����Ȥ��
	/* ��Ҫ�� Connect/Accept/Read/Write  4�����͵��¼����ֱ��ʾͨ���ġ����Ӿ������������վ���������������������д������ */
	/* Selectionkey.OP_CONNECT, Selectionkey.OP_ACCEPT, Selectionkey.OP_READ, Selectionkey.OP_WRITE  ������λ�������ϲ� */
	SelectionKey key = channel.register(selector, Selectionkey.OP_READ);
	
	key.attach(myObject); // ����ҵ����Ҫ����ض���key����ѡ
	
	while(true) {
		/* ���ط�0ֵ�������ж�Ӧ������channel�Ѿ����� */
		int readyChannels = selector.select();
		if(readyChannels == 0) continue;
		/* ��ȡ����ͨ����SelectionKey���������ʸ���ͨ�� */
		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		/* ��ȡͨ���ĵ����� */
		Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
		while(keyIterator.hasNext()) {
			selectedKey key = keyIterator.next();
			if(key.isAcceptable()) { 
				SocketChannel channel = (SocketChannel)key.channel();  /* ��ȡ��Ӧ��channel��������Ҫ�Լ�ǿת */
				Selector sele = key.selector();  // ����ȡselector
			}else if(key.isConnectable()) {
				Object attachedObj = selectionKey.attachment();  // ��ȡ�󶨵�
			}else if(key.isReadable()) {
				doSomethingC();
			}else if(key.isWritable()) {
				doSomethingD();
			}
			/* selector�����Լ�����ѡ�������Ƴ�SelectionKeyʵ������Ҫ�Լ������Ƴ����´�ͨ������ʱ�����ٴν��������ѡ����� */
			keyIterator.remove();
		}
	}
	// �����رգ�ʹ��ע�����ϵ�����SelectionKey��Ч�����ͨ�������ر�
	selector.close();
}

�ļ�ͨ�� FileChannel �ĳ��ò���
public void fileChannelUsage() {
	RandomAccessFile aFile = new RandomAccessFile("1.txt", "rw");
	FileChannel inChannel = aFile.getChannel();
	ByteBuffer buf = ByteBuffer.allocate(48);
	/* ��channel��������ݵ�buf��result��ʾ�ɹ���ȡ���ֽ���������-1��ʾ�������ļ�ĩβ  */
	int result = inChannel.read(buf);
	
	String newData = "a test string";
	ByteBuffer buf = ByteBuffer.allocate(96);
	/* ���buf���� */
	buf.clear();
	/* ���ַ���ת�����ֽڷ���buffer */
	buf.put(newData.getBytes());
	/* �л���ǰ��дģʽ����ǰ��bufд�л���buf�� */
	buf.flip();
	/* ��ȷ��һ������channel����д����ٸ��ֽڣ������Ҫwhileѭ��д�� */
	while(buf.hasRemaining()) {
		channel.write(buf);
	}
	/* ��ȡͨ���ĵ�ǰλ�� */
	long pos = channel.position();
	/* ����ͨ���ĵ�ǰλ�� */
	channel.position(pos + 123);
	/* ���ص�ǰͨ���������ļ���С */
	long fileSize = channel.size();
	/* ��ȡһ���ļ���ָ�����Ⱥ���Ĳ��ֽ���ɾ�� */
	channel.truncate(1024);
	/* ��ͨ������δд����̵�����ǿ��д�������� */
	channel.force():
}

�׽���ͨ�� SocketChannel �÷�
public void socketChannelUsage() {
	/* ��SocketChannel  */
	SocketChannel sc = SocketChannel.open();
	sc.connect(new InetSocketAddress("http://jenkov.com", 80));
	/* �ر�SocketChannel */
	sc.close();
	/* ��ȡ���ݣ�����-1��ʾ����������ĩβ�����ӹر���  */
	ByteBuffer buf = ByteBuffer.allocate(48);
	int result = sc.read(buf);
	/* SocketChannelдģʽ���ļ�ͨ��FileChannelһģһ���������о� */
	
	
	/* ������ģʽ���������첽ģʽ�µ��� connect() read() write() */
	sc.configureBlocking(false);
	sc.connect(new InetSocketAddress("http://jenkov.com", 80));
	/* ������ģʽ�£����������ӽ���֮ǰ�ͷ����ˣ���Ҫȷ�������Ƿ��� */
	while(!sc.finishConnect()) {
		doSomething();
	}
}

ServerSocketChannel �÷�
public void serverSocketChannelUsage() {
	ServerSocketChannel ssc = ServerSocketChannel.open();
	ssc.socket().bind(new InetSocketAddress(9999));
	
	while(true) {
		/* �������������ӣ���accept()�������ص�ʱ�򣬷���һ�������½������ӵ�SocketChannel */
		/* �˴���һֱ�������������ӵ��ͨ���������ֻ��ͦһ������ */
		SocketChannel sc = ssc.accept();
		doSomething();
	}
	/* ������ģʽ */
	ssc.configureBlocking(false);
	while(true) {
		SocketChannel sc = ssc.accept();
		if(sc != null) { doSomething(); }
	}
	ssc.close();
}

DatagramChannel �÷�
public void datagramChannelUsage()  {
	DatagramChannel dc = DatagramChannel.open();
	dc.socket().bind(new InetSocketAddress(9999));
	ByteBuffer buf = ByteBuffer.allocate(48);
	buf.clear();
	/* �����յ������ݰ����Ƶ�buf�����buf�ݲ����յ������ݣ���������ݽ������� */
	dc.receive(buf);
	
	String newData = "a test string";
	ByteBuffer buf = ByteBuffer.allocate(96);
	buf.clear();
	buf.put(newData.getBytes());
	buf.flip();
	/* ��һ���ַ����͵�jenkov.com��������UDP�˿�80���޷�ȷ�����ݰ��Ƿ��Ѿ��յ�����ΪUDP����֤���ݵ��� */
	int result = dc.send(buf, new InetSocketAddress("jenkov.com", 80));
	/* ����DCP�������ӵģ����ӵ��ض���ַ��������TCPͨ����������һ�����ӣ�������סDatagramChannel������ֻ�ܴ��ض���ַ�շ����� */
	dc.connect(new InetSocketAddress("jenkov.com", 80));
	/* �󶨵��ض���ַ�󣬾Ϳ�����ͳͨ��һ��������ֻ�ǲ���֤����ȷ�ϵ��� */
	int ret = dc.read(buf);
	int ret = dc.write(buf);
}

Pipe�÷�
public void pipeUsage() {
	Pipe p = Pipe.open();
	/* ��ܵ�д���ݣ���Ҫ����sinkͨ�� */
	Pipe.SinkChannel sc = p.sink();
	/* ��sinkChannelд�����ݣ�buf��������Դ��ǰ��һ�������ظ� */
	while(buf.hasRemaining()) {
		sc.write(buf);
	}
	/* �ӹܵ������ݣ���Ҫ����sourceͨ�� */
	Pipe.SourceChannel sc = p.source();
	int ret = sc.read(buf);
}

Path���÷�
import java.nio.file.Path;
import java.nio.file.Paths;
public void pathUsage() {
	/* ����·�� */
	Path path = Paths.get("c:\\data\\myfile.txt");
	/* ���·���������·���� c:\data\projects ����ʵ��������һ�����ַ���ƴ�ӹ��ܣ�linux�µ�. �� .. Ҳ����ʶ���	*/
	Path p1 = Paths.get("c:\\data", "projects");
	/* ����·������Ҫ��.��..�����ĸ��ֲ���Ҫ���������� */
	Path p2 = p1.normalize();
}

/****                                               RTTI                                        ****/
�����ӿں���
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

���Ƽ���������ֱ��ʹ��Class�ķ�ʽ���Ƽ�ʹ�÷���
static void printInfo(Class cc) {
		/* ��ȡ��������FancyToy���ж��Ƿ��ǽӿ� */
		Print.print("Class name: " + cc.getName() + " is interface? [" + cc.isInterface() + "]");
		/* ��ȡ��������FancyToy */
		Print.print("Simple name: " + cc.getSimpleName());
		/* ��ȡ����·������������typeinfo.toys.FancyToy */
		Print.print("Canonical name:" + cc.getCanonicalName());
		Print.print("==================separator========================");
	}
	
public static void main(String[] args) {
	Class c = null;
	try{
		/* ��ȡ�������Class���󣬲���Ҫ������import�ľ���·�����˴�FancyToy����defaultĿ¼�� */
		/* ��һ������ݵķ�ʽ��ʹ�������泣��  �� FancyToy.class */
		c = Class.forName("FancyToy");
	}catch(ClassNotFoundException e) {
		e.printStackTrace();
	}
	printInfo(c);
	/* ��ȡ����ʵ�ֵ����нӿ� */
	for(Class face: c.getInterfaces())
		printInfo(face);
	/* ��ȡ��ǰ���ֱϵ���� */
	Class up = c.getSuperclass();
	Object obj = null;
	try{
		/* ��ȡ��ǰ�����ָ����ľ���ʵ�������������Ĭ�Ϲ������������׳��쳣 */
		obj = up.newInstance();
	}catch(InstantiationException e){
		e.printStackTrace();
	}catch(IllegalAccessException e){
		e.printStackTrace();
	}
	/*  ��ȡ��ǰ����������Ͷ��� ����ӡ */
	printInfo(obj.getClass());
}
	
�����������ķ���д��
public static void main(String[] args) throws InstantiationException, IllegalAccessException {
	// ���÷��͵����������ʹ洢��������
	Class<FancyToy> ftClass = FancyToy.class;
	//  ftClass = int.class  ���벻������Ϊ�Ѿ��÷����޶�������
	FancyToy fancyToy = ftClass.newInstance();
	// �洢FancyToy�ĳ���������ã�ע��ֻ��˵���ǳ��࣬û˵���ĸ�����ĳ��࣬��˵ڶ���д�����벻��
	Class<? super FancyToy> up = ftClass.getSuperclass();
	//Class<Toy> up2 = ftClass.getSuperclass();
	/* ��Ϊ�����Ĳ���һ�־�������ͣ���˵õ��ı���Ҳֻ����Object���ͱ�ʾ */
	Object obj = up.newInstance();
	
	Toy t = new FancyToy();
	Class<FancyToy> fancyToyType = FancyToy.class;
	/* cast() ���������ת��Ϊ�����õ����ͣ���Toy�����洢��FancyToy����ת������ȫƥ��ĵ��õ������õ����� */
	FancyToy ft = fancyToyType.cast(t);
	/*  Ч��������һ�� */
	ft = (FancyToy)t;
	
	/* Class<?> �ȼ���ԭ�е�Class��û�������������û��Լ�������Դ洢�κε����ͣ���˱��Ƽ�����ʹ�� */
	Class<?> intClass = int.class;
	intClass = double.class;
}
	
	
/*  ���÷��͵��б��Զ������������� */
class CounterInteger {
	private static long counter;
	/* ע�⣬�˴�id�������ó�static���������е�idֵ�����0 */
	private final long id = counter++;
	public String toString() { return Long.toString(id); }
}
public class FilledList<T> {
	/* �˴���ʾ�洢һ������T��Class���͵������ñ���  */
	private Class<T> type;
	/* ʹ����������������� */
	public FilledList(Class<T> type) { this.type = type; }
	
	public List<T> create(int nElements) {
		List<T> result = new ArrayList<T>();
		try{
			for(int i=0; i<nElements; i++) {
				/* T��Class���������ñ����ϣ�����newInstance()���õ�T��Ĭ�Ϲ��캯��������ʵ������ */
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
		Print.print(fl.create(15));  // �õ� [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]
	}
}

/* �ж�x�Ƿ��� Dog���� ���� Dog���͵������� ��ʵ����*/
if(x instanceof Dog) { ((Dog)x).bark(); }


/****                                      ���ϵ�ʹ��                                       ****/
Iterator �� ListIterator �Ĳ���
1).Iteratorֻ�ܵ����ƶ���ListIterator����˫���ƶ���
2).ListIterator����ɾ�����滻�����Ԫ�أ���Iteratorֻ��ɾ��Ԫ�أ�
3).ListIterator���Է��ص�ǰ������next()��previous()���صģ�Ԫ�ص���������Iterator���ܡ�

Iterator �� Enumeration �Ĳ���
1).Iterator�����Ƴ��ӵײ㼯�ϵ�Ԫ��
2).Iterator�ķ������Ǳ�׼����

/* ���ϵĻ���ʹ�� */
List<Integer> list = new ArrayList<Integer>(); 
/* ��List�����Ԫ�� */
list.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10););
/* ע�����������Ҫ�������� .hasNext() .next() .remove()    
   Java 8 ����forEachRemaining������������ʵ�ֶ����µ�����Ԫ��ִ��ָ���Ĳ��� */
for(Iterator<Integer> iter = list.iterator(); iter.hasNext();)
{
	int i = iter.next(); // �Զ�����
	if(i == 3) iter.remove();  // ע�⣬�˴�ʹ�õ������Ƴ���ǰԪ�أ�����ʹ��list.remove(i)�����ƻ�list��ǰ�ṹ�������׳��쳣
}
System.out.println(list);  // �õ� [1, 2, 4, 5, 6, 7, 8, 9, 10]

ListIterator ��һ��Iterator�������ͣ�ֻ������List��ķ��ʣ�
public static void main(String[] args) {
	List<String> numArray = new ArrayList<String>(Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight"));
	/* ��ȡlistIterator�ĵ����� */
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
	// ���� next() ֮��it�ͽ������Ԫ�غ���Ŀ��previousIndex()վ���Ԫ�أ�nextIndex()վ�Ӻ����Ԫ��
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
	// ���� previous() ֮��it�ͽ������Ԫ��ǰ��Ŀ��previousIndex()վ��ǰ���Ԫ�أ�nextIndex()վ���Ԫ��
	while(it.hasPrevious()) {
		System.out.println(it.previous() + ",\t" + it.previousIndex() + "-" + it.nextIndex());
	}
	/* �ŵ�ָ���±��Ԫ��֮ǰ */
	it = numArray.listIterator(3);
	/* [zero, one, two, NULL, NULL, NULL, NULL, NULL, NULL]��
		վ�±�3��Ԫ��ǰ��Ŀӣ�Ȼ��������Ԫ����ռλ���޸�Ԫ�أ�����previous()ͬ�� */
	while(it.hasNext()) {
		it.next();
		it.set("NULL");
	}
	System.out.println(numArray);
}

IdentityHashMap �� HashMap �Ĳ���
IdentityHashMap��Map�ӿڵ�ʵ�֡���ͬ��HashMap�ģ�������òο�ƽ�ȡ�
1)��HashMap���������Ԫ������ȵģ���key1.equals(key2)
2)��IdentityHashMap���������Ԫ������ȵģ���key1 == key2

/****                                      �ַ���                                       ****/
StringBuilder��һ���÷���������JSE5���룬֮ǰ�� StringBuffer��StringBuffer���̰߳�ȫ�ģ���˿�������
import java.util.*;
public class UsingStringBuilder {
	public static Random rand = new Random(47);
	public String toString() {
		StringBuilder ret = new StringBuilder("[");
		for(int i=0; i<25; i++) {
			/* ͨ�� append()������ݣ�ע��˴��������������ϲ����������ڲ���������һ����ʱ��StringBuilderִ��ƴ�Ӷ��� */
			ret.append(rand.nextInt(100));
			ret.append(", ");
		}
		ret.delete(ret.length()-2, ret.length());
		ret.append("]");
		// ret.reverse()   ��StringBuilder��ԭ�ؽ��ַ�����ת
		// ret.substring(10) ��ret�ϵ�10���ַ�֮����ַ�����Ϊ����ֵ���أ�retԭ�����ݲ���
		return ret.toString();
	}
	public static void main(String[] args) {
		UsingStringBuilder usb = new UsingStringBuilder();
		System.out.println(usb);
	}
}
����ʶ�ĵݹ�
public class InfiniteRecursion {
	public String toString() {
		/* ��ȷ�÷������� super.toString()   */
		return " InfiniteRecursion address" + super.toString() + "\n";
		/* �����÷��� this����Ҫƴ���ַ����ǣ��������ָ�����toString()����ʱ�ͻ������ѭ���ĵ����� */
		//return " InfiniteRecursion address" + this + "\n";
	}
}
������ʽ  Java�� \\ת��һ��\�ַ������\Ҫ���������ʽ������Ҫ���� \\d ������ʽ
�ַ���֧��������ʽ����������  matches()  split()  replace()
"-1234".matches("-?\\d+");  // �ַ��� matches() �����Դ������ܣ��˴�ƥ�� ���ܵ� - �ַ���һ�����ϵ�����
s.split("\\W+");  // �����еķǵ��ʷָ�
s.replaceFirst("f\\w", "located");  // ��f��ͷ�ĵ�һ�������滻�� located
s.replaceAll("shrubbery|tree|herring", "banana")  // �����г��ֹ��� shrubbery tree herring ���滻�� banana


Scanner������������ɨ�����룬һ��˵�Ľ���ʽ���룬���ߴ��ı���ÿ�ζ�һ�в����д���Ĳ����������ļ�IO�����
Scanner in = new Scanner(FileChannel.input);
// ���еĶ�ȡ����Scanner�Լ�������ƥ��
in.nextLine();   // ��ȡ��һ��    
in.nextInt();   // ��ȡ��һ������
in.nextDouble();  // ��ȡ��һ��������

Scanner sc = new Scanner("12, 42, 78, 99, 42");
sc.useDelimiter("\\s*,\\s*");   //  ���ö������Ҳ���Ƿָ������˴���ƥ������հ��м���,�����
while(sc.hasNextInt())
	System.out.println(scanner.nextInt());   �õ�  12 42 79 99 42

/****                                      ����                                       ****/
��ά����
int[][] a = {{1, 2, 3}, {4, 5, 6}};
System.out.println(Arrays.deepToString(a));   // ����ά����ת��ΪString

int[][][] a = new int[2][3][4];
int[][][] a = new int[rand.nextInt(7)][][];
for(int i=0; i<a.length; i++) {
	a[i] = new int[rand.nextInt(5)][];
	for(int j=0; j<a[i].length; j++) 
		a[i][j] = new int[rand.nextInt(5)];
}

int size = 6;
boolean[] a1 = new boolean[size];
Arrays.fill(a1, true);    // ʹ�� true�����������
String[] a2 = new String[size];
Arrays.fill(a2, 3, 5, "hello");  // ���3��4����λ��Ϊ hello

