package com.mu.service;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;

/**
 * @author MUZUKI
 * @Classname AbstractRedisService
 * @Description TODO
 * @Date 2023/5/26 20:27
 */

public abstract class AbstractRedisService {

    public List<Map.Entry<Object, Object>> getHashEntriesByPage(RedisTemplate redisTemplate,String key, int pageSize, int pageNum) {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.scanOptions().count(pageSize).build());
        int count = 0;
        int startIndex = (pageNum - 1) * pageSize;
        List<Map.Entry<Object, Object>> result = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            if (count >= startIndex && count < startIndex + pageSize) {
                result.add(entry);
            } else if (count >= startIndex + pageSize) {
                break;
            }
            count++;
        }
        return result;
    }

    public Set<ZSetOperations.TypedTuple<Object>> getMembersByPage(RedisTemplate redisTemplate,String key, int pageSize, int pageNum) {
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = redisTemplate.opsForZSet().scan(key, ScanOptions.scanOptions().count(pageSize).build());
        int count = 0;
        int start = (pageNum - 1) * pageSize;
        int end = start + pageSize - 1;
        Set<ZSetOperations.TypedTuple<Object>> result = new LinkedHashSet<>();
        while (cursor.hasNext()) {
            ZSetOperations.TypedTuple<Object> item = cursor.next();
            if (count >= start && count <= end) {
                result.add(item);
            } else if (count > end) {
                break;
            }
            count++;
        }
        return result;
    }
}
