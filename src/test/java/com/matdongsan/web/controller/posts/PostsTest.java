package com.matdongsan.web.controller.posts;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.posts.PostsRepository;
import com.matdongsan.service.PostsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostsTest {

    @Autowired
    private PostsService postsService;

    @Test
    void newPost() {
        Posts posts = new Posts();
        posts.setId(1L);
        posts.setTitle("제목");
        posts.setContent("내용");
        posts.setPrivateStatus(true);
        posts.setAuthor(null);

        postsService.savePost("제목" , "내용" , true , null);
    }
}
