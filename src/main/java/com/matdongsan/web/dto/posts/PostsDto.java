package com.matdongsan.web.dto.posts;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.posts.Posts;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostsDto {

    private Long id;

    private Account author;  // 작성자

    private String title; // 제목

    private String content; // 내용

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    public Posts toEntity() {
        Posts posts = Posts.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .build();

        return posts;
    }

    @Builder
    public PostsDto(Long id, Account author, String title, String content, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }
}
