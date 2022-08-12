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

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;

    //댓글 저장
    public void saveReply(Member member, Posts posts, ReplyDto replyDto) {

    }

    //댓글 업데이트
    public void updateReply(Long id, String comment) {
        Reply replyById = replyRepository.getById(id);
        replyById.updateComment(comment);

    }

    //댓글 삭제
    public void deleteReply() {

    }


}
