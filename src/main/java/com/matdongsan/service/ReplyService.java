package com.matdongsan.service;

import com.matdongsan.domain.like.ReplyLike;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberRepository;
import com.matdongsan.domain.post.Post;
import com.matdongsan.domain.post.PostRepository;
import com.matdongsan.domain.reply.Reply;
import com.matdongsan.domain.reply.ReplyRepository;
import com.matdongsan.domain.reply.ReplyTime;
import com.matdongsan.web.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    /**
     * 댓글저장
     */
    public void saveReply(Post post, ReplyDto replyDto, Long id) {
        Optional<Member> byId = memberRepository.findById(id);
        Reply reply = Reply.builder()
                .comment(replyDto.getComment())
                .createDate(LocalDateTime.now())
                .writer(byId.get())////
                .post(post)
                .build();
        Reply savedReply = replyRepository.save(reply);     //id와 comment만 등록되어 있는 상태.
        post.addReply(savedReply);                 //Reply에 Post객체 초기화
    }

    /**
     * reply id로 댓글 객체 리턴
     */
    public Reply getReply(Long replyId) {
        Optional<Reply> reply = replyRepository.findById(replyId);
        return reply.get();
    }

    /**
     *post id로 페이징 댓글 가져오기
     */

    public Page<Reply> getReplyList(int page,Long id) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate")); //수정 댓글 리팩토링 필요
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return replyRepository.findRepliesByPostId(id, pageable);
    }

    /**
     * 댓글 수정
     */
    public void update(Reply reply, String content) {
        reply.updateComment(content);
        reply.insertReplyTime(convertDateTime(LocalDateTime.now()));
        replyRepository.save(reply);
    }


    /**
     * 댓글 삭제
     */
    public void deleteReply(Reply reply) {
        replyRepository.delete(reply);
    }

    /**
     * 댓글 좋아요 추가
     */
    public void plusReplyLike(Reply reply, Member member) {

        ReplyLike replyLike = ReplyLike.builder()
                .reply(reply)
                .member(member)
                .build();
        reply.getReplyLike().add(replyLike);
        replyRepository.save(reply);
    }

    /**
     * 댓글 시간 변경(~전)
     */

    public void refreshTime(List<Reply> replyList) {
        for (Reply reply : replyList) {
            if (reply.getModifyDate() == null){
                reply.insertReplyTime(convertDateTime(reply.getCreateDate()));
            }else{
                reply.insertReplyTime(convertDateTime(reply.getModifyDate()));
            }
        }
    }

    public static String convertDateTime(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now();

        long diffTime = localDateTime.until(now, ChronoUnit.SECONDS); // now보다 이후면 +, 전이면 -

        if (diffTime < ReplyTime.SEC){
            return diffTime + "초전";
        }
        diffTime = diffTime / ReplyTime.SEC;
        if (diffTime < ReplyTime.MIN) {
            return diffTime + "분 전";
        }
        diffTime = diffTime / ReplyTime.MIN;
        if (diffTime < ReplyTime.HOUR) {
            return diffTime + "시간 전";
        }
        diffTime = diffTime / ReplyTime.HOUR;
        if (diffTime < ReplyTime.DAY) {
            return diffTime + "일 전";
        }
        diffTime = diffTime / ReplyTime.DAY;
        if (diffTime < ReplyTime.MONTH) {
            return diffTime + "개월 전";
        }

        diffTime = diffTime / ReplyTime.MONTH;
        return diffTime + "년 전";
    }




}