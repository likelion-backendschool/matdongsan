package com.matdongsan.web.controller;

import com.matdongsan.service.MemberService;
import com.matdongsan.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String showSignUpPage(Model model) {
        model.addAttribute(new MemberDto());
        return "member/member-login";
    }

//    @GetMapping("/member/signup")
    public String createNewMember(@Valid MemberDto memberDto) {
        memberService.saveNewMember(memberDto);
        return "redirect:/";
    }
}
