package com.matdongsan.domain.reply;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
//자기 아이디로 들어와서 수정삭제 가능하게 어케하누
public class Reply {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String comment;

    @CreatedDate
    private LocalDateTime createComment;

    @LastModifiedDate
    private LocalDateTime modifyComment;

}
