package com.matdongsan.domain.posts;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import com.matdongsan.domain.reply.Reply;
import com.matdongsan.util.ImageUtil;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    // NPE 때문에 추가
    @Nullable
    private String imageUrls;

    @Column(updatable = false) // 수정 불가
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime modifiedTime;

    @Column(nullable = false)
    private boolean privateStatus; // 공개 / 비공개 여부  true => 비공개 , false => 공개

    @OneToMany(mappedBy = "posts" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

//    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<PostLike> postLike;

    public void addPlace(Place place) {
        this.place = place;
        place.getPosts().add(this);
    }

    public void addReply(Reply reply) {
        this.replyList.add(reply);
        reply.setPosts(this);
    }

    // 수정 메소드
    public void change(String title, String content,  String imageUrls , boolean privateStatus) {
        this.title = title;
        this.content = content;
        this.imageUrls = imageUrls == null ? "" : imageUrls;
        this.privateStatus = privateStatus;
    }
}
