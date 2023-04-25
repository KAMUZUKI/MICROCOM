package com.mu.utils;

import com.alibaba.fastjson.JSONObject;
import com.mu.entity.Message;
import com.mu.entity.User;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author tycoding
 * @date 2019-06-15
 */
public class CoreUtil {

    /**
     * 对List集合中的数据按照时间顺序排序
     *
     * @param list List<Message>
     */
    public static void sort(List<Message> list) {
        list.sort(Comparator.comparing(Message::getTime));
    }

    /**
     * format date
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static List<User> mapToList(Map<Object, Object> onlineUsers, Class<User> userClass) {
        List<User> list = new ArrayList<>();
        onlineUsers.forEach((k, v) -> {
            list.add(JSONObject.parseObject(v.toString(), userClass));
        });
        return list;
    }
}
