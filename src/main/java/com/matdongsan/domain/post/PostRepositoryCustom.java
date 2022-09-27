package com.matdongsan.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    Page<Post> searchTitle(String keyword, Pageable pageable);

    Page<Post> searchContent(String keyword, Pageable pageable);

    Page<Post> searchAuthor(String keyword, Pageable pageable);

    List<Post> findPostTop5();
}
