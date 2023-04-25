package com.mu.domain;

import lombok.Data;

/**
 * @author MUZUKI
 * @Classname Reply
 * @Description VlogComment的回复
 * @Date 2023/4/24 21:43
 */

@Data
public class Reply {
    private String id;
    private String author;
    private String content;
    private int likes;
}