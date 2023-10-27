package com.mu.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.extra.servlet.ServletUtil;
import com.github.houbb.sensitive.word.core.SensitiveWordHelper;
import com.mu.entity.Article;
import com.mu.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

    @SaCheckLogin
    @RequestMapping(value = "/addArticle")
    public SaResult addArticle(@RequestBody Article article) {
        try {
            if (SensitiveWordHelper.contains(article.getContent())){
                return SaResult.error("文章内容包含敏感词汇");
            }
            return SaResult.ok("添加文章成功").setData(articleService.addArticle(article));
        } catch (Exception e) {
            return SaResult.error("添加文章失败" + e.getMessage());
        }
    }

    @SaCheckLogin
    @RequestMapping(value = "/deleteArticle/{articleId}")
    public SaResult deleteArticle(@PathVariable("articleId") int articleId) {
        return SaResult.ok().setCode(articleService.deleteArticle(articleId));
    }

    @SaCheckLogin
    @RequestMapping(value = "/updateArticle")
    public SaResult alterArticle(@RequestBody Article article) {
        return SaResult.ok().setCode(articleService.alterArticle(article));
    }

    @RequestMapping(value = "/getAllArticle")
    public SaResult getAllArticle() {
        return SaResult.ok().setData(articleService.getAllArticle());
    }

    @RequestMapping(value = "/getArticleByPage")
    public SaResult getArticleByPage(@RequestParam Map<String, String> params) {
        return SaResult.ok().setData(articleService.getArticleByPage(params));
    }

    @RequestMapping(value = "/getArticleById/{articleId}")
    public SaResult getArticleById(@PathVariable("articleId") int articleId) {
        return SaResult.ok().setData(articleService.getArticleById(articleId));
    }

    @RequestMapping(value = "/getArticleTop")
    public SaResult getArticleTop() {
        return SaResult.ok().setData(articleService.getArticleTop());
    }

    @RequestMapping(value = "/getAllTags")
    public SaResult getAllTags() {
        return SaResult.ok().setData(articleService.getAllTags());
    }

    @RequestMapping(value = "/getByCategory/{categoryId}")
    public SaResult getArticleByCategory(@PathVariable("categoryId") int categoryId) {
        return SaResult.ok().setData(articleService.getArticleByCategory(categoryId));
    }

    @SaCheckLogin
    @RequestMapping(value = "/userLike/{articleId}/{userId}")
    public SaResult changeData(@PathVariable("articleId") String articleId, @PathVariable("userId") String userId) {
        if (articleService.changeData(articleId, userId)) {
            return SaResult.ok("点赞成功");
        } else {
            return SaResult.error("点赞失败 等待检测网络重试");
        }
    }
}
