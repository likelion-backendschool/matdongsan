package com.matdongsan.web.dto.posts;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class PostUpdateDto {

    private Long id;

    private String title;

    private String content;

//    private List<MultipartFile> imgFiles;

    //    private LocalDateTime modifiedTime;
    private String placeName;

    private Boolean privateStatus;
}
