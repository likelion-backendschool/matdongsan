package com.matdongsan.web.dto.posts;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class PostCreateDto {

    @NotBlank(message = "제목을 입력하세요")
    @Size(min = 5, max = 15, message = "제목은 5글자 ~ 15글자 이내로 작성해주세요.")
    private String title; // 제목

    @NotBlank(message = "내용을 입력하세요")
    private String content; // 내용

    private List<MultipartFile> imgFiles;

    private LocalDateTime modifiedTime;

    private Boolean privateStatus;

    @NotNull(message = "음식점을 골라주세요")
    private Long placeId;

}
