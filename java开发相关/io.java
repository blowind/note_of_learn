总体的IO分类：
着眼于传输数据的数据格式的类：
1、基于字节操作的I/O接口：InputStream和OutputStream
2、基于字符操作的I/O接口：Writer和Reader
着眼于传输数据的方式的类：
3、基于磁盘操作的I/O接口：File
4、基于网络操作的I/O接口：Socket



UNIX 5种基本IO模型：

底层数据流：  内核缓冲区无数据 -> 内核缓冲区数据ready -> 复制数据包到用户态缓冲区 ->  复制完成

1、阻塞I/O模型：
    从用户态发起recvfrom调用到底层把数据复制到用户态缓冲区期间，发起调用的线程一直阻塞

2、非阻塞I/O模型：
    用户态发起recvfrom调用后直接返回一个EWOULDBLOCK错误，然后用户线程自己不断发起轮询并接收到EWOULDBLOCK错误直到内核缓冲区ready，用户进程阻塞直到数据复制到用户态缓冲区

3、I/O复用模型：（NIO基于epoll多路复用技术实现）
    linux的select/poll模型，将多个fd传递给select/poll系统调用，select/poll顺序扫描fd检查是否ready，更高级的使用epoll系统调用，基于事件驱动方式代替顺序扫描，fd就绪时执行回掉函数rollback
	用户态线程执行select后会阻塞，等待任意fd内核态数据缓冲区ready，ready后用户态线程再执行recvfrom并阻塞，等待数据复制到用户态缓冲区后返回成功继续执行

4、信号驱动I/O模型：
	用户态进程通过sigaction系统调用执行一个信号处理函数（非阻塞，立即返回），内核态数据ready时为该进程生成SIGIO信号，通过信号回调通知进程调用recvfrom读取数据（recvfrom调用期间阻塞等待数据拷贝到用户态缓冲区）
	
5、异步I/O：
	进程告知内核启动某个操作，并在内核在整个操作完成后（包括将数据从内核缓冲区复制到用户态缓冲区）通知进程
	
	
I/O多路复用：通过把多个I/O的阻塞复用到同一个select的阻塞上，从而使得系统在单线程的情况下可以同时处理多个客户端请求。最大的优势是系统开销小
	
	
/****                                               原始BIO                                        ****/

带资源的try构造(try with resources) 在try块的参数表中声明流变量，Java 1.7的特性
// java 1.6之前的写法：
OutputStream out = null
try{
	out = new FileOutputStream("1.txt");
	doSomething();
}catch(IOException e) {
	System.err.println(ex.getMessage());
}finally{
	if(out != null) {      //  避免初始化失败等情况下的 NullPointerException
		try{
			out.close();
		}catch(IOException ex) {}   // 一般忽略关闭流时的异常，最多将这个异常记入日志
	}
}

// java 1.7的写法
try(OutputStream out = new FileOutputStream("1.txt")) {   //  Java会对try块参数表中声明的所有AutoCloseable对象自动调用close()
	doSomething();
}catch(IOException e) {
	System.err.println(e.getMessage());
}

// 从网络中读数据，一般循环读，因为网络不稳定，可能一次不能读取全部数据
int bytesRead = 0;
int bytesToRead = 1024;
byte[] input = new byte[bytesToRead];
while(bytesRead < bytesToRead) {
	int ret = in.read(input, bytesRead, bytesToRead - bytesRead);
	if(ret == -1) break;
	bytesRead += ret;
}

public void mark(int readAheadLimit)     /* 标记流的当前位置，以便在以后某个时刻，可以用reset()方法把流重置到之前标记的位置
 接下来的读取会返回从标记位置开始的数据。从标记处读取和重置的字节数由readAheadLimit确定，重置太远会抛出IOException异常*/
public void reset() throws IOException
public boolean markSupported() 
	
	
DataInputStream和DataOutputStream提供了一些方法，可以用二进制格式读/写JAVA的基本数据类型和字符串，接口如下，都是大端模式
public final void writeBoolean(boolean b) throws IOException
public final void writeByte(int b) throws IOException
public final void writeShort(int s) throws IOException
public final void writeChar(int c) throws IOException
public final void writeInt(int i) throws IOException
public final void writeLong(long l) throws IOException
public final void writeFloat(float f) throws IOException
public final void writeChars(String s) throws IOException   // 对参数迭代处理，将各个字符按顺序写为2字节的大端Unicode字符
public final void writeBytes(String s) throws IOException // 迭代处理，只写入每个字符的低字节，可能丢信息，不建议使用
public final void writeUTF(String s) throws IOException  // 包含字符串长度信息，前两个接口没有

java.io.Reader超类指定读取字符的API，使用Unicode字符
java.io.Writer超类指定写字符的API，使用Unicode字符

OutputStreamWriter是Write最重要的具体子类，从Java程序接收字符。根据指定的编码方式将这些字符转换成字节，并写入底层输出流
public OutputStreamWriter(OutputStream out, String encoding) throws UnsupportedEncodingException
public String getEncoding()

InputStreamReader是Reader最重要的具体子类。从底层输入流读取字节，根据指定的编码方式转为字符，并返回这些字符
public InputStreamReader(InputStream in, String encoding) throws UnsupportedEncodingException

//  多线程处理压缩文件示例
public class GZipRunnable implements Runnable {
	private final File input;
	public GZipRunnable(File file) {      //  使用File类作为构造函数入参
		this.input = file;
	}
	public void run() {
		if(!input.getName().endsWith(".gz")) {   // 判断是否.gz结尾
			File output = new File(input.getParent(), input.getName() + ".gz");         // 生成绝对路径名
			if(!output.exists()) {           //  压缩文件不存在时才生成
				try(InputStream in = new BufferedInputStream(new FileInputStream(input));   // java 1.7 的 try resources结构
					OutputStream out = new BufferedOutputStream(       //  因为后面操作是一字节一字节写入，因此最好加buffer
										new GZIPOutputStream(
							             new FileOutputStream(output)));
					){
						int b;
						while((b = in.read()) != -1) out.write(b);
						out.flush();
				}catch(IOException e) {
					e.printStackTrace();
				}		
			}
		}
	}
}


/****                                               NIO                                        ****/

NIO中主要Channel的实现：
FileChannel : 从文件中读写数据
DatagramChannel : 通过UDP读写网络中的数据
SocketChannel : 通过TCP读写网络中的数据
ServerSocketChannel : 通过监听新进来的TCP连接，像Web服务器那样，对每个新进来的连接都会创建一个SocketChannel

缓冲区本质上是一块可以写入数据，然后从中读取数据的内存。这块内存被包装成NIO对象，并提供了一组方法，用来方便的访问该块内存。
NIO中主要的Buffer实现：
ByteBuffer        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
CharBuffer        CharBuffer charBuffer = CharBuffer.allocate(1024);  
DoubleBuffer
FloatBuffer
IntBuffer
LongBuffer
ShortBuffer
MappedByteBuffer : 用于表示内存映射文件


// 初始化一个buffer并分配空间
ByteBuffer.allocate(48);
// 通过通道写入Buffer
inChannel.read(ByteBuffer);	
// 将数据从buffer写入通道
int writeSize = inChannel.write(buf);
// 调用put函数写入数据到buffer
buf.put(127);
// 使用get读取数据到本地变量
byte tmp = buf.get();
// 切换当前buffer的读/写模式，写切换到读时，将同时将limit设置成当前position值，position设置为0，
buf.flip();
// 将position设回0，可以重读buffer中的所有数据，limit保持不变
buf.rewind();
// 清空buffer，即将position设回0，同时limit设置成capacity(初始化时分配的大小)
buf.clear();
// 清除已经读过的数据，任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面，limit设置成capacity
buf.compact();
// capacity标记一个特定的position，使用reset()可以恢复到这个position
buf.mark();
buf.reset();
	

/* 示例 */	
public class UseNio {
	public static void useNio() {
		RandomAccessFile aFile = null;
		try {
			aFile = new RandomAccessFile("./1.txt", "rw");    // 定义文件对象
			FileChannel inChannel = aFile.getChannel();       // 从文件对象上获取通道  
			ByteBuffer byteBuffer = ByteBuffer.allocate(48);  // 创建容量为48字节的buffer
			int result = inChannel.read(byteBuffer);      // 读取48字节的数据，放入buffer
			
			while(result != -1) {
				System.out.println("Read: " + result);
				byteBuffer.flip();                            //  设置buffer切换模式为读模式
				
				while(byteBuffer.hasRemaining()) {
					System.out.println((char)byteBuffer.get());  // 每次读取1字节
				}
				
				/* 清空buffer，准备再次写入，clear()会清空整个缓冲区，compact()只会清空已经读取的部分，并将未读部分放到最开头
				*/
				byteBuffer.clear();  
				result = inChannel.read(byteBuffer);
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			try{
				aFile.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}

// NIO数据读取的一个完整示例
public void selector() throws IOException {
		/** Buffer使用几个变量（索引）保存数据的位置状态，主要有下面几个
		 *  capacity   缓冲区数据的总长度，此处是1024
		 *  position   下一个要操作的数据元素的位置，初始时是0
		 *  limit      缓冲区数据中不可操作的下一个元素的位置，limit<=capacity，初始时等于capacity
		 *  mark       记录当前position的前一个位置或者默认是0，用于reset()函数调用进行恢复
		 */
		ByteBuffer buffer = ByteBuffer.allocate(1024); // 创建一个1024byte的数组缓冲区
		Selector selector = Selector.open(); // 创建选择器
		ServerSocketChannel ssc = ServerSocketChannel.open(); // 创建服务端Channel
		ssc.configureBlocking(false);   // Channel设置为非阻塞模式
		ssc.socket().bind(new InetSocketAddress(8080)); // 生成socket对象绑定8080端口
		ssc.register(selector, SelectionKey.OP_ACCEPT); // 注册本Channel到selector的OP_ACCEPT事件上
		while (true) {
			// 每次通过selectedKeys()方法检查注册在选择器selector上的所有通信信道是否有需要的时间发生
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> it = selectedKeys.iterator();
			while(it.hasNext()) {
				SelectionKey key = it.next();
				// 如果OP_ACCEPT置位表明服务器通道已经ready，可以接受连接
				if((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
					// 通过有事件发生的key的channel()方法获取信道对象
					ServerSocketChannel ssChannel = (ServerSocketChannel)key.channel();
					// 设置通道为可以接受连接状态
					SocketChannel sc = ssChannel.accept();
					sc.configureBlocking(false);
					// 注册本通道到选择器selector的OP_READ事件上
					sc.register(selector, SelectionKey.OP_READ);
					it.remove();
				}
				// 如果OP_READ置位表明通道数据已经ready，可以读取
				else if((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
					// 通过有事件发生的key的channel()方法获取信道对象
					SocketChannel sc = (SocketChannel)key.channel();
					while (true) {
						// 恢复ByteBuffer缓冲区为默认状态，即limit和mark恢复为0，limit恢复到capacity
						buffer.clear();
						int n = sc.read(buffer); // 读取数据
						if(n <= 0) {
							break;
						}
						// 将limit置于当前position所在位置，position恢复到起始位置，通知操作系统从缓冲区读取limit前的字节发送出去
						buffer.flip();
					}
					it.remove();
				}
			}

		}
	}

	
【文件的编解码操作】
InputStreamReader:  读数据并完成字节到字符的编码转换（组合一个StreamDecoder到内部，此处decoder意指字节的解码）
OutputStreamWriter: 写数据并完成字符到字节的解码转换（组合一个StreamEncoder到内部，此处encoder意指字符的编码）
public static void writeFile() throws IOException{
	String path = "e:\\stream.txt";
	String charset  = "UTF-8";
	FileOutputStream fos = new FileOutputStream(path);
	OutputStreamWriter writer = new OutputStreamWriter(fos, charset);
	try{
		writer.write("这是要保存的中文字符");
	}finally {
		writer.close();
	}
}
public static void readFile() throws IOException {
	String path = "e:\\stream.txt";
	String charset  = "UTF-8";
	FileInputStream fis = new FileInputStream(path);
	InputStreamReader reader = new InputStreamReader(fis, charset);
	StringBuffer sb = new StringBuffer();
	char[] buf = new char[64];
	int count = 0;
	try{
		while ((count = reader.read(buf)) != -1) {
			sb.append(buf, 0, count);
		}
		System.out.println(sb.toString());
	}finally {
		reader.close();
	}
}

【内存编解码操作】
// 在Java中一个char类型占用两个字节16bit，byte占用一个字节8bit


// 字符串和byte[]数组的互相转换
String s = "这是一段中文字符串";
byte[] b = s.getBytes("UTF-8");
String n = new String(b, "UTF-8");
System.out.println(n);

// 字符串转成ByteBuffer和CharBuffer对象
Charset charset = Charset.forName("UTF-8");
ByteBuffer byteBuffer = charset.encode(s);
CharBuffer charBuffer = charset.decode(byteBuffer);

// 字符编解码相关知识点示例汇总
public static void printHex(byte[] data) {
	String s = Hex.encodeHexString(data);
	Pattern pattern = Pattern.compile("[a-zA-Z0-9]{2}");
	Matcher matcher = pattern.matcher(s);
	while(matcher.find()) {
		System.out.print(matcher.group() + " ");
	}
	System.out.println();

}
public static void encode() {
	String name = "I am 君山";
	try{
		/**
		 * 将7个char字符转为7个byte数组，ISO-8859-1是单字节编码，
		 * 很多识别不了的字符就转为0x3f的?，因此经常出现中文乱码，
		 * 中文转为ISO-8859-1编码会丢失信息（1字节表示的内容有限），因此应该避免使用该编码
		 */
		byte[] iso8859 = name.getBytes("ISO-8859-1");
		printHex(iso8859);
		/**
		 * 英文依然是ASCII码，中文是双字节，通过查码表进行编码，支持6763个汉字
		 */
		byte[] gb2312 = name.getBytes("GB2312");
		printHex(gb2312);
		/**
		 * 兼容GB2312，编码算法一样，差异是包含更多汉字
		 */
		byte[] gbk = name.getBytes("GBK");
		printHex(gbk);
		/**
		 * 强制规定每个字符占用两个字节，不足两个字节的ASCII字符补00，UTF-16只规定编码方式，没有指定存储端序
		 * 由于UTF-16对应的UCS-2标准没有规定端序，因此UTF-16通过带BOM(Byte Order Mark)进一步确定端序
		 * 大端序UTF-16BE会在最开头带一个0xFEFF的BOM，小端序UTF-16LE会在最开头带一个0xFFFE的BOM
		 * windows下默认为小端序的UTF-16LE，Java由于其自带的网络特性使用大端序UTF-16BE
		 * 不带BOM的UTF-16一般按照大端序的方式存储
		 */
		byte[] utf16 = name.getBytes("UTF-16");
		printHex(utf16);
		/**
		 * 针对UTF-16对于纯英文的ASCII字符浪费空间问题，推出了UTF-8
		 * UTF-8动态使用1~4字节表示字符，1字节字符范围同ASCII码，中文一般是三字节表示
		 * 由于UTF-8以字节为编码单元，没有端序的问题
		 * 除了节省空间，UTF-8由于是通过算法得到编码，比查码表进行转换的GBK和GB2312的编码效率高
		 * UTF-8 WITH BOM 带三字节的 0xEF 0xBB 0xBF BOM编码，
		 * UTF-8文件中带BOM是微软为区分ASCII码引入的设计，不含BOM的UTF-8才是标准形式
		 * 带BOM的文件在windows之外的OS里可能带来问题，另外UTF-8的网页html代码不应使用BOM，因此不要用windows自带记事本编辑网页
		 */
		byte[] utf8 = name.getBytes("UTF-8");
		printHex(utf8);
	}catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	}
}
			
			

scatter/gather 描述从Channel中读取或者写入到Channel的操作。
scatter 在读操作时将读取的数据依次写入多个buffer中，例如将一个消息的消息头和消息体写入不同的buffer
/* 由于每次移动到下一个buffer前，必须填满当前buffer，因此不适用于动态消息(即消息格式大小不固定的包) */
public void scatter() {
	ByteBuffer header = ByteBuffer.allocate(128);
	ByteBuffer body = ByteBuffer.allocate(1024);
	ByteBuffer[] bufferArray = { header, body };
	/* 当一个buffer被写满后，channel紧接着向另一个buffer中写入 */
	channel.read(bufferArray);
}

gatter  在写操作时将多个buffer的数据写入同一个Channel
/* 写入position和limit之间的数据，因此能较好的处理动态消息，即需要编码者自己在各个buffer中放入合适的数据，然后依次写入通道 */
public void gather() {
	ByteBuffer header = ByteBuffer.allocate(128);
	ByteBuffer body = ByteBuffer.allocate(1024);
	// 写入数据到buffer
	ByteBuffer[] bufferArray = { header, body };
	channel.write(bufferArray);
}

通道间数据传输（其中一个必须是文件通道 FileChannel）： 源通道可以是 SocketChannel
public void filePipeline() {
	RandomAccessFile fromFile = new RandomAccessFile("1.txt", "rw");
	FileChannel fromChannel = fromFile.getChannel();
	RandomAccessFile toFile = new RandomAccessFile("2.txt", "rw");
	FileChannel toChannel = toFile.getChannel();
	long position = 0;   //  被写入内容的起始地址
	long count = fromChannel.size();  // 最大传输的字节数
	/* 将数据从源通道传输到FileChannel中，注意此处源是fromChannel，目的是toChannel且必须是文件通道 */
	toChannel.transferFrom(position, count, fromChannel);
	/* 将数据从当前的文件通道传输到目的通道，因此 fromChannel 必须是文件通道 */
	fromChannel.transferTo(position, count, toChannel);
}

Selector 辅助单个线程管理多个通道，从而管理多个网络连接。 
鉴于Channel配合Selector时必须处于非阻塞模式，因此不能与FileChannel一起用
public void runSelector() {
	// 创建 selector
	Selector selector = Selector.open();
	/* 由于多个channel对一个selector，因此channel必须处于非阻塞模式，此处进行设置 */
	channel.configureBlocking(false);
	// 绑定channel，即将channel注册到selector上，第二个参数表示兴趣集合，表示selector对channel的什么感兴趣，
	/* 主要有 Connect/Accept/Read/Write  4种类型的事件，分别表示通道的“连接就绪”，“接收就绪”，“读就绪”，“写就绪” */
	/* Selectionkey.OP_CONNECT, Selectionkey.OP_ACCEPT, Selectionkey.OP_READ, Selectionkey.OP_WRITE  可以用位操作符合并 */
	SelectionKey key = channel.register(selector, Selectionkey.OP_READ);
	
	key.attach(myObject); // 根据业务需要绑定相关对象到key，可选
	
	while(true) {
		/* 返回非0值，表明有对应数量的channel已经就绪 */
		int readyChannels = selector.select();
		if(readyChannels == 0) continue;
		/* 获取所有通道的SelectionKey，用来访问各个通道 */
		Set<SelectionKey> selectedKeys = selector.selectedKeys();
		/* 获取通道的迭代器 */
		Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
		while(keyIterator.hasNext()) {
			selectedKey key = keyIterator.next();
			if(key.isAcceptable()) { 
				SocketChannel channel = (SocketChannel)key.channel();  /* 获取对应的channel，类型需要自己强转 */
				Selector sele = key.selector();  // 反获取selector
			}else if(key.isConnectable()) {
				Object attachedObj = selectionKey.attachment();  // 获取绑定到
			}else if(key.isReadable()) {
				doSomethingC();
			}else if(key.isWritable()) {
				doSomethingD();
			}
			/* selector不糊自己从已选键集中移除SelectionKey实例，需要自己动手移除，下次通道就绪时，会再次将其放入已选择键集 */
			keyIterator.remove();
		}
	}
	// 用完后关闭，使得注册其上的所有SelectionKey无效。相关通道本身不关闭
	selector.close();
}

文件通道 FileChannel 的常用操作
public void fileChannelUsage() {
	RandomAccessFile aFile = new RandomAccessFile("1.txt", "rw");
	FileChannel inChannel = aFile.getChannel();
	ByteBuffer buf = ByteBuffer.allocate(48);
	/* 从channel里面读数据到buf，result表示成功读取的字节数，返回-1表示读到了文件末尾  */
	int result = inChannel.read(buf);
	
	String newData = "a test string";
	ByteBuffer buf = ByteBuffer.allocate(96);
	/* 清空buf缓存 */
	buf.clear();
	/* 将字符串转换成字节放入buffer */
	buf.put(newData.getBytes());
	/* 切换当前读写模式，当前从buf写切换到buf读 */
	buf.flip();
	/* 不确定一次能向channel里面写入多少个字节，因此需要while循环写入 */
	while(buf.hasRemaining()) {
		channel.write(buf);
	}
	/* 获取通道的当前位置 */
	long pos = channel.position();
	/* 设置通道的当前位置 */
	channel.position(pos + 123);
	/* 返回当前通道关联的文件大小 */
	long fileSize = channel.size();
	/* 截取一个文件，指定长度后面的部分将被删除 */
	channel.truncate(1024);
	/* 将通道里尚未写入磁盘的数据强制写到磁盘上 */
	channel.force():
}

套接字通道 SocketChannel 用法
public void socketChannelUsage() {
	/* 打开SocketChannel  */
	SocketChannel sc = SocketChannel.open();
	sc.connect(new InetSocketAddress("http://jenkov.com", 80));
	/* 关闭SocketChannel */
	sc.close();
	/* 读取数据，返回-1表示读到了流的末尾，连接关闭了  */
	ByteBuffer buf = ByteBuffer.allocate(48);
	int result = sc.read(buf);
	/* SocketChannel写模式与文件通道FileChannel一模一样，不再列举 */
	
	
	/* 非阻塞模式，用来在异步模式下调用 connect() read() write() */
	sc.configureBlocking(false);
	sc.connect(new InetSocketAddress("http://jenkov.com", 80));
	/* 非阻塞模式下，可能在连接建立之前就返回了，需要确认连接是否建立 */
	while(!sc.finishConnect()) {
		doSomething();
	}
}

ServerSocketChannel 用法
public void serverSocketChannelUsage() {
	ServerSocketChannel ssc = ServerSocketChannel.open();
	ssc.socket().bind(new InetSocketAddress(9999));
	
	while(true) {
		/* 监听新来的连接，当accept()方法返回的时候，返回一个包含新进来连接的SocketChannel */
		/* 此处会一直阻塞到有新连接到达，通常不会仅仅只坚挺一个连接 */
		SocketChannel sc = ssc.accept();
		doSomething();
	}
	/* 非阻塞模式 */
	ssc.configureBlocking(false);
	while(true) {
		SocketChannel sc = ssc.accept();
		if(sc != null) { doSomething(); }
	}
	ssc.close();
}

DatagramChannel 用法
public void datagramChannelUsage()  {
	DatagramChannel dc = DatagramChannel.open();
	dc.socket().bind(new InetSocketAddress(9999));
	ByteBuffer buf = ByteBuffer.allocate(48);
	buf.clear();
	/* 将接收到的数据包复制到buf，如果buf容不下收到的数据，多出的数据将被丢弃 */
	dc.receive(buf);
	
	String newData = "a test string";
	ByteBuffer buf = ByteBuffer.allocate(96);
	buf.clear();
	buf.put(newData.getBytes());
	buf.flip();
	/* 将一串字符发送到jenkov.com服务器的UDP端口80，无法确认数据包是否已经收到，因为UDP不保证数据到达 */
	int result = dc.send(buf, new InetSocketAddress("jenkov.com", 80));
	/* 由于DCP是无连接的，连接到特定地址并不会像TCP通道那样创建一个连接，而是锁住DatagramChannel，让其只能从特定地址收发数据 */
	dc.connect(new InetSocketAddress("jenkov.com", 80));
	/* 绑定到特定地址后，就可以像传统通道一样操作，只是不保证数据确认到达 */
	int ret = dc.read(buf);
	int ret = dc.write(buf);
}

Pipe用法
public void pipeUsage() {
	Pipe p = Pipe.open();
	/* 向管道写数据，需要访问sink通道 */
	Pipe.SinkChannel sc = p.sink();
	/* 向sinkChannel写入数据，buf中数据来源与前面一样，不重复 */
	while(buf.hasRemaining()) {
		sc.write(buf);
	}
	/* 从管道读数据，需要访问source通道 */
	Pipe.SourceChannel sc = p.source();
	int ret = sc.read(buf);
}

Path类用法
import java.nio.file.Path;
import java.nio.file.Paths;
public void pathUsage() {
	/* 绝对路径 */
	Path path = Paths.get("c:\\data\\myfile.txt");
	/* 相对路径，其绝对路径是 c:\data\projects ，其实就是玩了一个多字符串拼接功能，linux下的. 和 .. 也是能识别的	*/
	Path p1 = Paths.get("c:\\data", "projects");
	/* 清理路径，主要是.和..带来的各种不必要的来回往复 */
	Path p2 = p1.normalize();
}

