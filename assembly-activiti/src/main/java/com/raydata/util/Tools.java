package com.raydata.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;

/**
 * @since 2017-08-18
 * @author tyro
 *
 */
public class Tools {

	/**
	 * 返回随机数
	 *
	 * @param n 个数
	 * @return
	 */
	public static String random(int n) {
		if (n < 1 || n > 10) {
			throw new IllegalArgumentException("cannot random " + n + " bit number");
		}
		Random ran = new Random();
		if (n == 1) {
			return String.valueOf(ran.nextInt(10));
		}
		int bitField = 0;
		char[] chs = new char[n];
		for (int i = 0; i < n; i++) {
			while (true) {
				int k = ran.nextInt(10);
				if ((bitField & (1 << k)) == 0) {
					bitField |= 1 << k;
					chs[i] = (char) (k + '0');
					break;
				}
			}
		}
		return new String(chs);
	}

	/**
	 * 指定范围的随机数
	 *
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @return
	 */
	public static int getRandomNum(int min, int max) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}

	/**
	 * 检测字符串是否不为空(null,"","null")
	 *
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s) {
		return s != null && !"".equals(s) && !"null".equals(s);
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 *
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s);
	}

	/**
	 * 检测是否为数字
	 * @param s
	 * @return
	 */
	public static boolean isNumber(String s) {
		try {
			Integer.parseInt(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 字符串换成UTF-8
	 *
	 * @param str
	 * @return
	 */
	public static String stringToUtf8(String str) {
		String result = null;
		try {
			if(Tools.isEmpty(str)){
				return "";
			}
			result = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * utf-8换成字符串
	 *
	 * @param str
	 * @return
	 */
	public static String utf8ToString(String str) {
		String result = null;
		try {
			if(Tools.isEmpty(str)){
				return "";
			}
			result = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @description: 获取以逗号分隔的字符串
	 *
	 * @param:
	 * @return: 
	 * @author: haiying.qin <haiying.qin@raykite.com>
	 * @createTime: 2019/6/29 16:12
	 */
	public static String getStrings(List strs){
		String str = "";
		for(int i = 0;i<strs.size();i++){
			if(i==0){
				str = "'"+strs.get(i)+"'";
			}else{
				str = str+",'"+strs.get(i)+"'";
			}
		}
		return str;
	}

	/**
	 * @description: 生成指定位数的随机数字和字母
	 *
	 * @param: 
	 * @return: 
	 * @author: haiying.qin <haiying.qin@raykite.com>
	 * @createTime: 2019/6/29 16:12
	 */
	public static String getStringRandom(int length) {

		String val = "";
		Random random = new Random();

		//参数length，表示生成几位随机数
		for(int i = 0; i < length; i++) {
			//生成一个随机的int值，该值介于[0,n)的区间，也就是0到n之间的随机int值，包含0而不包含n。
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			//输出字母还是数字
			if( "char".equalsIgnoreCase(charOrNum) ) {
				//输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char)(random.nextInt(26) + temp);
			} else if( "num".equalsIgnoreCase(charOrNum) ) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	public static void main(String[] args) {
		System.out.println(isNumber("22q3"));

		System.out.println(utf8ToString("%E8%8B%8F%E5%BC%BA%F0%9F%90%B3"));
	}
}
