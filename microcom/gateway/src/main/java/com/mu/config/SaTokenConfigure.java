package com.mu.config;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MUZUKI
 * @Classname SaTokenConfigure
 * @Description [Sa-Token 权限认证] 配置类
 * @Date 2023/5/13 0:58
 */

//@Configuration
//public class SaTokenConfigure {
//    // 注册 Sa-Token全局过滤器
//    @Bean
//    public SaReactorFilter getSaReactorFilter() {
//        return new SaReactorFilter()
//                // 拦截地址  拦截全部path
//                .addInclude("/**")
//                // 开放地址
//                .addExclude("/favicon.ico")
//                // 鉴权方法：每次访问进入
//                .setAuth(obj -> {
//                    // 登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
//                    SaRouter.match("/**", "/framework/user/login", r -> StpUtil.checkLogin());
//
//                    // 权限认证 -- 不同模块, 校验不同权限
//                    SaRouter.match("/framework/**", r -> StpUtil.checkPermission("user"));
//
//                    // 更多匹配 ...  */
//                })
//                // 异常处理方法：每次setAuth函数出现异常时进入
//                .setError(e -> {
//                    return SaResult.error(e.getMessage());
//                })
//                ;
//    }
//}
