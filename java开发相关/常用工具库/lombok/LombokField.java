package com.zxf;

import lombok.*;
import java.io.*;


// @Getter 放在类上，对所有非static变量生成get方法
// @ToString(exclude="id") 自动生成toString()方法，可以通过exclude排除不需要打印的字段
// @EqualsAndHashCode(exclude={"id", "shape"}, callSuper=true) 自动生成hashCode和equals方法
// @NoArgsConstructor(force = true) 生成一个无参构造函数，如果有final域，使用force强制所有final域为0/false/null
// @RequiredArgsConstructor 给每个域生成一个一参数的构造函数
// @RequiredArgsConstructor(staticName = "of") 生成一个静态工厂函数of和一个私有构造函数，工厂方法调用私有构造方法生成对象并返回
// @AllArgsConstructor(access = AccessLevel.PROTECTED)

// @Data 等价于使用@ToString, @EqualsAndHashCode, @Getter, @Setter,@RequiredArgsConstructor
// @Data(staticConstructor="of")
@ToString(includeFieldNames=true)
@EqualsAndHashCode(callSuper=false)
public class LombokField {

	private String name;

	private transient int transientVar = 10;

	/* 指定setter方法的级别为 protected */
	@Setter(AccessLevel.PROTECTED) private String nickname;

	/* 根据变量名自动生成get和set方法， */
	@Getter
	@Setter
	private int age = 10;

	// 函数参数判空处理，若为NULL则抛出异常
	public LombokField(@NonNull String name) {
		this.name = name;
	}

	public static void main(String[] args) throws IOException{
		/* 资源清理，保证资源变量失效前，仅支持资源关闭函数不带参数的情况
		 * 默认资源关闭函数是close()，若函数名不一样需指定，如下dispose的关闭函数
		 * @Cleanup("dispose") org.eclipse.swt.widgets.CoolBar bar = new CoolBar(parent, 0);
		 */
//		@Cleanup InputStream in = new FileInputStream(args[0]);
//		@Cleanup OutputStream out = new FileOutputStream(args[1]);
//		byte[] b = new byte[1000];
//		while(true) {
//			int r = in.read(b);
//			if(r == -1) break;
//			out.write(b, 0, r);
//		}

		LombokField a = new LombokField("myname");
		a.setNickname("newNickname");
		System.out.println(a.getAge());
		System.out.println(a.toString());
	}
}
