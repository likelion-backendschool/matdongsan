package com.matdongsan.web.dto.posts;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.posts.Posts;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostCreateDto {

    private Long id;

    private Member author;  // 작성자

    private String title; // 제목

    private String content; // 내용

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

    private Boolean privateStatus;

    public Posts toEntity() {
        return Posts.builder()
                .id(id)
                .author(author)
                .title(title)
                .content(content)
                .privateStatus(privateStatus)
                .build();
    }

    @Builder
    public PostCreateDto(Long id, Member author, String title, String content, LocalDateTime createdTime, LocalDateTime modifiedTime , Boolean privateStatus) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.privateStatus = privateStatus;
    }

}
