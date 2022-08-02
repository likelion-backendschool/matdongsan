package com.matdongsan.domain.member;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Member {
    @Id
    private Long id;

    private String loginId;

    private String loginPwd;
}
