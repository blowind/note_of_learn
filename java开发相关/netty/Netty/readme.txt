针对粘包/拆包问题的四种解法：
1、消息定长，例如每个报文的大小为固定长度200字节，如果不够，空位补空格；
	具体操作：消息长度固定，累积读取到长度总和为定长LEN的报文后，认为读取到了一个完整的消息；将计数器置位，重新开始读取下一个数据报；

2、在包尾增加回车换行符进行分割，例如FTP协议；
	具体操作：将回车换行符作为消息结束符，这种方式在文本协议中应用比较广泛；

3、将消息分为消息头和消息体，消息头中包含表示消息总长度（或者消息体总长度）的字段，通常设计思路为消息头的第一个字段使用int32来表示消息的总长度；
	具体操作：通过在消息头中定义长度字段来标识消息的总长度

4、更复杂的应用协议；
	具体操作：将特殊的分隔符作为消息的结束标志，回车换行符就是一种特殊的结束分隔符；