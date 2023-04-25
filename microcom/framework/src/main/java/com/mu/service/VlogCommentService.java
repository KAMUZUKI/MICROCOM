package com.mu.service;

import com.mu.domain.VlogComment;

import java.util.List;

/**
 * @author : MUZUKI
 * @Description : vlog评论的service接口
 * @date : 2023-04-25 20:22
 **/

public interface VlogCommentService {
    /**
     * 保存评论
     * @param comment 评论
     * @return 是否保存成功
     */
    boolean saveComment(VlogComment comment);

    /**
     * 根据vlogId获取评论
     * @param vlogId vlogId
     * @return 评论列表
     */
    List<VlogComment> findByVlogId(Long vlogId);

    /**
     * 根据vlogId和parentId获取子评论
     * @param vlogId vlogId
     * @param parentId 父评论id
     * @return 子评论列表
     */
    List<VlogComment> findChildren(Long vlogId,Long parentId);
}
