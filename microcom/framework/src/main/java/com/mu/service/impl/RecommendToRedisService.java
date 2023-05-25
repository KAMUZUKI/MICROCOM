package com.mu.service.impl;

import com.mu.entity.Vlog;
import com.mu.mapper.VlogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author MUZUKI
 * @Classname RecommendToRedisService
 * @Description TODO
 * @Date 2023/5/25 17:07
 */

@Slf4j
@Service
public class RecommendToRedisService {

    private static final String RECOMMEND_PREFIX = "recommend:user";

    @Autowired(required = false)
    private VlogMapper vlogMapper;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void getVlogListIds(Integer userId,Integer size){
        //从nacos中获取服务地址
        ServiceInstance service = discoveryClient.getInstances("recommend").get(0);
        String url = service.getHost() + ":" + service.getPort();
        //通过restTemplate调用
        List<Long> items = restTemplate.getForObject("http://"+url+"/recommend/batchRecommend/" + userId + "/" + size, List.class);
        List<Vlog> vlogWithList = vlogMapper.findVlogWithList(items);
        saveVlog(userId,vlogWithList);
    }

    public void saveVlog(Integer userId, List<Vlog> vlogs) {
        vlogs.forEach(vlog -> {
            redisTemplate.opsForHash().put(RECOMMEND_PREFIX + userId.toString(), vlog.getId().toString(), vlog);
        });
    }
}
