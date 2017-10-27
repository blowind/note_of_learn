 ///////////////////////////////                     ����֪ʶ                ///////////////////////////////
 $ Ҳ��һ����Ч�ı�����/������ ���ţ���һ����scala�ڲ�ʹ�ã���������������

scala�г�������һ��ʹ��ȫ��д�ַ�����PI, XOFFSET

`` ���������������ַ���ֻ����ʶ������������������ڸ�ʶ�����scala�ؼ�����ͬ�����  ���磺 Thread.`yield`()

scala�и���������Զ���� Unit

������ڡ�

object FindLongLines {
	def main(args: Array[String]) = {
		val width = args(0).toInt
		for (arg <- args.drop(1))    //  ʡ�Ե���һ��Ԫ�أ��ӵڶ���Ԫ�ؿ�ʼ����
			LongLines.processFile(arg, width)
}

�����ƽṹ��
// ��ʵ�����ϴ��ĵڶ��������Ǻ���ָ�룬��������� (String, String) => Boolean ������κͷ���ֵ����ȷ���˺�������
// �˴���װ������ʵ������һЩǰ����Ϣ������forѭ��������һ�����ļ����ͣ���˱�Ȼ��getName����
def filesMatching(query: String, matcher: (String, String) => Boolean) = {
	for (file <- filesHere; if matcher(file.getName, query)) yield file
}
// fileName.endsWith(query)����д�����в��� fileName�Ѿ���װ�� filesMatching�У�
// ͬʱ����ÿ������������һ�Σ�������ռλ�� _ �����ÿ��ռλ�����ζ�Ӧ�����ҵĲ���
// JAVA��һ�㽫���������ȡ��interface�ӿ�(����matcher��������)����ÿ�ַ���ʵ��һ�����������࣬
// FileMatcherʵ����ʱ���ݾ������ʹ���Ӧ����ʵ����������������Ϊinterface�ӿ����ͣ�������ģʽDuck���
def filesEnding(query: String) = filesMatching(query, _.endsWith(_)) 
def filesContaining(query: String) = filesMatching(query, _.contains(_))
def filesRegex(query: String) = filesMatching(query, _.matches(_))
//  �ռ��򻯰汾������query������filesMatching�в���ʹ�ã�ֱ�����ϲ㴫��ʱ�滻ռλ��
object FileMatcher {
	private def filesHere = (new java.io.File(".")).listFiles
	
	private def filesMatching(matcher: String => Boolean) {
		for (file <- filesHere; if matcher(file.getName)) yield file
		
	def filesEnding(query: String) = filesMatching(_.endsWith(query))
	def filesContaining(query: String) = filesMatching(_.contains(query))
	def filesRegex(query: String) = filesMatching(_.matches(query))
}

���ҵĺ����ֲ�ʽд����
// һ��Java����C������������forѭ������Ԫ�ز��Һ󷵻أ���OO��д�������ڷ�װ�����
// exists�����ѷ�װ�ĺ���ָ�룬ָ���������Ԫ�صĲ�������������������ϲ㴫�ݣ��˴��� _ < 0 ������_��ʾԪ������
def containsNeg(nums: List[Int]) = nums.exists(_ < 0)   // ����һ������List���Ƿ��븴��
def containsOdd(nums: List[Int]) = nums.exists(_ % 2 == 1)  // ���Ҽ������Ƿ�������

���ֲ����� currying��
��һ��ת�����ɣ�ͨ��Ԥ�ȴ���һ����� ���������Ѷ�Ԫ����ת��Ϊ����һЩԪ�ĺ���������һԪ�������ֲ����ã�Currying������һ�ֽ⹹���ɣ����ڰѶ�Ԫ�����ֽ�Ϊ�������ʽ���õĲ��ʽ��һԪ���������ֽ⹹���������������оֲ�Ӧ��һ����������
def curriedSum(x: Int)(y: Int) = x + y    // ������͵�curryingд�� �ȼ���  def curriedSum(x: Int, y: Int) = x + y
չ�������ǣ�
def first(x: Int) = (y: Int) => x + y
def second = first(1)    �ȼ���   val onePlus = curriedSum(1)_     // �˴�ռλ����ʾ����curriedSum(1)�Ĳ���
�ϲ������ʽΪ��
second(2)              �ȼ���   onePlus(2)

// �β�1�Ǹ�����ָ�룬�β�2��Double���ͣ����������һ��������������
def twice(op: Double => Double, x: Double) = op(op(x))  
twice(_ + 1, 5)  //  �õ�7����5��������1


�������� by-name parameter��  �����Ϊ�յ�ʱ�������ô�ã�Ϊ���ú����Ե��� if ֮��Ŀ��ƹؼ���
ԭ�и�ʽ���£�
var assertionsEnabled = true
def myAssert(predicate: () => Boolean) =   //  ��һ������ָ�룬���Ϊ�գ�����ֵ�ǲ�������
	if (assertionsEnabled && !predicate())
	throw new AssertionError
	
myAssert( () => 5 > 3)  //  () ����ʡ����Ϊ��ʾ�������ã����Ϊ�գ�ʡ���˽ṹ������

// ע�⵽�����ֶ�����ʽ�� predicate: Boolean ֱ�Ӵ��������͵��������ڣ��˴��Ǵ��ݵ�һ���������ڷ�װ�ڲ�ʵ��ʱ��ִ��
// �������Ϊһ���ӳ�ִ�еı��ʽ��������� predicate: Boolean ���ͣ���������ǰ��λ��ȱ����㣬Ȼ�󽫽����Ϊ��������
def byNameAssert(predicate: => Boolean) =       //  ���������ͬ�ϣ�����ʡ��()����ʾ������
	if (assertionsEnabled && !predicate)
	throw new AssertionError
	
byNameAssert(5 > 3)

 ///////////////////////////////                     ���ƽṹ                ///////////////////////////////

/////////// if��䣬scala��������䶼����һ��ֵ(����ʽ���Ե��ص�)����˿���ֱ�Ӱѽ����Ϊִ����䷵��
//  ע��˴��ı������ͣ����ļ��������洢ֵ�����������������ʹ��val���ͣ�����ȫ��Ч�׶���
//  ���⣬�����������Ҳ����ʽ�ȼ�(��������˼·)
val filename = if (!args.isEmpty) args(0) else "default.txt"

/////////// while��䣬while��do-while��scala����ѭ�������Ǳ��ʽ������ֵ��Զ�� Unit �ȼ��� ()
var line = ""
do {
	line = readLine()
	println("Read: " + line)
} while (line != "")

def gcdLoop(x: Long, y: Long): Long = {
	var a = x
	var b = y
	while (a != 0) {
		val temp = a
		a = b%a
		b = a
	}
	b       //  �˴�b����������ķ���ֵ
}
// ͬ�����ܵĵݹ�ʽд��(����ʽ˼ά)
def gcd(x: Long, y: Long): Long = if y == 0 x else gcd(y, x%y)

/////////// for���
// ��д����ͬ���������Ե��ص����ڣ�����Ҫ����һ���±���� i ��ͨ���±���������Ӽ��������ȡ���±�Ԫ�������ʼ���Ԫ�أ�
// �ŵ㣺��Ч���������Խ�硢0/1��ʼ������
val filesHere = (new java.io.File(".")).listFiles
for (file <- filesHere)   //  ��������䣬�������м�����(��ֻ����)�������ΰѼ���Ԫ�ط������
	println(file)
	
for ( i <- 1 to 5 )   //  ͬ�ϣ�������ӡ1��5
	println(i)
	
for ( i <- 1 until 4 )  //  ������ӡ1��3 �����������ޣ�
	println(i)

// ��������������������� if������ѭ������������ if �ĺô��ǣ����߻ᵼ��forѭ���Ĵ�ӡ��䷵��ֵ Unit ��Ϊfor��䷵��ֵ����
val filesHere = (new java.io.File(".")).listFiles
for (file <- filesHere if file.getName.endsWith(".scala"))
	println(file)
// ɸѡ��ӡ .scala��β���ļ���	
for ( file <- filesHere
	  if file.isFile
	  if file.getName.endsWith(".scala")   //  �������������
)println(file)
// ɸѡ��ӡ��ǰĿ¼������ .scala �ļ������ gcd �����ַ����ļ����ļ���
def fileLines(file: java.io.File) = scala.io.Source.fromFile(file).getLines().toList
def grep(pattern: String) =
	for (
		file <- filesHere
		if file.getName.endsWith(".scala");
		line <- fileLines(file)
		trimmed = line.trim             // ע�⵽�˴�trimmed��������ʱ���ɵģ�ѭ������ж������ʹ��
		if trimmed.matchs(pattern)        //  ���ϵ���
	) println(file + ": " + trimmed)
grep(".*gcd.*")

// yield��䣬ͨ����������ɼ��ϣ���׼��ʽ for clauses yield body��ʵ���ǰ�for��������Ԫ���� yield ���
def scalaFiles = 
	for {
		file <- filesHere
		if file.getName.endsWith(".scala")   // ע��˴�������ɸѡ������ {} �yield �ؼ���������������֮ǰ
	} yield file    // �������ļ���ΪԪ�ص����� Array[File]
//  ��ȡһ������ for�ַ���scala�ļ��ĸ��г��ȼ��ϣ����� Array[Int]
val forLineLengths = for {
	file <- filesHere
	if file.getName.endsWith(".scala")
	line <- fileLines(file)
	trimmed = line.trim
	if trimmed.matchs(".*for.*")
	} yield trimmed.length
	
// �쳣���� try-catch-finally �ṹҲ�з���ֵ��Ҫ������finally�����ʹ�� return
throw new IllegalArgumentException

val half = 
	if (n%2 == 0) n/2
	else 
		throw new RuntimeException("n must be even") // �׳�һ�������Ҳ���з���ֵ�ģ������� Nothing�������������ִ��
	
import java.io.FileReader
import java.io.FileNotFoundException
import java.io.IOException
try {
	val f = new FileReader("input.txt")
} catch {
	case ex: FileNotFoundException =>
	case ex: IOException =>
} finally {        // �����쳣�����Ȼִ����䣬ȷ����Դ�õ��ͷ�
	f.close()
}

def f(): Int = try return 1 finally return 2           //  �õ����2����Ϊreturn��䷵��ֵ������try���
def g(): Int = try 1 finally 2                 //  �õ����1����Ϊtry����ִ�н����Ϊ�������������

// match-case ��䣬��������������� switch-case ��䣬�������� case �жϲ�������
// ͬʱ������û��break��䣬Ҳû�����������ж��case����һ��ִ����������һ��caseִ�������˳�
val firstArg = if (args.length > 0) args(0) else ""
firstArg match {      
	case "salt" => println("pepper")
	case "chips" => println("salsa")
	case "eggs" => println("bacon")
	case _ => "huh?"           //  Ĭ�϶������
}
// ��һ��д����ע�⵽ match ���Ҳ���з���ֵ�ģ�ֱ�ӽ�����ֵ��ӡ����ܶ��ظ����룬ͬʱ�������������ѡ�������
val friend = firstArg match {         
	case "salt" => "pepper"
	case "chips" => "salsa"
	case "eggs" => "bacon"
	case _ => "huh?"
}
println(friend)
	
// ��ʹ��break��continue�ĺ���ʽɸѡд����scala��Ҳû�н���������Ϊ�����ؼ���
// �������Ե�д����ƥ���-��continueִ��.scala�жϣ�ƥ��.scala֮��break�˳�ѭ��
def searchFrom(i: Int): Int = 
	if (i >= args.length) -1 
	else if (args(i).startsWith("-")) searchFrom(i+1)
	else if (args(i).endsWith(".scala") i
	else searchFrom(i+1)
val i = searchFrom(0)

// ǿ��ʹ��break�ķ�����ע��˴�ǿ��������breakable��ǵ����� {} ��β�������������Ե�goto��䣬
// �ײ�ʹ���쳣����ʵ�֣����Կ��㺯����ת��ǿ����������break������goto
import scala.util.control.Breaks._
import java.io._

val in = new BufferedReader(new InputStreamReader(System.in))
breakable {
	while (true) {
		println("? ")
		if (in.readLine() == "") break
	}
}
	
 ///////////////////////////////                     �ඨ�����                /////////////////////////////// 
 
 ////////////  Scala�н�ֹ����ͬ�����෽���������������������ͬ������ʱ�������ܸ��Ǹ�����ͬ���޲η���
 //////// Java������4�����ռ䣺���������������ͣ���    
 ///////  Scala����ֻ��2�����ռ䣺���� values (fields, methods, packages, and singleton objects)
 ///////                          ���� types (class and trait names)
 
//  ���� Int���͵� Rational���͵�Ĭ��ת�������Զ�������  2 * rational ���ּ���ʱ��� 2Ĭ��ת���� Rational��Ч�� 
implicit def intToRational(x: Int) = new Rational(x) 
 
//  scala������ʹ�þ�̬�ࣨimmutable object�������˶�ο�����ռ�ø����ڴ����ȱ������4��ô���
//   1��״̬��ά����2����Ϊ�������������赣�����ݱ����3�����̰߳�ȫ��4��������Ϊhash��ʹ��
//  �ඨ�壬����Ҫ���βκ����ͣ��βο������ඨ�����κεط�ʹ�ã����������� Unit�������û�о������ݣ�����Ҫǿ�Ƽ��� {}
class Rational(n: Int, d: Int) extends Ordered[Rational] {   // �̳� trait ʱ���ô�����������Ҫ������
	// �ඨ������κο�ִ�д��붼�ᱻ�������ŵ����캯���ڣ�������ʾ���Ⱥ�˳��ִ�У����´�ӡ���ڶ�������ʱִ��
	println("Create " + n + "/" + d)
	// ǰ�������������ڹ��캯������ǰ�����Σ���������ǰ������Ҫ�󣬶��������ɣ��׳�IllegalArgumentException�쳣
	require(d != 0)
	private val g = gcd(n.abs, d.abs)
// ���壬�����βν��ڱ���������Ч(�������Ϊprivate)�����ⲿʹ��ʱ��Ҫ�����µ�����������������that.denomʹ�ó���
// ����д���� val numer: Int = n / g
	val numer = n / g
	val denom = d / g
	
	// �������캯������̬�Ļ������˴������ĸΪ1����������๹�캯��������������������Ĺ��캯����
	// �����϶�Ҫ�������캯��������scala��Ϊ�˷��������캯����д��������У�����֮ǰ�Ĳ��ֶ��������캯��������
	def this(n: Int) = this(n, 1)
	
	//  this ָ����Ȼ���ã���ͬ��java���this��ָ��������
	def lessThan(that: Rational): boolean = numer * that.denom < that.numer * denom
	def max(that: Rational): Rational = if (lessThan(that)) that else this
	
	//   ��������ȼ�Ĭ�ϼ̳�scala��ʵ�ֵĸ�����������ȼ����˴�ʵ����������ʽ�ļӷ�
	def + (that: Rational): Rational = new Rational( numer * that.denom + that.numer * denom, denom * that.denom )
	def + (i: Int): Rational = new Rational( numer + i * denom, denom )
	
	def - (that: Rational): Rational = new Rational( numer * that.denom - that.numer * denom, denom * that.denom )
	def - (i: Int): Rational = new Rational( numer - i * denom, denom )
	
	def * (that: Rational): Rational = new Rational( numer * that.numer, denom * that.denom )
	def * (i: Int): Rational = new Rational( numer * i, denom )
	
	def / (that: Rational): Rational = new Rational( numer * that.denom, denom * that.numer )
	def / (i: Int): Rational = new Rational( numer, denom * i )
	
	// ���� toString �������������������ɶ���ʱ��δ���ػ����Ĭ�ϵ� java.lang.Object �����toString ��ӡ���
	override def toString = numer + "/" + denom
	
	// ˽�з���
	private def gcd(a: Int, b:Int): Int = if (b == 0) a else gcd(b, a%b)
	
	// ��������Ϊ�̳е� trait ��Ҫʹ�õĻ�����������������ʵ��ʱ��
	def compare(that: Rational) = (this.numer * that.denom) - (that.numer * this.denom)
}

//  ע�⵽�ڴ˴���û��ʵ������жϲ�������Ϊ�ȼ��жϸ�������أ����˴�ʵ��ʱ�����������ǲ�����
trait Ordered[T] {      //  ��������ıȽ����ԣ����˸� Rational �ã�Ҳ���Ը����������ƹ��ܵ����ã� �о������Ʒ���
	def compare��that: T): Int  // �˴����ⷽ����û�к����壬���ڼ̳б� trait ����ȥʵ��
	
	def <(that: T): Boolean = (this compare that) < 0
	def >(that: T): Boolean = (this compare that) > 0
	def <=(that: T): Boolean = (this compare that) <= 0
	def >=(that: T): Boolean = (this compare that) >= 0
}

�������ࡿ  abstract class Element ....
abstract class Element {
	def contents: Array[String]   //  ������ķ��������෽����û�о���ʵ�֣�scala��û��ʵ�ֺ������Ĭ�Ͼ��� abstract
	def height: Int = contents.length   // �޲� �෽������ʵ�����lengthҲ�ǵ��õ��෽������ȡԪ�ظ���
	// val height = contents.length  //���ϲ�Ч��������һ�к�������һ��������һ���Ǻ������÷��ؽ����һ���Ƿ��ر���ֵ
	def width: Int = if (height == 0) 0 else contents(0).length   // ȡһ��Ԫ�صĳ�����Ϊ���(�˴����ַ�����)
}
��scala�У��Ƽ��޸�����(side effects)���޲η�������͵���ʱ���� () ���и����õķ���Ҫ��()�����˿��������ڵ��÷��������Ƕ�ȡ��������ǰ��� contents.length �������ĵĻ��޷����֣�
һ��IO������дvar������ֱ�ӻ���ʹ�ÿɱ���� ����Ϊ�������и����õ�

���̳С� ���������˽�б�������������̳�
class ArrayElement(conts: Array[String]) extends Element {
	//scala��������ͷ�����ͬһ�����գ��������д��Ҳ��override����ͬ����������Ȼ�������������Ǹ��������˴��Ǳ���
	val contents: Array[String] = conts    //  ���ǽ��մ洢����ʱ��scala�������е�д��
	//  def contents: Array[String] = conts   �ȼ�����һ�ж���
}
���Ż�д��������������ı����ظ�����
class ArrayElement(val contents: Array[String]) extends Element // ע�⵽������contentsҪ�븸����ͬ���˱������ⲿ����
{
	final override def demo() = {   // ͨ�� final ����ֹ ArrayElement ������ override demo����
		println("ArrayElement's implementation invoked")
	}
}
//  �����̳�ʱ�����ø���Ĺ��캯�������ڸ��๹������Ҫ���Σ��������н� Array(s) ���룬���������˸��๹�캯��
//  ��������Ϊ final �󣬲��ɱ�������̳�
final class LineElement(s: String) extends ArrayElement(Array(s)) {
	override def width = s.length
	override def height = 1
}
//  ���и����Ѿ�ʵ�ֹ��ķ���������ʱ����� override �ؼ��֣�δʵ�ֵĳ��󷽷���ǿ��Ҫ�� ���෽���޺���ʵ��ʱ���ܼӣ�
//  �����Ƶĺô��ǣ�1��N�ĸ������ϵʱ���ڸ�������һ����������ĳ������ʵ��ͬ�����������������澯

//  ��������������
class Cat { val dangerous = false }
class Tiger { override val dangerous: Boolean, private var age: Int) extends Cat
�ȼ���
class Tiger(param1: Boolean, param2: Int) extends Cat {
	override val dangerous = param1
	private var age = param2
}

����̬�� �ø����������������������������ͽ���ʵ������ʹ��ʱ�۷�����ʵ�ְ����������ﵽͬ���෽����ͬ������Ŀ��
val e1: Element = new ArrayElement(Array("hello", "world"))


�����Ρ�
������ĸ��ࣺ  Any
���ඨ��Ļ����������£�
final def ==(that: Any): Boolean  // �����Ͼ��� equals �������Ȼ�� final �����ǿ���ͨ���̳����� equals ���ı�ʵ��
final def !=(that: Any): Boolean  // �������� equals ȡ��
def equals(that: Any): Boolean
def ##: Int    //   ��ϣ�����
def hashCode: Int
def toString: String // ��ʽ�����㣬������������ַ���

�ڶ����2����֮һ���� AnyVal  �̳�Any�����࣬��Byte, Short, Char, Int, Long, Float, Double, Boolean, Unit�ĸ���

����ǰ8�����͵����� new Int ���������������У�������������˵ʵ��������ֵ�ࣩ����ֱ�ӱ�ʾ��  �� 42��'x', 1.998 �ȣ���Ϊ���е�ֵ��������Ϊ abstract �� final

Unit ���µ��� Java�� void�� Ψһ����������ʵ����ֵ���� ()

�ڶ����2����֮������ AnyRef �̳�Any�������������۵Ļ��࣬��ӦJava�е� java.lang.Object
// ����ȫ�� OO ���Ե��ص�
// This is Java
boolean isEqual(int x, int y) {
return x == y;
}
System.out.println(isEqual(421, 421));  //  �õ� true ����ֵ�Ƚ�
boolean isEqual(Integer x, Integer y) {
	return x == y;
}
System.out.println(isEqual(421, 421));    // �õ� false �� �������ñȽ�
//Java�и���䷵��false����Ϊ==�жϵ������������Ƿ�����ͬһ�����󣬶�����421�ֱ���������ת���ɶ���Ĳ�������˷ֱ�ָ�����ת���Ķ���

// ��ȫ��OO���Ե��ص㣬�������ʹ�������壬== ������͸����
def isEqual(x: Int, y: Int) = x == y
isEqual(421, 421)    //   �õ� true ��ֵ�Ƚ�
def isEqual(x: Any, y: Any) = x == y
isEqual(421, 421)   //   �õ� true ����Ƚϣ���������ֵ���������ֵ��ײ����������override
//  Java�����ַ����Ƚ����岻����֣�����
val x = "abcd".substring(2)
val y = "abcd".substring(2)
x == y    // �õ� true

���ñȽ�  eq �� ne
val x = new String("abc")
val y = new String("abc")
x == y  //  �õ� true
x eq y  //  �õ� false�� ���ñȽϣ��������õ�ͬһ������
x ne y  //  �õ� true

�ײ�2������֮һ��  scala.Null ���������� AnyRef��������࣬����ֵ�಻���ݣ��������д��������
val i: Int = null   //  ������澯��null����ֵ�಻����

�ײ�2������֮���� scala.Nothing û���κ�ʵ��ֵ�������쳣����ʱ�����������޷��������أ����Ҳû�б�����ʵ�ʽ��������ֵ
def error(message: String): Nothing =
	throw new RuntimeException(message)

������ģʽ�� factory object ��������   ���  162ҳ 10.13 ���û����  ��������������������������������������������

 ///////////////////////////////                     trait                /////////////////////////////// 
 trait ���뷨������Java�ӿڶ���ķ������ඨ��ķ����Ķ��֮���������ƽ�⣬����trait�еķ����к����壬����ڼ̳��������в��ü���ʵ�֣�JAVA interface�ж���Ķ��ǳ���������������չ��ͬʱ����������ĸ�������������ʱ��������Ŀ���
 ���������һ��trait���������̳У���ô��֤trait���޸�ĳ��������ʵ��Ӱ����С��������������
 
˼��һ����Java �нӿڵ�����: 
1��Java�ӿ��в��ܶ�����巽����ʵ�֣�scala���ԣ�
2��trait�п�������������ά����״̬����������һ������ȫһ�£�����һ����������
   1��trait ����ʱ�����в���
   2��trait �����б��̳еķ�����������ʹ��ʱ���Ƕ�̬�󶨵�


 trait ���Ե�������ʹ�ã����л��� trait ���඼��������ʵ���� trait ���ͱ���
 
val phil: Philosophical = frog
phil.philosophize()
 
trait Philosophical { //  Ĭ�ϵĸ����� AnyRef
	def philosophize() = {
		println("I consume memory, therefore I am!")
	}
}
class Frog extends Philosophical {  // mix in Philosophical �� �̳е��� trait �ĳ���
	override def toString = "green"
	override def philosophize() = {   //  ���� trait �еķ���
		println("It ain't easy being " + toString + "!")
	}
}

class Animal
trait HasLegs
class Frog extends Animal with Philosophical with HasLegs {   // �̳�һ���࣬���һ������� trait
override def toString = "green"
}

abstract class IntQueue {
	def get(): Int
	def put(x: Int)
}

import scala.collection.mutable.ArrayBuffer
class BasicIntQueue extends IntQueue {
	private val buf = new ArrayBuffer[Int]
	def get() = buf.remove(0)
	def put(x: Int) = { buf += x }
}

trait Doubling extends IntQueue {  // �������г��࣬������traitֻ�ܱ�IntQueue��������룬��BasicIntQueue
	// Ҫʹ�� super�� ǰ���abstract override�����٣�����trait�п�����ô��
	abstract override def put(x: Int) = { super.put(2 * x) } // �������ر������������ʵ��
}
  
val queue = new BasicIntQueue with Doubling   // �˴��ȶ���һ������̳�BasicIntQueue��Doubling�����
queue.put(10); queue.get()   // �õ� 20

trait Incrementing extends IntQueue {
	abstract override def put(x: Int) = { super.put(x + 1) }
}
trait Filtering extends IntQueue {
	abstract override def put(x: Int) = { if (x >= 0) super.put(x) }
}
val queue = (new BasicIntQueue with Incrementing with Filtering) //���ڲ�ͬtraitͬ���ĺ������޸ģ���Ч˳����ѭ������
queue.put(-1); queue.put(0); queue.put(1)
queue.get(); queue.get()     // �ֱ�õ�1 �� 2


 ///////////////////////////////                     �������                /////////////////////////////// 

 
 
 import scala.io.Source
 object LongLines {
	def processFile(filename: String, width: Int) = {
		// �˴��ں����ڲ�������һ���������������Ե�д���ǰ���������ŵ��ϲ�ͬһ��Ȼ���private��scala��Ϊ������¶�ӿ�̫�಻����
		def processLine(line: String) = {
			if (line.length > width)
				println(filename + ": " + line.trim)
		}
		
		val source = Source.fromFile(filename)
		for (line <- source.getLines())
			processLine(line)
	}
}
 
 function literal�� ���������� ��װ��δ�����ĺ�����������Ϊ�������ݡ� Դ���е�˵��
 function value�� ��function literal������ಢ������ʱʵ���������function value�� ����ʱ��function literal�������ݵĴ�����
 ʾ����������һ����lambda���ʽ��ʲô���𣿣�����������������������������
 (x: Int) => x + 1
 var increase = (x: Int) => x + 1
 increase(10)
 
 �������Դ���һ�ֺ����������÷� ע�⵽��Щ���ڶ�Ҫ����ඨ���һЩ����
 val someNumers = List(-11, -10, -5, 0, 5, 10, 11)
 someNumers.foreach( (x: Int) => println(x) )   //  ������������Ԫ�ش�ӡ����һ��for����д������̫��
 someNumers.foreach( println _ )           // ͬ�ϣ�����д������ _ �����ϣ�partially applied function ���ֲ��꺯��
 someNumers.foreach(println)      //  ͬ�ϣ������д����ʡ�����в�������������¿��ã��������Ϊ�����������ʹ��(foreach)
 someNumers.filter( (x: Int) => x > 0 )   //  ���˵�С�ڵ���0��Ԫ��
 someNumers.filter( x => x > 0 )       //  ��������д��������ͬ��
 someNumers.filter( _ > 0 )           // �������д��������ͬ�ϣ�ע�����д��������������ִ�����н�����һ��ʱ����
 
 
 val f = (_: Int) + (_: Int)  // ����scala�޷��ж����ͣ�����д���轫���ʹ��ϣ��˴�������������
 f(5, 10)
 
def sum(a: Int, b: Int, c: Int) = a + b + c     // ����һ������
 val a = sum _   //  ��������ֵ��һ��������ͨ�� _ ʡ���˲������������Զ�ʶ�� partially applied function��һ��
                //  �˴�sum�����ϱ���װ�����࣬���ඨ��һ��apply����ʵ��sum�ĺ������ܣ�Ȼ��ʵ����һ�������a
 a(1, 2 ,3)    //   �ȼ��� a.apply(1, 2, 3) �� �õ�6
 
 val b = sum(1, _: Int, 3)   // �ṩ���������� partially applied function��Ĭ��ȱʡһ��ʹ��ʱ����
 b(5)              //  �õ� 9

 ���հ��ķ�Χ��  ���н�����  ������������ʹ�õ��ⲿ������ֵ��ȡ�����������������ʱ�ñ�����ֵ����ʾ��3
 ʾ��1��
 var more = 1
 val addMore = (x: Int) => x + more
 addMore(10)   //  �õ�����11����Ϊmore��ֵ��1
 more = 9999
 addMore(10)   //  �õ�10009����Ϊmore��ֵ���Ϊ 9999
 // ע�⵽����������������õ��ⲿ�������ⲿ�����ı����ʱʱͬ���� �����������ı�����
 
 ʾ��2��
  var sum = 0            //  closure ���⣬ͬ�ϣ��ⲿ��sum��Ӱ����
someNumers.foreach(sum += _)   //  �����ִ����Ϻ�sum��ֵҲ������������ -11��ע��sum�ķ�Χ

ʾ��3��   ��ʾ��1�������ǣ�ʾ��1��more���ⲿ�������˴�more�Ǵ�����β�
def makeIncreaser(more: Int) = (x: Int) => x + more
val inc1 = makeIncreaser(1)     //  ʵ����ʱ��ʹ�õ�more�Ǵ洢���ڴ���ϵģ������ջ�޹�
val inc9999 = makeIncreaser(9999)
inc1(10)      //  �õ�11����Ϊinc1����ʱ��more��1
inc9999(10)     //  �õ�10009����Ϊinc9999����ʱmore��9999
 
 �������ƥ�䡿
 def echo(args: String*) = for ( arg <- args ) println(arg)    //  * ��ʾ���ԽӶ��ͬ��������˴���ʾ���String������Ϊ����
 echo("one")
 echo("hello", "world")
 
 val arr = Array("what's", "up", "doc?")
 echo(arr: _*)   //  �������д�����߱�����ȡ��arr��ÿ��Ԫ�طֱ���Ϊ��������echo������ echo(arr) ����Ϊ����һ������������������
 
 
 ��ʵ��������ֵ��  ���Բ����պ�������˳�򴫲Σ�һ����Ĭ�ϲ����ķ���һ���ã���Ĭ�ϲ�����д��ʣ�µ���������������
 def speed(distance: Float, time: Float): Float = distance/time
 speed(time = 10, distance == 100)  //  �ȼ���  speed(100, 10)
 
 ��Ĭ�ϲ�����
 //  outĬ�ϲ����ǿ���̨��divisorĬ�ϸ�ֵ 1
 def printTime2(out: java.io.PrintStream = Console.out, divisor: Int = 1) =
			out.println("time = " + System.currentTimeMillis()/divisor)
printTime2(out = Console.err)
printTime2(divisor = 1000)
 
 ��Լ򵥵ĵݹ麯����scala�������Ż������к�������ͬһ���û���ö������ջ�����£�
 def bomm(x: Int): Int = 
	if (x == 0) throw new Exception("boom!")
	else boom(x - 1) + 1
��ν�򵥵ݹ���ú��������������н����ö���ĺ��������Ҹõ�����Ϊ�ݹ��е����һ�����ִ�е�������������������������Ż�

ʾ��1����������δ�����������Ż�
def isEven(x: Int): Boolean = if (x == 0) true else isOdd(x - 1)
def isOdd(x: Int): Boolean = if (x == 0) false else isEven(x - 1)
ʾ��2�������ñ������°�װ����scalaʶ���ˣ����Ż�
val funValue = nestedFun _
def nestedFun(x: Int): Unit = { if (x != 0) { println(x); funValue(x - 1) } }

 
  ///////////////////////////////                     ���ú���˵��                ///////////////////////////////
  
 ��zip��
 ������������ԣ���Ԫ���ٵ�һ�������Ϻ����
 Array(1, 2, 3) zip Array("a", "b")   //  �õ�  Array((1, "a"), (2, "b"))
 
 ��mkString��
 ���е�sequence�����鶼ʵ���˱�������ͨ������ÿ��Ԫ�ص�toString������ÿ��Ԫ�ر���ַ�����ʹ��separator���Ӹ����ַ���
 array mkString separator
 
 ��max��
 42 max 43
 
 ��min��
 23 min 43
 
 ��until��
 1 until 5  // �õ� Range(1, 2, 3, 4)
 
 ��to�� 
 1 to 5     //  �õ� Range(1, 2 ,3 ,4 ,5)
 
 ��abs��
 (-3).abs