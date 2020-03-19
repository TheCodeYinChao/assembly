/**
 * Project Name:saas-access
 * File Name:GlobalExceptionHandler.java
 * Package Name:com.ccop.access.core.exception
 * Date:2017年9月20日上午11:00:14
 * Copyright (c) 2017, WangLZ All Rights Reserved.
 *
*/

package com.raydata.core.exception;

import com.raydata.core.ResultVo;
import com.raydata.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName:全局异常处理 <br/>
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResultVo defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex)
			throws Exception {
		String errorCode = null;
		String errorMsg = null;
		Object data = null;

		if (ex instanceof ArgumentException) {
			ArgumentException se = (ArgumentException) ex;
			errorCode = se.code();
			errorMsg = se.getMessage();
		}
		else {
			handleStackTraceElement(ex);
			errorCode = GlobalErrorCode.SYSTEM_ERROR.getCode();
			errorMsg = GlobalErrorCode.SYSTEM_ERROR.getMessage();
			data = ex.getMessage();
			log.error("平台交互 错误 [{}{}].", ex.getMessage(),ex);
		}
		ResultVo resp = ResultVo.failure(errorCode, errorMsg, data);
		log.info("exception handle response result : {}.", JacksonUtil.toJson(resp));
		return resp;
	}

	private void handleStackTraceElement(Exception ex) {
		StackTraceElement[] ste = ex.getStackTrace();
		if (ste != null) {
			for (int i = 0; i < ste.length; i++) {
				StackTraceElement cause = ste[i];
				if (i == 0) {
					log.info("Exception Location [ClassName: " + cause.getClassName() + ", MethodName: "
							+ cause.getMethodName() + ", LineNumber: " + cause.getLineNumber() + "]");
					break;
				}
			}
		}
	}
}
