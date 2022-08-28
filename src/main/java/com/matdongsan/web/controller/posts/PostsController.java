package com.matdongsan.web.controller.posts;

import com.matdongsan.domain.posts.Posts;
import com.matdongsan.service.PostsService;
import com.matdongsan.web.dto.posts.PostsDto;
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

    // 게시글 상세 조회
    @GetMapping("/posts/{id}")
    public String showDetailPost(@PathVariable long id , Model model){

        Posts posts = postsService.findById(id);

        model.addAttribute("detail" , posts);

        return "/posts/posts-detail";
    }

    // 게시글 전체 조회
    @GetMapping("/posts")
    public String showAllPosts(Model model) {
        List<Posts> posts = postsService.findAll();

        model.addAttribute("postList", posts);

        return "/posts/posts-list";
    }

    // 게시글 등록 폼 페이지
    @GetMapping("/posts/new")
    public String newPost(){
        return "/posts/posts-newForm";
    }

    // 게시글 등록 posts
//    @PreAuthorize("isAuthenticated()")
    @PostMapping("/posts/new")
    public String createPost(Posts posts , Model model ){

        Posts newPosts = postsService.savePost(posts.getTitle() , posts.getContent() , posts.isPrivateStatus() , posts.getAuthor());
        model.addAttribute("savePost", newPosts);

        // 저장 완료 후 , 게시글 목록으로 간다.
        return "redirect:posts/posts-list";
    }

    // 게시글 수정 뷰 페이지
    @GetMapping("/posts/update/{id}")
    public String modifyPost(@PathVariable Long id ,Model model) {

        Posts posts = postsService.findById(id);

        model.addAttribute("findPost", posts);

        return "posts/posts-updateForm";
    }


    @PostMapping("/posts/update/{id}")
    public String editPost(@PathVariable Long id , PostsDto postsDto){

        Posts postsUpdate = postsService.findById(id);

        postsService.update(postsUpdate , postsDto.getTitle() , postsDto.getContent() , postsDto.getAuthor() , postsDto.getPrivateStatus());
        return String.format("redirect:/posts/%s" , id);
    }

    //
    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id){

        Posts posts = postsService.findById(id);
        postsService.delete(posts);

        return "redirect:/posts";
    }





}
