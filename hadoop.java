% hadoop fs -ls file:///      //  �г������ļ�ϵͳ��Ŀ¼�µ��ļ�
% hadoop fs -lsr har:///my/files/har  // �Եݹ鷽ʽ�г��浵�ļ��еĲ����ļ�
% hadoop fs -rmr /my/files.har    //  �ݹ�ɾ��HAR�ļ�


//  distcp�ĵ���Ӧ�ó�����������HDFS��Ⱥ֮�䴫�����ݣ�Դ·�������Ǿ���·��
//  �ѵ�һ����Ⱥ/fooĿ¼�������ݸ��Ƶ��ڶ�����Ⱥ��/barĿ¼�£����ɵ�Ŀ¼�ṹ�� /bar/foo
% hadoop distcp hdfs://namenode1/foo  hdfs://namenode2/bar  

// ��������Ⱥ�临������
// �����������Ų�ͬHDFS�汾�ļ�Ⱥ��ʹ��distcp��������ʹ��hdfsЭ���ʧ�ܣ�����ʹ�û���httpЭ���hftp�ļ�ϵͳ����ҵ����������Ŀ�꼯Ⱥ�ϣ���ʵ��HDFS RPC�汾�ļ���
% hadoop distcp hftp://namenode1:50070/foo  hdfs://namenode2/bar
// ʹ��webhdfsЭ�飬�����ڲ����ݵ����
% hadoop distcp webhdfs://namenode1:50070/foo  webhdfs://namenode2:50070/bar

// ʹ��hadoop�浵����
//   ������������� �浵�ļ�����ԴĿ¼��Ŀ��Ŀ¼
% hadoop archive -archiveName files.har /my/files /my





ͨ��URLStreamHandlerʵ���Ա�׼������ʾHadoop�ļ�ϵͳ���ļ� // ���Ƽ�����Ϊÿ��Java�����ֻ�ܵ���һ���������
public class URLCat {
	static { URL.setURLStreamHandlerFactory( new FsUrlStreamHandlerFactory() ); }
	
	public static void main(String[] args) throws Exception {
		InputStream in = null;
		try {
			in = new URL(args[0]).openStream();    // "hdfs://host/path"
			// 1�������� 2������� 3����������С 4�����ƽ������Ƿ�ر�������
			IOUtils.copyBytes(in, System.out, 4096, false); 
		}finally{
			IOUtils.closeStream(in);
		}
	}
}
ʾ����
% hadoop URLCat hdfs://localhost/user/tom/quangle.txt


ֱ��ʹ��FileSystem�Ա�׼�����ʽ��ʾhadoop�ļ�ϵͳ�е��ļ����˴��������ӡ����
public class FileSystemCat {
	public static void main(String[] args) throws Exception {
		String uri = args[0];
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		FSDataInputStream in = null;     // �̳� java.io.DataInputStream �ӿڣ�ʵ���� Seekable, PositionedReadable
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
ʾ����
% hadoop FileSystemCat hdfs://localhost/user/tom/quangle.txt


�������ļ����Ƶ�Hadoop�ļ�ϵͳ
public class FileCopyWithProgress {
	public static void main(String[] args) throws Exception {
		String localSrc = args[0];
		String dst = args[1];
		InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
		
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		                  //���ط���Progressable�����ڴ��ݻص��ӿڣ�������д��datanode�Ľ���֪ͨ��Ӧ�ã��˴���Ҫ�Ǵ��
		OutputStream out = fs.create(new Path(dst), new Progressable() { 
														public void progress() {
															System.out.print("."); 
														}
													});
		IOUtils.copyBytes(in, System.out, 4096, false);
	}
}
ʾ����
% hadoop FileCopyWithProgress input/docs/1400-8.txt hdfs://localhost/user/tom/1400-8.txt







WebHDFS ����ͨ���� dfs.webhdfs.enable ѡ����Ϊtrue��������ã��ɶ�дHDFS