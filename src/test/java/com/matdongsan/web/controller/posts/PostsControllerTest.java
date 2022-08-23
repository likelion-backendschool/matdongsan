package com.matdongsan.web.controller.posts;

import com.matdongsan.domain.reply.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostsControllerTest {

    @Test
    public void inputTest() {
        String content = "asdf";

        Reply reply = Reply.builder()
                .comment(content)
                .build();


    }

}