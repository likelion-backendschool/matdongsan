package com.matdongsan.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class BoardFormDto {
    private String title;

    private String content;
}
