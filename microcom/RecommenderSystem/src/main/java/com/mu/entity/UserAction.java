package com.mu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MUZUKI
 * @Classname UserAricleOperation
 * @Description TODO
 * @Date 2023/5/22 14:07
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="recommend")
public class UserAction implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(type = IdType.INPUT)
    private Integer uid;

    /**
     * 文章id
     */
    private Integer iid;

    /**
     * 用户对文章评分
     * @Description 用户对文章的评分 0-10，初始为0，后续根据用户的操作进行更新
     */
    private Double value;

    /**
     * 用户对文章的操作的最后时间
     * @Description 用于向用户推荐时，根据时间对评分进行衰减计算
     */
    private Date time;
}
