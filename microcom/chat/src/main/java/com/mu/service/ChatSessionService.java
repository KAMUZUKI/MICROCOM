package com.mu.service;


import com.mu.entity.Message;
import com.mu.entity.User;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-06-14
 */
public interface ChatSessionService {
    /**
     * 加入聊天列表
     *
     * @param user
     * @return
     */
    void join(User user);

    /**
     * 将用户添加至redis中
     *
     * @param user
     */
    void recordUser(User user);

    /**
     * 根据ID从Redis中查询数据
     *
     * @param id
     * @return User对象
     */
    User findById(String id);

    /**
     * 推送消息，储存到Redis数据库中
     *
     * @param fromId  推送方ID
     * @param toId    接收方ID
     * @param message 消息
     */
    void pushMessage(String fromId, String toId, String message);

    /**
     * 获取在线用户列表
     *
     * @return
     */
    List<User> onlineList();

    List<User> getOnlineList();

    /**
     * 获取公共消息内容 -- 群组
     *
     * @return
     */
    List<Message> commonList();

    /**
     * 获取该用户与指定窗口的推送消息
     *
     * @param fromId 推送方ID
     * @param toId   接收方ID
     * @return
     */
    List<Message> selfList(String fromId, String toId);

    /**
     * 删除指定ID在Redis中储存的数据
     *
     * @param id
     */
    Long delete(String id);
}
