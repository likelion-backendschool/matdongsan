package com.matdongsan.web.controller.account;

import com.matdongsan.domain.account.AccountRepository;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberAge;
import com.matdongsan.domain.member.MemberRepository;
import com.matdongsan.web.dto.member.MemberInfoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@WebAppConfiguration
class AccountControllerTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Rollback
    @Transactional
    void initEnumTest() {

        MemberInfoDto newDto = new MemberInfoDto();
        newDto.setMemberAge(MemberAge.MEMBER_AGE_20S);
        Member newMember = memberRepository.save(Member.builder()
                .memberAge(newDto.getMemberAge())
                .build());

        assertThat(newMember.getMemberAge()).isEqualTo(MemberAge.MEMBER_AGE_20S);

        /*accountRepository.save(Account.builder()
                .username("abc123")
                .accountRole(AccountRole.ROLE_USER)
                .member(newMember)
                .build());*/
    }
}