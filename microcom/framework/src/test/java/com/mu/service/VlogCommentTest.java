package com.mu.service;

import com.mu.domain.VlogComment;
import com.mu.service.impl.VlogCommentServiceImpl;
import com.mu.utils.CoreUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @author : MUZUKI
 * @date : 2023-04-25 14:38
 **/

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VlogCommentTest {

    @Autowired
    private VlogCommentServiceImpl vlogCommentService;

    @Test
    public void testSaveComment() {
        for (long i = 4; i < 8; i++) {
            VlogComment comment = new VlogComment();
            comment.setId(i).setContent("Comment Loss it knife" + i).setVlogId(2L)
                    .setAuthor("lisi" + i).setTime(CoreUtils.format(new Date()));
            vlogCommentService.saveComment(comment);
        }
    }

    @Test
    public void testGetComment() {
        List<VlogComment> comments = vlogCommentService.findByVlogId(3L);
        System.out.println(comments);
    }

    @Test
    public void testGetChildrenComments() {
        List<VlogComment> childrenComments = vlogCommentService.findChildren(1L, 1L);
        System.out.println(childrenComments);
    }

    @Test
    public void testDeleteComment() {
        VlogComment comment = new VlogComment();
        comment.setId(1L).setVlogId(1L).setParentId(1L);
        System.out.println(vlogCommentService.deleteComment(comment));
    }
}
