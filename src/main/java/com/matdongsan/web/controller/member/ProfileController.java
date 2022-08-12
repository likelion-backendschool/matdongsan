package com.matdongsan.web.controller.member;

import com.matdongsan.domain.member.CurrentUser;
import com.matdongsan.domain.member.Member;
import com.matdongsan.service.MemberService;
import com.matdongsan.service.ProfileService;
import com.matdongsan.web.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;
    private final MemberService memberService;

    @GetMapping("/profile")
    public String showMyProfile() {

//        model.addAttribute("member", member);

        return "profile/profile-main";
    }

    @GetMapping("/profile/setting")
    public String showProfilePage(Principal principal, Model model) {
        log.info("principal.getName()={}", principal.getName());

        MemberVo member = memberService.getReadOnlyMember(principal.getName());
        model.addAttribute(member);

        return "profile/profile-setting";
    }

}
