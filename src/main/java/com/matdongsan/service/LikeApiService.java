package com.matdongsan.service;

import com.matdongsan.domain.like.PostLike;
import com.matdongsan.domain.like.PostLikeRepository;
import com.matdongsan.domain.like.ReplyLike;
import com.matdongsan.domain.like.ReplyLikeRepository;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.post.Post;
import com.matdongsan.domain.reply.Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeApiService {

    private final PostLikeRepository postLikeRepository;
    private final ReplyLikeRepository replyLikeRepository;

    private final PostService postService;
    private final ReplyService replyService;

    /* Post Like 시작 */

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
    public boolean modifyLikeStatus(Member member, Long id, String sort) {
        switch (sort) {
            case "post":
                Post currentPost = postService.findById(id);
                if (existPostLikeFlag(member, currentPost)) {
                    PostLike currentPostLike = postLikeRepository.findByMemberAndPost(member, currentPost);
                    postLikeRepository.delete(currentPostLike);
                    return false;
                } else {
                    createNewPostLike(member, currentPost);
                    return true;
                }
            case "reply":
                Reply currentReply = replyService.getReply(id);
                if (existReplyLikeFlag(member, currentReply)) {
                    ReplyLike currentReplyLike = replyLikeRepository.findByMemberAndReply(member, currentReply);
                    replyLikeRepository.delete(currentReplyLike);
                    return false;
                } else {
                    createNewReplyLike(member, currentReply);
                    return true;
                }
        }
        return false;
    }

    /* Post Like 끝 */

    /* Reply Like 시작 */

    @Transactional
    public void createNewReplyLike(Member member, Reply currentReply) {
        ReplyLike newPostLike = ReplyLike.builder()
                .member(member)
                .reply(currentReply)
                .build();
        replyLikeRepository.save(newPostLike);
    }

    public boolean existReplyLikeFlag(Member member, Reply reply) {
        return replyLikeRepository.existsByMemberAndReply(member, reply);
    }


    /* Reply Like 끝 */
}
