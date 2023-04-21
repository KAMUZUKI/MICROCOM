package com.mu.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author MUZUKI
 * @Classname JedisConfig
 * @Description TODO
 * @Date 2023/3/6 22:19
 */

@Component
@ConfigurationProperties(prefix = "redis")
@PropertySource(value = "classpath:application.yml")
public class JedisConfig {

    private String host;

    private String port;

    public String getREDIS_HOST() {
        return host;
    }

    public int getREDIS_PORT() {
        return (Integer.parseInt(port));
    }
}
