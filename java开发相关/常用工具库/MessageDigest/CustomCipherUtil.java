package com.zxf.springmvc.validator;

import org.apache.commons.codec.binary.Hex;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Base64;

/**
 *  @ClassName: CustomCipherUtil
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2018/12/21 16:51
 *  @Version: 1.0
 **/
public class CustomCipherUtil {

	private static String key = "mamamiya";
	private static String cipherText = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-=+";

	public static void main(String[] args) throws Exception {
		String src= "一一一一一一一TIANxia一家%%%";
		String middle = encode(src);
		System.out.println("base64: " + Base64.getUrlEncoder().encodeToString(src.getBytes("UTF-8")));
		System.out.println("urlEncode: " + URLEncoder.encode(src, "UTF-8"));
		System.out.println("middle: " + middle);
		System.out.println("output" + decode(middle));
	}

	public static String encode(String src) throws Exception {
		String key = CustomCipherUtil.key;
		String cipherText = CustomCipherUtil.cipherText;

		/*获取[0,64]中的随机整数*/
		int luckyIndex = (int) (Math.random() * 64);
		/*获取chpherText字符串中该位置的字符作为幸运字符*/
		char luckyChar = cipherText.charAt(luckyIndex);

		/*拼接key和选取的幸运字符，计算其MD5值，按照16进制的字符串形式返回*/
		String keyAfterMD5 = getPartialKey(key + luckyChar, luckyIndex);

		/*使用BASE64算法对输入字符串进行加密*/
		String srcAfterBase64 = Base64.getUrlEncoder().encodeToString(src.getBytes("UTF-8"));

		int j = 0, k = 0;
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < srcAfterBase64.length(); i++) {
			/*k在[0, md5Key.length]之间循环*/
			k %= keyAfterMD5.length();
			/*根据 综合随机数、chpherText中本次遍历字符位置、key的MD5哈希值中k处字符的二进制值 计算得到下标*/
			j = (luckyIndex + cipherText.indexOf(srcAfterBase64.charAt(i)) + (byte) keyAfterMD5.charAt(k++)) % 64;
			/*读取前述下标对应的值作为该字符的加密字符*/
			output.append(cipherText.charAt(j));
		}
		/*将幸运字符与加密后的字符作为结果输出，对结果进行url转换，即所有非数字字母字符转为%xx的形式*/
		return URLEncoder.encode(luckyChar + output.toString(), "UTF-8");
	}

	public static String decode(String src) throws Exception {
		String key = CustomCipherUtil.key;
		String cipherText = CustomCipherUtil.cipherText;

		String srcAfterUrlDecode = URLDecoder.decode(src, "UTF-8");

		/*获取传递过来的幸运字符*/
		String luckyChar = srcAfterUrlDecode.substring(0, 1);
		/*获取待解析的字符串*/
		String toDecode = srcAfterUrlDecode.substring(1);

		/*获取幸运字符在密码本上的下标*/
		int luckyIndex = cipherText.indexOf(luckyChar);
		/*拼接key和选取的幸运字符，计算其MD5值，按照16进制的字符串形式返回*/
		String keyAfterMD5 = getPartialKey(key + luckyChar, luckyIndex);

		StringBuilder output = new StringBuilder();
		int j = 0, k = 0;
		for (int i = 0; i < toDecode.length(); i++) {
			k %= keyAfterMD5.length();
			/*获取密码本cipherText中原字符所在位置j*/
			j = cipherText.indexOf(toDecode.charAt(i)) - luckyIndex - (byte)keyAfterMD5.charAt(k++);
			while (j < 0) {
				j += 64;
			}
			output.append(cipherText.charAt(j));
		}
		//		System.out.println("before base64 decode: " + tmp.toString());
		return new String(Base64.getUrlDecoder().decode(output.toString()), "UTF-8");
	}

	public static String getPartialKey(String srcKey, int luckyIndex) {
		try{
			String keyAfterMD5 = digestMD5(srcKey, null);
			/*截取指定区间的字符串作为key,md5值是32位的字符和数字，此处只可能取[0,15]之间的字符*/
			return keyAfterMD5.substring((luckyIndex % 8), (luckyIndex % 8 + 7) + (luckyIndex % 8));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String digestMD5(String src, String salt) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("md5");

		// 放入要进行摘要的原始数据
		md5.update(src.getBytes("UTF-8"));
		// 如果有salt要添加，则加入
		if (salt != null) {
			md5.update(src.getBytes("UTF-8"));
		}

		byte[] result = md5.digest();
		/* 返回数字签名的16进制字符串形式 */
		return Hex.encodeHexString(result);
	}
}
