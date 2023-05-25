package com.mu.service;

import com.mu.entity.Vlog;
import com.mu.mapper.UserMapper;
import com.mu.mapper.VlogMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author MUZUKI
 * @Classname RecommendToRedisServiceTest
 * @Description TODO
 * @Date 2023/5/25 21:26
 */

@SpringBootTest
public class RecommendToRedisServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private VlogMapper vlogMapper;

    @Test
    public void getVlogListIds() {
        //从nacos中获取服务地址
        ServiceInstance service = discoveryClient.getInstances("recommend").get(0);
        String url = service.getHost() + ":" + service.getPort();
        List<Long> items = restTemplate.getForObject("http://"+url+"/recommend/batchRecommend/1/100", List.class);
        List<Vlog> vlogWithList = vlogMapper.findVlogWithList(items);
        vlogWithList.forEach(System.out::println);
    }
}
