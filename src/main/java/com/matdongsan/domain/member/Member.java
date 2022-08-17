package com.matdongsan.domain.member;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AccountRole;
import com.matdongsan.domain.posts.Posts;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
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

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Account account;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Posts> postsList = new ArrayList<>();

//    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
//    private List<Reply> replyList = new ArrayList<>();
}