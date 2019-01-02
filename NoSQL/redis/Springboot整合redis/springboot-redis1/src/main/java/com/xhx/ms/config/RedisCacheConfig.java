package com.xhx.ms.config;

import com.sun.org.apache.xml.internal.utils.StringBufferPool;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.util.HashMap;


/**
 * redis缓存配置
 * Created by xuhaixing on 17-11-25.
 */

@Configuration
@EnableCaching//启用缓存
public class RedisCacheConfig extends CachingConfigurerSupport {

    /**
     * 自定义key,此方法会根据类名+方法名+所有参数的值生成一个
     * 唯一的key，即@Cacheable中的key
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                System.out.println(sb);
                return sb.toString();
            }
        };
    }

    /**
     * redisTemplate缓存操作类
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        return redisTemplate;
    }

    /**
     * 缓存管理器
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        //单位s
        redisCacheManager.setDefaultExpiration(3000);
        HashMap<String, Long> map = new HashMap<>(8);
        map.put("test",3000L);
        redisCacheManager.setExpires(map);
        return redisCacheManager;
    }


}
