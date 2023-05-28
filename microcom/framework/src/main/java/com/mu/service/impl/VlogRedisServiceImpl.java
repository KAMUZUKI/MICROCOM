package com.mu.service.impl;

import com.alibaba.fastjson.JSON;
import com.mu.entity.Vlog;
import com.mu.service.AbstractRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MUZUKI
 * @Classname VlogRedisServiceImpl
 * @Description TODO
 * @Date 2023/5/26 20:31
 */

@Service
public class VlogRedisServiceImpl extends AbstractRedisService {

    private static final String RECOMMEND_PREFIX = "recommend:user:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public List<Vlog> findByVlogId(Long userId, int pageSize, int pageNum) {
        String hashKey = RECOMMEND_PREFIX + userId;
        List<Vlog> vlogList = new ArrayList<>();
        getHashEntriesByPage(redisTemplate,hashKey, pageSize, pageNum).forEach(entry -> {
            Vlog vlog = JSON.parseObject(entry.getValue().toString(), Vlog.class);
            vlogList.add(vlog);
        });
        return vlogList;
    }
}
