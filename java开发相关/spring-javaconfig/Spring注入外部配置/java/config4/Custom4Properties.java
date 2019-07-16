package com.zxf.spring.config4;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MyConfig
 * @Description:  直接读取application.yml文件里的内容
 * @Author: zhangxuefeng
 * @Date: 2019/7/16 下午7:01
 * @Version: 1.0
 **/
@Component      //不加这个注解的话, 使用@Autowired 就不能注入进去了
@ConfigurationProperties(prefix = "my")  // 配置文件中的前缀
public class Custom4Properties {
    private List<String> servers = new ArrayList<String>();
    public List<String> getServers() { return this.servers;
    }
}
