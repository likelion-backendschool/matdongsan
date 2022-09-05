package com.matdongsan.web.controller.bookmark;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.bookmark.Bookmark;
import com.matdongsan.domain.favorite.Favorite;
import com.matdongsan.domain.member.Member;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.BookmarkService;
import com.matdongsan.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookmarkController {

    private final AccountService accountService;

    private final BookmarkService bookmarkService;

    private final FavoriteService favoriteService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/bookmark/create")
    public String createBookmark(Principal principal, @PathParam("subject") String subject) {
        Account account = accountService.findAccountByUsername(principal.getName());
        Member member = account.getMember();

        bookmarkService.createBookmark(subject,member);

        return "redirect:/favorite/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/bookmark/addFavorite/{bookmarkId}/{favoriteId}")
    public String addFavoriteInBookmark(Principal principal, @PathVariable("bookmarkId")Long bookmarkId, @PathVariable("favoriteId") Long favoriteId) {
        Account account = accountService.findAccountByUsername(principal.getName());
        Member member = account.getMember();

        Bookmark bookmark = bookmarkService.findByMemberAndId(member, bookmarkId);
        Favorite favorite = favoriteService.findByMemberAndId(member, favoriteId);
        log.info("bookmark name -> {}", bookmark.getId());
        bookmark.addFavorite(favorite);
        bookmarkService.save(bookmark);
        log.info("bookmark save (first) -> {}", bookmark.getFavoriteList().stream().findFirst().get().getId());


        return "redirect:/favorite/list";
    }
}
