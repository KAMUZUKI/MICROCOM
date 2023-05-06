package com.mu.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.mu.constant.VlogConstant;
import com.mu.domain.VlogComment;
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
public class VlogCommentServiceImpl {

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
        getHashEntriesByPage(hashKey, 10, pageNum).forEach(
                entry -> commentList.add(JSON.parseObject(entry.getValue().toString(), VlogComment.class)));
        return commentList;
    }

    public List<Map.Entry<Object, Object>> getHashEntriesByPage(String key, int pageSize, int pageNum) {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.scanOptions().count(pageSize).build());
        int count = 0;
        int startIndex = (pageNum - 1) * pageSize;
        List<Map.Entry<Object, Object>> result = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            if (count >= startIndex && count < startIndex + pageSize) {
                result.add(entry);
            } else if (count >= startIndex + pageSize) {
                break;
            }
            count++;
        }
        return result;
    }

    public List<VlogComment> findChildren(Long vlogId, Long parentId, int pageNum) {
        String zsetKey = CoreUtils.replyKey(vlogId, parentId);
        List<VlogComment> childrenObjects = new ArrayList<>();
        Set<ZSetOperations.TypedTuple<Object>> result = getMembersByPage(zsetKey, 2, pageNum);
        result.forEach(item -> childrenObjects.add(JSON.parseObject(item.getValue().toString(), VlogComment.class)));
        return childrenObjects;
    }

    public Set<ZSetOperations.TypedTuple<Object>> getMembersByPage(String key, int pageSize, int pageNum) {
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = redisTemplate.opsForZSet().scan(key, ScanOptions.scanOptions().count(pageSize).build());
        int count = 0;
        int start = (pageNum - 1) * pageSize;
        int end = start + pageSize - 1;
        Set<ZSetOperations.TypedTuple<Object>> result = new LinkedHashSet<>();
        while (cursor.hasNext()) {
            ZSetOperations.TypedTuple<Object> item = cursor.next();
            if (count >= start && count <= end) {
                result.add(item);
            } else if (count > end) {
                break;
            }
            count++;
        }
        return result;
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
