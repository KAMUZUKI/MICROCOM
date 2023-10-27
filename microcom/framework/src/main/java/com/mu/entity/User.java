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
 * @author MUZUKI
 * @TableName user
 */
@TableName("user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 
     */
    private String email;

    /**
     * 头像图片
     */
    private String head;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态		0停用 1启用 2禁言 3封禁
     */
    private String status;

    /**
     * 用户类型 1系统管理员	2一般用户
     */
    private String type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}