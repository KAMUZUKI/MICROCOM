package com.mu.service;

import com.mu.mapper.UserMapper;
import com.mu.service.impl.RecommendToRedisServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author MUZUKI
 * @Classname RecommendToRedisServiceTest
 * @Description TODO
 * @Date 2023/5/25 21:26
 */

@SpringBootTest
public class RecommendToRedisServiceTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecommendToRedisServiceImpl recommendToRedisService;

    @Test
    void getVlogListIds() {
        //从nacos中获取服务地址
        recommendToRedisService.freshRedisRecommend(1,100);
    }

    @Test
    void getUidListIds() {
        userMapper.getUidList().forEach(System.out::println);
    }
}
