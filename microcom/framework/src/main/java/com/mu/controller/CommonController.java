package com.mu.controller;

import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MUZUKI
 * @Classname CommonController
 * @Description TODO
 * @Date 2023/5/13 1:35
 */

@RestController
public class CommonController {
    @GetMapping("/**")
    public SaResult index() {
        return SaResult.ok().setMsg("Hello World");
    }
}
