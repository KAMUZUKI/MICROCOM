package com.mu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mu.domain.Vlog;
import org.springframework.data.repository.query.Param;

/**
 * @author MUZUKI
 * @description 针对表【vlog】的数据库操作Mapper
 */

public interface VlogMapper extends BaseMapper<Vlog> {
    IPage<Vlog> selectVlog(Page<Vlog> page, @Param("id") Integer id);
}
