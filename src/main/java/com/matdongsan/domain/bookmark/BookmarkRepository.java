package com.matdongsan.domain.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
