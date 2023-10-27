package com.mu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mu.entity.ApiInterface;
import com.mu.entity.User;
import com.mu.mapper.ApiInterfaceMapper;
import com.mu.service.ApiInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
* @author MUZUKI
* @description 针对表【api_interface】的数据库操作Service实现
* @createDate 2023-10-20 16:59:34
*/
@Service
public class ApiInterfaceServiceImpl extends ServiceImpl<ApiInterfaceMapper, ApiInterface>
    implements ApiInterfaceService {

    @Autowired(required = false)
    public ApiInterfaceMapper apiInterfaceMapper;

    public IPage getApiInterface(Map<String,String> params) {
        int currentPage = Integer.parseInt(params.get("currentPage"));
        int pageSize = Integer.parseInt(params.get("pageSize"));
        Page<ApiInterface> page = new Page<>(currentPage, pageSize);;
        return apiInterfaceMapper.selectPage(page,null);
    }

    public IPage search(Map<String, String> params) {
        IPage<ApiInterface> page = new Page<>(Integer.parseInt(params.get("currentPage")), Integer.parseInt(params.get("pageSize")));
        QueryWrapper<ApiInterface> queryWrapper = new QueryWrapper<>();
        if (!Objects.equals(params.get("name"), "")){
            queryWrapper.like("name", params.get("name"));
        }
        if (!Objects.equals(params.get("path"), "")){
            queryWrapper.like("path", params.get("path"));
        }
        return apiInterfaceMapper.selectPage(page, queryWrapper);
    }
}




