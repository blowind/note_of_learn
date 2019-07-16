package com.zxf.spring.config3;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

/**
 * @ClassName: Custom3Config
 * @Description:
 * @Author: zhangxuefeng
 * @Date: 2019/7/15 下午4:06
 * @Version: 1.0
 **/
@Configuration
public class Custom3Config {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("config/prop.yml"));  // classpath引入
        // yaml.setResources(new FileSystemResource("prop.yml"));  // file引入
        configurer.setProperties(yaml.getObject());
        return configurer;
    }
}
