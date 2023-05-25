package com.mu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mu.entity.DingtalkUser;
import com.mu.mapper.DingtalkMapper;
import com.mu.service.DingtalkService;
import org.springframework.stereotype.Service;

/**
* @author MUZUKI
* @description 针对表【dingtalk_user】的数据库操作Service实现
* @createDate 2023-03-27 15:21:25
*/

@Service
public class DingtalkServiceImpl extends ServiceImpl<DingtalkMapper, DingtalkUser> implements DingtalkService {
    /**
     * 添加钉钉用户
     */
    public int addDingtalkUser(DingtalkUser dingtalkUser) {
        dingtalkUser.setStatus("1");
        return baseMapper.insert(dingtalkUser);
    }
}




