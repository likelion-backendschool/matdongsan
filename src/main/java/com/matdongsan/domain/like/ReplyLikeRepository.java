package com.matdongsan.domain.like;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.post.Post;
import com.matdongsan.domain.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {
    boolean existsByMemberAndReply(Member member, Reply reply);

    ReplyLike findByMemberAndReply(Member member, Reply reply);
}
