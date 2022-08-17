package com.matdongsan.web.dto.account;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter @Setter
public class AccountSignUpDto {

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9_-]{3,20}$")
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    private String gender;

}
