package com.matdongsan.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> searchKeyword(String keyword, Pageable pageable);
}
