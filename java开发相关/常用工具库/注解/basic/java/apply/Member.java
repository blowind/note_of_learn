package com.zxf.apply;

import com.zxf.annotation.Constraints;
import com.zxf.annotation.DBTable;
import com.zxf.annotation.SQLInteger;
import com.zxf.annotation.SQLString;

/**
 *  @ClassName: Member
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2019/3/27 14:02
 *  @Version: 1.0
 **/
@DBTable(name = "NEW_MEMBER")
public class Member {
	// 注解中定义了value元素并且该元素时唯一需要赋值的元素时，可以无需使用名值对的写法，只写出值
	@SQLString(30)
	private String firstName;
	@SQLString(value = 50, constraints = @Constraints(allowNull = false))
	private String lastName;
	@SQLInteger
	private Integer age;
	//指定为主键，此处可以看出嵌套注解在使用时并没有很方便，一个变通是将@Constraints提取出来单独使用而不是作为组成上层注解的元素
	@SQLString(value = 30, constraints = @Constraints(primaryKey = true))
	private String handle;
}
