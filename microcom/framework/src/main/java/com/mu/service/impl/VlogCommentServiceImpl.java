package com.mu.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.mu.constant.VlogConstant;
import com.mu.entity.VlogComment;
import com.mu.service.AbstractRedisService;
import com.mu.utils.CoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author MUZUKI
 * @Classname VlogCommentServiceImpl
 * @Description vlog评论的service实现类
 * @Date 2023/4/24 21:47
 */

@Service
public class VlogCommentServiceImpl extends AbstractRedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean saveComment(VlogComment comment) {
        comment.setId(IdUtil.getSnowflakeNextId());
        if (comment.getParentId() == null) {
            String hashKey = VlogConstant.COMMENT_PREFIX + comment.getVlogId();
            redisTemplate.opsForHash().put(hashKey, comment.getId().toString(), comment);
            return redisTemplate.opsForHash().get(hashKey, comment.getId().toString()) != null;
        } else {
            String zsetKey = CoreUtils.replyKey(comment.getVlogId(), comment.getParentId());
            return Boolean.TRUE.equals(redisTemplate.opsForZSet().add(zsetKey, comment, comment.getId()));
        }
    }

    public List<VlogComment> findByVlogId(Long vlogId, int pageNum) {
        String hashKey = VlogConstant.COMMENT_PREFIX + vlogId;
        List<VlogComment> commentList = new ArrayList<>();
        getHashEntriesByPage(redisTemplate,hashKey, 10, pageNum).forEach(entry -> {
            VlogComment vlogComment = JSON.parseObject(entry.getValue().toString(), VlogComment.class);
            vlogComment.setHasReply(hasChildren(vlogId, vlogComment.getId()));
            commentList.add(vlogComment);
        });
        return commentList;
    }

    public List<VlogComment> findChildren(Long vlogId, Long parentId, int pageNum) {
        String zsetKey = CoreUtils.replyKey(vlogId, parentId);
        List<VlogComment> childrenObjects = new ArrayList<>();
        Set<ZSetOperations.TypedTuple<Object>> result = getMembersByPage(redisTemplate,zsetKey, 2, pageNum);
        result.forEach(item -> childrenObjects.add(JSON.parseObject(item.getValue().toString(), VlogComment.class)));
        return childrenObjects;
    }

    public boolean hasChildren(Long vlogId, Long parentId) {
        String zsetKey = CoreUtils.replyKey(vlogId, parentId);
        return !getMembersByPage(redisTemplate,zsetKey, 2, 1).isEmpty();
    }

    public boolean deleteComment(VlogComment comment) {
        if (comment.getParentId() == null) {
            String hashKey = VlogConstant.COMMENT_PREFIX + comment.getId();
            //如果父评论有子评论，不允许删除，修改内容为已删除
            if (redisTemplate.opsForZSet().size(CoreUtils.replyKey(comment.getVlogId(), comment.getId())) > 0) {
                comment.setContent("该评论已被删除");
                redisTemplate.opsForHash().put(hashKey, comment.getId().toString(), comment);
                return redisTemplate.opsForHash().get(hashKey, comment.getId().toString()) != null;
            }
            //如果父评论没有子评论，直接删除
            return redisTemplate.opsForHash().delete(hashKey, comment.getId()) == 1L;
        } else {
            String zsetKey = CoreUtils.replyKey(comment.getId(), comment.getParentId());
            double minScore = comment.getId() + Double.MIN_VALUE;
            double maxScore = comment.getId();
            return redisTemplate.opsForZSet().removeRangeByScore(zsetKey, minScore, maxScore) == 1L;
        }
    }
}
