package com.mu.system.SensitiveWord;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * @author MUZUKI
 * @Classname SensitiveTest
 * @Description TODO
 * @Date 2023/3/29 14:58
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SpringExtension.class})
public class SensitiveTest {
    @Autowired
    SensitiveFilter sensitiveFilter;

    @Test
    public void test(){
        String str = "hahaha";
        String oldValue = str;
        int newValue = sensitiveFilter.CheckSensitiveWord(str, 0, 2);
        System.out.println(newValue);
    }

    @Test
    public void test1(){
        // 获取当前项目路径
        String projectPath = System.getProperty("user.dir");
        File file = new File(projectPath + "/src/main/resources/sensitiveWordList.txt");

        // 输出当前项目路径
        System.out.println("Current project path: " + projectPath);
    }
}
