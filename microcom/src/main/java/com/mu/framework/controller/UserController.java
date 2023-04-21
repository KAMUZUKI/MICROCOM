package com.mu.framework.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.crypto.SecureUtil;
import com.mu.framework.domain.User;
import com.mu.framework.service.impl.UserServiceImpl;
import com.mu.framework.utils.HttpUtils;
import com.mu.framework.model.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
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
        JsonModel jm = new JsonModel();
        String username = request.getParameter("account");
        String password = request.getParameter("password");
        User user = userService.login(username, SecureUtil.md5(password));
        if (user == null || user.equals("")) {
            jm.setCode(0).setMsg("用户名或密码错误");
            return SaResult.error("登录失败").setCode(0).setMsg("登录失败");
        }
        StpUtil.login(user.getId());
        return SaResult.ok("登录成功").setCode(1).setMsg("登录成功").setData(user);
    }

    // 查询登录状态  ---- http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public SaResult isLogin() {
        return SaResult.ok("是否登录：" + StpUtil.isLogin());
    }

    // 查询 Token 信息  ---- http://localhost:8081/user/tokenInfo
    @RequestMapping("tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    // 测试注销  ---- http://localhost:8081/user/logout
    @RequestMapping("logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

    @SaCheckLogin
    @RequestMapping(value = "updateUserById")
    public SaResult updateUserById(HttpServletRequest request) {
        User user = HttpUtils.parseRequestToT(request,User.class);
        if (userService.updateUserById(user) == 1) {
            return SaResult.ok("修改成功").setCode(1);
        }
        return SaResult.ok("修改失败").setCode(0);
    }

    @RequestMapping(value = "register")
    public SaResult register(HttpServletRequest request) {
        User user = HttpUtils.parseRequestToT(request,User.class);
        user.setPwd(SecureUtil.md5((user.getPwd())));
        if (userService.register(user) == 1) {
            return SaResult.ok("注册成功").setCode(1);
        }
        return SaResult.ok("注册失败").setCode(0);
    }

    @SaCheckLogin
    @RequestMapping(value = "getAllUser")
    public SaResult getAllUser() {
        return SaResult.ok("获取成功").setCode(1).setData(userService.getAllUser());
    }

    @SaCheckLogin
    @RequestMapping(value = "getLikeList")
    public SaResult getLikeList(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("userId"));
        return SaResult.ok("获取喜欢列表成功").setCode(1).setData(userService.getLikeList(id));
    }

}
