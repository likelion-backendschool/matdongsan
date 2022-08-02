package com.matdongsan.domain.posts;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Posts {

    @Id
    private Long id;

    private String title;

    private String content;


}
