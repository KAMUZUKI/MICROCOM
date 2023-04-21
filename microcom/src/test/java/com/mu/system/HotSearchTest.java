package com.mu.system;

import com.mu.system.service.RedisServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author MUZUKI
 * @Classname HotSearchTest
 * @Description TODO
 * @Date 2023/3/29 21:46
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SpringExtension.class})
public class HotSearchTest {

    @Autowired
    RedisServiceImpl redisService;

    @Test
    public void test() {
        redisService.incrementScore("java");
        redisService.incrementScore("C++");
        redisService.incrementScore("python");
        redisService.incrementScoreByUserId("java");
        redisService.addSearchHistoryByUserId("1", "java");
    }

    @Test
    public void test1() {
        System.out.println(redisService.getHotList("java"));
        System.out.println(redisService.getSearchHistoryByUserId("1"));
//        redisService.delSearchHistoryByUserId("1", "java");
    }
}
