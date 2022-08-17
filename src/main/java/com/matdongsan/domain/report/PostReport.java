package com.matdongsan.domain.report;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.posts.Posts;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Posts posts;

    @Enumerated(EnumType.STRING)
    private ReportCategory reportCategory;

    private String content;

    private LocalDateTime reportDate;

    //== 생성 메서드 == //
    public static PostReport createReport(Member member, Posts posts, ReportCategory reportCategory, String content) {
        PostReport postReport = new PostReport();
        postReport.member = member;
        postReport.posts = posts;
        postReport.reportCategory = reportCategory;
        postReport.content = content;
        postReport.reportDate = LocalDateTime.now();
        return postReport;
    }




}
