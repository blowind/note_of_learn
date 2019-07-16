package com.zxf.spring.config1;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

/**
 * @ClassName: CustomProperties
 * @Description: 私人定制的属性加载，也可以指定惯常的公共属性，如spring.data.mongodb之类
 *              properties文件的引入注解要放在SpringMain上，否则不生效
 * @Author: zhangxuefeng
 * @Date: 2019/7/12 下午3:00
 * @Version: 1.0
 **/
@ConfigurationProperties(prefix = "my.custom", ignoreUnknownFields = false)
@Data
public class CustomProperties {
    private String name;
    private Integer age;
    private Double score;
    private Short cardNo;
    private BigDecimal money;
}
