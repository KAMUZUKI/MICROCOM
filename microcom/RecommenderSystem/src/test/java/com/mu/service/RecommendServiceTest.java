package com.mu.service;

import com.mu.service.impl.RecommendServiceImpl;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @author MUZUKI
 * @Classname RecommendServiceTest
 * @Description TODO
 * @Date 2023/5/27 12:00
 */

@SpringBootTest
public class RecommendServiceTest {
    @Autowired
    private RecommendServiceImpl recommendService;

    @Test
    void testRecommendByUser()  {
        recommendService.recommendByUser(1L,10);
    }
}
