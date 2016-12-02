package com.jxlt.stage.common.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author heshencao@163.com 2013-5-3 下午3:42:39
 */
public class Md5Encrypt {
	/**
	 * Used building output as Hex
	 */
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    
	/**
	 * 对字符串进行MD5加密
	 * @param text 明文
	 * @return 密文
	 */
	public static String md5(String text) {
		return md5(text,Charset.forName("utf8"));
	}
	
	/**
	 * 对字符串进行MD5加密
	 * @param text 明文
	 * @return 密文
	 */
	public static String md5(String text,Charset charset) {
		try {
			MessageDigest msgDigest = MessageDigest.getInstance("MD5");
			msgDigest.update(text.getBytes(charset));    //按照utf-8编码形式加密
			return  new String(encodeHex(msgDigest.digest()));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("当前系统不支持Md5加密");
        }
	}

	public static char[] encodeHex(byte[] data) {

		int l = data.length;
		char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}
		return out;
	}
	
	public static void main(String[] args) {
		System.out.println(Md5Encrypt.md5("ty189@2013admin"));
	}
	
	public static byte[] md5Byte(byte[] input) {
	    try {
	    	MessageDigest msgDigest = MessageDigest.getInstance("MD5");
	    	msgDigest.update(input);
	 	    return msgDigest.digest();
	    } catch (NoSuchAlgorithmException e) {
	      throw new IllegalStateException("System doesn't support MD5 algorithm.");
	    }
	  }
//	public static String md5Byte(byte[] array) {
//		try {
//			MessageDigest msgDigest = MessageDigest.getInstance("MD5");
//			msgDigest.update(array);
//			return  new String(encodeHex(msgDigest.digest()));
//		} catch (NoSuchAlgorithmException e) {
//			throw new IllegalStateException("当前系统不支持Md5加密�?);
//        }
//	}


}