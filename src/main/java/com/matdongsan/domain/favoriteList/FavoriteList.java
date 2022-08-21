package com.matdongsan.domain.favoriteList;

import javax.persistence.*;

@Entity
public class FavoriteList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 북마크 제목
    @Column(length = 200)
    private String subject;
    // 북마크 장소 1대다 매치
}