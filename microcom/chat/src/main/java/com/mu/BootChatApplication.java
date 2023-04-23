package com.mu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author MUZUKI
 */

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class BootChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootChatApplication.class, args);
    }
}
