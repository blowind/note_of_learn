package com.zxf.springmvc.util;

import java.io.*;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *  @ClassName: Base64Util
 *  @Description: 将二进制内容转成BASE64内容用于网络传输的工具类
 *  @Author: ZhangXuefeng
 *  @Date: 2019/3/16 10:45
 *  @Version: 1.0
 **/
public final class Base64Util {

	@Deprecated
	public static byte[] loadFileBeforeJDK7(File file) {
		FileInputStream fis = null;
		ByteArrayOutputStream baos = null;
		byte[] ret = null;

		try{
			fis = new FileInputStream(file);
			baos = new ByteArrayOutputStream((int)file.length());
			byte[] buffer = new byte[1024];
			int len;
			while((len = fis.read(buffer)) != -1) {
				baos.write(buffer, 0 , len);
			}
			ret = baos.toByteArray();
		}catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}
		finally {
			try{
				if(fis != null) {
					fis.close();
					fis = null;
				}
				baos.close();
			}catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return ret;
	}

	@Deprecated
	public static byte[] gzipCompressBeforeJDK7(byte[] data) {
		ByteArrayOutputStream baos = null;
		GZIPOutputStream gzip = null;
		byte[] ret = null;
		try{
			baos = new ByteArrayOutputStream();
			gzip = new GZIPOutputStream(baos);
			gzip.write(data);
			gzip.finish();
			gzip.flush();
			ret = baos.toByteArray();
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}finally {
			try{
				if(baos != null) {
					baos.close();
				}
				if(gzip != null) {
					gzip.close();
				}
			}catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return ret;
	}

	public static byte[] loadFile(String path) {
		File file = new File(path);
		return loadFile(file);
	}

	/**
	 * 使用gzip压缩二进制内容byte[]
	 */
	public static byte[] gzipCompress(byte[] data) {
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
				GZIPOutputStream gzip = new GZIPOutputStream(baos)) {
			gzip.write(data);
			gzip.finish();
			gzip.flush();
			return baos.toByteArray();
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用gzip解压二进制内容byte[]
	 */
	public static byte[] gzipUncompress(byte[] data) {
		try(InputStream in = new ByteArrayInputStream(data);
				GZIPInputStream gzip = new GZIPInputStream(in);
				ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			byte[] tmp = new byte[1024];
			int len;
			while((len = gzip.read(tmp, 0, 1024)) != -1) {
				baos.write(tmp, 0, len);
			}
			return baos.toByteArray();
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取文件并使转换为byte[]数组
	 */
	public static byte[] loadFile(File file) {
		try(FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream((int)file.length())){
			byte[] buffer = new byte[1024];
			int len;
			while((len = fis.read(buffer)) != -1) {
				baos.write(buffer, 0 , len);
			}
			return baos.toByteArray();
		}catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	/**
	 * 加载文件并使用gzip压缩
	 */
	public static byte[] loadAndGzipFile(File file) {
		try(FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream((int)file.length());
				GZIPOutputStream gzip = new GZIPOutputStream(baos)) {
			byte[] buffer = new byte[1024];
			int len;
			while((len = fis.read(buffer)) != -1) {
				gzip.write(buffer, 0 , len);
			}
			gzip.finish();
			gzip.flush();
			return baos.toByteArray();
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}

	/**
	 * 解压并输出到指定文件
	 */
	public static void ungzipToFile(byte[] data, String filePath) {
		File file = new File(filePath);
		if(!file.exists()) {
			try{
				file.createNewFile();
			}catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		try(InputStream in = new ByteArrayInputStream(data);
			GZIPInputStream gzip = new GZIPInputStream(in);
			FileOutputStream fos = new FileOutputStream(file)) {
			byte[] tmp = new byte[1024];
			int count;
			while((count = gzip.read(tmp, 0, 1024)) != -1) {
				fos.write(tmp, 0, count);
			}
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}


	public static void main(String[] args) throws Exception {
		String withoutGzip = new String(Base64.getMimeEncoder().encode(loadFile("E:\\crisis_intervention.jpg")), "utf-8");
		String withGzip = new String(Base64.getMimeEncoder().encode(gzipCompress(loadFile("E:\\crisis_intervention.jpg"))), "utf-8");

		String withGzipLater = new String(gzipCompress(Base64.getMimeEncoder().encode(loadFile("E:\\crisis_intervention.jpg"))), "utf-8");

//		System.out.println(withoutGzip);
//		System.out.println("==================================================");
//		System.out.println(withGzip);
//		System.out.println("==================================================");
		System.out.println("length of withoutGzip: " + withoutGzip.length());
		System.out.println("length of withGzip: " + withGzip.length());
		System.out.println("length of withGzipLater: " + withGzipLater.length());
//		System.out.println("==================================================");
//		System.out.println(new String());
//
//		ungzipToFile(Base64.getMimeDecoder().decode(withGzip.getBytes("utf-8")), "E:\\abc.jpg");
//
//		String c = new String(Base64.getMimeEncoder().encode(loadAndGzipFile(new File("E:\\crisis_intervention.jpg"))));
//		System.out.println("==================================================");
//		System.out.println(c);
//		ungzipToFile(Base64.getMimeDecoder().decode(c.getBytes("utf-8")), "E:\\123.jpg");
	}
}
