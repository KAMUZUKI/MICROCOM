package com.mu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mu.entity.User;

import java.util.List;

/**
* @author MUZUKI
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-03-02 20:33:40
* @Entity generator.domain.User
*/
public interface UserMapper extends BaseMapper<User> {
    /**
     * 获取所有用户id
     * @return List<Long> 用户id列表
     */
    List<Long> getUidList();
}




