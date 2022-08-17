package com.matdongsan.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainPageController {

    @GetMapping("/")
    public String mainPageMapping() {
        return "index";
    }

    @GetMapping("/error-page")
    public String errorNotFoundPage() {
        return "error-page";
    }
}
