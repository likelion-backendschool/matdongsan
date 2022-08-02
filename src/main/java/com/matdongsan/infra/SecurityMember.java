package com.matdongsan.infra;

import com.matdongsan.domain.member.Member;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityMember extends User {
    private Member member;

    public SecurityMember(Member member) {
        super(member.getUsername(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getMemberRole().toString()));
        this.member = member;
    }
}
