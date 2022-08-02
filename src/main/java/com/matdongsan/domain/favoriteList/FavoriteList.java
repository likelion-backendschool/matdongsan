package com.matdongsan.domain.favoriteList;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FavoriteList {
    @Id
    private Long id;
    private String title;
}
