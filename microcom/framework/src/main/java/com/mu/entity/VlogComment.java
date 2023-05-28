package com.mu.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author MUZUKI
 * @Classname VlogComment
 * @Description TODO
 * @Date 2023/4/24 21:42
 */

@Data
@Accessors(chain = true)
public class VlogComment implements Serializable {
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    private Long vlogId;
    private Long userId;
    private String content;
    private String author;
    private String time;
    /**
     * 父评论的ID，用于存储分层评论
     */
    private Long parentId;
    private Boolean hasReply;
}
