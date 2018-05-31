package cn.bainuo.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * 加密类
 */
public class Encrypt {
	public static String encryptMD5(String strInput) {
	    StringBuffer buf = null;
	    try {
	      MessageDigest md = MessageDigest.getInstance("MD5");
	      md.update(strInput.getBytes());
	      byte[] b = md.digest();
	      buf = new StringBuffer(b.length * 2);
	      for (int i = 0; i < b.length; ++i) {
	        if ((b[i] & 0xFF) < 16)
	          buf.append("0");

	        buf.append(Long.toHexString(b[i] & 0xFF));
	      }
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    return buf.toString();
	}
	public static String generatePwd(String pwd) {
		   return encryptMD5(pwd);	   
	}
	public static String generatePwd(String pwd,String salt) {
		   String mergePwd=pwd+"{"+salt+"}"; 
		   return encryptMD5(mergePwd);	   
	}
	/** 
     * BASE64编码
     * @param src 
     * @return 
     * @throws Exception 
     */  
    public static String base64Encoder(String src) throws Exception {  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(src.getBytes("utf-8"));  
    }  
      
    /** 
     * BASE64解码
     * @param dest 
     * @return 
     * @throws Exception 
     */  
    public static String base64Decoder(String dest) throws Exception {  
        BASE64Decoder decoder = new BASE64Decoder();  
        return new String(decoder.decodeBuffer(dest), "utf-8");  
    }  
    /**
     * 
     * digestMD5:MD5摘要
     * @author wangxf
     * @param msg
     * @return
     * @throws Exception
     * @since JDK 1.8
     */
    public static String digestMD5(String text) {
    	try {
    		MessageDigest md5 = MessageDigest.getInstance("MD5");
    		// 更新要计算的内容
        	md5.update(text.getBytes());
    		// 完成哈希计算，得到摘要
    		byte[] encoded = md5.digest();
    		return Base64.encodeBase64URLSafeString(encoded);
		} catch (Exception e) {
			return text;
		}
	}
	public static void main(String[] args) {
		//String str=encryptMD5("ff8080813adc720e013adcbd5e1c0000zray");
//		String str="";
//		try {
//			str = base64Decoder("ZmY4MDgwODEzYmJjYWUzZjAxM2JjYzM5YzE4YTAwMjI6MjAxMzA4MDgxNDI3MDE=");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(str);
		//0a0a607531654c420edeabffc32ca58e
		String str=encryptMD5("111111");
		//String str=encryptMD5("ccop_bbs_web101");
		System.out.println("str>>"+str);
		//str=encryptMD5(str+"171ade");
		//System.out.println("11>>"+str);
	}
	
	public static String generateLoginPwd(String loginName,String pwd,String randomCode){
		//md5(md5(md5(密码)+用户名)+随机码)
		//String md5Str1=encryptMD5(pwd);
		String md5Str2=encryptMD5(pwd+loginName);
		return encryptMD5(encryptMD5(md5Str2+randomCode));
	}
}
