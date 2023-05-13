package com.mu.controller;

/**
 * @author MUZUKI
 * @Classname as
 * @Description TODO
 * @Date 2023/3/29 22:09
 */

import com.mu.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search/")
public class SearchHistoryController {

    @Autowired
    RedisService redisService;


    @GetMapping("add")
    public String addSearchHistoryByUserId(String userId, String searchKey) {
        redisService.addSearchHistoryByUserId(userId, searchKey);
        redisService.incrementScore(searchKey);
        return null;
    }

    /**
     * 删除个人历史数据
     */
    @GetMapping("del")
    public Long delSearchHistoryByUserId(String userId, String searchKey) {
        return redisService.delSearchHistoryByUserId(userId, searchKey);
    }

    /**
     * 获取个人历史数据列表
     */
    @GetMapping("getUser")
    public List<String> getSearchHistoryByUserId(String userId) {
        return redisService.getSearchHistoryByUserId(userId);
    }



    /**
     * 根据searchKey搜索其相关最热的前十名 (如果searchKey为null空，则返回redis存储的前十最热词条)
     */
    @GetMapping("getHot")
    public List<String> getHotList(String searchKey) {
        return redisService.getHotList(searchKey);
    }

}
