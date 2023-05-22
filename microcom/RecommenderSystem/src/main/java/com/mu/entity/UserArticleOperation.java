package com.mu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author MUZUKI
 * @Classname UserAricleOperation
 * @Description TODO
 * @Date 2023/5/22 14:07
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserArticleOperation implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer articleId;

    private Integer operationType;

    @TableField(exist = false)
    private Integer value;
}
