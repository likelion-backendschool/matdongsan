package com.matdongsan.service;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AccountRepository;
import com.matdongsan.domain.account.AccountRole;
import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
@Slf4j
class AccountServiceTest {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    public void helloTest() {
        Account newAccount = Account.builder()
                .id(null)
                .username("abc123")
                .password(passwordEncoder.encode("abc123"))
                .accountRole(AccountRole.ROLE_USER)
                .member(null)
                .build();
        Account createAccount = accountRepository.save(newAccount);

        assertThat(newAccount.getId()).isEqualTo(createAccount.getId());
    }
}