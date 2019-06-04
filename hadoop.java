% hadoop fs -ls file:///      //  列出本地文件系统根目录下的文件
% hadoop fs -lsr har:///my/files/har  // 以递归方式列出存档文件中的部分文件
% hadoop fs -rmr /my/files.har    //  递归删除HAR文件


//  distcp的典型应用场景是在两个HDFS集群之间传输数据，源路径必须是绝对路径
//  把第一个集群/foo目录及其内容复制到第二个集群的/bar目录下，生成的目录结构是 /bar/foo
% hadoop distcp hdfs://namenode1/foo  hdfs://namenode2/bar  

// 在两个集群间复制数据
// 在两个运行着不同HDFS版本的集群上使用distcp复制数据使用hdfs协议会失败，可以使用基于http协议的hftp文件系统，作业必须运行在目标集群上，以实现HDFS RPC版本的兼容
% hadoop distcp hftp://namenode1:50070/foo  hdfs://namenode2/bar
// 使用webhdfs协议，不存在不兼容的情况
% hadoop distcp webhdfs://namenode1:50070/foo  webhdfs://namenode2:50070/bar

// 使用hadoop存档工具
//   最后三个参数： 存档文件名，源目录，目标目录
% hadoop archive -archiveName files.har /my/files /my





通过URLStreamHandler实例以标准方法显示Hadoop文件系统的文件 // 不推荐，因为每个Java虚拟机只能调用一次这个方法
public class URLCat {
	static { URL.setURLStreamHandlerFactory( new FsUrlStreamHandlerFactory() ); }
	
	public static void main(String[] args) throws Exception {
		InputStream in = null;
		try {
			in = new URL(args[0]).openStream();    // "hdfs://host/path"
			// 1、输入流 2、输出流 3、缓冲区大小 4、复制结束后是否关闭数据流
			IOUtils.copyBytes(in, System.out, 4096, false); 
		}finally{
			IOUtils.closeStream(in);
		}
	}
}
示例：
% hadoop URLCat hdfs://localhost/user/tom/quangle.txt


直接使用FileSystem以标准输出格式显示hadoop文件系统中的文件，此处将输入打印两遍
public class FileSystemCat {
	public static void main(String[] args) throws Exception {
		String uri = args[0];
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		FSDataInputStream in = null;     // 继承 java.io.DataInputStream 接口，实现了 Seekable, PositionedReadable
		try {
			in = fs.open(new Path(uri));
			IOUtils.copyBytes(in, System.out, 4096, false);
			in.seek(0);
			IOUtils.copyBytes(in, System.out, 4096, false);
		}finally{
			IOUtils.closeStream(in);
		}
	}
}
示例：
% hadoop FileSystemCat hdfs://localhost/user/tom/quangle.txt


将本地文件复制到Hadoop文件系统
public class FileCopyWithProgress {
	public static void main(String[] args) throws Exception {
		String localSrc = args[0];
		String dst = args[1];
		InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
		
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		                  //重载方法Progressable，用于传递回调接口，把数据写入datanode的进度通知给应用，此处主要是打点
		OutputStream out = fs.create(new Path(dst), new Progressable() { 
														public void progress() {
															System.out.print("."); 
														}
													});
		IOUtils.copyBytes(in, System.out, 4096, false);
	}
}
示例：
% hadoop FileCopyWithProgress input/docs/1400-8.txt hdfs://localhost/user/tom/1400-8.txt







WebHDFS 必须通过将 dfs.webhdfs.enable 选项置为true后才能启用，可读写HDFS