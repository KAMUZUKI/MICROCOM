package com.mu.controller;

import cn.hutool.extra.mail.MailUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : MUZUKI
 * @date : 2023-03-22 16:12
 * @description : 发送邮箱验证码
 **/

@RestController
@RequestMapping("/email")
public class EmailController {
    @RequestMapping("/send")
    public String sendEmail(@RequestParam("email") String email){
        MailUtil.send(email, "测试", "<h1>邮件来自Hutool测试</h1>", true);
        return "send email success";
    }
}

