package com.matdongsan.service.report;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.account.AccountRepository;
import com.matdongsan.domain.posts.Posts;
import com.matdongsan.domain.posts.PostsRepository;
import com.matdongsan.domain.report.PostReport;
import com.matdongsan.domain.report.PostReportRepository;
import com.matdongsan.domain.report.ReportCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class PostReportService {

    private final PostReportRepository postReportRepository;
    private final AccountRepository accountRepository;
    private final PostsRepository postsRepository;

    public PostReport savePostReport(long memberId, long postId,
                                     ReportCategory reportCategory,String content){
        Account account = accountRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원이 없음"));
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없음"));
        return postReportRepository.save(
                PostReport.createReport(account.getMember(), post, reportCategory, content)
        );
    }



}
