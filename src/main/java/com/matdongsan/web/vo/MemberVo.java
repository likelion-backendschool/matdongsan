package com.matdongsan.web.vo;

import com.matdongsan.domain.member.MemberRole;
import com.matdongsan.domain.posts.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MemberVo {
    private final String username;
    private final String email;
    private final Date birth;
    private final String gender;
    private final List<Posts> postsList;

}
