package com.matdongsan.web.controller.post;

import com.matdongsan.domain.reply.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostControllerTest {

    @Test
    public void inputTest() {
        String content = "asdf";

        Reply reply = Reply.builder()
                .comment(content)
                .build();


    }

}