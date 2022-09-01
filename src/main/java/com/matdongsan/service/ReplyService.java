package com.matdongsan.service;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberRepository;
import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.posts.PostsRepository;
import com.matdongsan.domain.reply.Reply;
import com.matdongsan.domain.reply.ReplyRepository;
import com.matdongsan.web.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;

    public List<Reply> getReplyList(Long id) {
        return replyRepository.findByPostsId(id);
    }

    /**
     * 댓글저장
     */
    public void saveReply(Posts post, ReplyDto replyDto, Long id) {
        Optional<Member> byId = memberRepository.findById(id);
        Reply reply = Reply.builder()
                .comment(replyDto.getComment())
                .createComment(LocalDateTime.now())
                .writer(byId.get())////
                .posts(post)
                .build();
        Reply savedReply = replyRepository.save(reply);     //id와 comment만 등록되어 있는 상태.
        post.addReply(savedReply);                 //Reply에 Post객체 초기화
    }

    /**
     * id로 댓글 객체 리턴
     */
    public Reply getReply(Long replyId) {
        Optional<Reply> reply = replyRepository.findById(replyId);
        return reply.get();
    }

    /**
     * 댓글 수정
     */
    public void update(Reply reply, String content) {
        reply.updateComment(content);
        replyRepository.save(reply);
    }


    /**
     * 댓글 삭제
     */

    public void deleteReply(Reply reply) {
        replyRepository.delete(reply);
    }


}