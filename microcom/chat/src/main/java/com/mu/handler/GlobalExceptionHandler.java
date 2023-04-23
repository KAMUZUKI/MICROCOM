package com.mu.handler;

import cn.dev33.satoken.util.SaResult;
import com.mu.exception.GlobalException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局Runtime异常处理器
 *
 * @author tycoding
 * @date 2019-06-17
 */
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public SaResult exception(Exception e) {
        e.printStackTrace();
        return SaResult.error().setMsg("系统异常");
    }

    @ExceptionHandler(value = GlobalException.class)
    public SaResult globalException(GlobalException e) {
        e.printStackTrace();
        return SaResult.error().setMsg(e.getMsg());
    }

}
