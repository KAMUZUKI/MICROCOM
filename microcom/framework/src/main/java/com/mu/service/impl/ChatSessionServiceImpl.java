package com.mu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mu.constant.CommonConstant;
import com.mu.entity.Message;
import com.mu.entity.SimpleUser;
import com.mu.service.ChatSessionService;
import com.mu.utils.CoreUtils;
import com.mu.service.ChatSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author tycoding
 * @date 2019-06-14
 */
@Slf4j
@Service
public class ChatSessionServiceImpl implements ChatSessionService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void join(SimpleUser user) {
        redisTemplate.opsForHash().put(CommonConstant.CHAT_ONLINE, String.valueOf(user.getId()), JSON.toJSONString(user));
    }

    @Override
    public void recordUser(SimpleUser user) {
        stringRedisTemplate.boundValueOps(CommonConstant.USER_PREFIX + user.getId()).set(JSON.toJSONString(user));
    }

    @Override
    public SimpleUser findById(String id) {
        if (id != null) {
            Object value = null;
            if (id.startsWith(CommonConstant.USER_PREFIX)) {
                value = stringRedisTemplate.boundValueOps(CommonConstant.USER_PREFIX + id).get();
            } else {
                value = stringRedisTemplate.boundValueOps(CommonConstant.USER_PREFIX + id).get();
            }
            return JSONObject.parseObject(value.toString(), SimpleUser.class);
        }
        return null;
    }

    @Override
    public void pushMessage(String fromId, String toId, String message) {
        Message entity = new Message();
        entity.setMessage(message);
        entity.setFrom(this.findById(fromId));
        entity.setTime(CoreUtils.format(new Date()));
        if (toId != null) {
            //查询接收方信息
            entity.setTo(this.findById(toId));
            //单个用户推送
            push(entity, CommonConstant.CHAT_FROM_PREFIX + fromId + CommonConstant.CHAT_TO_PREFIX + toId);
        } else {
            //公共消息 -- 群组
            entity.setTo(null);
            push(entity, CommonConstant.CHAT_COMMON_PREFIX + fromId);
        }
    }

    /**
     * 推送消息
     *
     * @param entity Session value
     * @param key    Session key
     */
    private void push(Message entity, String key) {
        //这里按照 PREFIX_ID 格式，作为KEY储存消息记录
        //但一个用户可能推送很多消息，VALUE应该是数组
        List<Message> list = new ArrayList<>();
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (value == null) {
            //第一次推送消息
            list.add(entity);
        } else {
            //第n次推送消息
            list = Objects.requireNonNull(JSONObject.parseArray(value)).toJavaList(Message.class);
            list.add(entity);
        }
        stringRedisTemplate.boundValueOps(key).set(JSONObject.toJSONString(list));
    }

    @Override
    public List<SimpleUser> onlineList() {
        List<SimpleUser> list = new ArrayList<>();
        Set<String> keys = stringRedisTemplate.keys(CommonConstant.USER_PREFIX + CommonConstant.REDIS_MATCH_PREFIX);
        if (keys != null && keys.size() > 0) {
            keys.forEach(key -> {
                list.add(this.findById(key));
            });
        }
        return list;
    }

    @Override
    public List<SimpleUser> getOnlineList() {
        // 获取在线用户
        Map<Object, Object> onlineUsers = redisTemplate.opsForHash().entries(CommonConstant.CHAT_ONLINE);
        return CoreUtils.mapToList(onlineUsers, SimpleUser.class);
    }

    @Override
    public List<Message> commonList() {
        List<Message> list = new ArrayList<>();
        Set<String> keys = stringRedisTemplate.keys(CommonConstant.CHAT_COMMON_PREFIX + CommonConstant.REDIS_MATCH_PREFIX);
        if (keys != null && keys.size() > 0) {
            keys.forEach(key -> {
                String value = stringRedisTemplate.boundValueOps(key).get();
                List<Message> messageList = Objects.requireNonNull(JSONObject.parseArray(value)).toJavaList(Message.class);
                list.addAll(messageList);
            });
        }
        CoreUtils.sort(list);
        return list;
    }

    @Override
    public List<Message> selfList(String fromId, String toId) {
        List<Message> list = new ArrayList<>();
        //A -> B
        String fromTo = stringRedisTemplate.boundValueOps(CommonConstant.CHAT_FROM_PREFIX + fromId + CommonConstant.CHAT_TO_PREFIX + toId).get();
        //B -> A
        String toFrom = stringRedisTemplate.boundValueOps(CommonConstant.CHAT_FROM_PREFIX + toId + CommonConstant.CHAT_TO_PREFIX + fromId).get();

        JSONArray fromToObject = JSONObject.parseArray(fromTo);
        JSONArray toFromObject = JSONObject.parseArray(toFrom);
        if (fromToObject != null) {
            list.addAll(fromToObject.toJavaList(Message.class));
        }
        if (toFromObject != null) {
            list.addAll(toFromObject.toJavaList(Message.class));
        }

        if (list.size() > 0) {
            CoreUtils.sort(list);
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Long delete(String id) {
        if (id != null) {
            log.info("从在线列表中移除用户id : " + id);
            return redisTemplate.opsForHash().delete(CommonConstant.CHAT_ONLINE, id);
        }
        return 0L;
    }
}
