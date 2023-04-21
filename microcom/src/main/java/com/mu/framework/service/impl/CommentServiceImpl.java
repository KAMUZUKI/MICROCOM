package com.mu.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mu.framework.domain.Comment;
import com.mu.framework.mapper.CommentMapper;
import com.mu.framework.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @author MUZUKI
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2023-03-05 19:20:49
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired(required = false)
    private CommentMapper commentMapper;

    /**
     * 添加评论
     */
    public int addComment(Comment comment) {
        return commentMapper.insert(comment);
    }

    /**
     * 通过文章id获取文章评论
     */
    public List<Comment> getComments(int articleId){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        return commentMapper.selectList(queryWrapper);
    }

    public List<Comment> getCommentsByArticleId(int articleId){
        return commentMapper.getCommentsByArticleId(articleId);
    }
}




