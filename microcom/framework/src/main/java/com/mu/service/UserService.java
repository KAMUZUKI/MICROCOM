package com.mu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mu.domain.SimpleUser;
import com.mu.domain.User;

import java.util.List;

/**
 * @author MUZUKI
 * @description 针对表【user】的数据库操作Service
 * @createDate 2023-03-05 19:12:56
 */
public interface UserService extends IService<User> {
    /**
     * 关注用户
     *
     * @param userId       用户ID
     * @param followUserId 关注的用户ID
     * @return
     */
    boolean follow(String userId, String followUserId);

    /**
     * 判断用户是否关注了某个用户
     *
     * @param userId       用户ID
     * @param targetUserId 关注的用户ID
     * @return 是否关注
     */
    public boolean isFollow(String userId, String targetUserId);

    /**
     * 取消关注用户
     *
     * @param userId       用户ID
     * @param followUserId 关注的用户ID
     * @return
     */
    boolean unfollow(String userId, String followUserId);

    /**
     * 获取用户的粉丝列表
     *
     * @param userId 用户ID
     * @return 粉丝列表
     */
    List<SimpleUser> getFollowers(String userId);

    /**
     * 获取用户关注的人的列表
     *
     * @param userId 用户ID
     * @return 关注列表
     */
    List<SimpleUser> getFollowing(String userId);
}
