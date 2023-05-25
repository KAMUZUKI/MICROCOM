package com.mu;

import cn.hutool.cron.CronUtil;
import org.apache.hadoop.mapreduce.v2.app.webapp.App;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;

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