package com.matdongsan.web.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReplyDto {

//    @NotEmpty(message = "댓글은 필수입니다.")
    private String comment;

    private Long id;
    private String replyTime;
    private Integer replyLikeCount;
    private String nickname;

    private List<ReplyDto> child = new ArrayList<>();

    public void insertComment(String comment) {
        this.comment = comment;
    }

}
