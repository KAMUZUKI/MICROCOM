package com.mu.service;

import com.mu.utils.RedisKeyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author MUZUKI
 * @Classname RedisServiceImpl
 * @Description 热点的搜索词条服务
 * @Date 2023/3/28 23:33
 */

@Transactional
@Service("redisService")
public class RedisServiceImpl implements RedisService {
    @Resource
    private StringRedisTemplate redisSearchTemplate;


    /**
     * searchkey 代表输入的关键词
     * 新增一条该userid用户在搜索栏的历史记录
     */
    @Override
    public int addSearchHistoryByUserId(String userid, String searchkey) {
        String shistory = RedisKeyUtils.getSearchHistoryKey(userid);
        boolean b = redisSearchTemplate.hasKey(shistory);
        if (b) {
            Object hk = redisSearchTemplate.opsForHash().get(shistory, searchkey);
            if (hk != null) {
                return 1;
            }else{
                redisSearchTemplate.opsForHash().put(shistory, searchkey, "1");
            }
        }else{
            redisSearchTemplate.opsForHash().put(shistory, searchkey, "1");
        }
        return 1;
    }

    /**
     * 删除个人历史数据
     * @param userid 代表用户id
     * @param searchkey 代表输入的关键词
     * @return
     */
    @Override
    public Long delSearchHistoryByUserId(String userid, String searchkey) {
        String shistory = RedisKeyUtils.getSearchHistoryKey(userid);
        return redisSearchTemplate.opsForHash().delete(shistory, searchkey);
    }

    /**
     * 获取个人历史数据列表
     * @param userid 代表用户id
     * @return
     */
    @Override
    public List<String> getSearchHistoryByUserId(String userid) {
        List<String> stringList = new ArrayList<>();
        String shistory = RedisKeyUtils.getSearchHistoryKey(userid);
        boolean b = redisSearchTemplate.hasKey(shistory);
        if(b){
            Cursor<Map.Entry<Object, Object>> cursor = redisSearchTemplate.opsForHash().scan(shistory, ScanOptions.NONE);
            while (cursor.hasNext()) {
                Map.Entry<Object, Object> map = cursor.next();
                String key = map.getKey().toString();
                stringList.add(key);
            }
            return stringList;
        }
        return null;
    }

    /**
     * 新增一条热词搜索记录，将用户输入的热词存储下来
     * @param searchkey 代表输入的关键词
     * @return
     */
    @Override
    public int incrementScoreByUserId(String searchkey) {
        Long now = System.currentTimeMillis();
        ZSetOperations zSetOperations = redisSearchTemplate.opsForZSet();
        ValueOperations<String, String> valueOperations = redisSearchTemplate.opsForValue();
        List<String> title = new ArrayList<>();
        title.add(searchkey);
        for (int i = 0, lengh = title.size(); i < lengh; i++) {
            String tle = title.get(i);
            try {
                if (zSetOperations.score("title", tle) <= 0) {
                    zSetOperations.add("title", tle, 0);
                    valueOperations.set(tle, String.valueOf(now));
                }
            } catch (Exception e) {
                zSetOperations.add("title", tle, 0);
                valueOperations.set(tle, String.valueOf(now));
            }
        }
        return 1;
    }

    /**
     * 根据searchkey搜索其相关最热的前十名 (如果searchkey为null空，则返回redis存储的前十最热词条)
     * @param searchkey 代表输入的关键词
     * return 返回相关最热的前十名
     * @return
     */
    @Override
    public List<String> getHotList(String searchkey) {
        String key = searchkey;
        Long now = System.currentTimeMillis();
        List<String> result = new ArrayList<>();
        ZSetOperations zSetOperations = redisSearchTemplate.opsForZSet();
        ValueOperations<String, String> valueOperations = redisSearchTemplate.opsForValue();
        Set<String> value = zSetOperations.reverseRangeByScore("title", 0, Double.MAX_VALUE);
        //key不为空的时候 推荐相关的最热前十名
        if(StringUtils.isNotEmpty(searchkey)){
            for (String val : value) {
                if (StringUtils.containsIgnoreCase(val, key)) {
                    if (result.size() > 9) {
                        //只返回最热的前十名
                        break;
                    }
                    Long time = Long.valueOf(valueOperations.get(val));
                    if ((now - time) < 2592000000L) {
                        //返回最近一个月的数据
                        result.add(val);
                    } else {
                        //时间超过一个月没搜索就把这个词热度归0
                        zSetOperations.add("title", val, 0);
                    }
                }
            }
        }else{
            for (String val : value) {
                if (result.size() > 9) {//只返回最热的前十名
                    break;
                }
                Long time = Long.valueOf(valueOperations.get(val));
                if ((now - time) < 2592000000L) {//返回最近一个月的数据
                    result.add(val);
                } else {//时间超过一个月没搜索就把这个词热度归0
                    zSetOperations.add("title", val, 0);
                }
            }
        }
        return result;
    }

    /**
     * 每次点击给相关词searchkey热度 +1
     * @param searchkey
     * @return
     */
    @Override
    public int incrementScore(String searchkey) {
        String key = searchkey;
        Long now = System.currentTimeMillis();
        ZSetOperations zSetOperations = redisSearchTemplate.opsForZSet();
        ValueOperations<String, String> valueOperations = redisSearchTemplate.opsForValue();
        zSetOperations.incrementScore("title", key, 1);
        valueOperations.getAndSet(key, String.valueOf(now));
        return 1;
    }
}