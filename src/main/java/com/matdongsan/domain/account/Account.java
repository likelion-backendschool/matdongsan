package com.matdongsan.domain.account;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.posts.Posts;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity @Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void addMember(Member member) {
        this.member = member;
    }
}
