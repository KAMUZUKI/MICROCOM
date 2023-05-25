package com.mu.cron;

import com.mu.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author MUZUKI
 * @Classname UserPreferencesCron
 * @Description TODO
 * @Date 2023/5/25 11:26
 */

@Slf4j
public class UserPreferencesCron {
    @Autowired
    private UserMapper userMapper;

    public void run() {
        log.info("=========开始同步用户偏好数据=========");
        // 获取所有用户的uid
//        List<Long> uidList = userMapper.getUidList();
//        uidList.forEach(uid -> {
//            // 获取用户偏好数据
//            List<Long> userPreferences = userPreferenceMapper.getIidListByUserId(uid);
//        });

        log.info("=========同步用户偏好数据完成=========");
    }
}
