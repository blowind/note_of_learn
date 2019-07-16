package com.zxf.spring.demo;

import com.zxf.spring.config1.CustomProperties;
import com.zxf.spring.config2.Custom2Properties;
import com.zxf.spring.config3.Custom3Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @ClassName: Demo
 * @Description:
 * @Author: zhangxuefeng
 * @Date: 2019/7/12 下午4:34
 * @Version: 1.0
 **/
@Component
@Slf4j
public class Demo implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CustomProperties customProperties;

    @Autowired
    private Custom2Properties custom2Properties;

    /* 注入配置文件的值 */
    @Value("${hello.see.one}")
    private String one;

    @Value("${hello.see.two}")
    private Integer two;



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
    @Value("#{custom2Properties.hello}")
    private String fromAnother;

    /*  注入文件资源 */
    @Value("classpath:test.txt")
    private Resource testFile;

    /* 注入网址资源 */
    @Value("http://www.baidu.com")
    private Resource testUrl;


    @Autowired
    public Custom3Properties custom3Properties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("使用实现方式一加载的配置类 : {}", customProperties);
        log.info("使用实现方式二加载的配置类 : {}", custom2Properties);
        log.info("使用实现方式三加载yml配置 : {}", custom3Properties);


        log.info("直接配置属性 : one is {}, two is {}", one, two);
        log.info(normal);
        log.info(osName);
        log.info("{}", randomNumber);
        log.info(fromAnother);

        try{
            log.info(IOUtils.toString(testFile.getInputStream(), Charset.forName("UTF-8")));
            log.info(IOUtils.toString(testUrl.getInputStream(), Charset.forName("UTF-8")));
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
