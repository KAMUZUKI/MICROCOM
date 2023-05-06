package com.mu.controller;

import cn.dev33.satoken.util.SaResult;
import com.mu.entity.Message;
import com.mu.entity.User;
import com.mu.exception.GlobalException;
import com.mu.service.ChatSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tycoding
 * @date 2019-06-11
 */
@Slf4j
@RestController
@ResponseBody
@RequestMapping("/chat/")
public class ChatController {

    @Autowired
    private ChatSessionService chatSessionService;

    /**
     * 加入聊天接口
     *
     * @param user
     * @return
     */
    @PostMapping("join")
    public SaResult join(@RequestBody User user) {
        chatSessionService.join(user);
        return SaResult.ok().setMsg("加入聊天成功");
    }

    @PostMapping("record")
    public SaResult recordUser(@RequestBody User user) {
        chatSessionService.recordUser(user);
        return SaResult.ok("添加成功");
    }

    /**
     * 获取当前窗口用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public SaResult info(@PathVariable("id") String id) {
        return SaResult.ok().setData(chatSessionService.findById(id));
    }

    /**
     * 向指定窗口推送消息
     *
     * @param toId    接收方ID
     * @param message 消息
     * @return
     */
    @PostMapping("push/{toId}")
    public SaResult push(@PathVariable("toId") String toId, @RequestBody Message message) {
        try {
            WebsocketServerEndpoint endpoint = new WebsocketServerEndpoint();
            endpoint.sendTo(toId, message);
            return SaResult.ok();
        } catch (GlobalException e) {
            e.printStackTrace();
            return SaResult.ok().setMsg(e.getMsg());
        }
    }

    /**
     * 查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("user/{id}")
    public SaResult findById(@PathVariable("id") String id) {
        return SaResult.ok().setData(chatSessionService.findById(id));
    }

    /**
     * 获取在线用户列表
     *
     * @return
     */
    @GetMapping("online/list")
    public SaResult onlineList() {
        return SaResult.ok().setData(chatSessionService.getOnlineList());
    }

    /**
     * 获取公共聊天消息内容
     *
     * @return
     */
    @GetMapping("common")
    public SaResult commonList() {
        return SaResult.ok().setData(chatSessionService.commonList());
    }

    /**
     * 获取指定用户的聊天消息内容
     *
     * @param fromId 该用户ID
     * @param toId   哪个窗口
     * @return
     */
    @GetMapping("self/{fromId}/{toId}")
    public SaResult selfList(@PathVariable("fromId") String fromId, @PathVariable("toId") String toId) {
        List<Message> list = chatSessionService.selfList(fromId, toId);
        return SaResult.ok().setData(list);
    }

    /**
     * 退出登录
     *
     * @param id 用户ID
     * @return
     */
    @PostMapping("logout/{id}")
    public SaResult logout(@PathVariable("id") String id) {
        if (chatSessionService.delete(id) > 0) {
            return SaResult.ok("退出成功");
        }
        return SaResult.ok("退出失败");
    }
}
