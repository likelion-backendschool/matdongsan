package com.matdongsan.web.controller.bookmark;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AuthUser;
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

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final FavoriteService favoriteService;

    /**
     *
     * @param account
     * @param subject
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/bookmark/create")
    public String createBookmark(@AuthUser Account account, @PathParam("subject") String subject) {
        Member member = account.getMember();

        bookmarkService.createBookmark(subject,member);

        return "redirect:/favorite/list";
    }

    /**
     *
     * @param account
     * @param bookmarkId
     * @param favoriteId
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/bookmark/addFavorite/{bookmarkId}/{favoriteId}")
    public String addFavoriteInBookmark(@AuthUser Account account, @PathVariable("bookmarkId")Long bookmarkId, @PathVariable("favoriteId") Long favoriteId) {
        Member member = account.getMember();

        Bookmark bookmark = bookmarkService.findByMemberAndId(member, bookmarkId);
        Favorite favorite = favoriteService.findByMemberAndId(member, favoriteId);
        bookmark.addFavorite(favorite);
        bookmarkService.save(bookmark);

        return "redirect:/favorite/list";
    }
}
