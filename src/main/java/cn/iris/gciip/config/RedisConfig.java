package cn.iris.gciip.config;


import cn.iris.gciip.util.JsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Iris 2022/8/6
 */
@Configuration
public class RedisConfig {

    @Bean
    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        JsonRedisSerializer<Object> serializer = new JsonRedisSerializer<>(Object.class);

        // 使用StringRedisSerializer进行序列化和反序列化Redis键值对
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // 序列化反序列化Hash键值对
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
        return template;
    }
}
