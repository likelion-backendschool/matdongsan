package com.matdongsan.domain.favoriteList;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FavoriteList {
    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
}