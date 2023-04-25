package com.mu.service.impl;

import com.mu.constant.VlogConstant;
import com.mu.domain.Reply;
import com.mu.domain.VlogComment;
import com.mu.service.VlogCommentService;
import com.mu.utils.BeanMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MUZUKI
 * @Classname VlogCommentServiceImpl
 * @Description TODO
 * @Date 2023/4/24 21:47
 */

@Service
public class VlogCommentServiceImpl implements VlogCommentService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveComment(VlogComment comment) {
        redisTemplate.opsForHash().putAll(VlogConstant.COMMENT_PREFIX + comment.getId(), BeanMapUtils.beanToMap(comment));
        redisTemplate.opsForZSet().add("comments", VlogConstant.COMMENT_PREFIX + comment.getId(), System.currentTimeMillis());
    }

    @Override
    public VlogComment findCommentById(String id) {
        Map<Object, Object> commentMap = redisTemplate.opsForHash().entries(VlogConstant.COMMENT_PREFIX + id);
        if (commentMap.isEmpty()) {
            return null;
        }
        return BeanMapUtils.mapToBean(commentMap, VlogComment.class);
    }

    @Override
    public void saveReply(String commentId, Reply reply) {
        redisTemplate.opsForHash().putAll(VlogConstant.REPLY_PREFIX + reply.getId(), BeanMapUtils.beanToMap(reply));
        redisTemplate.opsForList().rightPush(VlogConstant.COMMENT_PREFIX + commentId + ":replies", reply.getId());
    }

    @Override
    public List<Reply> findRepliesByCommentId(String commentId) {
        List<Object> replyIds = redisTemplate.opsForList().range(VlogConstant.COMMENT_PREFIX + commentId + ":replies", 0, -1);
        List<Reply> replies = new ArrayList<>();
        for (Object replyId : replyIds) {
            Map<Object, Object> replyMap = redisTemplate.opsForHash().entries(VlogConstant.REPLY_PREFIX + replyId);
            replies.add(BeanMapUtils.mapToBean(replyMap, Reply.class));
        }
        return replies;
    }
}
