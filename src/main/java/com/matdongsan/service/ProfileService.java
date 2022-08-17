package com.matdongsan.service;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @Slf4j
@RequiredArgsConstructor
public class ProfileService {

    private final AccountRepository accountRepository;

    public Account findUserInfoByUserName(String username) {
        Optional<Account> currentMember = accountRepository.findByUsername(username);
        return currentMember.orElse(null);
    }

    public Account updateMemberProfile() {

        return null;
    }
}
