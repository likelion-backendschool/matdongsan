package com.matdongsan.infra;

import com.matdongsan.domain.account.Account;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Slf4j
@Getter
@Setter
public class SecurityUser extends User {
    private Account account;
    private String nickname;

    public SecurityUser(Account account) {
        super(account.getUsername(), account.getPassword(), AuthorityUtils.createAuthorityList(account.getAccountRole().toString()));

        log.info("SecurityUser account.username = {}", account.getUsername());
        log.info("SecurityUser account.password = {}", account.getPassword());
        log.info("SecurityUser account.accountRole = {}", account.getAccountRole().toString());

        this.account = account;
        this.nickname = account.getMember().getNickname();
    }
}
