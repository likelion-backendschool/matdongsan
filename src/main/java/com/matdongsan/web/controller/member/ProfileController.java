package com.matdongsan.web.controller.member;

import com.matdongsan.domain.member.CurrentUser;
import com.matdongsan.domain.member.Member;
import com.matdongsan.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("")
    public String showMyProfile(@CurrentUser Member member, Model model) {

        model.addAttribute("member", member);

        return "profile/profile-main";
    }

//    @GetMapping("/{username}")
    public String showProfilePage(@CurrentUser Member member,
                                  @PathVariable String username,
                                  Model model) {
        Member currentMember = profileService.findUserInfoByUserName(username);
        model.addAttribute("member", member);
        if (currentMember == null) {
            return "redirect:/profile";
        }
        return "profile/profile-main";
    }

}
