package com.mu.service;

import com.mu.entity.EvaluationConstants;
import com.mu.mapper.UserPreferenceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author MUZUKI
 * @Classname UserActionServiceTest
 * @Description TODO
 * @Date 2023/5/24 14:22
 */

@SpringBootTest
public class UserActionServiceTest {

    @Autowired(required = false)
    private UserActionService userActionService;

    @Autowired(required = false)
    private UserPreferenceMapper userPreferenceMapper;

    @Test
    void testAddVlogComment() {
        userActionService.evaluationScore(1,1, EvaluationConstants.LEVEL1);
    }

    @Test
    void testGetIidByUserId() {
        userPreferenceMapper.getIidListByUserId(1L).forEach(System.out::println);
    }

    @Test
    void testGetUidList() {
        userPreferenceMapper.getUidList().forEach(System.out::println);
    }

    @Test
    void testGetUserPreference() {
        List<Long> uidList = userPreferenceMapper.getUidList();
        Stream.of(uidList).forEach(System.out::println);
    }
}
