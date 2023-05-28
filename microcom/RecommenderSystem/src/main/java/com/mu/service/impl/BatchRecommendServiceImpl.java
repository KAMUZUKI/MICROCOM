package com.mu.service.impl;

import com.mu.entity.UserAction;
import com.mu.mapper.UserPreferenceMapper;
import com.mu.service.AbstractRecommendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author MUZUKI
 * @Classname BatchRecommendServiceImpl
 * @Description 批量推荐，通过用户偏好数据集推荐
 * @Date 2023/5/25 15:51
 */

@Slf4j
@Service
public class BatchRecommendServiceImpl extends AbstractRecommendService{

    @Autowired(required = false)
    private UserPreferenceMapper userPreferenceMapper;

    /**
     * 批量推荐
     * @param userId 用户id
     * @param size 推荐数量
     * @return 推荐Id集合
     */
    public List<Long> recommendByItemList (Long userId, Integer size) {
        List<UserAction> userList = userPreferenceMapper.getAllUserPreference();
        List<Long> itemIdList = userPreferenceMapper.getIidListByUserId(userId);
        //创建数据模型
        DataModel model = this.createDataModel(userList);
        return this.absBatchRecommendByItem(model, userId, itemIdList, size);
    }
}
