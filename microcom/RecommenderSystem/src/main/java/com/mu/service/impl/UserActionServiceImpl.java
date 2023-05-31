package com.mu.service.impl;

import com.mu.entity.UserAction;
import com.mu.mapper.UserPreferenceMapper;
import com.mu.service.UserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author MUZUKI
 * @Classname UserActionServiceImpl
 * @Description TODO
 * @Date 2023/5/24 14:08
 */

@Service
public class UserActionServiceImpl implements UserActionService {
    @Autowired(required = false)
    private UserPreferenceMapper userArticleMapper;

    @Override
    public void evaluationScore(Integer userId, Integer vlogId,Double level) {
        UserAction userAction = userArticleMapper.getUserActionByUserIdAndVlogId(userId,vlogId);
        if (userAction!=null) {
            double maxScore = 10.0;
            // 已经存在数据,更新评分,评分最高为10
            if (Double.compare(userAction.getValue()+level,maxScore)>0) {
                // 评分超过10，设置为10
                userAction.setValue(10.0);
            }else {
                userAction.setValue(userAction.getValue()+level);
            }
            // 更新时间
            userAction.setTime(System.currentTimeMillis());
            userArticleMapper.updateByUserIdAndVlogId(userAction);
        } else {
            // 不存在数据，插入数据
            userArticleMapper.insert(new UserAction(userId,vlogId,level,System.currentTimeMillis()));
        }
    }
}
