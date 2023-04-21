package com.mu.controller;

import cn.hutool.extra.servlet.ServletUtil;
import com.mu.domain.Article;
import com.mu.model.JsonModel;
import com.mu.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MUZUKI
 * @Classname ArticleController
 * @Description TODO
 * @Date 2023/3/26 1:49
 */

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleServiceImpl articleService;

    @RequestMapping(value = "/addArticle")
    public JsonModel addArticle(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        JsonModel jm = new JsonModel();
        Article article = ServletUtil.toBean(request, Article.class,true);
        try {
            jm.setCode(articleService.addArticle(article));
        }catch (Exception e){
            jm.setCode(0);
            jm.setMsg("添加文章失败");
        }
        return jm;
    }

    @RequestMapping(value = "/deleteArticle")
    public JsonModel deleteArticle(HttpServletRequest request) {
        JsonModel jm = new JsonModel();
        int articleId = Integer.parseInt(request.getParameter("articleId"));
        jm.setCode(articleService.deleteArticle(articleId));
        return jm;
    }

    @RequestMapping(value = "/alterArticle")
    public JsonModel alterArticle(HttpServletRequest request) {
        JsonModel jm = new JsonModel();
        Article article = ServletUtil.toBean(request, Article.class, true);;
        jm.setCode(articleService.alterArticle(article));
        return jm;
    }

    @RequestMapping(value = "/getAllArticle")
    public JsonModel getAllArticle() {
        JsonModel jm = new JsonModel();
        jm.setCode(1).setData(articleService.getAllArticle());
        return jm;
    }

    @RequestMapping(value = "/getArticleById")
    public JsonModel getArticleById(HttpServletRequest request) {
        JsonModel jm = new JsonModel();
        int articleId = Integer.parseInt(request.getParameter("articleId"));
        jm.setCode(1).setData(articleService.getArticleById(articleId));
        return jm;
    }

    @RequestMapping(value = "/getArticleTop")
    public JsonModel getArticleTop() {
        JsonModel jm = new JsonModel();
        jm.setCode(1).setData(articleService.getArticleTop());
        return jm;
    }

    @RequestMapping(value = "/getAllTags")
    public JsonModel getAllTags() {
        JsonModel jm = new JsonModel();
        jm.setCode(1).setData(articleService.getAllTags());
        return jm;
    }

    @RequestMapping(value = "/getByCategory")
    public JsonModel getArticleByCategory(HttpServletRequest request) {
        JsonModel jm = new JsonModel();
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        jm.setCode(1).setData(articleService.getArticleByCategory(categoryId));
        return jm;
    }

    @RequestMapping(value = "/changeData")
    public JsonModel changeData(HttpServletRequest request) {
        JsonModel jm = new JsonModel();
        String articleId = request.getParameter("articleId");
        String userId = request.getParameter("userId");
        if(articleService.changeData(articleId,userId)){
            jm.setCode(1).setMsg("点赞成功");
        }else {
            jm.setCode(0).setMsg("点赞失败 等待检测网络重试");
        }
        return jm;
    }
}
