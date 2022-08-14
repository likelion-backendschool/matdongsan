package com.matdongsan.web.controller.posts;

import com.matdongsan.domain.posts.Posts;
import com.matdongsan.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    // 게시글 상세 페이지
    @GetMapping("/detail/{id}")
    public String showDetailPost(@PathVariable long id , Model model){

        Posts postDetail = postsService.findById(id);

        model.addAttribute("detail" , postDetail);

        return "/posts-detail";
    }
}
