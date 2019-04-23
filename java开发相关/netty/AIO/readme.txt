AIO：即NIO 2.0，
JDK1.7升级了NIO类库，升级后的NIO类库被称为NIO 2.0，正式提供了异步文件I/O操作，同时提供了与UNIX网络编程事件驱动I/O对一个的AIO

引入异步通道的概念，并提供了异步文件通道和异步套接字通道的实现。
提供以下两种方式获取操作结果：
1、通过java.util.concurrent.Future类来表示异步操作的结果；
2、在执行异步操作的时候传入一个java.nio.channels

CompletionHandler接口的实现类作为操作完成的回调