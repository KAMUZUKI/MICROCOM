package com.mu;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author : zengzw5
 * @date : 2023-10-18 10:10
 **/

@Slf4j
public class LogTest {
    @Test
    public void test(){
        long l = System.currentTimeMillis();
        log.debug("This is debug");
        long r = System.currentTimeMillis();
        System.out.println(r - l);
        log.info("This is info");
        log.warn("This is warn");
        log.error("This is error");
        log.error("This is exception", new RuntimeException("this is a exception"));

    }
}