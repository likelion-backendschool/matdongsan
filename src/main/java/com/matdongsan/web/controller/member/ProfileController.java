package com.matdongsan.web.controller.member;

import com.matdongsan.domain.member.CurrentUser;
import com.matdongsan.domain.member.Member;
import com.matdongsan.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile")
    public String showMyProfile(@CurrentUser Member member, Model model) {

        model.addAttribute("member", member);

        return "profile/profile-main";
    }

    @GetMapping("/profile/setting")
    public String showProfilePage(@CurrentUser Member member, Model model) {

        model.addAttribute("member", member);

        return "profile/profile-setting";
    }

}
