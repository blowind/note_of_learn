
import org.apache.commons.codec.binary.Hex;
import java.io.UnsupportedEncodingException;
import java.security.*;


/*  提供信息摘要算法的功能  */
public class MessageDigestUtil {
	/* 可用Provider(基于jdk 1.8):SUN, SunRsaSign, SunEC, SunJSSE, SunJCE, SunJGSS, SunSASL, XMLDSig, SunPCSC, SunMSCAPI */
	/* 常用签名算法：MD2  MD5  SHA-1  SHA-256  SHA-384  SHA-512  */

	public static String digestMD5(String src, String salt) {
		try{
			MessageDigest md5 = MessageDigest.getInstance("md5");
			//	MessageDigest md5 = MessageDigest.getInstance("md5", "SUN");  效果同上

			// 放入要进行摘要的原始数据
			md5.update(src.getBytes("UTF-8"));
			// 如果有salt要添加，则加入
			if(salt != null) {
				md5.update(src.getBytes("UTF-8"));
			}

			byte[] result = md5.digest();

			System.out.println(md5.getProvider());

			/* 返回数字签名的16进制字符串形式 */
			return Hex.encodeHexString(result);

		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String digestSHA256(String src, String salt) {
		try{
			MessageDigest sha256 = MessageDigest.getInstance("sha-256", "SUN");
			sha256.update(src.getBytes());

			if(salt != null) {
				sha256.update(salt.getBytes());
			}
			byte[] result = sha256.digest();

			return Hex.encodeHexString(result);

		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}catch (NoSuchProviderException e) {
			e.printStackTrace();
			return null;
		}
	}


	public static String digestSHA512(String src, String salt) {
		try{
			MessageDigest sha512 = MessageDigest.getInstance("sha-512", "SUN");
			sha512.update(src.getBytes("UTF-8"));

			if(salt != null) {
				sha512.update(salt.getBytes("UTF-8"));
			}
			byte[] result = sha512.digest();

			return Hex.encodeHexString(result);
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch (NoSuchProviderException e) {
			e.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * HMAC是密钥相关的哈希运算消息认证码（Hash-based Message Authentication Code）,HMAC运算利用哈希算法，以一个密钥和一个消息为输入，生成一个消息摘要作为输出
	 * HMAC是需要一个密钥的。所以，HMAC_SHA1也是需要一个密钥的，而SHA1不需要
	 * HMAC-SHA1是一种安全的基于加密 hash函数和共享密钥的消息认证协议
	 * HMAC-SHA1消息认证机制的成功在于一个加密的 hash函数、一个加密的随机密钥和一个安全的密钥交换机制
	 */
	public static String hamcSha1(String key, String data) {
		try{
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(keySpec);
			return Hex.encodeHexString(mac.doFinal(data.getBytes("UTF-8")));
		}catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}catch (InvalidKeyException ike) {
			ike.printStackTrace();
		}
		return null;
	}

	public static void usage(String src) {
		try{
			/* 获取对象 */
			MessageDigest md1 = MessageDigest.getInstance("md5");
			/* 填充数据，可多次调用 */
			md1.update("abc".getBytes());
			/*  重置对象为初始状态  */
			md1.reset();
			/*  填充数据并计算摘要，计算完毕后重置对象，与reset效果相同  */
			byte[] ret1 = md1.digest("abc".getBytes());
			System.out.println(Hex.encodeHexString(ret1));

			MessageDigest md2 = MessageDigest.getInstance("MD5");
			md2.update("abc".getBytes());
			/*  返回此信息摘要对象的提供者 */
			System.out.println("Provider: " + md2.getProvider().getName());
			/*  返回算法的名称  */
			System.out.println("Algorithm: " + md2.getAlgorithm());
			/*  返回以字节为单位的摘要长度 */
			System.out.println("DigestLength: " + md2.getDigestLength());
			byte[] ret2 = md2.digest();
			System.out.println(Hex.encodeHexString(ret2));

			/* 计较摘要计算的字节数组内容是否相等 */
			System.out.println("is equal: " + MessageDigest.isEqual(ret1, ret2));

		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] argv) {

		for(Provider p : Security.getProviders()) {
			System.out.println(p.getName());
		}

		System.out.println(digestMD5("abcde", null));
		System.out.println(digestMD5("abcde", "12345"));

		System.out.println(digestSHA256("abcde", null));
		System.out.println(digestSHA512("abcde", null));

		usage("abcde");
	}
}
