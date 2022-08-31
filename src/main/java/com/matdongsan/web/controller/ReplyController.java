package com.matdongsan.web.controller;


import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.posts.PostsRepository;
import com.matdongsan.domain.reply.Reply;
import com.matdongsan.domain.reply.ReplyRepository;
import com.matdongsan.infra.SecurityUser;
import com.matdongsan.service.PostsService;
import com.matdongsan.service.ReplyService;
import com.matdongsan.web.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final PostsService postsService;
    private final ReplyService replyService;
    private final PostsRepository postsRepository;
    private final ReplyRepository replyRepository;
    ReplyDto replyDto = new ReplyDto();

    /**댓글등록 (버튼)
     *
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/posts/{postId}")
    public String createReply(Model model,
                              @PathVariable("postId") Long id,
                              @RequestParam String content,
                              @Valid ReplyDto replyDto, BindingResult bindingResult,
                              @AuthenticationPrincipal SecurityUser securityUser) {
        Posts post = postsService.findById(id);
        ReplyDto replyDto2 = replyDto.builder().comment(content).build();
        Long memberId = securityUser.getAccount().getMember().getId(); //securityuser . account . member . id가져오기
        replyService.saveReply(post, replyDto2, memberId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "/posts/post-detail";
        }
        return "redirect:/posts/{postId}";

    }

    /**
     * 댓글 수정 컨트롤러 get, post
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reply/update/{replyId}")
    public String updateReply(@ModelAttribute("replyDto") ReplyDto replyDto,
                              @PathVariable("replyId") Long id, Principal principal) {
        Reply reply = replyService.getReply(id);
        if (!reply.getWriter().getAccount().getUsername().equals(principal.getName())) {    //??name?
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }
//        replyDto.builder().comment(reply.getComment());
//        replyDto.setComment(reply.getComment());    //builder로 하니까 값이 안넘어가지는 이유???
        replyDto.editComment(reply.getComment());
        return "/reply/reply-editForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reply/update/{replyId}")
    public String updateReply(@PathVariable("replyId") Long id, @Valid ReplyDto replyDto) {
//파라미터로
//        Principal principal,
//        BindingResult bindingResult,

//        if (bindingResult.hasErrors()) {
//            return "/reply/reply-editForm";
//        }
        Reply reply = replyService.getReply(id);
        replyService.update(reply, replyDto.getComment());
        return String.format("redirect:/posts/%s", reply.getPosts().getId());}


//    @PutMapping("/update/{replyId}")
//    public void updateReply(@PathVariable Long replyId,
//                            @RequestBody String comment) throws Exception {
//        replyService.updateReply(replyId, comment);
//    }


    @GetMapping()
    @DeleteMapping("/post/{postId}/reply/{replyId}")
    public void deleteReply(@PathVariable Long postId,
                            @PathVariable Long replyId,
                            @RequestBody ReplyDto replyDto) {
        replyService.deleteReply(replyId);
    }

}
