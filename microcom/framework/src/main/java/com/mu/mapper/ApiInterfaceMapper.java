package com.mu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mu.entity.ApiInterface;

import java.util.List;
import java.util.Map;

/**
* @author MUZUKI
* @description 针对表【api_interface】的数据库操作Mapper
* @createDate 2023-10-20 16:59:34
* @Entity generator.domain.ApiInterface
*/
public interface ApiInterfaceMapper extends BaseMapper<ApiInterface> {

    List<ApiInterface> search(Map<String, String> map);
}




