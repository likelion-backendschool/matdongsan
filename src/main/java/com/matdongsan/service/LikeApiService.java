package com.matdongsan.service;

import com.matdongsan.domain.like.PostLike;
import com.matdongsan.domain.like.PostLikeRepository;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeApiService {

    private final PostLikeRepository postLikeRepository;
    private final PostService postService;

    @Transactional
    public void createNewPostLike(Member member, Post currentPost) {
        PostLike newPostLike = PostLike.builder()
                .member(member)
                .post(currentPost)
                .build();
        postLikeRepository.save(newPostLike);
    }

    public boolean existPostLikeFlag(Member member, Post post) {
        return postLikeRepository.existsByMemberAndPost(member, post);
    }

    @Transactional
    public boolean modifyLikeStatus(Member member, Long postId) {
        Post currentPost = postService.findById(postId);
        boolean flag = existPostLikeFlag(member, currentPost);
        if (flag) {
            PostLike currentPostLike = postLikeRepository.findByMemberAndPost(member, currentPost);
            postLikeRepository.delete(currentPostLike);
            return false;
        } else {
            createNewPostLike(member, currentPost);
            return true;
        }
    }
}
