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

import java.util.List;

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
    public void saveReply(Member member, Posts post, ReplyDto replyDto) {
        Reply reply = Reply.builder()
                .comment(replyDto.getComment()).build();
        Reply savedReply = replyRepository.save(reply);     //id와 comment만 등록되어 있는 상태.
        post.addReply(savedReply);                 //Reply에 Post객체 초기화
        member.addReply(savedReply);  //Member클래스에 reply추가 + member정보 Reply에 초기
    }

    /**
     * 댓글 수정
     */
    public void updateReply(Long replyid, String comment) {
        Reply replyById = replyRepository.findById(replyid).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 존재하지 않습니다")
        );
        replyById.updateComment(comment);

    }

    /**
     * 댓글 삭제
     */
    public void deleteReply(Long replyId) {
        Reply deleteReply = replyRepository.findById(replyId).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

//        Posts post = postsRepository.findById(postId).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//
//        Member member = memberRepository.findById(userId).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );

        replyRepository.deleteById(deleteReply.getId());

    }


}