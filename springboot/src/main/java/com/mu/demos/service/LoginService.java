package com.mu.demos.service;

import com.mu.demos.Event.LoginSuccessEvent;
import com.mu.demos.web.User;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author : MUZUKI
 * @date : 2023-06-27 15:14
 **/

@Service
public class LoginService {
    @Order(1)
    @EventListener
    public void onEvent(LoginSuccessEvent event){
        User user = (User) event.getSource();
        System.out.println("======LoginService======感知到事件");
        recordLogin(user.getName());
    }

    public void recordLogin(String username){
        System.out.println("======LoginService.sendCoupon======" + username + "登录成功");
    }
}
