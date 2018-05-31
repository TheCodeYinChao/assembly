package cn.bainuo.util;

import java.util.Base64;

/**
 * ClassName: Base64Utils
 * Function: java8 Base64
 * date:  2017-11-29 11:22
 *
 * @author LH
 */
public class Base64Utils {

	/**
	 * 编码字节，编码内容输出为{@code String}格式。
	 * @param bytes byte数组。
	 * @return {@code String}格式的编码内容。
	 */
	public static String base64Encoding(byte[] bytes) {
		if(bytes != null)
			return Base64.getEncoder().encodeToString(bytes);
		else
			return null;
	}
	/**
	 * 编码字符串，编码内容输出为{@code String}格式。
	 * @param str 字符串。
	 * @return {@code String}格式的编码内容。
	 */
	public static String base64StrEncoding(String str) throws Exception{
		if(str != null)
			return Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
		else
			return null;
	}
	/**
	 * 解码base64字符串。
	 * @param str 待解码的字符串格式。
	 * @return 解码后的字节数组。
	 */
	public static byte[] base64Decoding(String str) {
		if(str != null)
			return Base64.getDecoder().decode(str);
		else
			return null;
	}


	public static void main(String[] args) {

	}
}
