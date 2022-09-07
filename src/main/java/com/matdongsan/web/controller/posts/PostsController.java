package com.matdongsan.web.controller.posts;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import com.matdongsan.domain.posts.Posts;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.PlaceService;
import com.matdongsan.service.PostsService;
import com.matdongsan.web.dto.ReplyDto;
import com.matdongsan.web.dto.posts.PostCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;
    private final AccountService accountService;

    // 게시글 상세 조회
    @GetMapping("/posts/{id}")
    public String showDetailPost(@PathVariable long id,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 Model model, @ModelAttribute("replyDto") ReplyDto replyDto){

        Posts posts = postsService.findById(id);
        posts.getPlace(); //수동 초기화

        model.addAttribute("post" , posts);

        return "/posts/post-detail";
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
    public String newPost(Model model) {
        model.addAttribute("postCreateDto", new PostCreateDto());
        return "/posts/posts-newForm";
    }


    // 게시글 등록 posts
    @PostMapping("/posts/new")
    public String createPost(@Valid PostCreateDto postCreateDto , BindingResult bindingResult , Model model , Principal principal, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postCreateDto", postCreateDto);
            return "posts/posts-newForm";
        }
        Member currentMember = accountService.findAccountByUsername(principal.getName()).getMember();
        Posts newPosts = postsService.savePost(currentMember, postCreateDto);
        Long id = newPosts.getId();
        redirectAttributes.addAttribute("id", id);

        // 저장 완료 후 , 게시글 목록으로 간다.
        return "redirect:/posts/{id}";
    }

    // 게시글 수정 뷰 페이지
    @GetMapping("/posts/modify/{id}")
    public String modifyPost(@PathVariable Long id ,Model model) {

        Posts posts = postsService.findById(id);
        model.addAttribute("findPost", posts);

        return "posts/posts-updateForm";
    }



    @PostMapping("/posts/update/{id}")
    public String updatePost(@PathVariable Long id , Posts posts){

        Posts updatePost = postsService.findById(id);
        updatePost.change(posts.getTitle() , posts.getContent() , posts.getPlace() , posts.getImageUrls() , posts.isPrivateStatus());

//        return String.format("redirect:/posts/%s" , id);
        return "redirect:/posts/{id}";
    }
/*
    //
    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id){

        Posts posts = postsService.findById(id);
        postsService.delete(posts);

        return "redirect:/posts";
    }*/

}
