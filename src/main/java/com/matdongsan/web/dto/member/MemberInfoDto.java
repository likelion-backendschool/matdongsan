package com.matdongsan.web.dto.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter @Setter
public class MemberInfoDto {

    @NotBlank
    private String introduce;

    @NotBlank
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private String gender;
}
