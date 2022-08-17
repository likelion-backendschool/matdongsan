package com.matdongsan.domain.account;

import com.matdongsan.domain.posts.Posts;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity @Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {
    // 주석 추가
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    // 로그인 아이디
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private Date birth;

    private String gender;

    private String introduce;

    private LocalDateTime signUpDate;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Posts> postsList = new ArrayList<>();

//    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
//    private List<Reply> replyList = new ArrayList<>();
}
