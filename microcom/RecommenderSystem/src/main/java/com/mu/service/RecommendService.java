package com.mu.service;

import org.apache.mahout.cf.taste.common.TasteException;

import java.util.List;

/**
 * @author MUZUKI
 * @Classname RecommendService
 * @Description 使用mybatis查出数据后，创建模型
 * @Date 2023/5/22 11:14
 */

public interface RecommendService {
    /**
     * 基于用户的协同过滤算法
     * @param userId 用户ID
     * @param size 推荐数量
     * @return 推荐列表
     * @throws Exception 异常
     */
    List<Long> recommendByUser (Long userId, Integer size) throws Exception;

    /**
     * 基于物品的协同过滤算法
     * @param userId 用户ID
     * @param itemId 商品ID
     * @param size 推荐数量
     * @return 推荐列表
     * @throws Exception 异常
     */
    List<Long> recommendByItem (Long userId,Long itemId,Integer size) throws Exception;

    /**
     * 基于用户的协同过滤算法,基于余弦相似度
     * @param userId
     * @return 推荐列表
     * @throws TasteException
     */
    List<Long> recommendByUserWithUncenteredCosine( Long userId,Integer size) throws Exception;
}
