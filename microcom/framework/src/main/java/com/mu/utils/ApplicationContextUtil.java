package com.mu.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author MUZUKI
 * @Classname ApplicationContextUtil
 * @Description 配置类，解决定时任务无法注入的问题
 * @Date 2023/5/26 0:01
 */

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;

    }


    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
}