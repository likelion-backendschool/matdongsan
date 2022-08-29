package com.matdongsan.domain.posts;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.reply.Reply;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Posts{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;  // 작성자

    @Column(length = 200 , nullable = false)
    private String title; // 제목

    @Column(nullable = false , columnDefinition = "TEXT")
    private String content; // 내용

    @Column(nullable = false)
    private boolean privateStatus; // 공개 / 비공개 여부  true => 비공개 , false => 공개

    @OneToMany(mappedBy = "posts" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    public void addReply(Reply reply) {
        this.replyList.add(reply);
        reply.setPosts(this);
    }

}
