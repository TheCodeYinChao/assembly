/**
 * Project Name:saas-common
 * File Name:ThreadUtils.java
 * Package Name:com.ccop.common.util
 * Date:2018年1月6日下午5:58:29
 * Copyright (c) 2018, WangLZ All Rights Reserved.
 *
*/

package cn.bainuo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ClassName:ThreadUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年1月6日 下午5:58:29 <br/>
 * 
 * @author WangLZ
 * @version
 * @since JDK 1.8
 * @see
 */
@Slf4j
public class ThreadUtils {
	/**
	 * 创建自定义线程<br/>
	 * createThreadPool:(prefix-线程名称前缀). <br/>
	 *
	 * @author WangLZ
	 * @param prefix:线程名称前缀.<br/>
	 * @return
	 * @since JDK 1.8
	 */
	public static Executor createThreadPool(String prefix) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(3);
		executor.setMaxPoolSize(5);
		executor.setQueueCapacity(100000);
		executor.setThreadNamePrefix(prefix);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		executor.initialize();
		log.info("{} ThreadPool init success.", prefix);
		return executor;
	}

}
