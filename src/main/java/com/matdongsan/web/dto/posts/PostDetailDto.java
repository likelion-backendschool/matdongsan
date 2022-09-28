package com.matdongsan.web.dto.posts;

import com.matdongsan.domain.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailDto {

    private Long id;

    private Account author;  // 작성자

    private String title; // 제목

    private String content; // 내용

    private LocalDateTime createdTime; // 생성 날짜

    private LocalDateTime modifiedTime; // 수정 날짜
}
