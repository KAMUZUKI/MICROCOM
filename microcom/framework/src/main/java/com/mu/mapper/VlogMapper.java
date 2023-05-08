package com.mu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.query.MPJQueryWrapper;
import com.mu.domain.Vlog;
import org.springframework.data.repository.query.Param;

/**
 * @author MUZUKI
 * @description 针对表【vlog】的数据库操作Mapper
 */

public interface VlogMapper extends BaseMapper<Vlog> {

}
