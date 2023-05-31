package com.mu.service.impl;

import com.mu.config.FastjsonRedisSerializer;
import com.mu.entity.Vlog;
import com.mu.mapper.VlogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.redis.core.RedisCallback;
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
@Service("recommendToRedisServiceImpl")
public class RecommendToRedisServiceImpl {

    private static final String RECOMMEND_PREFIX = "recommend:user:";

    @Autowired(required = false)
    private VlogMapper vlogMapper;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void freshRedisRecommend(Integer userId,Integer size){
        //从nacos中获取服务地址
        ServiceInstance service = discoveryClient.getInstances("recommend").get(0);
        String url = service.getHost() + ":" + service.getPort();
        //通过restTemplate调用
        List<Long> baseItemList = restTemplate.getForObject("http://"+url+"/recommend/batchRecommend/" + userId + "/" + size, List.class);
        List<Long> baseUserList = restTemplate.getForObject("http://"+url+"/recommend/uncounteredCosine/" + userId + "/100", List.class);
        baseUserList.addAll(baseItemList);
        // 获取用户的推荐列表
        List<Vlog> vlogWithList = vlogMapper.findVlogWithList(baseUserList);
        log.warn("用户ID:{},要求推荐数量为:{},实际推荐数量为:{}",userId,size,vlogWithList.size());
        saveVlog(userId,vlogWithList);
    }

    public void saveVlog(Integer userId, List<Vlog> vlogs) {
        FastjsonRedisSerializer<Vlog> serializer = new FastjsonRedisSerializer<>(Vlog.class);
        redisTemplate.delete(RECOMMEND_PREFIX + userId.toString());
        List<Vlog> temp = vlogs;
//        vlogs.forEach(vlog -> {
//            // 将用户的推荐列表存入redis
//            redisTemplate.opsForHash().put(RECOMMEND_PREFIX + userId, vlog.getId().toString(), vlog);
//        });
        redisTemplate.executePipelined((RedisCallback<Void>) connection -> {
            for (Vlog vlog : vlogs) {
                byte[] key = (RECOMMEND_PREFIX + userId).getBytes();
                byte[] hashKey = vlog.getId().toString().getBytes();
                byte[] value = serializer.serialize(vlog);
                connection.hSet(key, hashKey, value);
            }
            return null;
        });
    }
}
