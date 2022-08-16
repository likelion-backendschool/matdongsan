package com.matdongsan.web.controller.posts;

import com.matdongsan.domain.posts.Posts;
import com.matdongsan.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    // 게시글 상세 페이지
    @GetMapping("/post/{id}")
    public String showDetailPost(@PathVariable long id , Model model){

        Posts postDetail = postsService.findById(id);

        model.addAttribute("detail" , postDetail);

        return "/posts/posts-detail";
    }

    // 게시글 전체 조회
    @GetMapping("/posts")
    public String showAllPosts(Model model) {
        List<Posts> postsList = postsService.findAll();

        model.addAttribute("postList", postsList);

        return "posts/posts-details";
    }

    // 게시글 등록 폼 페이지
    @GetMapping("/post/new")
    public String newPost(){
        return "/posts/posts-newForm";
    }

    // 게시글 등록 posts
    @PostMapping("/post/create")
    public String createPost(Posts post){

        postsService.create(post);

        return "redirect:posts/posts-details";
    }

}
