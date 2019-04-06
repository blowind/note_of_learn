package com.zxf.run;

import com.zxf.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *  @ClassName: TableCreator
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2019/3/27 14:30
 *  @Version: 1.0
 **/
public class TableCreator {
	public static void main(String[] args) throws Exception {
		// 参数要给出全限定名，如com.zxf.apply.Member
		if(args.length < 1) {
			System.out.println("Arguments: annotated classes");
			System.exit(0);
		}

		for(String className : args) {
			// 通过反射拿到类定义
			Class<?> cl = Class.forName(className);
			// 获取指定注解
			DBTable dbTable = cl.getAnnotation(DBTable.class);
			if(dbTable == null) {
				System.out.println("No DBTable annotations in class " + className);
				continue;
			}
			// 没有指定表名时用类名做表名
			String tableName = dbTable.name();
			if(tableName.length() < 1) {
				tableName = cl.getName().toUpperCase();
			}
			// 遍历类的所有域变量，找出@SQLInteger和@SQLString注解进行解析
			List<String> columnDefs = new ArrayList<>();
			for(Field field : cl.getDeclaredFields()) {
				String columnName = null;
				Annotation[] anns = field.getDeclaredAnnotations();
				if(anns.length < 1) {
					continue;
				}
				// 遍历域的所有注解，找出需要处理的注解并处理
				for(Annotation ann : anns) {
					if(ann instanceof SQLInteger) {
						SQLInteger sInt = (SQLInteger)ann;
						if(sInt.name().length() < 1) {
							columnName = field.getName().toUpperCase();
						}else{
							columnName = sInt.name();
						}
						// 处理嵌套注解时可以抽取处理步骤进一步封装
						columnDefs.add(columnName + " INT" + getConstraints(sInt.constraints()));
					}
					if(ann instanceof SQLString) {
						SQLString sString = (SQLString)ann;
						if(sString.name().length() < 1) {
							columnName = field.getName().toUpperCase();
						}else{
							columnName = sString.name();
						}
						columnDefs.add(columnName + " VARCHAR(" + sString.value() + ")" + getConstraints(sString.constraints()));
					}
				}
			}
			StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");
			for(String columnDef : columnDefs) {
				createCommand.append("\n   " + columnDef + ",");
			}
			// 去掉最后一次尾部多加的,符号并结尾
			String tableCreate = createCommand.substring(0, createCommand.length() - 1) + "\n);";
			System.out.println("Table Creation SQL for " + className + " is :\n" + tableCreate);

			int tests = 0;
			int passed = 0;
			for(Method m : cl.getDeclaredMethods()) {
				if(m.isAnnotationPresent(Test.class)) {
					tests++;
					try{
						m.invoke(null);
						passed++;
					}catch (InvocationTargetException wrappedExc) {
						/* 被执行的函数内部抛出的异常，被InvocationTargetException捕获，通过getCause()获取 */
						Throwable exc = wrappedExc.getCause();
						System.out.println(m + " failed: " + exc);
					}catch (Exception exc) {
						/* 不正确的使用@Test注解，则抛出非InvocationTargetException异常，
						   比如示例将@Test注解应用在非静态函数上，由于m.invoke(null);的用法不是非静态函数用法，
						   因此在执行这个语句时抛出异常，
						   类似的还有用在带参数的静态函数上，用在没有访问权限的函数上
						   这些异常都是m.invoke(null)这个调用语句本身导致的 */
						System.out.println("Invalid @Test: " + m);
					}
				}

				if(m.isAnnotationPresent(ExceptionTest.class)) {
					tests++;
					try{
						m.invoke(null);
						/* 未抛出指定异常的处理代码放在此处，运行到这里时执行的函数未抛出任何异常 */
						System.out.println("Test %s failed: no exception %n" + m);
					}catch (InvocationTargetException wrappedEx) {
						Throwable exc = wrappedEx.getCause();
						Class<? extends Throwable> excType = m.getAnnotation(ExceptionTest.class).value();
						if(excType.isInstance(exc)) {
							passed++;
						}else{
							System.out.printf("Test %s failed: expected %s, got %s%n", m, excType.getName(), exc);
						}
					}catch (Exception exc) {
						System.out.println("Invalid @Test: " + m);
					}
				}
			}
			System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
		}
	}

	private static String getConstraints(Constraints con) {
		String constraints = "";
		if(!con.allowNull())
			constraints += " NOT NULL";
		if(con.primaryKey())
			constraints += " PRIMARY KEY";
		if(con.unique())
			constraints += " UNIQUE";
		return constraints;
	}
}
