package com.matdongsan.web.controller;

import com.matdongsan.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainPageController {

    private final PostService postService;

    @GetMapping("/")
    public String mainPageMapping(Model model) {
        model.addAttribute("top5", postService.findTop5Post());
        return "index";
    }

    @GetMapping("/error-page")
    public String errorNotFoundPage() {
        return "error-page";
    }
}
