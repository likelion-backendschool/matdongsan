package com.matdongsan.web.controller;


import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.posts.PostsRepository;
import com.matdongsan.domain.reply.Reply;
import com.matdongsan.domain.reply.ReplyRepository;
import com.matdongsan.service.PostsService;
import com.matdongsan.service.ReplyService;
import com.matdongsan.web.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final PostsService postsService;
    private final ReplyService replyService;
    private final PostsRepository postsRepository;
    private final ReplyRepository replyRepository;
    ReplyDto replyDto = new ReplyDto();

    //댓글등록 (버튼)
    @PostMapping("/posts/{postId}")
    public String createReply(@PathVariable("postId") Long id,
                           @RequestParam String content) {
        Posts post = postsService.findById(id);
        ReplyDto replyDto2 = replyDto.builder().comment(content)
                .build();

        replyService.saveReply(post, replyDto2);
        return "redirect:/posts/{postId}";
    }

    @GetMapping("/post/{postId}/reply")   //post단 id연관시켜 수정필요, PathVariable
    public List<Reply> getReplyList(@PathVariable("postId") Long id) {

        return replyService.getReplyList(id);
    }



    @PostMapping("/post/{postId}/reply")
    public ResponseEntity saveReply(@PathVariable("postId") Long id,
                                    @RequestBody ReplyDto replyDto,
                                    @AuthenticationPrincipal Account account) {
        Posts post = postsRepository.findById(id).orElse(null);
        if (post == null) {
            return ResponseEntity.badRequest().build();
        }
//        replyService.saveReply(account.getMember(), post, replyDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/post/{postId}/reply/{replyId}")
    public void updateReply(@PathVariable Long postId,
                            @PathVariable Long replyId,
                            @RequestBody ReplyDto replyDto) throws Exception {
        replyService.updateReply(replyId, replyDto.getComment());
    }


    @DeleteMapping("/post/{postId}/reply/{replyId}")
    public void deleteReply(@PathVariable Long postId,
                              @PathVariable Long replyId,
                              @RequestBody ReplyDto replyDto){
        replyService.deleteReply(replyId);
    }

}
