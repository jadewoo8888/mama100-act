package com.biostime.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
	private static ValueOperations<String, String> valueOperations;
	public static String get(String key) {
		return valueOperations.get(key);
	}
	
	public static <T> T get(String key,Class<T> c){
		String value = valueOperations.get(key);
		if(StringUtils.isEmpty(value)){
			return null;
		}
		return JsonUtil.fromJson(value, c);
		
	}

	public static void set(String key, String value) {
		if (value == null) {
			return;
		}
		valueOperations.set(key, value);
	}
	
	public static <T> void set(String key, T value) throws JsonProcessingException {
		if (value == null) {
			return;
		}
		valueOperations.set(key, JsonUtil.toJson(value));
	}
	
	public static void set(String key, String value, long minute) {
		if (value == null) {
			return;
		}
		valueOperations.set(key, value, minute, TimeUnit.MINUTES);
	}
	
	
	public static <T> void set(String key, T value, long minute) throws JsonProcessingException {
		if (value == null) {
			return;
		}
		valueOperations.set(key, JsonUtil.toJson(value), minute, TimeUnit.MINUTES);
	}
	
	@Resource(name = "redisTemplate")
	public void setValueOperations(ValueOperations<String, String> valueOperations) {
		RedisUtil.valueOperations = valueOperations;
	}
	
}
