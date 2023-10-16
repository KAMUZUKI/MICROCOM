package com.mu.controller;

import com.mu.entity.EvaluationConstants;
import com.mu.service.impl.UserActionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MUZUKI
 * @Classname UserActionController
 * @Description 用户行为计分控制器
 * @Date 2023/5/24 14:07
 */

@RestController
@RequestMapping("/userAction")
public class UserActionController {
    @Autowired(required = false)
    private UserActionServiceImpl userActionService;

    @RequestMapping("/addVlogComment/{userId}/{vlogId}")
    public void addVlogComment(@PathVariable("userId") Integer userId,@PathVariable("vlogId") Integer vlogId) {
        userActionService.evaluationScore(userId,vlogId, EvaluationConstants.LEVEL3);
    }

    @RequestMapping("/checkOutVlogComment/{userId}/{vlogId}")
    public void checkOutVlogComment(@PathVariable("userId") Integer userId,@PathVariable("vlogId") Integer vlogId) {
        userActionService.evaluationScore(userId,vlogId, EvaluationConstants.LEVEL2);
    }

    @RequestMapping("/checkOutVlog/{userId}/{vlogId}")
    public void checkOutVlog(@PathVariable("userId") Integer userId,@PathVariable("vlogId") Integer vlogId) {
        userActionService.evaluationScore(userId,vlogId, EvaluationConstants.LEVEL1);
    }
}
