package com.mu.demos.service;

import com.mu.demos.Event.LoginSuccessEvent;
import com.mu.demos.web.User;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author : MUZUKI
 * @date : 2023-06-27 14:52
 * @description : 优惠券服务
 **/

@Service
public class CouponService {
    @Order(1)
    @EventListener
    public void onEvent(LoginSuccessEvent event){
        User user = (User) event.getSource();
        System.out.println("======CouponService======感知到事件");
        sendCoupon(user.getName());
    }

    public void sendCoupon(String username){
        System.out.println("======CouponService.sendCoupon======" + username + "领取了优惠券");
    }
}
