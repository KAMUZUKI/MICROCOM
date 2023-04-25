package com.mu.domain;

import lombok.Data;

import java.util.List;

/**
 * @author MUZUKI
 * @Classname VlogComment
 * @Description TODO
 * @Date 2023/4/24 21:42
 */

@Data
public class VlogComment {
    private String id;
    private String author;
    private String content;
    private int likes;
    private List<Reply> replies;
}
