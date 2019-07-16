package com.zxf.spring.config3;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 **/
@Component
@ConfigurationProperties(prefix = "myyml")
@Data
public class Custom3Properties {
    private String simpleProp;
    private Integer number;
    private List<Integer> arrayProps;
    private List<Map<String, String>> listMapProp;
    private List<String> listProp;
    private Map<String, String> mapProp;

    /* yml文件里 --- 表示分隔符，表示多个不归属的yml配置，
       由于此处点名了前缀不能用属性名直接注入，需要通过@Value注入识别
       或者将内容提取到一个单独的yml文件里，再用此处的套路重新加载*/
    @Value("${mine.username}")
    private String username;
}
