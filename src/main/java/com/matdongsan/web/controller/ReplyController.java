package com.matdongsan.web.controller;


import com.matdongsan.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

}
