package com.mu.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * @author MUZUKI
 * @TableName vlog
 */
@Data
@Accessors(chain = true)
@TableName("vlog")
public class Vlog implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(insertStrategy = FieldStrategy.NOT_NULL,updateStrategy = FieldStrategy.NOT_NULL)
    private Integer createId;
    private Integer author;
    private String title;
    private String label;
    private String text;
    private String img;
    private String time;
}
