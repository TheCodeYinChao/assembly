/**
 * Project Name:ccopsms
 * File Name:ProtocolUtil.java
 * Package Name:com.ccop.common.util
 * Date:2016-7-15下午4:17:30
 * Copyright (c) 2016, wanglz All Rights Reserved.
 *
*/

package cn.bainuo.util;

import javax.servlet.http.HttpServletRequest;

/**
 * HTTP协议解析工具类
 * ClassName: ProtocolUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年10月17日 上午9:44:08 <br/>
 *
 * @author WangLZ
 * @version 
 * @since JDK 1.6
 */
public class ProtocolUtil {

	public static int doCheckContentType(HttpServletRequest request) {
		String contentType = request.getContentType();
		if ((contentType != null) && (contentType.indexOf("json") > -1)) {
			return 1;
		}
		return 0;
	}

	public static int doCheckAccept(HttpServletRequest request) {
		String accept = request.getHeader("Accept");
		if ((accept != null) && (accept.indexOf("json") > -1)) {
			return 1;
		}
		if((accept != null) && (accept.indexOf("xml") > -1)) {
			return 0;
		}
		return 2;
	}

	public static int doCheckVersion(HttpServletRequest request) {
		String uri = request.getRequestURI();
		int startIndex = 0;
		String str = uri.substring(startIndex + 1);
		int endIndex = str.indexOf("/") + 1;
		if (endIndex == -1)
			return 0;

		String version = uri.substring(startIndex + 1, endIndex);
		if (version.equals("2013-12-26")) {
			return 1;// 新版本
		}
		return 0;
	}

}
