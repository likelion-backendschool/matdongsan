package com.matdongsan.web.controller.likes;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class PostLikeController {

    @ResponseBody
    @PostMapping("")
    public boolean addNewLike(@AuthUser Account account) {

        return true;
    }
}
