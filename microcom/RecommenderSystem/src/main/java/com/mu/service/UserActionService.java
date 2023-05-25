package com.mu.service;

/**
 * @author MUZUKI
 * @Classname UserActionService
 * @Description 用户行为服务
 * @Date 2023/5/24 14:08
 */

public interface UserActionService {
    /**
     * 评估分数，通过用户的行为评估用户对于某个vlog的喜好程度
     * @param userId 用户ID
     * @param vlogId vlogId
     * @param level 评估分数
     */
    void evaluationScore(Integer userId,Integer vlogId,Double level);
}
