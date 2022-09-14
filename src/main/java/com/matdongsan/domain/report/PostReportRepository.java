package com.matdongsan.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostReportRepository extends JpaRepository<PostReport,Long> {

    List<PostReport> findAllByMemberId(Long memberId);

    List<PostReport> findAllByPostId(Long postId);
}
