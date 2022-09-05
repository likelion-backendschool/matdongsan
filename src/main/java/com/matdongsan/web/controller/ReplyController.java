package com.matdongsan.web.controller;


import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AuthUser;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.reply.Reply;
import com.matdongsan.service.MemberService;
import com.matdongsan.service.PostsService;
import com.matdongsan.service.ReplyService;
import com.matdongsan.web.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final PostsService postsService;
    private final ReplyService replyService;
    private final MemberService memberService;

    /**
     * 댓글등록 (버튼)
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/posts/{postId}")
    public String createReply(@PathVariable("postId") Long id,
                              @Valid ReplyDto replyDto, BindingResult bindingResult,
                              @AuthUser Account account) {
        if (bindingResult.hasErrors()) {
            log.info("값이 들어가지 않습니다. : " + replyDto.getComment());
            return "redirect:/posts/{postId}";
        }
        Posts post = postsService.findById(id);
        Long memberId = account.getMember().getId(); //securityuser . account . member . id가져오기
        replyService.saveReply(post, replyDto, memberId);
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
        if (!reply.getWriter().getAccount().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

        replyDto.editComment(reply.getComment());
        return "/reply/reply-editForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reply/update/{replyId}")
    public String updateReply(@PathVariable("replyId") Long id, @Valid ReplyDto replyDto) {

        Reply reply = replyService.getReply(id);
        replyService.update(reply, replyDto.getComment());
        return String.format("redirect:/posts/%s", reply.getPosts().getId());
    }

    /**
     * 댓글 삭제
     * @param id
     * @return
     */

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reply/delete/{replyId}")
    public String deleteReply(@PathVariable("replyId") Long id) {
        Reply reply = replyService.getReply(id);
        replyService.deleteReply(reply);
        return String.format("redirect:/posts/%s", reply.getPosts().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reply/like/{id}")
    public String replyLike(Principal principal, @PathVariable("id") Long id) {
        Reply reply = replyService.getReply(id);
        Member member = memberService.getMember(principal.getName());
        replyService.plusReplyLike(reply,member);

        return String.format("redirect:/posts/%s", reply.getPosts().getId());
    }
}
