package com.mu.service;

import java.util.List;

/**
 * @author MUZUKI
 * @Classname RedisService
 * @Description TODO
 * @Date 2023/3/28 23:33
 */

public interface RedisService {

    /**
     * 新增一条该userid用户在搜索栏的历史记录
     * @param userid 代表用户id
     * @param searchkey 代表输入的关键词
     * @return
     */
    int addSearchHistoryByUserId(String userid, String searchkey);

    /**
     * 删除个人历史数据
     * @param userid 代表用户id
     * @param searchkey 代表输入的关键词
     * @return 删除的个数
     */
    Long delSearchHistoryByUserId(String userid, String searchkey);

    /**
     * 获取个人历史数据列表
     * @param userid 代表用户id
     * @return 个人历史数据列表
     */
    List<String> getSearchHistoryByUserId(String userid);

    /**
     * 新增一条热词搜索记录，将用户输入的热词存储下来
     * @param searchkey 代表输入的关键词
     * @return
     */

    int incrementScoreByUserId(String searchkey);

    /**
     * 根据searchkey搜索其相关最热的前十名 (如果searchkey为null空，则返回redis存储的前十最热词条)
     * @param searchkey 代表输入的关键词
     * return 返回相关最热的前十名
     */
    List<String> getHotList(String searchkey);

    /**
     * 每次点击给相关词searchkey热度 +1
     * @param searchkey
     * @return
     */
    int incrementScore(String searchkey);
}
