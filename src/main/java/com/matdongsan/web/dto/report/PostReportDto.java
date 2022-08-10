package com.matdongsan.web.dto.report;

import com.matdongsan.domain.report.ReportCategory;
import lombok.Data;

@Data
public class PostReportDto {

    ReportCategory reportCategory;
    String content;


}
