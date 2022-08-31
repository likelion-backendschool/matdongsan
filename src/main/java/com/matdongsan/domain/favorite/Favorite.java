package com.matdongsan.domain.favorite;

import com.matdongsan.domain.bookmark.Bookmark;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.place.Place;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Setter
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {
    // 고유 Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    private Bookmark bookMark;

    public Favorite(Member member, Place place) {
        this.member = member;
        setPlace(place);
    }

    private void setPlace(Place place) {
        place.getFavorites().add(this);
        this.place = place;
    }
}
