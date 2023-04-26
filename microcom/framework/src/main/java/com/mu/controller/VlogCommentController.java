package com.mu.controller;

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
    public boolean saveComment(@RequestBody VlogComment comment) {
        return vlogCommentService.saveComment(comment);
    }

    @GetMapping("findByVlogId/{vlogId}")
    public List<VlogComment> findByVlogId(@PathVariable("vlogId") Long vlogId) {
        return vlogCommentService.findByVlogId(vlogId);
    }

    @GetMapping("findChildren/{vlogId}/{parentId}")
    public List<VlogComment> findChildren(@PathVariable("vlogId") Long vlogId, @PathVariable("parentId") Long parentId) {
        return vlogCommentService.findChildren(vlogId, parentId);
    }

    @DeleteMapping("delete")
    public boolean delete(@RequestBody VlogComment comment) {
        return vlogCommentService.deleteComment(comment);
    }
}