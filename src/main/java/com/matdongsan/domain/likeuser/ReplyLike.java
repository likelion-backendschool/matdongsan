package com.matdongsan.domain.likeuser;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.reply.Reply;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@AllArgsConstructor
@Entity
@Builder
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ReplyLike {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY )
    private Reply reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Include
    private Member member;

    


}
