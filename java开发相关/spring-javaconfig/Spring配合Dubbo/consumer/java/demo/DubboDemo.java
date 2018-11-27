package com.zxf.consumer.demo;

import com.zxf.consumer.service.HelloServiceCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName: DubboDemo
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2018/11/26 19:22
 * @Version: 1.0
 **/
@Component
public class DubboDemo implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private HelloServiceCaller caller;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
        try{
            for(int i=0; i<1000; i++) {
                String output = caller.doSayHello("timber");
                System.out.println(output);
                Thread.sleep(1000);
            }
        }catch (InterruptedException ep) {
            ep.printStackTrace();
        }
    }

}
