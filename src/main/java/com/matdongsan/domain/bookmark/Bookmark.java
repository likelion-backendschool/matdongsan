package com.matdongsan.domain.bookmark;

import com.matdongsan.domain.favorite.Favorite;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 북마크 제목
    @Column(length = 200)
    private String subject;

    // 북마크 장소 1대다 매치
    @Nullable
    @OneToMany(mappedBy = "favorite", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();
}