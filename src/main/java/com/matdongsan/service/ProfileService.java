package com.matdongsan.service;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AccountRepository;
import com.matdongsan.domain.member.MemberRepository;
import com.matdongsan.web.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @Slf4j
@RequiredArgsConstructor
public class ProfileService {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;

    public Account findUserInfoByUserName(String username) {
        Optional<Account> currentMember = accountRepository.findByUsername(username);
        return currentMember.orElse(null);
    }

    public MemberVo findByUsernameOrNickname(String nickname) {
        if (accountRepository.findByUsername(nickname).isPresent()) {
            return accountService.getReadOnlyMember(accountRepository.findByUsername(nickname).get().getUsername());
        } else if (memberRepository.findByNickname(nickname).isPresent()) {
            return accountService.getReadOnlyMember(memberRepository.findByNickname(nickname).get().getNickname());
        }
        return null;
    }
}
