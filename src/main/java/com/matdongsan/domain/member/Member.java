package com.matdongsan.domain.member;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String loginId;

    private String loginPwd;

    @Column(unique = true)
    private String nickname;

    private LocalDateTime signUpDate;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
}
