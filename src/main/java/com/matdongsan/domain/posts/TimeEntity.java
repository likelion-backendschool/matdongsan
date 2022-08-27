package com.matdongsan.domain.posts;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
// MatdongsanApplication에 @EnableJpaAuditing 어노테이션 추가해야함.
public class TimeEntity {

    @CreatedDate
    @Column(updatable = false) // 수정 불가
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime modifiedTime;

    public void modify(LocalDateTime time){
        this.modifiedTime = time;
    }
}
