package com.matdongsan.domain.bookmark;

import com.matdongsan.domain.favorite.Favorite;
import com.matdongsan.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    // 북마크 제목
    @Column(length = 200)
    private String subject;

    // 북마크 장소 1대다 매치
    @Nullable
    @OneToMany(mappedBy = "bookmark", cascade = CascadeType.ALL)
    private List<Favorite> favoriteList = new ArrayList<>();

    public void addFavorite(Favorite favorite) {
        favorite.setBookMark(this);
        getFavoriteList().add(favorite);
    }
}