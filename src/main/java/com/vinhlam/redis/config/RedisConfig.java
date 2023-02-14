package com.vinhlam.redis.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configurable
public class RedisConfig extends CachingConfigurerSupport
{
	
//	@Bean
//	public RedisCacheConfiguration cacheConfiguration() {
//	   return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(10));
//	}
	
//	@Bean
//	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//	    RedisSerializationContext.SerializationPair<Object> jsonSerializer = RedisSerializationContext.SerializationPair
//	            .fromSerializer(new GenericJackson2JsonRedisSerializer());
//
//	    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//	            .serializeValuesWith(jsonSerializer);
//
//	    return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory)
//	            .cacheDefaults(config).build();
//	}
	
	@Bean
	public JedisConnectionFactory connectFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		config.setHostName("localhost");
		config.setPort(6379);
		
		return new JedisConnectionFactory(config);
	}
	
	@Bean
	public RedisTemplate redisTemplate() {
		RedisTemplate template = new RedisTemplate<>();
		template.setConnectionFactory(connectFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new JdkSerializationRedisSerializer());
		template.setValueSerializer(new JdkSerializationRedisSerializer());
		template.setEnableTransactionSupport(true);
		template.afterPropertiesSet();
		return template;
	}
	
//	@Bean
//	private RedisTemplate<String, ?> createRedisTemplateForEntity() {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
//        redisTemplate.setConnectionFactory(connectFactory());
//        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.afterPropertiesSet();
//
//    return redisTemplate;
//}
	
	
}
