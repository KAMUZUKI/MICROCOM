package com.mu.controller;

import com.mu.service.impl.BatchRecommendServiceImpl;
import com.mu.service.impl.RecommendServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author MUZUKI
 * @Classname RecommendConroller
 * @Description TODO
 * @Date 2023/5/22 11:14
 */

@RestController
@RequestMapping("/recommend")
public class RecommendController {
    @Autowired
    private RecommendServiceImpl recommendService;

    @Autowired
    private BatchRecommendServiceImpl batchRecommendService;

    @RequestMapping("/user/{userId}/{size}")
    public List<Long> recommendByUser(@PathVariable("userId") Long userId,@PathVariable("size") Integer size) throws Exception{
        return recommendService.recommendByUser(userId,size);
    }

    @RequestMapping("/item/{userId}/{itemId}/{size}")
    public List<Long> recommendByItem(@PathVariable("userId") Long userId,@PathVariable("itemId") Long itemId,@PathVariable("size") Integer size) throws Exception{
        return recommendService.recommendByItem(userId,itemId,size);
    }

    @RequestMapping("/uncounteredCosine/{userId}/{size}")
    public List<Long> recommendByUserWithUncenteredCosine(@PathVariable("userId") Long userId,@PathVariable("size") Integer size) throws Exception {
        return recommendService.recommendByUserWithUncenteredCosine(userId,size);
    }

    @RequestMapping("/batchRecommend/{userId}/{size}")
    public List<Long> batchRecommend(@PathVariable("userId") Long userId, @PathVariable("size") Integer size) {
        return batchRecommendService.recommendByItemList(userId,size);
    }
}
