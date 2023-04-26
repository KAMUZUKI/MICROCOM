package com.mu.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName vlog
 */
@Data
@Accessors(chain = true)
public class Vlog implements Serializable {
    private Integer id;
    private String author;
    private String title;
    private String text;
    private String img;
    private Date time;
}
