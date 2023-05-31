package com.mu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MUZUKI
 */

@SpringBootApplication
@MapperScan("com.mu.mapper")
@EnableDiscoveryClient
public class RecommenderSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecommenderSystemApplication.class, args);
    }

}