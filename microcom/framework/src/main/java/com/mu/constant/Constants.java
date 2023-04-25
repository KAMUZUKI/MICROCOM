package com.mu.constant;

/**
 * @author : MUZUKI
 * @date : 2023-03-05 10:12
 **/

/**
 * 常量类
 */
public class Constants {

    /**
     *  redis中用户对哪些文章点了赞,用户点过赞的文章
     */
    public final static String REDIS_USER_PRAISE="_user_praise";

    /**
     *redis中保存的文章被哪些用户点过赞 后缀 格式: 文章id_后缀
     */
    public final static String REDIS_ARTICLE_PRAISE="_article_praise";

    /**
     * redis中保存文章的浏览量
     */
    public final static String REDIS_ARTICLE_READCNT="_article_readcnt";
}