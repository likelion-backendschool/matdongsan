package com.matdongsan.domain.posts;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.reply.Reply;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;  // 작성자

    @Column(length = 200 , nullable = false)
    private String title; // 제목

    @Column(nullable = false , columnDefinition = "TEXT")
    private String content; // 내용

    @Column(nullable = false)
    private boolean privateStatus; // 공개 / 비공개 여부  true => 비공개 , false => 공개


    @Builder
    public Posts(Long id, Member author, String title, String content, boolean privateStatus) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.privateStatus = privateStatus;

    }

    public void update(Posts post) {
        if (post.author != null) {
            this.author = post.author;
        }

        if (post.title != null) {
            this.title = post.title;
        }

        if (post.content != null) {
            this.content = post.content;
        }

        if (!post.privateStatus) {
            this.privateStatus = post.privateStatus;
        }

        // 수정 기간 변경
        modify(post.getModifiedTime());

    }

    //   2.post단에 추가할것

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<Reply> replyList = new ArrayList<>();

    public void addReply(Reply reply) {
        this.replyList.add(reply);
        reply.setPosts(this);
    }
}
