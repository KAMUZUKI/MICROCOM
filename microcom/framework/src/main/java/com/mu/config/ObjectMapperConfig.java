package com.mu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : MUZUKI
 * @date : 2023-04-25 16:53
 **/

@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 配置 ObjectMapper，比如禁用未知属性的反序列化时抛出异常、启用所有属性的可见性、启用默认的多态类型处理等等
        return objectMapper;
    }
}