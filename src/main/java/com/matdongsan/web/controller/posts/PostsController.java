package com.matdongsan.web.controller.posts;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.posts.PostsRepository;
import com.matdongsan.domain.reply.Reply;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.PlaceService;
import com.matdongsan.service.PostsService;
import com.matdongsan.service.ReplyService;
import com.matdongsan.web.dto.ReplyDto;
import com.matdongsan.web.dto.posts.PostCreateDto;
import com.matdongsan.web.dto.posts.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;
    private final AccountService accountService;
    private final ReplyService replyService;
    private final PostsRepository postsRepository;

    // 게시글 상세 조회
    @GetMapping("/posts/{id}")
    public String showDetailPost(@PathVariable long id,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 Model model, @ModelAttribute("replyDto") ReplyDto replyDto){

        Page<Reply> paging = replyService.getReplyList(page);
        model.addAttribute("paging", paging);

        Posts posts = postsService.findById(id);
        posts.getPlace(); //수동 초기화

        model.addAttribute("post" , posts);

        return "/posts/post-detail";
    }

    // 게시글 전체 조회
    // 페이징 처리 하기
//    @GetMapping("/posts")
//    public String showAllPosts(Model model) {
//        List<Posts> posts = postsService.findAll();
//
//        model.addAttribute("postList", posts);
//
//        return "/posts/posts-list";
//    }

    @GetMapping("/posts")
    public String showAllPosts(Model model , @PageableDefault(sort = "id" , direction = Sort.Direction.DESC , size = 10)Pageable pageable){

        // 게시글 전체 조회
        Page<Posts> paging = postsService.getList(pageable);
        // model에 담기
        model.addAttribute("paging" , paging);

        return "posts/posts-list";
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
    public String modifyPost(@PathVariable Long id ,Model model) throws IOException {

        Posts posts = postsService.findById(id);

        // posts가 가지고 있는 image들을 list로 받아 와야한다.
        List<MultipartFile> imageList = postsService.getImageList(posts.getImageUrls());

        PostUpdateDto dto = new PostUpdateDto();
        dto.setId(posts.getId());
        dto.setTitle(posts.getTitle());
        dto.setContent(posts.getContent());
        dto.setPlaceName(posts.getPlace().getPlaceName());
        dto.setPrivateStatus(posts.isPrivateStatus());


        model.addAttribute("findPost", dto);
        model.addAttribute("imageList", imageList);

        return "posts/posts-updateForm";
    }


    @PostMapping("/posts/update/{id}")
    public String updatePost(@PathVariable Long id, PostUpdateDto updateDto) {

        Posts updatePost = postsService.findById(id);

        updatePost.change(updateDto.getTitle(), updateDto.getContent(), "", updateDto.getPrivateStatus());

        postsRepository.save(updatePost);

        return "redirect:/posts/{id}";
    }

    //
    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id){

        postsService.delete(id);

        return "redirect:/posts";
    }

}
