package com.matdongsan.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> searchTitle(String keyword, Pageable pageable);

    Page<Post> searchContent(String keyword, Pageable pageable);

    Page<Post> searchAuthor(String keyword, Pageable pageable);
}
