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

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;

    //댓글 저장
    public void saveReply(Member member, Posts posts, ReplyDto replyDto) {
        Reply reply = Reply.builder()
                .comment(replyDto.getComment()).build();
        Reply savedReply = replyRepository.save(reply);     //id와 comment만 등록되어 있는 상태.
//        posts.addReply(Reply savedReply);                 //Reply에 Post객체 초기화
    }

    //댓글 업데이트
    public void updateReply(Long replyid, String comment) {
        Reply replyById = replyRepository.getById(replyid);
        replyById.updateComment(comment);

    }

    //댓글 삭제
    public void deleteReply() {

    }


}
