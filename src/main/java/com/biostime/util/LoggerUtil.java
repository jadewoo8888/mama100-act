package com.biostime.util;

import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;

public class LoggerUtil {

	private static final String WITH_REQUEST_INFO_LOG = "finish {0} cost {1} ms with request is {2} and response is {3}"; 
	private static final String WITHOUT_REQUEST_INFO_LOG = "finish {0} cost {1} ms and response is {2}"; 
	private static final String ERROR_LOG = "call {0} fail ,because of {1}"; 
	
	/**
	 * 日志出错 不应影响流程  所以捕获异常
	 * @param logger
	 * @param url
	 * @param time
	 * @param request
	 * @param response
	 */
	public static <T,K> void withInfo(Logger logger,String url,long time,K request, T response){
		try {
			if(logger.isInfoEnabled()){
				logger.info(MessageFormat.format(WITH_REQUEST_INFO_LOG, url,time,JsonUtil.toJson(request),JsonUtil.toJson(response)));
			}
		} catch (Exception e) {
			error(logger, url, e.getMessage(), e);
		}
		
	}
	
	public static <T> void withOutInfo(Logger logger,String url,long time,T response){
		try {
			if(logger.isInfoEnabled()){
				logger.info(MessageFormat.format(WITHOUT_REQUEST_INFO_LOG, url,time,JsonUtil.toJson(response)));
			}
		} catch (Exception e) {
			error(logger, url, e.getMessage(), e);
		}
		
	}
	
	public static void error(Logger logger,String url,String message,Exception e){
		logger.error(MessageFormat.format(ERROR_LOG, url,message),e);
	}
	

}
