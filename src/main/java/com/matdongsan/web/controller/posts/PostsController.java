package com.matdongsan.web.controller.posts;

import com.matdongsan.domain.posts.Posts;
import com.matdongsan.service.PostsService;
import com.matdongsan.web.dto.posts.PostsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public String createPost(PostsDto postsDto , Model model){

        Long savePostId = postsService.savePost(postsDto);
        model.addAttribute("savePost", savePostId);

        // 생성된 게시글을 가지고 와서 보여준다.
        return "redirect:posts/posts-details";
    }

    // 게시글 수정 뷰 페이지
    @GetMapping("/post/update/{id}")
    public String modifyPost(@PathVariable Long id ,Model model) {

        Posts findPost = postsService.findById(id);
        model.addAttribute("findPost", findPost);

        return "posts/posts-update";
    }


    @PatchMapping("/post/edit")
    public String editPost(@PathVariable Long id , PostsDto postsDto){
        Posts post = postsService.update(id , postsDto);
        return "redirect:posts/posts-details";
    }

    //
    @GetMapping("/detail/delete/{id}")
    public String deletePost(@PathVariable Long id){
        postsService.delete(id);
        return "redirect:posts/posts-details";
    }





}
