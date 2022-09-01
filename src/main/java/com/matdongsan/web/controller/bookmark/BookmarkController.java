package com.matdongsan.web.controller.bookmark;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.member.Member;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.server.PathParam;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class BookmarkController {

    private final AccountService accountService;

    private final BookmarkService bookmarkService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/bookmark/create")
    public String createBookmark(Principal principal, @PathParam("subject") String subject) {
        Account account = accountService.findAccountByUsername(principal.getName());
        Member member = account.getMember();

        bookmarkService.createBookmark(subject,member);

        return "redirect:/favorite/list";
    }
}
