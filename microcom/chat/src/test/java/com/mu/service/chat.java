package com.mu.service;

import com.alibaba.fastjson.JSONObject;
import com.mu.entity.User;
import com.mu.utils.CoreUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

/**
 * @author MUZUKI
 * @Classname chat
 * @Description TODO
 * @Date 2023/4/24 17:06
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class chat {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test() {
        System.out.println(redisTemplate.opsForSet().remove("online_users", "{\"id\":\"" + 1 + "\"}"));
    }

    @Test
    public void test2() {
        // 存储在线用户的Hash Key
        String onlineUserHashKey = "online-users";

        // 将在线用户存储到Redis中
        User user = new User();
        user.setId(1);
        user.setName("lalla");
        user.setHead("https://cdn.quasar.dev/img/avatar3.jpg");
        redisTemplate.opsForHash().put(onlineUserHashKey, String.valueOf(user.getId()), JSONObject.toJSONString(user));
    }

    @Test
    public void test3() {
        // 存储在线用户的Hash Key
        String onlineUserHashKey = "online-users";
        // 获取在线用户
        Map<Object, Object> onlineUsers = redisTemplate.opsForHash().entries(onlineUserHashKey);
        List<User> users = CoreUtil.mapToList(onlineUsers, User.class);
        System.out.println(users);
    }
}
