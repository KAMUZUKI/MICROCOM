package com.mu.framework.mapper;

import com.mu.framework.domain.Flink;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author MUZUKI
* @description 针对表【flink】的数据库操作Mapper
* @createDate 2023-03-02 20:33:40
* @Entity generator.domain.Flink
*/
public interface FlinkMapper extends BaseMapper<Flink> {
    /**
     * 添加友链
     * @param flink
     */
    void addFlink(Flink flink);
}




