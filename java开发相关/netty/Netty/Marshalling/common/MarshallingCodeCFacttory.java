package com.zxf.nio.marshalling;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * @ClassName: MarshallingCodeCFacttory
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/4/28 23:51
 * @Version: 1.0
 **/
public class MarshallingCodeCFacttory {

    public static MarshallingDecoder buildMarshallingDecoder() {
        /* serial表明创建的是Java序列化工厂对象 */
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        UnmarshallerProvider provider =new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        /* 1024表示单个消息序列化后的最大长度 */
        MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
        return decoder;
    }

    public static MarshallingEncoder buildMarshallingEncoder() {
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return encoder;
    }
}
