package com.mu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mu.domain.Comment;

import java.util.List;

/**
* @author MUZUKI
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2023-03-02 20:33:40
* @Entity generator.domain.Comment
*/
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> getCommentsByArticleId(int articleId);
}




