package com.matdongsan.web.dto;

import lombok.Data;

import java.time.LocalDateTime;

//replayDto
@Data
public class ReplyDto {
    Long id;
    Long boardId;
    String body;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}
