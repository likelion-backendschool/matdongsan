package com.matdongsan.web.dto.account;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter @Setter
public class AccountSignUpDto {

    @Size(min = 5, max = 15, message = "아이디는 5글자 ~ 15글자 이내로 작성해주세요!")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9_-]{3,20}$", message = "아이디에 특수문자를 포함할 수 없습니다!")
    private String username;

    @NotBlank(message = "비밀번호에 공백을 포함할 수 없습니다!")
    @Size(min = 8, max = 20, message = "비밀번호 길이를 확인해주세요!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는 최소 8자리 이상이며, 숫자, 문자, 특수문자 각각 1개 이상 포함해주세요.")
    private String password;

    @NotBlank(message = "이메일에 공백을 포함할 수 없습니다!")
    private String email;

}
