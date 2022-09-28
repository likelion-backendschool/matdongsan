package com.matdongsan.domain.reply;

import com.matdongsan.domain.like.ReplyLike;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.post.Post;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
//계정마다 관리가능하게
public class Reply {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 500)
    private String comment;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY) //댓글 작성자
    private Member writer;

    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReplyLike> replyLike = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Reply parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Reply> child = new ArrayList<>();

    private String replyTime;

    //데이터 필드를 가지고 있는 단에서 비즈니스 로직내기
    public void updateComment(String comment) {
        this.comment = comment;
        this.modifyDate = LocalDateTime.now();
    }

    public void insertReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }


}
