package com.matdongsan.service;

import com.matdongsan.domain.bookmark.Bookmark;
import com.matdongsan.domain.bookmark.BookmarkRepository;
import com.matdongsan.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    public void createBookmark(String subject, Member member) {
        Bookmark bookmark = new Bookmark();
        bookmark.setSubject(subject);
        bookmark.setMember(member);
        bookmarkRepository.save(bookmark);
    }
}
