package com.mu;

import com.mu.framework.utils.JedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

/**
 * @author MUZUKI
 * @Classname JedisTest
 * @Description TODO
 * @Date 2023/3/6 21:05
 */

@SpringBootTest
public class JedisTest {
    @Test
    void contextLoads() {
        Jedis jedis = JedisUtils.getInstance();
        System.out.println(jedis);
    }
}
