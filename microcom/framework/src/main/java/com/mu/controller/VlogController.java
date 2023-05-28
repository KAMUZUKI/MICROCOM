package com.mu.controller;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mu.constant.Message;
import com.mu.entity.Vlog;
import com.mu.service.impl.VlogRedisServiceImpl;
import com.mu.service.impl.VlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private VlogRedisServiceImpl vlogRedisService;

    @GetMapping("recommendWithPage/{userId}/{size}/{page}")
    public SaResult recommendWithPage(@PathVariable("userId") Long userId,@PathVariable("size") Integer size,@PathVariable("page") Integer page) {
        // 从redis中获取推荐列表
        List<Vlog> vlogList = vlogRedisService.findByVlogId(userId,size,page);
        if (size == vlogList.size()) {
            // redis中有数据则返回数据
            return SaResult.ok().setData(vlogList);
        }
        // redis中没有数据则从数据库中获取
        IPage<Vlog> userEntityIPage = vlogService.findWithPage(new Page<>(page, size));
        if (page > userEntityIPage.getPages()) {
            return SaResult.error().setMsg(Message.NO_MORE_DATA);
        }
        return SaResult.ok().setData(userEntityIPage.getRecords());
    }

    @GetMapping("findWithPage/{size}/{page}")
    public SaResult findWithPage(@PathVariable("size") Integer size,@PathVariable("page") Integer page) {
        IPage<Vlog> userEntityIPage = vlogService.findWithPage(new Page<>(page, size));
        if (page > userEntityIPage.getPages()) {
            return SaResult.error().setMsg(Message.NO_MORE_DATA);
        }
        return SaResult.ok().setData(userEntityIPage.getRecords());
    }

    @GetMapping("findWithPageById/{id}/{size}/{page}")
    public SaResult findWithPageById(@PathVariable("id") Integer id,@PathVariable("size") Integer size,@PathVariable("page") Integer page) {
        IPage<Vlog> userEntityIPage = vlogService.findWithPageById(id,new Page<>(page, size));
        if (page > userEntityIPage.getPages()) {
            return SaResult.error().setMsg(Message.NO_MORE_DATA);
        }
        return SaResult.ok().setData(userEntityIPage.getRecords());
    }

    @GetMapping("findVlogWithList")
    public SaResult findVlogWithList(@RequestBody List<Long> vlogIds) {
        return SaResult.ok().setData(vlogService.findVlogWithList(vlogIds));
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
