package com.matdongsan.web.controller.like;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AuthUser;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.reply.Reply;
import com.matdongsan.service.LikeApiService;
import com.matdongsan.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LikeApiController {

    private final ReplyService replyService;
    private final LikeApiService likeApiService;

    // Post
    @ResponseBody
    @PostMapping("/post/modify/like")
    public boolean modifyPostLike(@AuthUser Account account, @RequestParam Map<String, String> params) {
        Long postId = Long.valueOf(params.get("postNum"));

        // 좋아요가 새로 생겼다면 true, 기존에 좋아요가 있었다면 false
        return likeApiService.modifyLikeStatus(account.getMember(), postId, "post");
    }

    // Reply
    @ResponseBody
    @PostMapping("/reply/modify/like")
    public boolean modifyReplyLike(@AuthUser Account account, @RequestParam Map<String, String> params) {
        Long replyId = Long.parseLong(params.get("replyNum"));

        // 좋아요가 새로 생겼다면 true, 기존에 좋아요가 있었다면 false
        return likeApiService.modifyLikeStatus(account.getMember(), replyId, "reply");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reply/like/{id}")
    public String replyLike(@AuthUser Account account, Principal principal, @PathVariable("id") Long id) {
        Reply reply = replyService.getReply(id);
        Member member = account.getMember();

        replyService.plusReplyLike(reply,member);

        return String.format("redirect:/post/%s", reply.getPost().getId());
    }
}
