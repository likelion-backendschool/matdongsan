package com.matdongsan.domain.posts;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.reply.Reply;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;  // 작성자

    @Column(length = 200)
    private String title; // 제목

    @Column(columnDefinition = "TEXT")
    private String content; // 내용

    private LocalDateTime createdTime; // 생성 날짜

    private LocalDateTime modifiedTime; // 수정 날짜

    //   2.post단에 추가할것

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<Reply> replyList = new ArrayList<>();

    public void addReply(Reply reply){
        this.replyList.add(reply);
        reply.setPosts(this);
    }


}
