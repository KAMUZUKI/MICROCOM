package com.mu.service;

import com.mu.entity.Vlog;
import com.mu.service.impl.VlogRedisServiceImpl;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author MUZUKI
 * @Classname VlogRedisServiceTest
 * @Description TODO
 * @Date 2023/5/26 20:35
 */

@SpringBootTest
public class VlogRedisServiceTest {
    @Autowired(required = false)
    private VlogRedisServiceImpl vlogRedisService;

    @Test
    void test(){
        val vlogList = vlogRedisService.findByVlogId(1L, 8,1);
        for (int i = 0; i < vlogList.size(); i++) {
            System.out.println(vlogList.get(i));
        }
        System.out.println("=======================");
        save(vlogList);
    }

    public void save(List<Vlog> vlogList){
        vlogList.forEach(item -> System.out.println(item));
    }
}
