package com.biostime.config;


import com.biostime.constant.Constant;
import com.biostime.request.base.BaseReq;
import com.biostime.response.base.BaseRes;
import com.biostime.util.JsonUtil;
import com.biostime.util.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 
 *
 * @author Moss Zeng
 * @date 2017年6月12日上午9:31:24
 */
@Aspect // 定义一个切面
@Configuration
public class ControllerAspect {
	private static final Logger logger = LogManager.getLogger(ControllerAspect.class);

	// 定义切点Pointcut
	@Pointcut("execution(* com.biostime..*Controller.*(..))")
	public void execController() {
	}

	@Around("execController()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		String uri = request.getRequestURI();
		String seqNo = Constant.EMPTY;
		String params = Constant.EMPTY;
		if ("POST".equals(request.getMethod())) {
			Object[] paramsArray = pjp.getArgs();
			if (paramsArray != null && paramsArray.length > 0) {
				for (int i = 0; i < paramsArray.length; i++) {
					if (paramsArray[i] instanceof BaseReq) {
						BaseReq req = (BaseReq) paramsArray[i];
						if (StringUtils.isBlank(req.getSeqNo())) {
							req.setSeqNo(NumberUtil.genSeqNo());
						}
						seqNo = req.getSeqNo();
					}
					params += JsonUtil.toJson(paramsArray[i]);
				}
			}
		} else {
			params = request.getQueryString();
			seqNo = request.getParameter("seqNo");
		}
		if (StringUtils.isBlank(seqNo)) {
			seqNo = NumberUtil.genSeqNo();
		}
		if(StringUtils.isBlank(params)){
			params = "{\"seqNo\":" + seqNo + "}";
		}
		logger.info("开始调用{}, request: {}", uri, params);
		// result的值就是被拦截方法的返回值
		long startTime = System.currentTimeMillis();
		Object result = pjp.proceed();
		if (result instanceof BaseRes) {
			BaseRes baseResponse = (BaseRes) result;
			baseResponse.setSeqNo(seqNo);
		} else {
			logger.warn("{}的返回值没有继承BaseRes");
		}
		if (logger.isInfoEnabled()) {
			logger.info("结束调用{}，cost{} ms ,response:{} ", uri, System.currentTimeMillis() - startTime,JsonUtil.toJson(result));
		}
		return result;
	}
}
