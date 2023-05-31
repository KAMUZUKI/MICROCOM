package com.mu.service;

import com.mu.mapper.VlogMapper;
import com.mu.service.impl.MysqlDataSourceServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author MUZUKI
 * @Classname MysqlDataSourceServiceTest
 * @Description TODO
 * @Date 2023/5/27 12:18
 */

@SpringBootTest
public class MysqlDataSourceServiceTest {
    @Autowired
    private MysqlDataSourceServiceImpl mysqlDataSourceService;

    @Autowired(required = false)
    private VlogMapper vlogMapper;

    @Test
    void recommendByUser() {
        mysqlDataSourceService.recommendByUser(1L,10).forEach(System.out::println);
    }

    @Test
    void recommendByItem() {
        mysqlDataSourceService.recommendByItem(1L, 1L,10).forEach(System.out::println);
    }

    @Test
    void recommendByItemWithTimeDecay() {
        mysqlDataSourceService.recommendByItem(1L, 1L,10).forEach(System.out::println);
        System.out.println("====================================");
        mysqlDataSourceService.recommendByItemWithTimeDecay(1L, 1L,10).forEach(System.out::println);
    }

    @Test
    void recommendByUserWithUncenteredCosine() {
        mysqlDataSourceService.recommendByUserWithUncenteredCosine(1L,10).forEach(System.out::println);
    }

    @Test
    void recommendByUserWithPearson() {
        System.out.println(vlogMapper.selectTimeById(1L));
    }
}
