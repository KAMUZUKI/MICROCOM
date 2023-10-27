package com.mu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MUZUKI
 * @TableName api_interface
 */
@Data
@TableName("api_interface")
public class ApiInterface implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 接口名称或标识
     */
    private String name;

    /**
     * 接口路径
     */
    private String path;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数，包括参数名称、数据类型、是否必需、默认值等信息
     */
    private String requestParameters;

    /**
     * 返回数据结构，包括返回字段、数据类型、示例值等信息
     */
    private String responseData;

    /**
     * 接口示例
     */
    private String description;

    /**
     * 接口所需的权限或角色
     */
    private String permissions;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 接口版本号
     */
    private String version;

    private static final long serialVersionUID = 1L;
}