package com.zxf.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DemoListener implements ApplicationListener<DemoEvent> {
    /*  使用接口的onApplicationEvent方法对消息进行处理 */
    public void onApplicationEvent(DemoEvent event) {
        String msg = event.getMsg();
        System.out.println("bean-demoListener接收到了bean-demoPulisher发布的消息: " + msg);
    }
}
