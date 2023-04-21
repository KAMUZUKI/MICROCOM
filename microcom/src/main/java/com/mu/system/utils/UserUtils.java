package com.mu.system.utils;

import com.alibaba.fastjson.JSON;
import com.mu.framework.domain.DingtalkUser;

import java.util.Map;

/**
 * @author : MUZUKI
 * @date : 2023-03-27 15:02
 **/

public class UserUtils {
    public static DingtalkUser parseDingtalkUser(String user) {
        String[] split = user.split(",");
        Map<String,String> map = JSON.parseObject(split[2], Map.class);
        DingtalkUser dingtalkUser = new DingtalkUser();
        dingtalkUser.setUsername(split[0]).setUuid(split[1]);
        return dingtalkUser;
    }
}
