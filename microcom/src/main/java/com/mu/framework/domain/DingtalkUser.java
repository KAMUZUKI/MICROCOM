package com.mu.framework.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MUZUKI
 * @Classname DingtalkUser
 * @Description TODO
 * @Date 2023/3/26 16:13
 */

@Data
@TableName(value ="dingtalk_user")
@Accessors(chain = true)
public class DingtalkUser implements Serializable{
    /**
     * 第三方唯一id
     */
    @TableId
    private String uuid;

    /**
     * 第三方用户名
     */
    private String username;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态		0 停用	1启用
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
