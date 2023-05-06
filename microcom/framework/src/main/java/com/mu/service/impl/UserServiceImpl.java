package com.mu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mu.constant.Constants;
import com.mu.constant.UserConstant;
import com.mu.domain.SimpleUser;
import com.mu.domain.User;
import com.mu.mapper.UserMapper;
import com.mu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author MUZUKI
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-03-05 19:20:49
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 用户登录
     * @param account 用户信息 password 用户密码
     * @return 用户信息
     */
    public User login(String account, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        queryWrapper.eq("pwd", password);
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 用户注册
     * @param user 用户信息
     * @return 用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public int register(User user) {
        redisTemplate.opsForZSet().add(UserConstant.USER_PREFIX, user, user.getId());
        return userMapper.insert(user);
    }

    /**
     * 获取全部用户
     * @return 用户集合
     */
    public List<User> getAllUser() {
        return userMapper.selectList(null);
    }

    /**
     * 通过id修改用户
     */
    public int updateUserById(User user) {
        return userMapper.updateById(user);
    }

    /**
     * 获取用户喜欢的文章列表
     * @param id 用户id
     * @return 文章id集合
     */
    public List<Integer> getLikeList(int id) {
        //获取从前台传过来的用户id
        redisTemplate.opsForValue().get(id + Constants.REDIS_USER_PRAISE);
        List<Integer> likeList = new ArrayList<Integer>();
        //TODO: 从redis中获取用户喜欢的文章列表
        return likeList;
    }

    @Override
    public boolean follow(String userId, String targetUserId) {
        boolean res1 = Boolean.TRUE.equals(redisTemplate.opsForZSet().add(UserConstant.FOLLOWING + userId, targetUserId, System.currentTimeMillis()));
        boolean res2 = Boolean.TRUE.equals(redisTemplate.opsForZSet().add(UserConstant.FOLLOWERS + targetUserId, userId, System.currentTimeMillis()));
        return res1 && res2;
    }

    @Override
    public boolean unfollow(String userId, String targetUserId) {
        Long res1 = redisTemplate.opsForZSet().remove(UserConstant.FOLLOWING + userId, targetUserId);
        Long res2 = redisTemplate.opsForZSet().remove(UserConstant.FOLLOWERS + targetUserId, userId);
        return (res1 + res2)==2;
    }

    @Override
    public List<SimpleUser> getFollowers(String userId) {
        List<SimpleUser> followers = new ArrayList<>();
        String key = UserConstant.FOLLOWERS + userId;
        Set<Object> followerIds = redisTemplate.opsForZSet().range(key, 0, -1);
        for (Object followerId : followerIds) {
            Double score = Double.valueOf(followerId.toString());
            Set<Object> objs = redisTemplate.opsForZSet().rangeByScore(UserConstant.USER_PREFIX,score,score);
            if (objs == null) {
                continue;
            }
            followers.add((JSON.parseObject(objs.iterator().next().toString(), SimpleUser.class)));
        }
        return followers;
    }

    @Override
    public List<SimpleUser> getFollowing(String userId) {
        List<SimpleUser> following = new ArrayList<>();
        String key = UserConstant.FOLLOWING + userId;
        Set<Object> followingIds = redisTemplate.opsForZSet().range(key, 0, -1);
        for (Object followingId : followingIds) {
            double score = Double.parseDouble(followingId.toString());
            Set<Object> objs = redisTemplate.opsForZSet().rangeByScore(UserConstant.USER_PREFIX,score,score);
            if (objs == null) {
                continue;
            }
            following.add((JSON.parseObject(objs.iterator().next().toString(), SimpleUser.class)));
        }
        return following;
    }
}




