package com.mu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * @author MUZUKI
 * @TableName vlog
 */
@Data
@Accessors(chain = true)
@TableName(value = "vlog")
public class Vlog implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer createId;
    private String title;
    private String label;
    private String text;
    private String img;
    private String name;
    private String head;
    private String time;
}
