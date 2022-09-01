package com.matdongsan.web.controller.account;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AuthUser;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.ProfileService;
import com.matdongsan.web.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;
    private final AccountService accountService;

    @GetMapping("/profile")
    public String showMyProfile(Principal principal, Model model) {
        log.info("principal.getName()={}", principal.getName());
        MemberVo member = accountService.getReadOnlyMember(principal.getName());
        model.addAttribute("member", member);

        return "profile/profile-main";
    }

    @GetMapping("/profile/setting")
    public String showProfilePage(@AuthUser Account account, Model model) {
        log.info("account.getUsername()={}", account.getUsername());

        MemberVo member = accountService.getReadOnlyMember(account.getUsername());
        model.addAttribute("member", member);

        return "profile/profile-setting";
    }

}
