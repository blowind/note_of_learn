package com.zxf.el;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.apache.commons.io.IOUtils;

@Configuration
@ComponentScan("com.zxf.el")
@PropertySource("classpath:/test.properties")  /*  注入配置文件 */
public class ElConfig {
    /* 注入普通字符串 */
    @Value("I love you!")
    private String normal;

    /* 注入操作系统属性 */
    @Value("#{systemProperties['os.name']}")
    private String osName;

    /* 注入表达式结果 */
    @Value("#{T(java.lang.Math).random() * 100.0}")
    private double randomNumber;

    /* 注入其他bean属性 */
    @Value("#{demoService.another}")
    private String fromAnother;

    /*  注入文件资源 */
    @Value("classpath:test.txt")
    private Resource testFile;

    /* 注入网址资源 */
    @Value("http://www.baidu.com")
    private Resource testUrl;

    /*  取出注入的配置文件里面的值 */
    @Value("${book.name}")
    private String bookName;



    @Autowired
    private Environment environment;

    @Bean   /*  从配置文件注入时，需要同步配置此config bean */
    public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
        return new PropertySourcesPlaceholderConfigurer();
    }



    public void outputResource() {
        try{
            System.out.println(normal);
            System.out.println(osName);
            System.out.println(randomNumber);
            System.out.println(fromAnother);
            System.out.println(IOUtils.toString(testFile.getInputStream()));
            System.out.println(IOUtils.toString(testUrl.getInputStream()));
            System.out.println(bookName);
            System.out.println(environment.getProperty("book.author"));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
