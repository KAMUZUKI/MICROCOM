package com.mu;

import cn.hutool.cron.CronUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author MUZUKI
 */
@SpringBootApplication
@MapperScan("com.mu.mapper")
@ServletComponentScan("com.mu.filter")
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableScheduling //开启定时任务
public class MicrocomApplication{
    public static void main(String[] args) {
        SpringApplication.run(MicrocomApplication.class, args);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handle(Throwable e) {
        e.printStackTrace();
        return e.getMessage();
    }
}
