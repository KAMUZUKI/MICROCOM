package com.mu.framework.utils;

import com.mu.framework.config.JedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @author : MUZUKI
 * @date : 2023-03-07 18:54
 **/
@Component
public class JedisUtils {

    private static JedisConfig jedisConfig;

    private static Jedis jedis = null;

    @Autowired
    public void setJedisConfig(JedisConfig jedisConfig) {
        JedisUtils.jedisConfig = jedisConfig;
    }

    private JedisUtils(){
    }

    public static Jedis getInstance() {
        if (jedis == null){
            jedis = new Jedis(jedisConfig.getREDIS_HOST(), jedisConfig.getREDIS_PORT());
        }
        return jedis;
    }

}
