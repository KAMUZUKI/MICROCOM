package com.mu.blog.meilisearch;

import com.mu.meilisearch.service.SearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


/**
 * @author : MUZUKI
 * @date : 2023-04-02 15:15
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SpringExtension.class})
public class MeiliSearchTest {

        @Autowired
        private SearchService searchService;

        @Test
        public void test() {
            System.out.println(searchService.search("movies", ""));
        }
}
