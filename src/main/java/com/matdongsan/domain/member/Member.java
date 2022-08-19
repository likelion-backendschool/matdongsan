package com.matdongsan.domain.member;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AccountRole;
import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.reply.Reply;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    // 주석 추가
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date birth;

    private String gender;

    private String introduce;

    private LocalDateTime signUpDate;

    @Enumerated(EnumType.STRING)
    private MemberAge memberAge;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Account account;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Posts> postsList = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<Reply> replyList = new ArrayList<>();

    public void addReply(Reply reply) {
        this.replyList.add(reply);
        reply.setWriter(this);
    }

    public void addBasicInfo(Date birth, String gender, String introduce, MemberAge memberAge) {
        this.birth = birth;
        this.gender = gender;
        this.introduce = introduce;
        this.memberAge = memberAge;
    }

    public void changeBasicInfo(String introduce) {
        this.introduce = introduce;
    }
}