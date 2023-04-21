package com.mu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mu.framework.domain.User;
import com.mu.framework.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MybatisPlusDemoApplicationTests {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> allUsers = userMapper.selectList(queryWrapper);
        System.out.println(allUsers);
    }

    @Test
    public void test3(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select().likeRight("name", "张").likeRight("type","1");
        List<User> userList = userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }
}