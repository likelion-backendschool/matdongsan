package com.matdongsan.web.controller.report;

import com.matdongsan.domain.member.CurrentUser;
import com.matdongsan.domain.member.Member;
import com.matdongsan.domain.report.PostReport;
import com.matdongsan.domain.report.PostReportRepository;
import com.matdongsan.service.report.PostReportService;
import com.matdongsan.web.dto.report.PostReportDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostReportController {

    private final PostReportRepository postReportRepository;
    private final PostReportService postReportService;

    @PostMapping("/posts/{postId}/report")
    @ResponseBody
    public String postReport(
            @ModelAttribute("postReportDto")PostReportDto reportDto,
            @PathVariable("postId") Long postId,
            @CurrentUser Member member
            ){
        PostReport report = postReportService.savePostReport(member.getId(), postId,
                reportDto.getReportCategory(), reportDto.getContent());
        log.info("report = {}",report);
        return "OK";
    }
}
