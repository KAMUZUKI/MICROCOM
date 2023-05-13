package com.mu.controller;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mu.domain.Vlog;
import com.mu.service.impl.VlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public SaResult findAll() {
        return SaResult.ok().setData(vlogService.list());
    }

    @GetMapping("findWithPage/{size}/{page}")
    public SaResult findWithPage(@PathVariable("size") Integer size,@PathVariable("page") Integer page) {
        IPage<Vlog> userEntityIPage = vlogService.findWithPage(new Page<>(page, size));
        if (page > userEntityIPage.getPages()) {
            return SaResult.ok().setMsg("没有更多数据了");
        }
        return SaResult.ok().setData(userEntityIPage.getRecords());
    }

    @GetMapping("findWithPageById/{id}/{size}/{page}")
    public SaResult findWithPageById(@PathVariable("id") Integer id,@PathVariable("size") Integer size,@PathVariable("page") Integer page) {
        IPage<Vlog> userEntityIPage = vlogService.findWithPageById(id,new Page<>(page, size));
        if (page > userEntityIPage.getPages()) {
            return SaResult.ok().setMsg("没有更多数据了");
        }
        return SaResult.ok().setData(userEntityIPage.getRecords());
    }

    @PostMapping("add")
    public SaResult add(@RequestBody Vlog vlog) {
        return SaResult.ok().setMsg(vlogService.save(vlog) ? "添加成功" : "添加失败");
    }

    @DeleteMapping("delete/{id}")
    public SaResult delete(@PathVariable("id") Integer id) {
        return SaResult.ok().setMsg(vlogService.removeById(id) ? "删除成功" : "删除失败");
    }
}
