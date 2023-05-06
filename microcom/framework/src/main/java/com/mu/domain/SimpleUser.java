package com.mu.domain;

import com.alibaba.fastjson.annotation.JSONType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : MUZUKI
 * @date : 2023-05-06 16:09
 * @description : 简单用户信息
 **/

@TableName(value = "user")
@JSONType(orders = {"id", "name", "head"})
@Data
public class SimpleUser implements Serializable {
    /**
     *  用户id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 头像图片
     */
    private String head;
}
