package com.matdongsan.web.dto.posts;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.posts.Posts;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class PostCreateDto {

    @NotBlank(message = "제목을 입력하세요")
    @Size(min = 10 , max = 20 , message = "제목이 너무 짧습니다.")
    private String title; // 제목

    @NotBlank(message = "내용을 입력하세요")
    private String content; // 내용

    private List<MultipartFile> imgFiles;

    private LocalDateTime modifiedTime;

    private Boolean privateStatus;

}
