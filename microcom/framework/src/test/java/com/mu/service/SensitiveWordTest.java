package com.mu.service;

import com.github.houbb.sensitive.word.core.SensitiveWordHelper;
import org.junit.jupiter.api.Test;

/**
 * @author : zengzw5
 * @date : 2023-10-11 11:00
 **/

public class SensitiveWordTest {
    @Test
    public void test01() {
        final String text = "五星红旗迎风飘扬，毛主席的画像屹立在天安门前。";

        String word = SensitiveWordHelper.replace(text);
        System.out.println(word);
    }
}
