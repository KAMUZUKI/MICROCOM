package com.mu.service.impl;

import com.mu.constant.VlogConstant;
import com.mu.domain.VlogComment;
import com.mu.utils.CoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author MUZUKI
 * @Classname VlogCommentServiceImpl
 * @Description vlog评论的service实现类
 * @Date 2023/4/24 21:47
 */

@Service
public class VlogCommentServiceImpl{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean saveComment(VlogComment comment) {
        if (comment.getParentId() == null) {
            String hashKey = VlogConstant.COMMENT_PREFIX + comment.getVlogId();
            redisTemplate.opsForHash().put(hashKey,comment.getId().toString(), comment);
            return redisTemplate.opsForHash().get(hashKey, comment.getId().toString()) != null;
        }else{
            String zsetKey = CoreUtils.replyKey(comment.getVlogId(),comment.getParentId());
            return Boolean.TRUE.equals(redisTemplate.opsForZSet().add(zsetKey, comment, comment.getId()));
        }
    }

    public List<VlogComment> findByVlogId(Long vlogId) {
        String hashKey = VlogConstant.COMMENT_PREFIX + vlogId;
        Map<Object, Object> commentMap = redisTemplate.opsForHash().entries(hashKey);
        return CoreUtils.mapToList(commentMap, VlogComment.class);
    }

    public List<VlogComment> findChildren(Long vlogId,Long parentId) {
        String zsetKey = CoreUtils.replyKey(vlogId, parentId);
        Set<Object> childrenObjects = redisTemplate.opsForZSet().range(zsetKey, 0, -1);
        assert childrenObjects != null;
        return CoreUtils.setToList(childrenObjects, VlogComment.class);
    }

    public boolean deleteComment(VlogComment comment) {
        if (comment.getParentId() == null) {
            String hashKey = VlogConstant.COMMENT_PREFIX + comment.getId();
            //如果父评论有子评论，不允许删除，修改内容为已删除
            if (redisTemplate.opsForZSet().size(CoreUtils.replyKey(comment.getVlogId(),comment.getId())) > 0) {
                comment.setContent("该评论已被删除");
                redisTemplate.opsForHash().put(hashKey,comment.getId().toString(), comment);
                return redisTemplate.opsForHash().get(hashKey, comment.getId().toString()) != null;
            }
            //如果父评论没有子评论，直接删除
            return redisTemplate.opsForHash().delete(hashKey,comment.getId()) == 1L;
        }else{
            String zsetKey = CoreUtils.replyKey(comment.getId(),comment.getParentId());
            double minScore = comment.getId() + Double.MIN_VALUE;
            double maxScore = comment.getId();
            return redisTemplate.opsForZSet().removeRangeByScore(zsetKey, minScore,maxScore) == 1L;
        }
    }
}
