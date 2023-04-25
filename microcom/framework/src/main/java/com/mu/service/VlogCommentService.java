package com.mu.service;

import com.mu.domain.Reply;
import com.mu.domain.VlogComment;

import java.util.List;

/**
 * @author MUZUKI
 * @Description vlog评论的service
 */

public interface VlogCommentService {
    /**
     * 保存评论
     *
     * @param comment
     */
    void saveComment(VlogComment comment);

    /**
     * 根据id查找评论
     *
     * @param id
     * @return
     */
    VlogComment findCommentById(String id);

    /**
     * 通过评论id保存回复
     *
     * @param commentId 评论id
     * @param reply     回复
     * @return
     */
    void saveReply(String commentId, Reply reply);

    /**
     * 根据评论id查找回复
     *
     * @param commentId
     * @return
     */
    List<Reply> findRepliesByCommentId(String commentId);
}
