package com.mu.controller;

import cn.dev33.satoken.util.SaResult;
import com.mu.entity.Vlog;
import com.mu.entity.VlogComment;
import com.mu.service.impl.VlogCommentServiceImpl;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("save")
    public SaResult saveComment(@RequestHeader("userId") Long userId,@RequestBody VlogComment comment) {
        boolean res = vlogCommentService.saveComment(comment);
        if (res){
            //从nacos中获取服务地址
            ServiceInstance service = discoveryClient.getInstances("recommend").get(0);
            String url = service.getHost() + ":" + service.getPort();
            restTemplate.postForObject("http://" + url + "/userAction/addVlogComment/" + userId + "/" + comment.getVlogId(), null, String.class);
            return SaResult.ok("评论成功");
        }
        return SaResult.error("评论失败");
    }

    @GetMapping("findByVlogId/{vlogId}/{pageNum}")
    public SaResult findByVlogId(@RequestHeader("userId") Long userId,@PathVariable("vlogId") Long vlogId, @PathVariable("pageNum") int pageNum) {
        List<VlogComment> res = vlogCommentService.findByVlogId(vlogId, pageNum);
        if (res!=null||res.size()==0) {
            //从nacos中获取服务地址
            ServiceInstance service = discoveryClient.getInstances("recommend").get(0);
            String url = service.getHost() + ":" + service.getPort();
            restTemplate.postForObject("http://" + url + "/userAction/checkOutVlog/" + userId + "/" + vlogId, null, String.class);
            return SaResult.ok().setData(res);
        }
        return SaResult.error("查询评论失败");
    }

    @GetMapping("findChildren/{vlogId}/{parentId}/{pageNum}")
    public SaResult findChildren(@RequestHeader("userId") Long userId,@PathVariable("vlogId") Long vlogId, @PathVariable("parentId") Long parentId, @PathVariable("pageNum") int pageNum) {
        List<VlogComment> res = vlogCommentService.findChildren(vlogId, parentId, pageNum);
        if (res!=null){
            //从nacos中获取服务地址
            ServiceInstance service = discoveryClient.getInstances("recommend").get(0);
            String url = service.getHost() + ":" + service.getPort();
            restTemplate.postForObject("http://" + url + "/userAction/checkOutVlogComment/" + userId + "/" + vlogId, null, String.class);
            return SaResult.ok().setData(res);
        }
        return SaResult.error("查询评论失败");
    }

    @DeleteMapping("delete")
    public SaResult delete(@RequestBody VlogComment comment) {
        return SaResult.ok(vlogCommentService.deleteComment(comment) ? "删除成功" : "删除失败");
    }
}