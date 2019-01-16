package com.zxf.qrcode;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *  @ClassName: EncryptUtil
 *  @Description:
 *  @Author: ZhangXuefeng
 *  @Date: 2019/1/16 14:00
 *  @Version: 1.0
 **/
public class EncryptUtil {

	public static SecretKeySpec getKeySpec(String seed) {
		SecretKeySpec keySpec = null;
		try{
			/*生成密钥*/
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(seed.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] encoded = secretKey.getEncoded();
			keySpec = new SecretKeySpec(encoded, "AES");
		}catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}
		return keySpec;

	}

	public static String encryptByAES(String originText, String seed) {
		try{
			SecretKeySpec keySpec = getKeySpec(seed);
			/*加密*/
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] cipherText =  cipher.doFinal(originText.getBytes());
			return Hex.encodeHexString(cipherText);
		}catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}catch (NoSuchPaddingException nspe) {
			nspe.printStackTrace();
		}catch (InvalidKeyException ike) {
			ike.printStackTrace();
		}catch (IllegalBlockSizeException ibse) {
			ibse.printStackTrace();
		}catch (BadPaddingException bpe) {
			bpe.printStackTrace();
		}
		return null;
	}

	public static String decryptByAES(String cipherText, String seed) {
		try{
			byte[] cipherTextInByte = Hex.decodeHex(cipherText);

			SecretKeySpec keySpec = getKeySpec(seed);

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			return new String(cipher.doFinal(cipherTextInByte), "UTF-8");
		}catch (NoSuchAlgorithmException nsae) {
			nsae.printStackTrace();
		}catch (NoSuchPaddingException nspe) {
			nspe.printStackTrace();
		}catch (InvalidKeyException ike) {
			ike.printStackTrace();
		}catch (IllegalBlockSizeException ibse) {
			ibse.printStackTrace();
		}catch (BadPaddingException bpe) {
			bpe.printStackTrace();
		}catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}catch (DecoderException de) {
			de.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String originText = "一二三四五六七";
		String cipherText = encryptByAES(originText, "abc");
		System.out.println(cipherText);
		System.out.println(decryptByAES(cipherText, "abc"));
	}
}
