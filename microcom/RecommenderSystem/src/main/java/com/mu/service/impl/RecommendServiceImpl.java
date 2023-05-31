package com.mu.service.impl;

import com.mu.entity.UserAction;
import com.mu.mapper.UserPreferenceMapper;
import com.mu.mapper.VlogMapper;
import com.mu.service.AbstractRecommendService;
import com.mu.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MUZUKI
 * @Classname RecommendServiceImpl
 * @Description 使用mybatis查出数据后，创建模型
 * @Date 2023/5/22 11:15
 */

@Slf4j
@Service
public class RecommendServiceImpl extends AbstractRecommendService implements RecommendService {

    @Autowired(required = false)
    private UserPreferenceMapper userPreferenceMapper;

    @Override
    public List<Long> recommendByUser (Long userId,Integer size) {
        List<UserAction> userList = userPreferenceMapper.getAllUserPreference();
        //创建数据模型
        DataModel model = this.createDataModel(userList);
        return this.absRecommendByUser(model, userId, size);
    }

    @Override
    public List<Long> recommendByItem (Long userId,Long itemId,Integer size) {
        List<UserAction> userList = userPreferenceMapper.getAllUserPreference();
        //创建数据模型
        DataModel model = this.createDataModel(userList);
        return this.absRecommendByItem(model, userId, itemId, size);
    }

    @Override
    public List<Long> recommendByUserWithUncenteredCosine(Long userId,Integer size) {
        List<UserAction> userList = userPreferenceMapper.getAllUserPreference();
        //创建数据模型
        DataModel dataModel = this.createDataModel(userList);
        return this.absRecommendByUserWithUncenteredCosine(dataModel, userId, size);
    }
}
