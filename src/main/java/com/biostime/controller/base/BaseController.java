package com.biostime.controller.base;


import com.biostime.properties.ApplicationProperties;
import com.biostime.request.base.AccessTokenReq;
import com.biostime.response.base.UserInfoRes;
import com.biostime.util.RedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

public class BaseController {

	protected static final Logger logger = LogManager.getLogger(BaseController.class);
	@Resource
	private RestTemplate restTemplate;
	
	public UserInfoRes getUserInfo(AccessTokenReq req){
		UserInfoRes redis = RedisUtil.get(req.getDevid() + req.getAccessToken(),UserInfoRes.class);
		if(null != redis){
			return redis;
		}
		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		bodyMap.set("devid", req.getDevid());
		bodyMap.set("accessToken", req.getAccessToken());
		bodyMap.set("authData", req.getAuthData());
		bodyMap.set("tsno", req.getSeqNo());
		redis = restTemplate.postForObject(ApplicationProperties.O2O_BASE_URL + "/auth/o2o/user/getUserInfo.do", bodyMap, UserInfoRes.class);
		try {
			RedisUtil.set(req.getDevid() + req.getAccessToken(), redis, 60*24);//token1天过期
		} catch (JsonProcessingException e) {
			logger.error("设置缓存失败，key为" + (req.getDevid() + req.getAccessToken()) + ",因为" + e.getMessage());
		}
		return redis;
	}
}
