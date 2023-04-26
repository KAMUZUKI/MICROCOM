package com.mu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mu.domain.Vlog;
import com.mu.service.impl.VlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MUZUKI
 * @Classname VlogController
 * @Description TODO
 * @Date 2023/4/26 14:36
 */

@Controller
@ResponseBody
@RequestMapping("/vlog/")
public class VlogController {
    @Autowired
    private VlogServiceImpl vlogService;

    @GetMapping("findAll")
    public List<Vlog> findAll() {
        return vlogService.list();
    }

    @GetMapping("findWithPage/{page}")
    public List<Vlog> findWithPage(@PathVariable("page") Integer page) {
        QueryWrapper<Vlog> queryWrapper = new QueryWrapper<>();
        IPage<Vlog> userEntityIPage = vlogService.findWithPage(new Page<>(page, 8), queryWrapper);
        return userEntityIPage.getRecords();
    }

    @PostMapping("add")
    public boolean add(@RequestBody Vlog vlog) {
        return vlogService.save(vlog);
    }
    
    @DeleteMapping("delete/{id}")
    public boolean delete(@PathVariable("id") Integer id) {
        return vlogService.removeById(id);
    }
}
