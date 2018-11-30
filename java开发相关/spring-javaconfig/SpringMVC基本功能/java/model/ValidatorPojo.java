package com.zxf.springmvc.model;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @ClassName: ValidatorPojo
 * @Description:  使用已有的验证机制，通过各种验证相关的注解，用于验证HTTP请求中传入的各种参数
 *                  默认引入Hibernate validator机制支持的JSR-303验证规范进行验证
 * @Author: ZhangXuefeng
 * @Date: 2018/11/30 14:41
 * @Version: 1.0
 **/
@Data
public class ValidatorPojo {

    @NotNull(message = "id不能为空")
    private Long id;

    @Future(message = "需要一个将来日期")
    // @Past  只能是过去的日期
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 日期格式化转换
    @NotNull
    private Date date;

    @NotNull
    @DecimalMax(value = "10000.00")  // 限制最大值
    @DecimalMin(value = "0.1")   // 限制最小值
    private Double doubleVal;

    @Min(value = 1, message = "最小值为1")
    @Max(value = 88, message = "最大值为88")
    @NotNull
    private Integer integer;

    @Range(min = 1, max = 888, message = "范围为1到888")
    private Long range;

    @Email(message = "邮箱格式错误")
    private String email;

    @Size(min = 20, max = 30, message = "字符串长度要求20到30之间")
    private String size;
}
