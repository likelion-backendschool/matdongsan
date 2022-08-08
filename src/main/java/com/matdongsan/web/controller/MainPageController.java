package com.matdongsan.web.controller;

import com.matdongsan.domain.member.CurrentUser;
import com.matdongsan.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainPageController {

    @GetMapping("/")
    public String mainPageMapping(@CurrentUser Member member, Model model) {
        model.addAttribute(member);

        return "index";
    }
}
