package com.zxf.spring.demo;

import com.zxf.spring.config1.CustomProperties;
import com.zxf.spring.config2.Custom2Properties;
import com.zxf.spring.config3.Custom3Properties;
import com.zxf.spring.config4.Custom4Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @ClassName: Demo
 * @Description:
 *
 * el最常用的几种使用场景：
 *    1、从配置文件中读取属性
 *    2、缺失值情况下，配置默认值
 *    3、el内部字符串使用String的方法
 *    4、三目运算符
 *    5、正则表达式
 *    6、注入系统属性（system properties）
 *    7、调用系统原有函数
 *    8、直接注入文件进行操作
 *    9、读取另一个bean的函数的返回值
 *
 *        记住下边三句话
 *       ${}不支持表达式（三目表达式不算表达式）；#{}支持
 *       ${}读取属性文件的值
 *       ${}读取最后一个满足条件的值；#{}读取所有满足条件的值
 *
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

    @Autowired
    public Custom3Properties custom3Properties;

    @Autowired
    private Custom4Properties custom4Properties;

    /* 注入配置文件的值 */
    @Value("${hello.see.one}")
    private String one;

    @Value("${hello.see.two}")
    private Integer two;


    /* 缺失值情况下，配置默认值 */
    @Value("${nameDefault: '刚子'}")
    private String defaultName;

    /* 注入普通字符串 */
    @Value("I love you!")
    private String normal;

    /*el内部字符串使用String的方法*/
    @Value("#{'${name.list}'.split(',')}")
    private List<String> namelist;

    /*三目运算符*/
    @Value("${name.three!='杨过'?'黄蓉':'小龙女'}")
    private String nameThree;

    /* 注入操作系统属性 */
    @Value("#{systemProperties['os.name']}")
    private String osName;

    /* 注入表达式结果 */
    @Value("#{T(java.lang.Math).random() * 100.0}")
    private double randomNumber;

    /*正则表达式*/
    @Value("#{'100' matches '\\d+'}")//这里必须使用#，使用$是不行的
    private boolean isDigital;

    /* 注入其他bean属性 */
    @Value("#{custom2Properties.hello}")
    private String fromAnother;

    /*  注入文件资源 */
    @Value("classpath:test.txt")
    private Resource testFile;

    /* 注入网址资源 */
    @Value("http://www.baidu.com")
    private Resource testUrl;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("使用实现方式一加载的配置类 : {}", customProperties);
        log.info("使用实现方式二加载的配置类 : {}", custom2Properties);
        log.info("使用实现方式三加载yml配置 : {}", custom3Properties);
        log.info("使用实现方式四加载默认命名的application.yml : {}", custom4Properties.getServers().toString());


        log.info("直接配置属性 : one is {}, two is {}", one, two);
        log.info("{}", defaultName);
        log.info(namelist.toString());
        log.info(nameThree);
        log.info(String.valueOf(isDigital));
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
