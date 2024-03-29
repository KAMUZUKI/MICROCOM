package com.mu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName article
 */
@TableName("article")
@Data
public class Article implements Serializable {
    /**
     * 文章id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 作者
     */
    private String author;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 关键字
     */
    private String keyWords;

    /**
     * 描述
     */
    private String description;

    /**
     * 栏目
     */
    private Integer categoryId;

    /**
     * 标签，用逗号分隔
     */
    private String label;

    /**
     * 标题图片列表
     */
    private String titleImgs;

    /**
     * 状态 0. 删除 1.正常 2.审核 3.草稿
     */
    private String status;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 阅读量
     */
    private Integer readCnt;

    /**
     * 点赞数
     */
    private Integer agreeCnt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}