/**
 * Project Name:saas-common
 * File Name:DockerUtil.java
 * Package Name:com.ccop.common.util
 * Date:2017年11月3日上午9:08:43
 * Copyright (c) 2017, WangLZ All Rights Reserved.
 *
*/

package cn.bainuo.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;

/**
 * docker工具类<br/>
 * ClassName:DockerUtil <br/>
 * Date: 2017年11月3日 上午9:08:43 <br/>
 * 
 * @author WangLZ
 * @version
 * @since JDK 1.6
 * @see
 */
@Slf4j
public class DockerUtil {
	private static Integer PID = 0;// Application Process Id
	private static String DCID = "";// Docker Container Id

	/**
	 * Get Application Process Id
	 */
	public static Integer PID() {
		return PID;
	}

	/**
	 * Get Docker Container Id <br>
	 * for example : 700991e988df
	 */
	@Deprecated
	public static String DCID() {
		return DCID;
	}

	static {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		if (name != null && name != "" && name.contains("@")) {
			String[] arr = name.split("@");
			PID = Integer.parseInt(arr[0]);
			DCID = arr[1];
			log.debug("application PID:{} @ DCID:{}", PID, DCID);
		}
	}
}
