package com.biostime.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

	@Bean
	@ConfigurationProperties(prefix = "spring.redis")
	public JedisPoolConfig poolConfig() {
		return new JedisPoolConfig();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "spring.redis")
	public JedisConnectionFactory connectionFactory() {
		return new JedisConnectionFactory(poolConfig());
	}

	@Bean
	public RedisTemplate<String, String> getRedisTemplate() {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(connectionFactory());
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.redis")
	public CacheManager cacheManager() {
		return new RedisCacheManager(getRedisTemplate());
	}

	@Bean
    public KeyGenerator keyGenerator() {
		return (Object target, Method method, Object... params) -> {
			return target.getClass().getName() + method.getName() + Stream.of(params).map(m -> String.valueOf(m)).collect(Collectors.joining());
		};
    }

}
