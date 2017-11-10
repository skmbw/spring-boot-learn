package com.fuuzii.config;

import com.vteba.cache.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author yinlei
 * @since 2017/11/10 10:45
 */
@Configuration
public class CommonComponentConfiguration {

    /**
     * Redis 缓存服务。基于RedisTemplate实现。
     *
     * @param redisTemplate Spring 提供的基于Jedis的Redis模板方法
     * @return 缓存服务实例
     */
    @Bean
    @Autowired
    public RedisService redisService(RedisTemplate redisTemplate) {
        RedisService redisService = new RedisService();
        redisService.setRedisTemplate(redisTemplate);
        return redisService;
    }
}
