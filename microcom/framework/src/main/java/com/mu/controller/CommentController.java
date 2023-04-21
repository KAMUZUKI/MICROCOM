package com.mu.controller;

import cn.hutool.extra.servlet.ServletUtil;
import com.mu.SensitiveWord.SensitiveFilter;
import com.mu.domain.Comment;
import com.mu.model.JsonModel;
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


    @RequestMapping(value = "/addComment")
    public JsonModel addComment(HttpServletRequest request) {
        Comment comment = ServletUtil.toBean(request, Comment.class,false);
        JsonModel jm = new JsonModel();
        String searchkey = comment.getContent();

        //非法敏感词汇判断
        int n = sensitiveFilter.CheckSensitiveWord(searchkey,0,1);
        if(n > 0){
            //存在非法字符
            return jm.setCode(0).setMsg("存在非法字符");
        }
        jm.setCode(1).setData(commentService.addComment(comment));
        return jm;
    }

    @RequestMapping(value = "/getComments")
    public JsonModel getComments(HttpServletRequest request) {
        int articleId = Integer.parseInt(request.getParameter("articleId"));
        JsonModel jm = new JsonModel();
        jm.setCode(1).setData(commentService.getComments(articleId));
        return jm;
    }
}
