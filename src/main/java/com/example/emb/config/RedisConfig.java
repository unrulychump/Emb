package com.example.emb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();

        // Configure connection details, e.g., host, port, password
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setPassword("your_redis_password"); // Set your Redis password if applicable
        // Configure maximum number of connections


        // Configure connection timeout in milliseconds
        jedisConnectionFactory.setTimeout(2000);

        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // Set the Redis connection factory
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        // Use StringRedisSerializer for keys
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // Use Jackson2JsonRedisSerializer for values
        // You can customize the serializer based on your needs
        // redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

        // Enable transaction support
        // redisTemplate.setEnableTransactionSupport(true);

        return redisTemplate;
    }

}
