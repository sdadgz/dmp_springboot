package cn.sdadgz.dmp_springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis配置
 *
 * <p>
 * 废物本物
 * </p>
 *
 * @author sdadgz
 * @since 2023/3/12 13:45:55
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 连接，这逼东西得放第一位
        template.setConnectionFactory(redisConnectionFactory);

        // hash 用 string
        template.setHashKeySerializer(RedisSerializer.string());
        // key
        template.setKeySerializer(RedisSerializer.string());

        // 默认用json
//        template.setDefaultSerializer(getRedisJsonSerializer());
        template.setDefaultSerializer(RedisSerializer.json());

        // 赋默认值
        template.afterPropertiesSet();
        return template;
    }
}
