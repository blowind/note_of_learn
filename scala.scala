 ///////////////////////////////                     基础知识                ///////////////////////////////
 $ 也是一个有效的变量名/函数名 符号，但一般在scala内部使用，不建议用来命名

scala中常量别名一般使用全大写字符，如PI, XOFFSET

`` 反引号扩起来的字符串只用作识别符（变量名），用于该识别符与scala关键字相同的情况  例如： Thread.`yield`()

scala中复制运算永远返回 Unit

【总入口】

object FindLongLines {
	def main(args: Array[String]) = {
		val width = args(0).toInt
		for (arg <- args.drop(1))    //  省略掉第一个元素，从第二个元素开始遍历
			LongLines.processFile(arg, width)
}

【控制结构】
// 其实本质上传的第二个参数是函数指针，因此类型是 (String, String) => Boolean ，用入参和返回值类型确定了函数特征
// 此处封装函数其实包含了一些前置信息，比如for循环遍历的一定是文件类型，因此必然有getName方法
def filesMatching(query: String, matcher: (String, String) => Boolean) = {
	for (file <- filesHere; if matcher(file.getName, query)) yield file
}
// fileName.endsWith(query)的缩写，其中参数 fileName已经封装在 filesMatching中，
// 同时由于每个参数仅出现一次，可以用占位符 _ 替代，每个占位符依次对应从左到右的参数
// JAVA中一般将这个动作抽取成interface接口(包含matcher方法定义)，对每种方法实现一个单独的子类，
// FileMatcher实例化时根据具体类型传对应子类实例参数，参数类型为interface接口类型，详见设计模式Duck相关
def filesEnding(query: String) = filesMatching(query, _.endsWith(_)) 
def filesContaining(query: String) = filesMatching(query, _.contains(_))
def filesRegex(query: String) = filesMatching(query, _.matches(_))
//  终极简化版本，由于query参数在filesMatching中并不使用，直接在上层传参时替换占位符
object FileMatcher {
	private def filesHere = (new java.io.File(".")).listFiles
	
	private def filesMatching(matcher: String => Boolean) {
		for (file <- filesHere; if matcher(file.getName)) yield file
		
	def filesEnding(query: String) = filesMatching(_.endsWith(query))
	def filesContaining(query: String) = filesMatching(_.contains(query))
	def filesRegex(query: String) = filesMatching(_.matches(query))
}

查找的函数分层式写法：
// 一般Java或者C语言倾向于用for循环遍历元素查找后返回，而OO型写法倾向于封装成类库
// exists类似已封装的函数指针，指向遍历集合元素的操作，具体操作内容由上层传递，此处是 _ < 0 ，其中_表示元素内容
def containsNeg(nums: List[Int]) = nums.exists(_ < 0)   // 查找一个整数List中是否与复数
def containsOdd(nums: List[Int]) = nums.exists(_ % 2 == 1)  // 查找集合中是否有奇数

【局部套用 currying】
是一种转换技巧，通过预先传入一个或多 个参数来把多元函数转变为更少一些元的函数甚或是一元函数。局部套用（Currying）：是一种解构技巧，用于把多元函数分解为多个可链式调用的层叠式的一元函数，这种解构可以允许你在其中局部应用一个或多个参数
def curriedSum(x: Int)(y: Int) = x + y    // 两数求和的currying写法 等价于  def curriedSum(x: Int, y: Int) = x + y
展开来就是：
def first(x: Int) = (y: Int) => x + y
def second = first(1)    等价于   val onePlus = curriedSum(1)_     // 此处占位符表示传给curriedSum(1)的参数
上层调用形式为：
second(2)              等价于   onePlus(2)

// 形参1是个函数指针，形参2是Double类型，函数体里把一个函数调用两遍
def twice(op: Double => Double, x: Double) = op(op(x))  
twice(_ + 1, 5)  //  得到7，即5加了两次1


【名参数 by-name parameter】  仅入参为空的时候可以这么用，为了让函数显得像 if 之类的控制关键字
原有格式如下：
var assertionsEnabled = true
def myAssert(predicate: () => Boolean) =   //  传一个函数指针，入参为空，返回值是布尔类型
	if (assertionsEnabled && !predicate())
	throw new AssertionError
	
myAssert( () => 5 > 3)  //  () 不能省，因为表示函数调用，入参为空，省略了结构不完整

// 注意到，此种定义形式与 predicate: Boolean 直接传布尔类型的区别在于，此处是传递的一个函数，在封装内部实现时才执行
// 可以理解为一个延迟执行的表达式，而如果是 predicate: Boolean 类型，函数调用前入参会先被计算，然后将结果作为参数传递
def byNameAssert(predicate: => Boolean) =       //  定义的意义同上，但是省略()，表示名参数
	if (assertionsEnabled && !predicate)
	throw new AssertionError
	
byNameAssert(5 > 3)

 ///////////////////////////////                     控制结构                ///////////////////////////////

/////////// if语句，scala中所有语句都返回一个值(函数式语言的特点)，因此可以直接把结果作为执行语句返回
//  注意此处的变量类型，在文件名变量存储值基本不会变更的情况，使用val类型，更安全高效易读；
//  此外，表明变量与右侧计算式等价(函数语言思路)
val filename = if (!args.isEmpty) args(0) else "default.txt"

/////////// while语句，while和do-while在scala中是循环而不是表达式，返回值永远是 Unit 等价于 ()
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
	b       //  此处b是这个函数的返回值
}
// 同样功能的递归式写法(函数式思维)
def gcd(x: Long, y: Long): Long = if y == 0 x else gcd(y, x%y)

/////////// for语句
// 此写法不同于其他语言的特点在于，不需要定义一个下标遍历 i ，通过下标的自增，从集合里面抽取该下标元素来访问集合元素，
// 优点：有效避免的数组越界、0/1起始等问题
val filesHere = (new java.io.File(".")).listFiles
for (file <- filesHere)   //  生成器语句，对于所有集合类(不只数组)，会依次把集合元素放入变量
	println(file)
	
for ( i <- 1 to 5 )   //  同上，遍历打印1到5
	println(i)
	
for ( i <- 1 until 4 )  //  遍历打印1到3 （不包括上限）
	println(i)

// 过滤器，在生成器语句后加 if，比在循环体语句里面加 if 的好处是：后者会导致for循环的打印语句返回值 Unit 作为for语句返回值返回
val filesHere = (new java.io.File(".")).listFiles
for (file <- filesHere if file.getName.endsWith(".scala"))
	println(file)
// 筛选打印 .scala结尾的文件名	
for ( file <- filesHere
	  if file.isFile
	  if file.getName.endsWith(".scala")   //  多个过滤器叠加
)println(file)
// 筛选打印当前目录下所有 .scala 文件里包含 gcd 三个字符的文件的文件名
def fileLines(file: java.io.File) = scala.io.Source.fromFile(file).getLines().toList
def grep(pattern: String) =
	for (
		file <- filesHere
		if file.getName.endsWith(".scala");
		line <- fileLines(file)
		trimmed = line.trim             // 注意到此处trimmed变量是临时生成的，循环体和判断里可以使用
		if trimmed.matchs(pattern)        //  复合迭代
	) println(file + ": " + trimmed)
grep(".*gcd.*")

// yield语句，通过该语句生成集合，标准格式 for clauses yield body，实质是把for语句里最后元素用 yield 输出
def scalaFiles = 
	for {
		file <- filesHere
		if file.getName.endsWith(".scala")   // 注意此处迭代和筛选都放在 {} 里，yield 关键字在完整语句结束之前
	} yield file    // 生成以文件名为元素的数组 Array[File]
//  获取一个包含 for字符的scala文件的该行长度集合，类型 Array[Int]
val forLineLengths = for {
	file <- filesHere
	if file.getName.endsWith(".scala")
	line <- fileLines(file)
	trimmed = line.trim
	if trimmed.matchs(".*for.*")
	} yield trimmed.length
	
// 异常处理 try-catch-finally 结构也有返回值，要避免在finally语句里使用 return
throw new IllegalArgumentException

val half = 
	if (n%2 == 0) n/2
	else 
		throw new RuntimeException("n must be even") // 抛出一场的语句也是有返回值的，类型是 Nothing，后序语句永不执行
	
import java.io.FileReader
import java.io.FileNotFoundException
import java.io.IOException
try {
	val f = new FileReader("input.txt")
} catch {
	case ex: FileNotFoundException =>
	case ex: IOException =>
} finally {        // 不管异常结果必然执行语句，确保资源得到释放
	f.close()
}

def f(): Int = try return 1 finally return 2           //  得到结果2，因为return语句返回值覆盖了try语句
def g(): Int = try 1 finally 2                 //  得到结果1，因为try语句的执行结果作为整个语句结果返回

// match-case 语句，类似其他语言里的 switch-case 语句，区别在于 case 判断不限类型
// 同时，由于没有break语句，也没有其他语言中多个case共用一个执行体的情况，一个case执行完后就退出
val firstArg = if (args.length > 0) args(0) else ""
firstArg match {      
	case "salt" => println("pepper")
	case "chips" => println("salsa")
	case "eggs" => println("bacon")
	case _ => "huh?"           //  默认兜底语句
}
// 另一种写法，注意到 match 语句也是有返回值的，直接将返回值打印避免很多重复编码，同时语义解耦清晰（选择，输出）
val friend = firstArg match {         
	case "salt" => "pepper"
	case "chips" => "salsa"
	case "eggs" => "bacon"
	case _ => "huh?"
}
println(friend)
	
// 不使用break和continue的函数式筛选写法，scala中也没有将这两个作为基本关键字
// 其他语言的写法，匹配非-后continue执行.scala判断，匹配.scala之后break退出循环
def searchFrom(i: Int): Int = 
	if (i >= args.length) -1 
	else if (args(i).startsWith("-")) searchFrom(i+1)
	else if (args(i).endsWith(".scala") i
	else searchFrom(i+1)
val i = searchFrom(0)

// 强行使用break的方法，注意此处强行跳出到breakable标记的语句块 {} 结尾，类似其他语言的goto语句，
// 底层使用异常机制实现，可以跨多层函数跳转，强于其他语言break，弱于goto
import scala.util.control.Breaks._
import java.io._

val in = new BufferedReader(new InputStreamReader(System.in))
breakable {
	while (true) {
		println("? ")
		if (in.readLine() == "") break
	}
}
	
 ///////////////////////////////                     类定义相关                /////////////////////////////// 
 
 ////////////  Scala中禁止定义同名的类方法和类变量，子类里面有同名变量时，甚至能覆盖父类里同名无参方法
 //////// Java里面有4种名空间：变量，方法，类型，包    
 ///////  Scala里面只有2种名空间：变量 values (fields, methods, packages, and singleton objects)
 ///////                          类型 types (class and trait names)
 
//  声明 Int类型到 Rational类型的默认转换，以自动在类似  2 * rational 这种计算时完成 2默认转换成 Rational的效果 
implicit def intToRational(x: Int) = new Rational(x) 
 
//  scala倾向于使用静态类（immutable object），除了多次拷贝后占用更多内存这个缺点外有4点好处：
//   1、状态易维护；2、作为参数传递下无需担心内容变更；3、多线程安全；4、可以作为hash键使用
//  类定义，后面要接形参和类型，形参可以在类定义里任何地方使用，万能类型有 Unit，如果类没有具体内容，不需要强制加上 {}
class Rational(n: Int, d: Int) extends Ordered[Rational] {   // 继承 trait 时，该传操作数类型要传类型
	// 类定义里的任何可执行代码都会被编译器放到构造函数内，按照显示的先后顺序执行，如下打印会在对象生成时执行
	println("Create " + n + "/" + d)
	// 前置条件，用于在构造函数调用前检查入参，若不满足前置条件要求，对象不能生成，抛出IllegalArgumentException异常
	require(d != 0)
	private val g = gcd(n.abs, d.abs)
// 域定义，由于形参仅在本对象内有效(可以理解为private)，在外部使用时需要定义新的域变量，例如下面的that.denom使用场景
// 完整写法是 val numer: Int = n / g
	val numer = n / g
	val denom = d / g
	
	// 辅助构造函数，多态的基础，此处处理分母为1的情况，此类构造函数必须调用其他更基础的构造函数，
	// 本质上都要走主构造函数，这是scala的为了方便主构造函数编写引入的折中，本行之前的部分都是主构造函数的内容
	def this(n: Int) = this(n, 1)
	
	//  this 指针依然可用，等同于java里的this，指代本对象
	def lessThan(that: Rational): boolean = numer * that.denom < that.numer * denom
	def max(that: Rational): Rational = if (lessThan(that)) that else this
	
	//   运算符优先级默认继承scala已实现的该类运算符优先级，此处实现了两种形式的加法
	def + (that: Rational): Rational = new Rational( numer * that.denom + that.numer * denom, denom * that.denom )
	def + (i: Int): Rational = new Rational( numer + i * denom, denom )
	
	def - (that: Rational): Rational = new Rational( numer * that.denom - that.numer * denom, denom * that.denom )
	def - (i: Int): Rational = new Rational( numer - i * denom, denom )
	
	def * (that: Rational): Rational = new Rational( numer * that.numer, denom * that.denom )
	def * (i: Int): Rational = new Rational( numer * i, denom )
	
	def / (that: Rational): Rational = new Rational( numer * that.denom, denom * that.numer )
	def / (i: Int): Rational = new Rational( numer, denom * i )
	
	// 重载 toString 函数，交互窗口里生成对象时如未重载会调用默认的 java.lang.Object 对象的toString 打印结果
	override def toString = numer + "/" + denom
	
	// 私有方法
	private def gcd(a: Int, b:Int): Int = if (b == 0) a else gcd(b, a%b)
	
	// 本方法作为继承的 trait 需要使用的基本方法，在类里面实现时候
	def compare(that: Rational) = (this.numer * that.denom) - (that.numer * this.denom)
}

//  注意到在此处并没有实现相等判断操作，因为等价判断跟类型相关，而此处实现时，具体类型是不明的
trait Ordered[T] {      //  抽象出来的比较特性，除了给 Rational 用，也可以给其他有类似功能的类用， 感觉上类似泛型
	def compare（that: T): Int  // 此处虚拟方法，没有函数体，留在继承本 trait 的类去实现
	
	def <(that: T): Boolean = (this compare that) < 0
	def >(that: T): Boolean = (this compare that) > 0
	def <=(that: T): Boolean = (this compare that) <= 0
	def >=(that: T): Boolean = (this compare that) >= 0
}

【抽象类】  abstract class Element ....
abstract class Element {
	def contents: Array[String]   //  抽象类的方法，这类方法都没有具体实现，scala中没有实现函数体的默认就是 abstract
	def height: Int = contents.length   // 无参 类方法，其实后面的length也是调用的类方法，获取元素个数
	// val height = contents.length  //从上层效果看与上一行函数定义一样，但是一个是函数调用返回结果，一个是返回变量值
	def width: Int = if (height == 0) 0 else contents(0).length   // 取一个元素的长度作为宽度(此处是字符个数)
}
在scala中，推荐无副作用(side effects)的无参方法定义和调用时不带 () ，有副作用的方法要带()，让人看出来是在调用方法而不是读取变量（如前面的 contents.length 无上下文的话无法区分）
一般IO操作，写var变量，直接或间接使用可变变量 都认为是容易有副作用的

【继承】 超类的所有私有变量都不被子类继承
class ArrayElement(conts: Array[String]) extends Element {
	//scala里面变量和方法在同一个名空，因此如下写法也会override父类同名函数，虽然父类里声明的是个方法而此处是变量
	val contents: Array[String] = conts    //  仅是接收存储变量时，scala更倾向本行的写法
	//  def contents: Array[String] = conts   等价于上一行定义
}
更优化写法（避免无意义的变量重复）：
class ArrayElement(val contents: Array[String]) extends Element // 注意到变量名contents要与父类相同，此变量可外部访问
{
	final override def demo() = {   // 通过 final 来禁止 ArrayElement 的子类 override demo方法
		println("ArrayElement's implementation invoked")
	}
}
//  继续继承时，调用父类的构造函数，由于父类构造是需要传参，在声明中将 Array(s) 传入，表明调用了父类构造函数
//  将类声明为 final 后，不可被其他类继承
final class LineElement(s: String) extends ArrayElement(Array(s)) {
	override def width = s.length
	override def height = 1
}
//  所有父类已经实现过的方法，覆盖时必须加 override 关键字，未实现的抽象方法不强制要求， 本类方法无函数实现时不能加，
//  如此设计的好处是，1对N的父子类关系时，在父类新增一个方法，若某个子类实现同名方法，会出发编译告警

//  定义参数化域变量
class Cat { val dangerous = false }
class Tiger { override val dangerous: Boolean, private var age: Int) extends Cat
等价于
class Tiger(param1: Boolean, param2: Int) extends Cat {
	override val dangerous = param1
	private var age = param2
}

【多态】 用父类的类型声明变量，用子类的类型进行实例化，使用时累方法的实现按子类来，达到同名类方法不同动作的目的
val e1: Element = new ArrayElement(Array("hello", "world"))


【类层次】
所有类的父类：  Any
该类定义的基本方法如下：
final def ==(that: Any): Boolean  // 本质上就是 equals ，因此虽然是 final ，但是可以通过继承重载 equals 来改变实现
final def !=(that: Any): Boolean  // 本质上是 equals 取反
def equals(that: Any): Boolean
def ##: Int    //   哈希运算符
def hashCode: Int
def toString: String // 格式化运算，基本就是输出字符串

第二层次2大父类之一】： AnyVal  继承Any的子类，是Byte, Short, Char, Int, Long, Float, Double, Boolean, Unit的父类

上述前8个类型的所有 new Int 基本操作都不可行，字面量（或者说实例化的数值类）都是直接表示，  如 42，'x', 1.998 等，因为所有的值都被定义为 abstract 和 final

Unit 大致等于 Java的 void， 唯一的字面量（实例化值）是 ()

第二层次2大父类之二】： AnyRef 继承Any，是所有引用累的基类，对应Java中的 java.lang.Object
// 非完全体 OO 语言的特点
// This is Java
boolean isEqual(int x, int y) {
return x == y;
}
System.out.println(isEqual(421, 421));  //  得到 true ，数值比较
boolean isEqual(Integer x, Integer y) {
	return x == y;
}
System.out.println(isEqual(421, 421));    // 得到 false ， 对象引用比较
//Java中该语句返回false，因为==判断的是两个变量是否引用同一个对象，而两个421分别做了两次转换成对象的操作，因此分别指向各自转换的对象

// 完全体OO语言的特点，对于类型代表的意义，== 操作是透明的
def isEqual(x: Int, y: Int) = x == y
isEqual(421, 421)    //   得到 true 数值比较
def isEqual(x: Any, y: Any) = x == y
isEqual(421, 421)   //   得到 true 对象比较，但对于数值，具体的数值类底层进行了重载override
//  Java里面字符串比较陷阱不会出现，如下
val x = "abcd".substring(2)
val y = "abcd".substring(2)
x == y    // 得到 true

引用比较  eq 和 ne
val x = new String("abc")
val y = new String("abc")
x == y  //  得到 true
x eq y  //  得到 false， 引用比较，不是引用的同一个对象
x ne y  //  得到 true

底层2大子类之一】  scala.Null 所有引用类 AnyRef子类的子类，与数值类不兼容，因此如下写法不成立
val i: Int = null   //  报编译告警，null与数值类不兼容

底层2大子类之二】 scala.Nothing 没有任何实际值，用于异常处理时，表明函数无法正常返回，因此也没有变量能实际接收了这个值
def error(message: String): Nothing =
	throw new RuntimeException(message)

【单例模式】 factory object 工厂对象？   详见  162页 10.13 这块没看懂  ！！！！！！！！！！！！！！！！！！！！！！

 ///////////////////////////////                     trait                /////////////////////////////// 
 trait 的想法是是在Java接口定义的方法和类定义的方法的多寡之间进行折中平衡，由于trait中的方法有函数体，因此在继承他的类中不用继续实现（JAVA interface中定义的都是抽象函数），方便扩展，同时避免在子类的父类中新增方法时引入问题的可能
 （但是如果一个trait被多个子类继承，怎么保证trait里修改某个方法的实现影响最小？？？？？？）
 
思考一下与Java 中接口的区别: 
1、Java接口中不能定义具体方法的实现，scala可以；
2、trait中可以声明变量并维护其状态，基本上与一个类完全一致，除了一下两天区别
   1）trait 定义时不能有参数
   2）trait 中所有被继承的方法在子类中使用时都是动态绑定的


 trait 可以当做类型使用，所有混入 trait 的类都可以用来实例化 trait 类型变量
 
val phil: Philosophical = frog
phil.philosophize()
 
trait Philosophical { //  默认的父类是 AnyRef
	def philosophize() = {
		println("I consume memory, therefore I am!")
	}
}
class Frog extends Philosophical {  // mix in Philosophical ， 继承的是 trait 的超类
	override def toString = "green"
	override def philosophize() = {   //  重载 trait 中的方法
		println("It ain't easy being " + toString + "!")
	}
}

class Animal
trait HasLegs
class Frog extends Animal with Philosophical with HasLegs {   // 继承一个类，并且混入两个 trait
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

trait Doubling extends IntQueue {  // 声明中有超类，表明该trait只能被IntQueue的子类混入，如BasicIntQueue
	// 要使用 super， 前面的abstract override不可少，仅在trait中可以这么用
	abstract override def put(x: Int) = { super.put(2 * x) } // 调用重载本函数的子类的实现
}
  
val queue = new BasicIntQueue with Doubling   // 此处比定义一个新类继承BasicIntQueue和Doubling更快捷
queue.put(10); queue.get()   // 得到 20

trait Incrementing extends IntQueue {
	abstract override def put(x: Int) = { super.put(x + 1) }
}
trait Filtering extends IntQueue {
	abstract override def put(x: Int) = { if (x >= 0) super.put(x) }
}
val queue = (new BasicIntQueue with Incrementing with Filtering) //对于不同trait同样的函数的修改，生效顺序遵循从左到右
queue.put(-1); queue.put(0); queue.put(1)
queue.get(); queue.get()     // 分别得到1 和 2


 ///////////////////////////////                     函数相关                /////////////////////////////// 

 
 
 import scala.io.Source
 object LongLines {
	def processFile(filename: String, width: Int) = {
		// 此处在函数内部定义了一个函数，其他语言的写法是把这个函数放到上层同一级然后加private，scala认为这样暴露接口太多不美观
		def processLine(line: String) = {
			if (line.length > width)
				println(filename + ": " + line.trim)
		}
		
		val source = Source.fromFile(filename)
		for (line <- source.getLines())
			processLine(line)
	}
}
 
 function literal： 函数字面量 包装的未命名的函数，整体作为参数传递。 源码中的说法
 function value： 在function literal编译成类并在运行时实例化后就是function value。 运行时中function literal具体内容的代名词
 示例：（调研一下与lambda表达式有什么区别？？？？？？？？？？？？？？？）
 (x: Int) => x + 1
 var increase = (x: Int) => x + 1
 increase(10)
 
 集合类自带的一种函数字面量用法 注意到这些用于都要配合类定义的一些方法
 val someNumers = List(-11, -10, -5, 0, 5, 10, 11)
 someNumers.foreach( (x: Int) => println(x) )   //  将集合中所有元素打印，比一般for遍历写法方便太多
 someNumers.foreach( println _ )           // 同上，简练写法，用 _ 代表集合，partially applied function 部分补完函数
 someNumers.foreach(println)      //  同上，最简练写法，省略所有参数，部分情况下可用：仅在入参为函数的情况下使用(foreach)
 someNumers.filter( (x: Int) => x > 0 )   //  过滤掉小于等于0的元素
 someNumers.filter( x => x > 0 )       //  更简练的写法，语义同上
 someNumers.filter( _ > 0 )           // 最简练的写法，语义同上，注意此种写法仅在填充入参在执行体中仅出现一次时可用
 
 
 val f = (_: Int) + (_: Int)  // 由于scala无法判断类型，此种写法需将类型带上，此处接收两个参数
 f(5, 10)
 
def sum(a: Int, b: Int, c: Int) = a + b + c     // 定义一个函数
 val a = sum _   //  将函数赋值给一个变量，通过 _ 省略了参数，编译器自动识别， partially applied function的一种
                //  此处sum本质上被封装成了类，该类定义一个apply函数实现sum的函数功能，然后实例化一个对象给a
 a(1, 2 ,3)    //   等价于 a.apply(1, 2, 3) ， 得到6
 
 val b = sum(1, _: Int, 3)   // 提供两个参数的 partially applied function，默认缺省一个使用时补充
 b(5)              //  得到 9

 【闭包的范围】  书中解释是  函数字面量中使用的外部变量的值，取这个函数字面量生成时该变量的值，见示例3
 示例1：
 var more = 1
 val addMore = (x: Int) => x + more
 addMore(10)   //  得到数字11，因为more的值是1
 more = 9999
 addMore(10)   //  得到10009，因为more的值变更为 9999
 // 注意到如果函数字面量引用到外部变量，外部变量的变更会时时同步到 函数字面量的变量上
 
 示例2：
  var sum = 0            //  closure 问题，同上，外部的sum被影响了
someNumers.foreach(sum += _)   //  本语句执行完毕后，sum的值也发生变更，变成 -11，注意sum的范围

示例3：   与示例1的区别是，示例1中more是外部变量，此处more是传入的形参
def makeIncreaser(more: Int) = (x: Int) => x + more
val inc1 = makeIncreaser(1)     //  实例化时，使用的more是存储在内存堆上的，与调用栈无关
val inc9999 = makeIncreaser(9999)
inc1(10)      //  得到11，因为inc1生成时候more是1
inc9999(10)     //  得到10009，因为inc9999生成时more是9999
 
 【多参数匹配】
 def echo(args: String*) = for ( arg <- args ) println(arg)    //  * 表示可以接多个同类参数，此处表示多个String类型作为参数
 echo("one")
 echo("hello", "world")
 
 val arr = Array("what's", "up", "doc?")
 echo(arr: _*)   //  必须如此写，告诉编译器取出arr中每个元素分别作为参数传给echo，否则 echo(arr) 会认为传的一个参数，类型是数组
 
 
 【实参命名赋值】  可以不按照函数声明顺序传参，一般结合默认参数的方法一起用，即默认参数不写，剩下的用命名参数传参
 def speed(distance: Float, time: Float): Float = distance/time
 speed(time = 10, distance == 100)  //  等价于  speed(100, 10)
 
 【默认参数】
 //  out默认参数是控制台，divisor默认赋值 1
 def printTime2(out: java.io.PrintStream = Console.out, divisor: Int = 1) =
			out.println("time = " + System.currentTimeMillis()/divisor)
printTime2(out = Console.err)
printTime2(divisor = 1000)
 
 针对简单的递归函数，scala进行了优化，所有函数都在同一层而没有用多个调用栈，如下：
 def bomm(x: Int): Int = 
	if (x == 0) throw new Exception("boom!")
	else boom(x - 1) + 1
所谓简单递归调用函数，即函数体中仅调用定义的函数本身并且该调用作为递归中的最后一条语句执行的情况，如下两种情况不会进行优化

示例1：函数体中未调用自身，不优化
def isEven(x: Int): Boolean = if (x == 0) true else isOdd(x - 1)
def isOdd(x: Int): Boolean = if (x == 0) false else isEven(x - 1)
示例2：函数用变量重新包装过，scala识别不了，不优化
val funValue = nestedFun _
def nestedFun(x: Int): Unit = { if (x != 0) { println(x); funValue(x - 1) } }

 
  ///////////////////////////////                     常用函数说明                ///////////////////////////////
  
 【zip】
 将数组内容配对，以元素少的一方配对完毕后结束
 Array(1, 2, 3) zip Array("a", "b")   //  得到  Array((1, "a"), (2, "b"))
 
 【mkString】
 所有的sequence如数组都实现了本函数，通过调用每个元素的toString方法将每个元素变成字符串，使用separator连接各个字符串
 array mkString separator
 
 【max】
 42 max 43
 
 【min】
 23 min 43
 
 【until】
 1 until 5  // 得到 Range(1, 2, 3, 4)
 
 【to】 
 1 to 5     //  得到 Range(1, 2 ,3 ,4 ,5)
 
 【abs】
 (-3).abs