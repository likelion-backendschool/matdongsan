package com.matdongsan.domain.bookmark;

import com.matdongsan.domain.favorite.Favorite;
import com.matdongsan.domain.member.Member;
import com.matdongsan.web.dto.FavoriteListDto;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
    @OneToMany(mappedBy = "bookmark", cascade = CascadeType.ALL)
    private List<Favorite> favoriteList = new ArrayList<>();

    public void addFavorite(Favorite favorite) {
        favorite.setBookMark(this);
        getFavoriteList().add(favorite);
        for (Favorite favorite1 : favoriteList) {
            log.info("favoriteList = {}",favorite1.getPlace().getAddressName());
        }
    }
}