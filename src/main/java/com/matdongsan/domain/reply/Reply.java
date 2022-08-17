package com.matdongsan.domain.reply;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.posts.Posts;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime createComment;

    @LastModifiedDate
    private LocalDateTime modifyComment;

    @ManyToOne
//    @JoinColumn(name = "postId")
    private Posts posts;

    @ManyToOne //댓글 작성자
//    @JoinColumn(name="writerId")
    private Member writer;


    //데이터 필드를 가지고 있는 단에서 비즈니스 로직내기
    public void updateComment(String comment) {
        this.comment = comment;
    }


//    public void save(Posts post, Member member) {
//        this.posts = post;
//        this.writer = member;
//    }

}
