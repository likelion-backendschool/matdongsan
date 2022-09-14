package com.matdongsan.service.report;

import com.matdongsan.domain.account.AccountRepository;
import com.matdongsan.domain.post.PostRepository;
import com.matdongsan.domain.report.PostReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PostReportServiceTest {

    @Autowired
    private PostReportService postReportService;
    @Autowired
    private PostReportRepository postReportRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    @Rollback
    @DisplayName("PostReport 생성")
    void createPostReport(){
        // test comment
        // 멤버, 게시글 생성
//        Account member = Account.builder()
//                .username("memberA")
//                .password(passwordEncoder.encode("123"))
//                .email("memberA@gmail.com")
//                .gender("male")
//                .birth(Date.from(LocalDateTime.now().minusDays(10).atZone(ZoneId.systemDefault()).toInstant()))
//                .signUpDate(LocalDateTime.now())
//                .accountRole(AccountRole.ROLE_USER)
//                .build();
//        Post post = new Post(null, member, "title", "content", LocalDateTime.now(), LocalDateTime.now());
//
//        Account savedMember = accountRepository.save(member);
//        Post savedPost = postRepository.save(post);
//
//        // 결과 비교
//        PostReport savedReport = postReportService
//                .savePostReport(savedMember.getId(), savedPost.getId(), ReportCategory.TYPE1, "content");
//        PostReport findReport = postReportRepository.findById(savedReport.getId()).get();
//
//        assertThat(savedReport).isEqualTo(findReport);
    }
}