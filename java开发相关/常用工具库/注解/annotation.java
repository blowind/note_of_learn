
/**
 * 使用场景：
 * 1、大量重复的初始化脚手架代码时；
 * 2、大量重复的XML元素配置
 */

/**
 * Target：指明注解支持的使用范围，取值参考枚举ElementType
 * ElementType.TYPE  //类、接口、枚举
 * ElementType.FIELD //属性
 * ElementType.METHOD //方法
 * ElementType.PARAMETER //参数
 * ElementType.CONSTRUCTOR //构造器
 * ElementType.LOCAL_VARIABLE //局部变量
 * ElementType.ANNOTATION_TYPE //注解
 * ElementType.PACKAGE   //包
 *
 * Retention：指明注解保留的的时间长短，取值参考枚举RetentionPolicy
 * SOURCE //源文件中保留
 * CLASS //class编译时保留
 * RUNTIME //运行时保留
 *
 * Inherited：指明该注解类型被自动继承。
 * 如果一个annotation注解被@Inherited修饰,
 * 那么该注解作用于的类的子类也会使用该annotation注解
 * 注意一定是子类继承父类的注解，接口或者方法重载没有继承的说法！！！！
 *
 * Documented：指明拥有这个注解的元素可以被javadoc此类的工具文档化
 *
 * 注解本质上是个接口，不能继承其他注解，所以也就没有extends的用法了
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface UseCase {
	/** 注解元素内部可以使用的类型
	 *  1、所有基本类型，如int/float/boolean，此处使用了int（不许使用包装类型）
	 *  2、String
	 *  3、Class
	 *  4、enum
	 *  5、Annotation
	 *  6、以上类型的数组
	 *
	 *  所有注解内的元素要么必须有默认值，要么使用时必须指定，不允许使用null作为值
	 *  所有元素只能是public或者package-public
	 */
	// 所有方法定义的变量是使用时都是类似key=value模式的写法
	int id();  // 每一个方法实际上是声明了一个配置参数，方法名是参数名，返回值类型是参数类型
	String description() default "no description";  // 带默认值的注解元素
}


// 获取类的一般套路
Class<?> clazz = Class.forName("com.zxf.apply.Member");

Class clazz = this.getClass().getClassLoader().loadClass("com.zxf.apply.Member");


// 对注解属性处理的一般套路
Field[] fields = clazz.getDeclaredFields();
for(Field field : fields) {
	// 属性是否带有指定注解
	if(field.isAnnotationPresent(MyAnnotation.class) {
		MyAnnotation anno = field.getAnnotations(MyAnnotation.class);
		...
	}

}

// 对注解方法处理的一般套路
Method[] methods = clazz.getMethods();
for(Method method : methods) {
	// 方法是否带有指定的注解
	if(method.isAnnotationPresent(MyAnnotation.class)) {
		MyAnnotation anno = method.getAnnotation(MyAnnotation.class);
	}
}