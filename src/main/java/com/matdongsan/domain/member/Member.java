package com.matdongsan.domain.member;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    // 로그인 아이디
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private String gender;

    private LocalDateTime signUpDate;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Builder
    public Member(String username, String password, String email, String gender, LocalDateTime signUpDate, MemberRole memberRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.signUpDate = signUpDate;
        this.memberRole = memberRole;
    }

    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword) {
        return passwordEncoder.matches(checkPassword, getPassword());
    }
}
