package com.matdongsan.service.report;

import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.member.MemberRepository;
import com.matdongsan.domain.member.MemberRole;
import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.posts.PostsRepository;
import com.matdongsan.domain.report.PostReport;
import com.matdongsan.domain.report.PostReportRepository;
import com.matdongsan.domain.report.ReportCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostReportServiceTest {

    @Autowired
    private PostReportService postReportService;
    @Autowired
    private PostReportRepository postReportRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    @Rollback
    @DisplayName("PostReport 생성")
    void createPostReport(){
        // 멤버, 게시글 생성
        Member member = Member.builder()
                .username("memberA")
                .password(passwordEncoder.encode("123"))
                .email("memberA@gmail.com")
                .gender("male")
                .birth(Date.from(LocalDateTime.now().minusDays(10).atZone(ZoneId.systemDefault()).toInstant()))
                .signUpDate(LocalDateTime.now())
                .memberRole(MemberRole.ROLE_USER)
                .build();
        Posts posts = new Posts(null, member, "title", "content", LocalDateTime.now(), LocalDateTime.now());

        Member savedMember = memberRepository.save(member);
        Posts savedPost = postsRepository.save(posts);

        // 결과 비교
        PostReport savedReport = postReportService
                .savePostReport(savedMember.getId(), savedPost.getId(), ReportCategory.TYPE1, "content");
        PostReport findReport = postReportRepository.findById(savedReport.getId()).get();

        assertThat(savedReport).isEqualTo(findReport);
    }
}