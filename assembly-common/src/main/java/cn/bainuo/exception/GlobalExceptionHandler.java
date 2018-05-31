/**
 * Project Name:saas-access
 * File Name:GlobalExceptionHandler.java
 * Package Name:com.ccop.access.core.exception
 * Date:2017年9月20日上午11:00:14
 * Copyright (c) 2017, WangLZ All Rights Reserved.
 *
*/

package cn.bainuo.exception;

import cn.bainuo.exception.exceptionenum.GlobalErrorCode;
import cn.bainuo.vo.ResultVo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName:全局异常处理 <br/>
 * @version
 * @since JDK 1.8
 * @see
 */
@Slf4j
@ControllerAdvice
@Getter
@Setter
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Object defaultErrorHandler(HttpServletRequest request, HttpServletResponse response,
									   Exception ex)throws Exception {
		ResultVo rv = null;
		log.info("--------->进入全局异常 START {}",ex.getMessage());
		if(ex instanceof ServiceException){
			ServiceException se = (ServiceException) ex;
			rv =new ResultVo(se.getErrorCode(),se.getErrorMsg(),null);

		}else if (ex instanceof DBException){
			DBException de = (DBException)ex;
			rv = new ResultVo(de.getErrorCode(),de.getErrorMsg(),null);

		}else {
			handleStackTraceElement(ex);
			rv = new ResultVo(GlobalErrorCode.SYSTEM_ERROR.getCode(),
					GlobalErrorCode.SYSTEM_ERROR.getMessage(),ex.toString());
		}
		return rv;
	}

	private void handleStackTraceElement(Exception ex) {
		StackTraceElement[] ste = ex.getStackTrace();
		if (ste != null) {
			for (int i = 0; i < ste.length; i++) {
				StackTraceElement cause = ste[i];
				if (i == 0) {
					log.error("Exception Location [ClassName: " + cause.getClassName() + ", MethodName: "
							+ cause.getMethodName() + ", LineNumber: " + cause.getLineNumber() + "]");
					break;
				}
			}
		}
	}
}
