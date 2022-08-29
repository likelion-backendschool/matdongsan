package com.matdongsan.web.dto.posts;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.posts.Posts;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
public class PostCreateDto {

    private String title; // 제목

    private String content; // 내용

    private LocalDateTime modifiedTime;

    private Boolean privateStatus;

}
