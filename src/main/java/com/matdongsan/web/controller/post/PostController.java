package com.matdongsan.web.controller.post;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AuthUser;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.post.Post;

import com.matdongsan.domain.post.PostRepository;

import com.matdongsan.domain.reply.Reply;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.LikeApiService;
import com.matdongsan.service.PostService;
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

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final AccountService accountService;
    private final ReplyService replyService;

    private final PostRepository postRepository;

    private final LikeApiService likeApiService;


    // 게시글 상세 조회
    @GetMapping("/post/{id}")
    public String showDetailPost(@PathVariable long id,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 Model model, @ModelAttribute("replyDto") ReplyDto replyDto,
                                 @AuthUser Account account) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);

        List<Reply> replyList = post.getReplyList();
        replyService.refreshTime(replyList);

        Page<Reply> paging = replyService.getReplyList(page, id);
        model.addAttribute("paging", paging);

        boolean likeFlag = likeApiService.existPostLikeFlag(account.getMember(), post);
        model.addAttribute("likeFlag", likeFlag);


        return "/post/post-detail";
    }

    @GetMapping("/posts")
    public String showAllPosts(@RequestParam(defaultValue = "")String keyword,
                               @RequestParam(defaultValue = "0") int page,
                               Model model ,
                               @PageableDefault(sort = "id" , direction = Sort.Direction.DESC , size = 10)Pageable pageable){

        // 게시글 전체 조회
        Page<Post> paging = postService.getList(keyword , page , pageable);
        // model에 담기
        model.addAttribute("paging" , paging);

        return "/post/posts-list";
    }


    // 게시글 등록 폼 페이지
    @GetMapping("/post/new")
    public String newPost(Model model) {

        model.addAttribute("postCreateDto", new PostCreateDto());
        return "/post/post-newForm";
    }


    // 게시글 등록 post
    @PostMapping("/post/new")
    public String createPost(@Valid PostCreateDto postCreateDto , BindingResult bindingResult , Model model , Principal principal, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postCreateDto", postCreateDto);
            return "/post/post-newForm";
        }
        Member currentMember = accountService.findAccountByUsername(principal.getName()).getMember();
        Post newPost = postService.savePost(currentMember, postCreateDto);
        Long id = newPost.getId();
        redirectAttributes.addAttribute("id", id);

        // 저장 완료 후 , 게시글 목록으로 간다.
        return "redirect:/post/{id}";
    }

    // 게시글 수정 뷰 페이지
    @GetMapping("/post/modify/{id}")
    public String modifyPost(@PathVariable Long id ,Model model) throws IOException {

        Post post = postService.findById(id);

        // posts가 가지고 있는 image들을 list로 받아 와야한다.
        List<MultipartFile> imageList = postService.getImageList(post.getImageUrls());

        PostUpdateDto dto = new PostUpdateDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setPlaceName(post.getPlace().getPlaceName());
        dto.setPrivateStatus(post.isPrivateStatus());


        model.addAttribute("findPost", dto);
        model.addAttribute("imageList", imageList);

        return "/post/post-updateForm";
    }


    @PostMapping("/post/update/{id}")
    public String updatePost(@PathVariable Long id, PostUpdateDto updateDto) {

        Post updatePost = postService.findById(id);

        updatePost.change(updateDto.getTitle(), updateDto.getContent(), "", updateDto.getPrivateStatus());

        postRepository.save(updatePost);

        return "redirect:/post/{id}";
    }

    //
    @GetMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Long id){

        postService.delete(id);

        return "redirect:/posts";
    }

}
