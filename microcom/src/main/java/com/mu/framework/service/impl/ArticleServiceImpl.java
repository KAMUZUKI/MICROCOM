package com.mu.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mu.framework.domain.Article;
import com.mu.framework.mapper.ArticleMapper;
import com.mu.framework.service.ArticleService;
import com.mu.framework.utils.Constants;
import com.mu.framework.utils.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import java.util.List;

/**
* @author MUZUKI
* @description 针对表【article】的数据库操作Service实现
* @createDate 2023-03-05 19:20:49
*/

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired(required = false)
    private ArticleMapper articleMapper;

    /**
     * 添加文章
     */
    public int addArticle(Article article){
        return articleMapper.insert(article);
    }

    /**
     * 删除文章
     */
    public int deleteArticle(int articleId) {
        return articleMapper.deleteById(articleId);
    }

    /**
     * 修改文章
     */
    public int alterArticle(Article article){
        return articleMapper.updateById(article);
    }

    /**
     * 获取所有文章
     */
    public List<Article> getAllArticle() {
        return articleMapper.selectList(null);
    }

    /**
     * 根据id获取文章
     */
    public Article getArticleById(int articleId) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", articleId);
        return articleMapper.selectOne(queryWrapper);
    }

    /**
     * 获取文章排行
     * return List<Article>
     */
    public List<Article> getArticleTop() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("id", "title", "content", "read_cnt").orderByDesc("read_cnt").last("limit 3");
        return articleMapper.selectList(wrapper);
    }

    /**
     * article.action?op=changeData
     * 用来存redis,用户给文章点赞->文章对应的点赞数+1,取消赞->文章的点赞数-1,用户点击文章->文章浏览量+1
     * redis:
     * 浏览量:键key->readCnt:值value->ArticleId_
     * @param articleId
     * @param userId
     * @return
     */
    public boolean changeData(String articleId, String userId) {
        //TODO:
        try {
            Jedis jedis = JedisUtils.getInstance();
            if (jedis.sismember(articleId + Constants.REDIS_ARTICLE_PRAISE, userId + "")) {
                //此用户已经对这篇文章点赞,再点就是取消
                //删除对文章点过赞的用户
                jedis.srem(articleId + Constants.REDIS_ARTICLE_PRAISE, userId + "");
                //删除用户点过赞的文章
                jedis.srem(userId + Constants.REDIS_USER_PRAISE, articleId + "");
            } else {
                //此用户没有对这篇文章点过赞
                //添加用户点过赞的文章
                jedis.sadd(userId + Constants.REDIS_USER_PRAISE, articleId + "");
                //添加文章被哪些用户点过赞
                jedis.sadd(articleId + Constants.REDIS_ARTICLE_PRAISE, userId + "");
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取所有标签
     */
    public List<Article> getAllTags() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("label");
        return articleMapper.selectList(wrapper);
    }

    /**
     * 根据分类获取文章
     */
    public List<Article> getArticleByCategory(int categoryId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id", categoryId);
        return articleMapper.selectList(wrapper);
    }

}




