package com.mu.extra;

import com.mu.mapper.VlogMapper;
import org.apache.mahout.cf.taste.recommender.IDRescorer;

/**
 * @author MUZUKI
 * @Classname Time
 * @Description TODO
 * @Date 2023/5/27 10:33
 */

public class TimeDecayRescorer implements IDRescorer {
    private final VlogMapper vlogMapper;

    public TimeDecayRescorer(VlogMapper vlogMapper) {
        this.vlogMapper = vlogMapper;
    }

    @Override
    public double rescore(long itemId, double originalScore) {
        long currentTime = System.currentTimeMillis();
        // 获取用户对该物品的评分时间
        Long timestamp = this.getTimestamp(itemId);
        long timeDiff = currentTime - timestamp;
        // 根据时间差计算权重，例如使用指数衰减
        double weight = this.calculateDecayWeight(timeDiff); // 以天为单位进行指数衰减，86400000为一天的毫秒数
        return originalScore * weight;
    }

    @Override
    public boolean isFiltered(long id) {
        // 可选的过滤逻辑
        return false;
    }

    public double calculateDecayWeight(long timeDiff) {
        // 使用指数衰减函数计算权重
        double decayFactor = 0.5; // 衰减因子，可以根据需求进行调整
        double weight = Math.exp(-timeDiff / (decayFactor * 24 * 60 * 60 * 1000));
        return weight;
    }

    private long getTimestamp(Long itemId) {
       long res = vlogMapper.selectTimeById(itemId).getTime();
       return res;
    }
}

