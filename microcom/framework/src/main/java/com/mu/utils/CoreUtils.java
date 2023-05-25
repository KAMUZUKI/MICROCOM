package com.mu.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mu.constant.VlogConstant;
import com.mu.entity.DingtalkUser;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : MUZUKI
 * @date : 2023-03-27 15:02
 **/

public class CoreUtils {

    public static DingtalkUser parseDingtalkUser(String user) {
        String[] split = user.split(",");
        Map<String,String> map = JSON.parseObject(split[2], Map.class);
        DingtalkUser dingtalkUser = new DingtalkUser();
        dingtalkUser.setUsername(split[0]).setUuid(split[1]);
        return dingtalkUser;
    }

    /**
     * format date
     */
    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String replyKey(Long vlogid,Long parentid) {
       return VlogConstant.REPLY_PREFIX + vlogid.toString() + ":comment_id" + parentid.toString();
    }

    /**
     * map转list
     */
    public static <T> List<T> mapToList(Map<Object, Object> map, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        map.forEach((k, v) -> {
            list.add(JSONObject.parseObject(v.toString(), clazz));
        });
        return list;
    }

    /**
     * set转list
     */
    public static <T> List<T> setToList(Set<Object> set, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        set.forEach((v) -> {
            list.add(JSONObject.parseObject(v.toString(), clazz));
        });
        return list;
    }
}
