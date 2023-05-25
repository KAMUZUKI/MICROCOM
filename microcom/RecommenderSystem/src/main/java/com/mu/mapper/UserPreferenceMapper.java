package com.mu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mu.entity.UserAction;

import java.util.List;

/**
 * @author MUZUKI
 * @Classname USerArticleMapper
 * @Description TODO
 * @Date 2023/5/22 21:27
 */

public interface UserPreferenceMapper extends BaseMapper<UserAction>{
    /**
     * 获取所有用户的评分数据
     * @return List<UserAction> 用户评分数据列表
     */
    List<UserAction> getAllUserPreference();

    /**
     * 根据用户id和文章id获取用户评分数据
     * @param userId 用户id
     * @param vlogId 文章id
     * @return UserAction 用户评分数据
     */
    UserAction getUserActionByUserIdAndVlogId(Integer userId, Integer vlogId);

    /**
     * 更新用户评分数据
     * @param userAction 用户评分数据
     * @return int 更新结果
     */
    int updateByUserIdAndVlogId(UserAction userAction);

    /**
     * 根据用户id获取用户对文章的列表
     * @param userId 用户id
     * @return List<Integer> 文章id列表
     */
    List<Long> getIidListByUserId(Long userId);

    /**
     * 获取所有用户id
     * @return List<Long> 用户id列表
     */
    List<Long> getUidList();
}

