package com.mu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mu.entity.Vlog;
import com.mu.service.impl.VlogServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * @author MUZUKI
 * @Classname VlogTest
 * @Description TODO
 * @Date 2023/4/26 14:53
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VlogTest {
    @Autowired
    private VlogServiceImpl vlogService;

    @Value("${spring.redis.host}")
    private String host;

    @Test
    public void testFindAll() {
        vlogService.list().forEach(System.out::println);
    }

    @Test
    public void findWithPage() {
        IPage<Vlog> userEntityIPage = vlogService.findWithPage(new Page<>(1, 2));
        System.out.println("总页数： " + userEntityIPage.getPages());
        System.out.println("总记录数： " + userEntityIPage.getTotal());
        userEntityIPage.getRecords().forEach(System.out::println);
    }

    @Test
    public void findWithPageById(){
        int id =  1;
        int page = 1;
        IPage<Vlog> userEntityIPage = vlogService.findWithPageById(id,new Page<>(page, 8));
        System.out.println("总页数： " + userEntityIPage.getPages());
        System.out.println("总记录数： " + userEntityIPage.getTotal());
        userEntityIPage.getRecords().forEach(System.out::println);
    }

    @Test
    void findVlogWithList(){
        vlogService.findVlogWithList(Arrays.asList(1L,2L,3L)).forEach(System.out::println);
    }
}
