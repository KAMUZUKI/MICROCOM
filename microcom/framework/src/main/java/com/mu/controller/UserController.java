package com.mu.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.mu.domain.User;
import com.mu.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * @author MUZUKI
 * @Classname UserController
 * @Description TODO
 * @Date 2023/3/26 1:58
 */
@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "login")
    public SaResult login(HttpServletRequest request) {
        String username = request.getParameter("account");
        String password = request.getParameter("password");
        User user = userService.login(username, SecureUtil.md5(password));
        if (user == null || user.equals("")) {
            return SaResult.error("登录失败").setData(false);
        }
        user.setPwd(null);
        StpUtil.login(user.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return SaResult.ok(tokenInfo.tokenValue).setData(user);
    }

    /**
     * 查询登录状态  ---- http://localhost:8081/user/isLogin
     */
    @RequestMapping("isLogin")
    public SaResult isLogin() {
        return SaResult.ok("是否登录：" + StpUtil.isLogin());
    }

    /**
     * 查询 Token 信息  ---- http://localhost:8081/user/tokenInfo
     */
    @RequestMapping("tokenInfo/{token}")
    public SaResult tokenInfo(@PathVariable String token) {
        return SaResult.ok().setData(StpUtil.getLoginIdByToken(token));
    }

    /**
     * 注销  ---- http://localhost:8081/user/logout
     */
    @RequestMapping("logout/{token}")
    public SaResult logout(@PathVariable String token) {
        StpUtil.logoutByTokenValue(token);
        return SaResult.ok().setMsg("退出成功");
    }

    @SaCheckLogin
    @RequestMapping("updateUserById")
    public SaResult updateUserById(@RequestBody User user) {
        if (userService.updateUserById(user) == 1) {
            return SaResult.ok("修改成功").setData(true);
        }
        return SaResult.ok("修改失败").setData(false);
    }

    @RequestMapping("register")
    public SaResult register(HttpServletRequest request) {
        User user = ServletUtil.toBean(request, User.class, true);
        user.setPwd(SecureUtil.md5((user.getPwd())));
        if (userService.register(user) == 1) {
            return SaResult.ok("注册成功").setData(true);
        }
        return SaResult.ok("注册失败").setData(false);
    }

    @SaCheckLogin
    @RequestMapping("getAllUser")
    public SaResult getAllUser() {
        return SaResult.ok("获取成功").setData(userService.getAllUser());
    }

    @SaCheckLogin
    @RequestMapping("getLikeList")
    public SaResult getLikeList(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("userId"));
        return SaResult.ok("获取喜欢列表成功").setData(userService.getLikeList(id));
    }

    @RequestMapping("getUserById/{userId}")
    public SaResult getUserById(@PathVariable("userId") String userId) {
        return SaResult.ok("获取用户成功").setData(userService.getUserById(userId));
    }

    //    @SaCheckLogin
    @RequestMapping("follow/{userId}/{followUserId}")
    public SaResult follow(@PathVariable("userId") String userId, @PathVariable("followUserId") String followUserId) {
        if (userService.follow(userId, followUserId)) {
            return SaResult.ok("关注成功").setData(true);
        }
        return SaResult.ok("关注失败").setData(false);
    }

    @RequestMapping("isFollow/{userId}/{followUserId}")
    public SaResult isFollow(@PathVariable("userId") String userId, @PathVariable("followUserId") String followUserId) {
        if (userService.isFollow(userId, followUserId)) {
            return SaResult.ok("已关注").setData(true);
        }
        return SaResult.ok("未关注").setData(false);
    }

    //    @SaCheckLogin
    @RequestMapping("unfollow/{userId}/{followUserId}")
    public SaResult unfollow(@PathVariable("userId") String userId, @PathVariable("followUserId") String followUserId) {
        if (userService.unfollow(userId, followUserId)) {
            return SaResult.ok("取消关注成功").setData(true);
        }
        return SaResult.error("取消关注失败").setData(false);
    }

    //    @SaCheckLogin
    @RequestMapping("getFollowers/{userId}")
    public SaResult getFollowers(@PathVariable("userId") String userId) {
        return SaResult.ok("获取粉丝列表").setData(userService.getFollowers(userId));
    }

    //    @SaCheckLogin
    @RequestMapping("getFollowing/{userId}")
    public SaResult getFollowing(@PathVariable("userId") String userId) {
        return SaResult.ok("获取关注列表").setData(userService.getFollowing(userId));
    }

    @RequestMapping("getInterconnection/{userId}")
    public SaResult getInterconnection(@PathVariable("userId") String userId) {
        return SaResult.ok("获取互相关注列表").setData(userService.getInterconnections(userId));
    }
}
