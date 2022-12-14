package com.matdongsan.web.controller;


import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AuthUser;
import com.matdongsan.domain.post.Post;
import com.matdongsan.domain.reply.Reply;
import com.matdongsan.service.MemberService;
import com.matdongsan.service.PostService;
import com.matdongsan.service.ReplyService;
import com.matdongsan.web.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final PostService postService;
    private final ReplyService replyService;
    private final MemberService memberService;

    /**
     * 댓글등록 (버튼)
     */
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @RequestMapping(value = "/post/{postId}/reply", method = {RequestMethod.POST})
    public ResponseEntity createReply(@RequestParam(value = "COMMENT") String comment,
                              @PathVariable("postId") Long id,
                              @Valid ReplyDto replyDto, BindingResult bindingResult,
                              @AuthUser Account account) {
        if (bindingResult.hasErrors()) {
            log.info("값이 들어가지 않습니다. : " + replyDto.getComment());
            return ResponseEntity.badRequest().build();
        }
        Post post = postService.findById(id);

        Long memberId = account.getMember().getId(); //securityuser . account . member . id가져오기
        Long replyId = replyService.saveReply(post, replyDto, memberId, comment);
        Reply reply = replyService.getReply(replyId);

        ReplyDto newReplyDto = ReplyDto.builder()
                .id(reply.getId())
                .nickname(reply.getWriter().getNickname())
                .replyLikeCount(reply.getReplyLike().size())
                .replyTime(reply.getReplyTime())
                .comment(reply.getComment())
                .build();

        log.info("값이 들어갔습니다 = " + comment);
        return ResponseEntity.ok(newReplyDto);
    }

    /**
     * 댓글 수정
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reply/update/{replyId}")
    public String updateReply(@ModelAttribute("replyDto") ReplyDto replyDto,
                              @PathVariable("replyId") Long id, Principal principal) {
        Reply reply = replyService.getReply(id);
        if (!reply.getWriter().getAccount().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
        }

        replyDto.insertComment(reply.getComment());
        return "/reply/reply-editForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reply/update/{replyId}")
    public String updateReply(@PathVariable("replyId") Long id, @Valid ReplyDto replyDto) {

        Reply reply = replyService.getReply(id);
        replyService.update(reply, replyDto.getComment());
        return String.format("redirect:/post/%s", reply.getPost().getId());
    }

    /**
     * 댓글 삭제
     *
     * @param id
     * @return
     */

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reply/delete/{replyId}")
    public String deleteReply(@PathVariable("replyId") Long id) {
        Reply reply = replyService.getReply(id);
        replyService.deleteReply(reply);
        return String.format("redirect:/post/%s", reply.getPost().getId());
    }
}
