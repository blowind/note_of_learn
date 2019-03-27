package com.zxf.run;

import com.zxf.annotation.Constraints;
import com.zxf.annotation.DBTable;
import com.zxf.annotation.SQLInteger;
import com.zxf.annotation.SQLString;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
