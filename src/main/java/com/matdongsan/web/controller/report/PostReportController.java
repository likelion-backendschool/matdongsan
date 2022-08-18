package com.matdongsan.web.controller.report;

import com.matdongsan.domain.account.Account;
import com.matdongsan.domain.report.PostReport;
import com.matdongsan.domain.report.PostReportRepository;
import com.matdongsan.service.AccountService;
import com.matdongsan.service.report.PostReportService;
import com.matdongsan.web.dto.report.PostReportDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostReportController {

    private final PostReportRepository postReportRepository;
    private final PostReportService postReportService;
    private final AccountService accountService;

    @PostMapping("/posts/{postId}/report")
    @ResponseBody
    public String postReport(
            @ModelAttribute("postReportDto") PostReportDto reportDto,
            @PathVariable("postId") Long postId,
            Principal principal
    ) {
        Account account = accountService.findAccountByUsername(principal.getName());
        PostReport report = postReportService.savePostReport(account.getId(), postId,
                reportDto.getReportCategory(), reportDto.getContent());
        log.info("report = {}", report);
        return "OK";
    }
}
