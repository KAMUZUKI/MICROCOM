package com.mu.controller;

import cn.dev33.satoken.util.SaResult;
import com.mu.domain.VlogComment;
import com.mu.service.impl.VlogCommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : MUZUKI
 * @date : 2023-04-25 16:03
 **/

@ResponseBody
@Controller
@RequestMapping("/vlogcomment/")
public class VlogCommentController {
    @Autowired
    private VlogCommentServiceImpl vlogCommentService;

    @PostMapping("save")
    public SaResult saveComment(@RequestBody VlogComment comment) {
        return SaResult.ok(vlogCommentService.saveComment(comment) ?"评论成功":"评论失败");
    }

    @GetMapping("findByVlogId/{vlogId}")
    public SaResult findByVlogId(@PathVariable("vlogId") Long vlogId) {
        return SaResult.ok().setData(vlogCommentService.findByVlogId(vlogId));
    }

    @GetMapping("findChildren/{vlogId}/{parentId}")
    public SaResult findChildren(@PathVariable("vlogId") Long vlogId, @PathVariable("parentId") Long parentId) {
        return SaResult.ok().setData(vlogCommentService.findChildren(vlogId, parentId));
    }

    @DeleteMapping("delete")
    public SaResult delete(@RequestBody VlogComment comment) {
        return SaResult.ok(vlogCommentService.deleteComment(comment)? "删除成功":"删除失败");
    }
}