

/*****************  使用builder改造多可选参数的类的构造函数    ***************/

问题背景/使用要求： 
1、多参数初始化（原则上4个及以上的参数，或者预期未来大概率参数超过4个）
2、大部分参数是可选参数（有默认值）
3、可以接受参数缓存到builder带来的性能损失

基本写法举例：
public class NutritionFacts {
	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;
	// 注意使用内部类作为构造器时，类必须是static的，
	// 因为只有静态内部类可以直接new，普通内部类必须绑定在其外部类的实例上new，即oc.new NormalInnerClass();
	public static class Builder {
		// Required parameters，构造器类缓存需初始化使用的参数
		private final int servingSize;
		private final int servings;
		// Optional parameters - initialized to default values
		private int calories = 0;
		private int fat = 0;
		private int sodium = 0;
		private int carbohydrate = 0;
		
		// 构造器内部类的构造函数包含所有必须的参数
		public Builder(int servingSize, int servings) {
			this.servingSize = servingSize;
			this.servings = servings;
		}
		// 可选参数的返回类型都是内部构造类，用于链接参数
		public Builder calories(int val) { 
			calories = val; return this; 
		}
		public Builder fat(int val) { 
			fat = val; return this; 
		}
		public Builder sodium(int val) { 
			sodium = val; return this; 
		}
		public Builder carbohydrate(int val) { 
			carbohydrate = val; return this; 
		}
		// 不解释
		public NutritionFacts build() {
			return new NutritionFacts(this);
		}
	}
	// 外部类的构造器强烈建议私有，保证实例都通过内部构造器类初始化
	private NutritionFacts(Builder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;
	}
}
使用代码：
NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8).calories(100).sodium(35).carbohydrate(27).build();


带继承的写法举例：
// 父类
public abstract class Pizza {
	// 此处把所有可选的配料抽取到基类作共性部分
	public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE};
	final Set<Topping> toppings;
	
	abstract static class Builder<T extends Builder<T>> {
		EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
		
		public T addTopping(Topping topping) {
			toppings.add(Objects.requiresNonNull(topping));
			return self();  // 返回子类的buidler，所以此处不能返回Pizza.Builder，要用self()获取
		}
		
		// build()方法做成抽象的，因为子类的必须项可能各有差异
		abstract Pizza build();
		// 子类必须复写的用来返回this的方法，因为此处要返回子类类型的实例
		protected abstract T self();
	}
	
	Pizza(Builder<?> builder) {
		// 将构造器的枚举参数内容拷贝到基类的域变量，此处枚举比较特别，一般不同类型的参数都是使用=进行赋值
		toppings = builder.toppings.clone();
	}
}
// 子类1
public class NyPizza extends Pizza {
	public enum Size { SMALL, MEDIUM, LARGE };  // 子类扩展尺寸特性
	private final Size size;
	
	public static class Builder extends Pizza.Builder<Builder> {
		private final Size size;
		// 必须设置的参数使用构造方法设定，添加构造方法
		public Builder(Size size) {
			this.size = Objects.requiresNonNull(size);
		}
		
		@Override
		public NyPizza build() {
			return new NyPizza(this);
		}
		
		@Override
		protected Builder self() {
			return this;
		}
	}
	
	private NyPizza(Builder builder) {
		super(builder);
		size = builder.size;
	}
}
// 子类2  没有必须添加的元素，则不覆写Builder构造方法
public class Calzone extends Pizza {
	private final boolean sauceInside;
	
	public static class Builder extends Pizza.Builder<Builder> {
		private boolean sauceInside = false;  // 默认为false
		// 非必须设置的参数使用一般的set方法
		public Builder sauceInside() {
			sauceInside = true;
			return this;
		}
		
		@Override
		public Calzone build() {
			return new Calzone(this);
		}
		
		@Override
		protected Builder self() {
			return this;
		}
	}
	
	private Calzone(Builder builder) {
		super(builder);
		sauceInside = builder.sauceInside;
	}
}
// 使用代码：
NyPizza pizza = new NyPizza.Builder(SMALL).addTopping(SAUSAGE).addTopping(ONION).build();
Calzone calzone = new Calzone.Builder().addTopping(HAM).sauceInside().build();


覆写equals方法的准则
1、使用 == 操作符判断比较的是否是自己，若是返回true
2、使用 instanceof 操作符检查入参类型是否正确，不正确返回false，由于instanceof的左操作数为NULL永远返回false，因此节省了入参判空操作
   instanceof的右操作数可以是接口类型，用于在实现了同一个接口的类中进行比较
3、将入参强制转换为正确的类型，一般为instanceof的右操作数
4、对每个重要的属性进行一一对比
5、对非float和double的所有基本类型，使用==操作符进行比较，对类类型使用对应的equals方法比较，
   对float使用Float.compare(float, float)比较，对double使用Double.compare(double, double);
   对可能为NULL的属性，使用Objects.equals(Object, Object)比较
6、不要比较存储锁的属性，不要比较通过本地属性组合计算出来的属性除非该属性可以作为对象的特性签名提高性能
7、永远要保证反身性，对称性，传递性，一致性和非空型的原则
8、入参类型一定要是Object否则不会Override要覆写的equals方法，一定不允许重载equals方法
9、使用google的AutoValue开源框架写equals()和hashCode()方法，尽量不要自己手写！！！！！！
10、覆写equals()方法时一定要覆写hashCode()方法，否则在HashMap和HashSet中使用对象做key时使用equals的对象读取不到存入的值
11、对于要新增扩展属性的情况，使用组合(策略模式)而不是使用继承(extends)

示例：
public final class PhoneNumber {
	private final short areaCode, prefix, lineNum;
	
	@Override
	public boolean equals(Object o) {
		if(o == this)  return true;
		if(!(o instanceof PhoneNumber)) return false;
		PhoneNumber pn = (PhoneNumber)o;
		return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
	}
	
	@Override
	public int hashCode() {
		int result = Short.hashCode(areaCode);
		result = 31*result + Short.hashCode(prefix);
		result = 31*result + Short.hashcode(lineNum);
		return result;
	}
}