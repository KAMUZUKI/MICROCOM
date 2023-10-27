package com.mu;

import com.mu.entity.Vlog;
import com.mu.service.impl.UserServiceImpl;
import com.mu.service.impl.VlogCommentServiceImpl;
import com.mu.service.impl.VlogServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.stream.Collectors;

/**
 * @author : zengzw5
 * @date : 2023-10-25 10:39
 **/

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TmpTest {
    @Autowired
    private VlogServiceImpl vlogService;

    @Autowired
    private UserServiceImpl userService;
    @Test
    public void test(){
        // 获取vlogId
    }
}
