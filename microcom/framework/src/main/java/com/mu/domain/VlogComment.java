package com.mu.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author MUZUKI
 * @Classname VlogComment
 * @Description TODO
 * @Date 2023/4/24 21:42
 */

@Data
@Accessors(chain = true)
public class VlogComment implements Serializable {
    private Long id;
    private Long vlogId;
    private String content;
    private String author;
    private String time;
    /**
     * 父评论的ID，用于存储分层评论
     */
    private Long parentId;
}
