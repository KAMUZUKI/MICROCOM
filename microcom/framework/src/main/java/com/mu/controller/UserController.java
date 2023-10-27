package com.mu.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.mu.entity.User;
import com.mu.service.impl.UserServiceImpl;
import com.mu.utils.SystemUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author MUZUKI
 * @Classname UserController
 * @Description 用户控制类
 * @Date 2023/3/26 1:58
 */
@RestController
@RequestMapping("/user/")
@Slf4j
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "login")
    public SaResult login(HttpServletRequest request) {
        String username = request.getParameter("account");
        String password = request.getParameter("password");
        String ip = SystemUtils.getClientIP(request);
        User user = userService.login(username, SecureUtil.md5(password));
        if (user == null) {
            log.info("账号{}登录失败,IP地址为{}", username,ip);
            return SaResult.error("登录失败").setData(false);
        }
        if (("0").equals(user.getStatus())){
            return SaResult.error("账号已被封禁，请联系管理员").setData(false);
        }
        user.setPwd(null);
        StpUtil.login(user.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        log.info("账号{}登录成功,IP地址为{}", username,ip);
        return SaResult.ok(tokenInfo.tokenValue).setData(user);
    }

    @RequestMapping("adminLogin")
    public SaResult adminLogin(HttpServletRequest request) {
        String username = request.getParameter("account");
        String password = request.getParameter("password");
        User user = userService.login(username, SecureUtil.md5(password));
        if (!user.getType().equals("1")) {
            // 用户不具有管理员权限
            return SaResult.error("该用户不具有管理员权限").setData(false);
        }
        if (user==null) {
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

    @SaCheckRole("admin")
    @SaCheckLogin
    @RequestMapping("updateUserStatus/{userId}/{status}")
    public SaResult updateUserStatus(@PathVariable("userId") String userId, @PathVariable("status") String status) {
        if (userService.updateUserStatus(userId,status) == 1) {
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
    @SaCheckRole("admin")
    @RequestMapping("getAllUser")
    public SaResult getAllUser(@RequestParam Map<String, String> map) {
        return SaResult.ok("获取成功").setData(userService.getAllUser(map));
    }

    @RequestMapping("getUserRole/{userId}")
    public SaResult getUserRole(@PathVariable("userId") String userId) {
        return SaResult.ok("获取成功").setData(userService.getUserRole(userId));
    }

    @SaCheckLogin
    @RequestMapping("getLikeList")
    public SaResult getLikeList(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("userId"));
        return SaResult.ok("获取喜欢列表成功").setData(userService.getLikeList(id));
    }

    @RequestMapping("getUserInfo/{tokenInfo}")
    public SaResult getUserInfo(@PathVariable("tokenInfo") String tokenInfo) {
        String userId = StpUtil.getLoginIdByToken(tokenInfo).toString();
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
