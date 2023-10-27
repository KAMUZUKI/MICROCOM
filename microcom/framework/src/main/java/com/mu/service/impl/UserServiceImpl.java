package com.mu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mu.constant.Constants;
import com.mu.constant.UserConstant;
import com.mu.entity.Article;
import com.mu.entity.SimpleUser;
import com.mu.entity.User;
import com.mu.mapper.UserMapper;
import com.mu.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author MUZUKI
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-03-05 19:20:49
 */
@Service("userServiceImpl")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 用户登录
     *
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
     *
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
    public IPage getAllUser(Map<String,String> params) {
        int currentPage = Integer.parseInt(params.get("currentPage"));
        int pageSize = Integer.parseInt(params.get("pageSize"));
        Page<User> page = new Page<>(currentPage, pageSize);;
        return userMapper.selectPage(page,null);
    }

    /**
     * 搜索用户
     */
    public List<User> searchUSer(Map<String,String> params) {
        return userMapper.searchUser(params);
    }

    /**
     * 获取用户角色
     */
    public String getUserRole(String id) {
        return userMapper.selectById(id).getType();
    }

    /**
     * 通过id修改用户
     */
    public int updateUserById(User user) {
        return userMapper.updateById(user);
    }

    /**
     * 通过id修改用户状态
     */
    public int updateUserStatus(String userId, String status) {
        return userMapper.updateStatusById(userId, status);
    }

    /**
     * 通过id获取用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    public User getUserById(String userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 获取用户id列表
     * @return 用户id列表
     */
    public List<Long> getUidList() {
        return userMapper.getUidList();
    }

    /**
     * 获取用户喜欢的文章列表
     *
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
    public boolean isFollow(String userId, String targetUserId) {
        Double score = redisTemplate.opsForZSet().score(UserConstant.FOLLOWING + userId, targetUserId);
        return score != null;
    }

    @Override
    public boolean unfollow(String userId, String targetUserId) {
        Long res1 = redisTemplate.opsForZSet().remove(UserConstant.FOLLOWING + userId, targetUserId);
        Long res2 = redisTemplate.opsForZSet().remove(UserConstant.FOLLOWERS + targetUserId, userId);
        return (res1 + res2) == 2;
    }

    @Override
    public List<SimpleUser> getFollowers(String userId) {
        String key = UserConstant.FOLLOWERS + userId;
        Set<Object> followerIds = redisTemplate.opsForZSet().range(key, 0, -1);
        assert followerIds != null;
        return parseToList(followerIds);
    }

    @Override
    public List<SimpleUser> getFollowing(String userId) {
        String key = UserConstant.FOLLOWING + userId;
        Set<Object> followingIds = redisTemplate.opsForZSet().range(key, 0, -1);
        assert followingIds != null;
        return parseToList(followingIds);
    }

    @Override
    public List<SimpleUser> getInterconnections(String userId) {
        String followerKey = UserConstant.FOLLOWERS + userId;
        String followingKey = UserConstant.FOLLOWING + userId;
        // 计算两个有序集合的交集并获取结果
        Set<Object> interconnectionsId = redisTemplate.opsForZSet().intersect(followerKey, followingKey);
        assert interconnectionsId != null;
        return parseToList(interconnectionsId);
    }

    private List<SimpleUser> parseToList(Set<Object> set){
        List<SimpleUser> list = new ArrayList<>();
        for (Object followerId : set) {
            double score = Double.parseDouble(followerId.toString());
            Set<Object> objs = redisTemplate.opsForZSet().rangeByScore(UserConstant.USER_PREFIX, score, score);
            if (objs == null || objs.isEmpty()) {
                continue;
            }
            list.add((JSON.parseObject(objs.iterator().next().toString(), SimpleUser.class)));
        }
        return list;
    }

}




