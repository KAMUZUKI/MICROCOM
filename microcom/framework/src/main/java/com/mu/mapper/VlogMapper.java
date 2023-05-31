package com.mu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mu.entity.Vlog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author MUZUKI
 * @description 针对表【vlog】的数据库操作Mapper
 */

public interface VlogMapper extends BaseMapper<Vlog> {
    /**
     * @param ids vlog的id集合
     * @return
     * @description 根据id查询vlog
     */
    List<Vlog> findVlogWithList(@Param("ids") List<Long> ids);
}
