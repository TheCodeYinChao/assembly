/**
 * Project Name:saas-common
 * File Name:Utils.java
 * Package Name:com.ccop.common.util
 * Date:2017年11月13日下午6:01:46
 * Copyright (c) 2017, WangLZ All Rights Reserved.
 *
*/

package cn.bainuo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * ClassName:Utils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年11月13日 下午6:01:46 <br/>
 * 
 * @author WangLZ
 * @version
 * @since JDK 1.6
 * @see
 */
@Slf4j
public class Utils {
	public static String SAASID = "ytx";
	public static String MDT = "";// 模块归属标识
	public static String INSTANCE = "";// 实例标识符

	/**
	 * 初始化模块信息. <br/>
	 * Instance:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author WangLZ
	 * @since JDK 1.8
	 */
	public static void doInstance(String serverName) {
		String logPath = System.getProperty("log.path");
		if (StringUtils.isEmpty(logPath)) {
			log.error("[{}]Finished : FAIL logPath null", serverName);
			System.exit(0);
		}

		String instance = System.getProperty("saas.instance");
		if (StringUtils.isEmpty(instance)) {
			log.error("[{}]Finished : FAIL instance null", serverName);
			System.exit(0);
		}
		INSTANCE = instance;
	}

//	/*
//	 * 生产32位UUID
//	 */
//	public static String UUID() {
//		UUID uuid = UUID.randomUUID();
//		return uuid.toString().replace("-", "");
//	}

	public static Integer checkPhoneNum(String mobile){
		Integer result = 0;
		if (StringUtils.isEmpty(mobile)) {
			return result;
		}
		if (mobile.length() != 11) {
			return result;
		}
		// 移动
		// 134、135、136、137、138、139、
		// 150、151、152、157(TD)、158、159、
		// 182、183、184、187、178、188、147（数据卡号段） 、1705（虚拟运营商移动号段）
		// String chinaMobile =
		// "(13[4-9]|15[0-27-9]|178|125|147|18[24378])\\d{8}";
		String chinaMobile = "(13[4-9]|15[0-27-9]|178|125|18[24378])\\d{8}";
		// 联通
		// 中国联通：130、131、132、145(数据卡号段)155、156、176、185、186、1709（虚拟运营商联通号段）
		String chinaUnicom = "(13[0-2]|15[56]|176|185|186)\\d{8}";
		// 电信
		// 中国电信：133、153、177、180、181、189、（1349卫通）、1700（虚拟运营商电信号段）
		String chinaTelecom = "(133|153|177|173|180|181|189)\\d{8}";
		if (mobile.matches(chinaMobile)) {
			result = 1000;
		} else if (mobile.matches(chinaUnicom)) {
			result = 3000;
		} else if (mobile.matches(chinaTelecom)) {
			result = 2000;
		}
		return result;
	}

	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		String testRemotIp = request.getRemoteAddr();
		log.debug("testRemotIp:" + testRemotIp);
		// if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip) &&
		// !ip.startsWith("10"))
		// {
		// logger.info("X-Real-IP:" + ip);
		// return ip;
		// }

		// 优化后代码
		if (StringUtils.isNotBlank(ip) && !(ip.length() > 2 && ip.charAt(0) == '1' && ip.charAt(1) == '0')
				&& !"unknown".equalsIgnoreCase(ip)) {
			log.info("X-Real-IP:" + ip);
			return ip;
		}

		ip = request.getHeader("X-Forwarded-For");
		log.debug("X-Forwarded-For:{}", ip);
		if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				log.info("X-Forwarded-For index 0:" + ip);
				return ip.substring(0, index);
			} else {
				log.info("X-Forwarded-For:" + ip);
				return ip;
			}
		} else {
			ip = request.getRemoteAddr();
			log.info("request.getRemoteAddr:" + ip);
			return ip;
		}
	}

	public static InetAddress getLocalHostLANAddress() throws Exception {
		try {
			InetAddress candidateAddress = null;
			// 遍历所有的网络接口
			for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
				NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
				// 在所有的接口下再遍历IP
				for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
					InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
					if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
						if (inetAddr.isSiteLocalAddress()) {
							// 如果是site-local地址，就是它了
							return inetAddr;
						} else if (candidateAddress == null) {
							// site-local类型的地址未被发现，先记录候选地址
							candidateAddress = inetAddr;
						}
					}
				}
			}
			if (candidateAddress != null) {
				return candidateAddress;
			}
			// 如果没有发现 non-loopback地址.只能用最次选的方案
			InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
			return jdkSuppliedAddress;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * StringBuilder
	 * 
	 * @param strs
	 * @return
	 */
	public static String stringBuilder(Object... objs) {
		StringBuilder builder = new StringBuilder();
		for (Object str : objs) {
			builder.append(str);
		}
		return builder.toString();
	}

}
