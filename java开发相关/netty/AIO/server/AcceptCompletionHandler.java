package com.zxf.nio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @ClassName: AcceptCompletionHandler
 * @Description:
 * @Author: ZhangXuefeng
 * @Date: 2019/4/23 0:01
 * @Version: 1.0
 **/
public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AIOServerRunner> {
    @Override
    public void completed(AsynchronousSocketChannel result, AIOServerRunner attachment) {
        attachment.asynchronousServerSocketChannel.accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, AIOServerRunner attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
