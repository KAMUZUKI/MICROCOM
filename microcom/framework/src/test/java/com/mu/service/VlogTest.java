package com.mu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mu.domain.Vlog;
import com.mu.service.impl.VlogServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author MUZUKI
 * @Classname VlogTest
 * @Description TODO
 * @Date 2023/4/26 14:53
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VlogTest {
    @Autowired
    private VlogServiceImpl userService;

    @Test
    public void testFindAll() {
        userService.list().forEach(System.out::println);
    }

    @Test
    public void testPage() {
        QueryWrapper<Vlog> queryWrapper = new QueryWrapper<>();
        IPage<Vlog> userEntityIPage = userService.findWithPage(new Page<>(1, 2), queryWrapper);
        System.out.println("总页数： " + userEntityIPage.getPages());
        System.out.println("总记录数： " + userEntityIPage.getTotal());
        userEntityIPage.getRecords().forEach(System.out::println);
    }
}
