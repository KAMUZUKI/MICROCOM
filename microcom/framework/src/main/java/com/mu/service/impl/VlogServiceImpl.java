package com.mu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mu.entity.Vlog;
import com.mu.mapper.VlogMapper;
import com.mu.service.VlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MUZUKI
 * @Classname VlogServiceImpl
 * @Description TODO
 * @Date 2023/4/26 14:41
 */

@Service
public class VlogServiceImpl extends ServiceImpl<VlogMapper, Vlog> implements VlogService {
    @Autowired
    private VlogMapper vlogMapper;

    public IPage<Vlog> findWithPage(Page<Vlog> page) {
        QueryWrapper<Vlog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("vlog.id", "vlog.create_id", "vlog.title","vlog.label", "vlog.text", "vlog.img", "user.name", "user.head", "vlog.time")
                .last("LEFT JOIN user ON vlog.create_id = user.id"  + " ORDER BY vlog.time DESC");
        return baseMapper.selectPage(page, queryWrapper);
    }

    public IPage<Vlog> findWithPageById(int id,Page<Vlog> page) {
        QueryWrapper<Vlog> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("vlog.id", "vlog.create_id", "vlog.title","vlog.label", "vlog.text", "vlog.img", "user.name", "user.head", "vlog.time")
                .last("LEFT JOIN user ON vlog.create_id = user.id where create_id = " + id + " ORDER BY vlog.time DESC");
        return baseMapper.selectPage(page, queryWrapper);
    }

    public List<Vlog> findVlogWithList(List<Long> vlogIds) {
        return vlogMapper.findVlogWithList(vlogIds);
    }
}
