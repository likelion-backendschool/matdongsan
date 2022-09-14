package com.matdongsan.domain.like;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByMemberAndPost(Member member, Post post);

    PostLike findByMemberAndPost(Member member, Post post);
}
