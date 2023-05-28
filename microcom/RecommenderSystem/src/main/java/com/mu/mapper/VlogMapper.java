package com.mu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mu.entity.UserAction;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * @author MUZUKI
 * @Classname VlogMapper
 * @Description TODO
 * @Date 2023/5/27 17:15
 */

public interface VlogMapper extends BaseMapper<Long> {

    @Select("select time from vlog where id = #{id}")
    Date selectTimeById(long id);
}
