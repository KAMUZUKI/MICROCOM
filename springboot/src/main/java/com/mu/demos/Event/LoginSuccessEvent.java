package com.mu.demos.Event;

import org.springframework.context.ApplicationEvent;

/**
 * @author : MUZUKI
 * @date : 2023-06-27 14:55
 * @description : 登录成功事件
 **/

public class LoginSuccessEvent extends ApplicationEvent {
    public LoginSuccessEvent(Object source) {
        super(source);
    }
}
