package com.mu.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.extra.servlet.ServletUtil;
import com.mu.SensitiveWord.SensitiveFilter;
import com.mu.domain.Comment;
import com.mu.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MUZUKI
 * @Classname Comment
 * @Description TODO
 * @Date 2023/3/26 1:57
 */

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    SensitiveFilter sensitiveFilter;

    @SaCheckLogin
    @RequestMapping(value = "/addComment")
    public SaResult addComment(HttpServletRequest request) {
        Comment comment = ServletUtil.toBean(request, Comment.class, false);
        String searchkey = comment.getContent();
        //非法敏感词汇判断
        int n = sensitiveFilter.CheckSensitiveWord(searchkey, 0, 1);
        if (n > 0) {
            //存在非法字符
            return SaResult.ok("存在非法字符");
        }
        return SaResult.ok("添加成功").setData(commentService.addComment(comment));
    }

    @RequestMapping(value = "/getComments")
    public SaResult getComments(HttpServletRequest request) {
        int articleId = Integer.parseInt(request.getParameter("articleId"));
        return SaResult.ok("获取评论成功").setData(commentService.getComments(articleId));
    }
}
